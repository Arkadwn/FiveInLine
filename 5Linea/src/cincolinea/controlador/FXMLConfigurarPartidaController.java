package cincolinea.controlador;

import cincolinea.Main;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

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
    @FXML
    private ImageView imagen;
    @FXML
    private JFXComboBox<?> cbColorFichas;
    @FXML
    private JFXComboBox<?> cbTamano;
    private String idUsuario;

    @Override
    public void initialize(URL url, ResourceBundle idioma) {
        this.idioma = idioma;
        if (this.idioma != null) {
            iniciarIdiomaComponentes();
            rellenarCombobox();
        }
    }

    private void rellenarCombobox() {
        ObservableList tablero = cbTamano.getItems();
        tablero.addAll("10x10", "9x9", "8x8");
        cbTamano.setItems(tablero);
        ObservableList fichas = cbColorFichas.getItems();
        fichas.addAll(idioma.getString("fichaNegra"), idioma.getString("fichaBlanca"));
        cbColorFichas.setItems(fichas);

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
        main.desplegarMenuPrincipal(idioma, idUsuario);
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    @FXML
    private void iniciarJuego(ActionEvent event) {
        int tamaño;
        String tamañoElegido = cbTamano.getSelectionModel().getSelectedItem().toString();
        if (tamañoElegido.length() != 5) {
            tamaño = tamañoElegido.charAt(0);
        } else {
            tamaño = Integer.parseInt(tamañoElegido.substring(0, 2));
        }
        main.iniciarJuego(idioma, cbColorFichas.getSelectionModel().getSelectedItem().toString(), tamaño, idUsuario);
    }
}
