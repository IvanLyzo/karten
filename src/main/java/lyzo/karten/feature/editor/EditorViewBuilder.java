package lyzo.karten.feature.editor;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import lyzo.karten.model.Card;
import lyzo.karten.model.Deck;
import lyzo.karten.utility.interfaces.ViewBuilder;
import lyzo.karten.utility.logger.Logger;
import lyzo.karten.utility.ui.KControls;
import lyzo.karten.utility.ui.KRegions;

public class EditorViewBuilder implements ViewBuilder {

    private final ObjectProperty<Deck> deck;

    private final ObservableList<Card> deckCards;

    public EditorViewBuilder(ObjectProperty<Deck> deck, ObservableList<Card> deckCards) {
        this.deck = deck;
        this.deckCards = deckCards;
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

        // root pane
        VBox pane = KRegions.KVerticalBox("", Pos.TOP_LEFT,
                titleBox,
                subtitleBox,
                KControls.KSeparator("", Orientation.HORIZONTAL),
                nameBox,
                descriptionBox,
                workspace()
        );

        // set min width and spacing
        pane.setMinWidth(500);
        pane.setSpacing(40);

        // return it
        return pane;
    }

    private Node workspace() {
        ListView<Card> cardListView = new ListView<>();

        // add listener to selection model
        cardListView.getSelectionModel().selectedItemProperty().addListener((obs, oldCard, newCard) -> {
            Logger.log("selected card: " + newCard.front(), Logger.INFO);
        });

        // configure visual style of list cells
        cardListView.setCellFactory(lv -> new ListCell<>() {

            @Override
            protected void updateItem(Card card, boolean empty) {
                super.updateItem(card, empty);

                if (empty || card == null) {
                    setText(null);
                    setGraphic(null);
                    return;
                }

                Label id = KControls.KLabel("heading2-shadow", card.id() + "");
                HBox cardBox = KRegions.KHorizontalBox("div", Pos.CENTER_LEFT, 20, id);

                setGraphic(cardBox);
            }
        });

        // populate list view
        populateCardList(cardListView);

        // make reactive with card list changes
        deckCards.addListener((ListChangeListener<Card>) _ -> populateCardList(cardListView));

        // create workspace container
        HBox workspaceBox = KRegions.KHorizontalBox("div", Pos.CENTER_LEFT, 20, cardListView);
        workspaceBox.setMaxWidth(Region.USE_PREF_SIZE);

        // return it
        return workspaceBox;
    }

    private void populateCardList(ListView<Card> cardListView) {
        cardListView.getItems().clear();

        cardListView.getItems().addAll(deckCards);
    }
}
