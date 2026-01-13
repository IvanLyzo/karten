package lyzo.karten.feature.editor;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import lyzo.karten.model.*;
import lyzo.karten.utility.interfaces.Controller;

public class EditorController implements Controller {

    private final AppModel appModel;

    private final ObjectProperty<DeckCreation> deckChanges = new SimpleObjectProperty<>();
    private final ObjectProperty<CardCreation> cardChanges = new SimpleObjectProperty<>();

    public EditorController(AppModel appModel) {
        this.appModel = appModel;

        deckChanges.addListener((obs, oldV, newV) -> {
            appModel.updateDeck(newV);
        });

        cardChanges.addListener((obs, oldV, newV) -> {
            System.out.println(newV);
            appModel.updateCard(newV);
        });
    }

    @Override
    public Region buildView() {
        EditorViewBuilder viewBuilder = new EditorViewBuilder(
                appModel.getActiveDeck(), deckChanges,
                appModel.getDeckCards(), appModel.getActiveCard(),cardChanges,
                this::newDeckAction);

        return viewBuilder.build();
    }

    private void newDeckAction(MouseEvent mouseEvent) {
        Card card = appModel.addCard();

        appModel.setActiveCard(card);
    }
}
