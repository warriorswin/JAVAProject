package cn.hdu.HDU_Minitor.service;

import cn.hdu.HDU_Minitor.entity.User;
import cn.hdu.HDU_Minitor.util.MinitorResult;

public interface UserService {
	public MinitorResult<Object> checkLogin(String userPhone,String password);
	
	/**
	 * 插入或修改用户信息
	 * @param user 用户的新信息
	 * @return MinitorResult<Object> 处理结果类
	 */
	public MinitorResult<Object> insertOrUpdateUserInfo(User user);
}
