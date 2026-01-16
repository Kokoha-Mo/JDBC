package tw.lab.tutor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.Scanner;


public class Lab08 {

	private static final String URL = "jdbc:mysql://localhost:3306/iii";
	private static final String USER = "root";
	private static final String PASSWORD = "root";
	private static final String SQL_QUERY = """
			SELECT id,name,tel,addr,feature
			FROM gift
			WHERE name LIKE ? OR tel LIKE ? OR addr LIKE ? OR feature LIKE ? 
			ORDER BY id
			LIMIT ?,?
			""";
	//MSSQL分頁查詢強制ORDER BY
	//LIMIT 起始資料(不包含),資料筆數(EX: LIMITE 20,10 >>> S21~30)
	public static void main(String[] args) {
		
		System.out.println("請輸入查詢頁數(每頁10筆):");
		Scanner scanner = new Scanner(System.in);
		int page = scanner.nextInt();
		System.out.println("請輸入查詢關鍵字:");
		String key = scanner.next();
		String skey = "%" + key + "%";
//		scanner.close();
//		key.close();
		
		final int rpp = 10;
		int start = (page - 1) * rpp;
		
		
		
		Properties prop = new Properties();
		prop.put("user",USER);
		prop.put("password",PASSWORD);
		
		
		try(Connection connet = DriverManager.getConnection(URL, prop);
				PreparedStatement pstmt = connet.prepareStatement(SQL_QUERY)){
			pstmt.setString(1, skey);
			pstmt.setString(2, skey);
			pstmt.setString(3, skey);
			pstmt.setString(4, skey);
			pstmt.setInt(5, start);
			pstmt.setInt(6, rpp);
			
//			pstmt.setInt(1, start);
//			pstmt.setInt(2, rpp);
			
			
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				String id = rs.getString("id");
				String name = rs.getString("name");
				String tel = rs.getString("tel");
				String addr = rs.getString("addr");
				String feature = rs.getString("feature");
				System.out.printf("%s:%s:%s:%s\n\t%s\n",id,name,tel,addr,feature);	
			}
			

		}catch (Exception e) {
			System.out.println(e);
		}
	}
}


