package com.qf.dao;

import com.qf.entity.User;

/**
 * �û� DAO �ӿ�
 * 
 * @author С��
 *
 */
public interface UserDao {
	/**
	 * �����û��������ȡ�û�����
	 * 
	 * @param username
	 *            �û���
	 * @param password
	 *            ����
	 * @return ��ѯ�����û�����
	 */
	User get(String username, String password);

	/**
	 * ���ݲ����û���Ϣ��ȡ���û�����
	 * 
	 * @param user
	 *            �û���Ϣ
	 * @return �û�����
	 */
	User get(User user);
}
