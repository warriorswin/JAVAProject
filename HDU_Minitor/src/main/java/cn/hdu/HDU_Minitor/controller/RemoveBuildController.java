package cn.hdu.HDU_Minitor.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.hdu.HDU_Minitor.service.BuildService;
import cn.hdu.HDU_Minitor.util.MinitorResult;

@Controller
@RequestMapping("/build")
public class RemoveBuildController {
	
	@Resource(name="buildService")
	private BuildService buildService;
	@RequestMapping("/removebuild.do")
	@ResponseBody
	public MinitorResult<Object> execute(String user_id,String build_id) {
		return buildService.removeBuild(user_id, build_id);
	}
}
