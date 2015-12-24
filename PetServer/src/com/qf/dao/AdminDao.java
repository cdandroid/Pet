package com.qf.dao;

import com.qf.entity.Administrator;

/**
 * 管理员 DAO 接口
 * 
 * @author 小明
 *
 */
public interface AdminDao {
	/**
	 * 根据用户名密码获取管理员信息
	 * 
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return 管理员对象
	 */
	Administrator get(String username, String password);

	/**
	 * 根据管理员对象获取该管理员详情
	 * 
	 * @param admin
	 *            管理员对象
	 * @return 管理员
	 */
	Administrator get(Administrator admin);
}
