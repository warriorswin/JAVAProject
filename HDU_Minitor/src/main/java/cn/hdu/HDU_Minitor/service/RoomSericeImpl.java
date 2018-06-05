package cn.hdu.HDU_Minitor.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.hdu.HDU_Minitor.dao.RoomDao;
import cn.hdu.HDU_Minitor.dao.UserRoomDao;
import cn.hdu.HDU_Minitor.entity.Room;
import cn.hdu.HDU_Minitor.entity.UserRoom;
import cn.hdu.HDU_Minitor.util.MinitorResult;
import cn.hdu.HDU_Minitor.util.MinitorUtil;

@Service("roomService")
public class RoomSericeImpl implements RoomService{

	@Resource(name="roomDao")
	private RoomDao roomDao;
	@Resource(name="userRoomDao")
	private UserRoomDao userRoomDao;
	
	@Override
	public MinitorResult<Object> findUsersRoomOfBuild(String build_id,String user_id) {
		 MinitorResult<Object> result=new MinitorResult<Object>();
		 //异常处理
		 Map<String,String> ids=new HashMap<String,String>();
		 ids.put("build_id", build_id);
		 ids.put("user_id", user_id);
		 List<Room> rooms=roomDao.findRoomByBuUsId(ids);
		 result.setStatus(rooms.size());
		 result.setMsg("查询成功");
		 result.setData(rooms);
		 
		return result;
	}
	@Override
	public MinitorResult<Object> addRoom(
			       String user_id, String build_id, 
			           String room_name, String device_id) {
		//参数检查
	    if(user_id==null||"".equals(user_id)
	    		  ||build_id==null||"".equals(build_id)
	    		    ||room_name==null||"".equals(room_name)
	    		      ||device_id==null||"".equals(device_id)) {
	    	return null;
	    }
	    MinitorResult<Object> result=new MinitorResult<Object>();
		//先根据device_id,在hdu_monitor_build_room中查询
		 Room room=roomDao.findRoomByDId(device_id);
		 //存在
		 if(room!=null) {
		  //是否对应build_id
			  if(build_id.equals(room.getBuildID())) {
		      //是
		         //是否已经权限 即 user_id与room_id对应
				  	String room_id=room.getRoomID();
				    UserRoom userRoom=userRoomDao.findURByRId(room_id);
				    	if(userRoom!=null&&userRoom.getUser_id().equals(user_id)) {
				    		//是直接返回
				    		result.setStatus(0);
				    	    result.setMsg("该设备对应房间"+room.getRoomName()
				    	    	+",且已有管理权限;不可以重复添加,输入的房间名将无效");
				    	    return result;
				    	}else {
				    		//否 save
				    		Map<String,String>ids=new 
				    				HashMap<String,String>();
				    		ids.put("user_id", user_id);
				    		ids.put("room_id", room_id);
				    		if(1==userRoomDao.save(ids)) {
				    			result.setStatus(1);
				    			result.setMsg("在hdu_monitor_role_room插入成功");
				    		}
				    		return result;
				    		
				    	}
			  }else {
		     //否 
		         //返回错误
				  result.setStatus(-1);
				  result.setMsg("该设备ID已在其他楼栋使用，不属于该楼栋,操作无效，请检查设备ID");
				  return result;
			  }
		 }else {
			//不存在
			 //改进 异常处理
			
		   //insert hdu_monitor_build_room和hdu_user_role_room
			Room newRoom=new Room();
			
			//try catch
			String newRoomId=MinitorUtil.createId();
			newRoom.setRoomID(newRoomId);
			newRoom.setBuildID(build_id);
			newRoom.setDeviceID(device_id);
			newRoom.setRoomName(room_name);
			roomDao.save(newRoom);
			Map<String,String>ids=new HashMap<String,String>();
			ids.put("user_id", user_id);
			ids.put("room_id",newRoomId);
		    userRoomDao.save(ids);
		    
		    result.setStatus(3);
			result.setMsg("新设备添加成功");
			return result;
			
		 }
	
	}

}
