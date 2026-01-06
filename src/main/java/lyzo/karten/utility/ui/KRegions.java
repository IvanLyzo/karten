package lyzo.karten.utility.ui;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class KRegions {

    public static VBox KVerticalBox(String className, Node... children) {
        VBox vBox = new VBox();

        vBox.getStyleClass().add(className);
        vBox.getChildren().addAll(children);

        return vBox;
    }
}
