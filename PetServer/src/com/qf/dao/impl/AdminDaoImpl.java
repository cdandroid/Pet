package com.qf.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.qf.dao.AdminDao;
import com.qf.entity.Administrator;
import com.qf.util.JdbcUtil;

public class AdminDaoImpl implements AdminDao {

	@Override
	public Administrator get(String username, String password) {
		ResultSet rs = JdbcUtil.executeQuery(
				"SELECT id, username, password FROM administrator WHERE username=? AND password=?", username, password);
		try {
			Administrator admin = null;
			if (rs.next()) {
				admin = new Administrator(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
			}
			return admin;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close();
		}
	}

	@Override
	public Administrator get(Administrator admin) {
		return get(admin.getUsername(), admin.getPassword());
	}

}
