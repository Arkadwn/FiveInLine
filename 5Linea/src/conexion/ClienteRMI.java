package conexion;

import conexion.interfaces.ICuenta;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 *
 * @author Miguel Leonardo Jimenez
 * @author Adrian Bustamante Zarate
 */
public class ClienteRMI {
    
    private ICuenta cuenta;
    private Registry conexion;
    
    public ClienteRMI() throws RemoteException{
        conexion = LocateRegistry.getRegistry("192.168.43.75");
    }
    
    public boolean autenticarCuenta(String usuario, String contrasena) {
        boolean validacion = false;
        
        String contrasenaEncriptada = "";
        try {
            contrasenaEncriptada = encriptarContrasena(contrasena);
        } catch (NoSuchAlgorithmException ex) {
            //quitar
            ex.printStackTrace();
        }
        
        try{
            //Nombre del servico que proporciona el servidor
            cuenta = (ICuenta) conexion.lookup("ServiciosCuenta");

            validacion = cuenta.autenticarCuenta(usuario, contrasenaEncriptada);

        }catch(NotBoundException | RemoteException ex){
            //Quitar
            ex.printStackTrace();
        }
        
        return validacion;
    }
    
    public boolean registrarUsuario(String nombreUsuario, String contrasena){
        boolean validacion = false;
        String contrasenaEncriptada = "";
        
        try {
            contrasenaEncriptada = encriptarContrasena(contrasena);
        } catch (NoSuchAlgorithmException ex) {
            //quitar
            ex.printStackTrace();
        }

        try {
            //Nombre del servico que proporciona el servidor
            cuenta = (ICuenta) conexion.lookup("ServiciosCuenta");

            validacion = cuenta.registrarCuenta(nombreUsuario, contrasenaEncriptada, "img" + generarNumeroImagenAleatorio());

        } catch (NotBoundException | RemoteException ex) {
            //Quitar
            ex.printStackTrace();
        }
        
        return validacion;
    }
    
    public static String encriptarContrasena(String contrasena) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] hash = messageDigest.digest(contrasena.getBytes());
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < hash.length; i++) {
            stringBuilder.append(Integer.toString((hash[i] & 0xff) + 0x100, 16).substring(1));
        }
        return stringBuilder.toString();
    }
    
    private int generarNumeroImagenAleatorio(){
        Random aleatatio = new Random(System.currentTimeMillis());
        
        int numero = aleatatio.nextInt(99);
        
        return numero;
    }
}
