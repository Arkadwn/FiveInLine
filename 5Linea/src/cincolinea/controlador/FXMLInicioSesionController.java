package cincolinea.controlador;

import cincolinea.Main;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import conexion.ClienteRMI;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Controlador de la vista de inicio de sesión
 * 
 * @author Adrian Bustamante Zarate
 * @author Miguel Leonardo Jiménez Jiménez
 */
public class FXMLInicioSesionController implements Initializable {

    @FXML
    private Label labelContraseña;
    @FXML
    private Label labelUsuario;
    @FXML
    private Label labelTitle;
    @FXML
    private JFXButton btnSalir;
    @FXML
    private JFXButton btnIngresar;
    @FXML
    private Hyperlink linkRegistro;
    @FXML
    private JFXButton btnIdioma;
    private boolean estaEspañol;
    private ResourceBundle idioma;
    private Main main;
    private ClienteRMI conexion;
    @FXML
    private JFXTextField txtNombreUsuario;
    @FXML
    private JFXPasswordField txtContrasena;

    /**
     * Accion del botón btnIdioma.
     * 
     * @param evento El evento cachado por la presión del botón btnIdioma.
     */
    @FXML
    private void cambiarIdioma(ActionEvent evento) {
        if (estaEspañol) {
            cambiarComponentesIngles();
            inicializarComponentes(idioma);
        } else {
            cambiarComponentesEspañol();
            inicializarComponentes(idioma);
        }
    }

    /**
     * Acción del enlace linkRegistro
     * 
     * @param evento El evento cachado por la presión del enlace linkRegistro.
     */
    @FXML
    private void desplegarRegistroUsuario(javafx.event.ActionEvent evento) {
        main.desplegarRegistrarUsuario(idioma);
    }

    /**
     * Acción del botón btnIngresar.
     * 
     * @param evento El evento cachado por la presión del botón btnIngresar.
     */
    @FXML
    private void ingresarMenuPrincipal(ActionEvent evento) {
        if (!txtContrasena.getText().isEmpty() || !txtNombreUsuario.getText().isEmpty()) {
            int valorSesion = conexion.autenticarCuenta(txtNombreUsuario.getText(), txtContrasena.getText());
            switch (valorSesion) {
                case 1:
                    MensajeController.mensajeInformacion(idioma.getString("inicioSesion"));
                    conexion.desactivarEstadoSesion(txtNombreUsuario.getText());
                    main.desplegarMenuPrincipal(idioma, txtNombreUsuario.getText());
                    break;
                case 0:
                    MensajeController.mensajeAdvertencia(idioma.getString("errorUsuario"));
                    break;
                default:
                    MensajeController.mensajeAdvertencia(idioma.getString("sesionActiva"));
                    break;
            }
        } else {
            MensajeController.mensajeAdvertencia(idioma.getString("camposVacios"));
        }
    }

    /**
     * Setter de la variable main.
     * 
     * @param main Ventana principal.
     */
    public void setMain(Main main) {
        this.main = main;
    }

    /**
     * Cambia el Bundle a español.
     */
    private void cambiarComponentesEspañol() {
        idioma = ResourceBundle.getBundle("cincolinea/resources/Bundle_es_MX");
        estaEspañol = true;
    }

    /**
     * Cambia el Bundle a ingles.
     */
    private void cambiarComponentesIngles() {
        idioma = ResourceBundle.getBundle("cincolinea/resources/Bundle_en_US");
        estaEspañol = false;
    }

    /**
     * Internacionaliza los componentes de la vista de acuerdo al idioma por
     * defecto.
     * 
     * @param idioma Es el ResourceBundle con el idioma elegido.
     */
    private void inicializarComponentes(ResourceBundle idioma) {
        labelTitle.setText(idioma.getString("labelTitle"));
        labelContraseña.setText(idioma.getString("labelContraseña"));
        labelUsuario.setText(idioma.getString("labelUsuario"));
        btnIngresar.setText(idioma.getString("btnIngresar"));
        btnSalir.setText(idioma.getString("btnSalir"));
        linkRegistro.setText(idioma.getString("linkRegistro"));
    }

    @Override
    public void initialize(URL url, ResourceBundle idioma) {
        try {
            conexion = new ClienteRMI();
        } catch (RemoteException | NotBoundException ex) {
            System.out.println("Error: " + ex.getMessage());
        }

        this.idioma = idioma;
        estaEspañol = !idioma.getBaseBundleName().equals("cincolinea/resources/Bundle_en_US");
        inicializarComponentes(idioma);

        btnIdioma.setStyle("-fx-background-image: url('cincolinea/imagenes/language.png');"
                + "-fx-background-position: center center; -fx-background-repeat: stretch; -fx-background-size: 35px 35px 35px 35px;");

    }

    /**
     * Acción el botón btnSalir.
     * 
     * @param evento El evento cachado por la presión del botón btnSalir.
     */
    @FXML
    private void cerrarSistema(ActionEvent evento) {
        Stage stageActual = main.getStageLocal();
        stageActual.close();
        System.exit(0);
    }

}
