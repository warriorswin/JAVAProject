package cn.hdu.HDU_Minitor.service;

import cn.hdu.HDU_Minitor.entity.Build;
import cn.hdu.HDU_Minitor.util.MinitorResult;

public interface BuildService {
	/**
	 * �����û�user_id,�õ��û���Ϣ���Լ���֮������builds
	 * @param user_id
	 * @return
	 */
	public MinitorResult<Object> loadBuild(String user_id);
	
	
	
	
	/*
	 * �����û�����¥����Ȩ�ޣ����¥������������Ӹø�¥�������ݿ�
	 */
	public MinitorResult<Object> addBuild(String user_id,
												String build_num,
												String build_name);
	/*
	 * �Ƴ����û�build,ֻ������hdu_user_role_build,
	 * �Լ�build�ĸ�user�Ĺ����room��ϵ��������hdu_user_role_room
	 */
	public MinitorResult<Object> removeBuild(String user_id,
													String build_id);
}
