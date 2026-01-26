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

    private final Runnable homeAction;
    private final Runnable editorAction;
    private final Runnable libraryAction;
    private final Runnable settingsAction;

    public SideViewBuilder(Runnable homeAction, Runnable editorAction, Runnable libraryAction, Runnable settingsAction) {
        this.homeAction = homeAction;
        this.editorAction = editorAction;
        this.libraryAction = libraryAction;
        this.settingsAction = settingsAction;
    }

    @Override
    public Region build() {
        // creates vertical pane, fills it with nav options
        VBox pane = KRegions.KVerticalBox("", Pos.TOP_LEFT, 10,
                menuItem("KARTEN", homeAction),
                menuItem("Editor", editorAction),
                menuItem("Library", libraryAction),
                separator(),
                menuItem("Settings", settingsAction)
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
