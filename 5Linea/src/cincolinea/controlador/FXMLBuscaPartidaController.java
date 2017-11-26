package cincolinea.controlador;

import cincolinea.Main;
import cincolinea.modelo.ConfiguracionPartida;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author Adrian Bustamante Z
 */
public class FXMLBuscaPartidaController implements Initializable {

    @FXML
    private Label labelBuscandoP;
    @FXML
    private JFXButton btnCancelarBusqPartida;
    private ResourceBundle idioma;
    private Main main;
    private String idUsuario;
    private String idContrincante;
    private Socket socket;
    @FXML
    private ImageView imagenCargando;
    @FXML
    private JFXComboBox<String> idComboPartidas;
    @FXML
    private Label labelJugadores;
    @FXML
    private JFXButton btnJugar;

    private void iniciarIdiomaComponentes() {
        btnCancelarBusqPartida.setText(idioma.getString("btnCancelarBusqPartida"));
        labelBuscandoP.setText(idioma.getString("labelBuscandoP"));
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public void initialize(URL url, ResourceBundle idioma) {

        this.idioma = idioma;
        if (this.idioma != null) {
            iniciarIdiomaComponentes();
        }

        try {
            if (socket == null) {
                crearConexionIO();
                //tableroLogico = new Tablero(tamañoTablero);
            }
        } catch (URISyntaxException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        try {
            //Thread.currentThread().wait(3000);
            obtenerPartidasDisponibles();
        } catch (Exception ex) {
            System.out.println("Error: "+ex.getMessage());
        }
    }
    private void obtenerPartidasDisponibles(){
        socket.emit("peticionEnlacePartida");
    }
    
    private void crearConexionIO() throws URISyntaxException {

        socket = IO.socket("http://localhost:8000");
        

        socket.on("jugadores", new Emitter.Listener() {
            @Override
            public void call(Object... os) {
                JSONArray arrayJugadasDisponibles = (JSONArray) os[0];
                ObservableList partidasDisponibles = null;
                for (int i = 0; i < arrayJugadasDisponibles.length(); i++) {
                    JSONObject objetoRescatado = arrayJugadasDisponibles.getJSONObject(i);
                    partidasDisponibles = idComboPartidas.getItems();
                    partidasDisponibles.add("Jugar con: " + objetoRescatado.get("idAnfitrion").toString());
                    
                }
                idComboPartidas.setItems(partidasDisponibles);
                //colocarFichaContrincante(ficha);
            }
        }).on("respuestaEmparejamiento", new Emitter.Listener() {
            @Override
            public void call(Object... os) {
                System.out.println("Entre al ON");
                System.out.println(os[0].toString());
                System.out.println(os[1].toString()); 
                JSONObject configuracionPartida = (JSONObject) os[1];
                ConfiguracionPartida configuracion = new ConfiguracionPartida();
                configuracion.setSocket(socket);
                configuracion.setColorFicha((String) configuracionPartida.get("colorFicha"));
                configuracion.setTamaño((int) configuracionPartida.get("tamaño"));
                configuracion.setEsCreador(false);
                configuracion.setIdContrincante(os[0].toString());
                Platform.runLater(()->{
                    main.iniciarJuego(idioma, configuracion, idUsuario);
                });
                
            }
        });
        
        socket.connect();
    }

    public void setMain(Main main) {
        this.main = main;
    }
    
    @FXML
    private void emparejar(ActionEvent event) {
        idContrincante = idComboPartidas.getValue();
        System.out.println(idContrincante.substring(11));
        System.out.println(idUsuario);
        socket.emit("emparejar", idContrincante.substring(11), idUsuario);
    }

    @FXML
    private void regresarMenuPrincipal(ActionEvent event) {
        main.desplegarMenuPrincipal(idioma, idUsuario);
    }

}
