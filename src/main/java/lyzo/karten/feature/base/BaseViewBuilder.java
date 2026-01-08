package lyzo.karten.feature.base;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import lyzo.karten.feature.empty.EmptyViewBuilder;
import lyzo.karten.utility.interfaces.ViewBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// base view builder; renders the base layouts and manages all inserted UI
public class BaseViewBuilder implements ViewBuilder {

    private static final Logger log = LoggerFactory.getLogger(BaseViewBuilder.class);

    @Override
    public Region build() {
        // root is a horizontal splitPane for resizing flexibility
        SplitPane root = new SplitPane();
        root.setOrientation(Orientation.HORIZONTAL);

        // populate it with side and main screens
        root.getItems().add(sectionPane(new EmptyViewBuilder().build()));
        root.getItems().add(sectionPane(new EmptyViewBuilder().build()));

        // set default split at 1/6 width from left (for smaller left-side menu)
        root.setDividerPositions(1.0 / 6);

        // display splitPane root
        return root;
    }

    // wrapper for any displayed section
    private Region sectionPane(Region region) {
        // stackPane root
        StackPane pane = new StackPane(region);

        // create margins around each section
        StackPane.setMargin(pane, new Insets(20, 32, 32, 20));

        // display section
        return pane;
    }
}
