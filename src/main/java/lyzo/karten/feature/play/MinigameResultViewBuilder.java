package lyzo.karten.feature.play;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import lyzo.karten.feature.play.minigame.rowing.RowingGameState;
import lyzo.karten.utility.structures.ViewBuilder;
import lyzo.karten.utility.ui.KControls;
import lyzo.karten.utility.ui.KRegions;

import java.util.List;
import java.util.Map;

public class MinigameResultViewBuilder implements ViewBuilder {

    private final MinigameResult minigameResult;

    public MinigameResultViewBuilder(MinigameResult minigameResult) {
        this.minigameResult = minigameResult;
    }

    @Override
    public Region build() {
        System.out.println(minigameResult.leaderboard);
        ListView<Map.Entry<String, Integer>> listView = KRegions.KListView("", FXCollections.observableArrayList(minigameResult.leaderboard));
        listView.setSelectionModel(null);
        setCellFactory(listView);

        listView.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(listView, Priority.ALWAYS);



        VBox playerSummary = KRegions.KVerticalBox("", Pos.TOP_LEFT, 20, userResults());
        playerSummary.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(playerSummary, Priority.ALWAYS);

        return KRegions.KHorizontalBox("", Pos.CENTER, 50, listView, playerSummary);
    }

    private void setCellFactory(ListView<Map.Entry<String, Integer>> listView) {
        listView.setCellFactory(_ -> new ListCell<>() {

            @Override
            protected void updateItem(Map.Entry<String, Integer> entry, boolean empty) {
                super.updateItem(entry, empty);

                if (empty || entry == null) {
                    setText(null);
                    setGraphic(null);
                    return;
                }

                Label name = KControls.KLabel("heading2", entry.getKey());

                HBox middleSeparator = KRegions.KHorizontalBox("", Pos.CENTER, 0);
                middleSeparator.setPrefHeight(Double.MAX_VALUE);
                HBox.setHgrow(middleSeparator, Priority.ALWAYS);

                Label qualifier = KControls.KLabel("heading2", entry.getValue() + "m");

                HBox container = KRegions.KHorizontalBox("", Pos.TOP_LEFT, 50, name, middleSeparator, qualifier);

                setGraphic(container);
            }
        });
    }

    private Node userResults() {
        return KControls.KLabel("heading", minigameResult.user.name);
    }

    public record MinigameResult(List<Map.Entry<String, Integer>> leaderboard, RowingGameState.Player user,
                                 int questionsCorrect, int moneyEarned) { }
}
