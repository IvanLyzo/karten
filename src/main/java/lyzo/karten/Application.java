package lyzo.karten;

import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import lyzo.karten.database.AppDataPath;
import lyzo.karten.database.DBAccess;
import lyzo.karten.feature.base.BaseController;
import lyzo.karten.model.AppModel;
import lyzo.karten.repository.CardRepository;
import lyzo.karten.repository.DeckRepository;
import lyzo.karten.utility.logger.Logger;

import java.nio.file.Path;
import java.util.Objects;

public class Application extends javafx.application.Application {

    // program entry point; calls start method below
    public static void main(String[] args) {
        Logger.setLogLevel(Logger.INFO);
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        // prepare stage (window)
        stage.setTitle("Karten");
        stage.setMaximized(true);

        // create AppData directory
        Path appDataDir = AppDataPath.createAppDir();

        // get scene
        Scene scene = initScene(appDataDir);

        // add scene to window
        stage.setScene(scene);

        // show stage
        stage.show();
    }

    private Scene initScene(Path appDataDir) {
        // create DBAccess object
        DBAccess dbAccess = new DBAccess(appDataDir);

        // somewhere here will eventually create repositories to link the model with db access

        // create app model layer
        AppModel appModel = new AppModel(new DeckRepository(dbAccess), new CardRepository(dbAccess));

        // create the base controller (actually controls everything, application just does set up)
        BaseController controller = new BaseController(appModel);

        // prepare scene (window content)
        Scene scene = new Scene(controller.buildView());

        // add stylesheets to scene
        scene.getStylesheets().add(Objects.requireNonNull(this.getClass().getResource("/styles/k_controls.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(this.getClass().getResource("/styles/k_regions.css")).toExternalForm());

        scene.getStylesheets().add(Objects.requireNonNull(this.getClass().getResource("/styles/theme.css")).toExternalForm());

        // load fonts
        Font.loadFont(Objects.requireNonNull(getClass().getResource("/fonts/PlusJakartaSans-Bold.ttf")).toExternalForm(), 417);
        Font.loadFont(Objects.requireNonNull(getClass().getResource("/fonts/PlusJakartaSans-Regular.ttf")).toExternalForm(), 417);
        Font.loadFont(Objects.requireNonNull(getClass().getResource("/fonts/PlusJakartaSans-Light.ttf")).toExternalForm(), 417);

        // return scene
        return scene;
    }
}
