package cn.hdu.HDU_Minitor.dao;

import java.util.List;

import cn.hdu.HDU_Minitor.entity.Device;

public interface DeviceDao {
	//将上传信息插入
	public int save(Device device);
	//根据设备id查询device信息
	public List<Device> findByDId(String device_id);

}
