package cn.hdu.HDU_Minitor.service;

import cn.hdu.HDU_Minitor.entity.User;
import cn.hdu.HDU_Minitor.util.MinitorResult;

public interface UserService {
	public MinitorResult<Object> checkLogin(String userPhone,String password);
	public MinitorResult<Object> RdmPwdGenertor(String userPhone);
	
	/**
	 * 加载除了管理员之外的所有用户信息
	 */
	public MinitorResult<Object> loadAllUsers(String adminID);
	/**
	 * 插入或修改用户信息
	 * @param user 用户的新信息
	 * @return MinitorResult<Object> 处理结果类
	 */
	public MinitorResult<Object> insertOrUpdateUserInfo(User user);

	/**
	 * 增加用户(by user_phone)
	 */
	public MinitorResult<Object> insertUser(String user_name,String user_phone);
	
	/**
	 * 删除用户(by user_id)
	 */
	public MinitorResult<Object> deleteUser(String user_id);
}
