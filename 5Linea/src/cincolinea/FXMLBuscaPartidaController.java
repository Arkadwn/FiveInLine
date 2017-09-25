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
public class FXMLBuscaPartidaController implements Initializable {

    @FXML
    private Label labelBuscandoP;
    @FXML
    private JFXButton btnCancelarBusqPartida;
    private ResourceBundle Bundle;
    private Main main;

    private void iniciarIdiomaComponentes() {
        btnCancelarBusqPartida.setText(Bundle.getString("btnCancelarBusqPartida"));
        labelBuscandoP.setText(Bundle.getString("labelBuscandoP")); 
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Bundle = rb;
        if (Bundle != null) {
            iniciarIdiomaComponentes();
        }

    }

    public void setMain(Main main) {
        this.main=main;
    }

    @FXML
    private void regresarMenuPrincipal(ActionEvent event) {
        main.desplegarMenuPrincipal(Bundle);
    }  
    
}
