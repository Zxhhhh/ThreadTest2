package com.zxh.test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//java5的线程锁技术-ReenTranLock(代替synchronized)0.
//lock():上锁 unlock():解开锁 但前提是，线程必须用同一个锁对象
public class LockTest_11 {

	public static void main(String[] args) {

		new LockTest_11().init();

	}

	public void init() {

		// 内部类调用外部类成员变量需要加上final
		final Output output = new Output();

		// 开启一条线程
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					output.output("aaaaaaa");
				}
			}
		}).start();

		// 开启第二条线程
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {

					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					output.output1("bbbbbbbb");
				}
			}
		}).start();

	}

	static class Output {

		private Lock myLock = new ReentrantLock(); //创建同步锁

		// 用Java5并发线程库的线程锁来代替synchronized
		public/* synchronized */void output(String name) {

			myLock.lock(); //上锁
			try {
				int len = name.length();

				for (int i = 0; i < len; i++) {
					System.out.print(name.charAt(i));
				}

				System.out.println();
			} finally {// 释放锁的操作要放到finally块中
				myLock.unlock();
			}

		}

		// 用Java5并发线程库的线程锁来代替synchronized
		public void output1(String name) {

			myLock.lock();
			try {
				int len = name.length();

				for (int i = 0; i < len; i++) {
					System.out.print(name.charAt(i));
				}

				System.out.println();
			} finally {
				myLock.unlock();
			}
		}

	}
}
