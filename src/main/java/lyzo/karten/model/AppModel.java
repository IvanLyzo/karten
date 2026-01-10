package lyzo.karten.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lyzo.karten.repository.DeckRepository;

// main app model, creating at runtime for each instance
// loaded from and saved to database
public class AppModel {

    private final DeckRepository deckRepository;

    // observable list of all user-created decks
    private final ObservableList<Deck> decks = FXCollections.observableArrayList();

    // property for active deck for editing or playing
    private final ObjectProperty<Deck> activeDeck = new SimpleObjectProperty<>();

    // getter for binding and reading value
    public ObservableList<Deck> getDecks() {
        return decks;
    }

    // add one deck to collection (for CREATE operations)
    public Deck addDeck() {
        int id = deckRepository.insertDeck(new DeckCreation("New Deck #" + decks.size(), ""));

        Deck deck = deckRepository.getDeckById(id);
        decks.add(deck);

        return deck;
    }

    public void setActiveDeck(Deck deck) {
        activeDeck.set(deck);
    }

    public Deck getActiveDeck() {
        return activeDeck.get();
    }

    public AppModel(DeckRepository deckRepository) {
        this.deckRepository = deckRepository;

        decks.addAll(deckRepository.getAllDecks());
    }
}
