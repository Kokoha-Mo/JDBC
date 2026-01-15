package tw.lab.turtor;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Properties;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

public class Lab06 {
	
	private static final String URL_OPENDATA = "https://data.moa.gov.tw/Service/OpenData/ODwsv/ODwsvAgriculturalProduce.aspx";
	private static final String URL = "jdbc:mysql://localhost:3306/iii";
	private static final String USER = "root";
	private static final String PASSWORD = "root";
	private static final String SQL_INSERT = """
			INSERT INTO gift
				(name,feature,addr,tel,picurl,lat,lng)
			VALUES
				(?,?,?,?,?,?,?)
			""";
	private static final String SQL_DEL_ALL = """
			DELETE FROM gift
			""";
	private static final String SQL_RESET_AUTO_INCREMENT = """
			ALTER TABLE gift AUTO_INCREMENT = 1
			""";

	public static void main(String[] args) {
			String json;
			try {			
				json = fetchFromURL(URL_OPENDATA);
				parseJSON(json);
				System.out.println("Finish");
			}catch (Exception e) {
				System.out.println(e);
			}
		}
	
	static String fetchFromURL(String url) throws Exception {
		URL target = new URL(url);
		URLConnection connet = target.openConnection();
		
		try(BufferedInputStream bin = 
				new BufferedInputStream(connet.getInputStream())){			
			byte[] data = bin.readAllBytes();
			return new String(data);
		}
		
		
		
//		try(BufferedReader reader = new BufferedReader(
//				new InputStreamReader(connet.getInputStream()))){
//			String line = null;
//			StringBuffer sb = new StringBuffer();
//			while ((line = reader.readLine()) != null){
//				sb.append(line);
//			}
//			return sb.toString();		
//		}

	}
	
	static void parseJSON(String json) throws Exception {
		JSONArray root = new JSONArray(json);
		System.out.println(root.length());
		
		Properties prop = new Properties();
		prop.put("user",USER);
		prop.put("password",PASSWORD);
		try(Connection connet = DriverManager.getConnection(URL, prop);
				PreparedStatement pstmt = connet.prepareStatement(SQL_INSERT)){
			
			pstmt.execute(SQL_DEL_ALL);
			pstmt.execute(SQL_RESET_AUTO_INCREMENT);
			
			for (int i = 0 ;i < root.length();i++) {
				JSONObject row = root.getJSONObject(i);
	//			System.out.println(row.getString("Name"));
				
				String name = row.getString("Name");
				String feature = row.getString("Feature");		
				String addr = row.getString("County") + 
							row.getString("Township") + 
							row.getString("SalePlace");
				String tel = row.getString("ContactTel");
				String picurl = row.getString("Column1");
				String lat = row.getString("Longitude");
				String lng = row.getString("Latitude");
				
				pstmt.setString(1, name);
				pstmt.setString(2, feature);
				pstmt.setString(3, addr);
				pstmt.setString(4, tel);
				pstmt.setString(5, picurl);
				try {				
					pstmt.setDouble(6, Double.parseDouble(lat));
					pstmt.setDouble(7, Double.parseDouble(lng));
				}catch (Exception e) {
					pstmt.setDouble(6, 0.0);
					pstmt.setDouble(7, 0.0);
				}
				
				pstmt.executeUpdate();		
			}
		}
	}
}
