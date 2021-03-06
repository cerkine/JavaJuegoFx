package sample;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Canvas mainCanvas;
    @FXML
    private Text textA, textB, textRight, textLeft, textNueve, textSeis, textV, textQ, textControl;
    @FXML
    private Button btnStart;
    boolean empezar;
    private final int separacion = 10;
    private Image esquina;

    private BooleanProperty leftPressed = new SimpleBooleanProperty();
    private BooleanProperty rightPressed = new SimpleBooleanProperty();

    private BooleanProperty aPressed = new SimpleBooleanProperty();
    private BooleanProperty qPressed = new SimpleBooleanProperty();

    private BooleanProperty vPressed = new SimpleBooleanProperty();
    private BooleanProperty bPressed = new SimpleBooleanProperty();

    private BooleanProperty seisPressed = new SimpleBooleanProperty();
    private BooleanProperty nuevePressed = new SimpleBooleanProperty();

    private Font winner = new Font(20);
    private Font normal = new Font(12);

    private BooleanBinding anyPressed = aPressed.or(qPressed.or(leftPressed.or(rightPressed.or(vPressed.or(bPressed.or(seisPressed.or(nuevePressed)))))));

    private Platform platform;
    private Platform platform2;
    private Platform platform3;
    private Platform platform4;

    private Platform esqDerArri;
    private Platform esqDerAbaj;
    private Platform esqIzqArri;
    private Platform esqIzqAbaj;

    private Pelota pelota1;
    private Pelota pelota2;
    private Pelota pelota3;
    private Pelota pelota4;

    private AudioClip audioClip = new AudioClip(getClass().getClassLoader().getResource("sample/platformcoll.wav").toExternalForm());
    private AudioClip audioClip2 = new AudioClip(getClass().getClassLoader().getResource("sample/ballcoll.wav").toExternalForm());
    private AudioClip audioClip3 = new AudioClip(getClass().getClassLoader().getResource("sample/victory.wav").toExternalForm());

    private GraphicsContext gc;
    private Scene scene;

    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long timestamp) {
            if (leftPressed.get()) {
                platform2.clear(gc);
                if (!(platform2.getPosX() < esquina.getWidth())) {
                    platform2.changeDir("left");
                }
                platform2.render(gc);
            }
            if (rightPressed.get()) {
                platform2.clear(gc);
                if (!(platform2.getPosX() > scene.getWidth() - esquina.getWidth() - platform2.getWidth())) {
                    platform2.changeDir("right");
                }
                platform2.render(gc);
            }
            if (aPressed.get()) {
                platform.clear(gc);
                if (!(platform.getPosY() > scene.getHeight() - esquina.getHeight() - platform.getHeight())) {
                    platform.changeDir("down");
                }
                platform.render(gc);
            }
            if (qPressed.get()) {
                platform.clear(gc);
                if (!(platform.getPosY() < esquina.getHeight())) {
                    platform.changeDir("up");
                }
                platform.render(gc);
            }
            if (seisPressed.get()) {
                platform3.clear(gc);
                if (!(platform3.getPosY() > scene.getHeight() - esquina.getHeight() - platform3.getHeight())) {
                    platform3.changeDir("down");
                }
                platform3.render(gc);
            }
            if (nuevePressed.get()) {
                platform3.clear(gc);
                if (!(platform3.getPosY() < esquina.getHeight())) {
                    platform3.changeDir("up");
                }
                platform3.render(gc);
            }
            if (vPressed.get()) {
                platform4.clear(gc);
                if (!(platform4.getPosX() < esquina.getWidth())) {
                    platform4.changeDir("left");
                }
                platform4.render(gc);
            }
            if (bPressed.get()) {
                platform4.clear(gc);
                if (!(platform4.getPosX() > scene.getWidth() - esquina.getHeight() - platform4.getWidth())) {
                    platform4.changeDir("right");
                }
                platform4.render(gc);
            }

        }

    };

    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.017), new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if (empezar) {

                gc.clearRect(0, 0, scene.getWidth(), scene.getHeight());

                pelota1.clear(gc);
                pelota2.clear(gc);
                pelota3.clear(gc);
                pelota4.clear(gc);
                gc.drawImage(new Image("sample/fondo.png"),0,0,scene.getWidth(),scene.getHeight());
                pelota1.move();
                pelota2.move();
                pelota3.move();
                pelota4.move();
                collisions(pelota1);
                collisions(pelota2);
                collisions(pelota3);
                collisions(pelota4);
                ballToBallCollisions(pelota1, pelota2, pelota3, pelota4);
                pelota1.render(gc);
                pelota2.render(gc);
                pelota3.render(gc);
                pelota4.render(gc);
                platform.render(gc);
                platform2.render(gc);
                platform3.render(gc);
                platform4.render(gc);

                esqIzqArri.render(gc);
                esqDerArri.render(gc);
                esqDerAbaj.render(gc);
                esqIzqAbaj.render(gc);


                puntosPelota(pelota1, pelota2, pelota3, pelota4);
                if (pelota1.isEliminar()) pelota1 = crearPelota(pelota1);
                if (pelota2.isEliminar()) pelota2 = crearPelota(pelota2);
                if (pelota3.isEliminar()) pelota3 = crearPelota(pelota3);
                if (pelota4.isEliminar()) pelota4 = crearPelota(pelota4);
            }
        }
    })
    );

    private void ballToBallCollisions(Pelota pelota1, Pelota pelota2, Pelota pelota3, Pelota pelota4) {
        if (pelota1.getBoundary().intersects(pelota2.getBoundary())) {
            pelota1.changeDir();
            pelota2.changeDir();
            pelota1.render(gc);
            pelota2.render(gc);
            audioClip2.play();
        } else if (pelota1.getBoundary().intersects(pelota3.getBoundary())) {
            pelota1.changeDir();
            pelota3.changeDir();
            pelota1.render(gc);
            pelota3.render(gc);
            audioClip2.play();

        } else if (pelota1.getBoundary().intersects(pelota4.getBoundary())) {
            pelota1.changeDir();
            pelota4.changeDir();
            pelota1.render(gc);
            pelota4.render(gc);
            audioClip2.play();

        } else if (pelota2.getBoundary().intersects(pelota3.getBoundary())) {
            pelota2.changeDir();
            pelota3.changeDir();
            pelota2.render(gc);
            pelota3.render(gc);
            audioClip2.play();

        } else if (pelota2.getBoundary().intersects(pelota4.getBoundary())) {
            pelota2.changeDir();
            pelota4.changeDir();
            pelota2.render(gc);
            pelota4.render(gc);
            audioClip2.play();

        } else if (pelota3.getBoundary().intersects(pelota4.getBoundary())) {
            pelota3.changeDir();
            pelota4.changeDir();
            pelota3.render(gc);
            pelota4.render(gc);
            audioClip2.play();

        }
    }

    private void puntosPelota(Pelota pelota1, Pelota pelota2, Pelota pelota3, Pelota pelota4) {
        gc.setFont(normal);
        int puntosAzul = pelota1.getPuntos()[0] + pelota2.getPuntos()[0] + pelota3.getPuntos()[0] + pelota4.getPuntos()[0];
        int puntosVerde = pelota1.getPuntos()[1] + pelota2.getPuntos()[1] + pelota3.getPuntos()[1] + pelota4.getPuntos()[1];
        int puntosRojo = pelota1.getPuntos()[2] + pelota2.getPuntos()[2] + pelota3.getPuntos()[2] + pelota4.getPuntos()[2];
        int puntosAmarillo = pelota1.getPuntos()[3] + pelota2.getPuntos()[3] + pelota3.getPuntos()[3] + pelota4.getPuntos()[3];

        gc.setFill(Color.BLUE);
        gc.fillText(String.valueOf(puntosAzul), esqIzqAbaj.getPosX() + 10, esqIzqAbaj.getPosY() + 20);

        gc.setFill(Color.GREEN);
        gc.fillText(String.valueOf(puntosVerde), esqDerAbaj.getPosX() + 10, esqDerAbaj.getPosY() + 20);

        gc.setFill(Color.RED);
        gc.fillText(String.valueOf(puntosRojo), esqIzqArri.getPosX() + 10, esqIzqArri.getPosY() + 20);

        gc.setFill(Color.YELLOW);
        gc.fillText(String.valueOf(puntosAmarillo), esqDerArri.getPosX() + 10, esqDerArri.getPosY() + 20);

        gc.setFont(winner);

        if (puntosAzul == 5) {

            gc.setFill(Color.BLUE);
            gc.fillText("BLUE WINS", scene.getWidth() / 2 - 40, scene.getHeight() / 2 + 40);
            timeline.stop();
            timer.stop();
            audioClip3.play();
            empezarJuego();

        } else if (puntosVerde == 5) {
            gc.setFill(Color.GREEN);
            gc.fillText("GREEN WINS", scene.getWidth() / 2 - 40, scene.getHeight() / 2 + 40);
            timeline.stop();
            timer.stop();
            audioClip3.play();
            empezarJuego();


        } else if (puntosRojo == 5) {
            gc.setFill(Color.RED);
            gc.fillText("RED WINS", scene.getWidth() / 2 - 40, scene.getHeight() / 2 + 40);
            timeline.stop();
            timer.stop();
            audioClip3.play();
            empezarJuego();

        } else if (puntosAmarillo == 5) {
            gc.setFill(Color.YELLOW);
            gc.fillText("YELLOW WINS", scene.getWidth() / 2 - 40, scene.getHeight() / 2 - 40);
            timeline.stop();
            timer.stop();
            audioClip3.play();
            empezarJuego();

        }
    }

    private void collisions(Pelota pelota) {
        if (pelota.getBoundary().intersects(platform2.getBoundary())) {
            pelota.setPosY(scene.getHeight() - platform2.getHeight() - pelota.getImage().getHeight() - separacion);
            pelota.render(gc);
            pelota.changeDir("azul");
            pelota.setImage(new Image("sample/ball_azul.png"));
            pelota.setPunto("azul");
            audioClip.play();

        } else if (pelota.getBoundary().intersects(platform4.getBoundary())) {
            pelota.setPosY(platform4.getHeight() + separacion);
            pelota.render(gc);
            pelota.changeDir("amarillo");
            pelota.setImage(new Image("sample/ball_amarillo.png"));
            pelota.setPunto("amarillo");
            audioClip.play();

        } else if (pelota.getBoundary().intersects(platform3.getBoundary())) {
            pelota.setPosX((scene.getWidth() - platform3.getWidth() - pelota.getImage().getHeight() - separacion));
            pelota.render(gc);
            pelota.changeDir("verde");
            pelota.setImage(new Image("sample/ball_verde.png"));
            pelota.setPunto("verde");
            audioClip.play();


        } else if (pelota.getBoundary().intersects(platform.getBoundary())) {
            pelota.setPosX(platform.getWidth() + separacion);
            pelota.render(gc);
            pelota.changeDir("rojo");
            pelota.setImage(new Image("sample/ball_roja.png"));
            pelota.setPunto("rojo");
            audioClip.play();

        } else if (pelota.getBoundary().intersects(esqDerAbaj.getBoundary())) {
            pelota.render(gc);
            pelota.changeDir();
            audioClip2.play();

        } else if (pelota.getBoundary().intersects(esqDerArri.getBoundary())) {
            pelota.render(gc);
            pelota.changeDir();
            audioClip2.play();

        } else if (pelota.getBoundary().intersects(esqIzqAbaj.getBoundary())) {
            pelota.render(gc);
            pelota.changeDir();
            audioClip2.play();

        } else if (pelota.getBoundary().intersects(esqIzqArri.getBoundary())) {
            pelota.render(gc);
            pelota.changeDir();
            audioClip2.play();

        }
    }


    public void setScene(Scene sc) {
        scene = sc;

        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.LEFT) {
                leftPressed.set(true);
            }
            if (e.getCode() == KeyCode.RIGHT) {
                rightPressed.set(true);
            }
            if (e.getCode() == KeyCode.A) {
                aPressed.set(true);
            }
            if (e.getCode() == KeyCode.Q) {
                qPressed.set(true);
            }
            if (e.getCode() == KeyCode.V) {
                vPressed.set(true);
            }
            if (e.getCode() == KeyCode.B) {
                bPressed.set(true);
            }
            if (e.getCode() == KeyCode.NUMPAD6) {
                seisPressed.set(true);
            }
            if (e.getCode() == KeyCode.NUMPAD9) {
                nuevePressed.set(true);
            }


        });
        anyPressed.addListener((obs, wasPressed, isNowPressed) -> {
            if (isNowPressed) {
                if (empezar) timer.start();
            } else {
                timer.stop();
            }
        });

        scene.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.LEFT) {
                leftPressed.set(false);
            }
            if (e.getCode() == KeyCode.RIGHT) {
                rightPressed.set(false);
            }
            if (e.getCode() == KeyCode.A) {
                aPressed.set(false);
            }
            if (e.getCode() == KeyCode.Q) {
                qPressed.set(false);
            }
            if (e.getCode() == KeyCode.V) {
                vPressed.set(false);
            }
            if (e.getCode() == KeyCode.B) {
                bPressed.set(false);
            }
            if (e.getCode() == KeyCode.NUMPAD6) {
                seisPressed.set(false);
            }
            if (e.getCode() == KeyCode.NUMPAD9) {
                nuevePressed.set(false);
            }

        });


    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        empezar = false;

    }

    @FXML
    public void empezarJuego() {

        textA.setVisible(empezar);
        textQ.setVisible(empezar);
        textB.setVisible(empezar);
        textV.setVisible(empezar);
        textSeis.setVisible(empezar);
        textNueve.setVisible(empezar);
        textRight.setVisible(empezar);
        textLeft.setVisible(empezar);
        textControl.setVisible(empezar);
        btnStart.setVisible(empezar);
        this.empezar = !empezar;
        btnStart.setDisable(empezar);

        pelota1 = new Pelota();
        pelota2 = new Pelota();
        pelota3 = new Pelota();
        pelota4 = new Pelota();
        pelota1.setImage(new Image("sample/ball_gris.png"));
        pelota2.setImage(new Image("sample/ball_gris.png"));
        pelota3.setImage(new Image("sample/ball_gris.png"));
        pelota4.setImage(new Image("sample/ball_gris.png"));

        pelota1.setInicio(scene.getWidth() / 2, scene.getHeight() / 2);
        pelota2.setInicio(scene.getWidth() / 3, scene.getHeight() / 3);
        pelota3.setInicio(scene.getWidth() / 2, scene.getHeight() / 3);
        pelota4.setInicio(scene.getWidth() / 3, scene.getHeight() / 2);

        timeline.play();
        timer.start();

    }

    public void controlPlataformas() {
        gc = mainCanvas.getGraphicsContext2D();
        gc.setFill(Color.RED);

        esqDerAbaj = new Platform();
        esqDerArri = new Platform();
        esqIzqAbaj = new Platform();
        esqIzqArri = new Platform();
        esquina = new Image("sample/esquina.png");

        esqIzqArri.setImage(esquina);
        esqIzqAbaj.setImage(esquina);
        esqDerArri.setImage(esquina);
        esqDerAbaj.setImage(esquina);

        esqIzqAbaj.setInitialValue(0, scene.getHeight() - esquina.getHeight());
        esqDerAbaj.setInitialValue(scene.getWidth() - esquina.getWidth(), scene.getHeight() - esquina.getHeight());
        esqDerArri.setInitialValue(scene.getWidth() - esquina.getWidth(), 0);
        esqIzqArri.setInitialValue(0, 0);

        esqIzqAbaj.render(gc);
        esqDerAbaj.render(gc);
        esqDerArri.render(gc);
        esqIzqArri.render(gc);

        platform = new Platform();
        platform2 = new Platform();
        platform3 = new Platform();
        platform4 = new Platform();

        platform.setImage(new Image("sample/rojo.png"));
        platform.setInitialValue(separacion, scene.getHeight() / 2 - platform.getHeight() / 2);
        platform.render(gc);

        platform2.setImage(new Image("sample/azul.png"));
        platform2.setInitialValue(scene.getWidth() / 2 - platform2.getWidth() / 2, scene.getHeight() - platform2.getHeight() - separacion);
        System.out.println(platform2.getHeight());
        platform2.render(gc);

        platform3.setImage(new Image("sample/verde.png"));
        platform3.setInitialValue(scene.getWidth() - platform3.getWidth() - separacion, scene.getHeight() / 2 - platform.getHeight() / 2);
        platform3.render(gc);

        platform4.setImage(new Image("sample/amarillo.png"));
        platform4.setInitialValue(scene.getWidth() / 2 - platform4.getWidth() / 2, separacion);
        platform4.render(gc);


    }

    public void movimientoPelotas() {
        gc = mainCanvas.getGraphicsContext2D();
        gc.setFill(Color.RED);
        pelota1 = new Pelota();
        pelota2 = new Pelota();
        pelota3 = new Pelota();
        pelota4 = new Pelota();
        pelota1.setInicio(scene.getWidth() / 2, scene.getHeight() / 2);
        pelota2.setInicio(scene.getWidth() / 3, scene.getHeight() / 3);
        pelota3.setInicio(scene.getWidth() / 2, scene.getHeight() / 3);
        pelota4.setInicio(scene.getWidth() / 3, scene.getHeight() / 2);


        pelota1.setImage(new Image("sample/ball_gris.png"));
        pelota2.setImage(new Image("sample/ball_gris.png"));
        pelota3.setImage(new Image("sample/ball_gris.png"));
        pelota4.setImage(new Image("sample/ball_gris.png"));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

    }

    private Pelota crearPelota(Pelota pelota) {
        Random random = new Random();
        pelota.setPosX(scene.getWidth() / (random.nextInt(2) + 2));
        pelota.setPosY(scene.getHeight() / (random.nextInt(2) + 2));
        pelota.setImage(new Image("sample/ball_gris.png"));
        if (Math.random() * 10 % 2 < 1) {
            pelota.setVelX(-2);
        } else {
            pelota.setVelX(2);
        }
        if (Math.random() * 10 % 2 < 1) {
            pelota.setVelY(-2);
        } else {
            pelota.setVelY(2);
        }

        pelota.render(gc);
        pelota.setEliminar(false);
        return pelota;
    }

}
