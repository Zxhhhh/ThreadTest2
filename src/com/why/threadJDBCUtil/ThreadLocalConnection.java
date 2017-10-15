package com.why.threadJDBCUtil;

import java.sql.Connection;

import com.why.threadJDBCUtil.JDBCPoolUtil;

public class ThreadLocalConnection {
	
	private static ThreadLocal<Connection> connections = new ThreadLocal<Connection>();
	
	private ThreadLocalConnection(){} //�߳��ڵ���
	
	public static Connection getLocalConnection(){
		
		Connection instance = connections.get();
		if(instance==null){
			instance = JDBCPoolUtil.getConnection();
			connections.set(instance);
		}
		
		return instance;
		
	}
	
	public static void main(String[] args) {
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				
				Connection connect1 = ThreadLocalConnection.getLocalConnection();
				System.out.println(connect1); //com.mchange.v2.c3p0.impl.NewProxyConnection@510422a
				
				Connection connect2 = ThreadLocalConnection.getLocalConnection();
				System.out.println(connect1==connect2); //���ΪTrue��˵��ͬһ�߳���ȷʵ��ͬһ��Connection
				
			}
		}).start();
		
		//��ͬ�̼߳�����һ��Connection����
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				
				Connection connect1 = ThreadLocalConnection.getLocalConnection();
				System.out.println(connect1); //com.mchange.v2.c3p0.impl.NewProxyConnection@c7d6ff4
			}
		}).start();
	}
	
	

}
