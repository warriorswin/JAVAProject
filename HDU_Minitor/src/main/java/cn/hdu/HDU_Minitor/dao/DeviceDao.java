package cn.hdu.HDU_Minitor.dao;

import java.util.List;

import cn.hdu.HDU_Minitor.entity.Device;

public interface DeviceDao {
	//���ϴ���Ϣ����
	public int save(Device device);
	//�����豸id��ѯdevice��Ϣ
	public List<Device> findByDId(String device_id);

}
