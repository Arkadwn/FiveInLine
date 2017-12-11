package cincolineaservidor;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * Clase principal del control del servidor Cinco en linea
 *
 * @author Adrián Bustamante Zarate
 * @author Miguel Leonardo Jimenez
 * @date 10/12/2017
 * @time 11:36:35 PM
 */
public class CincoLineaServidor {

    private HiloServidorRMI encendedorServidorRMI;

    public static void main(String[] args) {
        CincoLineaServidor servidor = new CincoLineaServidor();
        servidor.controlServidorMenu();
    }

    /**
     * Metodo que visualiza el menu en consola del servidor
     */
    public void controlServidorMenu() {
        String opcionElegida = null;
        do {
            System.out.print("-- Bienvenido al servidor de Cinco en linea el juego -- \n"
                    + "Estas son las opciones disponibles que tiene el servidor:\n"
                    + "1. Iniciar el servidor.\n"
                    + "3. Verificar conexion con el servidor de Node.js y estado del servidor RMI\n"
                    + "5. Apagar el servidor y salir del menu.\n"
                    + "Elija un opción e introduzca el numero de preferencia: ");
            Scanner lecturaTeclado = new Scanner(System.in);
            opcionElegida = lecturaTeclado.nextLine();
            switch (opcionElegida) {
                case "1":
                    iniciarServidorRMI();
                    break;
                case "3":
                    if (verficarConexionSocket()) {
                        System.out.println("El servidor Node.js responde de maravilla.");
                        if (encendedorServidorRMI.isconectado()) {
                            System.out.println("El servidor RMI responde de maravilla.");
                        } else {
                            System.out.println("El servidor RMI no responde.");
                        }
                    } else {
                        System.out.println("El servidor Node.js no responde.");
                        decisionErrorServidor();
                    }
                    break;
                case "5":
                    apagarServidor();
                    break;
                default:
                    System.out.println("Opción no valida, por favor introduzca una de la lista presentada.");
                    break;
            }
        } while (!"5".equals(opcionElegida));
    }

    /**
     * Inicializa el servidor RMI, recordar activar el demonio RMIRegistry antes
     */
    public void iniciarServidorRMI() {
        encendedorServidorRMI = new HiloServidorRMI();
        encendedorServidorRMI.start();

    }

    /**
     * Retorna true si se puede establecer una conexión al servidor Socket del
     * servidor Node.js
     *
     * @return variable booleana del resultado de conexión
     */
    public boolean verficarConexionSocket() {
        boolean bandera = false;
        try {
            java.net.Socket socketPing = new Socket("localhost", 8000);
            bandera = socketPing.isConnected();
            socketPing.close();
        } catch (IOException ex) {
            bandera = false;
        }
        return bandera;
    }

    /**
     * Metodo de decisión que pregunta al administrador del servidor que hacer
     * al encontrar un error en el servidor Node.js
     */
    private void decisionErrorServidor() {
        Scanner lecturaTeclado = new Scanner(System.in);
        String opcionElegida = null;
        do {
            System.out.println("¿Desea apagar el servidor?");
            opcionElegida = lecturaTeclado.nextLine();
            switch (opcionElegida) {
                case "1":
                    System.out.println("Apagando ..");
                    apagarServidor();
                    break;
                case "0":
                    System.out.println("Ok ..");
                    break;
                default:
                    System.out.println("Opción no valida.");
            }
        } while (!"1".equals(opcionElegida) || !"0".equals(opcionElegida));
    }

    /**
     * Metodo que apaga apropiadamente el servidor y termina el proceso del
     * servidor
     */
    private void apagarServidor() {
        encendedorServidorRMI.apagarServidor();

        encendedorServidorRMI.stop();
        System.exit(0);
    }
}
