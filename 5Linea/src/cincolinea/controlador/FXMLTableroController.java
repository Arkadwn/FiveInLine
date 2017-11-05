package cincolinea.controlador;

import cincolinea.Main;
import com.jfoenix.controls.JFXButton;
import io.socket.client.IO;
import io.socket.client.Socket;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

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

    private ResourceBundle idioma;
    private Main main;
    @FXML
    private JFXButton fxml00;
    @FXML
    private GridPane tablero;
    
    private String colorFicha = "N";
    private Socket socket;
    
    private void inicializarComponentes(){
        labelTextoTiempo.setText(idioma.getString("labelTextoTiempo"));
        labelContTiempo.setText(idioma.getString("labelContTiempo"));
        labelNombreContrincante.setText(idioma.getString("labelNombreContrincante"));
        labelNombreJugador.setText(idioma.getString("labelNombreJugador"));
        btnAbandonarP.setText(idioma.getString("btnAbandonarP"));
        labelInfoJugador.setText(idioma.getString("labelInfoJugador"));
        labelInfoContrincante.setText(idioma.getString("labelInfoContrincante"));
    }
    
    private void abandonarPartida(ActionEvent event) {
        main.desplegarMenuPrincipal(idioma);
    }

    public void setMain(Main main) {
        this.main = main;
    }

    @Override
    public void initialize(URL url, ResourceBundle idioma) {
        this.idioma = idioma;
        if (this.idioma != null) {
            inicializarComponentes();
        }
        /*
        try {
            socket = IO.socket("http://localhost:8000");
        } catch (URISyntaxException ex) {
            Logger.getLogger(FXMLTableroController.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
        
    }
    
    @FXML
    private void retornarCoordenadas(ActionEvent event){
        
        int[] coordenadas = new int[2];
        JFXButton boton = (JFXButton) event.getSource();
        //Quitar
        System.out.println(boton.getId());
        //idGrid.getChildren().remove(10, 20);
        colocarFicha(boton, colorFicha);
        
        JFXButton boton2;
        boton2 = (JFXButton)tablero.getChildren().get(99);
        System.out.println(boton2.getId());
    }
    
    @FXML
    private void regresarMenuPrincipal(ActionEvent event) {
        main.desplegarMenuPrincipal(idioma);
    }
    
    private void colocarFicha(JFXButton boton, String colorFicha){
        String estilo = boton.getStyle();
        boton.setStyle("-fx-background-image: url('cincolinea/imagenes/"+colorFicha+".png');"
                + estilo + " -fx-background-position: center center; -fx-background-repeat: "
                + "stretch; -fx-background-size: 39px 39px 39px 39px;");
        
        
    }

}
