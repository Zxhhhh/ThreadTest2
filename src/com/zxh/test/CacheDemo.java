package com.zxh.test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

//���һ������ϵͳ
/*����ϵͳ����:��Ҫȡ����ʱ��ϵͳ���б�ö����Ƿ���ڣ���
 �����ھ͵����ݿ��в��ң�����������󱣴����������أ���
 �´λ�Ҫȡ�������ʱ����ֱ�ӷ��ض�����Ҫ�����ݿ�(�Ƚ�����
 ��Hibernate�ĳ־û�),Ҫ�����߳�ͬ�����ж�(��д��)
 *
 */
public class CacheDemo {

	private static Map<String, Object> cache = new HashMap<String, Object>(); // ׼�������������ݵ�"����"

	private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock(); // ׼����д��

	public Object getData(String key) {

		Object data = null;

		rwl.readLock().lock();
		try {
			data = cache.get(key); // �ڻ�����������
			if (data == null) {// ������ֻ�����û��������ݣ���Ҫд����(�⿪����������д��)

				rwl.readLock().unlock(); // ��עһ
				rwl.writeLock().lock();
				try {
					if (data == null) { // �����ж�dataΪ�յ�ԭ��:����2���̣߳��߳�1������д�����߳�2�ڡ���עһ�������������߳�1�⿪д����,�߳�2��ֱ�ӽ���д�������������ظ�д��
						data = "get data from DataBase...."; // ʡ�Դ����ݿ�����ݵĹ���
						cache.put(key, data); // �Ѳ鵽�����ݱ��浽���棬�´ο���ֱ�ӻ�ȡ
					}
				} finally {
					rwl.writeLock().unlock(); //�ͷ�д��
				}
				rwl.readLock().lock();// ���ͷ�д����Ҫ�ǵ��ٴμ��϶�������Ϊ��Ҫ���ж�ȡ����
				
				System.out.println("��ȡ:����������:"+data);
				
			}
		} finally {
			rwl.readLock().unlock(); //���ս⿪��������ɻ�ȡ
		}

		return data;

	}

}
