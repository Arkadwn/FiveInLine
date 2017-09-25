package cincolinea;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class FXMLRankingController implements Initializable {
    private ResourceBundle Bundle;
    
    @FXML
    private JFXButton btnRegresar;
    @FXML
    private TableColumn<?, ?> columJugador;
    @FXML
    private TableColumn<?, ?> columGanadas;
    @FXML
    private TableColumn<?, ?> columPerdidas;
    private Main main;
    @FXML
    private TableView<?> tablaRanking;
    @FXML
    private Label labelRanking;
    
    private void iniciarIdiomaComponentes() {
        btnRegresar.setText(Bundle.getString("btnRegresar"));
        columJugador.setText(Bundle.getString("columJugador"));
        columGanadas.setText(Bundle.getString("columGanadas"));
        columPerdidas.setText(Bundle.getString("columPerdidas"));
        labelRanking.setText(Bundle.getString("labelRanking")); 
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
