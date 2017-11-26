package cincolinea.controlador;

import cincolinea.Main;
import cincolinea.modelo.ConfiguracionPartida;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.application.Platform;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.json.JSONObject;
import cincolinea.modelo.utilerias.ConfiguracionIP;

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
    private JFXComboBox<String> cbColorFichas;
    @FXML
    private JFXComboBox<String> cbTamano;
    private String idUsuario;
    private Socket socket;
    private ConfiguracionPartida configuracion;

    @Override
    public void initialize(URL url, ResourceBundle idioma) {
        this.idioma = idioma;
        if (this.idioma != null) {
            iniciarIdiomaComponentes();
            crearConexion();
        }
    }

    @FXML
    private void rellenarComboboxTamaño() {
        ObservableList <String> tablero = FXCollections.observableArrayList("10x10","9x9", "8x8");
        cbTamano.setItems(tablero);
    }
    
    @FXML
    private void rellenarComboboxColores() {
        ObservableList <String> fichas = FXCollections.observableArrayList("Negras","Blancas");
        cbColorFichas.setItems(fichas);
    }
    
    private void crearConexion(){
        
        try {
            String[] ip = ConfiguracionIP.getIP();
            socket = IO.socket("http://"+ip[0]+"."+ip[1]+"."+ip[2]+"."+ip[3]+":8000");
            
            socket.on("conexionCreada", new Emitter.Listener(){
                @Override
                public void call(Object... os) {
                    
                }
                
            }).on("respuestaEmparejamiento", new Emitter.Listener() {
                @Override
                public void call(Object... os) {
                    configuracion.setIdContrincante((String) os[0]);
                    System.out.println(configuracion.getIdContrincante());
                    
                    Platform.runLater(()->{
                        main.iniciarJuego(idioma, configuracion,idUsuario);
                    });
                    
                }
            });
            socket.connect();
        } catch (URISyntaxException ex) {
            System.out.println("Conexion mal creada");
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
        main.desplegarMenuPrincipal(idioma, idUsuario);
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    @FXML
    private void iniciarJuego(ActionEvent event) {
        
        if(cbTamano.getItems().isEmpty() || cbColorFichas.getItems().isEmpty()){
            System.out.println("Campos no elegidos");
        }else{
            crearConfiguracion();
            
            JSONObject configuracionEncriptada = new JSONObject();
            
            if(configuracion.getColorFicha().equals("N")){
                configuracionEncriptada.put("colorFicha", "B");
            }else{
                configuracionEncriptada.put("colorFicha", "N");
            }
            
            configuracionEncriptada.put("tamaño", configuracion.getTamaño());
            
            socket.emit("peticionCreacionPartida", idUsuario, configuracionEncriptada);
            //
            
        }
        
    }
    
    private void crearConfiguracion(){
        configuracion = new ConfiguracionPartida();
        
        String valorComboBox = (String)cbTamano.getValue();

        if (valorComboBox.length() != 5) {
            configuracion.setTamaño(Integer.parseInt(valorComboBox.substring(0,1)));
        } else {
            int tamaño = Integer.parseInt(valorComboBox.substring(0, 2));
            configuracion.setTamaño(tamaño);
        }
        
        valorComboBox =(String)cbColorFichas.getValue();
        
        valorComboBox = valorComboBox.substring(0,1);
        
        configuracion.setColorFicha(valorComboBox);
        
        configuracion.setSocket(socket);
        configuracion.setEsCreador(true);
    }
}
