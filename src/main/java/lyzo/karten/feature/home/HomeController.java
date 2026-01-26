package lyzo.karten.feature.home;

import javafx.scene.layout.Region;
import lyzo.karten.utility.structures.Controller;

public class HomeController implements Controller {

    @Override
    public Region buildView() {
        HomeViewBuilder viewBuilder = new HomeViewBuilder();

        return viewBuilder.build();
    }
}
