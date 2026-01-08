package lyzo.karten.feature.home;

import javafx.scene.layout.Region;
import lyzo.karten.model.AppModel;
import lyzo.karten.utility.interfaces.Controller;

public class HomeController implements Controller {

    @Override
    public Region buildView(AppModel appModel) {
        HomeViewBuilder viewBuilder = new HomeViewBuilder();

        return viewBuilder.build();
    }
}
