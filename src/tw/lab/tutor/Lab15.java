package tw.lab.tutor;


import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Lab15 {
	
	private static final String URL = "jdbc:mysql://localhost:3306/iii";
	private static final String USER = "root";
	private static final String PASSWORD = "root";
	private static final String SQL_QUERY = """
			SELECT id,email,name,icon
			FROM member
			WHERE id = ?
			""";
	
	public static void main(String[] args) {
		try(	Connection conn = DriverManager.getConnection(URL,USER,PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(SQL_QUERY);
				){
			
			pstmt.setInt(1, 3);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				String email = rs.getString("email");
				String fname = String.format("dir2/%s.png",email);
				InputStream in = rs.getBinaryStream("icon");
				byte[] data = in.readAllBytes();
				
				try(FileOutputStream fout = new FileOutputStream(fname)){
					fout.write(data);
					fout.flush();					
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
