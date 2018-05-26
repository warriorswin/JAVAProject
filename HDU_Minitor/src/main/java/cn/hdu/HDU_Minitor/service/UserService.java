package cn.hdu.HDU_Minitor.service;

import cn.hdu.HDU_Minitor.entity.User;
import cn.hdu.HDU_Minitor.util.MinitorResult;

public interface UserService {
	public MinitorResult<Object> checkLogin(String userPhone,String password);
	public MinitorResult<Object> RdmPwdGenertor(String userPhone);
	
	/**
	 * ������޸��û���Ϣ
	 * @param user �û�������Ϣ
	 * @return MinitorResult<Object> ��������
	 */
	public MinitorResult<Object> insertOrUpdateUserInfo(User user);
}
