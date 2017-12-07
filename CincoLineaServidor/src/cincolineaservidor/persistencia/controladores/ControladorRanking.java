package cincolineaservidor.persistencia.controladores;

import cincolinea.modelo.Ranking;
import cincolineaservidor.persistencia.Rankings;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.RollbackException;

/**
 * 
 * @author Miguel Leonardo Jimenez Jimenez
 * @author Adrian Bustamante Zarate
 */
public class ControladorRanking {
    private EntityManagerFactory fabricaEntidad;

    public ControladorRanking(EntityManagerFactory fabricaEntidad) {
        this.fabricaEntidad = fabricaEntidad;
    }
    
    public EntityManager getEntityManager() {
        return fabricaEntidad.createEntityManager();
    }
    
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
            if(entidad.getTransaction().isActive()){
                entidad.getTransaction().rollback();
            }
        }finally{
            entidad.close();
        }
        
        return validacion;
    }
    
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
            if(entidad.getTransaction().isActive()){
                entidad.getTransaction().rollback();
            }
        }finally{
            entidad.close();
        }
        
        return validacion;
    }
    
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
            if(entidad.getTransaction().isActive()){
                entidad.getTransaction().rollback();
            }
        }finally{
            entidad.close();
        }
        
        return validacion;
    }
    
    private int buscarIdRankingJugador(String idJugador){
        EntityManager entidad = getEntityManager();
        Query consulta = entidad.createQuery("SELECT r.idRanking FROM Rankings r WHERE r.nombreUsuario.nombreUsuario = :nombreUser").setParameter("nombreUser", idJugador);
        int idRanking = 0;
        
        try{
            idRanking = (Integer) consulta.getSingleResult();
        }catch(Exception ex){
            System.out.println("Error: "+ex.getMessage());
        }finally{
            entidad.close();
        }
        
        return idRanking;
    }
    
    private List<Ranking> cambiarDeRankingsARanking(List<Rankings> rankings){
       List<Ranking> mejores10 = new ArrayList();
       Ranking rankingJugador;
       for(Rankings ranking: rankings){
           rankingJugador = new Ranking(ranking.getNombreUsuario().getNombreUsuario(),ranking.getPartidasGanadas(),ranking.getPartidasPerdidas(),ranking.getPartidasEmpatadas(),ranking.getPuntos());
           mejores10.add(rankingJugador);
       }
       
       return mejores10;
    }
    
    public List<Ranking> sacarMejores10(){
        List<Ranking> rankings;
        List<Rankings> rankingsJPA = new ArrayList();
        
        EntityManager entidad = getEntityManager();
        Query consulta = entidad.createQuery("SELECT r FROM Rankings r ORDER BY r.puntos DESC").setMaxResults(10);
        
        try{
            rankingsJPA = consulta.getResultList();
        }catch(Exception ex){
            
        }finally{
            entidad.close();
        }
        
        rankings = cambiarDeRankingsARanking(rankingsJPA);
                
        return rankings;
    }
}
