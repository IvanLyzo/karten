package lyzo.karten.database;

import lyzo.karten.mapper.Mapper;

import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public <T> List<T> executeQuery(String sql, Mapper<T> mapper, Object... args) {
        List<T> results = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < args.length; i++) {
                stmt.setObject(i + 1, args[i]);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    results.add(mapper.map(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return results;
    }

    public void executeUpdate(String sql, Object... args) {
        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < args.length; i++) {
                stmt.setObject(i + 1, args[i]);
            }

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int executeInsert(String sql, Object... args) {
        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            for (int i = 0; i < args.length; i++) {
                stmt.setObject(i + 1, args[i]);
            }

            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                } else {
                    throw new SQLException("Insert failed, no ID returned.");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}