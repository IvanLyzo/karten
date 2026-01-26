package lyzo.karten.feature.side;

import javafx.scene.layout.Region;
import lyzo.karten.utility.structures.Controller;

// controller for handling side menu action (mostly navigation)
public class SideController implements Controller {

    private final Runnable homeAction;
    private final Runnable editorAction;
    private final Runnable libraryAction;
    private final Runnable settingsAction;

    public SideController(Runnable homeAction,
                          Runnable editorAction,
                          Runnable libraryAction,
                          Runnable settingsAction) {

        // save passed-down events
        this.homeAction = homeAction;
        this.editorAction = editorAction;
        this.libraryAction = libraryAction;
        this.settingsAction = settingsAction;
    }

    @Override
    public Region buildView() {
        // create a base view builder
        SideViewBuilder viewBuilder = new SideViewBuilder(homeAction, editorAction, libraryAction, settingsAction);

        // display it
        return viewBuilder.build();
    }
}
