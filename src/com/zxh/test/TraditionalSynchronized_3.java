package com.zxh.test;

//传统线程互斥技术
public class TraditionalSynchronized_3 {

	public static void main(String[] args) {

		new TraditionalSynchronized_3().init();

	}

	public void init() {

		// 内部类调用外部类成员变量需要加上final
		final Output output = new Output();

		// 开启一条线程
		new Thread(new Runnable() {

			@Override
			public void run() {
				while(true){
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					output.output2("aaaaaaa");
				}
			}
		}).start();

		// 开启第二条线程
		new Thread(new Runnable() {

			@Override
			public void run() {
				while(true){
					
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					output.output3("bbbbbbbb");
				}
			}
		}).start();

	}

	static class Output {

		// 在方法上加锁
		public synchronized void output(String name) {

			int len = name.length();

			for (int i = 0; i < len; i++) {
				System.out.print(name.charAt(i));
			}

			System.out.println();

		}
		
		//用synchronized加锁，用this与方法锁效果一样
		public void output1(String name) {

			int len = name.length();

			synchronized (this) {

				for (int i = 0; i < len; i++) {
					System.out.print(name.charAt(i));
				}

				System.out.println();
			}
		}
		
		//静态方法锁,用的是该类的字节码文件Output.class
	  static synchronized void output2(String name){
			
		  int len = name.length();

			for (int i = 0; i < len; i++) {
				System.out.print(name.charAt(i));
			}

			System.out.println();
		}
	  
	  //用synchronized加锁，用class文件与静态方法锁效果一样
	  public void output3(String name) {
	  
			int len = name.length();

			synchronized (Output.class) {

			for (int i = 0; i < len; i++) {
				System.out.print(name.charAt(i));
			}

				System.out.println();
			}
		}
	}
}
