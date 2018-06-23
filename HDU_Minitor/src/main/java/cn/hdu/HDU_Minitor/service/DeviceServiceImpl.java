package cn.hdu.HDU_Minitor.service;

import java.text.DecimalFormat;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.databind.util.JSONPObject;

import cn.hdu.HDU_Minitor.dao.DeviceDao;
import cn.hdu.HDU_Minitor.entity.Device;
import cn.hdu.HDU_Minitor.util.MinitorResult;
import net.sf.json.JSONObject;

/*
 * ��mqtt.xml����spring-����
 */
public class DeviceServiceImpl implements DeviceService {
	
	@Resource(name="deviceDao")
	private DeviceDao deviceDao;
	@Resource(name="redisTemplate")
	private RedisTemplate<String, Device> redisTemp;
	public void saveDeviceInfo(String message) {
		//����sjon�ַ���
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
		//�������ݿ�
		if(1!=deviceDao.save(device)) {
			System.out.println("error");
		};
		//����
		ValueOperations<String, Device> list = redisTemp.opsForValue();
		list.set(device.getDevice_id(), device);
		
		
		
	
		
	}
	@Override
	public MinitorResult<?> loadDvceHistyData(String device_id) {
			MinitorResult<Object> result=new MinitorResult<Object>();
			try {
				List<Device> devcDatas=deviceDao.findByDId(device_id);
				result.setStatus(0);
				result.setMsg("��ѯ�ɹ�");
				result.setData(devcDatas);
			}catch(Exception e){
				result.setStatus(-1);
				result.setMsg("��ѯ�쳣,���Ժ�����");
				
			}
		return result;
	}
	@Override
	public MinitorResult<?> loadLastDvcDataFmRds(String device_id) {
		MinitorResult<Device> result=new MinitorResult<Device>();
		try {
			ValueOperations<String, Device> list = redisTemp.opsForValue();
			Device device=list.get(device_id);
			result.setStatus(0);
			result.setMsg("��ѯ�ɹ�");
			result.setData(device);
		}catch(Exception e) {
			result.setStatus(-1);
			result.setMsg("Redis��ѯʧ��");	
		}
		return  result;
	}

}
