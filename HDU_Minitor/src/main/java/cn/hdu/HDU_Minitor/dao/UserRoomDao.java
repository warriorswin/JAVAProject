package cn.hdu.HDU_Minitor.dao;

import java.util.Map;

import cn.hdu.HDU_Minitor.entity.UserRoom;

public interface UserRoomDao {
	//����room��Ӧ��user
	public UserRoom findURByRId(String room_id);
	//�����µ�����
	public int save(Map ids);
	//����user_id ɾ����������
	public int deleteByUId(String user_id);
	//ɾ����������
	public int delete(Map ids);
}
