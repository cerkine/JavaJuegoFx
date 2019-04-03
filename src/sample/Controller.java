package sample;

import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML private Canvas mainCanvas;
    final int separacion = 10;

    private BooleanProperty leftPressed = new SimpleBooleanProperty();
    private BooleanProperty rightPressed = new SimpleBooleanProperty();

    private BooleanProperty aPressed = new SimpleBooleanProperty();
    private BooleanProperty qPressed = new SimpleBooleanProperty();

    private BooleanProperty vPressed = new SimpleBooleanProperty();
    private BooleanProperty bPressed = new SimpleBooleanProperty();

    private BooleanProperty seisPressed = new SimpleBooleanProperty();
    private BooleanProperty nuevePressed = new SimpleBooleanProperty();


    private BooleanBinding anyPressed = aPressed.or(qPressed.or(leftPressed.or(rightPressed.or(vPressed.or(bPressed.or(seisPressed.or(nuevePressed)))))));

    private Platform platform;
    private Platform platform2;
    private Platform platform3;
    private Platform platform4;

    private Pelota pelota;
    private GraphicsContext gc;
    private Scene scene;

    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long timestamp) {
            if (leftPressed.get()) {
                platform2.clear(gc);
                platform2.changeDir("left");
                platform2.render(gc);
            }
            if (rightPressed.get()) {
                platform2.clear(gc);
                platform2.changeDir("right");
                platform2.render(gc);
            }
            if (aPressed.get()) {
                platform.clear(gc);
                platform.changeDir("down");
                platform.render(gc);
            }
            if (qPressed.get()) {
                platform.clear(gc);
                platform.changeDir("up");
                platform.render(gc);
            }
            if (seisPressed.get()) {
                platform3.clear(gc);
                platform3.changeDir("down");
                platform3.render(gc);
            }
            if (nuevePressed.get()) {
                platform3.clear(gc);
                platform3.changeDir("up");
                platform3.render(gc);
            }
            if (vPressed.get()) {
                platform4.clear(gc);
                platform4.changeDir("left");
                platform4.render(gc);
            }
            if (bPressed.get()) {
                platform4.clear(gc);
                platform4.changeDir("right");
                platform4.render(gc);
            }
        }
    };


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
                timer.start();
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

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public Platform getPlatform() {
        return platform;
    }

    public GraphicsContext getGc() {
        return gc;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void controlPlataformas() {
        gc = mainCanvas.getGraphicsContext2D();
        gc.setFill(Color.RED);

        platform = new Platform();
        platform2 = new Platform();
        platform3 = new Platform();
        platform4 = new Platform();

        platform.setImage(new Image("sample/platform.png"));
        platform.setInitialValue(separacion,scene.getHeight()/2-platform.getHeight()/2);
        platform.render(gc);

        platform2.setImage(new Image("sample/platform2.png"));
        platform2.setInitialValue(scene.getWidth()/2- platform2.getWidth()/2,scene.getHeight()-platform2.getHeight()-separacion);
        System.out.println(platform2.getHeight());
        platform2.render(gc);

        platform3.setImage(new Image("sample/platform3.png"));
        platform3.setInitialValue(scene.getWidth()-platform3.getWidth()-separacion,scene.getHeight()/2-platform.getHeight()/2);
        platform3.render(gc);

        platform4.setImage(new Image("sample/platform4.png"));
        platform4.setInitialValue(scene.getWidth()/2- platform4.getWidth()/2,separacion);
        platform4.render(gc);
    }

    public void movimientoPelotas(){
        gc = mainCanvas.getGraphicsContext2D();
        gc.setFill(Color.RED);

        pelota = new Pelota();

        pelota.setImage(new Image("img/ball.png"));

    }
}
