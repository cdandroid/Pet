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
	 * 查询所有宠物信息
	 * 
	 * @return 所有宠物的列表
	 */
	List<Pet> getAll();

	/**
	 * 查询所有未被领养的宠物信息
	 * 
	 * @return 未被领养宠物的列表
	 */
	List<Pet> getNotAdopted();

	/**
	 * 更新单个宠物信息
	 * 
	 * @param pet
	 *            待更新信息的宠物对象
	 * @return 是否更新成功
	 */
	boolean update(Pet pet);

	/**
	 * 批量更新宠物信息
	 * 
	 * @param pets
	 *            批量宠物列表
	 * @return 是否更新成功
	 */
	boolean update(List<Pet> pets);

	/**
	 * 删除指定宠物
	 * 
	 * @param pet
	 *            待删除宠物对象
	 * @return 是否删除成功
	 */
	boolean delete(Pet pet);

	/**
	 * 保存宠物信息
	 * 
	 * @param pet
	 *            待保存宠物
	 * @return 是否保存成功
	 */
	boolean save(Pet pet);

	/**
	 * 批量保存宠物信息
	 * 
	 * @param pets
	 *            宠物列表
	 * @return 是否保存成功
	 */
	boolean save(List<Pet> pets);

	/**
	 * 根据用户编号获取该用户所领养的所有宠物
	 * 
	 * @param uid
	 *            用户编号
	 * @return 宠物的列表
	 */
	List<Pet> getByUid(int uid);
}
