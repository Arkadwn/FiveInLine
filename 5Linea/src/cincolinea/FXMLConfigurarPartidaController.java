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

    private ResourceBundle idioma;
    private Main main;
    
    @FXML
    private Label labelTamaño;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idioma = rb;
        if (idioma != null) {
            iniciarIdiomaComponentes();
        }
    }

    public void setMain(Main main) {
        this.main = main;
    }

    private void iniciarIdiomaComponentes() {
        btnCrear.setText(idioma.getString("btnCrear"));
        btnCancelar.setText(idioma.getString("btnCancelar"));
        labelColorFichas.setText(idioma.getString("labelColor"));
        labelTamaño.setText(idioma.getString("labelTamaño"));
    }

    @FXML
    private void regresarMenuPrincipal(ActionEvent event) {
        main.desplegarMenuPrincipal(idioma);
    }
    
    @FXML
    private void iniciarJuego(ActionEvent event){
        main.iniciarJuego(idioma);
    }
}
