package tw.lab.tutor;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import tw.lab.apis.JdbcTool;
import tw.lab.apis.Member;
import tw.lab.apis.RowMapper;

public class Lab13 {

	public static void main(String[] args) {
		JdbcTool tool = new JdbcTool();
		String sql = """
				SELECT email,name
				FROM member
				""";
		//利用寫好的JdbcTool來查詢
		
		//LANBDA寫法，注意!只有一個抽象方法的時候可以用
		List<Member> members =  tool.query(sql,rs -> {
								Member member = new Member();
								member.setEmail(rs.getString("email"));
								member.setName(rs.getString("name"));
								return member;
							});
		//尋訪
		//寫法1
//		for (Gift gift:gifts) {
//			System.out.println(gift);
//		}
		//寫法2:因為gifts是list物件可以forEach
		members.forEach(System.out::println);
		
	}

}
