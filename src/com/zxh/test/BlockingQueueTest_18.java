package com.zxh.test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

//可阻塞队列--BlockingQueue
public class BlockingQueueTest_18 {
	
	public static void main(String[] args) {
		
		/*创建一个可阻塞队列,队列大小为3*/
		final BlockingQueue queue = new ArrayBlockingQueue(3);
		
		//开启两个放数据的线程
		for(int i =0;i<2;i++){
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					while(true){
						try{
							Thread.sleep((long)(Math.random()*1000));
							System.out.println(Thread.currentThread().getName()+" 准备放数据!");
							queue.put(1);
							System.out.println(Thread.currentThread().getName()+" 已放入数据, 队列目前有"+queue.size()+"个数据");
							
						}catch(Exception e){
							e.printStackTrace();
						}
					}
					
				}
			}).start();
		}
		
		//开启一个拿数据的线程
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					try{
						Thread.sleep(100);
						System.out.println(Thread.currentThread().getName()+" 准备取数据!");
						queue.take();
						System.out.println(Thread.currentThread().getName()+" 已取走数据, 队列目前有"+queue.size()+"个数据");
					}catch(Exception e){
						e.printStackTrace();
					}
					
				}
				
			}
		}).start();
		
	}

}
