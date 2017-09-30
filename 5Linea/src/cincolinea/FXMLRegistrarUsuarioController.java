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
public class FXMLRegistrarUsuarioController implements Initializable {

    @FXML
    private JFXButton btnRegistrar;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private Label labelNombreUsuario;
    @FXML
    private Label labelContrasena;
    @FXML
    private Label labelReContrasena;

    private ResourceBundle idioma;
    private Main main;

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
        btnRegistrar.setText(idioma.getString("btnRegistrar"));
        btnCancelar.setText(idioma.getString("btnCancelar"));
        labelNombreUsuario.setText(idioma.getString("labelUsuario"));
        labelContrasena.setText(idioma.getString("labelContrasena"));
        labelReContrasena.setText(idioma.getString("labelReContrasena"));
    }

    @FXML
    private void regresarMenuPrincipal(ActionEvent event) {
        main.desplegarMenuPrincipal(idioma);
    }
}
