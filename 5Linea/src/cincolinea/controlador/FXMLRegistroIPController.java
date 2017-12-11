package cincolinea.controlador;

import cincolinea.Main;
import cincolinea.modelo.utilerias.ConfiguracionIP;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;

/**
 * Controlador de la vista RegistroIP
 *
 * @author Adrián Bustamante Zarate
 */
public class FXMLRegistroIPController implements Initializable {

    @FXML
    private JFXButton btnSalir;
    @FXML
    private JFXButton btnVerificarConexion;
    @FXML
    private Label labelTituloIP;
    @FXML
    private JFXButton btnIdioma;
    @FXML
    private JFXTextField txtIP;
    @FXML
    private JFXTextField txtIP1;
    @FXML
    private JFXTextField txtIP2;
    @FXML
    private JFXTextField txtIP3;
    private boolean estaEspañol;
    private ResourceBundle idioma;
    private Main main;

    @Override
    public void initialize(URL url, ResourceBundle idioma) {
        btnIdioma.setStyle("-fx-background-image: url('cincolinea/imagenes/language.png');"
                + "-fx-background-position: center center; -fx-background-repeat: stretch; -fx-background-size: 35px 35px 35px 35px;");

        if (idioma != null) {
            inicializarComponentesPorDefecto(idioma);
        } else {
            cambiarComponentesEspañol();
        }

        String[] ip = ConfiguracionIP.getIP();
        txtIP.setText(ip[0]);
        txtIP1.setText(ip[1]);
        txtIP2.setText(ip[2]);
        txtIP3.setText(ip[3]);

    }

    /**
     * Internacionaliza los componentes de la vista.
     * 
     * @param idomaElegido Es el ResourceBundle con el idioma elegido.
     */
    private void inicializarComponentesPorDefecto(ResourceBundle idomaElegido) {

        labelTituloIP.setText(idomaElegido.getString("labelTituloIP"));
        btnSalir.setText(idomaElegido.getString("btnSalir"));
        btnVerificarConexion.setText(idomaElegido.getString("btnValidarConexion"));
        idioma = idomaElegido;
    }

    /**
     * Acción del botón btnIdioma.
     * 
     * @param evento El evento cachado por la presión del botón btnIdioma.
     */
    @FXML
    private void cambiarIdioma(ActionEvent evento) {
        if (estaEspañol) {
            cambiarComponentesIngles();
        } else {
            cambiarComponentesEspañol();
        }
    }

    /**
     * Internacionaliza los componentes de la vista.
     */
    private void cambiarComponentesEspañol() {
        idioma = ResourceBundle.getBundle("cincolinea/resources/Bundle_es_MX");
        estaEspañol = true;
        inicializarComponentesPorDefecto(idioma);
    }

    /**
     * Internacionalizar los componentes de la vista.
     */
    private void cambiarComponentesIngles() {
        idioma = ResourceBundle.getBundle("cincolinea/resources/Bundle_en_US");
        estaEspañol = false;
        inicializarComponentesPorDefecto(idioma);
    }

    /**
     * Acción del botón btnVerificarConexion.
     * 
     * @param evento El evento cachado por la presión del botón 
     * btnVerificarConexion.
     */
    @FXML
    private void verificarConexion(ActionEvent evento) {

        if (ConfiguracionIP.verificarConfiguracionIP(txtIP.getText() + "." + txtIP1.getText() + "." + txtIP2.getText() + "." + txtIP3.getText())) {
            MensajeController.mensajeInformacion(idioma.getString("conexionRealizada"));
            ConfiguracionIP.guardarConfiguracionIP(txtIP.getText(), txtIP1.getText(), txtIP2.getText(), txtIP3.getText());
            main.desplegarInicioSesion(idioma);
        } else {
            MensajeController.mensajeAdvertencia(idioma.getString("conexionFallida"));
        }

    }

    /**
     * Limita el número de caracteres de los campos para la ip.
     * 
     * @param evento Evento de presionar una tecla.
     */
    @FXML
    private void delimitadorCaracteresIP(KeyEvent evento) {
        JFXTextField textField = (JFXTextField) evento.getSource();
        if (textField.getText().length() > 2) {
            evento.consume();
        }
        char caracter = evento.getCharacter().charAt(0);
        if (caracter < 48 || caracter > 57) {
            evento.consume();
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

    @FXML
    private void cerrarPantalla(ActionEvent event) {
        main.getStageLocal().close();
        System.exit((0));
    }
    
}
