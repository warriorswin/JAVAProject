package cn.hdu.HDU_Minitor.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import cn.hdu.HDU_Minitor.dao.RoomDao;
import cn.hdu.HDU_Minitor.dao.UserRoomDao;
import cn.hdu.HDU_Minitor.entity.Device;
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
	@Resource(name="redisTemplate")
	private RedisTemplate<String, Device> redisTemp;
	
	@Override
	public MinitorResult<Object> findUsersRoomOfBuild(String build_id,String user_id) {
		 MinitorResult<Object> result=new MinitorResult<Object>();
		 //�쳣����
		 Map<String,String> ids=new HashMap<String,String>();
		 ids.put("build_id", build_id);
		 ids.put("user_id", user_id);
		 List<Room> rooms=roomDao.fdRomByUsIdForRds(ids);
		 //����deviceID��redis��ȡ�������豸��Ϣ
		 for(Room room:rooms) {
			
			  ValueOperations<String, Device> list = redisTemp.opsForValue();
			  Device device1=list.get(room.getDeviceID());
		      room.setDevice(device1);
		 }
		 result.setStatus(rooms.size());
		 result.setMsg("��ѯ�ɹ�");
		 result.setData(rooms);
		 
		return result;
	}
	@Override
	public MinitorResult<Object> addRoom(
			       String user_id, String room_id) {
		//�������
	    if(user_id==null||"".equals(user_id)
	    		  ||room_id==null||"".equals(room_id)) {
	    	return null;
	    }
	    MinitorResult<Object> result=new MinitorResult<Object>();
//		//�ȸ���device_id,��hdu_monitor_build_room�в�ѯ
//		 Room room=roomDao.findRoomByDId(device_id);
//		 //����
//		 if(room!=null) {
//		  //�Ƿ��Ӧbuild_id
//			  if(build_id.equals(room.getBuildID())) {
//		      //��
//		         //�Ƿ��Ѿ�Ȩ�� �� user_id��room_id��Ӧ
//				  	String room_id=room.getRoomID();
//				    UserRoom userRoom=userRoomDao.findURByRId(room_id);
//				    	if(userRoom!=null&&userRoom.getUser_id().equals(user_id)) {
//				    		//��ֱ�ӷ���
//				    		result.setStatus(0);
//				    	    result.setMsg("���豸��Ӧ����"+room.getRoomName()
//				    	    	+",�����й���Ȩ��;�������ظ����,����ķ���������Ч");
//				    	    return result;
//				    	}else {
//				    		//�� save
//				    		Map<String,String>ids=new 
//				    				HashMap<String,String>();
//				    		ids.put("user_id", user_id);
//				    		ids.put("room_id", room_id);
//				    		if(1==userRoomDao.save(ids)) {
//				    			result.setStatus(1);
//				    			result.setMsg("��hdu_monitor_role_room����ɹ�");
//				    		}
//				    		return result;
//				    		
//				    	}
//			  }else {
//		     //�� 
//		         //���ش���
//				  result.setStatus(-1);
//				  result.setMsg("���豸ID��������¥��ʹ�ã������ڸ�¥��,������Ч�������豸ID");
//				  return result;
//			  }
//		 }else {
//			//������
//			 //�Ľ� �쳣����
//			
//		   //insert hdu_monitor_build_room��hdu_user_role_room
//			Room newRoom=new Room();
//			
//			//try catch
//			String newRoomId=MinitorUtil.createId();
//			newRoom.setRoomID(newRoomId);
//			newRoom.setBuildID(build_id);
//			newRoom.setDeviceID(device_id);
//			newRoom.setRoomName(room_name);
//			roomDao.save(newRoom);
//			Map<String,String>ids=new HashMap<String,String>();
//			ids.put("user_id", user_id);
//			ids.put("room_id",newRoomId);
//		    userRoomDao.save(ids);
//		    
//		    result.setStatus(3);
//			result.setMsg("���豸��ӳɹ�");
//			return result;
//			
//		 }
	    //ֱ����hdu_user_role_room�в���
	    
	    try {
	    	Map<String,String> ids=new HashMap<String,String>();
	    	ids.put("user_id",user_id);
	    	ids.put("room_id",room_id);
	    	userRoomDao.save(ids);
	    	result.setMsg("��ӷ���Ȩ�޳ɹ�");
	    	result.setStatus(0);
	    	
	    }catch(Exception e) {
	    	result.setMsg("���ʧ�ܣ��Ժ�����");
	    	result.setStatus(-1);
	    }
	    return  result;
	}
	@Override
	public MinitorResult<?> loadOtherRooms(String user_id, String build_id) {
		MinitorResult<Object> result=new MinitorResult<Object>();
		try {
			Map<String,String> ids=new HashMap<String,String>();
			ids.put("user_id", user_id);
			ids.put("build_id", build_id);
			List<Room> rooms=roomDao.findNotBelongUBRm(ids);
			result.setStatus(0);
			result.setMsg("��ӷ���Ȩ�޳ɹ�");
			result.setData(rooms);
			
		}catch(Exception e) {
			result.setStatus(-1);
			result.setMsg("���ݿ��쳣");
		}
		return result;
	}
	@Override
	public MinitorResult<?> deleteRoom(String user_id, String room_id) {
		  MinitorResult<Object> result=new MinitorResult<Object>();
		  try {
			  Map<String,String> ids=new HashMap<String,String>();
			  ids.put("user_id", user_id);
			  ids.put("room_id", room_id);
			  userRoomDao.delete(ids);
			  result.setStatus(0);
			  result.setMsg("ɾ���ɹ�");
		  }catch(Exception e) {
			  result.setStatus(-1);
			  result.setMsg("���������쳣��ɾ��ʧ��");
		  }
		return result;
	}

}
