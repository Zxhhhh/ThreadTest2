package com.zxh.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

//ThreadLocal��-����̷߳�Χ�ڵĹ�������(�߳��ڹ����߳������)����
//Ӧ��: SQL�ϣ�ÿ������(Connection)����һ���������߳�(�̰߳�)
public class ThreadLocalTest_6 {
	
	public static int data = 0;
	//public static Map<Thread,Integer> threadData = new HashMap<Thread,Integer>();//���ڴ��ÿ���̵߳Ĺ������ݣ���ֹ�̼߳����ݴ���
	
	//������ݾ����뵱ǰ�߳���ص�
	public static ThreadLocal<Integer> threadData = new ThreadLocal<Integer>();
	
		//ÿ���̼߳����
	//public static ThreadLocal<MyThreadScopeData> threadData2 = new ThreadLocal<MyThreadScopeData>();
	
	public static void main(String[] args) {
		
		for(int i =0;i<2;i++){
			new Thread(new Runnable() {
				@Override
				public void run() {
					
					int data = new Random().nextInt();//��ȡ���߳��ϵĹ�������
					
					System.out.println(Thread.currentThread().getName()+" has put data:"+data);

					//�����̵߳����ݶ��󱣴�����(ͨ����̬������ȡ)
					MyThreadScopeData.getThreadInstance().setName("name:"+data);
					MyThreadScopeData.getThreadInstance().setAge(data);
					
					
					//��ͬһ���߳���A��Bģ��Ӧ�ù���һ������data
					new A().get();//aģ��ȡ����
					new B().get();//bģ��ȡ����
				}
			}).start();
		}
		
	}
	
	/*ģ��A��ģ����õ�����Ӧ���߳��ڹ���ģ�*/
	static class A{
		
		public void get(){
			
			
			MyThreadScopeData mtsd = MyThreadScopeData.getThreadInstance();
			
			System.out.println("A from "+Thread.currentThread().getName()+" get MyData: "+mtsd.getName()+"---"+mtsd.getAge());
		}
		
	}
	
	/*ģ��B��ģ����õ�����Ӧ���߳��ڹ���ģ�*/
	static class B{
		
		public void get(){
			
			
			MyThreadScopeData mtsd = MyThreadScopeData.getThreadInstance();
			
			System.out.println("B from "+Thread.currentThread().getName()+" get MyData: "+mtsd.getName()+"---"+mtsd.getAge());
		}
		
	}
}


	//Ҫ�������̵߳���ģʽ(���߳��еĵ���,��Ϊ����Ҫ�̼߳乲��,������ͬ�߳���ҪΨһ����),��ThreadLocal��װ�����У��û�ʹ��ֻ��Ҫ�����䷽��
	class MyThreadScopeData{
		
		
		private MyThreadScopeData(){}
		
		//ThreadLocalһ���߳��ڴ���һ��(�뵱ǰ�߳���ص�)������ThreadLocal��������ʽ����ģʽ��ֹ�߳�����
		private static ThreadLocal<MyThreadScopeData> map = new ThreadLocal<MyThreadScopeData>();
		
		//��ñ��̵߳ĵ���MyThreadScopeData����
		public static MyThreadScopeData getThreadInstance(){
			
			//Ѱ�ұ��߳��ڵ�MyThreadScopeData�Ƿ񱻸�ֵ�������̼߳�������ţ����Բ������߳�����
			MyThreadScopeData instance = map.get();
			if(instance==null){
				instance = new MyThreadScopeData();
				map.set(instance); //ʵ������ͰѶ��󸳸�ThreadLocal���߳��ٴη��ʾͻ�ֱ��get()��ʵ�ֵ���
			}
			
			return instance;
		}
		
		/*�߳��ڵĹ������ݣ��߳��ڹ����̼߳����s��*/
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
