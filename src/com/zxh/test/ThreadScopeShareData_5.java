package com.zxh.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

//�̷߳�Χ�ڵĹ�������(�߳��ڹ����߳������)
//Ӧ��: SQL�ϣ�ÿ������(Connection)����һ���������߳�(�̰߳�)
public class ThreadScopeShareData_5 {
	
	public static int data = 0;
	public static Map<Thread,Integer> threadData = new HashMap<Thread,Integer>();//���ڴ��ÿ���̵߳Ĺ������ݣ���ֹ�̼߳����ݴ���
	
	public static void main(String[] args) {
		for(int i =0;i<2;i++){
			new Thread(new Runnable() {
				@Override
				public void run() {
					
					int data = new Random().nextInt();//��ȡ���߳��ϵĹ�������
					System.out.println(Thread.currentThread().getName()+" has put data:"+data);
					
					threadData.put(Thread.currentThread(), data);//��ű��̵߳Ĺ�������
					
					//��ͬһ���߳���A��Bģ��Ӧ�ù���һ������data
					new A().get();//aģ��ȡ����
					new B().get();//bģ��ȡ����
				}
			}).start();
		}
		
	}
	
	static class A{
		
		public void get(){
			
			int data = threadData.get(Thread.currentThread());//��Map�л�ȡ���̵߳Ĺ�������
			
			System.out.println("A from "+Thread.currentThread().getName()+" get data:"+data);
			
		}
	}
	
	static class B{
		
		public void get(){
			
			System.out.println("B from "+Thread.currentThread().getName()+" get data:"+data);
		}
		
	}
}
