package lyzo.karten.feature.base;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import lyzo.karten.feature.empty.EmptyViewBuilder;
import lyzo.karten.utility.interfaces.ViewBuilder;
import lyzo.karten.utility.ui.KRegions;

// base view builder; renders the base layouts and manages all inserted UI
public class BaseViewBuilder implements ViewBuilder {

    // side menu pre-built region
    // (view builder does not concern itself with creation of other regions, merely their layout)
    private final Region sideMenu;

    public BaseViewBuilder(Region sideMenu) {
        this.sideMenu = sideMenu;
    }

    @Override
    public Region build() {
        // root is a horizontal splitPane for resizing flexibility
        SplitPane root = new SplitPane();
        root.setOrientation(Orientation.HORIZONTAL);

        // populate it with side and main screens
        root.getItems().add(sectionPane(sideMenu));
        root.getItems().add(sectionPane(new EmptyViewBuilder().build()));

        // set default split at 1/6 width from left (for smaller left-side menu)
        root.widthProperty().addListener((_, _, _) -> {
            root.setDividerPositions(1.0 / 6);
        });

        // display splitPane root
        return root;
    }

    // wrapper for any displayed section
    private Region sectionPane(Region region) {
        // stackPane root
        StackPane pane = KRegions.KStackPane("section-pane", region);

        // create margins around each section
        StackPane.setMargin(pane, new Insets(20, 20, 20, 20));

        // display section
        return pane;
    }
}
