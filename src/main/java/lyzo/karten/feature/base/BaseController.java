package lyzo.karten.feature.base;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import lyzo.karten.feature.side.SideController;
import lyzo.karten.model.AppModel;
import lyzo.karten.utility.interfaces.Controller;
import lyzo.karten.utility.logger.Logger;

// base controller for the entire application; handles most of the root logic
public class BaseController implements Controller {

    ObjectProperty<Region> sideView = new SimpleObjectProperty<>();
    ObjectProperty<Region> mainView = new SimpleObjectProperty<>();

    public BaseController(AppModel appModel) {
        sideView.set(new SideController(this::homeAction, this::libraryAction, this::settingsAction).buildView(appModel));
        mainView.set(new SideController(this::homeAction, this::libraryAction, this::settingsAction).buildView(appModel));
    }

    @Override
    public Region buildView(AppModel appModel) {
        // create a base view builder
        BaseViewBuilder viewBuilder = new BaseViewBuilder(sideView, mainView);

        // display it
        return viewBuilder.build();
    }

    private void homeAction(MouseEvent mouseEvent) {
        Logger.getInstance().log("Home action", Logger.NORMAL);
    }

    private void libraryAction(MouseEvent mouseEvent) {
        Logger.getInstance().log("Library action", Logger.NORMAL);
    }

    private void settingsAction(MouseEvent mouseEvent) {
        Logger.getInstance().log("Settings action", Logger.NORMAL);
    }
}