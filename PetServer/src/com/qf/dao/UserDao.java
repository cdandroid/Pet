package com.qf.dao;

import com.qf.entity.User;

/**
 * 用户 DAO 接口
 * 
 * @author 小明
 *
 */
public interface UserDao {
	/**
	 * 根据用户名密码获取用户对象
	 * 
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return 查询到的用户对象
	 */
	User get(String username, String password);

	/**
	 * 根据参数用户信息获取该用户详情
	 * 
	 * @param user
	 *            用户信息
	 * @return 用户对象
	 */
	User get(User user);
}
