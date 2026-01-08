package lyzo.karten.feature.side;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import lyzo.karten.utility.interfaces.ViewBuilder;
import lyzo.karten.utility.ui.KControls;
import lyzo.karten.utility.ui.KRegions;

// side menu view builder
public class SideViewBuilder implements ViewBuilder {

    @Override
    public Region build() {
        // creates vertical pane, fills it with nav options
        VBox pane = KRegions.KVerticalBox("",
                title(),
                menuItem("Library"),
                new Separator(),
                menuItem("Profile"),
                menuItem("Settings")
        );

        // set spacing and padding
        pane.setSpacing(10);
        pane.setMinWidth(250);

        // display pane
        return pane;
    }

    private Node title() {
        HBox title_box = new HBox();
        title_box.setAlignment(Pos.CENTER_LEFT);

        Label title = KControls.KLabel("", "Karten");
        title_box.getChildren().add(title);

        return title_box;
    }

    private Node menuItem(String name) {
        HBox box = new HBox();
        box.setAlignment(Pos.CENTER_LEFT);

        Label label = KControls.KLabel("", name);

        box.getChildren().add(label);

        return box;
    }
}
