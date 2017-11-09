package conexion;

import java.rmi.RemoteException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Leonardo
 */
public class ClienteRMITest {
    private ClienteRMI conexion;
    public ClienteRMITest() throws RemoteException {
        conexion = new ClienteRMI();
    }
    /**
     * Test of autenticarCuenta method, of class ClienteRMI.
     */
    @Test
    public void testAutenticarCuentaNombreUsuarioIncorrecto() throws RemoteException {
        String usuario = "Mauricio";
        String contrasena = "acdc";
        conexion = new ClienteRMI();
        boolean resultadoEsperado = false;
        boolean resultado = conexion.autenticarCuenta(usuario, contrasena);
        assertEquals(resultadoEsperado, resultado);
    }

    @Test
    public void testAutenticarCuenta() throws RemoteException {
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
