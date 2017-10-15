package com.zxh.test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/*����һ���������С�
 * 
 * ����(����ת����Ϣ���ź���)
 * 
 * 
 * */
public class BounderBuffer_18 {
	
	private Lock lock = new ReentrantLock();
	
	private Condition notFullCondition = lock.newCondition();//�����ڶ�����ʱ����������е��߳�
	private Condition notEmptyCondition = lock.newCondition();//�����ڶ��п�ʱ����ȡ�����е��߳�
	
	final Object[] items = new Object[100]; //����һ�������̶�Ϊ100�Ķ���
	
	private int count; //������Ʒ������
	private int putptr; //���ȡ�����е��±�
	private int takeptr; //��Ǽ�����е��±�
	
	/*������еĲ���*/
	public void put(Object x) throws Exception{
		
		lock.lock();//���������̻߳���
		try{
			
			if(count==items.length){ //����Ʒ�����������Ͱ�put�������� (��take()����������)
				notFullCondition.await();
			}
			
			items[putptr] = x; //��Ʒ�������
			if(++putptr==items.length){ //���������в������±��Ѽӵ�����ĩβ�������ض���ͷ���·�(���õ��ĻḲ������,��Ϊ��ͨ������,put����������ִ��)
				putptr = 0; 
			}
			count++; //��Ʒ����+1
			notEmptyCondition.signal(); //���ڼ�������Ʒ�����Զ���һ����Ϊ��,����ȡ�����߳�
			
		}finally{
			lock.unlock();
		}
		
	}
	
	/*ȡ�����еĲ���*/
	public Object take() throws Exception{
		
		Object item = null;
		
		lock.lock();//���������̻߳���
		try{
			if(count==0){//����Ʒ����=0���Ͱ�take������(��put(...)����������)
				notEmptyCondition.await();
			}
			
			item = items[takeptr]; //��Ʒȡ������
			if(++takeptr==items.length){//���ȡ�����в������±��Ѽӵ�����ĩβ�������ض���ͷ����ȡ
				takeptr = 0;
			}
			count--; //��Ʒ����-1
			notFullCondition.signal(); //����ȡ����Ʒ�����Զ���һ�����������м�����е��߳�
			
		}finally{
			lock.unlock();
		}
		
		return item; //������Ʒ
	}

}
