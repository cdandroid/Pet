package com.qf.service;

import java.util.List;

import com.qf.entity.Pet;

/**
 * ����ӿ�
 * 
 * @author С��
 *
 */
public interface ManageService {

	/**
	 * �ӷ�����ͬ��
	 * 
	 * @return ͬ����ĳ����б�
	 */
	List<Pet> loadFromServer();

	/**
	 * ���ط�����δ�������ĳ���
	 * 
	 * @return �����б�
	 */
	List<Pet> loadStorePets();

	/**
	 * ���ط����������г���
	 * 
	 * @return �����б�
	 */
	List<Pet> loadAllStorePets();

	/**
	 * ��¼
	 * 
	 * @param username
	 *            �û���
	 * @param password
	 *            ����
	 * @param isUser
	 *            ��¼���ͣ� �Ƿ���ͨ�û�
	 * @return �Ƿ��¼�ɹ�
	 */
	boolean login(String username, String password, boolean isUser);
	
	/**
	 * ��ָ���б�ĳ���ͬ������������
	 * @param pets ָ���ĳ����б�
	 * @return �Ƿ�ͬ���ɹ�
	 */
	boolean syncToServer(List<Pet> pets);
}
