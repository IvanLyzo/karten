package lyzo.karten.feature.preferences;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import lyzo.karten.utility.structures.ViewBuilder;
import lyzo.karten.utility.ui.KControls;
import lyzo.karten.utility.ui.KRegions;

public class PreferencesViewBuilder implements ViewBuilder {

    @Override
    public Region build() {
        Label title = KControls.KLabel("heading", "preferences go here");

        VBox container = KRegions.KVerticalBox("", Pos.TOP_LEFT, 50, title);

        return container;
    }
}
