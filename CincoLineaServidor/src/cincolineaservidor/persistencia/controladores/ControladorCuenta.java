package cincolineaservidor.persistencia.controladores;

import cincolinea.modelo.Cuenta;
import cincolineaservidor.persistencia.Cuentas;
import cincolineaservidor.persistencia.Rankings;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.RollbackException;

/**
 *
 * @author Adrián Bustamante Zarate
 * @author Miguel Leonardo Jimenez
 */
public class ControladorCuenta {

    public ControladorCuenta(EntityManagerFactory fabricaEntidad) {
        this.fabricaEntidad = fabricaEntidad;
    }
    private EntityManagerFactory fabricaEntidad = null;

    public EntityManager getEntityManager() {
        return fabricaEntidad.createEntityManager();
    }

    public Cuenta verificarAutenticacion(String nombreUsuario) {
        EntityManager entidad = getEntityManager();
        Query consulta = entidad.createQuery("SELECT c FROM Cuentas c WHERE c.nombreUsuario = :nombreUser").setParameter("nombreUser", nombreUsuario);
        Cuentas cuentaEntidadResultado;
        Cuenta cuentaResultado = new Cuenta(); 
        try {
            cuentaEntidadResultado = (Cuentas) consulta.getSingleResult();
            cuentaResultado.setContraseña(cuentaEntidadResultado.getContrasena());
            cuentaResultado.setEstadoSesion(cuentaEntidadResultado.getEstadoSesion());
        } catch (NoResultException ex) {
            System.out.println("Error en verificar autenticación: " + ex.getMessage());
        }finally{
            entidad.close();
        }
        return cuentaResultado;
    }

    public boolean registrarUsuario(Cuenta cuenta) {
        boolean registro = true;
        EntityManager entidad = getEntityManager();
        try {
            entidad.getTransaction().begin();
            Cuentas nuevaCuenta = new Cuentas();

            nuevaCuenta.setNombreUsuario(cuenta.getNombreUsuario());
            nuevaCuenta.setContrasena(cuenta.getContraseña());
            nuevaCuenta.setCorreoElectronico(cuenta.getCorreo());
            nuevaCuenta.setEstadoSesion(0);
            nuevaCuenta.setImagen(cuenta.getImagen());
            nuevaCuenta.setNombre(cuenta.getNombre());
            nuevaCuenta.setApellidos(cuenta.getApellidos());
            
            Collection<Rankings> rankings = new ArrayList();
            nuevaCuenta.setRankingsCollection(rankings);
            
            entidad.persist(nuevaCuenta);
            
            Rankings ranking = new Rankings();
            ranking.setIdRanking(null);
            ranking.setNombreUsuario(nuevaCuenta);
            ranking.setPartidasGanadas(0);
            ranking.setPartidasPerdidas(0);
            ranking.setPartidasEmpatadas(0);
            ranking.setPuntos(0);
            entidad.persist(ranking);
            
            entidad.getTransaction().commit();
            
        } catch (RollbackException ex) {
            System.out.println("Error: " + ex.getMessage());
            if (entidad.getTransaction().isActive()) {
                entidad.getTransaction().rollback();
            }
            registro = false;
        }finally{
            entidad.close();
        }

        return registro;
    }
    
    public String sacarImagenDePerfil(String idUsuario){
        String imagen = "";
        
        EntityManager entidad = getEntityManager();
        Query consulta = entidad.createQuery("SELECT c.imagen FROM Cuentas c WHERE c.nombreUsuario = :nombreUser").setParameter("nombreUser", idUsuario);
        
        try{
            imagen = (String) consulta.getSingleResult();
        } catch(NoResultException ex){
            System.out.println("Error: " + ex.getMessage());
        }finally{
            entidad.close();
        }
        
        return imagen;
    }
}
