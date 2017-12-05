package cincolinea.modelo;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 */
public class Cuenta implements Serializable{
    private static final String PATRON_CORREO = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private String nombre;
    private String apellidos;
    private String nombreUsuario;
    private String correo;
    private String contraseña;
    private String imagen;
    private Integer estadoSesion;

    public Cuenta(){
        
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Cuenta(String nombre, String apellidos, String nombreUsuario, String correo, String contraseña, String imagen) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.nombreUsuario = nombreUsuario;
        this.correo = correo;
        this.contraseña = contraseña;
        this.imagen = imagen;
    }

    public Integer isEstadoSesion() {
        return estadoSesion;
    }

    public void setEstadoSesion(Integer estadoSesion) {
        this.estadoSesion = estadoSesion;
    }

    
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
    
    
    public boolean[] validarCampos(Cuenta cuenta, String confirmacionContraseña){
        boolean[] validaciones = new boolean[7];
        //Nombre
        validaciones[0] = cuenta.getNombre().length() >= 2;
        //Apellidos
        validaciones[1] = cuenta.getApellidos().length() >= 2;
        //NombreUsuario
        validaciones[2] = cuenta.getNombreUsuario().length() > 0;
        //Contraseña concondancia
        validaciones[3] = cuenta.getContraseña().equals(confirmacionContraseña);
        //Contraseña tamaño
        validaciones[4] = cuenta.getContraseña().length() > 8;
        //Correo
        validaciones[5] = Cuenta.validarCorreo(cuenta.getCorreo());
        //Validaciones
        validaciones[6] = validaciones[0] && validaciones[1] && validaciones[2] && validaciones[3] && validaciones[4];
        
        return validaciones;
    }
    
    public static boolean validarCorreo(String email) {
        Pattern patron = Pattern.compile(PATRON_CORREO);
        Matcher concordancia = patron.matcher(email);
        return concordancia.matches();
    }
}
