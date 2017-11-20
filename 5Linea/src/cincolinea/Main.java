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
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class Main extends Application {

    private Stage stageLocal;
    
    @Override
    public void start(Stage stage) throws Exception {
        desplegarInicioSesion(stage);
    }

    public void desplegarInicioSesion(Stage stageInicio) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/cincolinea/vista/FXMLInicioSesion.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);

            stageInicio.setScene(scene);
            stageInicio.setTitle("5 in linea");

            FXMLInicioSesionController control = loader.getController();
            control.setMain(this);
            stageInicio.show();

            stageLocal = stageInicio;
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
            stageLocal.setTitle("Menu principal");

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
            stageLocal.setTitle("Ranking");

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
            stageLocal.setTitle("Buscando partida");

        } catch (IOException ex) {
            System.out.println("Excepción de tipo IO, mensaje: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void desplegarInicioSesion(ResourceBundle idiomaElegido) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/cincolinea/vista/FXMLInicioSesion.fxml"),idiomaElegido);
            Parent root = (Parent) loader.load();

            FXMLInicioSesionController control = loader.getController();
            control.setMain(this);

            Scene scene = new Scene(root);

            stageLocal.setScene(scene);
            stageLocal.setTitle("5 en linea");

        } catch (IOException ex) {
            System.out.println("Excepción tipo IO, mensaje: " + ex.getMessage());
        }
    }

    public void desplegarRegistrarUsuario(ResourceBundle idiomaElegido) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/cincolinea/vista/FXMLRegistrarUsuario.fxml"),idiomaElegido);
            Parent root = (Parent) loader.load();

            FXMLRegistrarUsuarioController control = loader.getController();
            control.setMain(this);

            Scene scene = new Scene(root);

            stageLocal.setScene(scene);
            stageLocal.setTitle("Registrar Usuario");

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
            stageLocal.setTitle("Configurar Partida");

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
            stageLocal.setTitle("Jugar");

        } catch (IOException ex) {
            System.out.println("Excepción tipo IO, mensaje: " + ex.getMessage());
        }
    }
}
