package cincolinea.modelo;

import java.io.Serializable;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 */
public class Ranking implements Serializable{
    private String nombreUsuario;
    private Integer partidasGanadas;
    private Integer partidasPerdidas;
    private Integer partidasEmpatadas;
    private Integer puntos;

    public Ranking(String nombreUsuario, Integer partidasGanadas, Integer partidasPerdidas, Integer partidasEmpatadas, Integer puntos) {
        this.nombreUsuario = nombreUsuario;
        this.partidasGanadas = partidasGanadas;
        this.partidasPerdidas = partidasPerdidas;
        this.partidasEmpatadas = partidasEmpatadas;
        this.puntos = puntos;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
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

    public Integer getPartidasEmpatadas() {
        return partidasEmpatadas;
    }

    public void setPartidasEmpatadas(Integer partidasEmpatadas) {
        this.partidasEmpatadas = partidasEmpatadas;
    }

    public Integer getPuntos() {
        return puntos;
    }

    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }
    
}
