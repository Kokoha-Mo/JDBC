package tw.lab.tutor;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Lab22 {
	public static void main(String[] args) throws Exception{
		ExecutorService pool = Executors.newFixedThreadPool(2);
		
		Callable<Integer> task1 = () -> {
			int v = 0;
			for(int i = 0;i <= 10;i++) {
				Thread.sleep(100);
				System.out.println("A:" + i);
				v += i;
			}
			return v;
		};
		
		Callable<Integer> task2 = () -> {
			int v = 0;
			for(int i = 0;i <= 10;i++) {
				Thread.sleep(100);
				System.out.println("B:" + i);
				v += i;
			}
			return v;
		};
		
		Future<Integer> f1 = pool.submit(task1);
		Future<Integer> f2 = pool.submit(task2);
		
		Integer r1 = f1.get();
		Integer r2 = f2.get();
		
		System.out.println(r1);
		System.out.println(r2);
		
		pool.shutdown();
		
		System.out.println("Main Finish");
	}
}
