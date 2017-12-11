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
    public ClienteRMITest() throws RemoteException {
        try {
            conexion = new ClienteRMI();
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
    public void testAutenticarCuentaNombreUsuarioIncorrecto() throws RemoteException, NotBoundException {
        String usuario = "Mauricio";
        String contrasena = "acdc";
        conexion = new ClienteRMI();
        boolean resultadoEsperado = false;
        int resultado = conexion.autenticarCuenta(usuario, contrasena);
        assertEquals(resultadoEsperado, resultado == 0);
    }

    @Test
    public void testAutenticarCuenta() throws RemoteException, NotBoundException {
        String usuario = "Leo";
        String contrasena = "acdc619mljj";
        conexion = new ClienteRMI();
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
        cuenta.setNombreUsuario("Cristhianhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
        cuenta.setCorreo("acdc@gmail.com");
        cuenta.setImagen("img01");
        boolean expResult = false;
        boolean result = conexion.registrarUsuario(cuenta);
        assertEquals(expResult, result);
    }
    
}
