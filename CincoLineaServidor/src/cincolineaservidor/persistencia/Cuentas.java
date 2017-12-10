package cincolineaservidor.persistencia;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entidad que hace referencia a la tabla de la base de datos Cuentas.
 * 
 * @author Adrián Bustamante Zarate
 * @author Miguel Leonardo Jiménez Jiménez
 */
@Entity
@Table(name = "cuentas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cuentas.findAll", query = "SELECT c FROM Cuentas c"),
    @NamedQuery(name = "Cuentas.findByNombreUsuario", query = "SELECT c FROM Cuentas c WHERE c.nombreUsuario = :nombreUsuario"),
    @NamedQuery(name = "Cuentas.findByContrasena", query = "SELECT c FROM Cuentas c WHERE c.contrasena = :contrasena"),
    @NamedQuery(name = "Cuentas.findByCorreoElectronico", query = "SELECT c FROM Cuentas c WHERE c.correoElectronico = :correoElectronico"),
    @NamedQuery(name = "Cuentas.findByEstadoSesion", query = "SELECT c FROM Cuentas c WHERE c.estadoSesion = :estadoSesion"),
    @NamedQuery(name = "Cuentas.findByImagen", query = "SELECT c FROM Cuentas c WHERE c.imagen = :imagen"),
    @NamedQuery(name = "Cuentas.findByNombre", query = "SELECT c FROM Cuentas c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Cuentas.findByApellidos", query = "SELECT c FROM Cuentas c WHERE c.apellidos = :apellidos")})
public class Cuentas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "nombreUsuario")
    private String nombreUsuario;
    @Column(name = "contrasena")
    private String contrasena;
    @Column(name = "correoElectronico")
    private String correoElectronico;
    @Column(name = "estadoSesion")
    private Integer estadoSesion;
    @Column(name = "imagen")
    private String imagen;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellidos")
    private String apellidos;
    @OneToMany(mappedBy = "nombreUsuario")
    private Collection<Rankings> rankingsCollection;

    /**
     * Contructor por defecto.
     */
    public Cuentas() {
    }

    /**
     * Constructor sobrecargado.
     * 
     * @param nombreUsuario Identificador del usuario.
     */
    public Cuentas(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * Getter de la varible nombreUsuario.
     * 
     * @return nombreUsuario.
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * Setter de la varible nombreUsuario.
     * 
     * @param nombreUsuario
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * Getter de la varible contrasena.
     * 
     * @return contrasena
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * Setter de la varible contrasena.
     * 
     * @param contrasena contraseña del usuario.
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     * Getter de la varible correoElectronico.
     * 
     * @return correoElectronico.
     */
    public String getCorreoElectronico() {
        return correoElectronico;
    }

    /**
     * Setter de la varible correoElectronico.
     * 
     * @param correoElectronico correo electronico del usuario. 
     */
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    /**
     * Getter de la varible estadoSesion.
     * 
     * @return estadoSesion.
     */
    public Integer getEstadoSesion() {
        return estadoSesion;
    }

    /**
     * Setter de la varible estadoSesion.
     * 
     * @param estadoSesion Acesso a esta cuenta.
     */
    public void setEstadoSesion(Integer estadoSesion) {
        this.estadoSesion = estadoSesion;
    }

    /**
     * Getter de la varible imagen.
     * 
     * @return imagen.
     */
    public String getImagen() {
        return imagen;
    }

    /**
     * Setter de la varible estadoSesion.
     * 
     * @param imagen Identificador de la imagen del usuario.
     */
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    /**
     * Getter de la varible nombre.
     * 
     * @return nombre.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Setter de la varible imagen.
     * 
     * @param nombre nombre personal del usuario.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Getter de la varible apellidos.
     * 
     * @return apellidos.
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Setter de la varible apellidos.
     * 
     * @param apellidos Apellidos del usario.
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * Getter de la varible rankingsCollection.
     * 
     * @return rankingCollection.
     */
    @XmlTransient
    public Collection<Rankings> getRankingsCollection() {
        return rankingsCollection;
    }

    /**
     * Setter de la varible rankingsCollection.
     * 
     * @param rankingsCollection Collecion de rankings pertenecientes a la cuenta.
     */
    public void setRankingsCollection(Collection<Rankings> rankingsCollection) {
        this.rankingsCollection = rankingsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nombreUsuario != null ? nombreUsuario.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cuentas)) {
            return false;
        }
        Cuentas other = (Cuentas) object;
        return !((this.nombreUsuario == null && other.nombreUsuario != null) || (this.nombreUsuario != null && !this.nombreUsuario.equals(other.nombreUsuario)));
    }
    
    @Override
    public String toString() {
        return "cincolineaservidor.persistencia.Cuentas[ nombreUsuario=" + nombreUsuario + " ]";
    }

}
