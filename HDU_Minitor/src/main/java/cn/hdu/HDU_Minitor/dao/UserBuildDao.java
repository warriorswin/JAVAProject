package cn.hdu.HDU_Minitor.dao;

import java.util.List;
import java.util.Map;

import cn.hdu.HDU_Minitor.entity.UserBuild;

public interface UserBuildDao {
	/*
	 * ����user �� build֮��Ĺ�ϵ
	 */
	public int save(Map<String,String> id);
	
	/*
	 * ����user_id �������Ӧ��build_id
	 */
	public List<UserBuild>findByUserId(String user_id);
	
	/*
	 * ����user_idɾ��������Ϣ
	 */
	public Integer deleteByUId(String user_id);
	
	/*
	 * ɾ��user build��Ϣ
	 */
	public Integer delete(Map ids);
}
