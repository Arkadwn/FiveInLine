package conexion.interfaces;

import cincolinea.modelo.Cuenta;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Miguel Leonardo Jimenez
 * @author Adrian bustamante Zarate
 */
public interface ICuenta extends Remote{
    public boolean autenticarCuenta(String usuario,String contrasena) throws RemoteException;
    public boolean registrarCuenta(Cuenta cuenta) throws RemoteException;
}
