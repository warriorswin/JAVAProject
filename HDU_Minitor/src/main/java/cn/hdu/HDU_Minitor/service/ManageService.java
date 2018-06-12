package cn.hdu.HDU_Minitor.service;

import java.util.List;

import cn.hdu.HDU_Minitor.entity.Build;
import cn.hdu.HDU_Minitor.entity.Room;
import cn.hdu.HDU_Minitor.util.MinitorResult;

public interface ManageService {

	/**
	 * 管理楼层部分
	 * 添加楼层
	 * @param build 楼层的信息
	 * @return 添加是否成功的状态信息
	 */
	public MinitorResult<Void> addBuild(Build build);
	
	/**
	 * 管理楼层部分
	 * 查询楼层所用信息
	 * @return 查询是否成功以及楼层的信息
	 */
	public MinitorResult<List<Build>> findAllBuilds();
	
	/**
	 * 管理楼层部分
	 * 通过楼层编号查询楼层信息
	 * @param buildNumber 楼层编号
	 * @return 查询是否成功以及楼层的信息
	 */
	public MinitorResult<Build> findBuildByNumber(String buildNumber);
	
	/**
	 * 管理楼层部分
	 * 更新楼层信息
	 * @param build 新的楼层信息
	 * @return 更新是否成功
	 */
	public MinitorResult<Void> updateBuild(Build build);
	
	/**
	 * 管理楼层部分
	 * 通过buildID删除楼层信息
	 * @param build
	 * @return
	 */
	public MinitorResult<Void> deleteBuild(String buildID);
	
	/**
	 * 管理楼层部分
	 * 添加房间信息
	 * @param room 房间信息
	 * @return 添加是否成功的信息
	 */
	public MinitorResult<?> addRoom(Room room);
	
	/**
	 * 管理楼层部分
	 * 根据楼层ID查找所有的房间信息
	 * @param buildID 楼层ID
	 * @return 查找是否成功的信息
	 */
	public MinitorResult<?> findAllRoomByBuild(String buildID);
	
	/**
	 * 管理楼层部分
	 * 根据房间ID查找设备ID
	 * @param roomID 房间ID
	 * @return 设备ID
	 */
	public MinitorResult<?> findDeviceIDbyRoomID(String roomID);
	
	/**
	 * 管理部分
	 * 更新房间信息
	 * @param room 最新的房间信息
	 * @return 更新是否成功的信息
	 */
	public MinitorResult<?> updateRoom(Room room);
	
	/**
	 * 管理部分
	 * 通过房间ID删除房间信息
	 * @param roomID 房间ID 
	 * @return 删除是否成功的信息
	 */
	public MinitorResult<?> deleteRoom(String roomID);
	
}
