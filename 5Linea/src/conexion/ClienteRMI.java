package conexion;

import cincolinea.modelo.Cuenta;
import cincolinea.modelo.Ranking;
import cincolinea.modelo.utilerias.ConfiguracionIP;
import conexion.interfaces.ICuenta;
import conexion.interfaces.IRanking;
import conexion.interfaces.IVerificacionConexion;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Miguel Leonardo Jimenez
 * @author Adrian Bustamante Zarate
 */
public class ClienteRMI {

    private Registry conexion;

    public ClienteRMI() throws RemoteException, NotBoundException {
        String[] ip = ConfiguracionIP.getIP();
        conexion = LocateRegistry.getRegistry(ip[0] + "." + ip[1] + "." + ip[2] + "." + ip[3]);
    }

    public ClienteRMI(String ip) throws RemoteException, NotBoundException {
        conexion = LocateRegistry.getRegistry(ip);
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

        try {
            //Nombre del servico que proporciona el servidor
            ICuenta cuenta = (ICuenta) conexion.lookup("ServiciosCuenta");
            validacion = cuenta.autenticarCuenta(usuario, contrasenaEncriptada);

        } catch (NotBoundException | RemoteException ex) {
            //Quitar
            ex.printStackTrace();
        }

        return validacion;
    }

    public boolean registrarUsuario(Cuenta cuentaNueva) throws RemoteException, AccessException, NotBoundException {
        boolean validacion = false;
        String contrasenaEncriptada = "";

        try {
            contrasenaEncriptada = encriptarContrasena(cuentaNueva.getContraseña());
        } catch (NoSuchAlgorithmException ex) {
            //quitar
            ex.printStackTrace();
        }

        cuentaNueva.setContraseña(contrasenaEncriptada);

        //Nombre del servico que proporciona el servidor
        ICuenta cuenta = (ICuenta) conexion.lookup("ServiciosCuenta");
        validacion = cuenta.registrarCuenta(cuentaNueva);

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

    public boolean verficarConexion(boolean banderaSeñal) throws RemoteException {
        boolean banderaRetorno = false;
        try {
            //Nombre del servico que proporciona el servidor
            IVerificacionConexion verificacion = (IVerificacionConexion) conexion.lookup("ServiciosValidacion");
            banderaRetorno = verificacion.verficarConexion(banderaSeñal);

        } catch (NotBoundException | RemoteException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return banderaRetorno;
    }

    public boolean guardarResultadosPardida(String ganador, String perdedor) {
        boolean validacion = false;

        try {
            IRanking ranking = (IRanking) conexion.lookup("ServiciosRanking");
            validacion = ranking.guardarResultadosPartida(ganador, perdedor);
        } catch (RemoteException | NotBoundException ex) {

        }

        return validacion;
    }

    public boolean guardarEmpate(String jugador1, String jugador2) {
        boolean validacion = false;

        try {
            IRanking ranking = (IRanking) conexion.lookup("ServiciosRanking");
            validacion = ranking.guardarEmpate(jugador1, jugador2);
        } catch (RemoteException | NotBoundException ex) {

        }

        return validacion;
    }

    public boolean aplicarCastigo(String idJugador) {
        boolean validacion = false;

        try {
            IRanking ranking = (IRanking) conexion.lookup("ServiciosRanking");
            validacion = ranking.aplicarCastigo(idJugador);
        } catch (RemoteException | NotBoundException ex) {

        }

        return validacion;
    }

    public List<Ranking> sacar10MejoresJugadores() {
        List<Ranking> mejores10Jugadores = new ArrayList();

        try {
            IRanking ranking = (IRanking) conexion.lookup("ServiciosRanking");
            mejores10Jugadores = ranking.sacarMejores10();
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(ClienteRMI.class.getName()).log(Level.SEVERE, null, ex);
        }

        return mejores10Jugadores;
    }
    
    public String sacarImagenDePerfil(String nombreUsuario) throws RemoteException, NotBoundException{
        String imagen = "";
        
        ICuenta cuenta = (ICuenta) conexion.lookup("ServiciosCuenta");
        imagen = cuenta.sacarImagenDePerfil(nombreUsuario);
        
        return imagen;
    }
}
