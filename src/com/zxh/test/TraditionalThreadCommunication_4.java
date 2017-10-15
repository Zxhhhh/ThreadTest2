package com.zxh.test;

//��ͳ�߳�ͬ��ͨ�ż���
/*Ҫ��:���߳�ѭ��10�Σ��������߳�ѭ��100�Σ��ٵ����߳�...���ѭ��50��*/
/*����֪ʶ��:ͬ��ͨ��(wait,notify),ͬ������(synchronized)*/
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
	
	//���̷߳���
	public synchronized void subBus(int i) {
		
		while(mainGo){//���mainGo=true��˵����main�̵߳�ִ�У����Ȱ����߳�wait
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		for (int j = 1; j <= 10; j++) {
			System.out.println("���̵߳�:" + j + "��---��" + i + "��");
		}
		notifyAll(); //ÿ���˱����궼�������߳�notify,�ٸĶ�mainGo��ʶ
		mainGo=true;
	}

	//���̷߳���
	public synchronized void mainBus(int i) {
		
		while(!mainGo){//ִ�з���ǰ���жϱ�ʶ����û�ֵ��Լ�������
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		for (int j = 1; j <= 100; j++) {
			System.out.println("���̵߳�:" + j + "��---��" + i + "��");
		}
		notifyAll();
		mainGo=false;
	}

}
