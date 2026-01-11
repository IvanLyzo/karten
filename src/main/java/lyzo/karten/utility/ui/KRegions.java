package lyzo.karten.utility.ui;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.*;

public class KRegions {

    public static VBox KVerticalBox(String className, Pos alignment, Node... children) {
        VBox vBox = new VBox();

        vBox.getStyleClass().add(className);
        vBox.setAlignment(alignment);

        vBox.getChildren().addAll(children);

        return vBox;
    }

    // default Karten HBox builder
    public static HBox KHorizontalBox(String className, Pos alignment, double spacing, Node... children) {
        // create base HBox
        HBox hBox = new HBox();

        // apply specified class and alignment
        hBox.getStyleClass().add(className);
        hBox.setAlignment(alignment);
        hBox.setSpacing(spacing);

        // add all on-startup children
        hBox.getChildren().addAll(children);

        // return it
        return hBox;
    }

    public static StackPane KStackPane(String className, Region region) {
        StackPane pane = new StackPane(region);

        pane.getStyleClass().add(className);

        return pane;
    }

    public static SplitPane KSplitPane(String className, Orientation orientation, Region... regions) {
        SplitPane root = new SplitPane();
        root.getStyleClass().add(className);

        root.setOrientation(orientation);

        root.getItems().addAll(regions);

        return root;
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
