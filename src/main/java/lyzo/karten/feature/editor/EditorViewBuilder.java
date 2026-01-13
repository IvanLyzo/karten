package lyzo.karten.feature.editor;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import lyzo.karten.model.Card;
import lyzo.karten.model.CardCreation;
import lyzo.karten.model.Deck;
import lyzo.karten.model.DeckCreation;
import lyzo.karten.utility.interfaces.ViewBuilder;
import lyzo.karten.utility.ui.KControls;
import lyzo.karten.utility.ui.KRegions;

public class EditorViewBuilder implements ViewBuilder {

    // passed-down properties/observables
    private final ObjectProperty<Deck> deck;
    private final ObjectProperty<DeckCreation> deckChanges;
    private final ObservableList<Card> deckCards;
    private final ObjectProperty<Card> activeCard;
    private final ObjectProperty<CardCreation> cardChanges;

    // passed-down events
    private final EventHandler<MouseEvent> newCardAction;

    // local variables
    private final BooleanProperty previewFront = new SimpleBooleanProperty(true);

    public EditorViewBuilder(ObjectProperty<Deck> deck, ObjectProperty<DeckCreation> deckChanges,
                             ObservableList<Card> deckCards, ObjectProperty<Card> activeCard, ObjectProperty<CardCreation> cardChanges,
                             EventHandler<MouseEvent> newCardAction) {
        // save passed-down information
        this.deck = deck;
        this.deckChanges = deckChanges;
        this.deckCards = deckCards;
        this.activeCard = activeCard;
        this.cardChanges = cardChanges;

        this.newCardAction = newCardAction;
    }

    @Override
    public Region build() {
        if (deck.get() == null) {
            return buildEmpty();
        }

        // title box
        Label title = KControls.KLabel("heading", "Deck Editor");
        HBox titleBox = KRegions.KHorizontalBox("", Pos.TOP_LEFT, 20, title);

        // subtitle box
        Label subtitle = KControls.KLabel("heading2", "Now editing your " + deck.get().name() + " deck.");
        HBox subtitleBox = KRegions.KHorizontalBox("", Pos.TOP_LEFT, 20, subtitle);

        // deck name box
        Label name = KControls.KLabel("heading-shadow", deck.get().name());
        deck.addListener((_, _, newDeck) -> {
            if (newDeck == null) {
                name.setText("EMPTY");
            } else {
                name.setText(newDeck.name());
            }
        });

        Button editName = KControls.KButton("yellow-button", KControls.KLabel("heading2-shadow", "edit"), null);

        HBox nameBox = KRegions.KHorizontalBox("div", Pos.CENTER_LEFT, 20, name, editName);
        nameBox.setMaxWidth(Region.USE_PREF_SIZE);

        editName.setOnAction(_ -> KControls.editMode(nameBox, name, s -> deckChanges.set(new DeckCreation(s, deck.get().description()))));

        // deck description box
        Label description = KControls.KLabel("heading2-shadow", deck.get().description());
        deck.addListener((_, _, newDeck) -> {
            if (newDeck == null) {
                description.setText("EMPTY");
            } else {
                description.setText(newDeck.description());
            }
        });

        Button editDescription = KControls.KButton("yellow-button", KControls.KLabel("heading2-shadow", "edit"), null);

        HBox descriptionBox = KRegions.KHorizontalBox("div", Pos.CENTER_LEFT, 20, description, editDescription);
        descriptionBox.setMaxWidth(Region.USE_PREF_SIZE);

        editDescription.setOnAction(e -> KControls.editMode(descriptionBox, description, s -> deckChanges.set(new DeckCreation(deck.get().name(), s))));

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
        activeCard.addListener((_, _, _) -> updateContent(content));
        previewFront.addListener((_, _, _) -> updateContent(content));

        // card body
        VBox cardPreview = KRegions.KVerticalBox("card-preview", Pos.CENTER, 0, content);

        // set aspect ratio of card
        cardPreview.prefHeightProperty().bind(cardPreview.widthProperty().multiply(9.0 / 16.0));

        // limit growth to hold aspect ratio
        VBox.setVgrow(cardPreview, Priority.NEVER);
        cardPreview.setMaxHeight(Region.USE_PREF_SIZE);

        // make content editable
        content.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                Card card = activeCard.get();
                if (previewFront.get()) {
                    KControls.editMode(cardPreview, content, s -> {
                        CardCreation newCard = new CardCreation(card.deckId(), card.position(), s, card.back());
                        cardChanges.set(newCard);
                    });
                } else {
                    KControls.editMode(cardPreview, content, s -> {
                        CardCreation newCard = new CardCreation(card.deckId(), card.position(), card.front(), s);
                        cardChanges.set(newCard);
                    });
                }
            }
        });

        // flip card action box
        Label side = KControls.KLabel("heading2-shadow", "Front");
        Button flipCard = KControls.KButton("yellow-button", KControls.KLabel("heading2-shadow", "Flip card"), _ -> {
            System.out.println(previewFront.get());
            previewFront.set(!previewFront.get());
            System.out.println(previewFront.get());
        });

        HBox flipBox = KRegions.KHorizontalBox("div-special", Pos.CENTER_LEFT, 20, side, flipCard);

        // root pane
        VBox creationContainer = KRegions.KVerticalBox("div", Pos.TOP_LEFT, 20, cardPreview, flipBox);

        // set width
        creationContainer.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(creationContainer, Priority.ALWAYS);

        // return it
        return creationContainer;
    }

    private void updateContent(Label label) {
        if (activeCard.get() == null) {
            label.setText("");
            return;
        }

        if (previewFront.get()) {
            label.setText(activeCard.get().front());
        } else {
            label.setText(activeCard.get().back());
        }
    }

    private Region buildEmpty() {
        return KControls.KLabel("heading", "Empty deck, nothing to show!");
    }
}
