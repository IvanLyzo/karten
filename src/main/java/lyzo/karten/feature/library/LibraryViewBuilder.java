package lyzo.karten.feature.library;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import lyzo.karten.model.Deck;
import lyzo.karten.utility.interfaces.ViewBuilder;
import lyzo.karten.utility.ui.KControls;
import lyzo.karten.utility.ui.KRegions;

public class LibraryViewBuilder implements ViewBuilder {

    // all user-created decks as an observable list
    private final ObservableList<Deck> decks;

    // set observable list of decks
    public LibraryViewBuilder(ObservableList<Deck> decks) {
        this.decks = decks;
    }

    @Override
    public Region build() {
        // title box
        Label title = KControls.KLabel("", "Library");
        HBox titleBox = KRegions.KHorizontalBox("", Pos.TOP_LEFT, title);

        // topMenu box
        Label newSetActionBtn = KControls.KLabel("", "New Deck +");
        HBox topBox = KRegions.KHorizontalBox("", Pos.TOP_RIGHT, newSetActionBtn);

        // pane wrapper
        VBox pane = KRegions.KVerticalBox("", Pos.TOP_CENTER, titleBox, topBox, deckView());

        // set min width and spacing
        pane.setMinWidth(500);
        pane.setSpacing(20);

        // display it
        return pane;
    }

    private Node deckView() {
        // set cols/rows
        int cols = 3;
        int rows = 2;

        // create gridPane
        GridPane grid = KRegions.KGridPane("", 20, 20, cols, rows);

        // populate grid with decks
        for (int i = 0; i < decks.size(); i++) {
            Deck d = decks.get(i);

            int x = i % cols;
            int y = i / cols;

            grid.add(deckCell(d), x, y);
        }

        return grid;
    }

    private Node deckCell(Deck deck) {

        // title
        Label title = KControls.KLabel("", deck.name());

        HBox titleBox = KRegions.KHorizontalBox("", Pos.TOP_LEFT, title);
        VBox.setMargin(titleBox, new Insets(0, 0, 20, 0));

        // root pane
        VBox pane = KRegions.KVerticalBox("", Pos.TOP_CENTER, titleBox);
        pane.setPadding(new Insets(20, 20, 20, 20));

        // return it
        return pane;
    }
}
