/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cincolinea;

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
    private ResourceBundle Bundle;
    private Main main;
    
    @FXML
    private void cambiarIdioma(ActionEvent event) {
        if (estaEspañol) {
            cambiarComponentesEnglish();
        } else {
            cambiarComponentesSpanish();
        }
    }

    @FXML
    private void desplegarRegistroUser(javafx.event.ActionEvent event){
        main.registrarUsuario(Bundle);
    }
    
    @FXML
    private void ingresarMenuPrincipal(ActionEvent event) {
        main.desplegarMenuPrincipal(Bundle);
    }
    
    public void setMain(Main main) {
        this.main = main;
    }

    private void cambiarComponentesSpanish() {
        Bundle = ResourceBundle.getBundle("cincolinea/resources/Bundle_es_MX");
        labelTitle.setText(Bundle.getString("labelTitle"));
        labelContraseña.setText(Bundle.getString("labelContraseña"));
        labelUsuario.setText(Bundle.getString("labelUsuario"));
        btnIngresar.setText(Bundle.getString("btnIngresar"));
        btnSalir.setText(Bundle.getString("btnSalir"));
        linkRegistro.setText(Bundle.getString("linkRegistro"));
        estaEspañol = true;
    }
    
    private void cambiarComponentesEnglish() {
        Bundle = ResourceBundle.getBundle("cincolinea/resources/Bundle_en_US");
        labelTitle.setText(Bundle.getString("labelTitle"));
        labelContraseña.setText(Bundle.getString("labelContraseña"));
        labelUsuario.setText(Bundle.getString("labelUsuario"));
        btnIngresar.setText(Bundle.getString("btnIngresar"));
        btnSalir.setText(Bundle.getString("btnSalir"));
        linkRegistro.setText(Bundle.getString("linkRegistro"));
        estaEspañol = false;
    }
    
    private void inicializarComponentesRB(ResourceBundle bundleElegido) {
        labelTitle.setText(bundleElegido.getString("labelTitle"));
        labelContraseña.setText(bundleElegido.getString("labelContraseña"));
        labelUsuario.setText(bundleElegido.getString("labelUsuario"));
        btnIngresar.setText(bundleElegido.getString("btnIngresar"));
        btnSalir.setText(bundleElegido.getString("btnSalir"));
        linkRegistro.setText(bundleElegido.getString("linkRegistro"));
        Bundle = bundleElegido;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (rb != null) {
            inicializarComponentesRB(rb);
        } else {
            cambiarComponentesSpanish();
        }
        translateButton.setStyle("-fx-background-image: url('cincolinea/imagenes/language.png');"
                + "-fx-background-position: center center; -fx-background-repeat: stretch; -fx-background-size: 35px 35px 35px 35px;");
        
    }
    
}
