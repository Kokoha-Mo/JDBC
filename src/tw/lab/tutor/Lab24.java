package tw.lab.tutor;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Lab24 {

	public static void main(String[] args) throws Exception{
		ExecutorService pool = Executors.newFixedThreadPool(3);
		//改為一件事完成才做下一件事的模式
		CompletionService<String> cs =  
				new ExecutorCompletionService<String>(pool);
		
		cs.submit(() -> {Thread.sleep(300);return "slow";});
		cs.submit(() -> {Thread.sleep(100);return "fast";});
		cs.submit(() -> {Thread.sleep(200);return "mid";});
		
		for (int i = 0; i < 3;i++) {
			Future<String> f = cs.take();
			System.out.println(f.get());
		}
		
		pool.shutdown();
		System.out.println("Main Finish");
	}

}
