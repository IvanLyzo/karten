package lyzo.karten.mapper;

import lyzo.karten.model.Deck;
import lyzo.karten.utility.exceptions.SQLMappingException;

import java.sql.ResultSet;
import java.sql.SQLException;

 public class DeckMapper implements Mapper<Deck> {

    @Override
    public Deck map(ResultSet rs) {
        Deck d = null;
        try {
            d = new Deck(rs.getInt("id"), rs.getTimestamp("created_at").toInstant(), rs.getTimestamp("last_edited").toInstant(), rs.getString("name"), rs.getString("description"));
        } catch (SQLException e) {
            throw new SQLMappingException(e, Deck.class, rs);
        }

        return d;
    }
}
