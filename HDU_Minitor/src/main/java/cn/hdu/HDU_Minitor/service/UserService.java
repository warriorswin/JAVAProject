package cn.hdu.HDU_Minitor.service;

import cn.hdu.HDU_Minitor.entity.User;
import cn.hdu.HDU_Minitor.util.MinitorResult;

public interface UserService {
	public MinitorResult<Object> checkLogin(String userPhone,String password);
	public MinitorResult<Object> RdmPwdGenertor(String userPhone);
	
	/**
	 * ���س��˹���Ա֮��������û���Ϣ
	 */
	public MinitorResult<Object> loadAllUsers(String adminID);
	/**
	 * ������޸��û���Ϣ
	 * @param user �û�������Ϣ
	 * @return MinitorResult<Object> ��������
	 */
	public MinitorResult<Object> insertOrUpdateUserInfo(User user);

	/**
	 * �����û�(by user_phone)
	 */
	public MinitorResult<Object> insertUser(String user_name,String user_phone);
	
	/**
	 * ɾ���û�(by user_id)
	 */
	public MinitorResult<Object> deleteUser(String user_id);
}
