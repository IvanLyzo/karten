package lyzo.karten.feature.editor;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import lyzo.karten.model.AppModel;
import lyzo.karten.model.Card;
import lyzo.karten.model.Deck;
import lyzo.karten.model.DeckCreation;
import lyzo.karten.utility.interfaces.Controller;

public class EditorController implements Controller {

    private final AppModel appModel;

    private final ObjectProperty<DeckCreation> deckChanges = new SimpleObjectProperty<>();

    public EditorController(AppModel appModel) {
        this.appModel = appModel;

        deckChanges.addListener((obs, oldV, newV) -> {
            appModel.updateDeck(newV);
        });
    }

    @Override
    public Region buildView() {
        EditorViewBuilder viewBuilder = new EditorViewBuilder(appModel.getActiveDeck(), deckChanges, appModel.getDeckCards(), appModel.getActiveCard(), this::newDeckAction);

        return viewBuilder.build();
    }

    private void newDeckAction(MouseEvent mouseEvent) {
        Card card = appModel.addCard();

        appModel.setActiveCard(card);
    }
}
