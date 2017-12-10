package cincolinea.modelo;

import java.io.Serializable;

/**
 * Clase que representa las caracteriticas del ranking de un jugador.
 * 
 * @author Miguel Leonardo Jimenez Jimenez
 * @author Adrián Bustamante Zarate
 */
public class Ranking implements Serializable{
    private String nombreUsuario;
    private Integer partidasGanadas;
    private Integer partidasPerdidas;
    private Integer partidasEmpatadas;
    private Integer puntos;

    /**
     * Constructor sobrecargado para crear un ranking.
     * 
     * @param nombreUsuario Nombre del usuario al que le pertenece le ranking.
     * @param partidasGanadas Número de paridas ganadas por le usuario.
     * @param partidasPerdidas Número de paridas perdidas por le usuario.
     * @param partidasEmpatadas Número de empatadas ganadas por le usuario.
     * @param puntos Puntos optenidos por el usuario.
     */
    public Ranking(String nombreUsuario, Integer partidasGanadas, Integer partidasPerdidas, Integer partidasEmpatadas, Integer puntos) {
        this.nombreUsuario = nombreUsuario;
        this.partidasGanadas = partidasGanadas;
        this.partidasPerdidas = partidasPerdidas;
        this.partidasEmpatadas = partidasEmpatadas;
        this.puntos = puntos;
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
     * @param nombreUsuario Identificador de la cuenta del usuario.
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * Getter de la varible partidasGanadas.
     * 
     * @return partidasGanadas.
     */
    public Integer getPartidasGanadas() {
        return partidasGanadas;
    }

    /**
     * Setter de la varible partidasGanadas.
     * 
     * @param partidasGanadas Número de partidas ganadas del usuario.
     */
    public void setPartidasGanadas(Integer partidasGanadas) {
        this.partidasGanadas = partidasGanadas;
    }

    /**
     * Getter de la varible partidasPerdidas.
     * 
     * @return partidasPerdidas.
     */
    public Integer getPartidasPerdidas() {
        return partidasPerdidas;
    }

    /**
     * Setter de la varible partidasPerdidas.
     * 
     * @param partidasPerdidas Número de partidas perdidas del usuario.
     */
    public void setPartidasPerdidas(Integer partidasPerdidas) {
        this.partidasPerdidas = partidasPerdidas;
    }

    /**
     * Getter de la varible partidasEmpatadas.
     * 
     * @return partidasEmpatadas
     */
    public Integer getPartidasEmpatadas() {
        return partidasEmpatadas;
    }

    /**
     * Setter de la varible partidasEmpatadas.
     * 
     * @param partidasEmpatadas Número de partidas empatadas del usuario.
     */
    public void setPartidasEmpatadas(Integer partidasEmpatadas) {
        this.partidasEmpatadas = partidasEmpatadas;
    }

    /**
     * Getter de la varible puntos.
     * 
     * @return puntos.
     */
    public Integer getPuntos() {
        return puntos;
    }

    /**
     * Setter de la varible puntos.
     * 
     * @param puntos Número de puntos optenidos por el usuario.
     */
    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }
    
}