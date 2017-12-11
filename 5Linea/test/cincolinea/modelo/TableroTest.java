/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cincolinea.modelo;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 */
public class TableroTest {

    /**
     * Test of validarEmpate method, of class Tablero.
     */
    @Test
    public void validarEmpateTableroTamaño10() {
        Tablero tablero = new Tablero(10);
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                tablero.validarJugada(i, j, "N");
            }
        }
        boolean expResult = true;
        boolean result = tablero.validarEmpate();
        assertEquals(expResult, result);
    }

    @Test
    public void validarEmpateTableroTamaño9() {
        Tablero tablero = new Tablero(9);
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                tablero.validarJugada(i, j, "N");
            }
        }
        boolean expResult = true;
        boolean result = tablero.validarEmpate();
        assertEquals(expResult, result);
    }
    
    @Test
    public void validarEmpateTableroTamaño8() {
        Tablero tablero = new Tablero(8);
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                tablero.validarJugada(i, j, "N");
            }
        }
        boolean expResult = true;
        boolean result = tablero.validarEmpate();
        assertEquals(expResult, result);
    }
    
    @Test
    public void validarEmpateTableroTamaño8Con56Tiros() {
        Tablero tablero = new Tablero(8);
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 7; j++){
                tablero.validarJugada(i, j, "N");
            }
        }
        boolean expResult = false;
        boolean result = tablero.validarEmpate();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of validarJugada method, of class Tablero.
     */
    @Test
    public void validarJugadaExitosa() {
        int x = 0;
        int y = 0;
        String ficha = "N";
        Tablero tablero = new Tablero(10);
        boolean expResult = true;
        boolean result = tablero.validarJugada(x, y, ficha);
        assertEquals(expResult, result);
    }
    
    @Test
    public void validarJugadaInvalia() {
        int x = 0;
        int y = 0;
        String ficha = "N";
        Tablero tablero = new Tablero(10);
        boolean expResult = false;
        tablero.validarJugada(x, y, ficha);
        boolean result = tablero.validarJugada(x, y, ficha);
        assertEquals(expResult, result);
    }
    
    @Test
    public void validarJugadaInvalidaConDiferenteColorDeFicha() {
        int x = 0;
        int y = 0;
        String ficha = "N";
        Tablero tablero = new Tablero(10);
        boolean expResult = false;
        tablero.validarJugada(x, y, ficha);
        ficha = "B";
        boolean result = tablero.validarJugada(x, y, ficha);
        assertEquals(expResult, result);
    }
    /**
     * Test of validarSiGano method, of class Tablero.
     */
    @Test
    public void testValidarSiGanoHorizontal5TirosMismoColor() {
        Tablero tablero = new Tablero(10);
        boolean expResult = true;
        tablero.validarJugada(0, 0, "N");
        tablero.validarJugada(1, 0, "N");
        tablero.validarJugada(2, 0, "N");
        tablero.validarJugada(3, 0, "N");
        tablero.validarJugada(4, 0, "N");
        boolean result = tablero.validarSiGano(4, 0, "N");
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidarSiGanoVertical5TirosMismoColor() {
        Tablero tablero = new Tablero(10);
        boolean expResult = true;
        tablero.validarJugada(0, 0, "N");
        tablero.validarJugada(0, 2, "N");
        tablero.validarJugada(0, 3, "N");
        tablero.validarJugada(0, 4, "N");
        tablero.validarJugada(0, 1, "N");
        boolean result = tablero.validarSiGano(0, 1, "N");
        assertEquals(expResult, result);
    }
    @Test
    public void testValidarSiGanoDiagonal5TirosMismoColor() {
        Tablero tablero = new Tablero(10);
        boolean expResult = true;
        tablero.validarJugada(0, 9, "N");
        tablero.validarJugada(1, 8, "N");
        tablero.validarJugada(2, 7, "N");
        tablero.validarJugada(3, 6, "N");
        tablero.validarJugada(4, 5, "N");
        boolean result = tablero.validarSiGano(4, 5, "N");
        assertEquals(expResult, result);
    }
    @Test
    public void testValidarSiGanoDiagonalInvertida5TirosMismoColor() {
        Tablero tablero = new Tablero(10);
        boolean expResult = true;
        tablero.validarJugada(9, 0, "N");
        tablero.validarJugada(8, 1, "N");
        tablero.validarJugada(7, 2, "N");
        tablero.validarJugada(6, 3, "N");
        tablero.validarJugada(5, 4, "N");
        boolean result = tablero.validarSiGano(5, 4, "N");
        assertEquals(expResult, result);
    }
    @Test
    public void testValidarSiNoGanoHorizontal5TirosDiferenteColor() {
        Tablero tablero = new Tablero(10);
        boolean expResult = false;
        tablero.validarJugada(0, 0, "N");
        tablero.validarJugada(1, 0, "B");
        tablero.validarJugada(2, 0, "N");
        tablero.validarJugada(3, 0, "B");
        tablero.validarJugada(4, 0, "N");
        boolean result = tablero.validarSiGano(4, 0, "N");
        assertEquals(expResult, result);
    }
    
    /**
     *
     */
    @Test
    public void testValidarSiNoGanoTiroDiferente() {
        Tablero tablero = new Tablero(10);
        boolean expResult = false;
        tablero.validarJugada(0, 0, "N");
        tablero.validarJugada(1, 0, "B");
        tablero.validarJugada(2, 0, "N");
        tablero.validarJugada(3, 0, "B");
        tablero.validarJugada(4, 0, "B");
        boolean result = tablero.validarSiGano(4, 0, "N");
        assertEquals(expResult, result);
    }
    @Test
    public void Validar5EnLineaPeroDiferenteColor() {
        Tablero tablero = new Tablero(10);
        boolean expResult = false;
        tablero.validarJugada(0, 0, "B");
        tablero.validarJugada(1, 0, "B");
        tablero.validarJugada(2, 0, "B");
        tablero.validarJugada(3, 0, "B");
        tablero.validarJugada(4, 0, "B");
        boolean result = tablero.validarSiGano(4, 0, "N");
        assertEquals(expResult, result);
    }
}
