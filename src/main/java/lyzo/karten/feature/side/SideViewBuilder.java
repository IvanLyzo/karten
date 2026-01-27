package lyzo.karten.feature.side;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import lyzo.karten.utility.structures.ViewBuilder;
import lyzo.karten.utility.ui.KControls;
import lyzo.karten.utility.ui.KRegions;

// side menu view builder
public class SideViewBuilder implements ViewBuilder {

    private final Runnable[] actions;

    public SideViewBuilder(Runnable... actions) {
        this.actions = actions;
    }

    @Override
    public Region build() {
        // creates vertical pane, fills it with nav options
        VBox pane = KRegions.KVerticalBox("", Pos.TOP_LEFT, 10,
                menuItem("KARTEN", actions[0]),
                menuItem("Editor", actions[1]),
                menuItem("Library", actions[2]),
                separator(),
                menuItem("Profile", actions[3]),
                menuItem("Settings", actions[4])
        );

        // set spacing and padding
        pane.setMinWidth(250);

        // display pane
        return pane;
    }

    private Node menuItem(String name, Runnable clickAction) {
        HBox box = new HBox();
        box.setAlignment(Pos.CENTER_LEFT);

        Label label = KControls.KLabel("heading", name);
        label.setOnMouseClicked(_ -> clickAction.run());

        box.getChildren().add(label);

        return box;
    }

    private Node separator() {
        return new Separator();
    }
}
