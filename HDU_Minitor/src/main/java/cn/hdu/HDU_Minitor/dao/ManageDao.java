package cn.hdu.HDU_Minitor.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.hdu.HDU_Minitor.entity.Build;
import cn.hdu.HDU_Minitor.entity.Room;

public interface ManageDao {

	/**
	 * ������
	 * ���¥��
	 * @param build ¥����Ϣ
	 * @return �ɹ���ӵ���Ŀ
	 */
	public Integer addBuild(Build build);
	
	/**
	 * ������
	 * ��ѯ����¥��
	 * @return ����¥����Ϣ
	 */
	public List<Build> findAllBuilds();
	
	/**
	 * ������
	 * ����¥���Ų�ѯ¥����Ϣ
	 * @param buildNumber ¥����
	 * @return ¥�����Ϣ
	 */
	public Build findBuildByNumber(String buildNumber);
	
	/**
	 * ������
	 * ����¥��ID��ѯ¥����Ϣ
	 * @param buildID ¥��ID
	 * @return ¥����Ϣ
	 */
	public Build findBuildByID(String buildID);
	
	/**
	 * ����¥����Ϣ
	 * @param build ¥���������Ϣ
	 * @return ���ĵĸ���
	 */
	public Integer updateBuildInfo(Build build);
	
	/**
	 * ����¥��idɾ��¥����Ϣ
	 * @param buildNumber ¥��id
	 * @return ɾ���ĸ���
	 */
	public Integer deleteBuild(String buildID);
	
	/**
	 * ����¥��ID�ͷ������������ѯ�豸ID
	 * @param buildID ¥�� ID
	 * @param roomName ��������
	 * @return �豸ID
	 */
	public String findRoombyBuild(@Param("buildID") String buildID,@Param("roomName") String roomName);
	
	/**
	 * ���ݷ���ID��ѯ�豸��ID
	 * @param roomID ����ID
	 * @return �豸ID
	 */
	public String findDeviceIDbyRoomID(String roomID);
	
	/**
	 * ��Ϣ���µ�room��Ϣ
	 * @param room room��Ϣ
	 * @return ���³ɹ��ĸ���
	 */
	public Integer updateRoomByRoomID(Room room);
	
	/**
	 * ����roomIDɾ��room��Ϣ
	 * @param roomID ����ID
	 * @return ɾ���ĸ���
	 */
	public Integer deleteRoomByID(String roomID);
	 
}
