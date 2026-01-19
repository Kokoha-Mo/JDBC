package tw.lab.apis;

import java.util.List;

public interface MemberDAO {
	boolean addMember(Member member) throws Exception;
	boolean updateMember(Member member) throws Exception;
	boolean deleteMember(long id) throws Exception;
	Member findById(long id) throws Exception;
	List<Member> findAll() throws Exception;
	Member login(String email,String passwd) throws Exception;
}
