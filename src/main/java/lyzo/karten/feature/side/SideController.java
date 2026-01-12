package lyzo.karten.feature.side;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import lyzo.karten.utility.interfaces.Controller;

// controller for handling side menu action (mostly navigation)
public class SideController implements Controller {

    private final EventHandler<MouseEvent> homeAction;
    private final EventHandler<MouseEvent> editorAction;
    private final EventHandler<MouseEvent> libraryAction;
    private final EventHandler<MouseEvent> settingsAction;

    public SideController(EventHandler<MouseEvent> homeAction,
                          EventHandler<MouseEvent> editorAction,
                          EventHandler<MouseEvent> libraryAction,
                          EventHandler<MouseEvent> settingsAction) {

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
