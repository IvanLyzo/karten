package lyzo.karten.feature.base;

import javafx.scene.layout.Region;
import lyzo.karten.model.AppModel;
import lyzo.karten.utility.interfaces.Controller;

public class BaseController implements Controller {

    @Override
    public Region buildView(AppModel appModel) {
        BaseViewBuilder viewBuilder = new BaseViewBuilder();

        return viewBuilder.build();
    }
}