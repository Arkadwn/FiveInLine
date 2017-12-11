package cincolinea.controlador;

import cincolinea.Main;
import com.jfoenix.controls.JFXButton;
import conexion.ClienteRMI;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.WindowEvent;

/**
 * Controlador de la vista Menu principal.
 *
 * @author Adrián Bustamante Zarate
 * @author Miguel Leonardo Jimenez
 */
public class FXMLMenuPrincipalController implements Initializable {

    @FXML
    private JFXButton btnBuscarPartida;
    @FXML
    private JFXButton btnMostrarRanking;

    private ResourceBundle idioma;
    private Main main;

    @FXML
    private JFXButton btnCerrarSesion;
    @FXML
    private JFXButton btnCrearPartida;
    private String idUsuario;
    private String imagenPerfil;
    @FXML
    private JFXButton imgPerfil;
    @FXML
    private Label labelNombreUsuario;

    /**
     * Setter de la variable main.
     *
     * @param main Ventana principal.
     */
    public void setMain(Main main) {
        this.main = main;
        main.getStageLocal().setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                try {
                    ClienteRMI conexion = new ClienteRMI();
                    conexion.activarEstadoSesion(idUsuario);
                    System.exit((0));
                } catch (RemoteException | NotBoundException ex) {
                    Logger.getLogger(FXMLMenuPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    /**
     * Internacionaliza los componentes de la vista.
     */
    private void iniciarIdiomaComponentes() {
        btnBuscarPartida.setText(idioma.getString("btnBuscarPartida"));
        btnMostrarRanking.setText(idioma.getString("btnMostrarRanking"));
        btnCerrarSesion.setText(idioma.getString("btnCerrarSesion"));
        btnCrearPartida.setText(idioma.getString("btnCrearPartida"));
    }

    @Override
    public void initialize(URL url, ResourceBundle idioma) {
        this.idioma = idioma;
        if (this.idioma != null) {
            iniciarIdiomaComponentes();
        }

    }

    /**
     * Setter de la variable idUsuario.
     *
     * @param idUsuario Identificador del usuario que ha iniciado sesión.
     */
    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
        cargarPerfil();
    }

    /**
     * Acción del botón btnMostrarRanking.
     *
     * @param evento El evento cachado por la presión del botón
     * btnMostrarRanking.
     */
    @FXML
    private void desplegarRanking(javafx.event.ActionEvent evento) {
        main.desplegarRanking(idioma, idUsuario, imagenPerfil);
    }

    /**
     * Acción del botón btnBuscarPartida.
     *
     * @param evento El evento cachado por la presión del botón
     * btnBuscarPartida.
     */
    @FXML
    private void desplegarBuscaPartida(javafx.event.ActionEvent evento) {
        main.desplegarBuscaPartida(idioma, idUsuario, imagenPerfil);
    }

    /**
     * Acción del botón btnCerrarSesion.
     *
     * @param evento El evento cachado por la presión del botón btnCerrarSesion.
     */
    @FXML
    private void cerrarSesion(javafx.event.ActionEvent evento) {
        try {
            ClienteRMI conexion = new ClienteRMI();
            conexion.activarEstadoSesion(idUsuario);
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(FXMLMenuPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            MensajeController.mensajeAdvertencia(idioma.getString("conexionRMIPerdida"));
        }
        main.desplegarInicioSesion(idioma);
    }

    /**
     * Acción del botón btnCrearPartida.
     *
     * @param evento El evento cachado por la presión del botón btnCrearPartida.
     */
    @FXML
    private void crearPartida(javafx.event.ActionEvent evento) {
        main.deplegarConfigurarPartida(idioma, idUsuario, imagenPerfil);
    }

    /**
     * Muestra el nombre y la imagen del usuario.
     */
    private void cargarPerfil() {
        try {
            ClienteRMI conexion = new ClienteRMI();
            imagenPerfil = conexion.sacarImagenDePerfil(idUsuario);
            imgPerfil.setStyle("-fx-background-image: url('cincolinea/imagenes/" + imagenPerfil + ".jpg" + "');"
                    + "-fx-background-position: center center; -fx-background-repeat: stretch; -fx-background-size: 129px 91px 129px 91px;");
            labelNombreUsuario.setText(idUsuario);
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(FXMLMenuPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            MensajeController.mensajeInformacion(idioma.getString("errorDeConexionIP"));
        }

    }
}
