package lyzo.karten.feature.play;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.scene.layout.*;
import lyzo.karten.utility.structures.ViewBuilder;
import lyzo.karten.utility.ui.KRegions;

public class PlayViewBuilder implements ViewBuilder {

    private final ObjectProperty<Region> view;

    public PlayViewBuilder(ObjectProperty<Region> view) {
        this.view = view;
    }

    @Override
    public Region build() {
        StackPane playRoot = KRegions.KStackPane("", view.get());

        playRoot.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        VBox.setVgrow(playRoot, Priority.ALWAYS);
        HBox.setHgrow(playRoot, Priority.ALWAYS);

        playRoot.setFocusTraversable(true);

        view.addListener((obs, oldView, newView) -> {
            playRoot.getChildren().setAll(newView);
            Platform.runLater(playRoot::requestFocus);
        });

        return playRoot;
    }
}
