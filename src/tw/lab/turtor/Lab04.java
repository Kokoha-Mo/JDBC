package tw.lab.turtor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Lab04 {
	
	private static final String URL = "jdbc:mysql://localhost:3306/iii";
	private static final String USER = "root";
	private static final String PASSWORD = "root";
	private static final String SQL_QUERY = """
			SELECT cname name,birthday FROM cust
			""";

	public static void main(String[] args) {
		Properties prop = new Properties();
		prop.put("user", USER);
		prop.put("password",PASSWORD);
		
		try (	Connection connet = DriverManager.getConnection(URL,prop);
				Statement stmt = connet.createStatement();
				ResultSet rs = stmt.executeQuery(SQL_QUERY);) {
			while (rs.next()) {
				String f1 = rs.getString("name");
				System.out.println(f1);
			}
			System.out.println("OK");
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

}
