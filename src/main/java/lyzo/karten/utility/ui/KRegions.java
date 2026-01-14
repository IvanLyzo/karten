package lyzo.karten.utility.ui;

import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.*;

public class KRegions {

    // default Karten VBox builder
    public static VBox KVerticalBox(String className, Pos alignment, double spacing, Node... children) {
        // create base VBox
        VBox vBox = new VBox(spacing, children);

        // apply specified class and alignment
        vBox.getStyleClass().add(className);
        vBox.setAlignment(alignment);

        // return it
        return vBox;
    }

    // default Karten HBox builder
    public static HBox KHorizontalBox(String className, Pos alignment, double spacing, Node... children) {
        // create base HBox
        HBox hBox = new HBox(spacing, children);

        // apply specified class and alignment
        hBox.getStyleClass().add(className);
        hBox.setAlignment(alignment);

        // return it
        return hBox;
    }

    public static StackPane KStackPane(String className, Region... region) {
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

    public static <T> ListView<T> KListView(String className, ObservableList<T> items) {
        // base generic list view
        ListView<T> cardListView = new ListView<>(items);

        // set style
        cardListView.getStyleClass().add(className);

        // return it
        return cardListView;
    }
}
