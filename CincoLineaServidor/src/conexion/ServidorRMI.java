package conexion;

import cincolineaservidor.persistencia.controladores.ControladorAutenticacion;
import conexion.interfaces.ICuenta;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Adrián Bustamante Zarate
 * @author Miguel Leonardo Jimenez
 */
public class ServidorRMI implements ICuenta {

    public boolean activarServicioAutenticacion() {
        boolean seActivo = true;
        try {
            ServidorRMI servidor = new ServidorRMI();
            ICuenta cuenta = (ICuenta) UnicastRemoteObject.exportObject(servidor, 0);

            Registry registry = LocateRegistry.getRegistry();
            registry.bind("ServiciosCuenta", cuenta);

        } catch (RemoteException | AlreadyBoundException ex) {
            System.out.println("Error: " + ex.getMessage());
            seActivo = false;
            ex.printStackTrace();
        }finally{
            return seActivo;
        }
    }

    @Override
    public boolean autenticarCuenta(String usuario, String contrasena) {
        boolean autentico = false;
        try {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CincoLineaServidorPU", null);
            ControladorAutenticacion controller = new ControladorAutenticacion(entityManagerFactory);

            String contrasenaCuenta = controller.verificarAutenticacion(usuario);

            if (contrasenaCuenta.equals(contrasena)) {
                autentico = true;
                System.out.println("Ha accedido el usuario: " + usuario);
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        }finally{
            return autentico;
        }
    }

    @Override
    public boolean registrarCuenta(String usuario, String contrasena, String idImagen) throws RemoteException {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CincoLineaServidorPU", null);
        ControladorAutenticacion controller = new ControladorAutenticacion(entityManagerFactory);
        boolean registro = controller.registrarUsuario(usuario, contrasena, idImagen);
        if (registro) {
            System.out.println("Se ha realizado un registro exitoso para el usuario: " + usuario);
        }
        return registro;
    }

}
