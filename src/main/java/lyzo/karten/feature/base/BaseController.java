package lyzo.karten.feature.base;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import lyzo.karten.feature.home.HomeController;
import lyzo.karten.feature.library.LibraryController;
import lyzo.karten.feature.side.SideController;
import lyzo.karten.model.AppModel;
import lyzo.karten.utility.interfaces.Controller;
import lyzo.karten.utility.logger.Logger;

// base controller for the entire application; handles most of the root logic
public class BaseController implements Controller {

    private final AppModel appModel;

    // region container for side view
    private final ObjectProperty<Region> sideView = new SimpleObjectProperty<>();

    // region container for main view
    private final ObjectProperty<Region> mainView = new SimpleObjectProperty<>();

    public BaseController(AppModel appModel) {
        // save appModel
        this.appModel = appModel;

        // set side view to default controller build view
        sideView.set(new SideController(this::homeAction, this::libraryAction, this::settingsAction).buildView(appModel));
        Logger.log("Set sideView to SideController.build()", Logger.INFO);

        // set main view to default controller build view
        mainView.set(new SideController(this::homeAction, this::libraryAction, this::settingsAction).buildView(appModel));
        Logger.log("Set mainView to SideController.build()", Logger.INFO);
    }

    @Override
    public Region buildView(AppModel appModel) {
        // create a base view builder
        BaseViewBuilder viewBuilder = new BaseViewBuilder(sideView, mainView);
        Logger.log("Created base viewBuilder with side view: " + sideView + ", main view: " + mainView + ".", Logger.INFO);

        // display it
        return viewBuilder.build();
    }

    private void homeAction(MouseEvent mouseEvent) {
        Logger.log("Home action, set main view to home controller build view", Logger.INFO);
        mainView.set(new HomeController().buildView(appModel));

    }

    private void libraryAction(MouseEvent mouseEvent) {
        Logger.log("Library action, set main view to library controller build view", Logger.INFO);
        mainView.set(new LibraryController().buildView(appModel));
    }

    private void settingsAction(MouseEvent mouseEvent) {
        Logger.log("Settings action", Logger.INFO);
    }
}