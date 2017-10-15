package com.zxh.test;

//传统线程同步通信技术
/*要求:子线程循环10次，接着主线程循环100次，再到子线程...如此循环50次*/
/*运用知识点:同步通信(wait,notify),同步互斥(synchronized)*/
public class TraditionalThreadCommunication_4 {

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
}


class Bussiness {

	boolean mainGo = false;
	
	//子线程方法
	public synchronized void subBus(int i) {
		
		while(mainGo){//如果mainGo=true则说明是main线程的执行，就先把子线程wait
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		for (int j = 1; j <= 10; j++) {
			System.out.println("子线程第:" + j + "次---第" + i + "轮");
		}
		notifyAll(); //每次运遍历完都把所有线程notify,再改动mainGo标识
		mainGo=true;
	}

	//主线程方法
	public synchronized void mainBus(int i) {
		
		while(!mainGo){//执行方法前先判断标识，若没轮到自己就阻塞
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		for (int j = 1; j <= 100; j++) {
			System.out.println("主线程第:" + j + "次---第" + i + "轮");
		}
		notifyAll();
		mainGo=false;
	}

}
