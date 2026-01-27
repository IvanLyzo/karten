package lyzo.karten.feature.side;

import javafx.scene.layout.Region;
import lyzo.karten.utility.structures.Controller;

// controller for handling side menu action (mostly navigation)
public class SideController implements Controller {

    private final Runnable[] actions;

    public SideController(Runnable... actions) {
        this.actions = actions;
    }

    @Override
    public Region buildView() {
        // create a base view builder
        SideViewBuilder viewBuilder = new SideViewBuilder(actions);

        // display it
        return viewBuilder.build();
    }
}
