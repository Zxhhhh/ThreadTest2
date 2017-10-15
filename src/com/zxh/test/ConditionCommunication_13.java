package com.zxh.test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//Java5的线程同步通信技术-Condition(代替wait,notify方法)
/*要求:子线程循环10次，接着主线程循环100次，再到子线程...如此循环50次*/
/*运用知识点:Java5同步通信(Condition),同步互斥(Lock)*/
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
	
	
	static class Bussiness {//(静态方法不能创建内部类的实例，所以内部类也改成静态)
		
		private Lock lock = new ReentrantLock(); //用同步锁代替synchronized
		private Condition condition = lock.newCondition(); //在线程互斥的同步锁基础上创建用于线程通信的Condition
		
		boolean mainGo = false;
		
		//子线程方法
		public /*synchronized*/ void subBus(int i) {
			
			lock.lock();
			try{
				if(mainGo){//如果mainGo=true则说明是main线程的执行，就先把子线程wait
					try {
						//this.wait();
						condition.await();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				for (int j = 1; j <= 10; j++) {
					System.out.println("子线程第:" + j + "次---第" + i + "轮");
				}
				
				//notifyAll(); //每次运遍历完都把所有线程notify,再改动mainGo标识
				condition.signal();
				mainGo=true;
			}finally{
				lock.unlock();
			}
		}

		//主线程方法
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
					System.out.println("主线程第:" + j + "次---第" + i + "轮");
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

