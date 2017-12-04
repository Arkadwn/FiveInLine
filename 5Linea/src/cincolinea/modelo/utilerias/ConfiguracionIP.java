/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cincolinea.modelo.utilerias;

import cincolinea.controlador.FXMLRegistroIPController;
import conexion.ClienteRMI;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.security.CodeSource;
import java.util.Properties;

/**
 *
 * @author Adrián Bustamante Zarate
 * @date 26/11/2017
 * @time 12:43:44 PM
 */
public class ConfiguracionIP {

    public static void guardarConfiguracionIP(String ip, String ip2, String ip3, String ip4) {
        Properties archivoProperties = new Properties();
        String ruta = obtenerRutaProperties();
        try {

            File fileProperties = new File(ruta);

            if (fileProperties.exists()) {

                archivoProperties.load(new FileReader(fileProperties));
                archivoProperties.setProperty("IP", ip);
                archivoProperties.setProperty("IP2", ip2);
                archivoProperties.setProperty("IP3", ip3);
                archivoProperties.setProperty("IP4", ip4);
                archivoProperties.store(new FileOutputStream(fileProperties.getAbsolutePath()), "Configuracion IP");

            } else {

                archivoProperties.setProperty("IP", ip);
                archivoProperties.setProperty("IP2", ip2);
                archivoProperties.setProperty("IP3", ip3);
                archivoProperties.setProperty("IP4", ip4);
                archivoProperties.store(new FileOutputStream(fileProperties.getAbsolutePath()), "Configuracion IP");

            }

        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public static boolean verificarConfiguracionIP(String ip) {
        boolean banderaConexion = false;
        try {
            ClienteRMI conexion = new ClienteRMI(ip);
            banderaConexion = conexion.verficarConexion(true);
        } catch (RemoteException | NotBoundException ex) {
            System.out.println("Error: "+ex.getMessage());
        }
        return banderaConexion;
    }

    public static String[] getIP() {
        Properties archivoProperties = new Properties();
        File rutaProperties = new File(obtenerRutaProperties());
        String[] ip = new String[4];

        if (rutaProperties.exists()) {
            try {
                archivoProperties.load(new FileReader(rutaProperties));
                ip[0] = archivoProperties.getProperty("IP");
                ip[1] = archivoProperties.getProperty("IP2");
                ip[2] = archivoProperties.getProperty("IP3");
                ip[3] = archivoProperties.getProperty("IP4");
            } catch (IOException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }else {
            for(int i = 0; i < ip.length; i++){
                ip[i]="X";
            }
        }
        
        return ip;
    }

    private static String obtenerRutaProperties() {

        String ruta = null;
        try {
            CodeSource direccion = FXMLRegistroIPController.class.getProtectionDomain().getCodeSource();
            File fileJar = new File(direccion.getLocation().toURI().getPath());
            File fileDir = fileJar.getParentFile();
            File fileProperties = new File(fileDir.getAbsolutePath() + "/ConfiguracionIP.properties");

            ruta = fileProperties.getAbsolutePath();

        } catch (URISyntaxException ex) {
            System.out.println("Error: " + ex.getMessage());
        }

        return ruta;
    }
}
