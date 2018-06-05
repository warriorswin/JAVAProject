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
			//异常处理待添加
			User user=userDao.findByUserID(user_id);
		 
		    if(user!=null) {
		    	result.setStatus(user.getBuilds().size());
		    	result.setMsg("查询成功");
			    result.setData(user.getBuilds());
		    }else {
		    	result.setStatus(-1);
		    	result.setMsg("没有该用户");
		    }
		    
//		    System.out.println(user.getBuilds());
		    
		}
		return result;
	}
	@Override
	public MinitorResult<Object> addBuild(String user_id, String build_num, String build_name) {
		MinitorResult<Object> result=new MinitorResult<Object>();
		//参数检查
		if(user_id==null||"".equals(user_id)) {
			 result.setStatus(-1);
			 result.setMsg("没有获得user_id");
			 return result;
		}
		if(build_num==null||"".equals(build_num)) {
			 result.setStatus(-1);
			 result.setMsg("没有获得build_num");
			 return result;
		}
		if(build_name==null||"".equals(build_name)) {
			 result.setStatus(-1);
			 result.setMsg("没有获得user_id");
			 return result;
		}
		//判断楼栋是否存在通过楼栋的名字
		Build build=buildDao.findBuildByNum(build_num);
		if(build==null) {
			//该楼栋不存在，则创建
			Build newBuild=new Build();
			String newBuildId=MinitorUtil.createId();
			newBuild.setBuildID(newBuildId);
			newBuild.setBuildName(build_name);
			newBuild.setBuildNumber(build_num);
			//插入build
			buildDao.save(newBuild);
			//插入UserBuild
			Map<String,String> ids=new HashMap<String,String>();
			ids.put("user_id", user_id);
			ids.put("build_id",newBuildId);
			
			userBuildDao.save(ids);
			result.setStatus(0);
			result.setMsg("楼栋信息已创建,并加入该用户管理权限");
			result.setData(newBuildId);
			return result;
			
		}else {
			//判断楼栋是否已经添加到该用户管理
			//查询该用户的所有楼栋信息
			boolean exist=false;
			List<UserBuild> userBuilds=userBuildDao.findByUserId(user_id); 
			for(UserBuild userBuild:userBuilds) {
				if(build.getBuildID().equals(userBuild.getBuild_id())) {
					exist=true;
				    break;
				}
				
			}
			//如果已存在已存在管理管理权限，则直接返回
			if(exist) {
				result.setStatus(1);
				result.setMsg("该用户对该楼栋已有管理权限");
				return result;
				
			}else {
				//加入管理权限
				Map<String,String> ids=new HashMap<String,String>();
				ids.put("user_id", user_id);
				ids.put("build_id", build.getBuildID());
				userBuildDao.save(ids);
				result.setStatus(2);
				result.setMsg("权限加入成功");
				result.setData(build.getBuildID());
				return result;
			}
		}
		
	}
	@Override
	public MinitorResult<Object> removeBuild(String user_id, 
			                                    String build_id) {
		MinitorResult<Object> result=new MinitorResult<Object>();
		//delete hdu_user_role_build中的关系
		 //参数检查
		if(user_id==null||build_id==null
				||"".equals(user_id)||"".equals(build_id)) {
			result.setStatus(-1);
			return result;
		}
		/*
		 * 事务管理，待处理
		 */
		//hdu_user_role_build 
		Map<String,String> ids=new HashMap<String,String>();
		ids.put("user_id", user_id);
		ids.put("build_id",build_id);
		userBuildDao.delete(ids);
		
		//解除该用户该楼栋的room管理权限 hdu_user_role_room
		   //查询rooms->rooms id
		 List<Room> rooms=roomDao.findRoomByBuUsId(ids);
		 if(rooms==null) {
			 result.setStatus(0);
			 result.setMsg("该用户在该楼栋没有监控的房间");
			 return result;
		 }else {
			 //解除user_role_room的关系
			 for(Room room:rooms) {
				 Map<String,String> urids=new HashMap<String,String>();
				 urids.put("user_id", user_id);
				 urids.put("room_id", room.getRoomID());
				 userRoomDao.delete(urids);
			 }
			 result.setStatus(1);
			 result.setMsg("该用户的管理的该楼栋的关系已解除");
			 return result;
		 }
		

	}

}
