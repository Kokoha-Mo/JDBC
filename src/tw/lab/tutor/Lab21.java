package tw.lab.tutor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Lab21 {
	private static final String URL = "jdbc:mysql://localhost:3306/iii";
	private static final String USER = "root";
	private static final String PASSWORD = "root";
	private static final String SQL_QUERY = """
			SELECT id,name,addr,tel,feature,lat,lng,picurl
			FROM gift
			""";
	public static void main(String[] args) {
		try (	Connection conn = DriverManager.getConnection(URL,USER,PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(SQL_QUERY,
						ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_UPDATABLE);
				ResultSet rs = pstmt.executeQuery();
				){
			rs.next();
			System.out.println(rs.getString("name"));
			rs.next();
			System.out.println(rs.getString("name"));
			rs.next();
			System.out.println(rs.getString("name"));
			//更新資料
			//移動到指定筆資料
			rs.absolute(8);
//			System.out.println(rs.getString("name"));
//			rs.updateString("feature", "很香很好喝的貴妃茶");
//			rs.updateString("addr", "賣茶的地方");
//			rs.updateRow(); //更新資料庫
			System.out.printf("%s:%s\n",rs.getString("name"),rs.getString("addr"));
			//刪除資料
//			rs.absolute(13);
//			rs.deleteRow();
			//新增資料
//			rs.moveToInsertRow();
//			rs.updateString("name", "新商品");
//			rs.updateString("feature","要買要快");
//			rs.updateString("addr", "詐騙公司");
//			rs.updateString("tel", "04-12345678");
//			rs.updateString("picurl", "https://newig");
//			rs.updateDouble("lat", 24);
//			rs.updateDouble("lng", 123);
//			rs.insertRow();
			
		}catch (Exception e) {
			System.out.println(e);
		}

	}

}
