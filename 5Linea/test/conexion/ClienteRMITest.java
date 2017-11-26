package conexion;

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
     */
    @Test
    public void testAutenticarCuentaNombreUsuarioIncorrecto() throws RemoteException, NotBoundException {
        String usuario = "Mauricio";
        String contrasena = "acdc";
        conexion = new ClienteRMI();
        boolean resultadoEsperado = false;
        boolean resultado = conexion.autenticarCuenta(usuario, contrasena);
        assertEquals(resultadoEsperado, resultado);
    }

    @Test
    public void testAutenticarCuenta() throws RemoteException, NotBoundException {
        String usuario = "Leo";
        String contrasena = "acdc";
        conexion = new ClienteRMI();
        boolean resultadoEsperado = true;
        boolean resultado = conexion.autenticarCuenta(usuario, contrasena);
        assertEquals(resultadoEsperado, resultado);
    }
    
    /**
     * Test of registrarUsuario method, of class ClienteRMI.
     */
    @Test
    public void probrarRegistroSobrepasoDeCaracteres() {
        String nombreUsuario = "Cristhianhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh";
        String contrasena = "123456";
        String imagen = "img01";
        boolean expResult = false;
        boolean result = conexion.registrarUsuario(nombreUsuario, contrasena);
        assertEquals(expResult, result);
    }
    
}
