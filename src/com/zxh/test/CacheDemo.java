package com.zxh.test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

//设计一个缓存系统
/*缓存系统概念:当要取对象时，系统先判别该对象是否存在，若
 不存在就到数据库中查找，并把这个对象保存下来并返回，当
 下次还要取这个数据时，就直接返回而不需要查数据库(比较类似
 于Hibernate的持久化),要进行线程同步的判断(读写锁)
 *
 */
public class CacheDemo {

	private static Map<String, Object> cache = new HashMap<String, Object>(); // 准备保存查过的数据的"缓存"

	private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock(); // 准备读写锁

	public Object getData(String key) {

		Object data = null;

		rwl.readLock().lock();
		try {
			data = cache.get(key); // 在缓存中找数据
			if (data == null) {// 如果发现缓存中没有这个数据，就要写数据(解开读锁，锁上写锁)

				rwl.readLock().unlock(); // 标注一
				rwl.writeLock().lock();
				try {
					if (data == null) { // 二次判断data为空的原因:若有2个线程，线程1进入了写锁，线程2在【标注一】中阻塞，在线程1解开写锁后,线程2会直接进入写锁，导致数据重复写入
						data = "get data from DataBase...."; // 省略从数据库查数据的过程
						cache.put(key, data); // 把查到的数据保存到缓存，下次可以直接获取
					}
				} finally {
					rwl.writeLock().unlock(); //释放写锁
				}
				rwl.readLock().lock();// 在释放写锁后要记得再次加上读锁，因为还要进行读取操作
				
				System.out.println("读取:将返回数据:"+data);
				
			}
		} finally {
			rwl.readLock().unlock(); //最终解开读锁，完成获取
		}

		return data;

	}

}
