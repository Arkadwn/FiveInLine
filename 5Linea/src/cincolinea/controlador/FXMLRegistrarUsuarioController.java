package cincolinea.controlador;

import cincolinea.Main;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import conexion.ClienteRMI;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

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
    private ClienteRMI conexion;
    @FXML
    private ImageView imagen;
    @FXML
    private JFXPasswordField tfReContrasena;
    @FXML
    private JFXPasswordField tfContrasena;
    @FXML
    private JFXTextField tfNombreUsuario;

    @Override
    public void initialize(URL url, ResourceBundle idioma) {
        this.idioma = idioma;
        if (this.idioma != null) {
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
        labelContrasena.setText(idioma.getString("labelContraseña"));
        labelReContrasena.setText(idioma.getString("labelReContrasena"));
    }

    @FXML
    private void regresarMenuPrincipal(ActionEvent evento) {
        main.desplegarMenuPrincipal(idioma, tfNombreUsuario.getText());//Quitar
        //main.desplegarInicioSesion(stageInicio); Crear metodo especial para puerta trasera
    }
    
    @FXML
    private void accionRegistrarUsuario(ActionEvent evento){
        //3
        if(tfNombreUsuario.getText().isEmpty() || tfContrasena.getText().isEmpty() || tfReContrasena.getText().isEmpty()){
            System.out.println("Hay campos vacios");
        }else{
            try {
                conexion = new ClienteRMI();
            } catch (RemoteException ex) {
                //QUITAR
                Logger.getLogger(FXMLRegistrarUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
            }
            //4
            if(tfContrasena.getText().equals(tfReContrasena.getText())){
                //5
                if (conexion.registrarUsuario(tfNombreUsuario.getText(), tfContrasena.getText())) {
                    System.out.println("Usuario guardado");
                } else {
                    System.out.println("No se guardo");
                }
            }else{
                System.out.println("La contraseña no conduerda");
            }
        }
        
    }
    
    @FXML
    private void restringirNumeroDeCaracteres(KeyEvent evento){
        if(tfNombreUsuario.getText().length() >= 20){
            evento.consume();
        }
    }
    
    @FXML   
    private void restringirEspacios(KeyEvent evento){
        char caracter = evento.getCharacter().charAt(0);
        
        if(caracter == ' '){
            evento.consume();
        }
    }
}
