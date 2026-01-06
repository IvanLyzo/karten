package lyzo.karten.feature.empty;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import lyzo.karten.utility.interfaces.ViewBuilder;

public class EmptyViewBuilder implements ViewBuilder {

    @Override
    public Region build() {
        VBox pane = new VBox();
        pane.setAlignment(Pos.CENTER);

        Label emptyMessage = new Label("Nothing to see here yet!");

        pane.getChildren().add(emptyMessage);

        return pane;
    }
}
