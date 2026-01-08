package lyzo.karten.feature.side;

import javafx.scene.layout.Region;
import lyzo.karten.model.AppModel;
import lyzo.karten.utility.interfaces.Controller;

// controller for handling side menu action (mostly navigation)
public class SideController implements Controller {

    @Override
    public Region buildView(AppModel appModel) {
        // create a base view builder
        SideViewBuilder viewBuilder = new SideViewBuilder();

        // display it
        return viewBuilder.build();
    }
}
