/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cincolinea.controlador;

import cincolinea.Main;
import com.jfoenix.controls.JFXButton;
import conexion.ClienteRMI;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

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
    private String imagenPerfil;
    @FXML
    private JFXButton btnAyuda;
    @FXML
    private JFXButton imgPerfil;
    @FXML
    private Label labelNombreUsuario;
    
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
        cargarPerfil();
    }

    @FXML
    private void desplegarRanking(javafx.event.ActionEvent event) {
        main.desplegarRanking(idioma,idUsuario,imagenPerfil);
    }
    
    @FXML
    private void desplegarBuscaPartida(javafx.event.ActionEvent event){
        main.desplegarBuscaPartida(idioma, idUsuario,imagenPerfil);
    }

    @FXML
    private void cerrarSesion(javafx.event.ActionEvent event) {
        try {
            ClienteRMI conexion = new ClienteRMI();
            conexion.activarEstadoSesion(idUsuario);
        } catch (RemoteException |NotBoundException ex) {
            System.out.println("Error: "+ex.getMessage());
        }
        main.desplegarInicioSesion(idioma);
    }
    
    @FXML
    private void crearPartida(javafx.event.ActionEvent event){
        main.deplegarConfigurarPartida(idioma, idUsuario, imagenPerfil);
    }
    
    private void cargarPerfil(){
        try {
            ClienteRMI conexion = new ClienteRMI();
            imagenPerfil = conexion.sacarImagenDePerfil(idUsuario);
            imgPerfil.setStyle("-fx-background-image: url('cincolinea/imagenes/" + imagenPerfil + ".jpg" + "');"
                + "-fx-background-position: center center; -fx-background-repeat: stretch; -fx-background-size: 129px 91px 129px 91px;");
            labelNombreUsuario.setText(idUsuario);
        } catch (RemoteException | NotBoundException ex) {
            System.out.println("Error: " + ex.getMessage());
            MensajeController.mensajeInformacion(idioma.getString("errorDeConexionIP"));
        }
        
    }
}
