package com.zxh.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

//java5线程并发库的应用(java.util.concurrent)
//线程池(ExecutorService类，通过工具类Executors创建)
//线程池用于执行线程，并把进入池的线程进行排队，池大小决定每次执行线程的数量，没有被执行到的线程则会等待
public class ThreadPoolTest_9 {

	public static void main(String[] args) {
		new ThreadPoolTest_9().PoolTest();
	}
	
	public void PoolTest(){
		
		ExecutorService executorService =  Executors.newFixedThreadPool(3); //固定线程池，池有固定大小，每次只能执行固定大小个线程数(每次3个)，其余的线程需要等待
		ExecutorService executorService2 = Executors.newCachedThreadPool(); //缓存线程池，池大小不固定，池的大小随加入的线程数加减变化
		ExecutorService executorService3 = Executors.newSingleThreadExecutor();//单线程池，类似于单线程，但其可以在一个线程死亡后可自动执行下一线程
																			  /*解决:如何在线程执行完毕后自动执行下一线程序*/
		
		for(int i=1;i<=10;i++){ //在线程池中加入10个线程
			final int task = i;
			executorService.execute(new Runnable() { //传入一个Runnable对象，表示该线程开始
				@Override
				public void run() {
					for(int j=1;j<=5;j++){//run方法:每个线程执行5次输出
						try {
							Thread.sleep(40);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println(Thread.currentThread().getName()+"运行, 第"+j+"轮,---线程"+task);
					}
				}
			});
		}
		
		System.out.println("完成");
		executorService.shutdown();
	}
	
/*	//测试定时器线程池
	public void scheduledTest(){
		
		Executors.newScheduledThreadPool(3).schedule(new Runnable() {
			
			@Override
			public void run() {
				
				System.out.println("定时器线程"+Thread.currentThread().getName()+"启动");
				
			}
		}, 6, TimeUnit.SECONDS);//schedule(Runnable,delay,unit):在6秒(TimeUnit.SECONDS)延迟后，执行该Runnable线程
		
	}*/
	
}
