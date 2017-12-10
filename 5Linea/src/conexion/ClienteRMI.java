package conexion;

import cincolinea.modelo.Cuenta;
import cincolinea.modelo.Ranking;
import cincolinea.modelo.utilerias.ConfiguracionIP;
import conexion.interfaces.ICuenta;
import conexion.interfaces.IRanking;
import conexion.interfaces.IVerificacionConexion;
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
 * Clase encargada de realizar la conexión con el servidor mediante RMI.
 *
 * @author Miguel Leonardo Jimenez Jimenez
 * @author Adrian Bustamante Zarate
 */
public class ClienteRMI {

    private Registry conexion;

    /**
     * Constructor que instancia el LocateRegistry.
     *
     * @throws RemoteException Si la referencia no pudo ser creada
     * @throws NotBoundException Si se intenta crear un lookup o unbind en el
     * registro un nombre que no tenga un enlace asociado.
     */
    public ClienteRMI() throws RemoteException, NotBoundException {
        String[] ip = ConfiguracionIP.getIP();
        conexion = LocateRegistry.getRegistry(ip[0] + "." + ip[1] + "." + ip[2] + "." + ip[3]);
    }

    /**
     * Constructo sobrecargado que instancia un LocateRegistry.
     *
     * @param ip Ip del servidor.
     * @throws RemoteException Si la referencia no pudo ser creada.
     * @throws NotBoundException Si se intenta crear un lookup o unbind en el
     * registro un nombre que no tenga un enlace asociado.
     */
    public ClienteRMI(String ip) throws RemoteException, NotBoundException {
        conexion = LocateRegistry.getRegistry(ip);
    }

    /**
     * Valida la indentidad de un usuario que desea ingresar al sistema.
     *
     * @param usuario Identificador de la cuenta del usuario.
     * @param contrasena Contraseña de la cuenta del usuario.
     * @return Validación de la identidad del usuario.
     */
    public int autenticarCuenta(String usuario, String contrasena) {
        int validacion = 0;

        String contrasenaEncriptada = "";
        try {
            contrasenaEncriptada = encriptarContrasena(contrasena);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ClienteRMI.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            ICuenta cuenta = (ICuenta) conexion.lookup("ServiciosCuenta");
            validacion = cuenta.autenticarCuenta(usuario, contrasenaEncriptada);

        } catch (NotBoundException | RemoteException ex) {
            Logger.getLogger(ClienteRMI.class.getName()).log(Level.SEVERE, null, ex);
        }

        return validacion;
    }

    /**
     * Registra un nueva cuenta.
     *
     * @param cuentaNueva Nueva cuenta de usuario.
     * @return Confirmación de la creación de la cuenta.
     * @throws RemoteException Si la referencia no pudo ser creada.
     * @throws NotBoundException Si se intenta crear un lookup o unbind en el
     * registro un nombre que no tenga un enlace asociado.
     */
    public boolean registrarUsuario(Cuenta cuentaNueva) throws RemoteException, NotBoundException {
        boolean validacion;
        String contrasenaEncriptada = "";

        try {
            contrasenaEncriptada = encriptarContrasena(cuentaNueva.getContraseña());
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ClienteRMI.class.getName()).log(Level.SEVERE, null, ex);
        }

        cuentaNueva.setContraseña(contrasenaEncriptada);

        ICuenta cuenta = (ICuenta) conexion.lookup("ServiciosCuenta");
        validacion = cuenta.registrarCuenta(cuentaNueva);

        return validacion;
    }

    /**
     * Encripta una cadena con SHA-256.
     *
     * @param contrasena Contraseña que se desea encriptar.
     * @return Cadena encriptada.
     * @throws NoSuchAlgorithmException Se lanza cuando se solicita un algoritmo
     * criptográfico particular, pero no está disponible en el entorno.
     */
    public static String encriptarContrasena(String contrasena) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] hash = messageDigest.digest(contrasena.getBytes());
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < hash.length; i++) {
            stringBuilder.append(Integer.toString((hash[i] & 0xff) + 0x100, 16).substring(1));
        }
        return stringBuilder.toString();
    }

    /**
     * Valida que haya una conexión con el servidor.
     *
     * @param banderaSeñal Boolean esperado.
     * @return Boolean contestación.
     * @throws RemoteException Si la referencia no pudo ser creada.
     * @throws NotBoundException Si se intenta crear un lookup o unbind en el
     * registro un nombre que no tenga un enlace asociado.
     */
    public boolean verficarConexion(boolean banderaSeñal) throws RemoteException, NotBoundException {
        boolean banderaRetorno;

        IVerificacionConexion verificacion = (IVerificacionConexion) conexion.lookup("ServiciosValidacion");
        banderaRetorno = verificacion.verficarConexion(banderaSeñal);
        return banderaRetorno;
    }

    /**
     * Guarda los resultados de una partida.
     * 
     * @param ganador Identificador del usuario ganador.
     * @param perdedor Identificador del usuario perdedor.
     * @return Confirmación de la operación realizada.
     */
    public boolean guardarResultadosPardida(String ganador, String perdedor) {
        boolean validacion = false;

        try {
            IRanking ranking = (IRanking) conexion.lookup("ServiciosRanking");
            validacion = ranking.guardarResultadosPartida(ganador, perdedor);
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(ClienteRMI.class.getName()).log(Level.SEVERE, null, ex);
        }

        return validacion;
    }

    /**
     * Guarda el empate de una pardida entre dos jugadores.
     * 
     * @param jugador1 Identifiacdor del usuario 1.
     * @param jugador2 Identificador del usuario 2.
     * @return Confirmación de la operación realizada.
     */
    public boolean guardarEmpate(String jugador1, String jugador2) {
        boolean validacion = false;

        try {
            IRanking ranking = (IRanking) conexion.lookup("ServiciosRanking");
            validacion = ranking.guardarEmpate(jugador1, jugador2);
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(ClienteRMI.class.getName()).log(Level.SEVERE, null, ex);
        }

        return validacion;
    }

    /**
     * Cambia el estado de la sesión de un usuario a activa.
     * 
     * @param nombreUsuario Identificador del usuario que a iniciado sesión.
     * @return Confirmación de la operación realizada.
     */
    public boolean activarEstadoSesion(String nombreUsuario) {
        boolean validacion = false;

        try {
            ICuenta cuenta = (ICuenta) conexion.lookup("ServiciosCuenta");
            validacion = cuenta.activarEstadoSesion(nombreUsuario);
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(ClienteRMI.class.getName()).log(Level.SEVERE, null, ex);
        }

        return validacion;
    }

    /**
     * Cambia el estado de la sesión de un usuario a inactiva.
     * 
     * @param nombreUsuario Identificador del usuario que a cerrado sesión.
     * @return Confirmación de la operación realizada.
     */
    public boolean desactivarEstadoSesion(String nombreUsuario) {
        boolean validacion = false;

        try {
            ICuenta cuenta = (ICuenta) conexion.lookup("ServiciosCuenta");
            validacion = cuenta.desactivarEstadoSesion(nombreUsuario);
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(ClienteRMI.class.getName()).log(Level.SEVERE, null, ex);
        }

        return validacion;
    }

    /**
     * Resta puntos a un usuario cuando abandona una partida.
     * 
     * @param idJugador Identificador del usuario que abandono partida.
     * @return Confirmación de la operación realizada.
     */
    public boolean aplicarCastigo(String idJugador) {
        boolean validacion = false;

        try {
            IRanking ranking = (IRanking) conexion.lookup("ServiciosRanking");
            validacion = ranking.aplicarCastigo(idJugador);
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(ClienteRMI.class.getName()).log(Level.SEVERE, null, ex);
        }

        return validacion;
    }

    /**
     * Saca los mejores 10 jugadores en base a sus puntajes.
     * 
     * @return Lista con los 10 mejores jugadores.
     */
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

    /**
     * Saca el Identificador imagen de perfil de un usuario en especifico.
     * 
     * @param nombreUsuario Identificador del usuario deseado.
     * @return Identificador de la imagen del usuario.
     * @throws RemoteException Si la referencia no pudo ser creada.
     * @throws NotBoundException Si se intenta crear un lookup o unbind en el
     * registro un nombre que no tenga un enlace asociado.
     */
    public String sacarImagenDePerfil(String nombreUsuario) throws RemoteException, NotBoundException {
        String imagen;

        ICuenta cuenta = (ICuenta) conexion.lookup("ServiciosCuenta");
        imagen = cuenta.sacarImagenDePerfil(nombreUsuario);

        return imagen;
    }
}
