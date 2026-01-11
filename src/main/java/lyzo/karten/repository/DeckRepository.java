package lyzo.karten.repository;

import lyzo.karten.database.DBAccess;
import lyzo.karten.mapper.DeckMapper;
import lyzo.karten.model.Deck;
import lyzo.karten.model.DeckCreation;

import java.util.List;

public class DeckRepository {

    private final DBAccess dbAccess;

    public DeckRepository(DBAccess dbAccess) {
        this.dbAccess = dbAccess;
    }

    public List<Deck> getAllDecks() {
        String sql = "SELECT * FROM decks";

        return dbAccess.executeQuery(sql, new DeckMapper());
    }

    public Deck getDeckById(int id) {
        String sql = "SELECT * FROM decks WHERE id = ?";

        return dbAccess.executeQuery(sql, new DeckMapper(), id).getFirst();
    }

    public int insertDeck(DeckCreation deckCreation) {
        String sql = "INSERT INTO decks (name, description) VALUES (?, ?)";

        return dbAccess.executeInsert(sql, deckCreation.name(), deckCreation.description());
    }

    public void removeDeck(Deck d) {
        String sql = "DELETE FROM decks WHERE id = ?";
        dbAccess.executeUpdate(sql, d.id());
    }
}
