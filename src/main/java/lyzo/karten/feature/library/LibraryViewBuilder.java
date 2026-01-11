package lyzo.karten.feature.library;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
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

// library view builder, renders library main view
public class LibraryViewBuilder implements ViewBuilder {

    // all user-created decks as an observable list
    private final ObservableList<Deck> decks;

    // active deck as observable property
    private final ObjectProperty<Deck> activeDeck;

    // reference to current listing page
    private final IntegerProperty deckPage = new SimpleIntegerProperty(1);

    // passed-down events
    private final EventHandler<MouseEvent> newDeckAction;
    private final EventHandler<MouseEvent> selectDeckAction;
    private final EventHandler<MouseEvent> deleteDeckAction;

    // TEMPORARY constant dimensions for grid
    private final int rows = 2;
    private final int cols = 3;

    // save passed-down information
    public LibraryViewBuilder(ObservableList<Deck> decks, ObjectProperty<Deck> activeDeck,
                              EventHandler<MouseEvent> newDeckAction,
                              EventHandler<MouseEvent> selectDeckAction,
                              EventHandler<MouseEvent> deleteDeckAction) {
        this.decks = decks;
        this.activeDeck = activeDeck;

        this.newDeckAction = newDeckAction;
        this.selectDeckAction = selectDeckAction;
        this.deleteDeckAction = deleteDeckAction;
    }

    @Override
    public Region build() {
        // title box
        Label title = KControls.KLabel("heading", "Library");
        HBox titleBox = KRegions.KHorizontalBox("", Pos.TOP_LEFT, 20, title);

        // subtitle box
        Label subtitle = KControls.KLabel("heading2", "Welcome back to your library. What deck will it be this time?");
        HBox subtitleBox = KRegions.KHorizontalBox("", Pos.TOP_LEFT, 20, subtitle);

        // topMenu box (create new & filter)
        Button createDeck = KControls.KButton("green-button", KControls.KLabel("heading2-shadow", "New Deck +"), newDeckAction);
        Button filterDecks = KControls.KButton("yellow-button", KControls.KLabel("heading2-shadow", "Filter By >"), null);

        HBox topBox = KRegions.KHorizontalBox("", Pos.TOP_RIGHT, 20, filterDecks, createDeck);

        // deckView box
        Node deckViewBox = deckView();
        VBox.setVgrow(deckViewBox, Priority.ALWAYS);

        // pane root
        VBox pane = KRegions.KVerticalBox("", Pos.TOP_CENTER, 40,
                titleBox,
                subtitleBox,
                KControls.KSeparator("", Orientation.HORIZONTAL),
                topBox,
                deckViewBox,
                pageControls()
        );

        // set min width and spacing
        pane.setMinWidth(500);

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

        // make grid reactive to deck updates
        decks.addListener((ListChangeListener<Deck>) _ -> populateGrid(grid));

        // make grid reactive to page updates
        deckPage.addListener((_, _, _) -> populateGrid(grid));

        // create girdPane container
        HBox gridContainer = KRegions.KHorizontalBox("", Pos.CENTER, 20, grid);
        gridContainer.setMaxWidth(Double.MAX_VALUE);
        grid.setMaxWidth(Double.MAX_VALUE);

        HBox.setHgrow(grid, Priority.ALWAYS);

        return gridContainer;
    }

    private void populateGrid(GridPane grid) {
        // clear previous cell decks
        grid.getChildren().clear();

        // calculate index start/end based on pages
        int startPoint = (deckPage.get() - 1) * cols * rows;
        int endPoint = Math.min(startPoint + cols * rows, decks.size());

        // create cell deck for each deck
        for (int i = startPoint; i < endPoint; i++) {
            // get deck object
            Deck d = decks.get(i);

            // calculate deck list index and position
            int index = i - startPoint;

            int x = index % cols;
            int y = index / cols;

            // add to grid
            grid.add(deckCell(d), x, y);
        }
    }

    private Node deckCell(Deck deck) {
        // title
        Label title = KControls.KLabel("heading2-shadow", deck.name());

        HBox titleBox = KRegions.KHorizontalBox("", Pos.TOP_LEFT, 20, title);
        VBox.setMargin(titleBox, new Insets(0, 0, 10, 0));

        // description
        Label description = KControls.KLabel("body-text", deck.description());

        HBox descriptionBox = KRegions.KHorizontalBox("", Pos.TOP_LEFT, 20, description);

        // middle fill
        VBox fillBox = KRegions.KVerticalBox("", Pos.CENTER, 0);
        VBox.setVgrow(fillBox, Priority.ALWAYS);

        // bottom buttons
        Button editDeck = KControls.KButton("yellow-button", KControls.KLabel("heading2-shadow", "edit"), mouseEvent -> {
            activeDeck.set(deck);
            selectDeckAction.handle(mouseEvent);
        });
        Button deleteDeck = KControls.KButton("red-button", KControls.KLabel("heading2-shadow", "delete"), mouseEvent -> {
            activeDeck.set(deck);
            deleteDeckAction.handle(mouseEvent);
        });

        // container for bottom buttons
        HBox buttonBox = KRegions.KHorizontalBox("", Pos.BOTTOM_RIGHT, 20, editDeck, deleteDeck);
        buttonBox.setPadding(new Insets(20, 0, 0, 0));

        // root pane with all components
        VBox pane = KRegions.KVerticalBox("div", Pos.TOP_CENTER, 20,
                titleBox,
                descriptionBox,
                fillBox,
                buttonBox);

        // return it
        return pane;
    }

    private Node pageControls() {
        // create page controls container
        HBox controls = KRegions.KHorizontalBox("", Pos.CENTER, 20);

        // populate page controls container
        populatePageControls(controls);

        // make container reactive to deck updates
        decks.addListener(((ListChangeListener<Deck>) _ -> populatePageControls(controls)));

        // return container
        return controls;
    }

    private void populatePageControls(HBox controls) {
        // clear previous children
        controls.getChildren().clear();

        // calculate number of pages based on per-page size and deck count
        int pages = (int) Math.ceil(decks.size() / (double)(rows * cols));

        // abandon drawing pages if only one page
        if (pages == 1) {
            deckPage.set(1);
            return;
        }

        // create appropriate amount of pages
        for (int i = 0; i < pages; i++) {
            // convert index for user-view
            int pageNum = i + 1;

            // create stylized page button
            Button pageButton = KControls.KButton("blue-button", KControls.KLabel("heading2-shadow", String.valueOf(pageNum)), _ -> deckPage.set(pageNum));

            // add to controls
            controls.getChildren().add(pageButton);
        }
    }
}
