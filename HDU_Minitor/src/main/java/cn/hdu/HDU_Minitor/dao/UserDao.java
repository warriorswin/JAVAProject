package cn.hdu.HDU_Minitor.dao;

import cn.hdu.HDU_Minitor.entity.User;

public interface UserDao {
	User findByUserPhone(String userPhone);
	/**
	 * 存入用户信息以及监控权限
	 * @param user 用户信息及监控权限
	 * @return 存入内容的个数，成功为非零，失败为零
	 */
	Integer saveUser(User user);
}
