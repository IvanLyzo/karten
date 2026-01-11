package lyzo.karten.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lyzo.karten.repository.DeckRepository;

// main app model, creating at runtime for each instance
// loaded from and saved to database
public class AppModel {

    // deck repository for database connection
    private final DeckRepository deckRepository;

    // observable list of all user-created decks
    private final ObservableList<Deck> decks = FXCollections.observableArrayList();

    // getter for binding and reading value
    public ObservableList<Deck> getDecks() {
        return decks;
    }

    // add one deck to collection (for CREATE operations)
    public Deck addDeck() {
        int id = deckRepository.insertDeck(new DeckCreation("New Deck #" + (decks.size() + 1), "Your very own deck of flashcards!"));

        Deck deck = deckRepository.getDeckById(id);
        decks.add(deck);

        return deck;
    }

    // property for active deck for editing or playing
    private final ObjectProperty<Deck> activeDeck = new SimpleObjectProperty<>();

    // getter for binding and reading value
    public ObjectProperty<Deck> getActiveDeck() {
        return activeDeck;
    }

    // setter for updating value
    public void setActiveDeck(Deck deck) {
        activeDeck.set(deck);
    }

    // delete the active deck
    public void deleteDeck() {
        deckRepository.removeDeck(activeDeck.get());

        decks.remove(activeDeck.get());
        activeDeck.set(null);
    }

    private final ObservableList<Card> deckCards = FXCollections.observableArrayList();

    public ObservableList<Card> getDeckCards() {
        return deckCards;
    }

    public AppModel(DeckRepository deckRepository) {
        // save deck repository access
        this.deckRepository = deckRepository;

        // load initial deck data
        decks.addAll(deckRepository.getAllDecks());
    }
}
