package com.zxh.test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//Java5的线程同步通信技术-Condition(Condition lock.newCondition())
//ConditionCommunication的升级版本:要求3个不同的线程进行通信，轮流做
/*要求:子线程1循环10次，接着子线程2循环20次,然后主线程循环100次，再到子线程1...如此循环50次*/
/*运用知识点:Java5同步通信(Condition await(),signal()),同步互斥(Lock)*/
public class ConditionThreeCommunication_13 {

	
	public static void main(String[] args) throws InterruptedException {

		final Bussiness bussiness = new Bussiness(); //内部类调用外部类的局部变量，其变量必须为final
		
		
		new Thread(new Runnable() {//子线程1

			public void run() {
				for (int i = 1; i <= 50; i++) {
					bussiness.sub1Bus(i);
				}
			}
		}).start();
		
		new Thread(new Runnable() {//子线程2

			public void run() {
				for (int i = 1; i <= 50; i++) {
					bussiness.sub2Bus(i);
				}
			}
		}).start();

		for (int i = 1; i <= 50; i++) {//主线程
			bussiness.mainBus(i);
		}
	}
	
	
	static class Bussiness {//(静态方法不能创建内部类的实例，所以内部类也改成静态)
		
		private Lock lock = new ReentrantLock(); //用同步锁代替synchronized
	//	private Condition condition = lock.newCondition(); //在线程互斥的同步锁基础上创建用于线程通信的Condition
		
		//因为要3个线程间通信，所以要创建3个Condition以识别哪个线程阻塞哪个线程放行
		
		Condition condition1 = lock.newCondition(); //主线程的
		Condition condition2 = lock.newCondition(); //子线程1的
		Condition condition3 = lock.newCondition(); //子线程2的
		
		
		//boolean mainGo = false;
		
		//3个线程以上交替执行就不能用boolean判断,应用一个Integer作为标识
		int flag =1;
		
		//主线程方法
		public /*synchronized*/ void mainBus(int i) {
			
			lock.lock();
			try{
				while(flag!=1){//如果mainGo=true则说明是main线程的执行，就先把子线程wait
					try {
						//this.wait();
						condition1.await(); //若标识flag!=1(没轮到自己)，就把自己的线程阻塞，直到flag=1
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				for (int j = 1; j <= 100; j++) {
					System.out.println("主线程第:" + j + "次---第" + i + "轮");
				}

				condition2.signal(); //把子线程1唤醒
				flag = 2;
				
			}finally{
				lock.unlock();
			}
		}

		//子线程1方法
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
					System.out.println("子线程1第:" + j + "次---第" + i + "轮");
				}

				condition3.signal(); //把子线程2唤醒
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
					System.out.println("子线程2第:" + j + "次---第" + i + "轮");
				}

				condition1.signal(); //把主线程唤醒，实现通信,交替执行
				flag = 1;
				
			}finally{
				lock.unlock();
			}
		}

	}
	
}

