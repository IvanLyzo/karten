package lyzo.karten.feature.base;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import lyzo.karten.feature.empty.EmptyViewBuilder;
import lyzo.karten.utility.interfaces.ViewBuilder;

public class BaseViewBuilder implements ViewBuilder {

    @Override
    public Region build() {
        SplitPane root = new SplitPane();
        root.setOrientation(Orientation.HORIZONTAL);

        root.getItems().add(sectionPane(new EmptyViewBuilder().build()));
        root.getItems().add(sectionPane(new EmptyViewBuilder().build()));

        root.setDividerPositions(1.0 / 6);

        return root;
    }

    private Region sectionPane(Region region) {
        StackPane pane = new StackPane(region);

        StackPane.setMargin(pane, new Insets(20, 32, 32, 20));

        return pane;
    }
}
