package conexion;

import cincolinea.modelo.Cuenta;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Leonardo
 */
public class ClienteRMITest {
    private ClienteRMI conexion;
    private final String IP = "192.168.43.75";
    
    public ClienteRMITest() throws RemoteException {
        try {
            conexion = new ClienteRMI(IP);
        } catch (NotBoundException ex) {
            Logger.getLogger(ClienteRMITest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Test of autenticarCuenta method, of class ClienteRMI.
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     */
    @Test
    public void testAutenticarCuentaNombreUsuarioQueHaIniciadoSesion() throws RemoteException, NotBoundException {
        String usuario = "Mauricio";
        String contrasena = "acdc";
        boolean resultadoEsperado = false;
        int resultado = conexion.autenticarCuenta(usuario, contrasena);
        assertEquals(resultadoEsperado, resultado == 2);
    }

    @Test
    public void testAutenticarCuenta() throws RemoteException, NotBoundException {
        String usuario = "Leo";
        String contrasena = "acdc619mljj";
        boolean resultadoEsperado = true;
        int resultado = conexion.autenticarCuenta(usuario, contrasena);
        assertEquals(resultadoEsperado, resultado == 1);
    }
    
    /**
     * Test of registrarUsuario method, of class ClienteRMI.
     */
    @Test
    public void probrarRegistroSobrepasoDeCaracteres() throws NotBoundException, RemoteException {
        Cuenta cuenta = new Cuenta();
        cuenta.setNombre("Mauricio");
        cuenta.setApellidos("Jimenez");
        cuenta.setContraseña("qwerty12345");
        cuenta.setEstadoSesion(0);
        cuenta.setNombreUsuario("Cristhianhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
        cuenta.setCorreo("acdc@gmail.com");
        cuenta.setImagen("img01");
        boolean expResult = false;
        boolean result = conexion.registrarUsuario(cuenta);
        assertEquals(expResult, result);
    }
    
    @Test
    public void probarActivacionDeCuenta() throws NotBoundException, RemoteException {
        boolean expResult = true;
        boolean result = conexion.activarEstadoSesion("AdrianB");
        assertEquals(expResult, result);
    }
    
    @Test
    public void probarDesactivacionDeCuenta() throws NotBoundException, RemoteException {
        boolean expResult = true;
        boolean result = conexion.activarEstadoSesion("AdrianB");
        assertEquals(expResult, result);
    }
    
    @Test
    public void probarActivacionDeCuentaNoExistente() throws NotBoundException, RemoteException {
        boolean expResult = false;
        boolean result = conexion.activarEstadoSesion("Beto");
        assertEquals(expResult, result);
    }
    
    @Test
    public void probarDesactivacionDeCuentaNoExistente() throws NotBoundException, RemoteException {
        boolean expResult = false;
        boolean result = conexion.activarEstadoSesion("Beto");
        assertEquals(expResult, result);
    }
    
    @Test
    public void probarConexionServidorCorrecta() throws NotBoundException, RemoteException {
        boolean expResult = true;
        boolean result = conexion.verficarConexion(true);
        assertEquals(expResult, result);
    }
    
    @Test
    public void probarConexionServidorIncorrecta() {
        boolean expResult = false;
        boolean result = false;
        ClienteRMI conexionFallida;
        try {
            conexionFallida = new ClienteRMI();
            result = conexionFallida.verficarConexion(true);
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(ClienteRMITest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertEquals(expResult, result);
    }
}
