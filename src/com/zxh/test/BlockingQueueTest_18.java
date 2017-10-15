package com.zxh.test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

//����������--BlockingQueue
public class BlockingQueueTest_18 {
	
	public static void main(String[] args) {
		
		/*����һ������������,���д�СΪ3*/
		final BlockingQueue queue = new ArrayBlockingQueue(3);
		
		//�������������ݵ��߳�
		for(int i =0;i<2;i++){
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					while(true){
						try{
							Thread.sleep((long)(Math.random()*1000));
							System.out.println(Thread.currentThread().getName()+" ׼��������!");
							queue.put(1);
							System.out.println(Thread.currentThread().getName()+" �ѷ�������, ����Ŀǰ��"+queue.size()+"������");
							
						}catch(Exception e){
							e.printStackTrace();
						}
					}
					
				}
			}).start();
		}
		
		//����һ�������ݵ��߳�
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					try{
						Thread.sleep(100);
						System.out.println(Thread.currentThread().getName()+" ׼��ȡ����!");
						queue.take();
						System.out.println(Thread.currentThread().getName()+" ��ȡ������, ����Ŀǰ��"+queue.size()+"������");
					}catch(Exception e){
						e.printStackTrace();
					}
					
				}
				
			}
		}).start();
		
	}

}
