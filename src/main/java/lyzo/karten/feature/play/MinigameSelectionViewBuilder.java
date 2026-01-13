package lyzo.karten.feature.play;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import lyzo.karten.utility.interfaces.ViewBuilder;
import lyzo.karten.utility.ui.KControls;
import lyzo.karten.utility.ui.KRegions;

public class MinigameSelectionViewBuilder implements ViewBuilder {

    @Override
    public Region build() {
        Button minigame1 = KControls.KButton("green-button", KControls.KLabel("heading-shadow", "Minigame 1"), null);
        Button minigame2 = KControls.KButton("green-button", KControls.KLabel("heading-shadow", "Minigame 2"), null);
        Button minigame3 = KControls.KButton("green-button", KControls.KLabel("heading-shadow", "Minigame 3"), null);

        HBox minigameBox = KRegions.KHorizontalBox("", Pos.CENTER, 50, minigame1, minigame2, minigame3);

        return minigameBox;
    }
}
