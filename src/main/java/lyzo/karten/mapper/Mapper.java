package lyzo.karten.mapper;

import java.sql.ResultSet;

@FunctionalInterface
public interface Mapper<T> {

    T map(ResultSet rs);
}
