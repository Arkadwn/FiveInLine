/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cincolinea.controlador;

import cincolinea.Main;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;

/**
 *
 * @author Adrian Bustamante Z
 */
public class FXMLDocumentController implements Initializable {
    
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
    
    @FXML
    private void cambiarIdioma(ActionEvent event) {
        if (estaEspañol) {
            cambiarComponentesIngles();
        } else {
            cambiarComponentesEspañol();
        }
    }

    @FXML
    private void desplegarRegistroUsuario(javafx.event.ActionEvent event){
        main.desplegarRegistrarUsuario(idioma);
    }
    
    @FXML
    private void ingresarMenuPrincipal(ActionEvent event) {
        main.desplegarMenuPrincipal(idioma);
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
    
    private void inicializarComponentesPorDefecto(ResourceBundle bundleElegido) {
        labelTitle.setText(bundleElegido.getString("labelTitle"));
        labelContraseña.setText(bundleElegido.getString("labelContraseña"));
        labelUsuario.setText(bundleElegido.getString("labelUsuario"));
        btnIngresar.setText(bundleElegido.getString("btnIngresar"));
        btnSalir.setText(bundleElegido.getString("btnSalir"));
        linkRegistro.setText(bundleElegido.getString("linkRegistro"));
        idioma = bundleElegido;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (rb != null) {
            inicializarComponentesPorDefecto(rb);
        } else {
            cambiarComponentesEspañol();
        }
        translateButton.setStyle("-fx-background-image: url('cincolinea/imagenes/language.png');"
                + "-fx-background-position: center center; -fx-background-repeat: stretch; -fx-background-size: 35px 35px 35px 35px;");
        
    }
    
}
