package cn.hdu.HDU_Minitor.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.hdu.HDU_Minitor.service.UserService;
import cn.hdu.HDU_Minitor.util.MinitorResult;

@RequestMapping("/user")
@Controller
public class AddUserController {
	@Resource(name="userService")
	private UserService userService;
	
	@RequestMapping("/adduser.do")
	@ResponseBody
	public MinitorResult<Object>execute(String user_name,String user_phone){
		System.out.println("add:user_name:"
							+user_name+"user_phone:"+user_phone);
		return userService.insertUser(user_name, user_phone);
	}
}
