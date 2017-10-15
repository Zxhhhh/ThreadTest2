package com.zxh.test;

//��ͳ�̻߳��⼼��
public class TraditionalSynchronized_3 {

	public static void main(String[] args) {

		new TraditionalSynchronized_3().init();

	}

	public void init() {

		// �ڲ�������ⲿ���Ա������Ҫ����final
		final Output output = new Output();

		// ����һ���߳�
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

		// �����ڶ����߳�
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

		// �ڷ����ϼ���
		public synchronized void output(String name) {

			int len = name.length();

			for (int i = 0; i < len; i++) {
				System.out.print(name.charAt(i));
			}

			System.out.println();

		}
		
		//��synchronized��������this�뷽����Ч��һ��
		public void output1(String name) {

			int len = name.length();

			synchronized (this) {

				for (int i = 0; i < len; i++) {
					System.out.print(name.charAt(i));
				}

				System.out.println();
			}
		}
		
		//��̬������,�õ��Ǹ�����ֽ����ļ�Output.class
	  static synchronized void output2(String name){
			
		  int len = name.length();

			for (int i = 0; i < len; i++) {
				System.out.print(name.charAt(i));
			}

			System.out.println();
		}
	  
	  //��synchronized��������class�ļ��뾲̬������Ч��һ��
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
