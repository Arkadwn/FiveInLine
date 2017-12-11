/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cincolineaservidor;

import cincolineaservidor.persistencia.controladores.ControladorCuenta;
import conexion.ServidorRMI;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Clase que sirve como hilo secundario para la ejecución del servidor RMI
 *
 * @author Adrián Bustamante Zarate
 * @author Miguel Leonardo Jimenez
 * @date 10/12/2017
 * @time 11:36:35 PM
 */
public class HiloServidorRMI extends Thread {

    boolean conectado;
    ServidorRMI servidorAutenticacion;

    public void run() {
        servidorAutenticacion = new ServidorRMI();
        try {
            if (servidorAutenticacion.activarServiciosRMI()) {
                System.out.println("El servidor RMI esta en linea");
                conectado = true;
            } else {
                System.out.println("El servidor RMI no se ha ejecutado correctamente");
                conectado = false;
            }
        } catch (RemoteException | AlreadyBoundException ex) {
            System.out.println("El servidor RMI no se ha ejecutado correctamente");
            conectado = false;
        }
    }

    /**
     * Apaga las cuentas de usuario activas en el servidor apropiadamente para
     * que no existan errores en los inicios de sesión posteriormente
     */
    public void apagarServidor() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CincoLineaServidorPU", null);
        ControladorCuenta controlador = new ControladorCuenta(entityManagerFactory);
        controlador.activarPerfilesInicioSesion();
    }

    /**
     * Retorna una variable booleana que representa el estado del servidor
     *
     * @return Variable booleana que represneta el estado actual del servidor
     * SRMI
     */
    public boolean isconectado() {
        return conectado;
    }

}
