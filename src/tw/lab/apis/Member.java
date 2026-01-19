package tw.lab.apis;

public class Member {
	private long id;
	private String email,passwd,name;
	public Member() {}
	public Member(long id,String email,String passwd,String name){
		this.id = id;
		this.email = email;
		this.passwd = passwd;
		this.name = name;	
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("%s:%s", email,name);
	}
 }
