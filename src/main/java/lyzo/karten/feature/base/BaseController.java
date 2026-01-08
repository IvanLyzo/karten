package lyzo.karten.feature.base;

import javafx.scene.layout.Region;
import lyzo.karten.model.AppModel;
import lyzo.karten.utility.interfaces.Controller;

// base controller for the entire application; handles most of the root logic
public class BaseController implements Controller {

    @Override
    public Region buildView(AppModel appModel) {
        // create a base view builder
        BaseViewBuilder viewBuilder = new BaseViewBuilder();

        // display it
        return viewBuilder.build();
    }
}