package tw.lab.tutor;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import tw.lab.apis.Bike;

public class Lab16 {
	
	private static final String URL = "jdbc:mysql://localhost:3306/iii";
	private static final String USER = "root";
	private static final String PASSWORD = "root";
	private static final String SQL_UPDATE = """
			UPDATE member
			SET bike = ?
			WHERE id = ?
			""";
	
	public static void main(String[] args) {
		
		Bike bike = new Bike();
		bike.upSpeed().upSpeed();
		System.out.println(bike);
		
		try(	Connection conn = DriverManager.getConnection(URL,USER,PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(SQL_UPDATE)){
			
			pstmt.setObject(1, bike);
			pstmt.setInt(2, 4);
			int n = pstmt.executeUpdate();
			System.out.println(n);
			
		}catch (Exception e) {
			System.out.println(e);
		}
	}
}
