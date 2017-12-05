package cincolinea.controlador;

import cincolinea.Main;
import cincolinea.modelo.Cuenta;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import conexion.ClienteRMI;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    private JFXPasswordField tfReContrasena;
    @FXML
    private JFXPasswordField tfContrasena;
    @FXML
    private JFXTextField tfNombreUsuario;
    @FXML
    private Label labelCorreo;
    @FXML
    private JFXTextField tfCorreo;
    @FXML
    private JFXTextField tfApellidos;
    @FXML
    private Label labelApellidos;
    @FXML
    private Label labelNombre;
    @FXML
    private JFXTextField tfNombre;
    @FXML
    private Label labelErrorNombre;
    @FXML
    private Label labelErrorApellidos;
    @FXML
    private Label labelErrorNombreUsuario;
    @FXML
    private Label labelErrorContraseña;
    @FXML
    private Label labelErrorReContraseña;
    @FXML
    private Label labelErrorCorreo;

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
        labelApellidos.setText(idioma.getString("labelApellidos"));
        labelCorreo.setText(idioma.getString("labelCorreo"));
        labelNombre.setText(idioma.getString("labelNombre"));
    }

    @FXML
    private void regresarMenuPrincipal(ActionEvent evento) {
        main.desplegarMenuPrincipal(idioma, tfNombreUsuario.getText());//Quitar
        //main.desplegarInicioSesion(stageInicio); Crear metodo especial para puerta trasera
    }
    
    @FXML
    private void accionRegistrarUsuario(ActionEvent evento){
        boolean hayConexion = true;
        Cuenta cuenta;
        //3
        if(validarCamposVacios()){
            System.out.println("Hay campos vacios");
        }else{
            try {
                conexion = new ClienteRMI();
            } catch (RemoteException | NotBoundException ex) {
                //QUITAR
                System.out.println("Entre");
                hayConexion = false;
            }
            //4
            if(hayConexion){
                cuenta = new Cuenta(tfNombre.getText(), tfApellidos.getText(), tfNombreUsuario.getText(), tfCorreo.getText(), tfContrasena.getText(), "img"+generarNumeroImagenAleatorio());

                boolean[] validaciones = cuenta.validarCampos(cuenta, tfReContrasena.getText());

                if (validaciones[6]) {
                    //5
                    if (conexion.registrarUsuario(cuenta)) {
                        System.out.println("Usuario guardado");
                    } else {
                        System.out.println("Ese usuario ya se encuentra registrado");
                        labelErrorNombreUsuario.setVisible(validaciones[2]);
                    }
                } else {
                    mostrarErroresCampos(validaciones);
                }
            }else{
                System.out.println("No hay conexion al servidor o internet");
            }
        }
        
    }
    
    private boolean validarCamposVacios(){
        return tfNombreUsuario.getText().isEmpty() || tfContrasena.getText().isEmpty() || tfReContrasena.getText().isEmpty() || tfApellidos.getText().isEmpty() || tfCorreo.getText().isEmpty() || tfNombre.getText().isEmpty();
    }
    
    @FXML
    private void restringirNumeroDeCaracteres(KeyEvent evento){
        if(tfNombreUsuario.getText().length() >= 50){
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

    private void mostrarErroresCampos(boolean[] validaciones){
        labelErrorNombre.setVisible(!validaciones[0]);
        labelErrorApellidos.setVisible(!validaciones[1]);
        labelErrorNombreUsuario.setVisible(!validaciones[2]);
        labelErrorContraseña.setVisible(!validaciones[3]);
        labelErrorReContraseña.setVisible(!validaciones[3]);
        labelErrorCorreo.setVisible(!validaciones[5]);
    }
    
    private int generarNumeroImagenAleatorio(){
        Random aleatatio = new Random(System.currentTimeMillis());
        
        int numero = aleatatio.nextInt(10);
        
        return numero;
    }
}
