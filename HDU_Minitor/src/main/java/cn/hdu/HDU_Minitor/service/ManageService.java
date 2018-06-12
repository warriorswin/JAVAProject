package cn.hdu.HDU_Minitor.service;

import java.util.List;

import cn.hdu.HDU_Minitor.entity.Build;
import cn.hdu.HDU_Minitor.entity.Room;
import cn.hdu.HDU_Minitor.util.MinitorResult;

public interface ManageService {

	/**
	 * ����¥�㲿��
	 * ���¥��
	 * @param build ¥�����Ϣ
	 * @return ����Ƿ�ɹ���״̬��Ϣ
	 */
	public MinitorResult<Void> addBuild(Build build);
	
	/**
	 * ����¥�㲿��
	 * ��ѯ¥��������Ϣ
	 * @return ��ѯ�Ƿ�ɹ��Լ�¥�����Ϣ
	 */
	public MinitorResult<List<Build>> findAllBuilds();
	
	/**
	 * ����¥�㲿��
	 * ͨ��¥���Ų�ѯ¥����Ϣ
	 * @param buildNumber ¥����
	 * @return ��ѯ�Ƿ�ɹ��Լ�¥�����Ϣ
	 */
	public MinitorResult<Build> findBuildByNumber(String buildNumber);
	
	/**
	 * ����¥�㲿��
	 * ����¥����Ϣ
	 * @param build �µ�¥����Ϣ
	 * @return �����Ƿ�ɹ�
	 */
	public MinitorResult<Void> updateBuild(Build build);
	
	/**
	 * ����¥�㲿��
	 * ͨ��buildIDɾ��¥����Ϣ
	 * @param build
	 * @return
	 */
	public MinitorResult<Void> deleteBuild(String buildID);
	
	/**
	 * ����¥�㲿��
	 * ��ӷ�����Ϣ
	 * @param room ������Ϣ
	 * @return ����Ƿ�ɹ�����Ϣ
	 */
	public MinitorResult<?> addRoom(Room room);
	
	/**
	 * ����¥�㲿��
	 * ����¥��ID�������еķ�����Ϣ
	 * @param buildID ¥��ID
	 * @return �����Ƿ�ɹ�����Ϣ
	 */
	public MinitorResult<?> findAllRoomByBuild(String buildID);
	
	/**
	 * ����¥�㲿��
	 * ���ݷ���ID�����豸ID
	 * @param roomID ����ID
	 * @return �豸ID
	 */
	public MinitorResult<?> findDeviceIDbyRoomID(String roomID);
	
	/**
	 * ������
	 * ���·�����Ϣ
	 * @param room ���µķ�����Ϣ
	 * @return �����Ƿ�ɹ�����Ϣ
	 */
	public MinitorResult<?> updateRoom(Room room);
	
	/**
	 * ������
	 * ͨ������IDɾ��������Ϣ
	 * @param roomID ����ID 
	 * @return ɾ���Ƿ�ɹ�����Ϣ
	 */
	public MinitorResult<?> deleteRoom(String roomID);
	
}
