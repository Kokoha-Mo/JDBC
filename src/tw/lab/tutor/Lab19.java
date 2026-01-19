package tw.lab.tutor;

import java.util.Scanner;

import tw.lab.apis.Member;
import tw.lab.apis.MemberDAO;
import tw.lab.apis.MemberDAOImpl;

public class Lab19 {

	public static void main(String[] args) {
		System.out.println("Member Login");
		Scanner scanner = new Scanner(System.in);
		System.out.println("Email");
		String email = scanner.nextLine();
		System.out.println("Password");
		String passwd = scanner.nextLine();
		
		MemberDAO dao = new MemberDAOImpl();
		try {
			Member member = dao.login(email, passwd);
			if (member != null) {
				System.out.printf("Welcome, %s",member.getName());
			} else {
				System.out.println("Login Failure");
			}
		} catch (Exception e) {
			System.out.println(e);
		}

	}

}
