package com.qf.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.qf.dao.UserDao;
import com.qf.entity.User;
import com.qf.util.JdbcUtil;

public class UserDaoImpl implements UserDao {

	@Override
	public User get(String username, String password) {
		ResultSet rs = JdbcUtil.executeQuery("SELECT id, username, password FROM users WHERE username=? AND password=?",
				username, password);
		try {
			User user = null;
			if (rs.next())
				user = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
			return user;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close();
		}
	}

	@Override
	public User get(User user) {
		return get(user.getUsername(), user.getPassword());
	}

}
