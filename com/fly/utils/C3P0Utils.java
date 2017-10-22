package com.fly.utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3P0Utils {
	private static ComboPooledDataSource dataSource = new ComboPooledDataSource();
	private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();

	public static DataSource getDataSource() {
		return dataSource;
	}

	public static Connection getConnnection() throws SQLException {
		Connection conn = threadLocal.get();
		if(conn==null){
			conn = dataSource.getConnection();
			threadLocal.set(conn);
		}
		return conn;
	}

	public static void closeConnection() throws SQLException {
		Connection conn = threadLocal.get();
		if(conn != null){
			conn.close();
			threadLocal.remove();
		}
	}

}
