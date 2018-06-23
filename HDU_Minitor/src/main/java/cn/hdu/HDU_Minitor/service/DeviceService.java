package cn.hdu.HDU_Minitor.service;

import cn.hdu.HDU_Minitor.util.MinitorResult;

public interface DeviceService {
	public MinitorResult<?> loadDvceHistyData(String device_id);
	public MinitorResult<?> loadLastDvcDataFmRds(String device_id);
	
}
