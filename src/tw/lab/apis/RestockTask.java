package tw.lab.apis;

//實作Task,員工介面

public class RestockTask implements Task{
	private static final int PRODUCT_ID = 1;
	final int qty;
	public RestockTask(int qty) {
		this.qty = qty;
	}

	@Override
	public void execute(StoreService service) throws Exception {
		service.restock(PRODUCT_ID, qty);
	}

	@Override
	public String label() {
		return "IN:" + qty;
	}

}
