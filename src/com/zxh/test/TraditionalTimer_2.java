package com.zxh.test;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

//��ͳ��ʱ�������ع�
public class TraditionalTimer_2 {
	
	private static int count = 0;
	
	public static void main(String[] args) {
		
		//ʵ��һ��TimerTask���࣬��ʵ������������ʱ��
		class MyTimerTask extends TimerTask{
			
			
			@Override
			public void run() {
				count = (count+1)%2;//��count��0��1֮���л�
				
				System.out.println("bombing");
				new Timer().schedule(new MyTimerTask(),2000+2000*count);//���Ƶݹ�,������һ����ʱ���Ĵ���ʱ����countֵ�仯��ʵ�������������(2�룬4�룬2��...)
			}
			
		}
		
		new Timer().schedule(new MyTimerTask(),2000);
		
		//quartz:���ߣ����ڼ������ں�ʱ����Timer��Ӧ��

		
//		//��ʱ������һ���¼�
//		new Timer().schedule(new TimerTask() {
//			
//			@Override
//			public void run() {
//				System.out.println("bombing!");
//				
//			}
//		}, 10000,3000); //��10��󴥷���ÿ3�봥��һ��TimerTask�¼�
		
		while(true){
			System.out.println(new Date().getSeconds()); //��ȡ��ǰ��
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	

}
