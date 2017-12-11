package cincolineaservidor.persistencia.controladores;

import cincolinea.modelo.Cuenta;
import cincolineaservidor.persistencia.Cuentas;
import cincolineaservidor.persistencia.Rankings;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.RollbackException;

/**
 * Controlador de la entidad Cuentas.
 *
 * @author Adrián Bustamante Zarate
 * @author Miguel Leonardo Jimenez
 */
public class ControladorCuenta {

    /**
     * Constructor sobrecargado.
     *
     * @param fabricaEntidad Referencia a la persistenacia.
     */
    public ControladorCuenta(EntityManagerFactory fabricaEntidad) {
        this.fabricaEntidad = fabricaEntidad;
    }
    private EntityManagerFactory fabricaEntidad = null;

    /**
     * Getter de la variable fabricaEntidad.
     *
     * @return fabricaEntidad.
     */
    public EntityManager getEntityManager() {
        return fabricaEntidad.createEntityManager();
    }

    /**
     * Busca los datos de un usuario que desea ingresar al sistema.
     *
     * @param nombreUsuario Identificador de la cuenta del usuario.
     * @return Cuenta del usuario.
     */
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
            Logger.getLogger(ControladorCuenta.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            entidad.close();
        }
        return cuentaResultado;
    }

    /**
     * Guarda dentro de la base de datos una nueva cuenta.
     *
     * @param cuenta Nueva cuenta que se desea guardar.
     * @return Confirmación de la operación realizada.
     */
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
            Logger.getLogger(ControladorCuenta.class.getName()).log(Level.SEVERE, null, ex);
            if (entidad.getTransaction().isActive()) {
                entidad.getTransaction().rollback();
            }
            registro = false;
        } finally {
            entidad.close();
        }

        return registro;
    }

    /**
     * Cambia la disposición de la cuenta a 1.
     *
     * @param nombreUsuario Identificador de la cuenta del usuario.
     * @return Confirmación de la operación realizada.
     */
    public boolean desactivarPerfilInicioSesion(String nombreUsuario) {
        EntityManager entidad = getEntityManager();
        int estadoSesion;
        boolean validacion = false;

        try {
            entidad.getTransaction().begin();
            Cuentas cuentaUsuario = entidad.find(Cuentas.class, nombreUsuario);
            estadoSesion = cuentaUsuario.getEstadoSesion();

            if (estadoSesion == 0) {
                estadoSesion = 1;
            }

            cuentaUsuario.setEstadoSesion(estadoSesion);
            entidad.getTransaction().commit();
            validacion = true;

        } catch (RollbackException ex) {
            if (entidad.getTransaction().isActive()) {
                entidad.getTransaction().rollback();
                validacion = false;
                Logger.getLogger(ControladorCuenta.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return validacion;
    }

    /**
     * Cambia el estado de la cuenta de un usuario a 0.
     *
     * @param nombreUsuario Identificador de la cuenta del usuario.
     * @return Confirmación de la operación realizada.
     */
    public boolean activarPerfilInicioSesion(String nombreUsuario) {
        EntityManager entidad = getEntityManager();
        int estadoSesion;
        boolean validacion = false;

        try {
            entidad.getTransaction().begin();
            Cuentas cuentaUsuario = entidad.find(Cuentas.class, nombreUsuario);
            estadoSesion = cuentaUsuario.getEstadoSesion();

            if (estadoSesion == 1) {
                estadoSesion = 0;
            }

            cuentaUsuario.setEstadoSesion(estadoSesion);
            entidad.getTransaction().commit();
            validacion = true;

        } catch (RollbackException ex) {
            if (entidad.getTransaction().isActive()) {
                entidad.getTransaction().rollback();
                validacion = false;
                Logger.getLogger(ControladorCuenta.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return validacion;
    }

    /**
     * Activa el inicio de sesión de todos los perfiles de usuarios
     * desactivados.
     *
     * @return Confirmación de la operación realizada.
     */
    public boolean activarPerfilesInicioSesion() {
        EntityManager entidad = getEntityManager();
        int estadoSesion;
        boolean validacion = false;

        try {
            entidad.getTransaction().begin();

            List<Cuentas> cuentasUsuario = entidad.createQuery("SELECT c FROM Cuentas c").getResultList();
            //estadoSesion = cuentaUsuario.getEstadoSesion();
            for (Cuentas cuenta : cuentasUsuario) {
                if (cuenta.getEstadoSesion() == 1) {
                    cuenta.setEstadoSesion(0);
                    entidad.getTransaction().commit();
                }
            }
            validacion = true;

        } catch (RollbackException ex) {
            if (entidad.getTransaction().isActive()) {
                entidad.getTransaction().rollback();
                validacion = false;
                //Logger.getLogger(ControladorCuenta.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return validacion;
    }

    /**
     * Saca de la base de datos el identificador de la imagen de la cuenta.
     *
     * @param idUsuario Identificador de la cuenta del usuario.
     * @return Identificador de la imagen del usuario.
     */
    public String sacarImagenDePerfil(String idUsuario) {
        String imagen = "";

        EntityManager entidad = getEntityManager();
        Query consulta = entidad.createQuery("SELECT c.imagen FROM Cuentas c WHERE c.nombreUsuario = :nombreUser").setParameter("nombreUser", idUsuario);

        try {
            imagen = (String) consulta.getSingleResult();
        } catch (NoResultException ex) {
            Logger.getLogger(ControladorCuenta.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            entidad.close();
        }

        return imagen;
    }
}
