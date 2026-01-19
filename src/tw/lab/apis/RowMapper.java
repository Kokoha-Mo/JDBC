package tw.lab.apis;

import java.sql.ResultSet;
import java.sql.SQLException;

// 泛型習慣用<T>(Type)表示

@FunctionalInterface
public interface RowMapper<T> {
	// 介面不須實作，直接;結束
	// 有關ResultSet幾乎90%都會拋出SQLException
	T mapRow(ResultSet rs) throws SQLException;
}
