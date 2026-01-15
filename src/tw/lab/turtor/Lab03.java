package tw.lab.turtor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Lab03 {
	
	private static final String URL = "jdbc:mysql://localhost:3306/iii";
	private static final String USER = "root";
	private static final String PASSWORD = "root";
	private static final String SQL_ISNERT = """
			INSERT INTO cust
				(cname,tel,birthday)
			VALUES
				('Amy','11223344','1999-04-05')
			""";
	private static final String SQL_UPDATE = """
			UPDATE cust
				SET tel = '3333', birthday = '2000-02-03'
			WHERE
				id >= 4
			""";
	private static final String SQL_DELETE = """
			DELETE FROM cust
			WHERE
				id >= 5
			""";

	public static void main(String[] args) {
		Properties prop = new Properties();
		prop.put("user", USER);
		prop.put("password",PASSWORD);
		
		try (	Connection connet = DriverManager.getConnection(URL,prop);
				Statement stmt = connet.createStatement()) {
			int n = stmt.executeUpdate(SQL_DELETE);
			System.out.println(n);
			System.out.println("OK");
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

}
