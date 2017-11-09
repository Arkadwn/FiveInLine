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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
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
    
    public void setMain(Main main){
        this.main=main;
    }
    private void iniciarIdiomaComponentes(){
        btnBuscarPartida.setText(idioma.getString("btnBuscarPartida"));
        btnMostrarRanking.setText(idioma.getString("btnMostrarRanking"));
        btnCerrarSesion.setText(idioma.getString("btnCerrarSesion"));
        btnCrearPartida.setText(idioma.getString("btnCrearPartida"));
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idioma = rb;
        if(idioma!=null)
            iniciarIdiomaComponentes();
        
    }    

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    @FXML
    private void desplegarRanking(javafx.event.ActionEvent event) {
        main.desplegarRanking(idioma,idUsuario);
    }
    
    @FXML
    private void desplegarBuscaPartida(javafx.event.ActionEvent event){
        main.desplegarBuscaPartida(idioma, idUsuario);
    }

    @FXML
    private void cerrarSesion(javafx.event.ActionEvent event) {
        main.cerrarSesion(idioma);
    }
    
    @FXML
    private void crearPartida(javafx.event.ActionEvent event){
        main.deplegarConfigurarPartida(idioma, idUsuario);
    }
    
}
