package tw.lab.tutor;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Lab23 {
	public static void main(String[] args) throws Exception {
		ExecutorService pool = Executors.newFixedThreadPool(3);
		
		List<Callable<String>> tasks = List.of(
				() -> "A",
				() -> {Thread.sleep(1000);return "B";},
				() -> "C"
				);
		
		List<Future<String>> futures = pool.invokeAll(tasks);
		
		for (Future<String> future:futures) {
			System.out.println(future.get());	
		}
		
		pool.shutdown();
		
		System.out.println("Main Finish");
	}
}
