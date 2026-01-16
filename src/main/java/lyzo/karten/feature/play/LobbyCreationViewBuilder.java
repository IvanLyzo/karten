package lyzo.karten.feature.play;

import javafx.beans.property.ObjectProperty;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import lyzo.karten.feature.play.minigame.rowing.RowingGameState;
import lyzo.karten.utility.interfaces.ViewBuilder;
import lyzo.karten.utility.interfaces.minigame.GameState;
import lyzo.karten.utility.ui.KControls;
import lyzo.karten.utility.ui.KRegions;

public class LobbyCreationViewBuilder implements ViewBuilder {

    private final ObjectProperty<GameState> gameState;

    private final Runnable playGameAction;

    public LobbyCreationViewBuilder(ObjectProperty<GameState> gameState, Runnable playGameAction) {
        this.gameState = gameState;
        this.playGameAction = playGameAction;
    }

    @Override
    public Region build() {
        Button playGame = KControls.KButton("blue-button", KControls.KLabel("heading", "play with 5+me total"), this::startGame);

        HBox container = KRegions.KHorizontalBox("", Pos.CENTER, 50, playGame);

        return container;
    }

    private void startGame(MouseEvent event) {
        RowingGameState rowingGameState = new RowingGameState(200, 5);
        gameState.set(rowingGameState);

        playGameAction.run();
    }
}
