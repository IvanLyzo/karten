package lyzo.karten.feature.play.minigame.rowing;

import javafx.scene.layout.Region;
import lyzo.karten.utility.structures.ViewBuilder;
import lyzo.karten.utility.ui.KControls;

public class RowingResultViewBuilder implements ViewBuilder {

    private final boolean won;

    public RowingResultViewBuilder(boolean won) {
        this.won = won;
    }

    @Override
    public Region build() {
        return KControls.KLabel("heading", "you won: " + won);
    }
}
