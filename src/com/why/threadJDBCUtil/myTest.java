package com.why.threadJDBCUtil;

import java.util.concurrent.locks.ReentrantLock;


//���4���̣߳����������߳�ÿ�ζ�j����1�����������̶߳�jÿ�μ���1��д������ 
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
						System.out.println(Thread.currentThread().getName()+"׼������J");
						j++;
						try{Thread.sleep(100);}catch (Exception e) {}
						System.out.println(Thread.currentThread().getName()+"����j++�Ĳ���:"+j);
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
						System.out.println(Thread.currentThread().getName()+"׼������J");
						j--;
						try{Thread.sleep(100);}catch (Exception e) {}
						System.out.println(Thread.currentThread().getName()+"����j--�Ĳ���:"+j);
						lock.unlock();
						
					}
					
				}
			}).start();
			
		}
		
		
	}
	

}
