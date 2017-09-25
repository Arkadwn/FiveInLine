package cincolinea;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;

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
	
	private ResourceBundle Bundle;
    private Main main;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Bundle = rb;
		
		if(Bundle != null){
			iniciarIdiomaComponentes();
		}
    }
	
	public void setMain(Main main){
        this.main=main;
    }
	
	private void iniciarIdiomaComponentes(){
		btnRegistrar.setText(Bundle.getString("btnRegistrar"));
		btnCancelar.setText(Bundle.getString("btnCancelar"));
		labelNombreUsuario.setText(Bundle.getString("labelUsuario"));
		labelContrasena.setText(Bundle.getString("labelContraseña"));
		labelReContrasena.setText(Bundle.getString("labelReContrasena"));
	}
	
	@FXML
    private void regresarMenuPrincipal(ActionEvent event) {
        main.desplegarMenuPrincipal(Bundle);
    }
}