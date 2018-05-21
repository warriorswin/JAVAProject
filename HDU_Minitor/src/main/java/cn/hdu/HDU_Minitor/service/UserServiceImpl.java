package cn.hdu.HDU_Minitor.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.hdu.HDU_Minitor.dao.UserDao;
import cn.hdu.HDU_Minitor.entity.User;
import cn.hdu.HDU_Minitor.util.MinitorResult;

@Service("userService")
public class UserServiceImpl implements UserService{
	@Resource(name="userDao")
	private UserDao userDao;

	@Override
	public MinitorResult<Object> checkLogin(String userPhone,String password) {
		User user=userDao.findByUserPhone(userPhone);
		MinitorResult<Object> result=new MinitorResult<Object>();
		if(user==null) {
			result.setStatus(1);
			result.setMsg("没有该用户");	
			return result;
		}else {
			 if(user.getUser_password().equals(password)) {
				 result.setStatus(0);
				 result.setMsg("登录成功！");
				 result.setData(user.getUser_id());
				 return result;
			 }else {
				 result.setStatus(2);
				 result.setMsg("密码错误");
				 return result;
			 }
		}
	}

}
