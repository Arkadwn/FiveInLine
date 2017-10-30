package conexion.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Miguel Leonardo Jimenez
 * @author Adrian bustamante Zarate
 */
public interface ICuenta extends Remote{
    public boolean autenticarCuenta(String usuario,String contrasena) throws RemoteException;
    public boolean registrarCuenta(String usuario, String contrasena, String idImagen) throws RemoteException;
}
