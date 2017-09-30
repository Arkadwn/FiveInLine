package cincolinea.controlador;

import cincolinea.Main;
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
    private ResourceBundle idioma;
    private Main main;

    private void iniciarIdiomaComponentes() {
        btnCancelarBusqPartida.setText(idioma.getString("btnCancelarBusqPartida"));
        labelBuscandoP.setText(idioma.getString("labelBuscandoP")); 
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idioma = rb;
        if (idioma != null) {
            iniciarIdiomaComponentes();
        }

    }

    public void setMain(Main main) {
        this.main=main;
    }

    @FXML
    private void regresarMenuPrincipal(ActionEvent event) {
        main.desplegarMenuPrincipal(idioma);
    }  
    
}
