package cincolinea;

import cincolinea.controlador.*;
import cincolinea.modelo.ConfiguracionPartida;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Clase encargada de desplegar la aplicación y cambiar de ventana
 * 
 * @author Adrian Bustamante Zarate
 * @author Miguel Leonardo Jimenez
 */
public class Main extends Application {

    private Stage stageLocal;

    /**
     * Optiene el Stage principal de la aplicación
     * 
     * @return Stage de la ventana primaria
     */
    public Stage getStageLocal() {
        return stageLocal;
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        desplegarConfiguracionIP(stage);
    }

    /**
     * Repinta la ventana principal con la vista de registrar usuario.
     * 
     * @param stageInicio Stage de la ventana principal.
     * @throws IOException Si la ventana no pudo cargar la vista deseada.
     */
    public void desplegarConfiguracionIP(Stage stageInicio) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/cincolinea/vista/FXMLRegistroIP.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);

            stageInicio.setScene(scene);

            FXMLRegistroIPController control = loader.getController();
            control.setMain(this);
            stageInicio.show();

            stageLocal = stageInicio;
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Repinta la ventana principal con la vista de inicio de sesión.
     * 
     * @param idiomaElegido Es el ResourceBundle con el idioma elegido.
     */
    public void desplegarInicioSesion(ResourceBundle idiomaElegido) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/cincolinea/vista/FXMLInicioSesion.fxml"), idiomaElegido);
            Parent root = (Parent) loader.load();
            
            FXMLInicioSesionController control = loader.getController();
            control.setMain(this);
            Scene scene = new Scene(root);

            stageLocal.setScene(scene);
            stageLocal.setTitle(idiomaElegido.getString("vtnaInicioSesion"));

        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Repinta la ventana principal con la vista del menu principal.
     * 
     * @param idiomaElegido Es el ResourceBundle con el idioma elegido.
     * @param idUsuario Es el identificador del usuario que ha iniciado sesión.
     */
    public void desplegarMenuPrincipal(ResourceBundle idiomaElegido, String idUsuario) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/cincolinea/vista/FXMLMenuPrincipal.fxml"),idiomaElegido);
            Parent root = (Parent) loader.load();
            
            FXMLMenuPrincipalController control = loader.getController();
            control.setMain(this);
            control.setIdUsuario(idUsuario);
            Scene scene = new Scene(root);

            stageLocal.setScene(scene);
            stageLocal.setTitle(idiomaElegido.getString("vtnaMenuPrincipal"));

        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Repinta la ventana principal con la vista del ranking.
     * 
     * @param idiomaElegido Es el ResourceBundle con el idioma elegido.
     * @param idUsuario Es el identificador del usuario que ha iniciado sesión.
     * @param imagenUsuario Es el identificador de la imagen del perfil del
     * usuario.
     */
    public void desplegarRanking(ResourceBundle idiomaElegido, String idUsuario, String imagenUsuario) {
        try {

            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/cincolinea/vista/FXMLRanking.fxml"),idiomaElegido);
            Parent root = (Parent) loader.load();

            FXMLRankingController control = loader.getController();
            control.setMain(this);
            control.setIdUsuario(idUsuario);
            control.setImagenDePerfil(imagenUsuario);
                    
            Scene scene = new Scene(root);
            
            
            stageLocal.setScene(scene);
            stageLocal.setTitle(idiomaElegido.getString("vtnaRanking"));

        } catch (NullPointerException | IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Repinta la ventana principal con la vista de buscar partida.
     * 
     * @param idiomaElegido Es el ResourceBundle con el idioma elegido.
     * @param idUsuario Es el identificador del usuario que ha iniciado sesión.
     * @param imagenJugador Es el identificador de la imagen del perfil del
     * usuario.
     */
    public void desplegarBuscaPartida(ResourceBundle idiomaElegido, String idUsuario, String imagenJugador) {
        try {

            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/cincolinea/vista/FXMLBuscaPartida.fxml"),idiomaElegido);
            Parent root = (Parent) loader.load();

            FXMLBuscaPartidaController control = loader.getController();
            control.setMain(this);
            control.setIdUsuario(idUsuario);
            control.setImagenDePerfil(imagenJugador);
            Scene scene = new Scene(root);

            stageLocal.setScene(scene);
            stageLocal.setTitle(idiomaElegido.getString("vtnaBuscarPartida"));

        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Main de la aplicación.
     * 
     * @param args Arreglo del main.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Repinta la ventana principal con la vista de registrar usuario.
     * 
     * @param idiomaElegido Es el ResourceBundle con el idioma elegido.
     */
    public void desplegarRegistrarUsuario(ResourceBundle idiomaElegido) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/cincolinea/vista/FXMLRegistrarUsuario.fxml"),idiomaElegido);
            Parent root = (Parent) loader.load();

            FXMLRegistrarUsuarioController control = loader.getController();
            control.setMain(this);

            Scene scene = new Scene(root);

            stageLocal.setScene(scene);
            stageLocal.setTitle(idiomaElegido.getString("vtnaRegistrarUsuario"));

        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Repinta la ventana principal con la vista de configurar partida.
     * 
     * @param idiomaElegido Es el ResourceBundle con el idioma elegido.
     * @param idUsuario Es el identificador del usuario que ha iniciado sesión.
     * @param imagenDePerfil Es el identificador de la imagen del perfil del
     * usuario.
     */
    public void deplegarConfigurarPartida(ResourceBundle idiomaElegido, String idUsuario, String imagenDePerfil) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/cincolinea/vista/FXMLConfigurarPartida.fxml"),idiomaElegido);
            Parent root = (Parent) loader.load();

            FXMLConfigurarPartidaController control = loader.getController();
            control.setMain(this);
            control.setIdUsuario(idUsuario);
            control.setImagenDePerfil(imagenDePerfil);

            Scene scene = new Scene(root);

            stageLocal.setScene(scene);
            stageLocal.setTitle(idiomaElegido.getString("vtnaConfigurarPartida"));

        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Repinta la ventana principal con la vista de iniciar juego.
     * 
     * @param idiomaElegido Es el ResourceBundle con el idioma elegido.
     * @param configuracion Es la configarión para la creacion del tablero.
     * @param idUsuario Es el identificador del usuario que ha iniciado sesión.
     */
    public void iniciarJuego(ResourceBundle idiomaElegido, ConfiguracionPartida configuracion, String idUsuario) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/cincolinea/vista/FXMLTablero.fxml"),idiomaElegido);
            Parent root = (Parent) loader.load();

            FXMLTableroController control = loader.getController();
            control.setMain(this);
            control.setConfiguracionJugador(configuracion, idUsuario);
            Scene scene = new Scene(root);

            stageLocal.setScene(scene);
            stageLocal.setTitle(idiomaElegido.getString("vtnaJugar"));

        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
