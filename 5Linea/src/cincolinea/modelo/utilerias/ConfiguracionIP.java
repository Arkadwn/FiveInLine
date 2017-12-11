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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase para metos estaticos para el manejo de las ip's de los servidores RMI y
 * socket.io.
 *
 * @author Adrián Bustamante Zarate
 * @author Miguel Leonardo Jiménez Jiménez
 */
public class ConfiguracionIP {

    /**
     * Guarda en un archivo.properties las cuatro digitos de una ip.
     *
     * @param ip Parte 1 de la ip.
     * @param ip2 Parte 2 de la ip.
     * @param ip3 Parte 3 de la ip.
     * @param ip4 Parte 4 de la ip.
     */
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
            Logger.getLogger(ConfiguracionIP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Valida si la ip ingresada corresponde a la del servidor.
     *
     * @param ip Ip ingresada por el usuario.
     * @return Validación de la ip.
     */
    public static boolean verificarConfiguracionIP(String ip) {
        boolean banderaConexion = false;
        try {
            ClienteRMI conexion = new ClienteRMI(ip);
            banderaConexion = conexion.verficarConexion(true);
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(ConfiguracionIP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return banderaConexion;
    }

    /**
     * Devuleve la Ip contenida en el archivo.properties.
     *
     * @return Ip del servidor.
     */
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
                Logger.getLogger(ConfiguracionIP.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            for (int i = 0; i < ip.length; i++) {
                ip[i] = "X";
            }
        }

        return ip;
    }

    /**
     * Optiene la ruta del archivo.properties.
     *
     * @return ruta del archivo.properties.
     */
    private static String obtenerRutaProperties() {

        String ruta = null;
        try {
            CodeSource direccion = FXMLRegistroIPController.class.getProtectionDomain().getCodeSource();
            File fileJar = new File(direccion.getLocation().toURI().getPath());
            File fileDir = fileJar.getParentFile();
            File fileProperties = new File(fileDir.getAbsolutePath() + "/ConfiguracionIP.properties");

            ruta = fileProperties.getAbsolutePath();

        } catch (URISyntaxException ex) {
            Logger.getLogger(ConfiguracionIP.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ruta;
    }

    /**
     * Retorna true si se puede establecer una conexión al servidor Socket del
     * servidor Node.js
     *
     * @return variable booleana del resultado de conexión
     */
    public static boolean verficarConexionSocket() {
        String[] ip = getIP();
        boolean bandera = false;
        try {
            java.net.Socket socketPing = new java.net.Socket(ip[0] + "." + ip[1] + "." + ip[2] + "." + ip[3], 8000);
            bandera = socketPing.isConnected();
            socketPing.close();
        } catch (IOException ex) {
            Logger.getLogger(ConfiguracionIP.class.getName()).log(Level.SEVERE, null, ex);
            bandera = false;
        }
        return bandera;
    }
}
