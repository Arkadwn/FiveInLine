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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
@Entity
@Table(name = "ranking")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ranking.findAll", query = "SELECT r FROM Ranking r"),
    @NamedQuery(name = "Ranking.findByIdRanking", query = "SELECT r FROM Ranking r WHERE r.idRanking = :idRanking"),
    @NamedQuery(name = "Ranking.findByPartidasGanadas", query = "SELECT r FROM Ranking r WHERE r.partidasGanadas = :partidasGanadas"),
    @NamedQuery(name = "Ranking.findByPartidasPerdidas", query = "SELECT r FROM Ranking r WHERE r.partidasPerdidas = :partidasPerdidas"),
    @NamedQuery(name = "Ranking.findByPuntos", query = "SELECT r FROM Ranking r WHERE r.puntos = :puntos")})
public class Ranking implements Serializable {

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

    public Ranking() {
    }

    public Ranking(Integer idRanking) {
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRanking != null ? idRanking.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ranking)) {
            return false;
        }
        Ranking other = (Ranking) object;
        if ((this.idRanking == null && other.idRanking != null) || (this.idRanking != null && !this.idRanking.equals(other.idRanking))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cincolineaservidor.persistencia.Ranking[ idRanking=" + idRanking + " ]";
    }

}
