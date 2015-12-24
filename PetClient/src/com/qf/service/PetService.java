package com.qf.service;

import java.util.List;

import com.qf.entity.Pet;

/**
 * ��������ӿ�
 * 
 * @author С��
 *
 */
public interface PetService {

	/**
	 * �������г���
	 * 
	 * @return ���г�����б�
	 */
	List<Pet> findAll();

	/**
	 * ι������
	 * 
	 * @param pet
	 *            ��ι������
	 * @return �Ƿ�ι���ɹ�
	 */
	boolean feed(Pet pet);

	/**
	 * �ͳ�����ˣ
	 * 
	 * @param pet
	 *            �������
	 * @return �Ƿ�����ɹ�
	 */
	boolean play(Pet pet);

	/**
	 * ɾ��������Ϣ
	 * 
	 * @param pet
	 *            ��ɾ������
	 * @return �Ƿ�ɾ���ɹ�
	 */
	boolean del(Pet pet);

	/**
	 * ɾ�����г���
	 * 
	 * @return �Ƿ�ɾ���ɹ�
	 */
	boolean removeAll();

	/**
	 * ����ָ�������г���
	 * 
	 * @param pets
	 *            �����������б�
	 * @return �Ƿ񱣴�ɹ�
	 */
	boolean savePets(List<Pet> pets);

	/**
	 * ��������
	 * 
	 * @param pet
	 *            ����������
	 * @return �Ƿ������ɹ�
	 */
	boolean adopt(Pet pet);

	/**
	 * ���·�����������Ϣ
	 * 
	 * @param pet
	 *            �����³���
	 * @return �Ƿ���³ɹ�
	 */
	boolean updateServerPet(Pet pet);

	/**
	 * ɾ��������������Ϣ
	 * 
	 * @param pet
	 *            ��ɾ������
	 * @return �Ƿ�ɾ���ɹ�
	 */
	boolean deleteServerPet(Pet pet);

	/**
	 * ����Ա��ӷ������̵ĳ�����Ϣ
	 * 
	 * @param pet
	 *            ����ӳ���
	 * @return �Ƿ���ӳɹ�
	 */
	boolean addServerPet(Pet pet);
}
