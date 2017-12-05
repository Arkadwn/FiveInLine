package conexion;

import cincolinea.modelo.Cuenta;
import cincolinea.modelo.Ranking;
import cincolineaservidor.persistencia.controladores.ControladorCuenta;
import cincolineaservidor.persistencia.controladores.ControladorRanking;
import conexion.interfaces.ICuenta;
import conexion.interfaces.IRanking;
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
public class ServidorRMI implements ICuenta, IVerificacionConexion, IRanking {

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
        
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CincoLineaServidorPU", null);
            ControladorCuenta controlador = new ControladorCuenta(entityManagerFactory);

            String contrasenaCuenta = controlador.verificarAutenticacion(usuario);

            if (contrasenaCuenta.equals(contrasena)) {
                autentico = true;
                System.out.println("Ha accedido el usuario: " + usuario);
            }
        
        return autentico; 
    }

    @Override
    public boolean registrarCuenta(Cuenta datosUsuario) throws RemoteException {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CincoLineaServidorPU", null);
        ControladorCuenta controller = new ControladorCuenta(entityManagerFactory);
        boolean registro = controller.registrarUsuario(datosUsuario);
        if (registro) {
            System.out.println("Se ha realizado un registro exitoso para el usuario: " + datosUsuario);
        }
        return registro;
    }

    @Override
    public boolean verficarConexion(boolean banderaSeñal) {
        return banderaSeñal;
    }

    @Override
    public boolean guardarResultadosPartida(String ganador, String perdedor) throws RemoteException {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CincoLineaServidorPU", null);
        ControladorRanking controlador = new ControladorRanking(entityManagerFactory);
        
        return controlador.guardarResultadosPartida(ganador, perdedor);
    }

    @Override
    public boolean guardarEmpate(String jugador1, String jugador2) throws RemoteException {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CincoLineaServidorPU", null);
        ControladorRanking controlador = new ControladorRanking(entityManagerFactory);
        
        return controlador.guardarEmpate(jugador1, jugador2);
    }

    @Override
    public boolean aplicarCastigo(String idJugador) throws RemoteException {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CincoLineaServidorPU", null);
        ControladorRanking controlador = new ControladorRanking(entityManagerFactory);
        
        return controlador.aplicarCastigo(idJugador);
    }

    @Override
    public List<Ranking> sacarMejores10() throws RemoteException {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CincoLineaServidorPU", null);
        ControladorRanking controlador = new ControladorRanking(entityManagerFactory);
        
        return sacarMejores10();
    }

}
