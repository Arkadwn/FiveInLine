package cincolinea;

import cincolinea.controlador.*;
import cincolinea.modelo.ConfiguracionPartida;
import java.io.IOException;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Adrian Bustamante Zarate
 * @author Miguel Leonardo Jimenez
 */
public class Main extends Application {

    private Stage stageLocal;
    
    @Override
    public void start(Stage stage) throws Exception {
        desplegarConfiguracionIP(stage);
    }

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
            System.out.println("Excepción tipo IO, mensaje: " + ex.getMessage());
        }
    }
    
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
            System.out.println("Excepción tipo IO, mensaje: " + ex.getMessage());
        }
    }

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
            System.out.println("Excepción tipo IO, mensaje: " + ex.getMessage());
        }
    }

    public void desplegarRanking(ResourceBundle idiomaElegido, String idUsuario) {
        try {

            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/cincolinea/vista/FXMLRanking.fxml"),idiomaElegido);
            Parent root = (Parent) loader.load();

            FXMLRankingController control = loader.getController();
            control.setMain(this);
            control.setIdUsuario(idUsuario);
                    
            Scene scene = new Scene(root);
            
            
            stageLocal.setScene(scene);
            stageLocal.setTitle(idiomaElegido.getString("vtnaRanking"));

        } catch (NullPointerException ex) {
            System.out.println("Excepción tipo Null, mensaje: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Excepción tipo IO, mensaje: " + ex.getMessage());
        }
    }

    public void desplegarBuscaPartida(ResourceBundle idiomaElegido, String idUsuario) {
        try {

            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/cincolinea/vista/FXMLBuscaPartida.fxml"),idiomaElegido);
            Parent root = (Parent) loader.load();

            FXMLBuscaPartidaController control = loader.getController();
            control.setMain(this);
            control.setIdUsuario(idUsuario);
            Scene scene = new Scene(root);

            stageLocal.setScene(scene);
            stageLocal.setTitle(idiomaElegido.getString("vtnaBuscarPartida"));

        } catch (IOException ex) {
            System.out.println("Excepción de tipo IO, mensaje: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

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
            System.out.println("Excepción tipo IO, mensaje: " + ex.getMessage());
        }
    }

    public void deplegarConfigurarPartida(ResourceBundle idiomaElegido, String idUsuario) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/cincolinea/vista/FXMLConfigurarPartida.fxml"),idiomaElegido);
            Parent root = (Parent) loader.load();

            FXMLConfigurarPartidaController control = loader.getController();
            control.setMain(this);
            control.setIdUsuario(idUsuario);

            Scene scene = new Scene(root);

            stageLocal.setScene(scene);
            stageLocal.setTitle(idiomaElegido.getString("vtnaConfigurarPartida"));

        } catch (IOException ex) {
            System.out.println("Excepción tipo IO, mensaje: " + ex.getMessage());
        }
    }
    
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
            System.out.println("Excepción tipo IO, mensaje: " + ex.getMessage());
        }
    }
}
