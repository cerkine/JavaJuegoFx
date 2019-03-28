package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private Scene scene;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = fxmlLoader.load();
        Scene sc = new Scene(root);

        Controller controller = fxmlLoader.getController();
        controller.setScene(sc);

        stage.setScene(sc);
        stage.setTitle("Rebota");
        stage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}