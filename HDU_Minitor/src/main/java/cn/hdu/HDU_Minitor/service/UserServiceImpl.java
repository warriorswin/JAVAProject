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
	 * ��¼��֤
	 * (non-Javadoc)
	 * @see cn.hdu.HDU_Minitor.service.UserService#checkLogin(java.lang.String, java.lang.String)
	 */
	@Override
	public MinitorResult<Object> checkLogin(String userPhone,String password) {
		User user=userDao.findByUserPhone(userPhone);
		MinitorResult<Object> result=new MinitorResult<Object>();
		if(user==null) {
			result.setStatus(1);
			result.setMsg("û�и��û�");	
			return result;
		}else {
			 if(user.getUser_password().equals(password)) {
				 result.setStatus(0);
				 result.setMsg("��¼�ɹ���");
				 result.setData(user.getUser_id());
				 return result;
			 }else {
				 result.setStatus(2);
				 result.setMsg("�������");
				 return result;
			 }
		}
	}
	@Override
	/*
	 * ����������
	 * (non-Javadoc)
	 * @see cn.hdu.HDU_Minitor.service.UserService#RdmPwdGenertor(java.lang.String)
	 */
	public MinitorResult<Object> RdmPwdGenertor(String userPhone) {
		MinitorResult<Object> result=new MinitorResult<Object>();
		
		//��ѯ���ݿ⣬��֤�Ƿ��и��û�
		User user=userDao.findByUserPhone(userPhone);
		if(user==null) {
			result.setStatus(1);
			result.setMsg("û�и��û�");
			return result;
			
		}else {
		
			//����������뷵��
			String randompwd=MinitorUtil.createId().substring(0, 6);
			 //���Ͷ���
			System.out.println(userPhone+":"+randompwd);
			//Boolean bool=SendPhoneMsg.sendMsg(userPhone,randompwd);
			Boolean bool=false;
			if(bool) {
				result.setStatus(0);
				result.setMsg("��������ѷ���");
				
			}else {
				result.setStatus(2);
				result.setMsg("������뷢��ʧ��");
			}
			
		}
		
		return result;
	}

}
