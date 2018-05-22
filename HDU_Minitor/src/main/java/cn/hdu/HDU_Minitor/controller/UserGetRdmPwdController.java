package cn.hdu.HDU_Minitor.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.hdu.HDU_Minitor.service.UserService;
import cn.hdu.HDU_Minitor.util.MinitorResult;

/*
 * »ñÈ¡Ëæ»úÃÜÂë
 */
@Controller
@RequestMapping("/user")
public class UserGetRdmPwdController {
	
	@Resource(name="userService")
	private UserService service;
	@RequestMapping("/getrdmpwd.do")
	@ResponseBody
	public MinitorResult<Object> execute(String userPhone){
		return service.RdmPwdGenertor(userPhone);
	}

}
