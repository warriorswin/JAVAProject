package cn.hdu.HDU_Minitor.dao;

import java.util.List;

import cn.hdu.HDU_Minitor.entity.Build;

public interface BuildDao {

	public Build findBuildByNum(String build_num);
	public int save(Build build);
	//��ѯ�����ڸ��û���build
	public List<Build> findNotBelongUser(String user_id);

}
