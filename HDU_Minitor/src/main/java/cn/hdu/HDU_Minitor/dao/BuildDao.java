package cn.hdu.HDU_Minitor.dao;

import java.util.List;

import cn.hdu.HDU_Minitor.entity.Build;

public interface BuildDao {

	public Build findBuildByNum(String build_num);
	public int save(Build build);
	//查询不属于该用户的build
	public List<Build> findNotBelongUser(String user_id);

}
