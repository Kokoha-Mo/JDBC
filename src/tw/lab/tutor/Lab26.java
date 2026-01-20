package tw.lab.tutor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import tw.lab.apis.PurchaseTask;
import tw.lab.apis.RestockTask;
import tw.lab.apis.StoreService;
import tw.lab.apis.Task;

public class Lab26 {

	private static final String URL = "jdbc:mysql://localhost:3306/iii";
	private static final String USER = "root";
	private static final String PASSWORD = "root";
	private static final BlockingQueue<Task> QUEUE = new ArrayBlockingQueue<>(200);
	//一種資料結構，如果抓不到東西會回傳null
	private static final AtomicBoolean RUNNING = new AtomicBoolean(true);
	
	
	public static void main(String[] args) throws Exception {
		try(Connection conn = DriverManager.getConnection(URL,USER,PASSWORD)){
			System.out.println("OK");
		}
		/*
		 * RUNNING == true
		 * Producer 一直補貨 -> QUEUE
		 * Worker 一直買貨 -> QUEUE
		 * MAIN Thread -> 喊停 / 跑迴圈
		 */
		
		// 登記進貨&買貨到QUEUE
		ExecutorService producer = Executors.newFixedThreadPool(2);
		producer.submit(new Producer());
		producer.submit(new Producer());
		
		// 執行QUEUE裡的Task
		ExecutorService writer = Executors.newFixedThreadPool(4);
		writer.submit(new Writer());
		writer.submit(new Writer());
		writer.submit(new Writer());
		writer.submit(new Writer());
		
		
		Thread.sleep(3*1000);
		RUNNING.set(false);
		producer.shutdown();
		System.out.println("Game Over");
	}
	
	static class Producer implements Runnable {
		private final Random r = new Random();
		@Override
		public void run() {
			try {
				while(RUNNING.get()) {
					Task t;
					if (r.nextInt(100) < 70) {					
						// 70%購買，一次買1~4個
						t = new PurchaseTask(1+r.nextInt(4));
					} else {
						// 30%補貨，一次補1~6個
						t = new RestockTask(1+r.nextInt(7));
					}
					QUEUE.put(t);
					Thread.sleep(30);
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}	
	}
	
	static class Writer implements Runnable {
		@Override
		public void run() {
			StoreService service = new StoreService(URL, USER, PASSWORD);
			try {
				while(RUNNING.get() || !QUEUE.isEmpty()) {
					Task t = QUEUE.poll(200, TimeUnit.MILLISECONDS);
					if (t == null) continue;
					try {						
					t.execute(service);
					System.out.println(t.label());
					}catch (Exception e) {
						System.out.println(e);
					}
				}
			}catch (Exception e) {
				System.out.println(e);
			}
		}
		
	}

}
