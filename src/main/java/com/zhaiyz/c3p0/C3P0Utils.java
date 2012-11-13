package com.zhaiyz.c3p0;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3P0Utils {
	private static C3P0Utils dbcputils = null;
	private ComboPooledDataSource cpds = null;

	private static final Logger LOGGER = Logger.getLogger(C3P0Utils.class);

	private C3P0Utils() {
		if (cpds == null) {
			cpds = new ComboPooledDataSource();
		}
		cpds.setUser("ptccmall_dev");
		cpds.setPassword("ptccmall_dev");
		cpds.setJdbcUrl("jdbc:oracle:thin:@130.251.101.4:1521:orcl");
		try {
			cpds.setDriverClass("oracle.jdbc.OracleDriver");
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		cpds.setInitialPoolSize(20);
		cpds.setMaxIdleTime(20);
		cpds.setMaxPoolSize(30);
		cpds.setMinPoolSize(10);
	}

	public synchronized static C3P0Utils getInstance() {
		if (dbcputils == null)
			dbcputils = new C3P0Utils();
		return dbcputils;
	}

	public Connection getConnection() {
		Connection con = null;
		try {
			con = cpds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	public static void main(String[] args) throws SQLException {
		Connection con = null;
		long begin = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
			con = C3P0Utils.getInstance().getConnection();
			LOGGER.info(con);
			con.close();
		}
		long end = System.currentTimeMillis();
		System.out.println("耗时为:" + (end - begin) + "ms");
	}
}