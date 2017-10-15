package com.zxh.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

//java5�̲߳������Ӧ��(java.util.concurrent)
//�̳߳�(ExecutorService�࣬ͨ��������Executors����)
//�̳߳�����ִ���̣߳����ѽ���ص��߳̽����Ŷӣ��ش�С����ÿ��ִ���̵߳�������û�б�ִ�е����߳����ȴ�
public class ThreadPoolTest_9 {

	public static void main(String[] args) {
		new ThreadPoolTest_9().PoolTest();
	}
	
	public void PoolTest(){
		
		ExecutorService executorService =  Executors.newFixedThreadPool(3); //�̶��̳߳أ����й̶���С��ÿ��ֻ��ִ�й̶���С���߳���(ÿ��3��)��������߳���Ҫ�ȴ�
		ExecutorService executorService2 = Executors.newCachedThreadPool(); //�����̳߳أ��ش�С���̶����صĴ�С�������߳����Ӽ��仯
		ExecutorService executorService3 = Executors.newSingleThreadExecutor();//���̳߳أ������ڵ��̣߳����������һ���߳���������Զ�ִ����һ�߳�
																			  /*���:������߳�ִ����Ϻ��Զ�ִ����һ�߳���*/
		
		for(int i=1;i<=10;i++){ //���̳߳��м���10���߳�
			final int task = i;
			executorService.execute(new Runnable() { //����һ��Runnable���󣬱�ʾ���߳̿�ʼ
				@Override
				public void run() {
					for(int j=1;j<=5;j++){//run����:ÿ���߳�ִ��5�����
						try {
							Thread.sleep(40);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println(Thread.currentThread().getName()+"����, ��"+j+"��,---�߳�"+task);
					}
				}
			});
		}
		
		System.out.println("���");
		executorService.shutdown();
	}
	
/*	//���Զ�ʱ���̳߳�
	public void scheduledTest(){
		
		Executors.newScheduledThreadPool(3).schedule(new Runnable() {
			
			@Override
			public void run() {
				
				System.out.println("��ʱ���߳�"+Thread.currentThread().getName()+"����");
				
			}
		}, 6, TimeUnit.SECONDS);//schedule(Runnable,delay,unit):��6��(TimeUnit.SECONDS)�ӳٺ�ִ�и�Runnable�߳�
		
	}*/
	
}
