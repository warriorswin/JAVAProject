package cn.hdu.HDU_Minitor.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.hdu.HDU_Minitor.service.RoomService;
import cn.hdu.HDU_Minitor.util.MinitorResult;

@Controller
@RequestMapping("/room")
public class DeleteRmURlatshpController {
	@Resource(name="roomService")
	private RoomService roomService;
	@RequestMapping("/deleteRURelshp")
	@ResponseBody
	public MinitorResult<?> excute(String user_id,String room_id) {
		return  roomService.deleteRoom(user_id, room_id);
	}
	
	

}
