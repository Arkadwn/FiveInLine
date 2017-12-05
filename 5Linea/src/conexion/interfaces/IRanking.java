package conexion.interfaces;

import cincolinea.modelo.Ranking;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 * @date 4/12/2017
 * @time 12:29:30 PM
 */
public interface IRanking extends Remote{
    public boolean guardarResultadosPartida(String ganador, String perdedor) throws RemoteException;
    public boolean guardarEmpate(String jugador1, String jugador2) throws RemoteException;
    public boolean aplicarCastigo(String idJugador) throws RemoteException;
    public List<Ranking> sacarMejores10() throws RemoteException;
}
