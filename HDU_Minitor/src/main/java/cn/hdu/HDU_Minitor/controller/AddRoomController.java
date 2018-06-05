package cn.hdu.HDU_Minitor.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.hdu.HDU_Minitor.service.RoomService;
import cn.hdu.HDU_Minitor.util.MinitorResult;

@Controller
@RequestMapping("/room")
public class AddRoomController {
	
	@Resource(name="roomService")
	private RoomService roomSerivce;
	@RequestMapping("/addroom.do")
	@ResponseBody
	public  MinitorResult<Object> execute(String user_id,String build_id,
											String room_name,String device_id){
		System.out.println("user_id:"+user_id+",build_id:"+build_id+
							",room_name:"+room_name+", device_id:"+
								device_id);
		return roomSerivce.addRoom(user_id, build_id, room_name, device_id);
	}

}
