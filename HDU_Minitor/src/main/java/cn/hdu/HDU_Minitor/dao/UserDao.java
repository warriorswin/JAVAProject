package cn.hdu.HDU_Minitor.dao;

import cn.hdu.HDU_Minitor.entity.User;

public interface UserDao {
	User findByUserPhone(String userPhone);

}
