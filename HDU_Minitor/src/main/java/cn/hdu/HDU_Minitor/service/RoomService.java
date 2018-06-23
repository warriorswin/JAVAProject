package cn.hdu.HDU_Minitor.service;

import cn.hdu.HDU_Minitor.util.MinitorResult;

public interface RoomService {
	public MinitorResult<Object> findUsersRoomOfBuild(String build_id, String user_id);
	public MinitorResult<Object> addRoom(String user_id,String room_id);
											
	
	public MinitorResult<?> loadOtherRooms(String user_id,String build_id);
	
	//ɾ��user_id&&room_id��Ӧ�Ĺ�ϵ
	public MinitorResult<?>deleteRoom(String user_id,String room_id);

}
