package lyzo.karten.feature.side;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import lyzo.karten.utility.interfaces.ViewBuilder;
import lyzo.karten.utility.ui.KControls;
import lyzo.karten.utility.ui.KRegions;

// side menu view builder
public class SideViewBuilder implements ViewBuilder {

    private final EventHandler<MouseEvent> homeAction;
    private final EventHandler<MouseEvent> libraryAction;
    private final EventHandler<MouseEvent> settingsAction;

    public SideViewBuilder(EventHandler<MouseEvent> homeAction, EventHandler<MouseEvent> libraryAction, EventHandler<MouseEvent> settingsAction) {
        this.homeAction = homeAction;
        this.libraryAction = libraryAction;
        this.settingsAction = settingsAction;
    }

    @Override
    public Region build() {
        // creates vertical pane, fills it with nav options
        VBox pane = KRegions.KVerticalBox("", Pos.TOP_LEFT,
                menuItem("KARTEN", homeAction),
                menuItem("Library", libraryAction),
                separator(),
                menuItem("Settings", settingsAction)
        );

        // set spacing and padding
        pane.setSpacing(10);
        pane.setMinWidth(250);

        // display pane
        return pane;
    }

    private Node menuItem(String name, EventHandler<MouseEvent> clickAction) {
        HBox box = new HBox();
        box.setAlignment(Pos.CENTER_LEFT);

        Label label = KControls.KLabel("heading", name);
        label.setOnMouseClicked(clickAction);

        box.getChildren().add(label);

        return box;
    }

    private Node separator() {
        return new Separator();
    }
}
