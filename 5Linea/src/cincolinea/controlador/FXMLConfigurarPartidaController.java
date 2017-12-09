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
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Miguel Leonardo Jiménez Jiménez
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

    @Override
    public void initialize(URL url, ResourceBundle idioma) {
        this.idioma = idioma;
        if (this.idioma != null) {
            iniciarIdiomaComponentes();
            activarEventos();
        }
    }

    @FXML
    private void rellenarComboboxTamaño() {
        ObservableList<String> tablero = FXCollections.observableArrayList("10x10", "9x9", "8x8");
        cbTamano.setItems(tablero);
    }

    @FXML
    private void rellenarComboboxColores() {
        ObservableList<String> fichas = FXCollections.observableArrayList(idioma.getString("fichaNegra"), idioma.getString("fichaBlanca"));
        cbColorFichas.setItems(fichas);
    }

    private void activarEventos() {

        try {
            String[] ipPartes = ConfiguracionIP.getIP();
            String ip = ipPartes[0] + "." + ipPartes[1] + "." + ipPartes[2] + "." + ipPartes[3];
            socket = IO.socket("http://" + ip + ":8000");

            socket.on("respuestaEmparejamiento", new Emitter.Listener() {
                @Override
                public void call(Object... os) {
                    configuracion.setIdContrincante((String) os[0]);
                    configuracion.setImagenPerfil(imagenDePerfil);
                    configuracion.setImagenPerfilInvitado((String) os[1]);
                    socket.off("respuestaEmparejamiento");

                    Platform.runLater(() -> {
                        main.iniciarJuego(idioma, configuracion, idUsuario);
                    });
                }
            });
            socket.connect();
        } catch (URISyntaxException ex) {
            System.out.println("Conexion mal creada");
        }
    }

    public void setMain(Main main) {
        this.main = main;
    }

    private void iniciarIdiomaComponentes() {
        btnCrear.setText(idioma.getString("btnCrear"));
        btnCancelar.setText(idioma.getString("btnCancelar"));
        labelColorFichas.setText(idioma.getString("labelColor"));
        labelTamaño.setText(idioma.getString("labelTamaño"));
    }

    @FXML
    private void regresarMenuPrincipal(ActionEvent evento) {
        socket.off("respuestaEmparejamiento");
        socket.disconnect();
        main.desplegarMenuPrincipal(idioma, idUsuario);
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setImagenDePerfil(String imagenDePerfil) {
        this.imagenDePerfil = imagenDePerfil;
        cargarImagenDePerfil();
    }

    @FXML
    private void iniciarJuego(ActionEvent evento) {

        if (cbTamano.getItems().isEmpty() || cbColorFichas.getItems().isEmpty()) {
            System.out.println("Campos no elegidos");
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
        }

    }

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

    @FXML
    private void accionCancelarPartida(ActionEvent evento) {
        socket.emit("cancelarPartida", idUsuario);
        ocultarElementosDeEspera();
    }

    private void cargarImagenDePerfil() {
        imgPerfil.setStyle("-fx-background-image: url('cincolinea/imagenes/" + imagenDePerfil + ".jpg" + "');"
                + "-fx-background-position: center center; -fx-background-repeat: stretch; -fx-background-size: 128px 90px 128px 90px;");
    }
}
