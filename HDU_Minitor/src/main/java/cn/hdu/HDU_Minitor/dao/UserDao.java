package cn.hdu.HDU_Minitor.dao;

import java.util.Map;

import cn.hdu.HDU_Minitor.entity.User;

public interface UserDao {
	
	/**
	 * ͨ���û����ֻ�������ѯuser��Ϣ
	 * @param userPhone �û��ĵ绰����
	 * @return �û���Ϣ�Լ����Ȩ��
	 */
	User findByUserPhone(String userPhone);
	
	/**
	 * ͨ���û���ID����ѯuser��Ϣ
	 * @param userID �û�ID
	 * @return �û�����Ϣ
	 */
	User findByUserID(String userID);
	
	/**
	 * �����û���Ϣ�Լ����Ȩ��
	 * @param user �û���Ϣ�����Ȩ��
	 * @return �������ݵĸ������ɹ�Ϊ���㣬ʧ��Ϊ��
	 */
	Integer saveUser(User user);
	
	/**
	 * �����û�����Ϣ
	 * @param updateInfo ��Ҫ���µ��û���Ϣ���ֶ�����ֵ
	 * @return �����Ƿ�ɹ����ɹ�Ϊ���㣬ʧ��Ϊ��
	 */
	Integer updateUser(Map<String, String> updateInfo);
	
	/**
	 * ɾ���û�����Ϣ�Լ��������󶨵ļ��¥�ͷ���
	 * @param user �û���Ϣ�����Ȩ��
	 * @return �����Ƿ�ɹ����ɹ�Ϊ���㣬ʧ��Ϊ��
	 */
	Integer deleteUser(User user);
}
