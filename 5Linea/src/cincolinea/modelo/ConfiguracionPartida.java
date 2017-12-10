package cincolinea.modelo;

import io.socket.client.Socket;

/**
 * Clase que representa la configuración del tablero,
 * 
 * @author Miguel Leonardo Jimenez Jimenez
 * @author Adrian Bustamante Zarate
 */
public class ConfiguracionPartida {
    private String colorFicha;
    private int tamaño;
    private Socket socket;
    private boolean esCreador;
    private String idContrincante;
    private String imagenPerfil;
    private String imagenPerfilInvitado;

    /**
     * Getter de la varible imagenPerfil.
     * 
     * @return imagenPerfil.
     */
    public String getImagenPerfil() {
        return imagenPerfil;
    }

    /**
     * Setter de la varible imagenPerfil.
     * 
     * @param imagenPerfil imagen de perfil del jugador.
     */
    public void setImagenPerfil(String imagenPerfil) {
        this.imagenPerfil = imagenPerfil;
    }

    /**
     * Getter de la varible imagenPerfilInvitado.
     * 
     * @return imagenPerfilInvitado.
     */
    public String getImagenPerfilInvitado() {
        return imagenPerfilInvitado;
    }

    /**
     * Setter de la varible imagenPerfilInvitado.
     * 
     * @param imagenPerfilInvitado imagen de perfil del contrincante.
     */
    public void setImagenPerfilInvitado(String imagenPerfilInvitado) {
        this.imagenPerfilInvitado = imagenPerfilInvitado;
    }

    /**
     * Getter de la varible socket.
     * 
     * @return socket.
     */
    public Socket getSocket() {
        return socket;
    }

    /**
     * Setter de la varible socket.
     * 
     * @param socket socket del jugador.
     */
    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    /**
     * Getter de la varible esCreador.
     * 
     * @return esCreador.
     */
    public boolean isEsCreador() {
        return esCreador;
    }

    /**
     * Setter de la varible esCreador.
     * 
     * @param esCreador es creador el jugador.
     */
    public void setEsCreador(boolean esCreador) {
        this.esCreador = esCreador;
    }

    /**
     * Getter de la varible idContrincante.
     * 
     * @return idContrincante.
     */
    public String getIdContrincante() {
        return idContrincante;
    }

    /**
     * Setter de la varible idContrincante.
     * 
     * @param idContrincante Identificador del contrincante.
     */
    public void setIdContrincante(String idContrincante) {
        this.idContrincante = idContrincante;
    }
    
    /**
     * Getter de la varible colorFicha.
     * 
     * @return coloFicha.
     */
    public String getColorFicha() {
        return colorFicha;
    }

    /**
     * Setter de la varible colorFicha.
     * 
     * @param colorFicha Color de la ficha elegida por el jugador.
     */
    public void setColorFicha(String colorFicha) {
        this.colorFicha = colorFicha;
    }

    /**
     * Getter de la varible tamaño.
     * 
     * @return tamaño.
     */
    public int getTamaño() {
        return tamaño;
    }

    /**
     * Setter de la varible tamaño.
     * 
     * @param tamaño tamaño elegido del tablero.
     */
    public void setTamaño(int tamaño) {
        this.tamaño = tamaño;
    }
}
