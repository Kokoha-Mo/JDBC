package tw.lab.tutor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
//相當於在cmd中下mysql -u root -p
public class Lab02 {

	public static void main(String[] args) {
		//用JDBC語法跟mysql的localhost:3306建立溝通
		//使用者跟密碼用?代表開始輸入
		//--------------------
		//sql sever版
		//url = "jdbc:sqlsever://localhost:1433;databassName=iii;user=sa;password=123456"
		//******************
		//** 注意都不能有空白 **
		//******************
		//url,user,password寫一起
//		String url = "jdbc:mysql://localhost:3306/iii?user=root&password=root";
		//--------------------
		//通用寫法1{getConnection(url,user,password,...)}
		String url = "jdbc:mysql://localhost:3306/iii";
		String user = "root";
		String password = "root";
		//通用寫法2{getConnection(url,prop)}
		Properties prop = new Properties();
		prop.put("user", user);
		prop.put("password",password);
		
		try {
			Connection connet = DriverManager.getConnection(url,prop);
			connet.close();
			System.out.println("OK");
		} catch (SQLException e) {
			System.out.println(e);
		}

	}

}
