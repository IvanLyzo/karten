package lyzo.karten.repository;

import lyzo.karten.disk.database.DBAccess;
import lyzo.karten.mapper.CardMapper;
import lyzo.karten.model.Card;

import java.sql.Timestamp;
import java.time.Instant;
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

    public int insertCard(Card.CreateData cardCreation) {
        String sql = "INSERT INTO cards (deck_id, position, front, back) VALUES (?, ?, ?, ?)";

        return dbAccess.executeInsert(sql, cardCreation.deck_id(), cardCreation.position(), cardCreation.front(), cardCreation.back());
    }

    public Card updateCard(int cardId, Card.CreateData newCard) {
        String sql = "UPDATE cards SET last_edited = ?, position = ?, front = ?, back = ? WHERE id = ?";

        dbAccess.executeUpdate(sql, Timestamp.from(Instant.now()), newCard.position(), newCard.front(), newCard.back(), cardId);

        return getCardById(cardId);
    }

    public void removeCard(Card c) {
        String sql = "DELETE FROM cards WHERE id = ?";
        dbAccess.executeUpdate(sql, c.id());
    }
}
