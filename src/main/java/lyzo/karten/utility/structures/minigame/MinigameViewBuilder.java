package lyzo.karten.utility.structures.minigame;

import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import lyzo.karten.utility.structures.ViewBuilder;
import lyzo.karten.utility.ui.KControls;
import lyzo.karten.utility.ui.KRegions;

public abstract class MinigameViewBuilder implements ViewBuilder {

    public abstract void drawGame();

    public void buildOverlay(StackPane parent, GameState gameState, StringProperty response) {
        Label frontContent = KControls.KLabel("heading2", gameState.activeCard.get().front());
        gameState.activeCard.addListener((observable, oldValue, newValue) -> frontContent.setText(newValue.front()));

        TextField input = KControls.KTextField("heading2", "answer");
        response.bind(input.textProperty());

        VBox overlay = KRegions.KVerticalBox("div", Pos.CENTER, 20, frontContent, input);

        overlay.prefWidthProperty().bind(parent.widthProperty().multiply((double) 3 / 4));
        overlay.prefHeightProperty().bind(parent.heightProperty().multiply((double) 3 / 4));
        overlay.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        Region dimmer = new Region();
        dimmer.setStyle("-fx-background-color: rgba(0,0,0,0.4);");
        dimmer.prefWidthProperty().bind(parent.widthProperty().subtract(50));
        dimmer.prefHeightProperty().bind(parent.heightProperty().subtract(50));

        gameState.overlayOn.addListener(((_, _, newV) -> {
            if (newV) {
                parent.getChildren().addAll(dimmer, overlay);
                Platform.runLater(input::requestFocus);
            } else {
                parent.getChildren().removeAll(dimmer, overlay);
            }
        }));
    }
}
