package cincolinea.modelo;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase que contiene la abstracción de la cuenta de un usuario dentro del juego.
 * 
 * @author Miguel Leonardo Jimenez Jimenez
 * @author Adrian Bustamante Zarate
 */
public class Cuenta implements Serializable{
    private static final String PATRON_CORREO = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private String nombre;
    private String apellidos;
    private String nombreUsuario;
    private String correo;
    private String contraseña;
    private String imagen;
    private Integer estadoSesion;
    
    /**
     * Getter de la variable imagen.
     * 
     * @return imagen.
     */
    public String getImagen() {
        return imagen;
    }

    /**
     * Setter de la varible imagen.
     * 
     * @param imagen Identificador de la imagen de perfil de usuario.
     */
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Cuenta() {
        this.nombre = "";
        this.apellidos = "";
        this.nombreUsuario = "";
        this.correo = "";
        this.contraseña = "";
        this.imagen = "";
    }

    /**
     * Contructor sobre cargado para crear una cuenta.
     * 
     * @param nombre Nombre personal del usuario.
     * @param apellidos Apellidos del usuario.
     * @param nombreUsuario Nombre de la cuenta del usuario.
     * @param correo Correo electronico del jugador.
     * @param contraseña Contraseña de la cuenta del usuario.
     * @param imagen Identificador de la imagen del usuario.
     */
    public Cuenta(String nombre, String apellidos, String nombreUsuario, String correo, String contraseña, String imagen) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.nombreUsuario = nombreUsuario;
        this.correo = correo;
        this.contraseña = contraseña;
        this.imagen = imagen;
    }

    /**
     * Getter de la variable estadoSesion..
     * @return estadoSesion.
     */
    public Integer isEstadoSesion() {
        return estadoSesion;
    }

    /**
     * Setter de la variable estado de sesión
     * 
     * @param estadoSesion Estado de la sesión del usuario.
     */
    public void setEstadoSesion(Integer estadoSesion) {
        this.estadoSesion = estadoSesion;
    }
    
    /**
     * Getter de la variable nombre
     * 
     * @return nombre.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Setter de la variable nombre
     * 
     * @param nombre nombre personal de usuario.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Getter de la variable apellidos
     * 
     * @return apellidos
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Setter de la varible apellidos
     * 
     * @param apellidos Apellidos del usuario.
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * Getter de la varible nombre de usuario.
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
     * Getter de la variable correo.
     * 
     * @return correo.
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Setter de la varible correo.
     * 
     * @param correo correo del usuario.
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Getter de la variable contraseña.
     * 
     * @return contraseña.
     */
    public String getContraseña() {
        return contraseña;
    }

    /**
     * Setter de la varible contraseña.
     * 
     * @param contraseña Contraseña de la cuneta del usuario.
     */
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
    
    /**
     * Valida que los datos de la cuenta cumplan con los requerimientos de
     * establecidos por los creadores del juego.
     * 
     * @param cuenta Cuenta de un usuario que desea registrarse.
     * @param confirmacionContraseña Repetición de la contraseña del usuario.
     * @return Validación de los campos de la cuenta.
     */
    public boolean[] validarCampos(Cuenta cuenta, String confirmacionContraseña){
        boolean[] validaciones = new boolean[7];
        /*Nombre personal del usuario mayor a 2 caracteres*/
        validaciones[0] = cuenta.getNombre().length() >= 2;
        /*Apellidos del usuario mayor a dos caracteres*/
        validaciones[1] = cuenta.getApellidos().length() >= 2;
        /*Identificador de la cuneta mayor a un caracter*/
        validaciones[2] = cuenta.getNombreUsuario().length() > 0;
        /*Contraseñas iguales*/
        validaciones[3] = cuenta.getContraseña().equals(confirmacionContraseña);
        /*Tamaño de la contraseña mayor a 8 caracteres*/
        validaciones[4] = cuenta.getContraseña().length() > 8;
        /*Correo valido*/
        validaciones[5] = Cuenta.validarCorreo(cuenta.getCorreo());
        /*¿Campos correctos?*/
        validaciones[6] = validaciones[0] && validaciones[1] && validaciones[2] && validaciones[3] && validaciones[4] && validaciones[5];
        
        return validaciones;
    }
    
    /**
     * Valida que correo del usuario sea valido.
     * 
     * @param email correo del usuario.
     * @return validación de correo.
     */
    public static boolean validarCorreo(String email) {
        Pattern patron = Pattern.compile(PATRON_CORREO);
        Matcher concordancia = patron.matcher(email);
        return concordancia.matches();
    }
}