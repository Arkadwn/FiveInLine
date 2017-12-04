/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cincolinea.controlador;

import cincolinea.Main;
import cincolinea.modelo.utilerias.ConfiguracionIP;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import conexion.ClienteRMI;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Adrián Bustamante Zarate
 */
public class FXMLRegistroIPController implements Initializable {

    @FXML
    private AnchorPane pane;
    @FXML
    private JFXButton btnSalir;
    @FXML
    private JFXButton btnVerificarConexion;
    @FXML
    private Label labelIP;
    @FXML
    private Label labelTituloIP;
    @FXML
    private JFXButton translateButton;
    @FXML
    private JFXTextField txtIP;
    @FXML
    private JFXTextField txtIP1;
    @FXML
    private JFXTextField txtIP2;
    @FXML
    private JFXTextField txtIP3;

    private ClienteRMI conexion;
    private boolean estaEspañol;
    private ResourceBundle idioma;
    private Main main;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        translateButton.setStyle("-fx-background-image: url('cincolinea/imagenes/language.png');"
                + "-fx-background-position: center center; -fx-background-repeat: stretch; -fx-background-size: 35px 35px 35px 35px;");

        if (idioma != null) {
            inicializarComponentesPorDefecto(idioma);
        } else {
            cambiarComponentesEspañol();
        }

        String[] ip = ConfiguracionIP.getIP();
        System.out.println(ip[0]);
        txtIP.setText(ip[0]);
        txtIP1.setText(ip[1]);
        txtIP2.setText(ip[2]);
        txtIP3.setText(ip[3]);

    }

    private void inicializarComponentesPorDefecto(ResourceBundle idomaElegido) {

        labelTituloIP.setText(idomaElegido.getString("labelTituloIP"));
        btnSalir.setText(idomaElegido.getString("btnSalir"));
        btnVerificarConexion.setText(idomaElegido.getString("btnValidarConexion"));
        idioma = idomaElegido;
    }

    @FXML
    private void cambiarIdioma(ActionEvent event) {
        if (estaEspañol) {
            cambiarComponentesIngles();
        } else {
            cambiarComponentesEspañol();
        }
    }

    private void cambiarComponentesEspañol() {
        idioma = ResourceBundle.getBundle("cincolinea/resources/Bundle_es_MX");
        labelTituloIP.setText(idioma.getString("labelTituloIP"));
        btnSalir.setText(idioma.getString("btnSalir"));
        btnVerificarConexion.setText(idioma.getString("btnValidarConexion"));
        estaEspañol = true;
    }

    private void cambiarComponentesIngles() {
        idioma = ResourceBundle.getBundle("cincolinea/resources/Bundle_en_US");
        labelTituloIP.setText(idioma.getString("labelTituloIP"));
        btnSalir.setText(idioma.getString("btnSalir"));
        btnVerificarConexion.setText(idioma.getString("btnValidarConexion"));
        estaEspañol = false;
    }

    @FXML
    private void verificarConexion(ActionEvent event) {

        if (ConfiguracionIP.verificarConfiguracionIP(txtIP.getText() + "." + txtIP1.getText() + "." + txtIP2.getText() + "." + txtIP3.getText())) {
            MensajeController.mensajeInformacion(idioma.getString("conexionRealizada"));
            ConfiguracionIP.guardarConfiguracionIP(txtIP.getText(), txtIP1.getText(), txtIP2.getText(), txtIP3.getText());
            main.desplegarInicioSesion(idioma);
        } else {
            MensajeController.mensajeAdvertencia(idioma.getString("conexionFallida"));
        }

    }

    @FXML
    private void delimitadorCaracteresIP(KeyEvent event) {
        JFXTextField textField = (JFXTextField) event.getSource();
        if (textField.getText().length() > 2) {
            event.consume();
        }
        char caracter = event.getCharacter().charAt(0);
        if (caracter < 48 || caracter > 57) {
            event.consume();
        }
    }

    public void setMain(Main aThis) {
        main = aThis;
    }

}
