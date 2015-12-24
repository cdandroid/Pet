package com.qf.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * JDBC������
 * 
 * @author С��
 *
 */
public class JdbcUtil {

	/* ���ʹ�������ļ����� */
	private static String driver = "org.sqlite.JDBC"; // ����
	private static String url; // �����ַ���

	private static Connection conn; // Connection����
	private static PreparedStatement pstmt; // PreparedStatement����
	private static ResultSet rs; // ResultSet����

	/* �������ݿ����� */
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
	 * ��ȡ���ݿ�����
	 * 
	 * @return ���Ӷ���
	 */
	public static Connection getConnection() {
		try {
			return conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * �ͷ�ResultSet��Դ
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
	 * �ͷ�ResultSet��Դ
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
	 * �ͷ�ResultSet��Դ
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
	 * �ͷ�������Դ
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
	 * ִ����ɾ��
	 * 
	 * @param sql
	 *            ��ִ�е�SQL���
	 * @param params
	 *            SQL�����?ռλ���Ĳ���
	 * @return ��Ӱ�������
	 */
	public static int executeUpdate(String sql, Object... params) {
		try {
			pstmt = getConnection().prepareStatement(sql);

			if (null != params) { // ΪSQL�����?ռλ�����ò���
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
	 * ʵ�ֲ�ѯ
	 * 
	 * @param sql
	 *            ��ִ�еĲ�ѯSQL���
	 * @param params
	 *            SQL�����?ռλ���Ĳ���
	 * @return ��ѯ�����ResultSet����
	 */
	public static ResultSet executeQuery(String sql, Object... params) {
		try {
			pstmt = getConnection().prepareStatement(sql);

			if (null != params) { // ΪSQL�����?ռλ�����ò���
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
