package lyzo.karten.database;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBAccess {

    private final String dbURL;

    public DBAccess(Path appDataPath) {
        // create database
        Path dbPath = appDataPath.resolve("karten.db");

        // construct URL for database connection
        dbURL = "jdbc:sqlite:" + dbPath;

        // establish connection
        try (Connection conn = DriverManager.getConnection(dbURL)) {
            System.out.println("Connection established successfully!");
        } catch (SQLException e) {
            throw new RuntimeException("Could not establish connection to database!");
        }
    }
}
