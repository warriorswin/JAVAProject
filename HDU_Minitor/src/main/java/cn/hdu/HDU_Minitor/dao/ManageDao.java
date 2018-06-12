package cn.hdu.HDU_Minitor.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.hdu.HDU_Minitor.entity.Build;
import cn.hdu.HDU_Minitor.entity.Room;

public interface ManageDao {

	/**
	 * 管理部分
	 * 添加楼层
	 * @param build 楼层信息
	 * @return 成功添加的数目
	 */
	public Integer addBuild(Build build);
	
	/**
	 * 管理部分
	 * 查询所有楼层
	 * @return 所有楼层信息
	 */
	public List<Build> findAllBuilds();
	
	/**
	 * 管理部分
	 * 根据楼层编号查询楼层信息
	 * @param buildNumber 楼层编号
	 * @return 楼层的信息
	 */
	public Build findBuildByNumber(String buildNumber);
	
	/**
	 * 管理部分
	 * 根据楼层ID查询楼层信息
	 * @param buildID 楼层ID
	 * @return 楼层信息
	 */
	public Build findBuildByID(String buildID);
	
	/**
	 * 更新楼层信息
	 * @param build 楼层的最新信息
	 * @return 更改的个数
	 */
	public Integer updateBuildInfo(Build build);
	
	/**
	 * 根据楼层id删除楼层信息
	 * @param buildNumber 楼层id
	 * @return 删除的个数
	 */
	public Integer deleteBuild(String buildID);
	
	/**
	 * 根据楼层ID和房间的名字来查询设备ID
	 * @param buildID 楼层 ID
	 * @param roomName 房间名字
	 * @return 设备ID
	 */
	public String findRoombyBuild(@Param("buildID") String buildID,@Param("roomName") String roomName);
	
	/**
	 * 根据房间ID查询设备的ID
	 * @param roomID 房间ID
	 * @return 设备ID
	 */
	public String findDeviceIDbyRoomID(String roomID);
	
	/**
	 * 信息最新的room信息
	 * @param room room信息
	 * @return 更新成功的个数
	 */
	public Integer updateRoomByRoomID(Room room);
	
	/**
	 * 依据roomID删除room信息
	 * @param roomID 房间ID
	 * @return 删除的个数
	 */
	public Integer deleteRoomByID(String roomID);
	 
}
