package cincolineaservidor;

import cincolinea.modelo.Cuenta;
import conexion.ServidorRMI;
import java.util.List;

public class CincoLineaServidor {
    List<Cuenta> cuentasActivas;
    List<String> renovacionesTiempo;
    public static void main(String[] args) {
        
        ServidorRMI servidorAutenticacion = new ServidorRMI();
        if(servidorAutenticacion.activarServicioAutenticacion()){
            System.out.println("El servidor RMI esta en linea");
        }else{
            System.out.println("El servidor RMI no se ha ejecutado correctamente");
        }
        
    }
    
    public void controlUsuarios(){
        do{
            
        }while(true);
    }

}
