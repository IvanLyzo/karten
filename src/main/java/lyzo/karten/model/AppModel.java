package lyzo.karten.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lyzo.karten.repository.CardRepository;
import lyzo.karten.repository.DeckRepository;
import lyzo.karten.utility.logger.Logger;

// main app model, creating at runtime for each instance
// loaded from and saved to database
public class AppModel {

    // deck repository for database connection
    private final DeckRepository deckRepository;

    // card repository for database connection
    private final CardRepository cardRepository;


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

    // replace deck for updating deck entries
    public void updateDeck(DeckCreation newDeck) {
        // update entry in database
        Deck deck = deckRepository.updateDeck(activeDeck.get().id(), newDeck);

        // update presence in list
        for (int i = 0; i < decks.size(); i++) {
            if (decks.get(i) == activeDeck.get()) {
                decks.set(i, deck);
                break;
            }
        }

        // update active deck reference
        activeDeck.set(deck);
    }

    // delete the active deck
    public void deleteDeck() {
        // remove associated card references
        for (Card deckCard : deckCards) {
            cardRepository.removeCard(deckCard);
        }
        deckCards.clear();

        // remove deck references
        deckRepository.removeDeck(activeDeck.get());

        decks.remove(activeDeck.get());
        activeDeck.set(null);
    }

    // observable list of active deck's cards
    private final ObservableList<Card> deckCards = FXCollections.observableArrayList();

    public ObservableList<Card> getDeckCards() {
        return deckCards;
    }

    public Card addCard() {
        int id = cardRepository.insertCard(new CardCreation(activeDeck.get().id(), deckCards.size(), "Front Content", "Back Content"));

        Card card = cardRepository.getCardById(id);

        deckCards.add(card);

        return card;
    }

    private final ObjectProperty<Card> activeCard = new SimpleObjectProperty<>();

    public ObjectProperty<Card> getActiveCard() {
        return activeCard;
    }

    public void setActiveCard(Card c) {
        activeCard.set(c);
    }

    public AppModel(DeckRepository deckRepository, CardRepository cardRepository) {
        // save repository access
        this.deckRepository = deckRepository;
        this.cardRepository = cardRepository;

        // load initial deck data
        decks.addAll(deckRepository.getAllDecks());

        // tie card list to active deck
        activeDeck.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                deckCards.clear();
            } else {
                deckCards.setAll(cardRepository.getCardsInDeck(newValue.id()));
            }
        });
    }
}
