package cn.hdu.HDU_Minitor.dao;

import java.util.Map;

import cn.hdu.HDU_Minitor.entity.User;

public interface UserDao {
	
	/**
	 * 通过用户的手机号来查询user信息
	 * @param userPhone 用户的电话号码
	 * @return 用户信息以及监控权限
	 */
	User findByUserPhone(String userPhone);
	
	/**
	 * 通过用户的ID来查询user信息
	 * @param userID 用户ID
	 * @return 用户的信息
	 */
	User findByUserID(String userID);
	
	/**
	 * 存入用户信息以及监控权限
	 * @param user 用户信息及监控权限
	 * @return 存入内容的个数，成功为非零，失败为零
	 */
	Integer saveUser(User user);
	
	/**
	 * 更新用户的信息
	 * @param updateInfo 所要更新的用户信息的字段名和值
	 * @return 更新是否成功，成功为非零，失败为零
	 */
	Integer updateUser(Map<String, String> updateInfo);
	
	/**
	 * 删除用户的信息以及（或）所绑定的监控楼和房间
	 * @param user 用户信息及监控权限
	 * @return 更新是否成功，成功为非零，失败为零
	 */
	Integer deleteUser(User user);
}
