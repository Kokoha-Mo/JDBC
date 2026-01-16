package tw.lab.tutor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.Scanner;

import tw.lab.apis.BCrypt;

public class Lab10 {
	private static final String URL = "jdbc:mysql://localhost:3306/iii";
	private static final String USER = "root";
	private static final String PASSWORD = "root";
	private static final String SQL_REG = """
			INSERT INTO member
				(email,passwd,name)
			VALUES
				(?,?,?)
			""";
	private static final String SQL_CHECK1 = """
			SELECT email
			FROM member
			WHERE email = ?
			""";
	private static final String SQL_CHECK2 = """
			SELECT COUNT(email) count
			FROM member
			WHERE email = ?
			""";
	
	static final Properties prop = new Properties();
	
	public static void main(String[] args) {
		System.out.println("Memeber Register");
		Scanner scanner = new Scanner(System.in);
		System.out.println("Email:");
		String email = scanner.nextLine();
		System.out.println("Password:");
		String passwd = scanner.nextLine();
		System.out.println("Name:");
		String name = scanner.nextLine();
		

		prop.put("user", USER);
		prop.put("password", PASSWORD);
		
		try {
			if (!isEmailExist(email)) {
				if (regMember(email,passwd,name)) {
					System.out.println("Register Success");
				}else {
					System.out.println("Register Failure");
				}
			} else {
				System.out.println("Email XXX");
			}
			
		} catch (Exception e) {
			System.out.println("ERROR: 500");
		}
	}
		
//		static boolean isEmailExist(String email) throw Exception {
//			try(Connection conn = DriverManager.getConnection(URL,prop);
//					PreparedStatement pstmt = conn.prepareStatement(SQL_CHECK1)){
//				pstmt.setString(1, email);
//				ResultSet rs = pstmt.executeQuery();
//				return rs.next();
//			}
//		}
		
		static boolean isEmailExist(String email) throws Exception {
			try(Connection conn = DriverManager.getConnection(URL,prop);
					PreparedStatement pstmt = conn.prepareStatement(SQL_CHECK2)){
				pstmt.setString(1, email);
				ResultSet rs = pstmt.executeQuery();
				rs.next();
				int count = rs.getInt("count");
				return count > 0;
			}
		}
		
		static boolean regMember(String email,String passwd,String name) throws Exception {
			try (Connection conn = DriverManager.getConnection(URL,prop);
					PreparedStatement pstmt = conn.prepareStatement(SQL_REG)){
				
				pstmt.setString(1, email);
				pstmt.setString(2, BCrypt.hashpw(passwd, BCrypt.gensalt()));
				pstmt.setString(3, name);
				
				int n = pstmt.executeUpdate();
				return n > 0;
			}
		}
	
}
