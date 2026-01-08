package lyzo.karten.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lyzo.karten.repository.DeckRepository;
import lyzo.karten.utility.logger.Logger;

// main app model, creating at runtime for each instance
// loaded from and saved to database
public class AppModel {

    // observable list of all user-created decks
    private final ObservableList<Deck> decks = FXCollections.observableArrayList();

    // getter for binding and reading value
    public ObservableList<Deck> getDecks() {
        return decks;
    }

    // add one deck to collection (for CREATE operations)
    public void addDeck(Deck deck) {
        decks.add(deck);
    }

    public AppModel(DeckRepository deckRepository) {
        decks.addAll(deckRepository.getAllDecks());

        Logger.getInstance().log(decks.toString(), Logger.NORMAL);
    }
}
