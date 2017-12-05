/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cincolineaservidor.persistencia;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author Adrián Bustamante Zarate
 * @date 4/12/2017
 * @time 07:07:30 PM
 */
@Entity
@Table(name = "rankings")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rankings.findAll", query = "SELECT r FROM Rankings r"),
    @NamedQuery(name = "Rankings.findByIdRanking", query = "SELECT r FROM Rankings r WHERE r.idRanking = :idRanking"),
    @NamedQuery(name = "Rankings.findByPartidasGanadas", query = "SELECT r FROM Rankings r WHERE r.partidasGanadas = :partidasGanadas"),
    @NamedQuery(name = "Rankings.findByPartidasPerdidas", query = "SELECT r FROM Rankings r WHERE r.partidasPerdidas = :partidasPerdidas"),
    @NamedQuery(name = "Rankings.findByPuntos", query = "SELECT r FROM Rankings r WHERE r.puntos = :puntos"),
    @NamedQuery(name = "Rankings.findByPartidasEmpatadas", query = "SELECT r FROM Rankings r WHERE r.partidasEmpatadas = :partidasEmpatadas")})
public class Rankings implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idRanking")
    private Integer idRanking;
    @Column(name = "partidasGanadas")
    private Integer partidasGanadas;
    @Column(name = "partidasPerdidas")
    private Integer partidasPerdidas;
    @Column(name = "puntos")
    private Integer puntos;
    @Column(name = "partidasEmpatadas")
    private Integer partidasEmpatadas;
    @JoinColumn(name = "nombreUsuario", referencedColumnName = "nombreUsuario")
    @ManyToOne
    private Cuentas nombreUsuario;

    public Rankings() {
    }

    public Rankings(Integer idRanking) {
        this.idRanking = idRanking;
    }

    public Integer getIdRanking() {
        return idRanking;
    }

    public void setIdRanking(Integer idRanking) {
        this.idRanking = idRanking;
    }

    public Integer getPartidasGanadas() {
        return partidasGanadas;
    }

    public void setPartidasGanadas(Integer partidasGanadas) {
        this.partidasGanadas = partidasGanadas;
    }

    public Integer getPartidasPerdidas() {
        return partidasPerdidas;
    }

    public void setPartidasPerdidas(Integer partidasPerdidas) {
        this.partidasPerdidas = partidasPerdidas;
    }

    public Integer getPuntos() {
        return puntos;
    }

    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }

    public Integer getPartidasEmpatadas() {
        return partidasEmpatadas;
    }

    public void setPartidasEmpatadas(Integer partidasEmpatadas) {
        this.partidasEmpatadas = partidasEmpatadas;
    }

    public Cuentas getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(Cuentas nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRanking != null ? idRanking.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rankings)) {
            return false;
        }
        Rankings other = (Rankings) object;
        if ((this.idRanking == null && other.idRanking != null) || (this.idRanking != null && !this.idRanking.equals(other.idRanking))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cincolineaservidor.persistencia.Rankings[ idRanking=" + idRanking + " ]";
    }

}
