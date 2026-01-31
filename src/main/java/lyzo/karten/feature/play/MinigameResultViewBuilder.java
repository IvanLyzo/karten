package lyzo.karten.feature.play;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.util.Pair;
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
        ListView<Map.Entry<String, Integer>> listView = KRegions.KListView("", FXCollections.observableArrayList(minigameResult.leaderboard));
        listView.setSelectionModel(null);
        setCellFactory(listView);



        return listView;
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
                Label qualifier = KControls.KLabel("heading2", entry.getValue() + "");
                HBox container = KRegions.KHorizontalBox("", Pos.TOP_LEFT, 50, name, qualifier);

                setGraphic(container);
            }
        });
    }

    public record MinigameResult(List<Map.Entry<String, Integer>> leaderboard, int moneyEarned) { }
}
