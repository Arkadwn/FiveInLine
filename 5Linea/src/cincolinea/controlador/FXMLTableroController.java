package cincolinea.controlador;

import cincolinea.Main;
import cincolinea.modelo.ConfiguracionPartida;
import cincolinea.modelo.Ficha;
import cincolinea.modelo.Tablero;
import com.jfoenix.controls.JFXButton;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private String idContrincante;
    private boolean esCreador;
    
    private void inicializarComponentes(){
        labelTextoTiempo.setText(idioma.getString("labelTextoTiempo"));
        labelContTiempo.setText(idioma.getString("labelContTiempo"));
        labelNombreContrincante.setText(idioma.getString("labelNombreContrincante"));
        labelNombreJugador.setText(idioma.getString("labelNombreJugador"));
        btnAbandonarP.setText(idioma.getString("btnAbandonarP"));
        labelInfoJugador.setText(idioma.getString("labelInfoJugador"));
        labelInfoContrincante.setText(idioma.getString("labelInfoContrincante"));
    }
    
    private void abandonarPartida(ActionEvent event) {
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
        this.esCreador = configuracion.isEsCreador();
        tableroLogico = new Tablero(tamañoTablero);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle idioma) {
        this.idioma = idioma;
        if (this.idioma != null) {
            inicializarComponentes();
        }
        
        try {
            if(socket == null){
              
            crearConexionIO();
            }
        } catch (URISyntaxException ex) {
            Logger.getLogger(FXMLTableroController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    
    private void crearConexionIO() throws URISyntaxException{
        socket = IO.socket("http://localhost:8000");
        
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
        });
        socket.connect();
    }
    
    @FXML
    private void retornarCoordenadas(ActionEvent event){
        
        JFXButton boton = (JFXButton) event.getSource();
        
        Ficha ficha = crearFicha(boton.getId());
        
        if(tableroLogico.validarEmpate()){
            System.out.println("Empate");
        }else{
            if (tableroLogico.validarJugada(ficha.getX(), ficha.getY(), ficha.getColorFicha())) {
                colocarFicha(boton, colorFicha);

                JSONObject fichaIncriptada = new JSONObject(ficha);

                socket.emit("realizarJugada", fichaIncriptada);

                tablero.setDisable(true);
            } else {
                System.out.println("Ya hay un ficha en esa posicion");
            }

            if (tableroLogico.validarSiGano(ficha.getX(), ficha.getY(), ficha.getColorFicha())) {
                System.out.println("ganaste");
            }
        }
        
        System.out.println(boton.getId());
    }
    
    @FXML
    private void regresarMenuPrincipal(ActionEvent event) {
        socket.off("jugadaRealizada");
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
            System.out.println("0" + ficha.getX() + "-" + ficha.getY());
            int posicion = Integer.parseInt("0" + ficha.getY() + ficha.getX());
            System.out.println("Hijo: " + posicion);

            JFXButton boton = (JFXButton) tablero.getChildren().get(posicion);

            colocarFicha(boton, ficha.getColorFicha());
            
            tablero.setDisable(false);
        }
        
    }

}
