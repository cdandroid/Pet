package com.qf.dao;

import java.util.List;

import com.qf.entity.Pet;

/**
 * 宠物 DAO 接口
 * 
 * @author 小明
 *
 */
public interface PetDao {

	/**
	 * 加载所有宠物信息
	 * 
	 * @return 所有宠物列表
	 */
	List<Pet> loadAll();

	/**
	 * 更新指定宠物信息
	 * 
	 * @param pet
	 *            待更新宠物
	 * @return 是否更新成功
	 */
	boolean update(Pet pet);

	/**
	 * 删除指定宠物信息
	 * 
	 * @param pet
	 *            待删除宠物
	 * @return 是否删除成功
	 */
	boolean delete(Pet pet);

	/**
	 * 删除所有宠物
	 * 
	 * @return 是否删除成功
	 */
	boolean deleteAll();

	/**
	 * 保存指定的所有宠物
	 * 
	 * @param pets
	 *            待保存的宠物列表
	 * @return 是否保存成功
	 */
	boolean save(List<Pet> pets);

	/**
	 * 保存指定的宠物
	 * 
	 * @param pet
	 *            待保存宠物
	 * @return 是否保存成功
	 */
	boolean save(Pet pet);
}
