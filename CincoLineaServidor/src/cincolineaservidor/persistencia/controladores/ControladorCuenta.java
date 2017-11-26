/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cincolineaservidor.persistencia.controladores;

import cincolineaservidor.persistencia.Cuentas;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

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

    public String verificarAutenticacion(String nombreUsuario) throws Exception {
        EntityManager entidad = getEntityManager();
        String s = "SELECT c FROM Cuenta c WHERE c.nombreUsuario = :nombreUser ";
        Query q = entidad.createQuery(s).setParameter("nombreUser", nombreUsuario);
        Cuentas cuentaResultado;
        String contrasenaResultado = "";
        try {
            cuentaResultado = (Cuentas) q.getSingleResult();
            contrasenaResultado = cuentaResultado.getContrasena();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return contrasenaResultado;
    }

    public boolean registrarUsuario(List<String> datosUsuario) {
        boolean registro = true;
        EntityManager entidad = getEntityManager();
        try {
            entidad.getTransaction().begin();
            Cuentas nuevaCuenta = new Cuentas();

            nuevaCuenta.setNombreUsuario(datosUsuario.get(0));
            nuevaCuenta.setContrasena(datosUsuario.get(1));
            nuevaCuenta.setCorreoElectronico(datosUsuario.get(2));
            nuevaCuenta.setEstadoSesion(Integer.parseInt(datosUsuario.get(3)));
            nuevaCuenta.setImagen(datosUsuario.get(4));
            nuevaCuenta.setNombre(datosUsuario.get(5));
            nuevaCuenta.setApellidoMatern(datosUsuario.get(6));
            nuevaCuenta.setApellidoPatern(datosUsuario.get(7));

            entidad.persist(nuevaCuenta);
            entidad.getTransaction().commit();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            if (entidad.getTransaction().isActive()) {
                entidad.getTransaction().rollback();
            }
            registro = false;
        }

        return registro;
    }
}
