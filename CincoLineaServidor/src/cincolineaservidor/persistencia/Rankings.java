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
 * Entidad que hace referencia a la tabla de la base de datos Rankings.
 * 
 * @author Adrián Bustamante Zarate
 * @author Miguel Leonardo Jimenez Jimenez
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

    /**
     * Constructor por defecto.
     */
    public Rankings() {
    }

    /**
     * Constructor sobrecargado.
     * 
     * @param idRanking Identificador del ranking.
     */
    public Rankings(Integer idRanking) {
        this.idRanking = idRanking;
    }

    /**
     * Getter de la varible idRanking.
     * 
     * @return idRanking.
     */
    public Integer getIdRanking() {
        return idRanking;
    }

    /**
     * Setter de la variable idRanking.
     * 
     * @param idRanking Identificador del ranking.
     */
    public void setIdRanking(Integer idRanking) {
        this.idRanking = idRanking;
    }

    /**
     * Getter de la varible partidasGanadas. 
     * 
     * @return partidas ganadas.
     */
    public Integer getPartidasGanadas() {
        return partidasGanadas;
    }

    /**
     * Setter de la variable partidasGanadas.
     * 
     * @param partidasGanadas partidas ganadas.
     */
    public void setPartidasGanadas(Integer partidasGanadas) {
        this.partidasGanadas = partidasGanadas;
    }

    /**
     * Getter de la variable partidasPerdidas.
     * 
     * @return partida perdidas.
     */
    public Integer getPartidasPerdidas() {
        return partidasPerdidas;
    }

    /**
     * Setter de la variable partidasPerdidas.
     * 
     * @param partidasPerdidas partidas perdidas
     */
    public void setPartidasPerdidas(Integer partidasPerdidas) {
        this.partidasPerdidas = partidasPerdidas;
    }

    /**
     * Getter de la variable puntos.
     * 
     * @return puntos.
     */
    public Integer getPuntos() {
        return puntos;
    }

    /**
     * Setter de la variable puntos.
     * 
     * @param puntos puntos.
     */
    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }

    /**
     * Getter de la variable partidasEmpatadas.
     * 
     * @return partidasEmpatadas
     */
    public Integer getPartidasEmpatadas() {
        return partidasEmpatadas;
    }

    /**
     * Setter de la variable partidasEmpatadas.
     * 
     * @param partidasEmpatadas partidasEmpatadas
     */
    public void setPartidasEmpatadas(Integer partidasEmpatadas) {
        this.partidasEmpatadas = partidasEmpatadas;
    }

    /**
     * Getter de la variable nombreUsuario.
     * 
     * @return nombreUsuario.
     */
    public Cuentas getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * Setter de la variable nombreUsuario.
     * 
     * @param nombreUsuario nombreUsuario.
     */
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
        return !((this.idRanking == null && other.idRanking != null) || (this.idRanking != null && !this.idRanking.equals(other.idRanking)));
    }

    @Override
    public String toString() {
        return "cincolineaservidor.persistencia.Rankings[ idRanking=" + idRanking + " ]";
    }

}
