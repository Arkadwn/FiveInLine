package cincolinea.controlador;

import java.util.Optional;
import javafx.scene.control.Alert;
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
        ventana.initStyle(StageStyle.UTILITY);
        ventana.showAndWait();
    }
    
    public static void mensajeInformacion(String mensaje){
        Alert ventana = new Alert(Alert.AlertType.INFORMATION);
        ventana.setTitle(null);
        ventana.setHeaderText(null);
        ventana.setContentText(mensaje);
        ventana.initStyle(StageStyle.UTILITY);
        ventana.showAndWait();
    }
    
    public static boolean mensajeDecisio(String mensaje){
        boolean validacion = true;
        
        Alert ventana = new Alert(Alert.AlertType.CONFIRMATION);
        ventana.setTitle(null);
        ventana.setHeaderText(null);
        ventana.setContentText(mensaje);
        ventana.initStyle(StageStyle.UTILITY);
        
        
        Optional<ButtonType> resultado = ventana.showAndWait();
        
        validacion = resultado.get() == ButtonType.OK;
        
        return validacion;
    }
}
