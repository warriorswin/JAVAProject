package cn.hdu.HDU_Minitor.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.hdu.HDU_Minitor.service.UserService;
import cn.hdu.HDU_Minitor.util.MinitorResult;

@Controller
@RequestMapping("/user")
public class DeleteUserController {
	@Resource(name="userService")
	private UserService userService;
	
	@RequestMapping("/deleteuser.do")
	@ResponseBody
	public MinitorResult<Object> execute(String user_id){
		
		return userService.deleteUser(user_id);
		
	}

}
