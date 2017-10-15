package com.zxh.test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//java5���߳�������-ReenTranLock(����synchronized)0.
//lock():���� unlock():�⿪�� ��ǰ���ǣ��̱߳�����ͬһ��������
public class LockTest_11 {

	public static void main(String[] args) {

		new LockTest_11().init();

	}

	public void init() {

		// �ڲ�������ⲿ���Ա������Ҫ����final
		final Output output = new Output();

		// ����һ���߳�
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

		// �����ڶ����߳�
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

		private Lock myLock = new ReentrantLock(); //����ͬ����

		// ��Java5�����߳̿���߳���������synchronized
		public/* synchronized */void output(String name) {

			myLock.lock(); //����
			try {
				int len = name.length();

				for (int i = 0; i < len; i++) {
					System.out.print(name.charAt(i));
				}

				System.out.println();
			} finally {// �ͷ����Ĳ���Ҫ�ŵ�finally����
				myLock.unlock();
			}

		}

		// ��Java5�����߳̿���߳���������synchronized
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
