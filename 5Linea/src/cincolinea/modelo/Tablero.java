package cincolinea.modelo;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 * @author Adrian Bustamate Zarate
 */
public class Tablero {
    private final int tamaño;
    private final String tableroLogico[][];
    private int numeroDeJugadas;
    
    public Tablero(int tamaño){
        this.tamaño = tamaño;
        this.tableroLogico = new String[this.tamaño][this.tamaño];
        llenarTableroDe0();
        numeroDeJugadas = 0;
    }
    
    public boolean validarEmpate(){
        return numeroDeJugadas == (tamaño*tamaño);
    }
    
    private void llenarTableroDe0(){
        
        for(int x = 0; x < tamaño; x++){
            
            for(int y =0; y < tamaño; y++){
                tableroLogico[x][y] = "0";
            }
        }
    }
    
    public boolean validarJugada(int x, int y, String ficha){
        boolean validacion = false;
        
        if("0".equals(tableroLogico[x][y])){
            numeroDeJugadas++;
            tableroLogico[x][y] = ficha;
            validacion = true;
        }
        
        return validacion;
    }
    
    public boolean validarSiGano(int x, int y, String colorFicha){
        
        return validarVertical(x, y, colorFicha) || validarHorizontal(x, y, colorFicha) || validarDiagonal(x, y, colorFicha) || validarDiagonalInvertida(x, y, colorFicha);
    }
    
    /**
     * 
     * 
     * @param x Posicion en el eje de las x del tablero donde fue colocada la
     * ficha.
     * @param y
     * @param colorFicha
     * @return 
     */
    private boolean validarHorizontal(int x, int y, String colorFicha){
        boolean validacion = false;
        int fichasEnLinea = 1;
        int xOriginal = x;
        x =+ 4;
        
        while(x < tamaño){
            
            if(tableroLogico[x][y].equals(colorFicha)){
                fichasEnLinea++;
            }else{
                break;
            }
            
            if(fichasEnLinea == 5){
                validacion = true;
                System.out.println("gano horizontal derecha");
                break;
            }
            
            x--;
        }
        
        if(!validacion){
            x = xOriginal;
            x -= 4;
            fichasEnLinea = 1;
            while(x >= 0){
                if (tableroLogico[x][y].equals(colorFicha)) {
                    fichasEnLinea++;
                } else {
                    break;
                }

                if (fichasEnLinea == 5) {
                    System.out.println("gano horizontal izquierda");
                    validacion = true;
                    break;
                }
                
                x++;
            }
        }
        
        return validacion;
    }
    
    private boolean validarVertical(int x, int y, String colorFicha){
        boolean validacion = false;
        int fichasEnLinea = 1;
        int yOriginal = y;
        y += 4;
        
        while(y < tamaño){
            
            /*Valida si el color de ficha que se encuentra en esas coordenadas
            es igual a la que se acaba de colocar*/
            if(tableroLogico[x][y].equals(colorFicha)){
                fichasEnLinea++;
            }else{
                break;
            }
            
            if(fichasEnLinea == 5){
                validacion = true;
                System.out.println("gano vertical asi arriva");
                break;
            }
            
            y--;
        }
        
        if(!validacion){
            y = yOriginal;
            fichasEnLinea = 1;
            y -= 4;
            
            while(y >= 0){
                
                if(tableroLogico[x][y].equals(colorFicha)){
                    fichasEnLinea++;
                }else{
                    break;
                }
                
                if(fichasEnLinea == 5){
                    System.out.println("gano vertical asi abajo");
                    validacion = true;
                    break;
                }
                
                y++;
            }
        }
        
        return validacion;
    }
    
    private boolean validarDiagonal(int x, int y, String colorFicha){
        boolean validacion = false;
        int xOriginal = x;
        int yOriginal = y;
        int fichasEnlinea = 1;
        
        x += 4;
        y += 4;
        
        while(y < tamaño && x < tamaño){
           
           if(tableroLogico[x][y].equals(colorFicha)){
               fichasEnlinea++;
           }else{
               break;
           }
           
           if(fichasEnlinea == 5){
               validacion = true;
               System.out.println("gano diagonal asi arriva");
               break;
           }
           
           y--;
           x--;
        }
        
        if(!validacion){
            x = xOriginal;
            y = yOriginal;
            fichasEnlinea = 1;
            
            x -= 4;
            y -= 4;
            
            while(x >= 0 && y >= 0){
                
                if(tableroLogico[x][y].equals(colorFicha)){
                    fichasEnlinea++;
                }else{
                    break;
                }
                
                if(fichasEnlinea == 5){
                    validacion = true;
                    System.out.println("Gano diagonal asi abajo");
                    break;
                }
                
                y++;
                x++;
            }
        }
        
        return validacion;
    }
    
    private boolean validarDiagonalInvertida(int x, int y, String colorFicha){
        boolean validacion = false;
        int xOriginal = x;
        int yOriginal = y;
        int fichasEnlinea = 1;
        
        x += 4;
        y -= 4;
        
        while(x < tamaño && y >= 0){
            
            if (tableroLogico[x][y].equals(colorFicha)) {
                fichasEnlinea++;
            } else {
                break;
            }

            if (fichasEnlinea == 5) {
                validacion = true;
                System.out.println("gano diagonal ivertida asi arriva");
                break;
            }
            
            x--;
            y++;
        }
        
        if(!validacion){
            x = xOriginal;
            y = yOriginal;
            fichasEnlinea = 1;
            
            x -= 4;
            y += 4;
            
            while(x >= 0 && y < tamaño){
                if (tableroLogico[x][y].equals(colorFicha)) {
                    fichasEnlinea++;
                } else {
                    break;
                }

                if (fichasEnlinea == 5) {
                    validacion = true;
                    System.out.println("Gano diagonal invertida asi abajo");
                    break;
                }
                
                x++;
                y--;
            }
        }
        
        return validacion;
    }
    
}
