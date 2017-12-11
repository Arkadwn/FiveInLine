package cincolinea.controlador;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;

/**
 * Controlador de los mesajes para el usuario.
 * 
 * @author Miguel Leonardo Jimenez Jimenez
 * @author Adrián Bustamante Zarate
 */
public class MensajeController {
    
    /**
     * Muestra una ventana de advertencia.
     * 
     * @param mensaje Mensaje a mostrar.
     */
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
    
    /**
     * Muestra una ventana de información.
     * 
     * @param mensaje Mensaje a mostrar.
     */
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
    
    /**
     * Muestra una ventana de desición.
     * 
     * @param mensaje Mensaje a mostrar.
     * @param si Palabra 'Si' internacionalizada.
     * @param no Palabra 'No' internacionalizada.
     * @return La desición tomada por el usuario.
     */
    public static boolean mensajeDesicion(String mensaje, String si, String no){
        boolean validacion = false;
        
        Alert ventana = new Alert(Alert.AlertType.CONFIRMATION);
        ventana.setTitle(null);
        ventana.setHeaderText(null);
        ventana.setContentText(mensaje);
        ventana.initStyle(StageStyle.UNDECORATED);
        ButtonType botonSi = new ButtonType(si, ButtonData.YES);
        ButtonType botonNo = new ButtonType(no, ButtonData.NO);
        ventana.getButtonTypes().setAll(botonNo,botonSi);
        
        Optional<ButtonType> resultado = ventana.showAndWait();
        
        if(resultado.isPresent()){
            validacion = resultado.get() == botonSi;
        }
        
        
        return validacion;
    }
}
