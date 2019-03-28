package sample;

import com.sun.javafx.perf.PerformanceTracker;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;


import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML private Canvas mainCanvas;

    //Image pilota;
    private Pilota pilota;
    private GraphicsContext gc;
    private Scene scene;

    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.017), new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event) {
            PerformanceTracker perfTracker = PerformanceTracker.getSceneTracker(mainCanvas.getScene());
            // System.out.println(("FPS (Timeline) = " + perfTracker.getInstantFPS()));

        }
    })
    );

    public void setScene(Scene sc) {
        scene = sc;
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.A) {
                pilota.clear(gc);
                pilota.changeDir("left");
                pilota.render(gc);
            }else if (e.getCode() == KeyCode.D){
                pilota.clear(gc);
                pilota.changeDir("right");
                pilota.render(gc);

            }
        });
//        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                Point2D point = new Point2D(mouseEvent.getX(),mouseEvent.getY());
//                if(pilota.isClicked(point)) pilota.changeDir();
//                System.out.println("click");
//            }
//        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gc = mainCanvas.getGraphicsContext2D();
        gc.setFill(Color.RED);

        pilota = new Pilota();


        pilota.setImage(new Image("sample/ball.png"));
        pilota.render(gc);
//        timeline.setCycleCount(Timeline.INDEFINITE);
//        timeline.play();

    }

}