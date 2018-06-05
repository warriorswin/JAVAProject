package cn.hdu.HDU_Minitor.dao;

import java.util.List;
import java.util.Map;

import cn.hdu.HDU_Minitor.entity.UserBuild;

public interface UserBuildDao {
	/*
	 * 插入user 和 build之间的关系
	 */
	public int save(Map<String,String> id);
	
	/*
	 * 根据user_id 查找其对应的build_id
	 */
	public List<UserBuild>findByUserId(String user_id);
	
	/*
	 * 根据user_id删除该条信息
	 */
	public Integer deleteByUId(String user_id);
	
	/*
	 * 删除user build信息
	 */
	public Integer delete(Map ids);
}
