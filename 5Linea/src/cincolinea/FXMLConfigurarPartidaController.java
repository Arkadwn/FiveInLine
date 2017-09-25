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
public class FXMLConfigurarPartidaController implements Initializable {
	
	
	@FXML
	private JFXButton btnCrear;
	@FXML
	private JFXButton btnCancelar;
	@FXML
	private Label labelColorFichas;
	@FXML
	private Label labelTamano;
	
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
		btnCrear.setText(Bundle.getString("btnCrear"));
		btnCancelar.setText(Bundle.getString("btnCancelar"));
		labelColorFichas.setText(Bundle.getString("labelColor"));
		labelTamano.setText(Bundle.getString("labelTamano"));
	}
	
	@FXML
    private void regresarMenuPrincipal(ActionEvent event) {
        main.desplegarMenuPrincipal(Bundle);
    }
}