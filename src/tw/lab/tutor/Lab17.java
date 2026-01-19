package tw.lab.tutor;


import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import tw.lab.apis.Bike;

public class Lab17 {
	
	private static final String URL = "jdbc:mysql://localhost:3306/iii";
	private static final String USER = "root";
	private static final String PASSWORD = "root";
	private static final String SQL_QUERY = """
			SELECT id,email,name,bike
			FROM member
			WHERE id = ?
			""";
	
	public static void main(String[] args) {
		try(	Connection conn = DriverManager.getConnection(URL,USER,PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(SQL_QUERY);
				){
			
			pstmt.setInt(1, 4);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				ObjectInputStream oin = new ObjectInputStream(rs.getBinaryStream("bike"));
				Object obj = oin.readObject();
				if (obj instanceof Bike) {
					Bike bike = (Bike)obj;
					System.out.println(bike);
				}
				System.out.println("OK");
			}else {
				System.out.println("Member Dont Exist");
			}
			
		}catch (Exception e) {
			System.out.println(e);
		}
	}
}
