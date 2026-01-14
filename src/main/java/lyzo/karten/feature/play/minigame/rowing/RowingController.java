package lyzo.karten.feature.play.minigame.rowing;

import javafx.scene.layout.Region;
import lyzo.karten.utility.interfaces.MinigameController;

public class RowingController implements MinigameController {

    @Override
    public Region buildView() {
        RowingViewBuilder viewBuilder = new RowingViewBuilder();

        return viewBuilder.build();
    }
}
