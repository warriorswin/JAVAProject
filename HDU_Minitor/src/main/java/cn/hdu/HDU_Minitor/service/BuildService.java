package cn.hdu.HDU_Minitor.service;

import cn.hdu.HDU_Minitor.entity.Build;
import cn.hdu.HDU_Minitor.util.MinitorResult;

public interface BuildService {
	/**
	 * 根据用户user_id,得到用户信息，以及与之关联的builds
	 * @param user_id
	 * @return
	 */
	public MinitorResult<Object> loadBuild(String user_id);
	
	
	
	
	/*
	 * 增加用户管理楼栋的权限，如果楼栋不存在则添加该该楼栋到数据库
	 */
	public MinitorResult<Object> addBuild(String user_id,
												String build_num,
												String build_name);
	/*
	 * 移除该用户build,只操作了hdu_user_role_build,
	 * 以及build的该user的管理的room关系，即操作hdu_user_role_room
	 */
	public MinitorResult<Object> removeBuild(String user_id,
													String build_id);
}
