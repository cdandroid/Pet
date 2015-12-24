package com.qf.service;

import java.util.List;

import com.qf.entity.Pet;

/**
 * 宠物服务层接口
 * 
 * @author 小明
 *
 */
public interface PetService {

	/**
	 * 查找所有宠物
	 * 
	 * @return 所有宠物的列表
	 */
	List<Pet> findAll();

	/**
	 * 喂养宠物
	 * 
	 * @param pet
	 *            待喂养宠物
	 * @return 是否喂养成功
	 */
	boolean feed(Pet pet);

	/**
	 * 和宠物玩耍
	 * 
	 * @param pet
	 *            宠物对象
	 * @return 是否操作成功
	 */
	boolean play(Pet pet);

	/**
	 * 删除宠物信息
	 * 
	 * @param pet
	 *            待删除宠物
	 * @return 是否删除成功
	 */
	boolean del(Pet pet);

	/**
	 * 删除所有宠物
	 * 
	 * @return 是否删除成功
	 */
	boolean removeAll();

	/**
	 * 保存指定的所有宠物
	 * 
	 * @param pets
	 *            待宠物宠物的列表
	 * @return 是否保存成功
	 */
	boolean savePets(List<Pet> pets);

	/**
	 * 领养宠物
	 * 
	 * @param pet
	 *            待领养宠物
	 * @return 是否领养成功
	 */
	boolean adopt(Pet pet);

	/**
	 * 更新服务器宠物信息
	 * 
	 * @param pet
	 *            待更新宠物
	 * @return 是否更新成功
	 */
	boolean updateServerPet(Pet pet);

	/**
	 * 删除服务器宠物信息
	 * 
	 * @param pet
	 *            待删除宠物
	 * @return 是否删除成功
	 */
	boolean deleteServerPet(Pet pet);

	/**
	 * 管理员添加服务器商的宠物信息
	 * 
	 * @param pet
	 *            待添加宠物
	 * @return 是否添加成功
	 */
	boolean addServerPet(Pet pet);
}
