package lyzo.karten.model;

import java.time.Instant;

// record is immutable, ensures sync with database by not allowing direct mutability
public record Deck(int id, Instant createdAt, Instant lastEdited, String name, String description) {
    public record CreateData(String name, String description) {

    }
}
