package cincolinea.controlador;

import cincolinea.Main;
import cincolinea.modelo.ConfiguracionPartida;
import cincolinea.modelo.utilerias.ConfiguracionIP;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import conexion.ClienteRMI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.stage.WindowEvent;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Controlador de la vista de buscar partida.
 *
 * @author Adrian Bustamante Zarate
 * @author Miguel Leonardo Jiménez Jiménez
 */
public class FXMLBuscaPartidaController implements Initializable {

    @FXML
    private JFXButton btnCancelarBusqPartida;
    private ResourceBundle idioma;
    private Main main;
    private String idUsuario;
    private Socket socket;
    @FXML
    private Label labelJugadores;
    @FXML
    private JFXButton btnJugar;
    @FXML
    private JFXButton btnActualizar;
    @FXML
    private ImageView imgCargando;
    @FXML
    private Label labelBuscandoPartidas;
    @FXML
    private JFXComboBox<String> cbPartidas;
    private String imagenDePerfil;
    @FXML
    private JFXButton imgPerfil;
    javax.swing.Timer temporizador;
    /**
     * Asigna el valor de la imagen de perfil del jugador.
     * 
     * @param imagenDePerfil Es el identificador de la imagen del perfil del
     * usuario.
     */
    public void setImagenDePerfil(String imagenDePerfil) {
        this.imagenDePerfil = imagenDePerfil;
        cargarImagenPerfil();
    }

    /**
     * Coloca los textos en las partes de la vista de acuerdo al idioma elegido.
     */
    private void iniciarIdiomaComponentes() {
        btnCancelarBusqPartida.setText(idioma.getString("btnCancelarBusqPartida"));
        labelBuscandoPartidas.setText(idioma.getString("labelBuscandoP"));
        labelJugadores.setText(idioma.getString("labelJugadores"));
        btnActualizar.setText(idioma.getString("btnActualizar"));
        btnJugar.setText(idioma.getString("btnJugar"));
    }

    /**
     * Setter de la variable idUsuario.
     * 
     * @param idUsuario Es el id del usuario que ha iniciado sesión.
     */
    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public void initialize(URL url, ResourceBundle idioma) {

        this.idioma = idioma;
        if (this.idioma != null) {
            iniciarIdiomaComponentes();
        }
        temporizador = temporizadorConexion();
        temporizador.start();
        try {
            if (socket == null) {
                crearConexionIO();
            }
        } catch (URISyntaxException ex) {
            Logger.getLogger(FXMLBuscaPartidaController.class.getName()).log(Level.SEVERE, null, ex);
        }

        cbPartidas.setVisible(false);
        obtenerPartidasDisponibles();
    }

    /**
     * Metodo que retorna un objeto del tipo Timer, que sirve de temporizador
     * para verificar conexión con el servidor Node.js
     * @return Objeto tipo javax.swing.Timer
     */
    private javax.swing.Timer temporizadorConexion() {

        javax.swing.Timer temporizador = new javax.swing.Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                if (!ConfiguracionIP.verficarConexionSocket()) {
                    Platform.runLater(() -> {
                        MensajeController.mensajeAdvertencia(idioma.getString("errorDeConexionServidor"));
                    });
                    Platform.runLater(() -> {
                        socket.off("jugadores");
                        socket.off("respuestaEmparejamiento");
                        socket.off("respuestaEmparejamientoNegativa");
                        socket.disconnect();
                        main.desplegarMenuPrincipal(idioma, idUsuario);
                    });
                    Thread.currentThread().stop();
                }
            }
        });

        return temporizador;
    }
    
    /**
     * Manda el emit al servidor para solicitar las partidas creadas y 
     * personaliza la vista.
     */
    private void obtenerPartidasDisponibles() {
        imgCargando.setVisible(true);
        cbPartidas.setVisible(false);
        labelBuscandoPartidas.setVisible(true);
        labelJugadores.setVisible(false);
        socket.emit("peticionEnlacePartida");
    }

    /**
     * Crea la conexión con un socket.io y activa los ons para esperar los
     * eventos con su respectivo código para reaccionar a ellos.
     * 
     * @throws URISyntaxException 
     */
    private void crearConexionIO() throws URISyntaxException {

        String[] ipArray = ConfiguracionIP.getIP();
        String ip = ipArray[0] + "." + ipArray[1] + "." + ipArray[2] + "." + ipArray[3];
        socket = IO.socket("http://" + ip + ":8000");

        socket.on("jugadores", new Emitter.Listener() {

            @Override
            public void call(Object... os) {

                JSONArray arrayJugadasDisponibles = (JSONArray) os[0];
                if (arrayJugadasDisponibles.length() != 0) {
                    imgCargando.setVisible(false);
                    labelBuscandoPartidas.setVisible(false);
                }
                ObservableList<String> partidasDisponibles = FXCollections.observableArrayList();
                for (int i = 0; i < arrayJugadasDisponibles.length(); i++) {
                    JSONObject objetoRescatado = arrayJugadasDisponibles.getJSONObject(i);

                    cbPartidas.setVisible(true);
                    labelJugadores.setVisible(true);
                    partidasDisponibles.add(idioma.getString("textoCb") + objetoRescatado.get("idAnfitrion").toString());
                }
                Platform.runLater(() -> {
                    cbPartidas.setItems(partidasDisponibles);
                });

            }
        });

        socket.on("respuestaEmparejamiento", new Emitter.Listener() {
            @Override
            public void call(Object... os) {
                JSONObject configuracionPartida = (JSONObject) os[1];
                ConfiguracionPartida configuracion = new ConfiguracionPartida();
                configuracion.setSocket(socket);
                configuracion.setColorFicha((String) configuracionPartida.get("colorFicha"));
                configuracion.setTamaño((int) configuracionPartida.get("tamaño"));
                configuracion.setImagenPerfilInvitado(configuracionPartida.getString("imagenDePerfil"));
                configuracion.setImagenPerfil(imagenDePerfil);
                configuracion.setEsCreador(false);
                configuracion.setIdContrincante(os[0].toString());
                Platform.runLater(() -> {
                    main.iniciarJuego(idioma, configuracion, idUsuario);
                });

            }
        });
        socket.on("respuestaEmparejamientoNegativa", new Emitter.Listener() {
            @Override
            public void call(Object... os) {
                Platform.runLater(() -> {
                    MensajeController.mensajeInformacion(idioma.getString("mensajePartidaEliminada"));
                    obtenerPartidasDisponibles();
                });

            }
        });

        socket.connect();
    }

    /**
     * Setter de la variable main.
     * 
     * @param main Ventana principal.
     */
    public void setMain(Main main) {
        this.main = main;
        main.getStageLocal().setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                try {
                    ClienteRMI conexion = new ClienteRMI();
                    conexion.activarEstadoSesion(idUsuario);
                } catch (RemoteException | NotBoundException ex) {
                    Logger.getLogger(FXMLMenuPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
                }
                socket.disconnect();
                System.exit((0));
            }
        });
    }

    /**
     * Acción del botón btnJugar.
     * 
     * @param evento El evento cachado por la presión del botón btnJugar.
     */
    @FXML
    private void emparejar(ActionEvent evento) {
        String idContrincante = cbPartidas.getValue();
        if (idContrincante != null) {
            socket.emit("emparejar", idContrincante.substring(11), idUsuario, imagenDePerfil);
        } else {
            MensajeController.mensajeInformacion(idioma.getString("jugarSinPareja"));
        }
    }

    /**
     * Acción del botón btnCancelar.
     * 
     * @param evento El evento cachado por la presión del botón btnCancelar.
     */
    @FXML
    private void regresarMenuPrincipal(ActionEvent evento) {
        socket.off("jugadores");
        socket.off("respuestaEmparejamiento");
        socket.off("respuestaEmparejamientoNegativa");
        socket.disconnect();
        temporizador.stop();
        main.desplegarMenuPrincipal(idioma, idUsuario);
    }

    /**
     * Acción del botón btnActualizar.
     * 
     * @param evento El evento cachado por la presión del botón btnActualizar.
     */
    @FXML
    private void buscarPartidasNuevas(ActionEvent evento) {
        cbPartidas.setVisible(false);
        labelJugadores.setVisible(false);
        imgCargando.setVisible(true);
        labelBuscandoPartidas.setVisible(true);

        obtenerPartidasDisponibles();
    }

    /**
     * Muestra la imagen de perfil del usuario.
     */
    private void cargarImagenPerfil() {
        imgPerfil.setStyle("-fx-background-image: url('cincolinea/imagenes/" + imagenDePerfil + ".jpg" + "');"
                + "-fx-background-position: center center; -fx-background-repeat: stretch; -fx-background-size: 128px 90px 128px 90px;");
    }

}
