package lyzo.karten.utility.exceptions;

import java.sql.ResultSet;

public class SQLMappingException extends RuntimeException {

    public SQLMappingException(Exception initialException, Class<?> type, ResultSet rs) {
        super(initialException.getMessage());

        System.err.println("An error occurred while trying to map result to Java object. Could not map ResultSet: " + rs + ". The destination was the following class: " + type);
    }
}
