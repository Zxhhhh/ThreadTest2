package com.zxh.test;

import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

//读写锁-ReentrantReadWriteLock(代替线程互斥的synchronized)
//分为读锁和写锁，多个读锁不互斥，读锁与写锁互斥，写锁与写锁互斥 
public class ReadWriteLockTest_12 {
	public static void main(String[] args) {
		
		final Queue3 q3 = new Queue3();
		
		for(int i =0;i<3;i++){
			
			//循环创建3个读的线程
			new Thread(new Runnable() {
				@Override
				public void run() {
					while(true){
						q3.get();
					}
				}
			}).start();
			
			//循环创建3个写的线程
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					while(true){
						q3.put(new Random().nextInt(10000));
					}
				}
			}).start();
			
		}
		
		
	}
}

//保存读操作和写操作的对象
class Queue3{
	private Object data = null; //共享数据
	private ReadWriteLock rwl = new ReentrantReadWriteLock();
	
	//读取操作
	public void get(){
		
		rwl.readLock().lock();
		
		System.out.println(Thread.currentThread().getName()+" be ready to read data");
		try {
			Thread.sleep((long)(Math.random()*1000));
			System.out.println(Thread.currentThread().getName()+" has read data :"+data);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			rwl.readLock().unlock();
		}
		
	}
	
	//写操作
	public void put(Object data){
		
		rwl.writeLock().lock();
		
		System.out.println(Thread.currentThread().getName()+" be ready to write data");
		try {
			Thread.sleep((long)(Math.random()*1000));

			this.data = data; //写入数据 
			System.out.println(Thread.currentThread().getName()+" has write data :  "+data);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			rwl.writeLock().unlock();
		}
	}
}




//二:用读写锁构建一个缓存对象
class CacheData{
	
	private Object data;
	volatile boolean cacheValid;
	ReadWriteLock rwl = new ReentrantReadWriteLock(); //创建读写锁
	
	public void processCacheData(){
		
		rwl.readLock().lock();
		
		if(!cacheValid){//代表还没数据，就要先写
			
			rwl.readLock().unlock();
			
			rwl.writeLock().lock();
			
			if(!cacheValid){//再次判断数据为空
				setData();//写入数据
				cacheValid = true; //更改标识，表示数据载入
			}
			
			rwl.readLock().lock(); //在写锁锁上的期间也可以挂上读锁(其实是把写锁降级为更新锁)
			rwl.writeLock().unlock(); //解开写锁
		}
		
		useData(); //读取数据
		
		rwl.readLock().unlock(); //最后解开读锁，完成
		
	}
	
	//写入数据
	public void setData(){
		data = new String("my data is settled");
	}
	
	//读取数据
	public void useData(){
		System.out.println(data);
	}
	
}
