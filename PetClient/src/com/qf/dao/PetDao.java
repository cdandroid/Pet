package com.qf.dao;

import java.util.List;

import com.qf.entity.Pet;

/**
 * ���� DAO �ӿ�
 * 
 * @author С��
 *
 */
public interface PetDao {

	/**
	 * �������г�����Ϣ
	 * 
	 * @return ���г����б�
	 */
	List<Pet> loadAll();

	/**
	 * ����ָ��������Ϣ
	 * 
	 * @param pet
	 *            �����³���
	 * @return �Ƿ���³ɹ�
	 */
	boolean update(Pet pet);

	/**
	 * ɾ��ָ��������Ϣ
	 * 
	 * @param pet
	 *            ��ɾ������
	 * @return �Ƿ�ɾ���ɹ�
	 */
	boolean delete(Pet pet);

	/**
	 * ɾ�����г���
	 * 
	 * @return �Ƿ�ɾ���ɹ�
	 */
	boolean deleteAll();

	/**
	 * ����ָ�������г���
	 * 
	 * @param pets
	 *            ������ĳ����б�
	 * @return �Ƿ񱣴�ɹ�
	 */
	boolean save(List<Pet> pets);

	/**
	 * ����ָ���ĳ���
	 * 
	 * @param pet
	 *            ���������
	 * @return �Ƿ񱣴�ɹ�
	 */
	boolean save(Pet pet);
}
