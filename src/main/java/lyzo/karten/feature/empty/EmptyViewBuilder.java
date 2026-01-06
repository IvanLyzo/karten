package lyzo.karten.feature.empty;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import lyzo.karten.utility.interfaces.ViewBuilder;
import lyzo.karten.utility.ui.KControls;
import lyzo.karten.utility.ui.KRegions;

public class EmptyViewBuilder implements ViewBuilder {

    @Override
    public Region build() {
        Label emptyMessage = KControls.KLabel("", "Nothing to see here yet!");

        VBox pane = KRegions.KVerticalBox("", emptyMessage);
        pane.setAlignment(Pos.CENTER_LEFT);

        return pane;
    }
}
