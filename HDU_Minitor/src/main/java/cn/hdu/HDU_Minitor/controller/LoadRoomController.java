package cn.hdu.HDU_Minitor.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.hdu.HDU_Minitor.service.RoomService;
import cn.hdu.HDU_Minitor.util.MinitorResult;

@Controller
@RequestMapping("/room")
public class LoadRoomController {
	@Resource(name="roomService")
	private RoomService roomService;
	
	@RequestMapping("loadroom.do")
	@ResponseBody
	public MinitorResult<Object>excute(String build_id,String user_id){
		System.out.println("user_id:"+user_id+":"+"build_id:"+build_id);
		return roomService.findUsersRoomOfBuild(build_id,user_id);
	}
}
