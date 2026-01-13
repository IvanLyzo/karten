package lyzo.karten.mapper;

import lyzo.karten.model.Card;
import lyzo.karten.utility.exceptions.SQLMappingException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CardMapper implements Mapper<Card> {

    @Override
    public Card map(ResultSet rs) {
        Card c;
        try {
            c = new Card(
                    rs.getInt("id"),
                    rs.getInt("deck_id"),
                    rs.getTimestamp("created_at").toInstant(),
                    rs.getTimestamp("last_edited").toInstant(),
                    rs.getInt("position"),
                    rs.getString("front"),
                    rs.getString("back")
            );
        } catch (SQLException e) {
            throw new SQLMappingException(e, Card.class, rs);
        }

        return c;
    }
}
