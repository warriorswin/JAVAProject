package cn.hdu.HDU_Minitor.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.hdu.HDU_Minitor.dao.UserDao;
import cn.hdu.HDU_Minitor.entity.User;
import cn.hdu.HDU_Minitor.util.MinitorResult;
import cn.hdu.HDU_Minitor.util.MinitorUtil;
import cn.hdu.HDU_Minitor.util.SendPhoneMsg;

@Service("userService")
public class UserServiceImpl implements UserService{
	
	@Resource(name="userDao")
	private UserDao userDao;
	/*
	 * 登录验证
	 * (non-Javadoc)
	 * @see cn.hdu.HDU_Minitor.service.UserService#checkLogin(java.lang.String, java.lang.String)
	 */
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
	@Override
	/*
	 * 随机密码产生
	 * (non-Javadoc)
	 * @see cn.hdu.HDU_Minitor.service.UserService#RdmPwdGenertor(java.lang.String)
	 */
	public MinitorResult<Object> RdmPwdGenertor(String userPhone) {
		MinitorResult<Object> result=new MinitorResult<Object>();
		
		//查询数据库，验证是否有该用户
		User user=userDao.findByUserPhone(userPhone);
		if(user==null) {
			result.setStatus(1);
			result.setMsg("没有该用户");
			return result;
			
		}else {
		
			//产生随机密码返回
			String randompwd=MinitorUtil.createId().substring(0, 6);
			 //发送短信
			System.out.println(userPhone+":"+randompwd);
			//Boolean bool=SendPhoneMsg.sendMsg(userPhone,randompwd);
			Boolean bool=false;
			if(bool) {
				result.setStatus(0);
				result.setMsg("随机密码已发送");
				
			}else {
				result.setStatus(2);
				result.setMsg("随机密码发送失败");
			}
			
		}
		
		return result;
	}

}
