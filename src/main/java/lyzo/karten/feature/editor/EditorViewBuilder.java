package lyzo.karten.feature.editor;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import lyzo.karten.model.Card;
import lyzo.karten.model.Deck;
import lyzo.karten.utility.interfaces.ViewBuilder;
import lyzo.karten.utility.ui.KControls;
import lyzo.karten.utility.ui.KRegions;

public class EditorViewBuilder implements ViewBuilder {

    // passed-down properties/observables
    private final ObjectProperty<Deck> deck;
    private final ObservableList<Card> deckCards;
    private final ObjectProperty<Card> activeCard;

    // passed-down events
    private final EventHandler<MouseEvent> newCardAction;

    public EditorViewBuilder(ObjectProperty<Deck> deck, ObservableList<Card> deckCards, ObjectProperty<Card> activeCard,
                             EventHandler<MouseEvent> newCardAction) {
        // save passed-down information
        this.deck = deck;
        this.deckCards = deckCards;
        this.activeCard = activeCard;

        this.newCardAction = newCardAction;
    }

    @Override
    public Region build() {
        // title box
        Label title = KControls.KLabel("heading", "Deck Editor");
        HBox titleBox = KRegions.KHorizontalBox("", Pos.TOP_LEFT, 20, title);

        // subtitle box
        Label subtitle = KControls.KLabel("heading2", "Now editing your " + deck.get().name() + " deck.");
        HBox subtitleBox = KRegions.KHorizontalBox("", Pos.TOP_LEFT, 20, subtitle);

        // deck name box
        Label name = KControls.KLabel("heading-shadow", deck.get().name());
        Button editName = KControls.KButton("yellow-button", KControls.KLabel("heading2-shadow", "edit"), null);

        HBox nameBox = KRegions.KHorizontalBox("div", Pos.CENTER_LEFT, 20, name, editName);
        nameBox.setMaxWidth(Region.USE_PREF_SIZE);

        // deck description box
        Label description = KControls.KLabel("heading2-shadow", deck.get().description());
        Button editDescription = KControls.KButton("yellow-button", KControls.KLabel("heading2-shadow", "edit"), null);

        HBox descriptionBox = KRegions.KHorizontalBox("div", Pos.CENTER_LEFT, 20, description, editDescription);
        descriptionBox.setMaxWidth(Region.USE_PREF_SIZE);

        // header box
        HBox headerBox = KRegions.KHorizontalBox("", Pos.CENTER_LEFT, 50, nameBox, descriptionBox);

        // workspace box
        HBox workspaceBox = KRegions.KHorizontalBox("", Pos.CENTER_LEFT, 50, cardList(), cardEditor());
        workspaceBox.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(workspaceBox, Priority.ALWAYS);

        // root pane
        VBox pane = KRegions.KVerticalBox("", Pos.TOP_LEFT, 40,
                titleBox,
                subtitleBox,
                KControls.KSeparator("", Orientation.HORIZONTAL),
                headerBox,
                workspaceBox
        );

        // set min width and spacing
        pane.setMinWidth(500);

        // return it
        return pane;
    }

    private Node cardList() {
        // new card box
        Button newCard = KControls.KButton("green-button", KControls.KLabel("heading2-shadow", "New Card +"), newCardAction);
        HBox newCardBox = KRegions.KHorizontalBox("", Pos.CENTER, 0, newCard);

        // base card view
        ListView<Card> cardListView = KRegions.KListView("", deckCards);
        cardListView.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(cardListView, Priority.ALWAYS);

        // add selection model listener
        cardListView.getSelectionModel().selectedItemProperty().addListener((_, _, newV) -> activeCard.set(newV));

        // set cell factory
        setCellFactory(cardListView);

        // create component container
        VBox cardListContainer = KRegions.KVerticalBox("div", Pos.TOP_CENTER, 20, newCardBox, cardListView);
        cardListContainer.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(cardListContainer, Priority.ALWAYS);

        // return it
        return cardListContainer;
    }

    private void setCellFactory(ListView<Card> cardListView) {
        // configure visual style of list cells
        cardListView.setCellFactory(_ -> new ListCell<>() {

            @Override
            protected void updateItem(Card card, boolean empty) {
                super.updateItem(card, empty);

                if (empty || card == null) {
                    setText(null);
                    setGraphic(null);
                    return;
                }

                Label id = KControls.KLabel("heading2-shadow", card.id() + "");
                HBox cardBox = KRegions.KHorizontalBox("card-view", Pos.CENTER_LEFT, 20, id);

                setGraphic(cardBox);
            }
        });
    }

    private Node cardEditor() {
        // card content label
        Label content = KControls.KLabel("body-text", "");
        activeCard.addListener((_, _, newCard) -> content.setText(newCard.id() + ": " + newCard.front()));

        // card body
        VBox cardPreview = KRegions.KVerticalBox("card-preview", Pos.CENTER, 0, content);

        // set aspect ratio of card
        cardPreview.prefHeightProperty().bind(cardPreview.widthProperty().multiply(9.0 / 16.0));

        // limit growth to hold aspect ratio
        VBox.setVgrow(cardPreview, Priority.NEVER);
        cardPreview.setMaxHeight(Region.USE_PREF_SIZE);

        // flip card action box
        Label side = KControls.KLabel("heading2-shadow", "Front");
        Button flipCard = KControls.KButton("yellow-button", KControls.KLabel("heading2-shadow", "Flip card"), null);

        HBox flipBox = KRegions.KHorizontalBox("div-special", Pos.CENTER_LEFT, 20, side, flipCard);

        // root pane
        VBox creationContainer = KRegions.KVerticalBox("div", Pos.TOP_LEFT, 20, cardPreview, flipBox);

        // set width
        creationContainer.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(creationContainer, Priority.ALWAYS);

        // return it
        return creationContainer;
    }
}
