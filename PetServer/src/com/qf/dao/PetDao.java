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
	 * ��ѯ���г�����Ϣ
	 * 
	 * @return ���г�����б�
	 */
	List<Pet> getAll();

	/**
	 * ��ѯ����δ�������ĳ�����Ϣ
	 * 
	 * @return δ������������б�
	 */
	List<Pet> getNotAdopted();

	/**
	 * ���µ���������Ϣ
	 * 
	 * @param pet
	 *            ��������Ϣ�ĳ������
	 * @return �Ƿ���³ɹ�
	 */
	boolean update(Pet pet);

	/**
	 * �������³�����Ϣ
	 * 
	 * @param pets
	 *            ���������б�
	 * @return �Ƿ���³ɹ�
	 */
	boolean update(List<Pet> pets);

	/**
	 * ɾ��ָ������
	 * 
	 * @param pet
	 *            ��ɾ���������
	 * @return �Ƿ�ɾ���ɹ�
	 */
	boolean delete(Pet pet);

	/**
	 * ���������Ϣ
	 * 
	 * @param pet
	 *            ���������
	 * @return �Ƿ񱣴�ɹ�
	 */
	boolean save(Pet pet);

	/**
	 * �������������Ϣ
	 * 
	 * @param pets
	 *            �����б�
	 * @return �Ƿ񱣴�ɹ�
	 */
	boolean save(List<Pet> pets);

	/**
	 * �����û���Ż�ȡ���û������������г���
	 * 
	 * @param uid
	 *            �û����
	 * @return ������б�
	 */
	List<Pet> getByUid(int uid);
}
