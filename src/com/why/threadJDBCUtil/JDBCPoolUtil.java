package com.why.threadJDBCUtil;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.UUID;


import com.mchange.v2.c3p0.ComboPooledDataSource;

public final class JDBCPoolUtil {

	/*
	 * �����������ӳصķ�ʽ�������Connection�ṩ�û�ʹ�ã���ֱ�Ӵ���ConnectionЧ�ʸ��?
	 */


	private static ComboPooledDataSource dataSource;

	private JDBCPoolUtil() {
	}

	static {

		dataSource = new ComboPooledDataSource();
		Properties properties = new Properties();
		InputStream is = JDBCPoolUtil.class.getClassLoader()
				.getResourceAsStream("JDBCPoolConfig.properties");

		try {
			properties.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// ����������ݿ���˻������룬url�����?
		dataSource.setUser(properties.getProperty("username"));
		dataSource.setPassword(properties.getProperty("password"));
		dataSource.setJdbcUrl(properties.getProperty("jdbcUrl"));
		try {
			dataSource.setDriverClass(properties.getProperty("driverClass"));
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}

		// �������ӳ���С����󣬿���ʱ��Ȳ���
		dataSource.setInitialPoolSize(Integer.parseInt(properties
				.getProperty("initialPoolSize")));
		dataSource.setMinPoolSize(Integer.parseInt(properties
				.getProperty("minPoolSize")));
		dataSource.setMaxPoolSize(Integer.parseInt(properties
				.getProperty("maxPoolSize")));
		dataSource.setMaxIdleTime(Integer.parseInt(properties
				.getProperty("maxIdleTime")));
		dataSource.setIdleConnectionTestPeriod(Integer.parseInt(properties
				.getProperty("idleConnectionTestPeriod")));

		try {
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	public static void close(Object obj) {

		if (obj == null) {
			return;
		}
		if (obj instanceof Connection) {
			try {
				((Connection) obj).close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (obj instanceof Statement) {
			try {
				((Statement) obj).close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

}
