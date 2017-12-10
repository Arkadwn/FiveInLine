package conexion.interfaces;

import cincolinea.modelo.Ranking;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Interfaz para realizar los servicos RMI para Rankings.
 * 
 * @author Miguel Leonardo Jimenez Jimenez
 * @author Adrián Bustamante Zarate
 */
public interface IRanking extends Remote{
    /**
     * Guarda los resultados de una partida.
     * 
     * @param ganador Identificador del usuario ganador.
     * @param perdedor Identificador del usuario perdedor.
     * @return Confirmación de la operación realizada.
     * @throws RemoteException Si la referencia no pudo ser creada.
     */
    public boolean guardarResultadosPartida(String ganador, String perdedor) throws RemoteException;
    
    /**
     * Guarda el empate de una pardida entre dos jugadores.
     * 
     * @param jugador1 Identifiacdor del usuario 1.
     * @param jugador2 Identificador del usuario 2.
     * @return Confirmación de la operación realizada.
     * @throws RemoteException Si la referencia no pudo ser creada.
     */
    public boolean guardarEmpate(String jugador1, String jugador2) throws RemoteException;
    
    /**
     * Resta puntos a un usuario cuando abandona una partida.
     * 
     * @param idJugador Identificador del usuario que abandono partida.
     * @return Confirmación de la operación realizada.
     * @throws RemoteException Si la referencia no pudo ser creada.
     */
    public boolean aplicarCastigo(String idJugador) throws RemoteException;
    
    /**
     * Saca los mejores 10 jugadores en base a sus puntajes.
     * 
     * @return Lista con los 10 mejores jugadores.
     * @throws RemoteException Si la referencia no pudo ser creada.
     */
    public List<Ranking> sacarMejores10() throws RemoteException;
}