package lyzo.karten.mapper;

import java.sql.ResultSet;

/// functional interface for a DAO-to-POJO mapping,
/// used for dynamic querying of database

@FunctionalInterface
public interface Mapper<T> {

    T map(ResultSet rs);
}
