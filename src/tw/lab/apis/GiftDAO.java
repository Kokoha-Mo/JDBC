package tw.lab.apis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;


public class GiftDAO {
	private static final String URL = "jdbc:mysql://localhost:3306/iii";
	private static final String USER = "root";
	private static final String PASSWORD = "root";
	private static final String SQL_FIND_ALL = """
			SELECT id,name,feature,addr,tel
			FROM gift
			ORDER BY id
			""";
	private static Connection conn;
	private static PreparedStatement pstmt;
	private static ResultSet rs;
	private static String[] fieldNames;
	public GiftDAO() throws Exception{
		
		conn = DriverManager.getConnection(URL,USER,PASSWORD);
		pstmt = conn.prepareStatement(SQL_FIND_ALL,
				ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		rs = pstmt.executeQuery();
		ResultSetMetaData rsmd = rs.getMetaData();
		fieldNames = new String[rsmd.getColumnCount()];
		for(int i = 0;i < fieldNames.length;i++) {
			fieldNames[i] = rsmd.getColumnName(i+1);
		}
	}
	
	
	public int getRows() {
		try {
			rs.last();
			return rs.getRow();
		} catch (SQLException e) {
			System.out.println(e);
			return 0;
		}
	}
	
	public int getCols() {
		return fieldNames.length;
	}
	
	public String getData(int row,int col) {
		try {
			rs.absolute(row+1);
			return rs.getString(col+1);
		} catch (SQLException e) {
			System.out.println(e);
			return "ERROR";
		}
		
	}


	public String[] getFields() {return fieldNames;}
	
	public void updateData(String newData, int row, int col) {
		try {
			rs.absolute(row+1);
			rs.updateString(col+1, newData);
			rs.updateRow();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
	
	public void delRow(int row) {
		try {
			rs.absolute(row+1);
			rs.deleteRow();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
	
}
