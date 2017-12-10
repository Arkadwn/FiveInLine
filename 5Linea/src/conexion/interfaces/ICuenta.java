package conexion.interfaces;

import cincolinea.modelo.Cuenta;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interfaz para realizar los servicos RMI para Cuentas.
 * 
 * @author Adrian Bustamante Zarate
 * @author Miguel Leonardo Jimenez
 */
public interface ICuenta extends Remote{
    /**
     * Valida la indentidad de un usuario que desea ingresar al sistema.
     *
     * @param usuario Identificador de la cuenta del usuario.
     * @param contrasena Contraseña de la cuenta del usuario.
     * @return Validación de la identidad del usuario.
     * @throws RemoteException Si la referencia no pudo ser creada.
     */
    public int autenticarCuenta(String usuario, String contrasena) throws RemoteException;
    
    /**
     * Registra un nueva cuenta.
     *
     * @param cuentaNueva Nueva cuenta de usuario.
     * @return Confirmación de la creación de la cuenta.
     * @throws RemoteException Si la referencia no pudo ser creada.
     */
    public boolean registrarCuenta(Cuenta cuentaNueva) throws RemoteException;
    
    /**
     * Saca el Identificador imagen de perfil de un usuario en especifico.
     * 
     * @param nombreUsuario Identificador del usuario deseado.
     * @return Identificador de la imagen del usuario.
     * @throws RemoteException Si la referencia no pudo ser creada.
     */
    public String sacarImagenDePerfil(String nombreUsuario) throws RemoteException;
    
    /**
     * Cambia el estado de la sesión de un usuario a activa.
     * 
     * @param nombreUsuario Identificador del usuario que a iniciado sesión.
     * @return Confirmación de la operación realizada.
     * @throws java.rmi.RemoteException Si la referencia no pudo ser creada.
     */
    public boolean activarEstadoSesion(String nombreUsuario) throws RemoteException;
    
    /**
     * Cambia el estado de la sesión de un usuario a inactiva.
     * 
     * @param nombreUsuario Identificador del usuario que a cerrado sesión.
     * @return Confirmación de la operación realizada.
     * @throws RemoteException Si la referencia no pudo ser creada.
     */
    public boolean desactivarEstadoSesion(String nombreUsuario) throws RemoteException;
}