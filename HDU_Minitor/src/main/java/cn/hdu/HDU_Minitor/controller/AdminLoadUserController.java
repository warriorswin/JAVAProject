package cn.hdu.HDU_Minitor.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import cn.hdu.HDU_Minitor.service.UserService;
import cn.hdu.HDU_Minitor.util.MinitorResult;

@Controller
@RequestMapping("/admin")
public class AdminLoadUserController {
	
	@Resource(name="userService")
	private UserService userService;
	
	@RequestMapping("/loaduser.do")
	@ResponseBody
	public MinitorResult<Object> execute(String user_id) {
		System.out.println("user_id:"+user_id);
		return userService.loadAllUsers(user_id);		
	}
}
