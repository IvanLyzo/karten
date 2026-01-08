package lyzo.karten.model;

import java.time.Instant;

// record is immutable, ensures sync with database by not allowing direct mutability
public record Card(int id, int deckId, Instant createdAt, Instant lastEdited, int position, String front, String back) { }
