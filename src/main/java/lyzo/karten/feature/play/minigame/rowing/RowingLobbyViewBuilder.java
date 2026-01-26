package lyzo.karten.feature.play.minigame.rowing;

import javafx.beans.property.ObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import lyzo.karten.utility.structures.ViewBuilder;
import lyzo.karten.utility.structures.minigame.GameState;
import lyzo.karten.utility.ui.KControls;
import lyzo.karten.utility.ui.KRegions;

public class RowingLobbyViewBuilder implements ViewBuilder {

    private final ObjectProperty<GameState> gameState;

    private final Runnable playGameAction;

    public RowingLobbyViewBuilder(ObjectProperty<GameState> gameState, Runnable playGameAction) {
        this.gameState = gameState;
        this.playGameAction = playGameAction;
    }

    @Override
    public Region build() {
        Button playGame = KControls.KButton("blue-button", KControls.KLabel("heading", "play with 5+me total"), this::startGame);

        HBox container = KRegions.KHorizontalBox("", Pos.CENTER, 50, playGame);

        return container;
    }

    private void startGame() {
        RowingGameState rowingGameState = new RowingGameState(1000, 5);
        gameState.set(rowingGameState);

        playGameAction.run();
    }
}
