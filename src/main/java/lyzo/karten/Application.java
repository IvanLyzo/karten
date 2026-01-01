package lyzo.karten;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {

    // program entry point; calls start method below
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        // prepare stage (window)
        stage.setTitle("Karten");
        stage.setMaximized(true);

        // prepare scene (window content)
        Scene scene = new Scene(new Label("Hello, World!"));

        // add scene
        stage.setScene(scene);

        // show stage
        stage.show();
    }
}
