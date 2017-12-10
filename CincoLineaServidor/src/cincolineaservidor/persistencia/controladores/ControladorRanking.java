package cincolineaservidor.persistencia.controladores;

import cincolinea.modelo.Ranking;
import cincolineaservidor.persistencia.Rankings;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.RollbackException;

/**
 * Controlador de la Entidad Rankings.
 * 
 * @author Miguel Leonardo Jimenez Jimenez
 * @author Adrian Bustamante Zarate
 */
public class ControladorRanking {
    private EntityManagerFactory fabricaEntidad;

    /**
     * Constructor sobrecargado del controlador.
     * 
     * @param fabricaEntidad Referencia a la persistencia.
     */
    public ControladorRanking(EntityManagerFactory fabricaEntidad) {
        this.fabricaEntidad = fabricaEntidad;
    }
    
    /**
     * Getter de la varable fabricaEntidad.
     * 
     * @return fabricaEntidad.
     */
    public EntityManager getEntityManager() {
        return fabricaEntidad.createEntityManager();
    }
    
    /**
     * Guarda dentro de la base de datos el usuario que ganó la partida y quien
     * la perdio.
     * 
     * @param ganador Identificador del usuario que ganó.
     * @param perdedor Identificador del usuario que perdió.
     * @return Confirmación de la operación realizada.
     */
    public boolean guardarResultadosPartida(String ganador, String perdedor){
        boolean validacion = false;
        int idRankingGanador = buscarIdRankingJugador(ganador);
        int idRankingPerdedor  = buscarIdRankingJugador(perdedor);
        EntityManager entidad = getEntityManager();
        try{
            
            entidad.getTransaction().begin();
            Rankings rankingGanador = entidad.find(Rankings.class, idRankingGanador);
            Rankings rankingPerdedor = entidad.find(Rankings.class, idRankingPerdedor);
            
            rankingGanador.setPartidasGanadas(rankingGanador.getPartidasGanadas() + 1);
            rankingGanador.setPuntos(rankingGanador.getPuntos() + 3);
            
            rankingPerdedor.setPartidasPerdidas(rankingPerdedor.getPartidasPerdidas() + 1);
            
            entidad.getTransaction().commit();
            
            validacion = true;
        }catch(RollbackException ex){
            Logger.getLogger(ControladorRanking.class.getName()).log(Level.SEVERE, null, ex);
            if(entidad.getTransaction().isActive()){
                entidad.getTransaction().rollback();
            }
        }finally{
            entidad.close();
        }
        
        return validacion;
    }
    
    /**
     * Guarda dentro de la base de datos de los usario que empataron una partida.
     * 
     * @param jugador1 Identificador del usuario 1.
     * @param jugador2 Identificador del usuario 2.
     * @return Confirmación de la operación realizada.
     */
    public boolean guardarEmpate(String jugador1, String jugador2){
        boolean validacion = false;
        int idRankingJugador1 = buscarIdRankingJugador(jugador1);
        int idRankingJugador2  = buscarIdRankingJugador(jugador2);
        EntityManager entidad = getEntityManager();
        try{
            
            entidad.getTransaction().begin();
            Rankings rankingJugador1 = entidad.find(Rankings.class, idRankingJugador1);
            Rankings rankingJugador2 = entidad.find(Rankings.class, idRankingJugador2);
            
            rankingJugador1.setPartidasEmpatadas(rankingJugador1.getPartidasEmpatadas() + 1);
            rankingJugador1.setPuntos(rankingJugador1.getPuntos() + 1);
            
            rankingJugador2.setPartidasEmpatadas(rankingJugador2.getPartidasEmpatadas() + 1);
            rankingJugador2.setPuntos(rankingJugador2.getPuntos() + 1);
            
            entidad.getTransaction().commit();
            
            validacion = true;
        }catch(RollbackException ex){
            Logger.getLogger(ControladorRanking.class.getName()).log(Level.SEVERE, null, ex);
            if(entidad.getTransaction().isActive()){
                entidad.getTransaction().rollback();
            }
        }finally{
            entidad.close();
        }
        
        return validacion;
    }
    
    /**
     * Guarda dentro de la base de datos la reducción de puntos al usuario que
     * abandonó partida.
     * 
     * @param idJugador Identificador del usuario que abandonó partida.
     * @return Confirmación de la operación realizada.
     */
    public boolean aplicarCastigo(String idJugador){
        boolean validacion = false;
        int idRankingJugador = buscarIdRankingJugador(idJugador);
        EntityManager entidad = getEntityManager();
        try{
            entidad.getTransaction().begin();
            Rankings rankingJugador = entidad.find(Rankings.class, idRankingJugador);
            
            rankingJugador.setPuntos(rankingJugador.getPuntos() - 1);
            
            entidad.getTransaction().commit();
            
            validacion = true;
        }catch(RollbackException ex){
            Logger.getLogger(ControladorRanking.class.getName()).log(Level.SEVERE, null, ex);
            if(entidad.getTransaction().isActive()){
                entidad.getTransaction().rollback();
            }
        }finally{
            entidad.close();
        }
        
        return validacion;
    }
    
    /**
     * Busca un usuario que dentro de la tabla de Rankings.
     * 
     * @param idUsuario Identificador del usuario.
     * @return 
     */
    private int buscarIdRankingJugador(String idUsuario){
        EntityManager entidad = getEntityManager();
        Query consulta = entidad.createQuery("SELECT r.idRanking FROM Rankings r WHERE r.nombreUsuario.nombreUsuario = :nombreUser").setParameter("nombreUser", idUsuario);
        int idRanking = 0;
        
        try{
            idRanking = (Integer) consulta.getSingleResult();
        }catch(NoResultException ex){
            Logger.getLogger(ControladorRanking.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            entidad.close();
        }
        
        return idRanking;
    }
    
    /**
     * Pasa una List<Rankings> a una List<Ranking>.
     * 
     * @param rankings Lista de 10 rankings.
     * @return List<Ranking> con los 10 mejores jugadores.
     */
    private List<Ranking> cambiarDeRankingsARanking(List<Rankings> rankings){
       List<Ranking> mejores10 = new ArrayList();
       Ranking rankingJugador;
       for(Rankings ranking: rankings){
           rankingJugador = new Ranking(ranking.getNombreUsuario().getNombreUsuario(),ranking.getPartidasGanadas(),ranking.getPartidasPerdidas(),ranking.getPartidasEmpatadas(),ranking.getPuntos());
           mejores10.add(rankingJugador);
       }
       
       return mejores10;
    }
    
    /**
     * Saca de la base de datos los 10 mejores de acuerdo a sus puntos de mayor
     * a menor.
     * 
     * @return Lista con los 10 mejores jugadores.
     */
    public List<Ranking> sacarMejores10(){
        List<Ranking> rankings;
        List<Rankings> rankingsJPA = new ArrayList();
        
        EntityManager entidad = getEntityManager();
        Query consulta = entidad.createQuery("SELECT r FROM Rankings r ORDER BY r.puntos DESC").setMaxResults(10);
        
        try{
            rankingsJPA = consulta.getResultList();
        }catch(Exception ex){
            Logger.getLogger(ControladorRanking.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            entidad.close();
        }
        
        rankings = cambiarDeRankingsARanking(rankingsJPA);
                
        return rankings;
    }
}
