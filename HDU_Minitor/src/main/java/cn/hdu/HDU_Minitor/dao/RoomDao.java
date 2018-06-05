package cn.hdu.HDU_Minitor.dao;

import java.util.List;
import java.util.Map;

import cn.hdu.HDU_Minitor.entity.Device;
import cn.hdu.HDU_Minitor.entity.Room;

public interface RoomDao {
	public Device findDeviceByDeId(String devcie_id);
	//��ѯ���û��ĸ�¥���ķ��估�豸��Ϣ
	public List<Room> findRoomByBuUsId(Map<String,String> ids);
	//��ѯ��¥�������з��䣬���豸��Ϣ
    public List<Room> findRoomByBuildId(String build_id);
    //��ѯdevice_id��ѯroom��Ϣ
    public Room  findRoomByDId(String device_id);
    
    //��¥�������µķ��估�豸��Ϣ
    public int save(Room room);
}
