/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author Adrian Bustamante Z
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
    private JFXButton translateButton;

    private boolean estaEspañol;
    private ResourceBundle idioma;
    private Main main;
    private ClienteRMI conexion;
    @FXML
    private JFXTextField txtNombreUsuario;
    @FXML
    private JFXPasswordField txtContrasena;
    @FXML
    private AnchorPane pane;

    @FXML
    private void cambiarIdioma(ActionEvent event) {
        if (estaEspañol) {
            cambiarComponentesIngles();
        } else {
            cambiarComponentesEspañol();
        }
    }

    @FXML
    private void desplegarRegistroUsuario(javafx.event.ActionEvent event) {
        main.desplegarRegistrarUsuario(idioma);
    }

    @FXML
    private void ingresarMenuPrincipal(ActionEvent event) {
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

    public void setMain(Main main) {
        this.main = main;
    }

    private void cambiarComponentesEspañol() {
        idioma = ResourceBundle.getBundle("cincolinea/resources/Bundle_es_MX");
        labelTitle.setText(idioma.getString("labelTitle"));
        labelContraseña.setText(idioma.getString("labelContraseña"));
        labelUsuario.setText(idioma.getString("labelUsuario"));
        btnIngresar.setText(idioma.getString("btnIngresar"));
        btnSalir.setText(idioma.getString("btnSalir"));
        linkRegistro.setText(idioma.getString("linkRegistro"));
        estaEspañol = true;
    }

    private void cambiarComponentesIngles() {
        idioma = ResourceBundle.getBundle("cincolinea/resources/Bundle_en_US");
        labelTitle.setText(idioma.getString("labelTitle"));
        labelContraseña.setText(idioma.getString("labelContraseña"));
        labelUsuario.setText(idioma.getString("labelUsuario"));
        btnIngresar.setText(idioma.getString("btnIngresar"));
        btnSalir.setText(idioma.getString("btnSalir"));
        linkRegistro.setText(idioma.getString("linkRegistro"));
        estaEspañol = false;
    }

    private void inicializarComponentes(ResourceBundle idomaDefecto) {
        labelTitle.setText(idomaDefecto.getString("labelTitle"));
        labelContraseña.setText(idomaDefecto.getString("labelContraseña"));
        labelUsuario.setText(idomaDefecto.getString("labelUsuario"));
        btnIngresar.setText(idomaDefecto.getString("btnIngresar"));
        btnSalir.setText(idomaDefecto.getString("btnSalir"));
        linkRegistro.setText(idomaDefecto.getString("linkRegistro"));
    }

    @Override
    public void initialize(URL url, ResourceBundle idioma) {
        try {
            conexion = new ClienteRMI();
        } catch (RemoteException ex) {
            System.out.println("Error: " + ex.getMessage());
        } catch (NotBoundException ex) {
            System.out.println("Error: " + ex.getMessage());
        }

        this.idioma = idioma;
        estaEspañol = !idioma.getBaseBundleName().equals("cincolinea/resources/Bundle_en_US");
        inicializarComponentes(idioma);

        translateButton.setStyle("-fx-background-image: url('cincolinea/imagenes/language.png');"
                + "-fx-background-position: center center; -fx-background-repeat: stretch; -fx-background-size: 35px 35px 35px 35px;");

    }

    @FXML
    private void cerrarSistema(ActionEvent event) {
        Stage stageActual = main.getStageLocal();
        stageActual.close();
    }

}
