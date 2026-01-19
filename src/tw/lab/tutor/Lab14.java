package tw.lab.tutor;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Lab14 {
	
	private static final String URL = "jdbc:mysql://localhost:3306/iii";
	private static final String USER = "root";
	private static final String PASSWORD = "root";
	private static final String SQL_UPDATE = """
			UPDATE member
			SET icon = ?
			WHERE id = ?
			""";
	
	public static void main(String[] args) {
		try(FileInputStream fin = new FileInputStream("C:\\Users\\User\\eclipse-workspace\\lab\\dir1\\FF14_logo.png");
				Connection conn = DriverManager.getConnection(URL,USER,PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(SQL_UPDATE)){
			
			pstmt.setBinaryStream(1, fin);
			pstmt.setInt(2, 4);
			int n = pstmt.executeUpdate();
			System.out.println(n);
			
		}catch (Exception e) {
			System.out.println(e);
		}
	}
}
