package tw.lab.tutor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;
//防範 SQL 注入攻擊 (SQL Injection)
public class Lab05 {
	
	private static final String URL = "jdbc:mysql://localhost:3306/iii";
	private static final String USER = "root";
	private static final String PASSWORD = "root";
	private static final String SQL_INSERT = """
			INSERT INTO cust
				(cname,tel,birthday)
			VALUES
				(?,?,?)
			""";

	public static void main(String[] args) {
		Properties prop = new Properties();
		prop.put("user",USER);
		prop.put("password",PASSWORD);
		
		try (	Connection connet = DriverManager.getConnection(URL,prop);
				PreparedStatement pstmt = connet.prepareStatement(SQL_INSERT);) {
				
				Scanner scanner = new Scanner(System.in);
				System.out.print("Name:");
				String cname = scanner.next();
				System.out.print("Tel:");
				String tel = scanner.next();
				System.out.print("Birthday:");
				String birthday = scanner.next();
			
				pstmt.setString(1, cname);
				pstmt.setString(2, tel);
				pstmt.setString(3, birthday);
				
				int n = pstmt.executeUpdate();
				System.out.println(n);
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

}
