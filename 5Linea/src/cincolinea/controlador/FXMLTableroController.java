package cincolinea.controlador;

import cincolinea.Main;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
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
    private GridPane idGrid;
    
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
    public void initialize(URL url, ResourceBundle rb) {
        idioma = rb;
        if (idioma != null) {
            inicializarComponentes();
        }
    }
    
    @FXML
    private int[] retonarCoordenadas(ActionEvent event){
        int[] coordenadas = new int[2];
        JFXButton btn = (JFXButton) event.getSource();
        System.out.println(btn.getId());
        idGrid.getChildren().remove(10, 20);
        return coordenadas;
    }
    
    @FXML
    private void regresarMenuPrincipal(ActionEvent event) {
        main.desplegarMenuPrincipal(idioma);
    }

}
