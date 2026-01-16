package tw.lab.tutor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.Scanner;

import tw.lab.apis.BCrypt;
import tw.lab.apis.Member;

public class Lab11 {

		private static final String URL = "jdbc:mysql://localhost:3306/iii";
		private static final String USER = "root";
		private static final String PASSWORD = "root";
		static final Properties prop = new Properties();
		private static final String SQL_LOGIN = """
				SELECT id,email,passwd,name
				FROM member
				WHERE email = ?
				""";
		//因為密碼有加密，所以先抓email對的，後續再去比密碼
		private static final String SQL_CHPASSWD = """
				UPDATE member
				SET passwd = ?
				WHERE id = ?
				""";
		public static void main(String[] args) {
			System.out.println("Memeber Login");
			Scanner scanner = new Scanner(System.in);
			System.out.println("Email:");
			String email = scanner.nextLine();
			System.out.println("Password:");
			String passwd = scanner.nextLine();
			
			prop.put("user", USER);
			prop.put("password", PASSWORD);
			// 1.Login
			// Member member = login()
			//System.out.printf("Welcome,%s\n",member.getName());
			//System.out.print("Change Password");
			//String chPasswd =scanner.nextLine();
			
			// 2.Change Password
			//boolean isOK = chPasswd(member)
			try(Connection conn = DriverManager.getConnection(URL,prop);
					PreparedStatement pstmt = conn.prepareStatement(SQL_LOGIN)){
					pstmt.setString(1, email);
					ResultSet rs = pstmt.executeQuery();
					if (rs.next()) {
						if (BCrypt.checkpw(passwd, rs.getString("passwd"))) {
							Member member = new Member(
									rs.getLong("id"),
									rs.getString("email"),
									rs.getString(""),
									rs.getString("name")) ;
							System.out.printf("Welcome,%s\n",member.getName());
							System.out.print("Change Password");
							String chPasswd =scanner.nextLine();
							if (chPasswd == "") {
								
							} else {
								
							}
						}
					}else {
						System.out.println("Login Failure");
					}
			}catch (Exception e) {
				System.out.println(e);
			}
	}

}
