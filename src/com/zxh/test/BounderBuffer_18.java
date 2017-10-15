package com.zxh.test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/*【做一个阻塞队列】
 * 
 * 例子(电脑转发信息到信号塔)
 * 
 * 
 * */
public class BounderBuffer_18 {
	
	private Lock lock = new ReentrantLock();
	
	private Condition notFullCondition = lock.newCondition();//用于在队列满时阻塞加入队列的线程
	private Condition notEmptyCondition = lock.newCondition();//用于在队列空时阻塞取出队列的线程
	
	final Object[] items = new Object[100]; //创建一个容量固定为100的队列
	
	private int count; //计算物品的数量
	private int putptr; //标记取出队列的下标
	private int takeptr; //标记加入队列的下标
	
	/*加入队列的操作*/
	public void put(Object x) throws Exception{
		
		lock.lock();//加上锁以线程互斥
		try{
			
			if(count==items.length){ //若物品数量已满，就把put给阻塞掉 (由take()方法来放行)
				notFullCondition.await();
			}
			
			items[putptr] = x; //物品加入队列
			if(++putptr==items.length){ //如果加入队列操作的下标已加到队列末尾，就跳回队列头重新放(不用担心会覆盖数据,因为有通信阻塞,put不可能无限执行)
				putptr = 0; 
			}
			count++; //物品数量+1
			notEmptyCondition.signal(); //由于加入了物品，所以队列一定不为空,放行取出的线程
			
		}finally{
			lock.unlock();
		}
		
	}
	
	/*取出队列的操作*/
	public Object take() throws Exception{
		
		Object item = null;
		
		lock.lock();//加上锁以线程互斥
		try{
			if(count==0){//若物品数量=0，就把take阻塞掉(由put(...)方法来放行)
				notEmptyCondition.await();
			}
			
			item = items[takeptr]; //物品取出队列
			if(++takeptr==items.length){//如果取出队列操作的下标已加到队列末尾，就跳回队列头重新取
				takeptr = 0;
			}
			count--; //物品数量-1
			notFullCondition.signal(); //由于取出物品，所以队列一定不满，放行加入队列的线程
			
		}finally{
			lock.unlock();
		}
		
		return item; //返回物品
	}

}
