package cn.hdu.HDU_Minitor.dao;

import cn.hdu.HDU_Minitor.entity.User;

public interface UserDao {
	User findByUserPhone(String userPhone);
	/**
	 * �����û���Ϣ�Լ����Ȩ��
	 * @param user �û���Ϣ�����Ȩ��
	 * @return �������ݵĸ������ɹ�Ϊ���㣬ʧ��Ϊ��
	 */
	Integer saveUser(User user);
}
