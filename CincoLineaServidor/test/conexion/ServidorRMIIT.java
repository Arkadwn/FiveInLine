/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

import cincolinea.modelo.Cuenta;
import cincolinea.modelo.Ranking;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class ServidorRMIIT {

    /*
    Para que todos estos test funcionen debe echarse a funcionar primero el servidor
    RMI para que funcionen, todos en conjunto no funcionarán por la implementación del
    daemon RMIRegistry.
     */
    /**
     * Test de autenticarCuenta, de la clase ServidorRMI.
     */
    @Test
    public void testAutenticarCuenta() throws Exception {
        System.out.println("Se probara el metodo: autenticarCuenta");
        String usuario = "ArkadWN";
        String contrasena = "2937f11cc9bba01b1fb18147e982895733390124b266237440de9af66312dc58";
        ServidorRMI instance = new ServidorRMI();
        int expResult = 1;
        int result = instance.autenticarCuenta(usuario, contrasena);
        assertEquals(expResult, result);

        //fail("El test a fallado");
    }

    //Todos estos test funcionan una vez echado el daemon andar.
    /*
    * Test de autenticarCuenta, debera retornar falso.
     */
    @Test
    public void testAutenticarCuentaFalso() throws Exception {
        System.out.println("Se probara el metodo: autenticarCuenta");
        String usuario = "algúnUsuarioErroneo";
        String contrasena = "contrasenaSinHashear";
        ServidorRMI instance = new ServidorRMI();
        int expResult = 0;
        int result = instance.autenticarCuenta(usuario, contrasena);
        assertEquals(expResult, result);

        //fail("El test a fallado");
    }

    /**
     * Test de registrarCuenta, de la clase ServidorRMI
     */
    @Test
    public void testRegistrarCuenta() throws Exception {
        System.out.println("Se probara el metodo: registrarCuenta");
        Cuenta usuario = new Cuenta();
        usuario.setNombreUsuario("Adri");
        usuario.setContraseña("ChinaHP");
        usuario.setCorreo("arkadwn@gmail.com");

        usuario.setImagen("img99");
        usuario.setNombre("Adrián");
        usuario.setApellidos("Bustamante Zarate");
        ServidorRMI instance = new ServidorRMI();
        boolean expResult = true;
        boolean result = instance.registrarCuenta((Cuenta) usuario);
        assertEquals(expResult, result);

        //fail("El teste a fallado");
    }

    /**
     * Test de registrarCuenta, este debera retornar falso
     */
    @Test
    public void testRegistrarCuentaFalso() throws Exception {
        System.out.println("Se probara el metodo: registrarCuenta");
        Cuenta usuario = new Cuenta();
        usuario.setNombreUsuario("asdfghjklñpoiuytrewqzxcvbnmklpñoiuhygvtfcredxswqazxasdfg");
        usuario.setContraseña("ChinaHP");
        usuario.setCorreo("arkadwn@gmail.com");

        usuario.setImagen("img99");
        usuario.setNombre("Adrián");
        usuario.setApellidos("Bustamante Zarate");
        ServidorRMI instance = new ServidorRMI();
        boolean expResult = false;
        boolean result = instance.registrarCuenta(usuario);
        assertEquals(expResult, result);

        //fail("El teste a fallado");
    }

    /**
     * Test positivo para probar el metodo de verficarConexion
     */
    @Test
    public void testVerficarConexion() {
        System.out.println("verficarConexion");
        boolean banderaSeñal = false;
        ServidorRMI instance = new ServidorRMI();
        boolean expResult = false;
        boolean result = instance.verficarConexion(banderaSeñal);
        assertEquals(expResult, result);

    }

    /**
     * Test negativo para probar el metodo de verficarConexion
     */
    @Test
    public void testVerficarConexionFalso() {
        System.out.println("verficarConexion");
        boolean banderaSeñal = false;
        ServidorRMI instance = new ServidorRMI();
        boolean expResult = true;
        boolean result = instance.verficarConexion(banderaSeñal);
        assertEquals(expResult, result);
    }

    /**
     * Test positivo para probar el metodo de guardarResultadosPartida
     */
    @Test
    public void testGuardarResultadosPartida() throws Exception {
        System.out.println("guardarResultadosPartida");
        String ganador = "ArkadWN";
        String perdedor = "Leo";
        ServidorRMI instance = new ServidorRMI();
        boolean expResult = true;
        boolean result = instance.guardarResultadosPartida(ganador, perdedor);
        assertEquals(expResult, result);

    }

    /**
     * Test negativo para probar el metodo de guardarResultadosPartida
     */
    @Test
    public void testGuardarResultadosPartidaFalso() throws Exception {
        System.out.println("guardarResultadosPartida");
        String ganador = "ArkadWN";
        String perdedor = "ElRevo";
        ServidorRMI instance = new ServidorRMI();
        boolean expResult = false;
        boolean result = instance.guardarResultadosPartida(ganador, perdedor);
        assertEquals(expResult, result);

    }

    /**
     * Test positivo para probar el metodo de guardarEmpate
     */
    @Test
    public void testGuardarEmpate() throws Exception {
        System.out.println("guardarEmpate");
        String jugador1 = "ArkadWN";
        String jugador2 = "Leo";
        ServidorRMI instance = new ServidorRMI();
        boolean expResult = true;
        boolean result = instance.guardarEmpate(jugador1, jugador2);
        assertEquals(expResult, result);
    }

    /**
     * Test negativo para probar el metodo de guardarEmpate
     */
    @Test
    public void testGuardarEmpateFalso() throws Exception {
        System.out.println("guardarEmpate");
        String jugador1 = "ArkadWN";
        String jugador2 = "ElRevo";
        ServidorRMI instance = new ServidorRMI();
        boolean expResult = false;
        boolean result = instance.guardarEmpate(jugador1, jugador2);
        assertEquals(expResult, result);
    }

    /**
     * Test positivo para probar el metodo de aplicarCastigo
     */
    @Test
    public void testAplicarCastigo() throws Exception {
        System.out.println("aplicarCastigo");
        String idJugador = "";
        ServidorRMI instance = new ServidorRMI();
        boolean expResult = true;
        boolean result = instance.aplicarCastigo(idJugador);
        assertEquals(expResult, result);
    }

    /**
     * Test negativo para probar el metodo de aplicarCastigo
     */
    @Test
    public void testAplicarCastigoNegativo() throws Exception {
        System.out.println("aplicarCastigo");
        String idJugador = "ElRevo";
        ServidorRMI instance = new ServidorRMI();
        boolean expResult = false;
        boolean result = instance.aplicarCastigo(idJugador);
        assertEquals(expResult, result);
    }

    /**
     * Test positivo para el metodo sacarMejores10
     */
    @Test
    public void testSacarMejores10() throws Exception {
        System.out.println("sacarMejores10");
        ServidorRMI instance = new ServidorRMI();
        List<Ranking> result = instance.sacarMejores10();
        assertEquals(false, result.isEmpty());
    }

    /**
     * Test negativo para el metodo sacarMejores10
     */
    @Test
    public void testSacarMejores10False() throws Exception {
        System.out.println("sacarMejores10");
        ServidorRMI instance = new ServidorRMI();
        List<Ranking> result = instance.sacarMejores10();
        assertEquals(true, result.isEmpty());
    }

    /**
     * Test positivo para el metodo sacarImagenDePerfil
     */
    @Test
    public void testSacarImagenDePerfil() throws Exception {
        System.out.println("sacarImagenDePerfil");
        String nombreUsuario = "ArkadWN";
        ServidorRMI instance = new ServidorRMI();
        String expResult = "img3";
        String result = instance.sacarImagenDePerfil(nombreUsuario);
        assertEquals(expResult, result);
    }

    /**
     * Test negativo para el metodo sacarImagenDePerfil
     */
    @Test
    public void testSacarImagenDePerfilFalso() throws Exception {
        System.out.println("sacarImagenDePerfil");
        String nombreUsuario = "ElRevo";
        ServidorRMI instance = new ServidorRMI();
        String expResult = null;
        String result = instance.sacarImagenDePerfil(nombreUsuario);
        assertEquals(expResult, result);
    }

    /**
     * Test positivo para el metodo activarEstadoSesion
     */
    @Test
    public void testActivarEstadoSesion() throws Exception {
        System.out.println("activarEstadoSesion");
        String nombreUsuario = "ArkadWN";
        ServidorRMI instance = new ServidorRMI();
        boolean expResult = true;
        boolean result = instance.activarEstadoSesion(nombreUsuario);
        assertEquals(expResult, result); 
    }

    /**
     * Test negativo para el metodo activarEstadoSesion
     */
    @Test
    public void testActivarEstadoSesionFalso() throws Exception {
        System.out.println("activarEstadoSesion");
        String nombreUsuario = "ElRevo";
        ServidorRMI instance = new ServidorRMI();
        boolean expResult = false;
        boolean result = instance.activarEstadoSesion(nombreUsuario);
        assertEquals(expResult, result);
    }

    /**
     * Test positivo para el metodo desactivarEstadoSesion
     */
    @Test
    public void testDesactivarEstadoSesion() throws Exception {
        System.out.println("desactivarEstadoSesion");
        String nombreUsuario = "ArkadWN";
        ServidorRMI instance = new ServidorRMI();
        boolean expResult = true;
        boolean result = instance.desactivarEstadoSesion(nombreUsuario);
        assertEquals(expResult, result);
    }
    
        /**
     * Test positivo para el metodo desactivarEstadoSesion
     */
    @Test
    public void testDesactivarEstadoSesionFalso() throws Exception {
        System.out.println("desactivarEstadoSesion");
        String nombreUsuario = "ElRevo";
        ServidorRMI instance = new ServidorRMI();
        boolean expResult = false;
        boolean result = instance.desactivarEstadoSesion(nombreUsuario);
        assertEquals(expResult, result);
    }
}
