package com.qf.dao;

import com.qf.entity.Administrator;

/**
 * ����Ա DAO �ӿ�
 * 
 * @author С��
 *
 */
public interface AdminDao {
	/**
	 * �����û��������ȡ����Ա��Ϣ
	 * 
	 * @param username
	 *            �û���
	 * @param password
	 *            ����
	 * @return ����Ա����
	 */
	Administrator get(String username, String password);

	/**
	 * ���ݹ���Ա�����ȡ�ù���Ա����
	 * 
	 * @param admin
	 *            ����Ա����
	 * @return ����Ա
	 */
	Administrator get(Administrator admin);
}
