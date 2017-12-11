package cincolinea.controlador;

import cincolinea.Main;
import cincolinea.modelo.ConfiguracionPartida;
import cincolinea.modelo.Ficha;
import cincolinea.modelo.Tablero;
import com.jfoenix.controls.JFXButton;
import conexion.ClienteRMI;
import io.socket.emitter.Emitter;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.WindowEvent;
import org.json.JSONObject;

/**
 * Controlador de la vista Tablero.
 *
 * @author Adrian Bustamante Zarate
 * @author Miguel Leonardo Jimenez Jimenez
 */
public class FXMLTableroController implements Initializable {

    @FXML
    private JFXButton btnAbandonarPartida;
    @FXML
    private Label labelNombreJugador;
    @FXML
    private Label labelNombreContrincante;
    private ResourceBundle idioma;
    private Main main;
    @FXML
    private GridPane tablero;
    private String idUsuario;
    private Tablero tableroLogico;
    private ConfiguracionPartida configuracion;
    @FXML
    private JFXButton imgPerfil;
    @FXML
    private JFXButton imgPerfilContrincante;
    private final String EVENTO_ABANDONO_PARTIDA = "abandonarPartida";

    /**
     * Internacionaliza los componentes de la vista.
     */
    private void inicializarComponentes() {
        btnAbandonarPartida.setText(idioma.getString("btnAbandonarP"));
    }

    /**
     * Acción del botón btnAbandonarPartida.
     *
     * @param evento El evento cachado por la presión del botón
     * btnAbandonarPartida.
     */
    @FXML
    private void abandonarPartida(ActionEvent evento) {
        if (MensajeController.mensajeDesicion(idioma.getString("desicion"), idioma.getString("afirmacion"), idioma.getString("negacion"))) {
            configuracion.getSocket().emit(EVENTO_ABANDONO_PARTIDA, idUsuario);
            configuracion.getSocket().emit("desconectar", idUsuario);
            configuracion.getSocket().disconnect();
            main.desplegarMenuPrincipal(idioma, idUsuario);
        }

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
                configuracion.getSocket().emit(EVENTO_ABANDONO_PARTIDA, idUsuario);
                configuracion.getSocket().emit("desconectar", idUsuario);
                configuracion.getSocket().disconnect();
                System.exit((0));
            }
        });
    }

    /**
     * Carga la configuración del tablero.
     *
     * @param configuracion Configuración del tablero.
     * @param idUsuario Identificador del usuario anfitrión.
     */
    public void setConfiguracionJugador(ConfiguracionPartida configuracion, String idUsuario) {
        this.configuracion = configuracion;
        this.idUsuario = idUsuario;
        tableroLogico = new Tablero(this.configuracion.getTamaño());
        activarEventosDelJuego();

        cargarPerfiles();
        crearTablero(configuracion.getTamaño());
    }

    @Override
    public void initialize(URL url, ResourceBundle idioma) {
        this.idioma = idioma;
        if (this.idioma != null) {
            inicializarComponentes();
        }

    }

    /**
     * Activa los ons para jugar entre dos jugadores.
     */
    private void activarEventosDelJuego() {

        /*Activa el evento para recibir las jugadas del contrincante*/
        configuracion.getSocket().on("jugadaRealizada", new Emitter.Listener() {
            @Override
            public void call(Object... os) {

                JSONObject objeto = (JSONObject) os[0];
                Ficha ficha = new Ficha();
                ficha.setX((Integer) objeto.get("x"));
                ficha.setY((Integer) objeto.get("y"));
                ficha.setColorFicha((String) objeto.get("colorFicha"));

                colocarFichaContrincante(ficha);
            }
        });
        /*Activa el evento que informa si pierde el jugador*/
        configuracion.getSocket().on("perder", new Emitter.Listener() {
            @Override
            public void call(Object... os) {
                Platform.runLater(() -> {
                    MensajeController.mensajeInformacion(idioma.getString("perder"));
                    regresarMenuPrincipal();
                });

            }
        });
        /*Activa el evento que informa si el contrincante abandono la partida*/
        configuracion.getSocket().on("ganarPorAbandono", new Emitter.Listener() {
            @Override
            public void call(Object... os) {
                Platform.runLater(() -> {
                    guardarAbandonoPartida(idUsuario, configuracion.getIdContrincante());
                    MensajeController.mensajeInformacion(idioma.getString("ganarPorAbandono"));
                    regresarMenuPrincipal();
                });

            }
        });
        /*Activa el evento que informa si el los jugadores empataron*/
        configuracion.getSocket().on("empatar", new Emitter.Listener() {
            @Override
            public void call(Object... os) {
                Platform.runLater(() -> {
                    MensajeController.mensajeInformacion(idioma.getString("empate"));
                    regresarMenuPrincipal();
                });

            }
        });
    }

    /**
     * Acción de un botón del tablero.
     *
     * @param evento Evento de presionar un botón del tablero.
     */
    @FXML
    private void retornarCoordenadas(ActionEvent evento) {

        JFXButton boton = (JFXButton) evento.getSource();

        Ficha ficha = crearFicha(boton.getId());

        if (tableroLogico.validarJugada(ficha.getX(), ficha.getY(), ficha.getColorFicha())) {
            colocarFicha(boton, configuracion.getColorFicha());

            JSONObject fichaIncriptada = new JSONObject(ficha);

            configuracion.getSocket().emit("realizarJugada", fichaIncriptada, configuracion.getIdContrincante(), !configuracion.isEsCreador());

            tablero.setDisable(true);

            if (tableroLogico.validarSiGano(ficha.getX(), ficha.getY(), ficha.getColorFicha())) {
                configuracion.getSocket().emit("ganar", idUsuario);
                guardarResultado(idUsuario, configuracion.getIdContrincante());
                MensajeController.mensajeInformacion(idioma.getString("ganar"));
                regresarMenuPrincipal();
            }

            if (tableroLogico.validarEmpate()) {
                configuracion.getSocket().emit("empate", idUsuario);
                guardarEmpate(idUsuario, configuracion.getIdContrincante());
                MensajeController.mensajeInformacion(idioma.getString("empate"));
                regresarMenuPrincipal();
            }
        }
    }

    /**
     * Despliega la ventana del menú principal y desconecta socket.io
     */
    private void regresarMenuPrincipal() {
        configuracion.getSocket().emit("desconectar", idUsuario);
        apagarOns();
        configuracion.getSocket().disconnect();
        main.desplegarMenuPrincipal(idioma, idUsuario);
    }

    /**
     * Coloca la ficha en el tablero.
     *
     * @param boton Referencia al boton que se presiono en el tablero.
     * @param colorFicha Color de la ficha del jugador.
     */
    private void colocarFicha(JFXButton boton, String colorFicha) {
        String estilo = boton.getStyle();
        boton.setStyle("-fx-background-image: url('cincolinea/imagenes/" + colorFicha + ".png');"
                + estilo + " -fx-background-position: center center; -fx-background-repeat: "
                + "stretch; -fx-background-size: 39px 39px 39px 39px;");

    }

    /**
     * Crea un objeto ficha y saca las coordenadas de la ficha.
     *
     * @param coordenadas Cadena con las cadenas de la ficha.
     * @return Ficha donde se realizo la jugada.
     */
    private Ficha crearFicha(String coordenadas) {
        Ficha ficha = new Ficha();

        String[] divicion = coordenadas.split("-");

        ficha.setX(Integer.parseInt(divicion[0]));
        ficha.setY(Integer.parseInt(divicion[1]));
        ficha.setColorFicha(configuracion.getColorFicha());

        return ficha;
    }

    /**
     * Coloca la jugada del contrincante en el tablero.
     *
     * @param ficha Ficha de la jugada del tablero.
     */
    private void colocarFichaContrincante(Ficha ficha) {
        if (tableroLogico.validarJugada(ficha.getX(), ficha.getY(), ficha.getColorFicha())) {
            int posicion = Integer.parseInt("0" + ficha.getY() + ficha.getX());
            JFXButton boton = (JFXButton) tablero.getChildren().get(posicion);

            colocarFicha(boton, ficha.getColorFicha());

            tablero.setDisable(false);
        }

    }

    /**
     * Modifica el tablero de la vista de acuerdo al tamaño de la configuración.
     *
     * @param tamaño Tamaño del tablero seleccionado.
     */
    private void crearTablero(int tamaño) {
        if (configuracion.getColorFicha().equals("B")) {
            tablero.setDisable(true);
        }
        switch (tamaño) {
            case 8:
                crearTableroTamaño8();
                break;
            case 9:
                crearTableroTamaño9();
                break;
        }
    }

    /**
     * Crea la conexón con RMI para hacer referencia a guardar resultado.
     *
     * @param ganador Identificador del jugador ganador.
     * @param perdedor Identificador del jugador perdedor.
     */
    private void guardarResultado(String ganador, String perdedor) {
        ClienteRMI conexion;
        try {
            conexion = new ClienteRMI();
            conexion.guardarResultadosPardida(ganador, perdedor);
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(FXMLTableroController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Crea la conexón con RMI para hacer referencia a guardar empate.
     *
     * @param jugador1 Identificador del jugador anfitrión.
     * @param jugador2 Identificador del jugador invitado.
     */
    private void guardarEmpate(String jugador1, String jugador2) {
        ClienteRMI conexion;
        try {
            conexion = new ClienteRMI();
            conexion.guardarEmpate(jugador1, jugador2);
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(FXMLTableroController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Crea la conexón con RMI para hacer referencia a guardar abandono de
     * partida.
     *
     * @param ganador Identificador del jugador ganador.
     * @param desertor Idenfitificador del jugador que abandono partida.
     */
    private void guardarAbandonoPartida(String ganador, String desertor) {
        ClienteRMI conexion;
        try {
            conexion = new ClienteRMI();
            conexion.guardarResultadosPardida(ganador, desertor);
            conexion.aplicarCastigo(desertor);
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(FXMLTableroController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Desactiva todos los ons creados para jugar.
     */
    private void apagarOns() {
        configuracion.getSocket().off("empatar");
        configuracion.getSocket().off("ganarPorAbandono");
        configuracion.getSocket().off("perder");
        configuracion.getSocket().off("jugadaRealizada");
    }

    /**
     * Carga las imagenes y los nombres de los jugadores.
     */
    private void cargarPerfiles() {
        labelNombreContrincante.setText(configuracion.getIdContrincante());
        labelNombreJugador.setText(this.idUsuario);
        imgPerfil.setStyle("-fx-background-image: url('cincolinea/imagenes/" + configuracion.getImagenPerfil() + ".jpg" + "');"
                + "-fx-background-position: center center; -fx-background-repeat: stretch; -fx-background-size: 113px 105px 113px 105px;");
        imgPerfilContrincante.setStyle("-fx-background-image: url('cincolinea/imagenes/" + configuracion.getImagenPerfilInvitado() + ".jpg" + "');"
                + "-fx-background-position: center center; -fx-background-repeat: stretch; -fx-background-size: 113px 105px 113px 105px;");
    }
    
    private void crearTableroTamaño8(){
        int i;
        JFXButton boton;
        for (i = 9; i < 100; i += 10) {
                    boton = (JFXButton) tablero.getChildren().get(i);
                    boton.setVisible(false);

                    if (i == 79) {
                        break;
                    }
                }

                for (i = 8; i < 100; i += 10) {
                    boton = (JFXButton) tablero.getChildren().get(i);
                    boton.setVisible(false);

                    if (i == 78) {
                        break;
                    }
                }

                for (i = 90; i < 100; i++) {
                    boton = (JFXButton) tablero.getChildren().get(i);
                    boton.setVisible(false);
                }

                for (i = 80; i < 90; i++) {
                    boton = (JFXButton) tablero.getChildren().get(i);
                    boton.setVisible(false);
                }
    }
    
    private void crearTableroTamaño9(){
        int i;
        JFXButton boton;
        for (i = 9; i < 100; i += 10) {
                    boton = (JFXButton) tablero.getChildren().get(i);
                    boton.setVisible(false);

                    if (i == 89) {
                        break;
                    }
                }

                for (i = 90; i < 100; i++) {
                    boton = (JFXButton) tablero.getChildren().get(i);
                    boton.setVisible(false);
                }
    }
}
