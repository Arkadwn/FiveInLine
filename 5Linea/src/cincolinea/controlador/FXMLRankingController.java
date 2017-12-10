package cincolinea.controlador;

import cincolinea.Main;
import cincolinea.modelo.Ranking;
import com.jfoenix.controls.JFXButton;
import conexion.ClienteRMI;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

/**
 * Controlador de la vista Ranking.
 *
 * @author Adrián Bustamante Zarate
 * @author Miguel Leonardo Jimenez Jimenez
 */
public class FXMLRankingController implements Initializable {
    private ResourceBundle idioma;
    @FXML
    private JFXButton btnRegresar;
    private Main main;
    @FXML
    private Label labelRanking;
    private String idUsuario;
    @FXML
    private Label labelNombreJugador;
    @FXML
    private Label labelPartidas;
    @FXML
    private ListView<String> listUsuarios;
    @FXML
    private ListView<String> listPartidasGanadas;
    @FXML
    private ListView<String> listPartidasPerdidas;
    @FXML
    private ListView<String> listPartidasEmpatadas;
    @FXML
    private ListView<String> listTotalPartidas;
    @FXML
    private ListView<String> listPuntos;
    @FXML
    private Label labelPartidasEmpatadas;
    @FXML
    private Label labelTotalPartidas;
    @FXML
    private Label labelPuntos;
    @FXML
    private JFXButton imgPerfil;
    private String imagenDePerfil;
    @FXML
    private Label labelPartidasGanadas;
    @FXML
    private Label labelPartidasPerdidas;

    /**
     * Setter de la variable imagenDePerfil.
     * 
     * @param imagenDePerfil Identificador de la imagen de perfil del usuario.
     */
    public void setImagenDePerfil(String imagenDePerfil) {
        this.imagenDePerfil = imagenDePerfil;
        cargarPerfil();
    }
    
    /**
     * Internacionaliza los componentes de la vista.
     */
    private void iniciarIdiomaComponentes() {
        btnRegresar.setText(idioma.getString("btnRegresar"));
        labelRanking.setText(idioma.getString("labelRanking"));
        labelTotalPartidas.setText(idioma.getString("labelTotalPartidas"));
        labelPuntos.setText(idioma.getString("labelPuntos"));
        labelPartidasPerdidas.setText(idioma.getString("labelPartidasPertidas"));
        labelPartidasGanadas.setText(idioma.getString("labelPartidasGanas"));
        labelPartidasEmpatadas.setText(idioma.getString("labelPartidasEmpatadas"));
        labelPartidas.setText(idioma.getString("labelPartidas"));
        labelNombreJugador.setText(idioma.getString("labelNombreJugador"));
    }

    @Override
    public void initialize(URL url, ResourceBundle idiomaElegido) {
        idioma = idiomaElegido;
        if (idioma != null) {
            iniciarIdiomaComponentes();
        }
        obtenerRankings();
    }

    /**
     * Setter de la variable idUsuario.
     * 
     * @param idUsuario Identificador del usuario que ha iniciado sesión.
     */
    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * Setter de la varible main.
     * 
     * @param main Ventana principal.
     */
    public void setMain(Main main) {
        this.main = main;
    }

    /**
     * Saca los mejores 10 jugadores.
     */
    public void obtenerRankings(){
        List <Ranking> listaRankigs = new ArrayList();
        ObservableList <String> usuarios = FXCollections.observableArrayList();
        ObservableList <String> partidasGanadas = FXCollections.observableArrayList();
        ObservableList <String> partidasPerdidas = FXCollections.observableArrayList();
        ObservableList <String> partidasEmpatadas = FXCollections.observableArrayList();
        ObservableList <String> puntos = FXCollections.observableArrayList();
        
        try {
            ClienteRMI conexion = new ClienteRMI();
            listaRankigs = conexion.sacar10MejoresJugadores();
        } catch (RemoteException | NotBoundException ex) {
            MensajeController.mensajeAdvertencia(idioma.getString("ErrorDeConexionIP"));
            Logger.getLogger(FXMLRankingController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for(Ranking ranking : listaRankigs){
            usuarios.add(ranking.getNombreUsuario());
            partidasGanadas.add(String.valueOf(ranking.getPartidasGanadas()));
            partidasPerdidas.add(String.valueOf(ranking.getPartidasPerdidas()));
            partidasEmpatadas.add(String.valueOf(ranking.getPartidasEmpatadas()));
            puntos.add(String.valueOf(ranking.getPuntos()));
        }
        rellenarTabla(usuarios, partidasGanadas, partidasPerdidas, partidasEmpatadas, puntos);
    }
    
    /**
     * Rellena las listas que representan las columnas de la tabla del ranking.
     * 
     * @param usuarios nombres de los 10 jugadores. 
     * @param partidasGanadas Partidas ganadas de los 10 mejores jugadores
     * respectivamente.
     * @param partidasPerdidas Partidas perdidas de los 10 mejores jugadores
     * respectivamente.
     * @param partidasEmpatadas Partidas empatadas de los 10 mejores jugadores
     * respectivamente.
     * @param puntos Puntos de los 10 mejores jugadores respectivamente.
     */
    public void rellenarTabla(ObservableList usuarios, ObservableList partidasGanadas, ObservableList partidasPerdidas, ObservableList partidasEmpatadas, ObservableList puntos){
        ObservableList<String> partidasTotales = FXCollections.observableArrayList();
        
        for(int i = 0; i < usuarios.size(); i++){
            int suma = Integer.parseInt((String) partidasGanadas.get(i)) + Integer.parseInt((String) partidasPerdidas.get(i)) + Integer.parseInt((String) partidasEmpatadas.get(i));
            partidasTotales.add(String.valueOf(suma));
        }
        
        listUsuarios.setItems(usuarios);
        listPartidasGanadas.setItems(partidasGanadas);
        listPartidasPerdidas.setItems(partidasPerdidas);
        listPartidasEmpatadas.setItems(partidasEmpatadas);
        listTotalPartidas.setItems(partidasTotales);
        listPuntos.setItems(puntos);
        
    }
    
    /**
     * Acción del botón btnRegresar.
     * 
     * @param evento El evento cachado por la presión del botón btnRegresar.
     */
    @FXML
    private void regresarMenuPrincipal(ActionEvent evento) {
        main.desplegarMenuPrincipal(idioma, idUsuario);
    }
    
    /**
     * Muestra la imagen de perfil del usuario.
     */
    private void cargarPerfil(){
        imgPerfil.setStyle("-fx-background-image: url('cincolinea/imagenes/" + imagenDePerfil + ".jpg" + "');"
                + "-fx-background-position: center center; -fx-background-repeat: stretch; -fx-background-size: 128px 90px 128px 90px;");
    }

}
