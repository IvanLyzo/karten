package lyzo.karten.feature.library;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import lyzo.karten.model.AppModel;
import lyzo.karten.model.Deck;
import lyzo.karten.utility.interfaces.Controller;

public class LibraryController implements Controller {

    private final AppModel appModel;

    private final EventHandler<MouseEvent> selectDeckAction;

    public LibraryController(AppModel appModel, EventHandler<MouseEvent> selectDeckAction) {
        // save app model
        this.appModel= appModel;

        // save select deck action
        this.selectDeckAction = selectDeckAction;
    }

    @Override
    public Region buildView() {
        LibraryViewBuilder viewBuilder = new LibraryViewBuilder(appModel.getDecks(), this::createDeck);

        return viewBuilder.build();
    }

    private void createDeck(MouseEvent mouseEvent) {
        Deck deck = appModel.addDeck();
        appModel.setActiveDeck(deck);

        selectDeckAction.handle(mouseEvent);
    }
}
