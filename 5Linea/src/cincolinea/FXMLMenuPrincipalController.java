/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cincolinea;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class FXMLMenuPrincipalController implements Initializable {

    @FXML
    private JFXButton btnBuscarPartida;
    @FXML
    private JFXButton btnMostrarRanking;
    
    private ResourceBundle Bundle;
    private Main main;
    @FXML
    private JFXButton btnCerrarSesion;
    
    public void setMain(Main main){
        this.main=main;
    }
    private void iniciarIdiomaComponentes(){
        btnBuscarPartida.setText(Bundle.getString("btnBuscarPartida"));
        btnMostrarRanking.setText(Bundle.getString("btnMostrarRanking"));
        btnCerrarSesion.setText(Bundle.getString("btnCerrarSesion"));   
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Bundle = rb;
        if(Bundle!=null)
            iniciarIdiomaComponentes();
        
    }    

    @FXML
    private void desplegarRanking(javafx.event.ActionEvent event) {
        main.desplegarRanking(Bundle);
    }
    
    @FXML
    private void desplegarBuscaPartida(javafx.event.ActionEvent event){
        main.desplegarBuscaPartida(Bundle);
    }

    @FXML
    private void cerrarSesion(javafx.event.ActionEvent event) {
        main.cerrarSesion(Bundle);
    }
}
