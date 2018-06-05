package cn.hdu.HDU_Minitor.dao;

import java.util.List;
import java.util.Map;

import cn.hdu.HDU_Minitor.entity.Device;
import cn.hdu.HDU_Minitor.entity.Room;

public interface RoomDao {
	public Device findDeviceByDeId(String devcie_id);
	//查询该用户的该楼栋的房间及设备信息
	public List<Room> findRoomByBuUsId(Map<String,String> ids);
	//查询该楼栋的所有房间，及设备信息
    public List<Room> findRoomByBuildId(String build_id);
    //查询device_id查询room信息
    public Room  findRoomByDId(String device_id);
    
    //该楼栋插入新的房间及设备信息
    public int save(Room room);
}
