package cincolinea;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * FXML Controller class
 *
 * @author Miguel Leonardo Jiménez
 */
public class FXMLConfigurarPartidaController implements Initializable {

    @FXML
    private JFXButton btnCrear;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private Label labelColorFichas;

    private ResourceBundle Bundle;
    private Main main;
    
    @FXML
    private Label labelTamaño;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Bundle = rb;
        if (Bundle != null) {
            iniciarIdiomaComponentes();
        }
    }

    public void setMain(Main main) {
        this.main = main;
    }

    private void iniciarIdiomaComponentes() {
        btnCrear.setText(Bundle.getString("btnCrear"));
        btnCancelar.setText(Bundle.getString("btnCancelar"));
        labelColorFichas.setText(Bundle.getString("labelColor"));
        labelTamaño.setText(Bundle.getString("labelTamaño"));
    }

    @FXML
    private void regresarMenuPrincipal(ActionEvent event) {
        main.desplegarMenuPrincipal(Bundle);
    }
    
    @FXML
    private void iniciarJuego(ActionEvent event){
        main.iniciarJuego(Bundle);
    }
}
