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
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Adrian Bustamante Z
 */
public class FXMLTableroController implements Initializable {

    @FXML
    private JFXButton btnAbandonarP;
    @FXML
    private Label labelTextoTiempo;
    @FXML
    private Label labelContTiempo;
    @FXML
    private Label labelNombreJugador;
    @FXML
    private Label labelNombreContrincante;
    @FXML
    private Label labelInfoContrincante;
    @FXML
    private Label labelInfoJugador;

    private ResourceBundle Bundle;
    private Main main;
    
    private void inicializarComponentes(){
        labelTextoTiempo.setText(Bundle.getString("labelTextoTiempo"));
        labelContTiempo.setText(Bundle.getString("labelContTiempo"));
        labelNombreContrincante.setText(Bundle.getString("labelNombreContrincante"));
        labelNombreJugador.setText(Bundle.getString("labelNombreJugador"));
        btnAbandonarP.setText(Bundle.getString("btnAbandonarP"));
        labelInfoJugador.setText(Bundle.getString("labelInfoJugador"));
        labelInfoContrincante.setText(Bundle.getString("labelInfoContrincante"));
    }
    
    @FXML
    private void abandonarPartida(ActionEvent event) {
        main.desplegarMenuPrincipal(Bundle);
    }

    public void setMain(Main main) {
        this.main = main;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Bundle = rb;
        if (Bundle != null) {
            inicializarComponentes();
        }
    }

}
