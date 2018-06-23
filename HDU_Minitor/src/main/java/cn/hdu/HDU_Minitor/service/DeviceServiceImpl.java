package cn.hdu.HDU_Minitor.service;

import java.text.DecimalFormat;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.databind.util.JSONPObject;

import cn.hdu.HDU_Minitor.dao.DeviceDao;
import cn.hdu.HDU_Minitor.entity.Device;
import net.sf.json.JSONObject;

public class DeviceService {
	
	@Resource(name="deviceDao")
	private DeviceDao deviceDao;
	public void saveDeviceInfo(String message) {
		//解析sjon字符串
		JSONObject obj=JSONObject.fromObject(message);
		JSONObject position=(JSONObject)obj.get("p");
		JSONObject current=(JSONObject)obj.get("i");
		JSONObject tpture=(JSONObject)obj.get("t");
		Device device=new Device();
		device.setDevice_id(obj.getString("id"));
		device.setDevice_ic(current.getString("ic"));
		device.setDevice_it(current.getString("it"));
		
		DecimalFormat df = new DecimalFormat( "0.00 ");
		Double tc=Double.parseDouble(tpture.getString("tc"))*0.0625;
		String tcStr=df.format(tc);
		device.setDevice_tc(tcStr);
		
		Double tt=Double.parseDouble(tpture.getString("tt"))*0.0625;
		String ttStr=df.format(tt);
		device.setDevice_tt(ttStr);
		
		
		device.setDevice_time(obj.getString("tm"));
		device.setDevice_J(position.getString("j"));
		device.setDevice_W(position.getString("w"));
		device.setDevice_status(obj.getString("s"));
		//存入数据库
		if(1!=deviceDao.save(device)) {
			System.out.println("error");
		};
		
		System.out.println(device);
		
	
		
	}

}
