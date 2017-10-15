package com.zxh.test;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

//传统定时器技术回顾
public class TraditionalTimer_2 {
	
	private static int count = 0;
	
	public static void main(String[] args) {
		
		//实现一个TimerTask子类，已实现连环触发定时器
		class MyTimerTask extends TimerTask{
			
			
			@Override
			public void run() {
				count = (count+1)%2;//让count在0与1之间切换
				
				System.out.println("bombing");
				new Timer().schedule(new MyTimerTask(),2000+2000*count);//类似递归,开启下一个定时器的触发时间随count值变化，实现连环间隔触发(2秒，4秒，2秒...)
			}
			
		}
		
		new Timer().schedule(new MyTimerTask(),2000);
		
		//quartz:工具，用于计算日期和时间在Timer的应用

		
//		//定时器调度一个事件
//		new Timer().schedule(new TimerTask() {
//			
//			@Override
//			public void run() {
//				System.out.println("bombing!");
//				
//			}
//		}, 10000,3000); //过10秒后触发并每3秒触发一次TimerTask事件
		
		while(true){
			System.out.println(new Date().getSeconds()); //获取当前秒
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	

}
