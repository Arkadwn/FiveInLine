package cincolineaservidor.persistencia.controladores;

import cincolinea.modelo.Cuenta;
import cincolineaservidor.persistencia.Cuentas;
import cincolineaservidor.persistencia.Rankings;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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

    public String verificarAutenticacion(String nombreUsuario) {
        EntityManager entidad = getEntityManager();
        Query consulta = entidad.createQuery("SELECT c FROM Cuentas c WHERE c.nombreUsuario = :nombreUser").setParameter("nombreUser", nombreUsuario);
        Cuentas cuentaResultado;
        String contrasenaResultado = "";
        try {
            cuentaResultado = (Cuentas) consulta.getSingleResult();
            contrasenaResultado = cuentaResultado.getContrasena();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return contrasenaResultado;
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
            nuevaCuenta.setEstadoSesion(1);
            nuevaCuenta.setImagen(cuenta.getImagen());
            nuevaCuenta.setNombre(cuenta.getNombre());
            nuevaCuenta.setApellidos(cuenta.getApellidos());
            
            Rankings ranking = new Rankings();
            ranking.setIdRanking(null);
            ranking.setNombreUsuario(nuevaCuenta);
            ranking.setPartidasGanadas(0);
            ranking.setPartidasPerdidas(0);
            ranking.setPuntos(0);
            Collection<Rankings> rankings = new ArrayList();
            rankings.add(ranking);
            nuevaCuenta.setRankingsCollection(rankings);
            
            entidad.persist(nuevaCuenta);
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
}
