package lyzo.karten.feature.editor;

import javafx.scene.layout.Region;
import lyzo.karten.model.Deck;
import lyzo.karten.utility.interfaces.Controller;

public class EditorController implements Controller {

    private final Deck deck;

    public EditorController(Deck deck) {
        this.deck = deck;
    }

    @Override
    public Region buildView() {
        EditorViewBuilder viewBuilder = new EditorViewBuilder(deck.name());

        return viewBuilder.build();
    }
}
