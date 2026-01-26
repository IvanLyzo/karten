package lyzo.karten.feature.base;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.Region;
import lyzo.karten.feature.editor.EditorController;
import lyzo.karten.feature.home.HomeController;
import lyzo.karten.feature.library.LibraryController;
import lyzo.karten.feature.play.PlayController;
import lyzo.karten.feature.side.SideController;
import lyzo.karten.model.AppModel;
import lyzo.karten.utility.structures.Controller;
import lyzo.karten.utility.logger.Logger;

// base controller for the entire application; handles most of the root logic
public class BaseController implements Controller {

    // application model
    private final AppModel appModel;

    // region container for side view
    private final ObjectProperty<Region> sideView = new SimpleObjectProperty<>();

    // region container for main view
    private final ObjectProperty<Region> mainView = new SimpleObjectProperty<>();

    public BaseController(AppModel appModel) {
        // save appModel
        this.appModel = appModel;

        // set side view to default controller build view
        sideView.set(new SideController(this::homeAction, this::editorAction, this::libraryAction, this::settingsAction).buildView());
        Logger.log("Set sideView to SideController.build()", Logger.INFO);

        // set main view to default controller build view
        mainView.set(new HomeController().buildView());
        Logger.log("Set mainView to SideController.build()", Logger.INFO);
    }

    @Override
    public Region buildView() {
        // create a base view builder
        BaseViewBuilder viewBuilder = new BaseViewBuilder(sideView, mainView);
        Logger.log("Created base viewBuilder with side view: " + sideView + ", main view: " + mainView + ".", Logger.INFO);

        // display it
        return viewBuilder.build();
    }

    // display play main view
    private void playAction() {
        Logger.log("play action, set main view to play controller", Logger.INFO);
        mainView.set(new PlayController(appModel, this::homeAction).buildView());
    }

    // display home main view
    private void homeAction() {
        Logger.log("Home action, set main view to home controller build view", Logger.INFO);
        mainView.set(new HomeController().buildView());

    }

    // display library main view
    private void libraryAction() {
        Logger.log("Library action, set main view to library controller build view", Logger.INFO);
        mainView.set(new LibraryController(appModel, this::playAction, this::editorAction).buildView());
    }

    // display editor main view
    private void editorAction() {
        Logger.log("Editor action, set main view to editor controller build view based on active deck", Logger.INFO);
        mainView.set(new EditorController(appModel).buildView());
    }

    // display settings main view (NOT IMPLEMENTED)
    private void settingsAction() {
        Logger.log("Settings action", Logger.INFO);
    }
}