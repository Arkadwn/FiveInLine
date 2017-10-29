/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cincolineaservidor.persistencia.controladores;

import cincolineaservidor.persistencia.Cuenta;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 *
 * @author Adrián Bustamante Zarate
 * @author Miguel Leonardo Jimenez
 */
public class ControladorAutenticacion {

    public ControladorAutenticacion(EntityManagerFactory fabricaEntidad) {
        this.fabricaEntidad = fabricaEntidad;
    }
    private EntityManagerFactory fabricaEntidad = null;

    public EntityManager getEntityManager() {
        return fabricaEntidad.createEntityManager();
    }

    public String verificarAutenticacion(String nombreUsuario)throws Exception {
        EntityManager entidad = getEntityManager();
        String s = "SELECT c FROM Cuenta c WHERE c.nombreUsuario = :nombreUser ";
        Query q = entidad.createQuery(s).setParameter("nombreUser", nombreUsuario);
        Cuenta cuentaResultado;
        String contrasenaResultado = "";
        try{
        cuentaResultado = (Cuenta) q.getSingleResult();
        contrasenaResultado = cuentaResultado.getContrasena();
        }catch(Exception ex){
            System.out.println("Error: "+ex.getMessage());
        }
        return contrasenaResultado;
    }

    public boolean registrarUsuario(String nombreUsuario, String contrasena, String idImagen) {
        boolean registro = true;
        try {
            EntityManager entidad = getEntityManager();
            entidad.getTransaction().begin();
            
            Cuenta nuevaCuenta = new Cuenta(nombreUsuario);
            nuevaCuenta.setContrasena(contrasena);
            nuevaCuenta.setImagen(idImagen);
            
            entidad.persist(nuevaCuenta);
            entidad.getTransaction().commit();
            
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            registro = false;
        }
        
        return registro;
    }
}
