package com.qf.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * JDBC辅助类
 * 
 * @author 小明
 *
 */
public class JdbcUtil {

	/* 最好使用配置文件保存 */
	private static String driver = "org.sqlite.JDBC"; // 驱动
	private static String url; // 连接字符串

	private static Connection conn; // Connection对象
	private static PreparedStatement pstmt; // PreparedStatement对象
	private static ResultSet rs; // ResultSet对象

	/* 加载数据库驱动 */
	static {
		try {
			Class.forName(driver);
			url = "jdbc:sqlite:" + Initialization.path;
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	private JdbcUtil() {
	}

	/**
	 * 获取数据库连接
	 * 
	 * @return 连接对象
	 */
	public static Connection getConnection() {
		try {
			return conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 释放ResultSet资源
	 * 
	 * @param rs
	 */
	public static void close(ResultSet rs) {
		try {
			if (null != rs)
				rs.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 释放ResultSet资源
	 * 
	 * @param rs
	 */
	public static void close(Statement stmt) {
		try {
			if (null != stmt)
				stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 释放ResultSet资源
	 * 
	 * @param rs
	 */
	public static void close(Connection conn) {
		try {
			if (null != conn)
				conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 释放所有资源
	 */
	public static void close() {
		try {
			if (null != rs) {
				rs.close();
			}
			rs = null;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (null != pstmt) {
					pstmt.close();
				}
				pstmt = null;
			} catch (SQLException e) {
				throw new RuntimeException(e);
			} finally {
				try {
					if (null != conn) {
						conn.close();
					}
					conn = null;
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	/**
	 * 执行增删改
	 * 
	 * @param sql
	 *            待执行的SQL语句
	 * @param params
	 *            SQL语句中?占位符的参数
	 * @return 受影响的行数
	 */
	public static int executeUpdate(String sql, Object... params) {
		try {
			pstmt = getConnection().prepareStatement(sql);

			if (null != params) { // 为SQL语句中?占位符设置参数
				for (int i = 0; i < params.length; i++) {
					pstmt.setObject(i + 1, params[i]);
				}
			}

			return pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			close();
		}
	}

	/**
	 * 实现查询
	 * 
	 * @param sql
	 *            待执行的查询SQL语句
	 * @param params
	 *            SQL语句中?占位符的参数
	 * @return 查询结果集ResultSet对象
	 */
	public static ResultSet executeQuery(String sql, Object... params) {
		try {
			pstmt = getConnection().prepareStatement(sql);

			if (null != params) { // 为SQL语句中?占位符设置参数
				for (int i = 0; i < params.length; i++) {
					pstmt.setObject(i + 1, params[i]);
				}
			}

			return rs = pstmt.executeQuery();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
