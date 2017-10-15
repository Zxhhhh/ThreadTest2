package com.zxh.test;

import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

//��д��-ReentrantReadWriteLock(�����̻߳����synchronized)
//��Ϊ������д����������������⣬������д�����⣬д����д������ 
public class ReadWriteLockTest_12 {
	public static void main(String[] args) {
		
		final Queue3 q3 = new Queue3();
		
		for(int i =0;i<3;i++){
			
			//ѭ������3�������߳�
			new Thread(new Runnable() {
				@Override
				public void run() {
					while(true){
						q3.get();
					}
				}
			}).start();
			
			//ѭ������3��д���߳�
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

//�����������д�����Ķ���
class Queue3{
	private Object data = null; //��������
	private ReadWriteLock rwl = new ReentrantReadWriteLock();
	
	//��ȡ����
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
	
	//д����
	public void put(Object data){
		
		rwl.writeLock().lock();
		
		System.out.println(Thread.currentThread().getName()+" be ready to write data");
		try {
			Thread.sleep((long)(Math.random()*1000));

			this.data = data; //д������ 
			System.out.println(Thread.currentThread().getName()+" has write data :  "+data);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			rwl.writeLock().unlock();
		}
	}
}




//��:�ö�д������һ���������
class CacheData{
	
	private Object data;
	volatile boolean cacheValid;
	ReadWriteLock rwl = new ReentrantReadWriteLock(); //������д��
	
	public void processCacheData(){
		
		rwl.readLock().lock();
		
		if(!cacheValid){//����û���ݣ���Ҫ��д
			
			rwl.readLock().unlock();
			
			rwl.writeLock().lock();
			
			if(!cacheValid){//�ٴ��ж�����Ϊ��
				setData();//д������
				cacheValid = true; //���ı�ʶ����ʾ��������
			}
			
			rwl.readLock().lock(); //��д�����ϵ��ڼ�Ҳ���Թ��϶���(��ʵ�ǰ�д������Ϊ������)
			rwl.writeLock().unlock(); //�⿪д��
		}
		
		useData(); //��ȡ����
		
		rwl.readLock().unlock(); //���⿪���������
		
	}
	
	//д������
	public void setData(){
		data = new String("my data is settled");
	}
	
	//��ȡ����
	public void useData(){
		System.out.println(data);
	}
	
}
