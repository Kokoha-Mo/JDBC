package tw.lab.apis;

public class NotEnoughException extends Exception{
	public NotEnoughException(String mesg) {
		super(mesg);
	}
}
