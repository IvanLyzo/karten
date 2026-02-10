package lyzo.karten;

import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import lyzo.karten.handler.ProfileHandler;
import lyzo.karten.handler.ThemeHandler;
import lyzo.karten.io.disk.AppDataPath;
import lyzo.karten.io.disk.DBAccess;
import lyzo.karten.feature.base.BaseController;
import lyzo.karten.io.disk.FileAccess;
import lyzo.karten.model.AppModel;
import lyzo.karten.model.UserModel;
import lyzo.karten.repository.CardRepository;
import lyzo.karten.repository.DeckRepository;
import lyzo.karten.utility.logger.Logger;
import lyzo.karten.utility.ui.KControls;

import java.nio.file.Path;
import java.util.Objects;

public class Application extends javafx.application.Application {

    private final Scene applicationSurface = new Scene(KControls.KLabel("heading", "Processing..."));

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
        initScene(appDataDir);

        // add scene to window
        stage.setScene(applicationSurface);

        // show stage
        stage.show();
    }

    private void initScene(Path appDataDir) {
        // create DBAccess object
        DBAccess dbAccess = new DBAccess(appDataDir);

        // create FileAccess object
        FileAccess fileAccess = new FileAccess(appDataDir);

        // create app model layer
        AppModel appModel = new AppModel(new DeckRepository(dbAccess), new CardRepository(dbAccess));

        // create user model layer
        UserModel userModel = new UserModel(new ProfileHandler(fileAccess), new ThemeHandler(fileAccess, this::addStylesheet));

        // create the base controller (actually controls everything, application just does set up)
        BaseController controller = new BaseController(appModel, userModel);

        // prepare scene (window content)
        applicationSurface.setRoot(controller.buildView());

        // add stylesheets to scene
        applicationSurface.getStylesheets().add(Objects.requireNonNull(this.getClass().getResource("/styles/k_controls.css")).toExternalForm());
        applicationSurface.getStylesheets().add(Objects.requireNonNull(this.getClass().getResource("/styles/k_regions.css")).toExternalForm());

        // load fonts
        Font.loadFont(Objects.requireNonNull(getClass().getResource("/fonts/PlusJakartaSans-Bold.ttf")).toExternalForm(), 417);
        Font.loadFont(Objects.requireNonNull(getClass().getResource("/fonts/PlusJakartaSans-Regular.ttf")).toExternalForm(), 417);
        Font.loadFont(Objects.requireNonNull(getClass().getResource("/fonts/PlusJakartaSans-Light.ttf")).toExternalForm(), 417);
    }

    private void addStylesheet(String oldCSS, String newCSS) {
        applicationSurface.getStylesheets().removeIf(s -> s.equals(oldCSS));
        applicationSurface.getStylesheets().add(newCSS);
    }
}
