package com.zxh.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

//ThreadLocal类-解决线程范围内的共享数据(线程内共享，线程外独立)问题
//应用: SQL上，每个连接(Connection)处理一个独立的线程(线程绑定)
public class ThreadLocalTest_6 {
	
	public static int data = 0;
	//public static Map<Thread,Integer> threadData = new HashMap<Thread,Integer>();//用于存放每个线程的共享数据，防止线程间数据错乱
	
	//存的数据就是与当前线程相关的
	public static ThreadLocal<Integer> threadData = new ThreadLocal<Integer>();
	
		//每个线程间独立
	//public static ThreadLocal<MyThreadScopeData> threadData2 = new ThreadLocal<MyThreadScopeData>();
	
	public static void main(String[] args) {
		
		for(int i =0;i<2;i++){
			new Thread(new Runnable() {
				@Override
				public void run() {
					
					int data = new Random().nextInt();//获取本线程上的共享数据
					
					System.out.println(Thread.currentThread().getName()+" has put data:"+data);

					//给本线程的数据对象保存数据(通过静态方法获取)
					MyThreadScopeData.getThreadInstance().setName("name:"+data);
					MyThreadScopeData.getThreadInstance().setAge(data);
					
					
					//在同一个线程内A与B模块应该共享一个数据data
					new A().get();//a模块取数据
					new B().get();//b模块取数据
				}
			}).start();
		}
		
	}
	
	/*模块A（模块间用的数据应是线程内共享的）*/
	static class A{
		
		public void get(){
			
			
			MyThreadScopeData mtsd = MyThreadScopeData.getThreadInstance();
			
			System.out.println("A from "+Thread.currentThread().getName()+" get MyData: "+mtsd.getName()+"---"+mtsd.getAge());
		}
		
	}
	
	/*模块B（模块间用的数据应是线程内共享的）*/
	static class B{
		
		public void get(){
			
			
			MyThreadScopeData mtsd = MyThreadScopeData.getThreadInstance();
			
			System.out.println("B from "+Thread.currentThread().getName()+" get MyData: "+mtsd.getName()+"---"+mtsd.getAge());
		}
		
	}
}


	//要创建成线程单例模式(在线程中的单例,因为数据要线程间共享,所以在同线程中要唯一存在),把ThreadLocal封装在类中，用户使用只需要调用其方法
	class MyThreadScopeData{
		
		
		private MyThreadScopeData(){}
		
		//ThreadLocal一个线程内存在一个(与当前线程相关的)，可用ThreadLocal进行懒汉式单例模式防止线程问题
		private static ThreadLocal<MyThreadScopeData> map = new ThreadLocal<MyThreadScopeData>();
		
		//获得本线程的单例MyThreadScopeData对象
		public static MyThreadScopeData getThreadInstance(){
			
			//寻找本线程内的MyThreadScopeData是否被赋值，各个线程间各不干扰，所以不会有线程问题
			MyThreadScopeData instance = map.get();
			if(instance==null){
				instance = new MyThreadScopeData();
				map.set(instance); //实例化后就把对象赋给ThreadLocal，线程再次访问就会直接get()，实现单例
			}
			
			return instance;
		}
		
		/*线程内的共享数据（线程内共享，线程间独立s）*/
		private String name;
		private int age;
		

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}
		
		
	}
