package lyzo.karten.feature.library;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import lyzo.karten.model.Deck;
import lyzo.karten.utility.interfaces.ViewBuilder;
import lyzo.karten.utility.ui.KControls;
import lyzo.karten.utility.ui.KRegions;

public class LibraryViewBuilder implements ViewBuilder {

    // all user-created decks as an observable list
    private final ObservableList<Deck> decks;

    // reference to current listing page
    private final IntegerProperty deckPage = new SimpleIntegerProperty(1);

    private final EventHandler<MouseEvent> newDeckAction;

    private int rows = 2;
    private int cols = 3;

    // set observable list of decks
    public LibraryViewBuilder(ObservableList<Deck> decks, EventHandler<MouseEvent> newDeckAction) {
        this.decks = decks;

        this.newDeckAction = newDeckAction;
    }

    @Override
    public Region build() {
        // title box
        Label title = KControls.KLabel("", "Library");
        HBox titleBox = KRegions.KHorizontalBox("", Pos.TOP_LEFT, title);

        // topMenu box
        Button newDeckAction_btn = KControls.KButton("", KControls.KLabel("", "New Deck +"), newDeckAction);
        HBox topBox = KRegions.KHorizontalBox("", Pos.TOP_RIGHT, newDeckAction_btn);

        // deckView box
        Node deckViewBox = deckView();
        VBox.setVgrow(deckViewBox, Priority.ALWAYS);

        // pane root
        VBox pane = KRegions.KVerticalBox("", Pos.TOP_CENTER, titleBox, topBox, deckViewBox, pageControls());

        // set min width and spacing
        pane.setMinWidth(500);
        pane.setSpacing(20);

        // display it
        return pane;
    }

    private Node deckView() {
        // create gridPane
        GridPane grid = KRegions.KGridPane("", 50, 50);

        // add constraints

        // 3 equal columns
        for (int i = 0; i < cols; i++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setPercentWidth((double) 100 / cols);
            grid.getColumnConstraints().add(cc);
        }

        // 2 rows
        for (int i = 0; i < rows; i++) {
            RowConstraints rc = new RowConstraints();
            rc.setPercentHeight((double) 100 / rows);
            grid.getRowConstraints().add(rc);
        }

        // populate grid
        populateGrid(grid);

        // make grid reactive to page updates
        deckPage.addListener((obs, oldPage, newPage) -> {
            populateGrid(grid);
        });

        // create girdPane container
        HBox gridContainer = KRegions.KHorizontalBox("", Pos.CENTER, grid);
        grid.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(grid, Priority.ALWAYS);

        return gridContainer;
    }

    private void populateGrid(GridPane grid) {
        grid.getChildren().clear();

        // populate grid with decks
        int startPoint = (deckPage.get() - 1) * cols * rows;
        int endPoint = Math.min(startPoint + cols * rows, decks.size());

        for (int i = startPoint; i < endPoint; i++) {
            Deck d = decks.get(i);

            int index = i - startPoint;

            int x = index % cols;
            int y = index / cols;

            grid.add(deckCell(d), x, y);
        }
    }

    private Node deckCell(Deck deck) {
        // title
        Label title = KControls.KLabel("", deck.name());

        HBox titleBox = KRegions.KHorizontalBox("", Pos.TOP_LEFT, title);
        VBox.setMargin(titleBox, new Insets(0, 0, 20, 0));

        // root pane
        VBox pane = KRegions.KVerticalBox("deck-cell", Pos.TOP_CENTER, titleBox);
        pane.setPadding(new Insets(20, 20, 20, 20));
        pane.setMinHeight(250);

        // return it
        return pane;
    }

    private Node pageControls() {
        HBox controls = KRegions.KHorizontalBox("", Pos.CENTER);

        int pages = (int) Math.ceil(decks.size() / (double)(rows * cols));
        for (int i = 0; i < pages; i++) {

            int pageNum = i + 1;
            Button pageButton = KControls.KButton("", KControls.KLabel("", String.valueOf(pageNum)), _ -> deckPage.set(pageNum));
            controls.getChildren().add(pageButton);
        }

        return controls;
    }
}
