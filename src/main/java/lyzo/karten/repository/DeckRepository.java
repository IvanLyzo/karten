package lyzo.karten.repository;

import lyzo.karten.database.DBAccess;
import lyzo.karten.mapper.DeckMapper;
import lyzo.karten.model.Deck;

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
}
