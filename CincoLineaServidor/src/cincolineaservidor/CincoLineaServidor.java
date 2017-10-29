package cincolineaservidor;

import conexion.ServidorRMI;
//import java.io.IOException;

public class CincoLineaServidor {

    public static void main(String[] args) {
//        try {
//            String[] cmd = {"node", "servidor.js"};
//            Runtime.getRuntime().exec(cmd);
//        } catch (IOException ioe) {
//            System.out.println(ioe);
//        }
        ServidorRMI servidorAutenticacion = new ServidorRMI();
        if(servidorAutenticacion.activarServicioAutenticacion()){
            System.out.println("El servidor RMI esta en linea");
        }else{
            System.out.println("El servidor RMI no se ha ejecutado correctamente");
        }
    }

}
