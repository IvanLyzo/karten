package lyzo.karten.feature.base;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.Region;
import lyzo.karten.feature.editor.EditorController;
import lyzo.karten.feature.home.HomeController;
import lyzo.karten.feature.library.LibraryController;
import lyzo.karten.feature.play.PlayController;
import lyzo.karten.feature.preferences.PreferencesController;
import lyzo.karten.feature.profile.ProfileController;
import lyzo.karten.feature.side.SideController;
import lyzo.karten.model.AppModel;
import lyzo.karten.model.UserModel;
import lyzo.karten.utility.structures.Controller;

// base controller for the entire application; handles most of the root logic
public class BaseController implements Controller {

    // models
    private final AppModel appModel;
    private final UserModel userModel;

    // region container for side view
    private final ObjectProperty<Region> sideView = new SimpleObjectProperty<>();

    // region container for main view
    private final ObjectProperty<Region> mainView = new SimpleObjectProperty<>();

    public BaseController(AppModel appModel, UserModel userModel) {
        // save models
        this.appModel = appModel;
        this.userModel = userModel;

        // set side view to default controller build view
        sideView.set(new SideController(this::homeAction, this::editorAction, this::libraryAction, this::profileAction, this::preferencesAction).buildView());

        // set main view to default controller build view
        mainView.set(new LibraryController(appModel, this::playAction, this::editorAction).buildView());
    }

    @Override
    public Region buildView() {
        // create a base view builder
        BaseViewBuilder viewBuilder = new BaseViewBuilder(sideView, mainView);

        // display it
        return viewBuilder.build();
    }

    // display play main view
    private void playAction() {
        mainView.set(new PlayController(appModel, this::homeAction).buildView());
    }

    // display home main view
    private void homeAction() {
        mainView.set(new HomeController().buildView());

    }

    // display library main view
    private void libraryAction() {
        mainView.set(new LibraryController(appModel, this::playAction, this::editorAction).buildView());
    }

    // display editor main view
    private void editorAction() {
        mainView.set(new EditorController(appModel).buildView());
    }

    // display profile main view
    private void profileAction() {
        mainView.set(new ProfileController(userModel).buildView());
    }

    // display preferences main view
    private void preferencesAction() {
        mainView.set(new PreferencesController(userModel).buildView());
    }
}