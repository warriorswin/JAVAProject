package cn.hdu.HDU_Minitor.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.hdu.HDU_Minitor.dao.BuildDao;
import cn.hdu.HDU_Minitor.dao.RoomDao;
import cn.hdu.HDU_Minitor.dao.UserBuildDao;
import cn.hdu.HDU_Minitor.dao.UserDao;
import cn.hdu.HDU_Minitor.dao.UserRoomDao;
import cn.hdu.HDU_Minitor.entity.Build;
import cn.hdu.HDU_Minitor.entity.Room;
import cn.hdu.HDU_Minitor.entity.User;
import cn.hdu.HDU_Minitor.entity.UserBuild;
import cn.hdu.HDU_Minitor.util.MinitorResult;
import cn.hdu.HDU_Minitor.util.MinitorUtil;

@Service("buildService")
public class BuildServiceImpl implements BuildService {
	@Resource(name="userDao")
	private UserDao userDao;
	@Resource(name="buildDao")
	private BuildDao buildDao;
	@Resource(name="roomDao")
	private RoomDao roomDao;
	@Resource(name="userBuildDao")
	private UserBuildDao userBuildDao;
	@Resource(name="userRoomDao")
	private UserRoomDao userRoomDao;
	
	@Override
	public MinitorResult<Object> loadBuild(String user_id) {
		   MinitorResult<Object> result=new MinitorResult<Object>();
		if(user_id!=null) {
			//�쳣��������
			User user=userDao.findByUserID(user_id);
		 
		    if(user!=null) {
		    	result.setStatus(user.getBuilds().size());
		    	result.setMsg("��ѯ�ɹ�");
			    result.setData(user.getBuilds());
		    }else {
		    	result.setStatus(-1);
		    	result.setMsg("û�и��û�");
		    }
		    
//		    System.out.println(user.getBuilds());
		    
		}
		return result;
	}
	@Override
	public MinitorResult<Object> addBuild(String user_id, String build_num, String build_name) {
		MinitorResult<Object> result=new MinitorResult<Object>();
		//�������
		if(user_id==null||"".equals(user_id)) {
			 result.setStatus(-1);
			 result.setMsg("û�л��user_id");
			 return result;
		}
		if(build_num==null||"".equals(build_num)) {
			 result.setStatus(-1);
			 result.setMsg("û�л��build_num");
			 return result;
		}
		if(build_name==null||"".equals(build_name)) {
			 result.setStatus(-1);
			 result.setMsg("û�л��user_id");
			 return result;
		}
		//�ж�¥���Ƿ����ͨ��¥��������
		Build build=buildDao.findBuildByNum(build_num);
		if(build==null) {
			//��¥�������ڣ��򴴽�
			Build newBuild=new Build();
			String newBuildId=MinitorUtil.createId();
			newBuild.setBuildID(newBuildId);
			newBuild.setBuildName(build_name);
			newBuild.setBuildNumber(build_num);
			//����build
			buildDao.save(newBuild);
			//����UserBuild
			Map<String,String> ids=new HashMap<String,String>();
			ids.put("user_id", user_id);
			ids.put("build_id",newBuildId);
			
			userBuildDao.save(ids);
			result.setStatus(0);
			result.setMsg("¥����Ϣ�Ѵ���,��������û�����Ȩ��");
			result.setData(newBuildId);
			return result;
			
		}else {
			//�ж�¥���Ƿ��Ѿ���ӵ����û�����
			//��ѯ���û�������¥����Ϣ
			boolean exist=false;
			List<UserBuild> userBuilds=userBuildDao.findByUserId(user_id); 
			for(UserBuild userBuild:userBuilds) {
				if(build.getBuildID().equals(userBuild.getBuild_id())) {
					exist=true;
				    break;
				}
				
			}
			//����Ѵ����Ѵ��ڹ������Ȩ�ޣ���ֱ�ӷ���
			if(exist) {
				result.setStatus(1);
				result.setMsg("���û��Ը�¥�����й���Ȩ��");
				return result;
				
			}else {
				//�������Ȩ��
				Map<String,String> ids=new HashMap<String,String>();
				ids.put("user_id", user_id);
				ids.put("build_id", build.getBuildID());
				userBuildDao.save(ids);
				result.setStatus(2);
				result.setMsg("Ȩ�޼���ɹ�");
				result.setData(build.getBuildID());
				return result;
			}
		}
		
	}
	@Override
	public MinitorResult<Object> removeBuild(String user_id, 
			                                    String build_id) {
		MinitorResult<Object> result=new MinitorResult<Object>();
		//delete hdu_user_role_build�еĹ�ϵ
		 //�������
		if(user_id==null||build_id==null
				||"".equals(user_id)||"".equals(build_id)) {
			result.setStatus(-1);
			return result;
		}
		/*
		 * �������������
		 */
		//hdu_user_role_build 
		Map<String,String> ids=new HashMap<String,String>();
		ids.put("user_id", user_id);
		ids.put("build_id",build_id);
		userBuildDao.delete(ids);
		
		//������û���¥����room����Ȩ�� hdu_user_role_room
		   //��ѯrooms->rooms id
		 List<Room> rooms=roomDao.findRoomByBuUsId(ids);
		 if(rooms==null) {
			 result.setStatus(0);
			 result.setMsg("���û��ڸ�¥��û�м�صķ���");
			 return result;
		 }else {
			 //���user_role_room�Ĺ�ϵ
			 for(Room room:rooms) {
				 Map<String,String> urids=new HashMap<String,String>();
				 urids.put("user_id", user_id);
				 urids.put("room_id", room.getRoomID());
				 userRoomDao.delete(urids);
			 }
			 result.setStatus(1);
			 result.setMsg("���û��Ĺ���ĸ�¥���Ĺ�ϵ�ѽ��");
			 return result;
		 }
		

	}

}
