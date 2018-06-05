package cn.hdu.HDU_Minitor.dao;

import java.util.Map;

import cn.hdu.HDU_Minitor.entity.UserRoom;

public interface UserRoomDao {
	//查找room对应的user
	public UserRoom findURByRId(String room_id);
	//插入新的数据
	public int save(Map ids);
	//根据user_id 删除该条数据
	public int deleteByUId(String user_id);
	//删除该条数据
	public int delete(Map ids);
}
