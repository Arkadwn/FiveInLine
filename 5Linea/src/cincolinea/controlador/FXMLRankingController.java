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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

/**
 * FXML Controller class
 *
 * @author Adrián Bustamante Zarate
 * @author Miguel Leonardo Jimenez
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
    private Label labelPartidasGanas;
    @FXML
    private Label labelPartidasPertidas;
    @FXML
    private Label labelPartidasEmpatadas;
    @FXML
    private Label labelTotalPartidas;
    @FXML
    private Label labelPuntos;
    @FXML
    private JFXButton imgPerfil;
    private String imagenDePerfil;

    public void setImagenDePerfil(String imagenDePerfil) {
        this.imagenDePerfil = imagenDePerfil;
        cargarPerfil();
    }
    
    private void iniciarIdiomaComponentes() {
        btnRegresar.setText(idioma.getString("btnRegresar"));
        labelRanking.setText(idioma.getString("labelRanking")); 
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idioma = rb;
        if (idioma != null) {
            iniciarIdiomaComponentes();
        }
        obtenerRankings();
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setMain(Main main) {
        this.main=main;
    }

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
            System.out.println("Error: "+ex.getMessage());
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
    
    @FXML
    private void regresarMenuPrincipal(ActionEvent event) {
        main.desplegarMenuPrincipal(idioma, idUsuario);
    }
    
    private void cargarPerfil(){
        imgPerfil.setStyle("-fx-background-image: url('cincolinea/imagenes/" + imagenDePerfil + ".jpg" + "');"
                + "-fx-background-position: center center; -fx-background-repeat: stretch; -fx-background-size: 128px 90px 128px 90px;");
    }

}
