package conexion.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Adrian Bustamante Zarate
 * @author Miguel Leonardo Jimenez
 */
public interface ICuenta extends Remote{
    public boolean autenticarCuenta(String usuario, String contrasena) throws RemoteException;
    public boolean registrarCuenta(List<String> datosUsuario) throws RemoteException;
}
