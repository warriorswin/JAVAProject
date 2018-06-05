package cn.hdu.HDU_Minitor.controller.device;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/device")
//设备请求传输路径
public class DeviceUploadInfoController {
	
	@RequestMapping("/save.do")
	public int execute() {
		return 0;
	}
  
}
