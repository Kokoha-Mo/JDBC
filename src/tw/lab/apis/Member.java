package tw.lab.apis;

public class Member {
	private long id;
	private String email,passwd,name;
	public Member(long id,String email,String passwd,String name){
		this.id = id;
		this.email = email;
		this.passwd = passwd;
		this.name = name;
		
		}
	
	public long getId() {
		return id;
	}
	
	public String getEmail() {
		return email;
	}
	public String getPasswd() {
		return passwd;
	}
	public String getName() {
		return name;
	}
 }
