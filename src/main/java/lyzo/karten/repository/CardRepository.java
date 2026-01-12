package lyzo.karten.repository;

import lyzo.karten.database.DBAccess;
import lyzo.karten.mapper.CardMapper;
import lyzo.karten.model.Card;
import lyzo.karten.model.CardCreation;

import java.util.List;

public class CardRepository {

    private final DBAccess dbAccess;

    public CardRepository(DBAccess dbAccess) {
        this.dbAccess = dbAccess;
    }

    public List<Card> getCardsInDeck(int deckId) {
        String sql = "SELECT * FROM cards WHERE deck_id = ?";

        return dbAccess.executeQuery(sql, new CardMapper(), deckId);
    }

    public Card getCardById(int id) {
        String sql = "SELECT * FROM cards WHERE id = ?";

        return dbAccess.executeQuery(sql, new CardMapper(), id).getFirst();
    }

    public int insertCard(CardCreation cardCreation) {
        String sql = "INSERT INTO cards (deck_id, position, front, back) VALUES (?, ?, ?, ?)";

        return dbAccess.executeInsert(sql, cardCreation.deck_id(), cardCreation.position(), cardCreation.front(), cardCreation.back());
    }

    public void removeCard(Card c) {
        String sql = "DELETE FROM cards WHERE id = ?";
        dbAccess.executeUpdate(sql, c.id());
    }
}
