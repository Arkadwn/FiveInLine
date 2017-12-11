package cincolinea.controlador;

import cincolinea.Main;
import cincolinea.modelo.ConfiguracionPartida;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.application.Platform;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.json.JSONObject;
import cincolinea.modelo.utilerias.ConfiguracionIP;
import conexion.ClienteRMI;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.stage.WindowEvent;

/**
 * Controlador de la vista configurar partida.
 *
 * @author Miguel Leonardo Jiménez Jiménez
 * @author Adrián Bustamante Zarate
 */
public class FXMLConfigurarPartidaController implements Initializable {

    @FXML
    private JFXButton btnCrear;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private Label labelColorFichas;
    private ResourceBundle idioma;
    private Main main;
    @FXML
    private Label labelTamaño;
    @FXML
    private JFXComboBox<String> cbColorFichas;
    @FXML
    private JFXComboBox<String> cbTamano;
    private String idUsuario;
    private Socket socket;
    private ConfiguracionPartida configuracion;
    @FXML
    private ImageView imgCargador;
    @FXML
    private JFXButton btnCancelarPartida;
    @FXML
    private Label labelEsperando;
    private String imagenDePerfil;
    @FXML
    private JFXButton imgPerfil;
    private boolean partidaCreada;
    private final String EVENTO_RESPUESTA_EMPAREJAMIENTO = "respuestaEmparejamiento";;

    @Override
    public void initialize(URL url, ResourceBundle idioma) {
        this.idioma = idioma;
        if (this.idioma != null) {
            iniciarIdiomaComponentes();
            crearConexionIO();
            partidaCreada = false;
        }
    }

    /**
     * Rellena el combo box cbTamano.
     */
    @FXML
    private void rellenarComboboxTamaño() {
        ObservableList<String> tablero = FXCollections.observableArrayList("10x10", "9x9", "8x8");
        cbTamano.setItems(tablero);
    }

    /**
     * Rellena el combo box cbColorFicha.
     */
    @FXML
    private void rellenarComboboxColores() {
        ObservableList<String> fichas = FXCollections.observableArrayList(idioma.getString("fichaNegra"), idioma.getString("fichaBlanca"));
        cbColorFichas.setItems(fichas);
    }

    /**
     * Crea la conexón con socket.io y activa los ons con su respectivo código.
     */
    private void crearConexionIO() {

        try {
            String[] ipPartes = ConfiguracionIP.getIP();
            String ip = ipPartes[0] + "." + ipPartes[1] + "." + ipPartes[2] + "." + ipPartes[3];
            socket = IO.socket("http://" + ip + ":8000");

            socket.on(EVENTO_RESPUESTA_EMPAREJAMIENTO, new Emitter.Listener() {
                @Override
                public void call(Object... os) {
                    configuracion.setIdContrincante((String) os[0]);
                    configuracion.setImagenPerfil(imagenDePerfil);
                    configuracion.setImagenPerfilInvitado((String) os[1]);
                    socket.off(EVENTO_RESPUESTA_EMPAREJAMIENTO);
                    
                    Platform.runLater(() -> {
                        main.iniciarJuego(idioma, configuracion, idUsuario);
                    });
                }
            });
            socket.connect();
        } catch (URISyntaxException ex) {
            Logger.getLogger(FXMLConfigurarPartidaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Setter de la variable main.
     * 
     * @param main Ventana pricipal.
     */
    public void setMain(Main main) {
        this.main = main;
        main.getStageLocal().setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                try {
                    ClienteRMI conexion = new ClienteRMI();
                    conexion.activarEstadoSesion(idUsuario);
                    if(partidaCreada){
                        socket.emit("cancelarPartida", idUsuario);
                    }
                    socket.disconnect();
                    System.exit((0));
                } catch (RemoteException | NotBoundException ex) {
                    Logger.getLogger(FXMLMenuPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    /**
     * Internacionaliza los componentes de la vista configurar partida.
     */
    private void iniciarIdiomaComponentes() {
        btnCrear.setText(idioma.getString("btnCrear"));
        btnCancelar.setText(idioma.getString("btnCancelar"));
        labelColorFichas.setText(idioma.getString("labelColor"));
        labelTamaño.setText(idioma.getString("labelTamaño"));
    }

    /**
     * Acción del botón btnAbandonarPartida.
     * 
     * @param evento El evento cachado por la presión del botón 
     * btnAbandonarPartida.
     */
    @FXML
    private void regresarMenuPrincipal(ActionEvent evento) {
        socket.off(EVENTO_RESPUESTA_EMPAREJAMIENTO);
        socket.disconnect();
        main.desplegarMenuPrincipal(idioma, idUsuario);
    }

    /**
     * Setter de la variable idUsuario.
     * 
     * @param idUsuario Identificador del usuario que ha iniciado sesión. 
     */
    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * Setter de la varialbe imagenDePerfil.
     * 
     * @param imagenDePerfil Identificador de la imagen de perfil del usuario.
     */
    public void setImagenDePerfil(String imagenDePerfil) {
        this.imagenDePerfil = imagenDePerfil;
        cargarImagenDePerfil();
    }

    /**
     * Acción del botón btnCrear.
     * 
     * @param evento El evento cachado por la presión del botón btnCrear.
     */
    @FXML
    private void iniciarJuego(ActionEvent evento) {

        if (cbTamano.getItems().isEmpty() || cbColorFichas.getItems().isEmpty()) {
            MensajeController.mensajeAdvertencia(idioma.getString("camposVacios"));
        } else {
            crearConfiguracion();

            JSONObject configuracionEncriptada = new JSONObject();
            String valorComboBox = (String) cbColorFichas.getValue();

            if (valorComboBox.equals("Whites") || valorComboBox.equals("Blancas")) {
                configuracionEncriptada.put("colorFicha", "N");
            } else {
                configuracionEncriptada.put("colorFicha", "B");
            }

            configuracionEncriptada.put("tamaño", configuracion.getTamaño());
            configuracionEncriptada.put("imagenDePerfil", imagenDePerfil);

            socket.emit("peticionCreacionPartida", idUsuario, configuracionEncriptada);
            mostrarElementosDeEspera();
            partidaCreada = true;
        }

    }

    /**
     * Repinta la vista cuando creas una partida.
     */
    private void mostrarElementosDeEspera() {
        btnCancelar.setVisible(false);
        btnCrear.setVisible(false);
        cbColorFichas.setVisible(false);
        cbTamano.setVisible(false);
        labelColorFichas.setVisible(false);
        labelTamaño.setVisible(false);
        labelEsperando.setVisible(true);
        labelEsperando.setText(idioma.getString("labelEsperando"));
        imgCargador.setVisible(true);
        btnCancelarPartida.setVisible(true);
        btnCancelarPartida.setText(idioma.getString("btnCancelar"));
    }

    /**
     * Repinta la vista a la forma original
     */
    private void ocultarElementosDeEspera() {
        btnCancelar.setVisible(true);
        btnCrear.setVisible(true);
        cbColorFichas.setVisible(true);
        cbTamano.setVisible(true);
        labelColorFichas.setVisible(false);
        labelTamaño.setVisible(false);
        labelEsperando.setVisible(false);
        imgCargador.setVisible(false);
        btnCancelarPartida.setVisible(false);
    }

    /**
     * Crea un objeto ConfiguracionPartida con los datos de la partida creada.
     */
    private void crearConfiguracion() {
        configuracion = new ConfiguracionPartida();

        String valorComboBox = (String) cbTamano.getValue();

        if (valorComboBox.length() != 5) {
            configuracion.setTamaño(Integer.parseInt(valorComboBox.substring(0, 1)));
        } else {
            int tamaño = Integer.parseInt(valorComboBox.substring(0, 2));
            configuracion.setTamaño(tamaño);
        }

        valorComboBox = (String) cbColorFichas.getValue();

        if (valorComboBox.equals("Whites") || valorComboBox.equals("Blancas")) {
            configuracion.setColorFicha("B");
        } else {
            configuracion.setColorFicha("N");
        }
        configuracion.setSocket(socket);
        configuracion.setEsCreador(true);
    }

    /**
     * Acción del botón btnCancelarPartida.
     * 
     * @param evento El evento cachado por la presión del botón 
     * btnCancelarPartida.
     */
    @FXML
    private void accionCancelarPartida(ActionEvent evento) {
        socket.emit("cancelarPartida", idUsuario);
        ocultarElementosDeEspera();
        partidaCreada = false;
    }

    /**
     * Muestra la imagen de perfil del usuario
     */
    private void cargarImagenDePerfil() {
        imgPerfil.setStyle("-fx-background-image: url('cincolinea/imagenes/" + imagenDePerfil + ".jpg" + "');"
                + "-fx-background-position: center center; -fx-background-repeat: stretch; -fx-background-size: 128px 90px 128px 90px;");
    }
}
