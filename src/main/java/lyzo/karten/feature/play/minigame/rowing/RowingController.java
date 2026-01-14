package lyzo.karten.feature.play.minigame.rowing;

import javafx.scene.layout.Region;
import lyzo.karten.utility.interfaces.minigame.MinigameController;

public class RowingController implements MinigameController {

    private final RowingGameState gameState;

    public RowingController(RowingGameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public Region buildView() {
        RowingViewBuilder viewBuilder = new RowingViewBuilder(gameState);

        return viewBuilder.build();
    }
}
