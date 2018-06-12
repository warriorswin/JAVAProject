package cn.hdu.HDU_Minitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.hdu.HDU_Minitor.entity.Build;
import cn.hdu.HDU_Minitor.entity.Room;
import cn.hdu.HDU_Minitor.service.ManageService;
import cn.hdu.HDU_Minitor.service.ex.DeleteException;
import cn.hdu.HDU_Minitor.util.MinitorResult;

@Controller
@RequestMapping("/manage")
public class ManageController {

	@Autowired
	@Qualifier("manageService")
	ManageService manageService;
	
	/**
	 * ���¥���controller��
	 * @param build ¥����Ϣ
	 * @return ����Ƿ�ɹ��Ľ��
	 */
	@RequestMapping(value="/build",method=RequestMethod.POST,
			consumes="application/json",
			produces="application/json;charset=utf-8"
			)
	@ResponseBody
	public MinitorResult<?> addBuild(@RequestBody Build build) {
		return manageService.addBuild(build);
	}
	
	/**
	 * ��ѯ¥����Ϣ��controller��
	 * @param buildNumber ¥����
	 * @return ¥����Ϣ
	 */
	@RequestMapping(value="/build/{type}",method=RequestMethod.GET,
			produces="application/json;charset=utf-8"
			)
	@ResponseBody
	public MinitorResult<?> updateBuild(@PathVariable("type") String buildNumber){
		if("ALL".equals(buildNumber)) {
			return manageService.findAllBuilds();
		}else {
			return manageService.findBuildByNumber(buildNumber);
		}
	}
	
	/**
	 * ��ѯ����¥����Ϣ��controller��
	 * @param build ¥����Ϣ
	 * @return �����Ƿ�ɹ�
	 */
	@RequestMapping(value="/build",method=RequestMethod.PUT,
			consumes="application/json",
			produces="application/json;charset=utf-8"
			)
	@ResponseBody
	public MinitorResult<?> updateBuild(@RequestBody Build build) {
		
		return manageService.updateBuild(build);
		
	}
	/**
	 * ͨ��buildIDɾ��build����Ϣ��controller��
	 * @param buildID 
	 * @return ɾ���Ƿ�ɹ�����Ϣ
	 */
	@RequestMapping(value="/build/{deleteBuildID}",method = RequestMethod.DELETE)
	@ResponseBody
	public MinitorResult<?> deleteBuild(@PathVariable("deleteBuildID") String buildID){
		MinitorResult<?> result = new MinitorResult<>();
		try {
			result = manageService.deleteBuild(buildID);
			return result;
		} catch (DeleteException e) {
			result.setStatus(0);
			result.setMsg(e.getMessage());
			return result;
		}
	}
	
	/**
	 * ��ӷ����controller��
	 * @param room ������Ϣ
	 * @return �����Ƿ���ӳɹ�����Ϣ
	 */
	@RequestMapping(value="/room",method=RequestMethod.POST)
	@ResponseBody
	public MinitorResult<?> addRoom(@RequestBody Room room){
		System.err.println(room);
		MinitorResult<?> result = new MinitorResult<>();
		try {
			return manageService.addRoom(room);
		} catch (Exception e) {
			result.setStatus(0);
			result.setMsg(e.getMessage());
			return result;
		}
	}
	
	/**
	 * ͨ��roomID�����豸id��ͨ��buildID�������з�����Ϣ
	 * @param type roomID��ALL
	 * @param buildID ¥��id
	 * @return �����Ƿ�ɹ���Ϣ
	 */
	@RequestMapping(value="/room/{type}/{buildID}",method=RequestMethod.GET)
	@ResponseBody
	public MinitorResult<?> findRoom(
			@PathVariable("type") String type,
			@PathVariable(value="buildID",required=false) String buildID
			) {
		System.err.println(type);
		if("ALL".equals(type)) {
			return manageService.findAllRoomByBuild(buildID);
		}else {
			return manageService.findDeviceIDbyRoomID(type);
		}
	}
	
	/**
	 * �޸ķ�����Ϣ��controller��
	 * @param room �µķ�����Ϣ
	 * @return �����Ƿ�ɹ�����Ϣ
	 */
	@RequestMapping(value="/room",method=RequestMethod.PUT)
	@ResponseBody
	public MinitorResult<?> updateRoom(@RequestBody Room room){
		
		System.err.println(room);
		MinitorResult<?> result = new MinitorResult<>();
		try {
			return manageService.updateRoom(room);
		} catch (Exception e) {
			result.setStatus(0);
			result.setMsg(e.getMessage());
			return result;
		}
	}
	@RequestMapping(value="/room/{roomID}",method=RequestMethod.DELETE)
	@ResponseBody
	public MinitorResult<?> deleteRoom(@PathVariable("roomID") String roomID){
		System.err.println(roomID);
		MinitorResult<Void> result = new MinitorResult<>();
		try {
			return manageService.deleteRoom(roomID);
		} catch (DeleteException e) {
			result.setStatus(0);
			result.setMsg(e.getMessage());
			return result;
		}
		
	}
	
	
}
