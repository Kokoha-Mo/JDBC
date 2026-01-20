package tw.lab.apis;

//實作Task，消費者介面
public class PurchaseTask implements Task{
	private static final int PRODUCT_ID = 1;
	final int qty;
	
	public PurchaseTask(int qty) {
		this.qty = qty;
	}
	
	@Override
	public void execute(StoreService service) throws Exception {
		try {
			service.purchase(PRODUCT_ID, qty);
		}catch (Exception e) {
			System.out.println("不足:" + qty);
		}
	}

	@Override
	public String label() {
		return "OUT:" + qty;
	}

}
