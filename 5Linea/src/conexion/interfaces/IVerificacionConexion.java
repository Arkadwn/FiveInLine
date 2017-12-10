package conexion.interfaces;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interfaz para validar la conexón con RMI.
 * 
 * @author Adrián Bustamante Zarate
 * @author Miguel Leonardo Jiménez Jiménez
 */
public interface IVerificacionConexion extends Remote{
    /**
     * Valida que haya una conexión con el servidor.
     *
     * @param banderaSeñal Boolean esperado.
     * @return Boolean contestación.
     * @throws RemoteException Si la referencia no pudo ser creada. 
     */
    public boolean verficarConexion(boolean banderaSeñal)throws RemoteException;
}