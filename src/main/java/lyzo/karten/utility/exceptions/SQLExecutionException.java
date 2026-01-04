package lyzo.karten.utility.exceptions;

import java.util.Arrays;

public class SQLExecutionException extends RuntimeException {

    public SQLExecutionException(Exception initialException, String sql, Object... args) {
        super(initialException.getMessage());

        System.err.println("An error occurred while trying to execute SQL query. Could not execute SQL string: " + sql + ". The following arguments were used: " + Arrays.toString(args));
    }
}
