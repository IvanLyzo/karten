package lyzo.karten.feature.themes;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.util.Pair;
import lyzo.karten.handler.ThemeHandler;
import lyzo.karten.utility.structures.ViewBuilder;
import lyzo.karten.utility.ui.KControls;
import lyzo.karten.utility.ui.KRegions;

import javax.swing.text.html.Option;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;

public class ThemesViewBuilder implements ViewBuilder {

    private final ObservableList<Pair<String, String>> values;

    private final BiConsumer<ThemeHandler.PROPERTIES, String> makeChangesAction;

    public ThemesViewBuilder(ObservableList<Pair<String, String>> values, BiConsumer<ThemeHandler.PROPERTIES, String> makeChangesAction) {
        this.values = values;
        this.makeChangesAction = makeChangesAction;
    }

    @Override
    public Region build() {
        ListView<Pair<String, String>> listView = KRegions.KListView("", values);

        listView.setSelectionModel(null);
        setCellFactory(listView);

        return listView;
    }

    private void setCellFactory(ListView<Pair<String, String>> listView) {
        listView.setCellFactory(_ -> new ListCell<>() {

            @Override
            protected void updateItem(Pair<String, String> pair, boolean empty) {
                super.updateItem(pair, empty);

                if (empty || pair == null) {
                    setText(null);
                    setGraphic(null);
                    return;
                }

                Label propertyName = KControls.KLabel("heading", pair.getKey());
                TextField propertyValue = KControls.KTextField("", pair.getValue());
                Button makeChanges = KControls.KButton("yellow-button", KControls.KLabel("heading", "Save changes"), () -> {
                    Optional<ThemeHandler.PROPERTIES> property = Arrays.stream(ThemeHandler.PROPERTIES.values()).filter(s -> Objects.equals(s.uid, pair.getKey())).findFirst();
                    if (property.isEmpty()) {
                        throw new RuntimeException("bla bla bla");
                    }

                    makeChangesAction.accept(property.get(), propertyValue.getText());
                });

                HBox container = KRegions.KHorizontalBox("", Pos.CENTER_LEFT, 50, propertyName, propertyValue, makeChanges);

                setGraphic(container);
            }
        });
    }
}
