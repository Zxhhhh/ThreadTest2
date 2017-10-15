package com.zxh.test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//Java5���߳�ͬ��ͨ�ż���-Condition(Condition lock.newCondition())
//ConditionCommunication�������汾:Ҫ��3����ͬ���߳̽���ͨ�ţ�������
/*Ҫ��:���߳�1ѭ��10�Σ��������߳�2ѭ��20��,Ȼ�����߳�ѭ��100�Σ��ٵ����߳�1...���ѭ��50��*/
/*����֪ʶ��:Java5ͬ��ͨ��(Condition await(),signal()),ͬ������(Lock)*/
public class ConditionThreeCommunication_13 {

	
	public static void main(String[] args) throws InterruptedException {

		final Bussiness bussiness = new Bussiness(); //�ڲ�������ⲿ��ľֲ����������������Ϊfinal
		
		
		new Thread(new Runnable() {//���߳�1

			public void run() {
				for (int i = 1; i <= 50; i++) {
					bussiness.sub1Bus(i);
				}
			}
		}).start();
		
		new Thread(new Runnable() {//���߳�2

			public void run() {
				for (int i = 1; i <= 50; i++) {
					bussiness.sub2Bus(i);
				}
			}
		}).start();

		for (int i = 1; i <= 50; i++) {//���߳�
			bussiness.mainBus(i);
		}
	}
	
	
	static class Bussiness {//(��̬�������ܴ����ڲ����ʵ���������ڲ���Ҳ�ĳɾ�̬)
		
		private Lock lock = new ReentrantLock(); //��ͬ��������synchronized
	//	private Condition condition = lock.newCondition(); //���̻߳����ͬ���������ϴ��������߳�ͨ�ŵ�Condition
		
		//��ΪҪ3���̼߳�ͨ�ţ�����Ҫ����3��Condition��ʶ���ĸ��߳������ĸ��̷߳���
		
		Condition condition1 = lock.newCondition(); //���̵߳�
		Condition condition2 = lock.newCondition(); //���߳�1��
		Condition condition3 = lock.newCondition(); //���߳�2��
		
		
		//boolean mainGo = false;
		
		//3���߳����Ͻ���ִ�оͲ�����boolean�ж�,Ӧ��һ��Integer��Ϊ��ʶ
		int flag =1;
		
		//���̷߳���
		public /*synchronized*/ void mainBus(int i) {
			
			lock.lock();
			try{
				while(flag!=1){//���mainGo=true��˵����main�̵߳�ִ�У����Ȱ����߳�wait
					try {
						//this.wait();
						condition1.await(); //����ʶflag!=1(û�ֵ��Լ�)���Ͱ��Լ����߳�������ֱ��flag=1
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				for (int j = 1; j <= 100; j++) {
					System.out.println("���̵߳�:" + j + "��---��" + i + "��");
				}

				condition2.signal(); //�����߳�1����
				flag = 2;
				
			}finally{
				lock.unlock();
			}
		}

		//���߳�1����
		public /*synchronized*/ void sub1Bus(int i) {
			lock.lock();
			try{
				if(flag!=2){
					try {
						//this.wait();
						condition2.await();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				for (int j = 1; j <= 10; j++) {
					System.out.println("���߳�1��:" + j + "��---��" + i + "��");
				}

				condition3.signal(); //�����߳�2����
				flag = 3;
				
			}finally{
				lock.unlock();
			}
		}
		
		public /*synchronized*/ void sub2Bus(int i) {
			lock.lock();
			try{
				if(flag!=3){
					try {
						//this.wait();
						condition3.await();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				for (int j = 1; j <= 20; j++) {
					System.out.println("���߳�2��:" + j + "��---��" + i + "��");
				}

				condition1.signal(); //�����̻߳��ѣ�ʵ��ͨ��,����ִ��
				flag = 1;
				
			}finally{
				lock.unlock();
			}
		}

	}
	
}

