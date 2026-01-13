package lyzo.karten.feature.play;

import javafx.beans.property.ObjectProperty;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import lyzo.karten.utility.interfaces.ViewBuilder;
import lyzo.karten.utility.ui.KRegions;

public class PlayViewBuilder implements ViewBuilder {

    private final ObjectProperty<Region> view;

    public PlayViewBuilder(ObjectProperty<Region> view) {
        this.view = view;
    }

    @Override
    public Region build() {
        StackPane pane = KRegions.KStackPane("", view.get());
        view.addListener((obs, oldView, newView) -> pane.getChildren().setAll(newView));

        return pane;
    }
}
