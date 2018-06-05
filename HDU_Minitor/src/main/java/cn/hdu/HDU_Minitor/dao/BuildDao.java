package cn.hdu.HDU_Minitor.dao;

import cn.hdu.HDU_Minitor.entity.Build;

public interface BuildDao {

	public Build findBuildByNum(String build_num);
	public int save(Build build);
}
