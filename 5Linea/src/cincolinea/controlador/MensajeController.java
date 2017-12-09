package cincolinea.controlador;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 */
public class MensajeController {
    public static void mensajeAdvertencia(String mensaje){
        Alert ventana = new Alert(Alert.AlertType.WARNING);
        ventana.setTitle(null);
        ventana.setHeaderText(mensaje);
        ventana.setContentText(null);
        ventana.initStyle(StageStyle.UNDECORATED);
        ButtonType boton = new ButtonType("OK", ButtonData.OK_DONE);
        ventana.getButtonTypes().setAll(boton);
        ventana.showAndWait();
    }
    
    public static void mensajeInformacion(String mensaje){
        Alert ventana = new Alert(Alert.AlertType.INFORMATION);
        ventana.setTitle(null);
        ventana.setHeaderText(null);
        ventana.setContentText(mensaje);
        ventana.initStyle(StageStyle.UNDECORATED);
        ButtonType boton = new ButtonType("OK", ButtonData.OK_DONE);
        ventana.getButtonTypes().setAll(boton);
        ventana.showAndWait();
    }
    
    public static boolean mensajeDesicion(String mensaje, String si, String no){
        boolean validacion = true;
        
        Alert ventana = new Alert(Alert.AlertType.CONFIRMATION);
        ventana.setTitle(null);
        ventana.setHeaderText(null);
        ventana.setContentText(mensaje);
        ventana.initStyle(StageStyle.UNDECORATED);
        ButtonType botonSi = new ButtonType(si, ButtonData.YES);
        ButtonType botonNo = new ButtonType(no, ButtonData.NO);
        ventana.getButtonTypes().setAll(botonSi,botonNo);
        
        Optional<ButtonType> resultado = ventana.showAndWait();
        
        validacion = resultado.get() == ButtonType.OK;
        
        return validacion;
    }
}
