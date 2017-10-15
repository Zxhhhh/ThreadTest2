package com.why.threadJDBCUtil;

import java.util.concurrent.locks.ReentrantLock;


//设计4个线程，其中两个线程每次对j增加1，另外两个线程对j每次减少1。写出程序。 
public class myTest {
	
	private static int j=0;
	
	
	public static void main(String[] args)  {
		
		
		final ReentrantLock lock = new ReentrantLock();
		
		for(int i=0;i<2;i++){
			
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					
					while(true){
						
						lock.lock();
						System.out.println(Thread.currentThread().getName()+"准备操作J");
						j++;
						try{Thread.sleep(100);}catch (Exception e) {}
						System.out.println(Thread.currentThread().getName()+"进行j++的操作:"+j);
						lock.unlock();
						
					}
					
				}
			}).start();
			
		}
		
		for(int k=0;k<2;k++){
			
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					
					while(true){
						
						lock.lock();
						System.out.println(Thread.currentThread().getName()+"准备操作J");
						j--;
						try{Thread.sleep(100);}catch (Exception e) {}
						System.out.println(Thread.currentThread().getName()+"进行j--的操作:"+j);
						lock.unlock();
						
					}
					
				}
			}).start();
			
		}
		
		
	}
	

}
