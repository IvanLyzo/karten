package lyzo.karten.feature.library;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import lyzo.karten.model.AppModel;
import lyzo.karten.model.Deck;
import lyzo.karten.utility.interfaces.Controller;

// library controller, in charge of the deck library view
public class LibraryController implements Controller {

    // application model
    private final AppModel appModel;

    // passed-down action
    private final EventHandler<MouseEvent> playDeckAction;
    private final EventHandler<MouseEvent> selectDeckAction;

    // save passed-down information
    public LibraryController(AppModel appModel, EventHandler<MouseEvent> playDeckAction, EventHandler<MouseEvent> selectDeckAction) {
        this.appModel= appModel;
        this.playDeckAction = playDeckAction;
        this.selectDeckAction = selectDeckAction;
    }

    @Override
    public Region buildView() {
        // create a library view builder
        LibraryViewBuilder viewBuilder = new LibraryViewBuilder(appModel.getDecks(), appModel.getActiveDeck(),
                this::createDeck,
                playDeckAction,
                selectDeckAction,
                this::deleteDeck);

        // display it
        return viewBuilder.build();
    }

    // action for creating a deck
    private void createDeck(MouseEvent mouseEvent) {
        // create the deck
        Deck deck = appModel.addDeck();
        appModel.setActiveDeck(deck);

        // pass along for further action with active deck
        selectDeckAction.handle(mouseEvent);
    }

    private void deleteDeck(MouseEvent mouseEvent) {
        appModel.deleteDeck();
    }
}
