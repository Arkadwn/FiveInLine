package cincolinea.modelo;

/**
 * Clase que representa la vista logica del tablero del juego 5 en linea.
 * 
 * @author Miguel Leonardo Jimenez Jimenez
 * @author Adrian Bustamate Zarate
 */
public class Tablero {
    private final int tamaño;
    private final String tableroLogico[][];
    private int numeroDeJugadas;
    
    /**
     * Constructor sobrecargado de la clase para crear la matriz del tamaño
     * deseado.
     * 
     * @param tamaño Es el tamaño del tablero que se desea crear.
     */
    public Tablero(int tamaño){
        this.tamaño = tamaño;
        this.tableroLogico = new String[this.tamaño][this.tamaño];
        llenarTableroDe0();
        numeroDeJugadas = 0;
    }
    
    /**
     * Valida si ha llegado al empate
     * 
     * @return Validación de empate.
     */
    public boolean validarEmpate(){
        return numeroDeJugadas == (tamaño*tamaño);
    }
    
    /**
     * Llena la matriz de 0's.
     */
    private void llenarTableroDe0(){
        
        for(int x = 0; x < tamaño; x++){
            
            for(int y =0; y < tamaño; y++){
                tableroLogico[x][y] = "0";
            }
        }
    }
    
    /**
     * Valida si la jugada realizada por el jugador se puede realizar.
     * 
     * @param x Posicion en el ejes de las x del tablero donde realizo el tiro.
     * @param y Posicion en el ejes de las y del tablero donde realizo el tiro.
     * @param ficha Color de la ficha del jugador.
     * @return Validacion de la jugada.
     */
    public boolean validarJugada(int x, int y, String ficha){
        boolean validacion = false;
        
        if("0".equals(tableroLogico[x][y])){
            numeroDeJugadas++;
            tableroLogico[x][y] = ficha;
            validacion = true;
        }
        
        return validacion;
    }
    
    /**
     * Valida si un jugador a ganado.
     * 
     * @param x Posición en el ejes de las x del tablero donde realizó el tiro.
     * @param y Posición en el ejes de las y del tablero donde realizó el tiro.
     * @param colorFicha Color de la ficha del jugador.
     * @return Validacion de ganar.
     */
    public boolean validarSiGano(int x, int y, String colorFicha){
        return validarVertical(x,colorFicha) || validarHorizontal(y, colorFicha) || validarDiagonal(x, y, colorFicha) || validarDiagonalInvertida(x, y, colorFicha);
    }
    
    /**
     * Valida si el jugador a juntado 5 fichas en línea de manera horizontal.
     * 
     * @param y Posición en el ejes de las y del tablero donde realizó el tiro.
     * @param colorFicha Color de la ficha del jugador.
     * @return Validación de ganar horizontalmente.
     */
    private boolean validarHorizontal(int y, String colorFicha){
        
        boolean validacion = false;
        int fichasEnLinea = 0;
        int x = 0;
        
        while(x < tamaño){
            
            if(tableroLogico[x][y].equals(colorFicha)){
                fichasEnLinea++;
            }else{
                fichasEnLinea = 0;
            }
            
            if(fichasEnLinea == 5){
                validacion = true;
                break;
            }
            
            x++;
        }
        return validacion;
    }
    
    /**
     * Valida si el jugador a juntado 5 fichas en línea de manera vertical.
     * 
     * @param x Posición en el ejes de las x del tablero donde realizó el tiro.
     * @param colorFicha Color de la ficha del jugador.
     * @return Validación de ganar verticalmente.
     */
    private boolean validarVertical(int x,String colorFicha){
        boolean validacion = false;
        int fichasEnLinea = 0;
        int y = 0;
        
        while(y < tamaño){
            
            /*Valida si el color de ficha que se encuentra en esas coordenadas
            es igual a la que se acaba de colocar*/
            if(tableroLogico[x][y].equals(colorFicha)){
                fichasEnLinea++;
            }else{
                fichasEnLinea = 0;
            }
            
            if(fichasEnLinea == 5){
                validacion = true;
                break;
            }
            
            y++;
        }
        return validacion;
    }
    
    /**
     * Valida si el jugador a juntado 5 fichas en línea en diagonal.
     * 
     * @param x Posición en el ejes de las x del tablero donde realizó el tiro.
     * @param y Posición en el ejes de las y del tablero donde realizó el tiro.
     * @param colorFicha Color de la ficha del jugador.
     * @return Validación de ganar en diagonal.
     */
    private boolean validarDiagonal(int x, int y, String colorFicha){
        boolean validacion = false;
        int fichasEnlinea = 0;
        
        if(x == y){
            x = 0;
            y = 0;
        }else if (x < y){
            y -= x;
            x -= x;
        }else if (y < x){
            x -= y;
            y -= y;
        }
        
        while(y < tamaño && x < tamaño){
           
           if(tableroLogico[x][y].equals(colorFicha)){
               fichasEnlinea++;
           }else{
               fichasEnlinea = 0;
           }
           
           if(fichasEnlinea == 5){
               validacion = true;
               break;
           }
           
           y++;
           x++;
        }
        return validacion;
    }
    
    /**
     * Valida si el jugador a juntado 5 fichas en línea en diagonal invertida.
     * 
     * @param x Posición en el ejes de las x del tablero donde realizó el tiro.
     * @param y Posición en el ejes de las y del tablero donde realizó el tiro.
     * @param colorFicha Color de la ficha del jugador.
     * @return Validación de ganar en diagonal invertida.
     */
    private boolean validarDiagonalInvertida(int x, int y, String colorFicha){
        boolean validacion = false;
        int fichasEnlinea = 0;
        
        if((x + y) == (tamaño - 1)){
            x = 0;
            y = tamaño - 1;
        }else if ((x + y) < (tamaño - 1)){
            y += x;
            x -= x;
        }else if ((x + y) > (tamaño - 1)){
            x = (x + y) - (tamaño - 1);
            y = tamaño - 1;            
        }
        
        while(x < tamaño && y >= 0){
            
            if (tableroLogico[x][y].equals(colorFicha)) {
                fichasEnlinea++;
            } else {
                fichasEnlinea = 0;
            }

            if (fichasEnlinea == 5) {
                validacion = true;
                break;
            }
            
            x++;
            y--;
        }
        return validacion;
    }
    
}
