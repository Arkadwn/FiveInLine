/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cincolinea;

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
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("FXMLDocument.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            
            stageInicio.setScene(scene);
            stageInicio.setTitle("5 in linea");
            
            FXMLDocumentController control = loader.getController();
            control.setMain(this);
            stageInicio.show();
            
            stageLocal = stageInicio;
        } catch (IOException ex) {
            System.out.println("Excepción tipo IO, mensaje: " + ex.getMessage());
        }
    }

    public void desplegarMenuPrincipal(ResourceBundle bundleElegido) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("FXMLMenuPrincipal.fxml"));
            Parent root = (Parent) loader.load();
            
            FXMLMenuPrincipalController control = loader.getController();
            control.setMain(this);
            
            Scene scene = new Scene(root);    
            
            control.initialize(null, bundleElegido);
            stageLocal.setScene(scene);
            stageLocal.setTitle("Menu principal");
            
        } catch (IOException ex) {
            System.out.println("Excepción tipo IO, mensaje: " + ex.getMessage());
        }
    }

    public void desplegarRanking(ResourceBundle bundleElegido) {
        try {
            
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("FXMLRanking.fxml"));
            Parent root = (Parent) loader.load();
            
            FXMLRankingController control = loader.getController();
            control.setMain(this);
            
            Scene scene = new Scene(root);    
            
            control.initialize(null, bundleElegido);
            stageLocal.setScene(scene);
            stageLocal.setTitle("Ranking");
            
        } catch (NullPointerException ex ) {
            System.out.println("Excepción tipo Null, mensaje: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Excepción tipo IO, mensaje: " + ex.getMessage());
        }
    }
    
    public void desplegarBuscaPartida(ResourceBundle bundleElegido){
        try{
            
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("FXMLBuscaPartida.fxml"));
            Parent root = (Parent) loader.load();
            
            FXMLBuscaPartidaController control = loader.getController();
            control.setMain(this);
            
            Scene scene = new Scene(root);
            
            control.initialize(null, bundleElegido);
            stageLocal.setScene(scene);
            stageLocal.setTitle("Buscando partida");
            
        }catch(IOException ex){
            System.out.println("Excepción de tipo IO, mensaje: " + ex.getMessage());
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }

    public void cerrarSesion(ResourceBundle bundleElegido) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("FXMLDocument.fxml"));
            Parent root = (Parent) loader.load();
            
            FXMLDocumentController control = loader.getController();
            control.setMain(this);
            
            Scene scene = new Scene(root);
            
            control.initialize(null, bundleElegido);
            stageLocal.setScene(scene);
            stageLocal.setTitle("5 en linea");
            
        } catch (IOException ex) {
            System.out.println("Excepción tipo IO, mensaje: " + ex.getMessage());
        }
    }

    

}
