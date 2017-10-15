package com.zxh.test;

//多个线程之间共享数据的方式
/*若线程间执行代码相同，可执行同一个Runnable对象(如卖票系统)
  若每个线程执行代码不同，需要用到不同的Runnable对象,有以下2种方法实现Runnable对象间的数据共享 
   */
public class MultiThreadShareData_7 {
	
	//方法一的外部类成员
	private static ShareData method1Data = new ShareData();
	
	public static void main(String[] args) {
		
		/*方法一:将共享数据及操作方法封装在另一个对象中
		 * ,将这些Runnable对象作为一个内部类，
		   共享数据作为外部类的成员对象，在内部类中调用
		  成员对象的方法操作共享数据 
		   */
		new Thread(new Runnable() {
			@Override
			public void run() {
				method1Data.increment();
				System.out.println(method1Data.getJ());
			}
		});
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				method1Data.decrement();
				System.out.println(method1Data.getJ());
			}
		});
		
		
		/*方法二:将共享数据及操作方法封装在另外的两个类中，后将  
		  这个对象逐一传递给各个Runnable对象，再由其Runnable类
		 进行对应操作方法的调用 
		 */
		ShareData method2Data = new ShareData();
		new Thread(new Inc(method2Data)).start();
		new Thread(new Dec(method2Data)).start();
	}

}

/*存放共享数据的对象*/
class ShareData{
	
	private int j = 0;
	
	//数据操作方法:j自增
	public synchronized void increment(){
		j++;
	}
	
	//数据操作方法:j自减
	public synchronized void decrement(){
		j--;
	}

	public int getJ() {
		return j;
	}

	public void setJ(int j) {
		this.j = j;
	}
	
	
}
	
	/*方法二的自增线程*/
	class Inc implements Runnable{
		
		private ShareData shareData;
		
		public Inc(ShareData shareData){
			
			this.shareData = shareData;
			
		}
		
		@Override
		public void run() {
			
			while(true){
				shareData.increment();
				System.out.println(shareData.getJ());
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	
	/*方法二的自减线程*/
	class Dec implements Runnable{
		
		private ShareData shareData;
		
		public Dec(ShareData shareData){
			
			this.shareData = shareData;
			
		}
		
		@Override
		public void run() {
			
			while(true){
				shareData.decrement();
				System.out.println(shareData.getJ());
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}

