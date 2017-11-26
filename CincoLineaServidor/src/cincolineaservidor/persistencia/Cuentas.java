/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
 * 
 * @author Adrián Bustamante Zarate
 * @date 18/11/2017
 * @time 09:12:53 PM
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
    @NamedQuery(name = "Cuentas.findByApellidoPatern", query = "SELECT c FROM Cuentas c WHERE c.apellidoPatern = :apellidoPatern"),
    @NamedQuery(name = "Cuentas.findByApellidoMatern", query = "SELECT c FROM Cuentas c WHERE c.apellidoMatern = :apellidoMatern")})
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
    @Column(name = "apellidoPatern")
    private String apellidoPatern;
    @Column(name = "apellidoMatern")
    private String apellidoMatern;
    @OneToMany(mappedBy = "nombreUsuario")
    private Collection<Rankings> rankingsCollection;

    public Cuentas() {
    }

    public Cuentas(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public Integer getEstadoSesion() {
        return estadoSesion;
    }

    public void setEstadoSesion(Integer estadoSesion) {
        this.estadoSesion = estadoSesion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPatern() {
        return apellidoPatern;
    }

    public void setApellidoPatern(String apellidoPatern) {
        this.apellidoPatern = apellidoPatern;
    }

    public String getApellidoMatern() {
        return apellidoMatern;
    }

    public void setApellidoMatern(String apellidoMatern) {
        this.apellidoMatern = apellidoMatern;
    }

    @XmlTransient
    public Collection<Rankings> getRankingsCollection() {
        return rankingsCollection;
    }

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
        if ((this.nombreUsuario == null && other.nombreUsuario != null) || (this.nombreUsuario != null && !this.nombreUsuario.equals(other.nombreUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cincolineaservidor.Cuentas[ nombreUsuario=" + nombreUsuario + " ]";
    }

}
