package cn.hdu.HDU_Minitor.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.hdu.HDU_Minitor.service.BuildService;
import cn.hdu.HDU_Minitor.util.MinitorResult;

@Controller
@RequestMapping("/build")
public class LoadBuildController {
	@Resource(name="buildService")
	private BuildService buildService;
	
	@RequestMapping("/loadbuild.do")
	@ResponseBody
	public  MinitorResult<Object> execute(String user_id) {
		
		
		return buildService.loadBuild(user_id);
		
	}
}
