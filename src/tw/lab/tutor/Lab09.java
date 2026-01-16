package tw.lab.tutor;

import tw.lab.apis.BCrypt;

public class Lab09 {

	public static void main(String[] args) {
		String psswd = "12345678";
		String hashPasswd =  BCrypt.hashpw(psswd, BCrypt.gensalt());
		System.out.println(hashPasswd);
		String input = new String("12345678");
		if (BCrypt.checkpw(input, hashPasswd)) {
			System.out.println("OK");
		} else {
			System.out.println("XX");
		}

	}

}
