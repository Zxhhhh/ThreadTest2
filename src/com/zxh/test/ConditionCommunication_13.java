package com.zxh.test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//Java5���߳�ͬ��ͨ�ż���-Condition(����wait,notify����)
/*Ҫ��:���߳�ѭ��10�Σ��������߳�ѭ��100�Σ��ٵ����߳�...���ѭ��50��*/
/*����֪ʶ��:Java5ͬ��ͨ��(Condition),ͬ������(Lock)*/
public class ConditionCommunication_13 {

	public static void main(String[] args) throws InterruptedException {

		final Bussiness bussiness = new Bussiness();

		new Thread(new Runnable() {

			public void run() {
				for (int i = 1; i <= 50; i++) {
					bussiness.subBus(i);
				}
			}
		}).start();

		for (int i = 1; i <= 50; i++) {
			bussiness.mainBus(i);
		}
	}
	
	
	static class Bussiness {//(��̬�������ܴ����ڲ����ʵ���������ڲ���Ҳ�ĳɾ�̬)
		
		private Lock lock = new ReentrantLock(); //��ͬ��������synchronized
		private Condition condition = lock.newCondition(); //���̻߳����ͬ���������ϴ��������߳�ͨ�ŵ�Condition
		
		boolean mainGo = false;
		
		//���̷߳���
		public /*synchronized*/ void subBus(int i) {
			
			lock.lock();
			try{
				if(mainGo){//���mainGo=true��˵����main�̵߳�ִ�У����Ȱ����߳�wait
					try {
						//this.wait();
						condition.await();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				for (int j = 1; j <= 10; j++) {
					System.out.println("���̵߳�:" + j + "��---��" + i + "��");
				}
				
				//notifyAll(); //ÿ���˱����궼�������߳�notify,�ٸĶ�mainGo��ʶ
				condition.signal();
				mainGo=true;
			}finally{
				lock.unlock();
			}
		}

		//���̷߳���
		public /*synchronized*/ void mainBus(int i) {
			lock.lock();
			try{
				if(!mainGo){
					try {
						//this.wait();
						condition.await();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				for (int j = 1; j <= 100; j++) {
					System.out.println("���̵߳�:" + j + "��---��" + i + "��");
				}
				//notifyAll();
				condition.signal();
				mainGo=false;
			}finally{
				lock.unlock();
			}
		}

	}
	
}

