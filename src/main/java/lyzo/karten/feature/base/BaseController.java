package lyzo.karten.feature.base;

import javafx.scene.layout.Region;
import lyzo.karten.feature.interfaces.Controller;

public class BaseController implements Controller {

    @Override
    public Region buildView() {
        BaseViewBuilder viewBuilder = new BaseViewBuilder();

        return viewBuilder.build();
    }
}