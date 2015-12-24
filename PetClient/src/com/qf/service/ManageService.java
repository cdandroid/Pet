package com.qf.service;

import java.util.List;

import com.qf.entity.Pet;

/**
 * 服务接口
 * 
 * @author 小明
 *
 */
public interface ManageService {

	/**
	 * 从服务器同步
	 * 
	 * @return 同步后的宠物列表
	 */
	List<Pet> loadFromServer();

	/**
	 * 加载服务器未被领养的宠物
	 * 
	 * @return 宠物列表
	 */
	List<Pet> loadStorePets();

	/**
	 * 加载服务器的所有宠物
	 * 
	 * @return 宠物列表
	 */
	List<Pet> loadAllStorePets();

	/**
	 * 登录
	 * 
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @param isUser
	 *            登录类型， 是否普通用户
	 * @return 是否登录成功
	 */
	boolean login(String username, String password, boolean isUser);
	
	/**
	 * 将指定列表的宠物同步到服务器中
	 * @param pets 指定的宠物列表
	 * @return 是否同步成功
	 */
	boolean syncToServer(List<Pet> pets);
}
