package lyzo.karten.utility.ui;

import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class KRegions {

    public static VBox KVerticalBox(String className, Node... children) {
        VBox vBox = new VBox();

        vBox.getStyleClass().add(className);
        vBox.getChildren().addAll(children);

        return vBox;
    }

    public static StackPane KStackPane(String className, Region region) {
        StackPane pane = new StackPane(region);

        pane.getStyleClass().add(className);

        return pane;
    }
}
