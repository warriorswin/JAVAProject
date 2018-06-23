package cn.hdu.HDU_Minitor.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.hdu.HDU_Minitor.service.DeviceService;
import cn.hdu.HDU_Minitor.util.MinitorResult;

@Controller
@RequestMapping("/device")
public class LoadDeviceDatasController {
	@Resource(name="deviceService")
	public DeviceService deviceService;
	@RequestMapping("/loadhistorydata.do")
	@ResponseBody
	public MinitorResult<?> loadHistoryData(String device_id){
		return deviceService.loadDvceHistyData(device_id);
		
	}
	@RequestMapping("/loadlastdata.do")
	@ResponseBody
	public MinitorResult<?> loadLastData(String device_id){
		return deviceService.loadLastDvcDataFmRds(device_id);
	}
	

}
