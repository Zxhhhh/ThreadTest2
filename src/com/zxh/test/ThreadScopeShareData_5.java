package com.zxh.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

//线程范围内的共享数据(线程内共享，线程外独立)
//应用: SQL上，每个连接(Connection)处理一个独立的线程(线程绑定)
public class ThreadScopeShareData_5 {
	
	public static int data = 0;
	public static Map<Thread,Integer> threadData = new HashMap<Thread,Integer>();//用于存放每个线程的共享数据，防止线程间数据错乱
	
	public static void main(String[] args) {
		for(int i =0;i<2;i++){
			new Thread(new Runnable() {
				@Override
				public void run() {
					
					int data = new Random().nextInt();//获取本线程上的共享数据
					System.out.println(Thread.currentThread().getName()+" has put data:"+data);
					
					threadData.put(Thread.currentThread(), data);//存放本线程的共享数据
					
					//在同一个线程内A与B模块应该共享一个数据data
					new A().get();//a模块取数据
					new B().get();//b模块取数据
				}
			}).start();
		}
		
	}
	
	static class A{
		
		public void get(){
			
			int data = threadData.get(Thread.currentThread());//从Map中获取本线程的共享数据
			
			System.out.println("A from "+Thread.currentThread().getName()+" get data:"+data);
			
		}
	}
	
	static class B{
		
		public void get(){
			
			System.out.println("B from "+Thread.currentThread().getName()+" get data:"+data);
		}
		
	}
}
