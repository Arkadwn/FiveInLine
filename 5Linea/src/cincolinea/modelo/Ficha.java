package cincolinea.modelo;

/**
 * Clase que representa las caracteristicas de una ficha que se colocara
 * en el tablero del juego.
 * 
 * @author Miguel Leonardo Jimenez Jimenez
 * @author Adrian Bustamate Zarate
 */
public class Ficha{
    private String colorFicha;
    private int x;
    private int y;
    
    /**
     * Getter de la varible colorFicha.
     * 
     * @return colorFicha.
     */
    public String getColorFicha() {
        return colorFicha;
    }

    /**
     * Setter de la varible colorFicha.
     * 
     * @param colorFicha Color de la ficha que se desea colocar.
     */
    public void setColorFicha(String colorFicha) {
        this.colorFicha = colorFicha;
    }

    /**
     * Getter de la varible x.
     * 
     * @return x.
     */
    public int getX() {
        return x;
    }

    /**
     * Setter de la varible x.
     * 
     * @param x Posición en el ejes de las x.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Getter de la varible y.
     * 
     * @return y.
     */
    public int getY() {
        return y;
    }

    /**
     * Setter de la varible y.
     * 
     * @param y Posición en el ejes de las y.
     */
    public void setY(int y) {
        this.y = y;
    }
    
    
}
