/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

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
        boolean expResult = true;
        boolean result = instance.autenticarCuenta(usuario, contrasena);
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
        boolean expResult = false;
        boolean result = instance.autenticarCuenta(usuario, contrasena);
        assertEquals(expResult, result);
        
        //fail("El test a fallado");
    }

    /**
     * Test de registrarCuenta, de la clase ServidorRMI
     */
    @Test
    public void testRegistrarCuenta() throws Exception {
        System.out.println("Se probara el metodo: registrarCuenta");
        List<String> datosUsuario = new ArrayList();
        datosUsuario.add("Adri");
        datosUsuario.add("ChinaHP");
        datosUsuario.add("arkadwn@gmail.com");
        datosUsuario.add("0");
        datosUsuario.add("img99");
        datosUsuario.add("Adrián");
        datosUsuario.add("Bustamante");
        datosUsuario.add("Zarate");
        ServidorRMI instance = new ServidorRMI();
        boolean expResult = true;
        boolean result = instance.registrarCuenta(datosUsuario);
        assertEquals(expResult, result);
        
        //fail("El teste a fallado");
    }
    
    /**
     * Test de registrarCuenta, este debera retornar falso
     */
    @Test
    public void testRegistrarCuentaFalso() throws Exception {
        System.out.println("Se probara el metodo: registrarCuenta");
        List<String> datosUsuario = new ArrayList();
        datosUsuario.add("asdfghjklñpoiuytrewqzxcvbnmklpñoiuhygvtfcredxswqazxasdfg");
        datosUsuario.add("ChinaHP");
        datosUsuario.add("arkadwn@gmail.com");
        datosUsuario.add("0");
        datosUsuario.add("img99");
        datosUsuario.add("Adrián");
        datosUsuario.add("Bustamante");
        datosUsuario.add("Zarate");
        ServidorRMI instance = new ServidorRMI();
        boolean expResult = false;
        boolean result = instance.registrarCuenta(datosUsuario);
        assertEquals(expResult, result);
        
        //fail("El teste a fallado");
    }
}
