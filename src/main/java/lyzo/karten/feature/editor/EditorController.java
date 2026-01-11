package lyzo.karten.feature.editor;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.layout.Region;
import lyzo.karten.model.Card;
import lyzo.karten.model.Deck;
import lyzo.karten.utility.interfaces.Controller;

public class EditorController implements Controller {

    private final ObjectProperty<Deck> deck;

    private final ObservableList<Card> deckCards;

    public EditorController(ObjectProperty<Deck> deck, ObservableList<Card> deckCards) {
        this.deck = deck;
        this.deckCards = deckCards;
    }

    @Override
    public Region buildView() {
        EditorViewBuilder viewBuilder = new EditorViewBuilder(deck, deckCards);

        return viewBuilder.build();
    }
}
