package sample;

import javafx.animation.AnimationTimer;
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

    private BooleanProperty leftPressed = new SimpleBooleanProperty();
    private BooleanProperty rightPressed = new SimpleBooleanProperty();
    private BooleanProperty aPressed = new SimpleBooleanProperty();
    private BooleanProperty dPressed = new SimpleBooleanProperty();

    private BooleanBinding anyPressed = aPressed.or(dPressed.or(leftPressed.or(rightPressed)));

    private Platform pilota;
    private Platform pilota2;
    private GraphicsContext gc;
    private Scene scene;

    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long timestamp) {
            if (leftPressed.get()) {
                pilota.clear(gc);
                pilota.changeDir("left");
                pilota.render(gc);
            }
            if (rightPressed.get()) {
                pilota.clear(gc);
                pilota.changeDir("right");
                pilota.render(gc);
            }
            if (aPressed.get()) {
                pilota2.clear(gc);
                pilota2.changeDir("left");
                pilota2.render(gc);
            }
            if (dPressed.get()) {
                pilota2.clear(gc);
                pilota2.changeDir("right");
                pilota2.render(gc);
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

            if (e.getCode() == KeyCode.D) {
                dPressed.set(true);
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

            if (e.getCode() == KeyCode.D) {
                dPressed.set(false);
            }
        });



    }

    public void setPilota(Platform pilota) {
        this.pilota = pilota;
    }

    public Platform getPilota() {
        return pilota;
    }

    public GraphicsContext getGc() {
        return gc;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void moverPelota() {
        gc = mainCanvas.getGraphicsContext2D();
        gc.setFill(Color.RED);

        pilota = new Platform();
        pilota2 = new Platform();

        pilota.setImage(new Image("sample/platform.png"));
        pilota.setInitialValue(scene.getWidth()/2-pilota.getWidth()/2,scene.getHeight()-pilota.getHeight());
        pilota.render(gc);

        pilota2.setImage(new Image("sample/platform.png"));
        pilota2.setInitialValue(scene.getWidth()/2-pilota.getWidth()/2,0);
        pilota2.render(gc);
    }
}
