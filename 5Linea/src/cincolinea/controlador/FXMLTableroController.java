package cincolinea.controlador;

import cincolinea.Main;
import cincolinea.modelo.ConfiguracionPartida;
import cincolinea.modelo.Ficha;
import cincolinea.modelo.Tablero;
import com.jfoenix.controls.JFXButton;
import conexion.ClienteRMI;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.net.URISyntaxException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author Adrian Bustamante Zarate
 * @author Miguel Leonardo Jimenez Jimenez
 */
public class FXMLTableroController implements Initializable {

    @FXML
    private JFXButton btnAbandonarP;
    @FXML
    private Label labelTextoTiempo;
    @FXML
    private Label labelContTiempo;
    @FXML
    private Label labelNombreJugador;
    @FXML
    private Label labelNombreContrincante;
    @FXML
    private Label labelInfoContrincante;
    @FXML
    private Label labelInfoJugador;
    private ResourceBundle idioma;
    private Main main;
    private int tamañoTablero;
    @FXML
    private GridPane tablero;
    private String colorFicha;
    private Socket socket;
    private Tablero tableroLogico;
    private String idUsuario;
    private JSONObject contrincante;
    
    private void inicializarComponentes(){
        labelTextoTiempo.setText(idioma.getString("labelTextoTiempo"));
        labelContTiempo.setText(idioma.getString("labelContTiempo"));
        labelNombreContrincante.setText(idioma.getString("labelNombreContrincante"));
        labelNombreJugador.setText(idioma.getString("labelNombreJugador"));
        btnAbandonarP.setText(idioma.getString("btnAbandonarP"));
        labelInfoJugador.setText(idioma.getString("labelInfoJugador"));
        labelInfoContrincante.setText(idioma.getString("labelInfoContrincante"));
    }
    
    @FXML
    private void abandonarPartida(ActionEvent event) {
        socket.emit("abandonarPartida", idUsuario);
        socket.emit("desconectar", idUsuario);
        socket.disconnect();
        main.desplegarMenuPrincipal(idioma, idUsuario);
        
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void setConfiguracionJugador(ConfiguracionPartida configuracion, String idUsuario){
        this.colorFicha = configuracion.getColorFicha();
        this.tamañoTablero = configuracion.getTamaño();
        this.idUsuario = idUsuario;
        contrincante = new JSONObject();
        contrincante.put("tipo", !configuracion.isEsCreador());
        contrincante.put("idJugador", configuracion.getIdContrincante());
        this.socket = configuracion.getSocket();
        tableroLogico = new Tablero(tamañoTablero);
        try {
            crearConexionIO();
        } catch (URISyntaxException ex) {
            
        }
        crearTablero(configuracion.getTamaño());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle idioma) {
        this.idioma = idioma;
        if (this.idioma != null) {
            inicializarComponentes();
        }
        
    }
    
    private void crearConexionIO() throws URISyntaxException{
        
        socket.on("jugadaRealizada", new Emitter.Listener() {
            @Override
            public void call(Object... os) {

                JSONObject objeto = (JSONObject) os[0];
                Ficha ficha = new Ficha();
                ficha.setX((Integer) objeto.get("x"));
                ficha.setY((Integer) objeto.get("y"));
                ficha.setColorFicha((String) objeto.get("colorFicha"));

                colocarFichaContrincante(ficha);
            }
        }).on("perder", new Emitter.Listener() {
            @Override
            public void call(Object... os) {
                Platform.runLater(()->{
                    MensajeController.mensajeInformacion(idioma.getString("perder"));
                    regresarMenuPrincipal();
                });
                
            }
        }).on("ganarPorAbandono", new Emitter.Listener() {
            @Override
            public void call(Object... os) {
                Platform.runLater(()->{
                    guardarAbandonoPartida(idUsuario, contrincante.getString("idJugador"));
                    MensajeController.mensajeInformacion(idioma.getString("ganarPorAbandono"));
                    regresarMenuPrincipal();
                });
                
            }
        }).on("empatar", new Emitter.Listener() {
            @Override
            public void call(Object... os) {
                Platform.runLater(()->{
                    MensajeController.mensajeInformacion(idioma.getString("empate"));
                    regresarMenuPrincipal();
                });
                
            }
        });
    }
    
    @FXML
    private void retornarCoordenadas(ActionEvent event){
        
        JFXButton boton = (JFXButton) event.getSource();
        
        Ficha ficha = crearFicha(boton.getId());
        
        
            if (tableroLogico.validarJugada(ficha.getX(), ficha.getY(), ficha.getColorFicha())) {
                colocarFicha(boton, colorFicha);

                JSONObject fichaIncriptada = new JSONObject(ficha);

                socket.emit("realizarJugada", fichaIncriptada,contrincante);

                tablero.setDisable(true);
                
                if (tableroLogico.validarSiGano(ficha.getX(), ficha.getY(), ficha.getColorFicha())) {
                    socket.emit("ganar", idUsuario);                   
                    guardarResultado(idUsuario, contrincante.getString("idJugador"));
                    MensajeController.mensajeInformacion(idioma.getString("ganar"));
                    regresarMenuPrincipal();
                }
                
                if (tableroLogico.validarEmpate()) {
                    socket.emit("empate",idUsuario);
                    guardarEmpate(idUsuario, contrincante.getString("idJugador"));
                    MensajeController.mensajeInformacion(idioma.getString("empate"));
                    regresarMenuPrincipal();
                }
            } else {
                System.out.println("Ya hay una ficha");
            }
    }
    
    
    private void regresarMenuPrincipal() {
        socket.emit("desconectar", idUsuario);
        apagarOns();
        socket.disconnect();
        main.desplegarMenuPrincipal(idioma, idUsuario);
    }
    
    private void colocarFicha(JFXButton boton, String colorFicha){
        String estilo = boton.getStyle();
        boton.setStyle("-fx-background-image: url('cincolinea/imagenes/"+colorFicha+".png');"
                + estilo + " -fx-background-position: center center; -fx-background-repeat: "
                + "stretch; -fx-background-size: 39px 39px 39px 39px;");
        
        
    }
    
    private Ficha crearFicha(String coordenadas){
        Ficha ficha = new Ficha();
        
        String[] divicion = coordenadas.split("-");
        
        ficha.setX(Integer.parseInt(divicion[0]));
        ficha.setY(Integer.parseInt(divicion[1]));
        ficha.setColorFicha(colorFicha);
        
        return ficha;
    }
    
    private void colocarFichaContrincante(Ficha ficha){
        
        
        if(tableroLogico.validarJugada(ficha.getX(), ficha.getY(), ficha.getColorFicha())){
            int posicion = Integer.parseInt("0" + ficha.getY() + ficha.getX());
            JFXButton boton = (JFXButton) tablero.getChildren().get(posicion);

            colocarFicha(boton, ficha.getColorFicha());
            
            tablero.setDisable(false);
        }
        
    }
    
    private void crearTablero(int tamaño){
        if (validarSiTiraPrimero()) {
            tablero.setDisable(true);
        }
        JFXButton boton;
        int i = 0;
        switch(tamaño){
            case 8:
                for(i = 9; i < 100; i += 10){
                    boton = (JFXButton) tablero.getChildren().get(i);
                    boton.setVisible(false);
                    
                    if(i == 79){
                        break;
                    }
                }
                
                for(i = 8; i < 100; i += 10){
                    boton = (JFXButton) tablero.getChildren().get(i);
                    boton.setVisible(false);
                    
                    if(i == 78){
                        break;
                    }
                }
                
                for(i = 90; i < 100; i++){
                    boton = (JFXButton) tablero.getChildren().get(i);
                    boton.setVisible(false);
                }
                
                for(i = 80; i < 90; i++){
                    boton = (JFXButton) tablero.getChildren().get(i);
                    boton.setVisible(false);
                }
                
                break;
            case 9:
                for(i = 9; i < 100; i += 10){
                    boton = (JFXButton) tablero.getChildren().get(i);
                    boton.setVisible(false);
                    
                    if(i == 89){
                        break;
                    }
                }
                
                for(i = 90; i < 100; i++){
                    boton = (JFXButton) tablero.getChildren().get(i);
                    boton.setVisible(false);
                }               
                
                break;
            case 10:
                
                break;
        }
    }
    
    private boolean validarSiTiraPrimero(){
        return colorFicha.equals("B");
    }
    
    private void guardarResultado(String ganador, String perdedor){
        ClienteRMI conexion;
        try {
            conexion = new ClienteRMI();
            conexion.guardarResultadosPardida(ganador, perdedor);
        } catch (RemoteException | NotBoundException ex) {
            //hacer algo
        }
    }
    
    private void guardarEmpate(String jugador1, String jugador2){
        ClienteRMI conexion;
        try {
            conexion = new ClienteRMI();
            conexion.guardarEmpate(jugador1, jugador2);
        } catch (RemoteException | NotBoundException ex) {
            //hacer algo
        }
    }
    
    private void guardarAbandonoPartida(String ganador, String desertor){
        ClienteRMI conexion;
        try {
            conexion = new ClienteRMI();
            conexion.guardarResultadosPardida(ganador, desertor);
            conexion.aplicarCastigo(desertor);
        } catch (RemoteException | NotBoundException ex) {
            //hacer algo
        }
    }

    private void apagarOns(){
        socket.off("empatar");
        socket.off("ganarPorAbandono");
        socket.off("perder");
        socket.off("jugadaRealizada");
    }
}
