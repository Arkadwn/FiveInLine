package conexion.interfaces;

import cincolinea.modelo.Cuenta;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Adrian Bustamante Zarate
 * @author Miguel Leonardo Jimenez
 */
public interface ICuenta extends Remote{
    public int autenticarCuenta(String usuario, String contrasena) throws RemoteException;
    public boolean registrarCuenta(Cuenta datosUsuario) throws RemoteException;
    public String sacarImagenDePerfil(String nombreUsuario) throws RemoteException;
    public boolean activarEstadoSesion(String nombreUsuario) throws RemoteException;
    public boolean desactivarEstadoSesion(String nombreUsuario) throws RemoteException;
}