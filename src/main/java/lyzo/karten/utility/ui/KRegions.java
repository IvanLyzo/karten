package lyzo.karten.utility.ui;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.*;

public class KRegions {

    public static VBox KVerticalBox(String className, Pos alignment, Node... children) {
        VBox vBox = new VBox();

        vBox.getStyleClass().add(className);
        vBox.setAlignment(alignment);

        vBox.getChildren().addAll(children);

        return vBox;
    }

    public static HBox KHorizontalBox(String className, Pos alignment, Node... children) {
        HBox hBox = new HBox();

        hBox.getStyleClass().add(className);
        hBox.setAlignment(alignment);

        hBox.getChildren().addAll(children);

        return hBox;
    }

    public static StackPane KStackPane(String className, Region region) {
        StackPane pane = new StackPane(region);

        pane.getStyleClass().add(className);

        return pane;
    }

    public static GridPane KGridPane(String className, int hGap, int vGap) {
        // base gridPane
        GridPane grid = new GridPane();

        // set style
        grid.getStyleClass().add(className);

        // set cell gaps
        grid.setHgap(hGap);
        grid.setVgap(vGap);

        // return it
        return grid;
    }
}
