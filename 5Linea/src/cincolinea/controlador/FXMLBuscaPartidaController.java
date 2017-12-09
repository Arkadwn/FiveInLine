package cincolinea.controlador;

import cincolinea.Main;
import cincolinea.modelo.ConfiguracionPartida;
import cincolinea.modelo.utilerias.ConfiguracionIP;
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
import javafx.collections.FXCollections;
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
    private JFXButton btnCancelarBusqPartida;
    private ResourceBundle idioma;
    private Main main;
    private String idUsuario;
    private String idContrincante;
    private Socket socket;
    @FXML
    private Label labelJugadores;
    @FXML
    private JFXButton btnJugar;
    @FXML
    private JFXButton btnActualizar;
    @FXML
    private ImageView imgCargando;
    @FXML
    private Label labelBuscandoPartidas;
    @FXML
    private JFXComboBox<String> cbPartidas;
    private String imagenDePerfil;
    @FXML
    private JFXButton imgPerfil;

    public void setImagenDePerfil(String imagenDePerfil) {
        this.imagenDePerfil = imagenDePerfil;
        cargarImagenPerfil();
    }

    private void iniciarIdiomaComponentes() {
        btnCancelarBusqPartida.setText(idioma.getString("btnCancelarBusqPartida"));
        labelBuscandoPartidas.setText(idioma.getString("labelBuscandoP"));
        labelJugadores.setText(idioma.getString("labelJugadores"));
        btnActualizar.setText(idioma.getString("btnActualizar"));
        btnJugar.setText(idioma.getString("btnJugar"));
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
            }
        } catch (URISyntaxException ex) {
            System.out.println("Error URI: " + ex.getMessage());
        }

        cbPartidas.setVisible(false);

        obtenerPartidasDisponibles();
    }

    private void obtenerPartidasDisponibles() {
        try {
            imgCargando.setVisible(true);
            cbPartidas.setVisible(false);
            labelBuscandoPartidas.setVisible(true);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        socket.emit("peticionEnlacePartida");
    }

    private void crearConexionIO() throws URISyntaxException {

        String[] ipArray = ConfiguracionIP.getIP();
        String ip = ipArray[0] + "." + ipArray[1] + "." + ipArray[2] + "." + ipArray[3];
        socket = IO.socket("http://" + ip + ":8000");

        socket.on("jugadores", new Emitter.Listener() {

            @Override
            public void call(Object... os) {

                JSONArray arrayJugadasDisponibles = (JSONArray) os[0];
                if (arrayJugadasDisponibles.length() != 0) {
                    imgCargando.setVisible(false);
                    labelBuscandoPartidas.setVisible(false);
                }
                ObservableList<String> partidasDisponibles = FXCollections.observableArrayList();
                for (int i = 0; i < arrayJugadasDisponibles.length(); i++) {
                    JSONObject objetoRescatado = arrayJugadasDisponibles.getJSONObject(i);

                    cbPartidas.setVisible(true);
                    //Agregar al bundle
                    partidasDisponibles.add(idioma + objetoRescatado.get("idAnfitrion").toString());
                }
                Platform.runLater(() -> {
                    cbPartidas.setItems(partidasDisponibles);
                });

            }
        }).on("respuestaEmparejamiento", new Emitter.Listener() {
            @Override
            public void call(Object... os) {
                JSONObject configuracionPartida = (JSONObject) os[1];
                ConfiguracionPartida configuracion = new ConfiguracionPartida();
                configuracion.setSocket(socket);
                configuracion.setColorFicha((String) configuracionPartida.get("colorFicha"));
                configuracion.setTamaño((int) configuracionPartida.get("tamaño"));
                configuracion.setImagenPerfilInvitado(configuracionPartida.getString("imagenDePerfil"));
                configuracion.setImagenPerfil(imagenDePerfil);
                configuracion.setEsCreador(false);
                configuracion.setIdContrincante(os[0].toString());
                Platform.runLater(() -> {
                    main.iniciarJuego(idioma, configuracion, idUsuario);
                });

            }
        }).on("respuestaEmparejamientoNegativa", new Emitter.Listener() {
            @Override
            public void call(Object... os) {
                Platform.runLater(() -> {
                    MensajeController.mensajeInformacion(idioma.getString("mensajePartidaEliminada"));
                    obtenerPartidasDisponibles();
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
        idContrincante = cbPartidas.getValue();
        //Borrar
        socket.emit("emparejar", idContrincante.substring(11), idUsuario, imagenDePerfil);
    }

    @FXML
    private void regresarMenuPrincipal(ActionEvent event) {
        socket.off("jugadores");
        socket.off("respuestaEmparejamiento");
        socket.off("respuestaEmparejamientoNegativa");
        socket.disconnect();
        main.desplegarMenuPrincipal(idioma, idUsuario);
    }

    @FXML
    private void buscarPartidasNuevas(ActionEvent event) {
        cbPartidas.setVisible(false);
        imgCargando.setVisible(true);
        labelBuscandoPartidas.setVisible(true);

        obtenerPartidasDisponibles();
    }
    
    private void cargarImagenPerfil(){
        imgPerfil.setStyle("-fx-background-image: url('cincolinea/imagenes/" + imagenDePerfil + ".jpg" + "');"
                + "-fx-background-position: center center; -fx-background-repeat: stretch; -fx-background-size: 128px 90px 128px 90px;");
    }

}
