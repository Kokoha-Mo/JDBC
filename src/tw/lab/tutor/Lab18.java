package tw.lab.tutor;

import java.util.List;

import tw.lab.apis.Member;
import tw.lab.apis.MemberDAO;
import tw.lab.apis.MemberDAOImpl;

public class Lab18 {

	public static void main(String[] args) {
		MemberDAO dao = new MemberDAOImpl();
		
		Member member = new Member();
				member.setEmail("eric@lab.tw");
				member.setPasswd("12345678");
				member.setName("艾瑞克");
		try {
			if (dao.addMember(member)) {
				System.out.println("OK");
			} else {
				System.out.println("XX");
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		
//		try {
//			Member member = dao.findById(5);
//			System.out.println(member.getEmail() + ":" + member.getName());
//		} catch (Exception e) {
//			System.out.println(e);
//		}
		
//		try {
//			if (dao.deleteMember(4)) {
//				System.out.println("DELETE SUCCESS");
//			} else {
//				System.out.println("DELETE FAILURE");
//			}
//		} catch (Exception e) {
//			System.out.println(e);
//		}
		
//		try {
//			Member member = dao.findById(5);
//			System.out.println(member.getEmail() + ":" + member.getName());
//			member.setEmail("tony@lab.tw");
//			member.setPasswd("12345678");
//			if (dao.updateMember(member)) {
//				System.out.println("UPDATE SUCCESS");
//			} else {
//				System.out.println("UPDATE FAILURE");
//			}
//		} catch (Exception e) {
//			System.out.println(e);
//		}
		
//		try {
//			List<Member> members =  dao.findAll();
//			for (Member member:members) {
//				System.out.printf("%d:%s:%s\n",member.getId(),member.getEmail(),member.getName());
//			}
//		} catch (Exception e) {
//			System.out.println(e);
//		}
		
		
	}

}
