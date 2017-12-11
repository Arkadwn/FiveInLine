package cincolinea.modelo;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 */
public class CuentaTest {
    
    public CuentaTest() {
    }
    

    /**
     * Test of validarCampos method, of class Cuenta.
     */
    @Test
    public void validarCamposCorrectos() {
        Cuenta cuenta = new Cuenta();
        cuenta.setNombre("Mauricio");
        cuenta.setApellidos("Jimenez");
        cuenta.setContraseña("qwerty12345");
        cuenta.setEstadoSesion(0);
        cuenta.setNombreUsuario("Mauri");
        cuenta.setCorreo("acdc@gmail.com");
        cuenta.setImagen("img01");
        boolean[] expResult = {true,true,true,true,true,true,true};
        boolean[] result = cuenta.validarCampos(cuenta, "qwerty12345");
        assertArrayEquals(expResult, result);
    }

    @Test
    public void validarNombreIncorrecto() {
        Cuenta cuenta = new Cuenta();
        cuenta.setNombre("M");
        cuenta.setApellidos("Jimenez");
        cuenta.setContraseña("qwerty12345");
        cuenta.setEstadoSesion(0);
        cuenta.setNombreUsuario("Mauri");
        cuenta.setCorreo("acdc@gmail.com");
        cuenta.setImagen("img01");
        boolean expResult = false;
        boolean[] result = cuenta.validarCampos(cuenta, "qwerty12345");
        assertEquals(expResult, result[0]);
    }
    
    @Test
    public void validarNombreUsuarioIncorrecto() {
        Cuenta cuenta = new Cuenta();
        cuenta.setNombre("Mauricio");
        cuenta.setApellidos("Jimenez");
        cuenta.setContraseña("qwerty12345");
        cuenta.setEstadoSesion(0);
        cuenta.setNombreUsuario("");
        cuenta.setCorreo("acdc@gmail.com");
        cuenta.setImagen("img01");
        boolean expResult = false;
        boolean[] result = cuenta.validarCampos(cuenta, "qwerty12345");
        assertEquals(expResult, result[2]);
    }
    
    @Test
    public void validarApellidosIncorrecto() {
        Cuenta cuenta = new Cuenta();
        cuenta.setNombre("Mauricio");
        cuenta.setApellidos("");
        cuenta.setContraseña("qwerty12345");
        cuenta.setEstadoSesion(0);
        cuenta.setNombreUsuario("Adrian");
        cuenta.setCorreo("acdc@gmail.com");
        cuenta.setImagen("img01");
        boolean expResult = false;
        boolean[] result = cuenta.validarCampos(cuenta, "qwerty12345");
        assertEquals(expResult, result[1]);
    }
    
    @Test
    public void validarContraseñaCorta() {
        Cuenta cuenta = new Cuenta();
        cuenta.setNombre("Mauricio");
        cuenta.setApellidos("Jimenez");
        cuenta.setContraseña("12345");
        cuenta.setEstadoSesion(0);
        cuenta.setNombreUsuario("");
        cuenta.setCorreo("acdc@gmail.com");
        cuenta.setImagen("img01");
        boolean expResult = false;
        boolean[] result = cuenta.validarCampos(cuenta, "12345");
        assertEquals(expResult, result[4]);
    }
    
    @Test
    public void validarContraseñasNoConcuerdan() {
        Cuenta cuenta = new Cuenta();
        cuenta.setNombre("Mauricio");
        cuenta.setApellidos("Jimenez");
        cuenta.setContraseña("qwerty123454");
        cuenta.setEstadoSesion(0);
        cuenta.setNombreUsuario("mAURI");
        cuenta.setCorreo("acdc@gmail.com");
        cuenta.setImagen("img01");
        boolean expResult = false;
        boolean[] result = cuenta.validarCampos(cuenta, "qwerty12345");
        assertEquals(expResult, result[3]);
    }
    
    @Test
    public void validarCorreoIncorrecto() {
        Cuenta cuenta = new Cuenta();
        cuenta.setNombre("Mauricio");
        cuenta.setApellidos("Jimenez");
        cuenta.setContraseña("qwerty12345");
        cuenta.setEstadoSesion(0);
        cuenta.setNombreUsuario("Miguel");
        cuenta.setCorreo("acdc@gmail@.com");
        cuenta.setImagen("img01");
        boolean expResult = false;
        boolean[] result = cuenta.validarCampos(cuenta, "qwerty12345");
        assertEquals(expResult, result[5]);
    }
    /**
     * Test of validarCorreo method, of class Cuenta.
     */
    @Test
    public void ValidarCorreoCorrecto() {
        String email = "acdc@gmail.com";
        boolean expResult = true;
        boolean result = Cuenta.validarCorreo(email);
        assertEquals(expResult, result);
    }
    
    @Test
    public void ValidarCorreoIncorrecto() {
        String email = "acdc@gmail.com";
        boolean expResult = true;
        boolean result = Cuenta.validarCorreo(email);
        assertEquals(expResult, result);
    }
    
}
