package lyzo.karten.io.disk;

import lyzo.karten.mapper.Mapper;
import lyzo.karten.utility.exceptions.SQLExecutionException;
import lyzo.karten.utility.logger.Logger;

import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Decided against multiple DAOs for simple project, only one class will interact with database
public class DBAccess {

    // url to access the database
    private final String dbURL;

    public DBAccess(Path appDataPath) {
        // create database
        Path dbPath = appDataPath.resolve("karten.db");

        // construct URL for database connection
        dbURL = "jdbc:sqlite:" + dbPath;

        // create schemas (if not already existing)
        SchemaInitializer.initialize(this);

        // establish connection
        try (Connection conn = DriverManager.getConnection(dbURL)) {
            Logger.log("Connection established successfully!", Logger.DEBUG);
        } catch (SQLException e) {
            throw new RuntimeException("Could not establish connection to database!");
        }
    }

    // executeQuery is used for executing READ queries, mapping the result with a custom
    // Mapper functional interface, and returning a list of the results
    public <T> List<T> executeQuery(String sql, Mapper<T> mapper, Object... args) {
        // returnable list of result objects T
        List<T> results = new ArrayList<>();

        // establish database connection with a PreparedStatement
        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // add arguments
            for (int i = 0; i < args.length; i++) {
                stmt.setObject(i + 1, args[i]);
            }

            // execute query and map all results, adding them to list
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    results.add(mapper.map(rs));
                }
            }

        // catch SQLExceptions to throw our own runtime exceptions
        } catch (SQLException e) {
            throw new SQLExecutionException(e, sql, args);
        }

        // return list of objects
        return results;
    }

    // executeUpdate is used for executing any SQL commands that do not expect
    // a result to be given back (UPDATE, DELETE)
    public void executeUpdate(String sql, Object... args) {
        // establish database connection with PreparedStatement
        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // add arguments
            for (int i = 0; i < args.length; i++) {
                stmt.setObject(i + 1, args[i]);
            }

            // execute statement
            stmt.executeUpdate();

        // catch SQLExceptions to throw our own runtime exceptions
        } catch (SQLException e) {
            throw new SQLExecutionException(e, sql, args);
        }
    }

    // executeInsert is similar to executeUpdate, but returns the UID of the
    // newly created object, useful for CREATE operations when continuing work
    // on just-created objects via new UID
    public int executeInsert(String sql, Object... args) {
        // establish database connection with a PreparedStatement
        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // add arguments
            for (int i = 0; i < args.length; i++) {
                Object arg = args[i];
                if (arg instanceof String s) {
                    stmt.setString(i + 1, s);
                } else {
                    stmt.setObject(i + 1, arg);
                }
            }

            // execute statement
            stmt.executeUpdate();

            // return first key (UID) of affected row(s) (useful only for one-at-a-time CREATE)
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }

                // return universal sign of something wrong (UID always >= 0)
                else {
                   return -1;
                }
            }

        // catch SQLExceptions to throw our own runtime exceptions
        } catch (SQLException e) {
            throw new SQLExecutionException(e, sql, args);
        }
    }
}