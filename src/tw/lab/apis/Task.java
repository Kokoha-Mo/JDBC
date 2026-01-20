package tw.lab.apis;

//定義使用者介面

public interface Task {
	void execute(StoreService service) throws Exception;
	String label();
}
