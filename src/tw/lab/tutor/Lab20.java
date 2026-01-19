package tw.lab.tutor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Lab20 {
	private static final String URL = "jdbc:mysql://localhost:3306/northwind";
	private static final String USER = "root";
	private static final String PASSWORD = "root";
	private static final String SQL_QUERY = """
			SELECT o.EmployeeID id,e.LastName name,SUM(od.UnitPrice*od.Quantity) sum
			FROM `orders` o
				JOIN employees e ON o.EmployeeID = e.EmployeeID
				JOIN orderdetails od ON o.OrderID = od.OrderID
			GROUP BY o.EmployeeID
			ORDER BY sum DESC
			""";
	public static void main(String[] args) {
		try(	Connection conn = DriverManager.getConnection(URL,USER,PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(SQL_QUERY);
				ResultSet rs = pstmt.executeQuery();
				){
			while (rs.next()) {
				String id = rs.getString("id");
				String name = rs.getString("name");
				String sum = rs.getString("sum");
				System.out.printf("%s:%s:%s\n",id,name,sum);
			}
		}catch (Exception e) {
			System.out.println(e);
		}

	}

}
