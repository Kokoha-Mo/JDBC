package tw.lab.tutor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import tw.lab.apis.Gift;
import tw.lab.apis.JdbcTool;
import tw.lab.apis.RowMapper;

public class Lab12 {

	public static void main(String[] args) {
		JdbcTool tool = new JdbcTool();
		String sql = """
				SELECT name,addr,tel
				FROM gift
				WHERE name LIKE ? OR addr LIKE ?
				""";
		//利用寫好的JdbcTool來查詢
		/*-------------------------------------------------- 
		//原做法
 		tool.query(sql,new RowMapper<Gift>() {
			public Gift mapRow(ResultSet rs) throws SQLException {
				Gift gift = new Gift();
				gift.setName(rs.getString("name"));
				gift.setAdd(rs.getString("addr"));
				gift.setFeature(rs.getString("feature"));
				return gift;
			};
		} , "%%","%%");
		------------------------------------------------------ */
		
		//LANBDA寫法，注意!只有一個抽象方法的時候可以用
		List<Gift> gifts =  tool.query(sql,rs -> {
								Gift gift = new Gift();
								gift.setName(rs.getString("name"));
								gift.setAdd(rs.getString("addr"));
								gift.setTel(rs.getString("tel"));
								return gift;
							}, "%禮盒%","%禮盒%");
		//尋訪
		//寫法1
//		for (Gift gift:gifts) {
//			System.out.println(gift);
//		}
		//寫法2:因為gifts是list物件可以forEach
		gifts.forEach(System.out::println);
		
	}

}
