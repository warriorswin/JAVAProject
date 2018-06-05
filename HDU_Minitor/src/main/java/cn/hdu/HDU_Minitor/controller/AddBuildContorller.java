package cn.hdu.HDU_Minitor.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.hdu.HDU_Minitor.service.BuildService;
import cn.hdu.HDU_Minitor.util.MinitorResult;

@RequestMapping("/build")
@Controller
public class AddBuildContorller {
	
	@Resource(name="buildService")
	private BuildService buildService;
	
	@RequestMapping("/addbuild.do")
	@ResponseBody
	public MinitorResult<Object> excute(String user_id,
												String build_num,
												String build_name){
		System.out.println("user_id:"+user_id
				+ " build_num:"+build_num+" build_name:"+build_name);
		
		return buildService.addBuild(user_id, build_num, build_name);
	}
}
