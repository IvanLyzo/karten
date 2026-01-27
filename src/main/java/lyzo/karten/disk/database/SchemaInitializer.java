package lyzo.karten.disk.database;

public class SchemaInitializer {

    private static final String CREATE_DECKS_TABLE = """
            CREATE TABLE IF NOT EXISTS decks (
                id INTEGER PRIMARY KEY,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                last_edited TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                name VARCHAR(22) NOT NULL,
                description VARCHAR(150) NOT NULL
            );
            """;

    private static final String CREATE_CARDS_TABLE = """
            CREATE TABLE IF NOT EXISTS cards (
                id INTEGER PRIMARY KEY,
                deck_id INTEGER NOT NULL,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                last_edited TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                position INTEGER NOT NULL,
                front VARCHAR(150) NOT NULL,
                back VARCHAR(150) NOT NULL
            );
            """;

    public static void initialize(DBAccess dbAccess) {
        initializeSchema(dbAccess, CREATE_DECKS_TABLE);
        initializeSchema(dbAccess, CREATE_CARDS_TABLE);
    }

    private static void initializeSchema(DBAccess dbAccess, String sqlString) {
        dbAccess.executeUpdate(sqlString);
    }
}
