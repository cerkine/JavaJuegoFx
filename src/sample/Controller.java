package sample;

import com.sun.javafx.perf.PerformanceTracker;
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
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML private Canvas mainCanvas;

    private BooleanProperty leftPressed = new SimpleBooleanProperty();
    private BooleanProperty rightPressed = new SimpleBooleanProperty();
    private BooleanProperty aPressed = new SimpleBooleanProperty();
    private BooleanProperty dPressed = new SimpleBooleanProperty();

    private BooleanBinding anyPressed = aPressed.or(dPressed.or(leftPressed.or(rightPressed)));

    private Platform platform;
    private Platform platform2;
    private Pelota pelota;
    private GraphicsContext gc;
    private Scene scene;

    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long timestamp) {
            if (leftPressed.get()) {
                platform.clear(gc);
                platform.changeDir("left");
                platform.render(gc);
            }
            if (rightPressed.get()) {
                platform.clear(gc);
                platform.changeDir("right");
                platform.render(gc);
            }
            if (aPressed.get()) {
                platform2.clear(gc);
                platform2.changeDir("left");
                platform2.render(gc);
            }
            if (dPressed.get()) {
                platform2.clear(gc);
                platform2.changeDir("right");
                platform2.render(gc);
            }
        }
    };

    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.017), new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event) {
            PerformanceTracker perfTracker = PerformanceTracker.getSceneTracker(mainCanvas.getScene());
            // System.out.println(("FPS (Timeline) = " + perfTracker.getInstantFPS()));
            pelota.clear(gc);
            pelota.move();
            if (pelota.getBoundary().intersects(platform.getBoundary())){
                pelota.changeDir();
            }
            else if (pelota.getBoundary().intersects(platform2.getBoundary())){
                pelota.changeDir();
            }
            pelota.render(gc);

        }
    })
    );


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

        platform.setImage(new Image("sample/platform.png"));
        platform.setInitialValue(scene.getWidth()/2- platform.getWidth()/2,scene.getHeight()- platform.getHeight());
        platform.render(gc);

        platform2.setImage(new Image("sample/platform2.png"));
        platform2.setInitialValue(scene.getWidth()/2- platform.getWidth()/2,0);
        platform2.render(gc);
    }

    public void movimientoPelotas(){
        gc = mainCanvas.getGraphicsContext2D();
        gc.setFill(Color.RED);

        pelota = new Pelota();

        pelota.setImage(new Image("sample/ball.png"));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

    }
}
