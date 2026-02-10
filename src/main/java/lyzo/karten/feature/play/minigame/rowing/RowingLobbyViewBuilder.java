package lyzo.karten.feature.play.minigame.rowing;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import lyzo.karten.io.resources.BotLoader;
import lyzo.karten.model.Bot;
import lyzo.karten.utility.structures.ViewBuilder;
import lyzo.karten.utility.structures.minigame.GameState;
import lyzo.karten.utility.ui.KControls;
import lyzo.karten.utility.ui.KRegions;

import java.util.ArrayList;
import java.util.List;

public class RowingLobbyViewBuilder implements ViewBuilder {

    private final ObjectProperty<GameState> gameState;
    private final Runnable playGameAction;

    private final String username;

    private final List<Bot> bots = new ArrayList<>();

    public RowingLobbyViewBuilder(ObjectProperty<GameState> gameState, Runnable playGameAction, String username) {
        this.gameState = gameState;
        this.playGameAction = playGameAction;

        this.username = username;
    }

    @Override
    public Region build() {
        // TEMPORARY BYPASS
        startGame();
        // ----------------

        Button playGame = KControls.KButton("blue-button", KControls.KLabel("heading", "play with 5+me total"), this::startGame);

        return KRegions.KHorizontalBox("", Pos.CENTER, 50, botList(), playGame);
    }

    private Node botList() {
        List<String> botNames = BotLoader.getBotList();

        List<Bot> bots = new ArrayList<>();
        for (String s : botNames) {
            bots.add(BotLoader.loadBot(s));
        }

        ListView<Bot> botList = KRegions.KListView("", FXCollections.observableArrayList(bots));

        botList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        botList.getSelectionModel().getSelectedItems().addListener((ListChangeListener<Bot>) c -> bots.addAll(c.getAddedSubList()));

         return botList;
    }

    private void startGame() {
        RowingGameState rowingGameState = new RowingGameState(username, 2000, 5);
        gameState.set(rowingGameState);

        playGameAction.run();
    }
}
