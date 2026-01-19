package tw.lab.tutor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import tw.lab.apis.BCrypt;
import tw.lab.apis.Member;

public class Lab11ChangePasswd {

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
		// 1.Login
			// Member member = login()
			//System.out.printf("Welcome,%s\n",member.getName());
			//System.out.print("Change Password");
			//String chPasswd =scanner.nextLine();
		// 2.Change Password
			//boolean isOK = chPasswd(member)
		public static void main(String[] args) {		
			prop.put("user", USER);
			prop.put("password", PASSWORD);
			Member member = login();
			if (member != null) {
				System.out.printf("Welcome,%s\n",member.getName());
				boolean isOK = chPasswd(member);
				if(isOK) {System.out.println("Password Change Success");}
				else {System.out.println("Password Change Cancel");}
			}

			
	}
		
		
		static Member login() {
			System.out.println("Memeber Login");
			Scanner scanner = new Scanner(System.in);
			System.out.println("Email:");
			String email = scanner.nextLine();
			
			try(Connection conn = DriverManager.getConnection(URL,prop);
					PreparedStatement pstmt = conn.prepareStatement(SQL_LOGIN)){
					pstmt.setString(1, email);
					ResultSet rs = pstmt.executeQuery();
					if (rs.next()) {
						System.out.println("Password:");
						String passwd = scanner.nextLine();
						if (BCrypt.checkpw(passwd, rs.getString("passwd"))) {
							Member member = new Member(
									rs.getLong("id"),
									rs.getString("email"),
									rs.getString("passwd"),
									rs.getString("name")) ;
							return member;
						}else {
							System.out.println("Login Failure");
							return null;
						}
					} else {
						System.out.println("Email Dont Exist");
						return null;
					}
			}catch (Exception e) {
				System.out.println(e);
				return null;
			}
		}
		
		static boolean chPasswd(Member member) {
			System.out.println("Change Password,Please Enter New Password:");
			Scanner scanner = new Scanner(System.in);
			String newPasswd = scanner.nextLine(); 
			String passwdRegex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";
			if (newPasswd == passwdRegex) {					 
				try(Connection conn = DriverManager.getConnection(URL,prop);
					 PreparedStatement pstmt = conn.prepareStatement(SQL_CHPASSWD)){
					 pstmt.setString(1, BCrypt.hashpw(newPasswd, BCrypt.gensalt()));
					 pstmt.setLong(2, member.getId());
					 pstmt.executeUpdate();
					 return true;
				} catch (SQLException e) {
				 System.out.println(e);
				 return false;
				}				 
			} else {
//				System.out.println("Cancel Change Password");
				return false;
			}
		}

}
