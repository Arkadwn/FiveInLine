package cincolinea.controlador;

import cincolinea.Main;
import cincolinea.modelo.Cuenta;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import conexion.ClienteRMI;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;

/**
 * Controlador de la vista registrar usuario.
 *
 * @author Miguel Leonardo Jiménez Jiménez
 * @author Adrián Bustamante Zarate
 */
public class FXMLRegistrarUsuarioController implements Initializable {

    @FXML
    private JFXButton btnRegistrar;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private Label labelNombreUsuario;
    @FXML
    private Label labelContrasena;
    @FXML
    private Label labelReContrasena;
    private ResourceBundle idioma;
    private Main main;
    @FXML
    private JFXPasswordField tfReContrasena;
    @FXML
    private JFXPasswordField tfContrasena;
    @FXML
    private JFXTextField tfNombreUsuario;
    @FXML
    private Label labelCorreo;
    @FXML
    private JFXTextField tfCorreo;
    @FXML
    private JFXTextField tfApellidos;
    @FXML
    private Label labelApellidos;
    @FXML
    private Label labelNombre;
    @FXML
    private JFXTextField tfNombre;
    @FXML
    private Label labelErrorNombre;
    @FXML
    private Label labelErrorApellidos;
    @FXML
    private Label labelErrorNombreUsuario;
    @FXML
    private Label labelErrorContraseña;
    @FXML
    private Label labelErrorReContraseña;
    @FXML
    private Label labelErrorCorreo;
    private String imagenDeJugador;
    private int numeroImagen;
    @FXML
    private JFXButton imgPerfil;

    @Override
    public void initialize(URL url, ResourceBundle idioma) {
        this.idioma = idioma;
        if (this.idioma != null) {
            iniciarIdiomaComponentes();
            mostrarImagenPerfil();
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
     * Internacionaliza los componentes de la vista.
     */
    private void iniciarIdiomaComponentes() {
        btnRegistrar.setText(idioma.getString("btnRegistrar"));
        btnCancelar.setText(idioma.getString("btnCancelar"));
        labelNombreUsuario.setText(idioma.getString("labelUsuario"));
        labelContrasena.setText(idioma.getString("labelContraseña"));
        labelReContrasena.setText(idioma.getString("labelReContrasena"));
        labelApellidos.setText(idioma.getString("labelApellidos"));
        labelCorreo.setText(idioma.getString("labelCorreo"));
        labelNombre.setText(idioma.getString("labelNombre"));
    }

    /**
     * Acción del botón btnCancelar.
     * 
     * @param evento El evento cachado por la presión del botón btnCancelar.
     */
    @FXML
    private void regresarMenuPrincipal(ActionEvent evento) {
        main.desplegarInicioSesion(idioma);
    }

    /**
     * Acción del botón btnRegistrar.
     * 
     * @param evento El evento cachado por la presión del botón btnRegistrar.
     */
    @FXML
    private void accionRegistrarUsuario(ActionEvent evento) {
        Cuenta cuenta;
        if (validarCamposVacios()) {
            MensajeController.mensajeInformacion(idioma.getString("camposVacios"));
        } else {
            cuenta = new Cuenta(tfNombre.getText(), tfApellidos.getText(), tfNombreUsuario.getText(), tfCorreo.getText(), tfContrasena.getText(), imagenDeJugador);

            boolean[] validaciones = cuenta.validarCampos(cuenta, tfReContrasena.getText());

            if (validaciones[6]) {
                try {
                    ClienteRMI conexion = new ClienteRMI();
                    if (conexion.registrarUsuario(cuenta)) {
                        MensajeController.mensajeInformacion(idioma.getString("usuarioGuardado"));
                        main.desplegarMenuPrincipal(idioma, tfNombreUsuario.getText());
                    } else {
                        labelErrorNombreUsuario.setVisible(validaciones[2]);
                        MensajeController.mensajeAdvertencia("usuarioYaExistente");
                    }
                } catch (RemoteException | NotBoundException ex) {
                    Logger.getLogger(FXMLRegistrarUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
                    MensajeController.mensajeAdvertencia(idioma.getString("errorDeConexionIP"));
                }
            } else {
                mostrarErroresCampos(validaciones);
                MensajeController.mensajeAdvertencia(idioma.getString("camposInvalidos"));
            }
        }

    }

    /**
     * Valida si alguno de los campos se encuentra vacío.
     * 
     * @return validación de los campos validos.
     */
    private boolean validarCamposVacios() {
        return tfNombreUsuario.getText().isEmpty() || tfContrasena.getText().isEmpty() || tfReContrasena.getText().isEmpty() || tfApellidos.getText().isEmpty() || tfCorreo.getText().isEmpty() || tfNombre.getText().isEmpty();
    }

    /**
     * Restringe el número de caracteres a 50.
     * 
     * @param evento Evento de la presión de alguna tecla.
     */
    @FXML
    private void restringirNumeroDeCaracteres(KeyEvent evento) {
        if (tfNombreUsuario.getText().length() >= 50) {
            evento.consume();
        }
    }

    /**
     * Evita que se agregue un espacio en el campo.
     * 
     * @param evento Evento de la presión de alguna tecla.
     */
    @FXML
    private void restringirEspacios(KeyEvent evento) {
        char caracter = evento.getCharacter().charAt(0);

        if (caracter == ' ') {
            evento.consume();
        }
    }

    /**
     * Hace visibles los labels de los errores en los campos.
     * 
     * @param validaciones Arreglo con las validaciones de los campos.
     */
    private void mostrarErroresCampos(boolean[] validaciones) {
        labelErrorNombre.setVisible(!validaciones[0]);
        labelErrorApellidos.setVisible(!validaciones[1]);
        labelErrorNombreUsuario.setVisible(!validaciones[2]);
        labelErrorContraseña.setVisible(!validaciones[3]);
        labelErrorContraseña.setVisible(!validaciones[4]);
        labelErrorReContraseña.setVisible(!validaciones[3]);
        labelErrorCorreo.setVisible(!validaciones[5]);
    }

    /**
     * Genera un número entero aleatorio.
     * 
     * @return número entero.
     */
    private int generarNumeroImagenAleatorio() {
        Random aleatatio = new Random(System.currentTimeMillis());

        return  aleatatio.nextInt(15);
    }

    /**
     * Muestra una imagen para el perfil del usuario.
     */
    private void mostrarImagenPerfil() {
        numeroImagen = generarNumeroImagenAleatorio();
        imagenDeJugador = "img" + numeroImagen;
        imgPerfil.setStyle("-fx-background-image: url('cincolinea/imagenes/" + imagenDeJugador + ".jpg" + "');"
                + "-fx-background-position: center center; -fx-background-repeat: stretch; -fx-background-size: 116px 105px 116px 105px;");
    }

    /**
     * Acción del botón imgPerfil. Cambia la imagen del perfil.
     * 
     * @param evento El evento cachado por la presión del botón imgPerfil.
     */
    @FXML
    private void cambiarImagen(ActionEvent evento) {
        if (numeroImagen > 14)
            numeroImagen = 1;
        else 
            numeroImagen++;
        
        imagenDeJugador = "img" + numeroImagen;
        imgPerfil.setStyle("-fx-background-image: url('cincolinea/imagenes/" + imagenDeJugador + ".jpg" + "');"
                + "-fx-background-position: center center; -fx-background-repeat: stretch; -fx-background-size: 116px 105px 116px 105px;");
    }
}
