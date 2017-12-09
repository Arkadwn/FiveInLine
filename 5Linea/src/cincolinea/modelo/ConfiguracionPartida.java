package cincolinea.modelo;

import io.socket.client.Socket;

/**
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

    public String getImagenPerfil() {
        return imagenPerfil;
    }

    public void setImagenPerfil(String imagenPerfil) {
        this.imagenPerfil = imagenPerfil;
    }

    public String getImagenPerfilInvitado() {
        return imagenPerfilInvitado;
    }

    public void setImagenPerfilInvitado(String imagenPerfilInvitado) {
        this.imagenPerfilInvitado = imagenPerfilInvitado;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public boolean isEsCreador() {
        return esCreador;
    }

    public void setEsCreador(boolean esCreador) {
        this.esCreador = esCreador;
    }

    public String getIdContrincante() {
        return idContrincante;
    }

    public void setIdContrincante(String idContrincante) {
        this.idContrincante = idContrincante;
    }
    
    public String getColorFicha() {
        return colorFicha;
    }

    public void setColorFicha(String colorFicha) {
        this.colorFicha = colorFicha;
    }

    public int getTamaño() {
        return tamaño;
    }

    public void setTamaño(int tamaño) {
        this.tamaño = tamaño;
    }
}
