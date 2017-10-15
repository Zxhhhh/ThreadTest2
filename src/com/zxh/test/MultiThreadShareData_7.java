package com.zxh.test;

//����߳�֮�乲�����ݵķ�ʽ
/*���̼߳�ִ�д�����ͬ����ִ��ͬһ��Runnable����(����Ʊϵͳ)
  ��ÿ���߳�ִ�д��벻ͬ����Ҫ�õ���ͬ��Runnable����,������2�ַ���ʵ��Runnable���������ݹ��� 
   */
public class MultiThreadShareData_7 {
	
	//����һ���ⲿ���Ա
	private static ShareData method1Data = new ShareData();
	
	public static void main(String[] args) {
		
		/*����һ:���������ݼ�����������װ����һ��������
		 * ,����ЩRunnable������Ϊһ���ڲ��࣬
		   ����������Ϊ�ⲿ��ĳ�Ա�������ڲ����е���
		  ��Ա����ķ��������������� 
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
		
		
		/*������:���������ݼ�����������װ��������������У���  
		  ���������һ���ݸ�����Runnable����������Runnable��
		 ���ж�Ӧ���������ĵ��� 
		 */
		ShareData method2Data = new ShareData();
		new Thread(new Inc(method2Data)).start();
		new Thread(new Dec(method2Data)).start();
	}

}

/*��Ź������ݵĶ���*/
class ShareData{
	
	private int j = 0;
	
	//���ݲ�������:j����
	public synchronized void increment(){
		j++;
	}
	
	//���ݲ�������:j�Լ�
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
	
	/*�������������߳�*/
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
	
	
	/*���������Լ��߳�*/
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

