package conexion;

import cincolineaservidor.persistencia.controladores.ControladorCuenta;
import conexion.interfaces.ICuenta;
import conexion.interfaces.IVerificacionConexion;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Adrián Bustamante Zarate
 * @author Miguel Leonardo Jimenez
 */
public class ServidorRMI implements ICuenta, IVerificacionConexion {

    public boolean activarServicioAutenticacion() {
        boolean seActivo = true;
        try {
            ServidorRMI servidorRMICuenta = new ServidorRMI();
            ICuenta cuenta = (ICuenta) UnicastRemoteObject.exportObject(servidorRMICuenta, 0);
            
            ServidorRMI servidorRMIVerificacion = new ServidorRMI();
            IVerificacionConexion validacion = (IVerificacionConexion) UnicastRemoteObject.exportObject(servidorRMIVerificacion, 0);

            Registry registryCuenta = LocateRegistry.getRegistry();
            registryCuenta.bind("ServiciosCuenta", cuenta);
            Registry registryValidacion = LocateRegistry.getRegistry();
            registryValidacion.bind("ServiciosValidacion", validacion);

        } catch (RemoteException | AlreadyBoundException ex) {
            System.out.println("Error: " + ex.getMessage());
            seActivo = false;
            ex.printStackTrace();
        }
            return seActivo;
    }

    @Override
    public boolean autenticarCuenta(String usuario, String contrasena) {
        boolean autentico = false;
        try {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CincoLineaServidorPU", null);
            ControladorCuenta controller = new ControladorCuenta(entityManagerFactory);

            String contrasenaCuenta = controller.verificarAutenticacion(usuario);

            if (contrasenaCuenta.equals(contrasena)) {
                autentico = true;
                System.out.println("Ha accedido el usuario: " + usuario);
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        }
            return autentico; 
    }

    @Override
    public boolean registrarCuenta(List<String> datosUsuario) throws RemoteException {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CincoLineaServidorPU", null);
        ControladorCuenta controller = new ControladorCuenta(entityManagerFactory);
        boolean registro = controller.registrarUsuario(datosUsuario);
        if (registro) {
            System.out.println("Se ha realizado un registro exitoso para el usuario: " + datosUsuario.get(0));
        }
        return registro;
    }

    @Override
    public boolean verficarConexion(boolean banderaSeñal) {
        return banderaSeñal;
    }

}
