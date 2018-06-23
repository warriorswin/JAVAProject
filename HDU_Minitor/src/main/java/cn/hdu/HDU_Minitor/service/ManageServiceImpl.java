package cn.hdu.HDU_Minitor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.hdu.HDU_Minitor.dao.ManageDao;
import cn.hdu.HDU_Minitor.dao.RoomDao;
import cn.hdu.HDU_Minitor.entity.Build;
import cn.hdu.HDU_Minitor.entity.Room;
import cn.hdu.HDU_Minitor.service.ex.BindException;
import cn.hdu.HDU_Minitor.service.ex.DeleteException;
import cn.hdu.HDU_Minitor.service.ex.InsertAndUpdateException;
import cn.hdu.HDU_Minitor.util.MinitorResult;
import cn.hdu.HDU_Minitor.util.MinitorUtil;

@Service("manageService")
public class ManageServiceImpl implements ManageService {

	@Autowired
	@Qualifier("manageDao")
	ManageDao manageDao;
	@Autowired
	@Qualifier("roomDao")
	RoomDao roomDao;
	
	@Override
	public MinitorResult<Void> addBuild(Build build) {
		MinitorResult<Void> result = new MinitorResult<>();
		String buildID = MinitorUtil.createId();
		build.setBuildID(buildID);
		try {
			manageDao.addBuild(build);
			result.setStatus(1);
			result.setMsg("添加楼层成功！");
			return result;
		} catch (Exception e) {
			result.setStatus(0);
			result.setMsg("添加楼层失败，请检查楼标号和楼名字是否重复");
			return result;
		}
	}

	@Override
	public MinitorResult<List<Build>> findAllBuilds() {
		MinitorResult<List<Build>> result = new MinitorResult<>();
		try {
			List<Build> builds = manageDao.findAllBuilds();
			result.setStatus(1);
			result.setMsg("查询成功");
			result.setData(builds);
			return result;
		} catch (Exception e) {
			result.setStatus(0);
			result.setMsg("系统繁忙请稍后再试！");
			return result;
		}
	}

	@Override
	public MinitorResult<Build> findBuildByNumber(String buildNumber) {
		MinitorResult<Build> result = new MinitorResult<>();
		try {
			Build build = manageDao.findBuildByNumber(buildNumber);
			if(build.getBuildID()==null) {
				result.setStatus(0);
				result.setMsg("查无此人！");
				return result;
			}
			result.setStatus(1);
			result.setMsg("查询成功！");
			result.setData(build);
			return result;
		} catch (Exception e) {
			result.setStatus(0);
			result.setMsg("系统繁忙稍后再试！");
			return result;
		}
	}

	@Override
	public MinitorResult<Void> updateBuild(Build build) {
		MinitorResult<Void> result = new MinitorResult<>();
		try {
			Integer num = manageDao.updateBuildInfo(build);
			if(num==0) {
				result.setStatus(0);
				result.setMsg("此楼层已不存在！");
				return result;
			}
			result.setStatus(1);
			result.setMsg("更新成功！");
			return result;
		} catch (Exception e) {
			result.setStatus(0);
			result.setMsg("系统繁忙稍后再试！");
			return result;
		}
	}

	@Transactional
	@Override
	public MinitorResult<Void> deleteBuild(String buildID) {
		MinitorResult<Void> result = new MinitorResult<>();
		try {
			manageDao.deleteBuild(buildID);
			result.setStatus(1);
			result.setMsg("删除成功！");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DeleteException("系统繁忙请稍后再试！", e);
		}
	}

	@Transactional
	@Override
	public MinitorResult<?> addRoom(Room room) {
		MinitorResult<? super Room> result = new MinitorResult<>();
		Room oldRoom=null;
		try {
			String buildID = room.getBuildID();
			String roomName = room.getRoomName();
			String oldDeviceID = manageDao.findRoombyBuild(buildID, roomName);
			if(oldDeviceID!=null) {
				oldRoom = room;
				Build build = manageDao.findBuildByID(buildID);
				oldRoom.setBuild(build);
				oldRoom.setDeviceID(oldDeviceID);
				throw new BindException("该楼层已有该房间");
			}
			String deviceID = room.getDeviceID();
			oldRoom = roomDao.findRoomByDId(deviceID);
			if(oldRoom!=null&&oldRoom.getRoomID()!=null) {
				String oldBuildID = oldRoom.getBuildID();
				Build build = manageDao.findBuildByID(oldBuildID);
				oldRoom.setBuild(build);
				throw new BindException("该设备已经与其他房间绑定了");
			}
			
			String uuid = MinitorUtil.createId();
			room.setRoomID(uuid);
			roomDao.save(room);
			result.setStatus(1);
			result.setMsg("添加成功！");
			return result;
		} catch (BindException e) {
			result.setStatus(2);
			result.setMsg(e.getMessage());
			result.setData(oldRoom);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new InsertAndUpdateException("系统繁忙请稍后再试！",e);
		}
	}

	@Override
	public MinitorResult<?> findAllRoomByBuild(String buildID) {
		MinitorResult<List<Room>> result = new MinitorResult<>();
		try {
			List<Room> rooms = roomDao.findRoomByBuildId(buildID);
			result.setStatus(1);
			result.setMsg("查询成功");
			result.setData(rooms);
			System.err.println(result);
			return result;
		} catch (Exception e) {
			System.err.println("error");
			e.printStackTrace();
			result.setStatus(0);
			result.setMsg("系统繁忙请稍后再试！");
			return result;
		}
	}

	@Override
	public MinitorResult<?> findDeviceIDbyRoomID(String roomID) {
		MinitorResult<String> result = new MinitorResult<>();
		try {
			String deviceID = manageDao.findDeviceIDbyRoomID(roomID);
			result.setStatus(1);
			result.setMsg("查找成功！");
			result.setData(deviceID);
			return result;
		} catch (Exception e) {
			result.setStatus(0);
			result.setMsg("系统繁忙请稍后再试！");
			return result;
		}
	}

	@Transactional
	@Override
	public MinitorResult<?> updateRoom(Room room) {
		MinitorResult<? super Room> result = new MinitorResult<>();
		Room oldRoom=null;
		try {
			String buildID = room.getBuildID();
			String roomName = room.getRoomName();
			String oldDeviceID = manageDao.findRoombyBuild(buildID, roomName);
			System.err.println(oldDeviceID);
			if(oldDeviceID!=null) {
				Room repeatRoom = roomDao.findRoomByDId(oldDeviceID);
				if(!repeatRoom.getRoomID().equals(room.getRoomID())) {
					oldRoom = repeatRoom;
					Build build = manageDao.findBuildByID(buildID);
					oldRoom.setBuild(build);
					oldRoom.setDeviceID(oldDeviceID);
					throw new BindException("该楼层已有该房间");
				}
			}
			String deviceID = room.getDeviceID();
			oldRoom = roomDao.findRoomByDId(deviceID);
			if(oldRoom!=null&&oldRoom.getRoomID()!=null&&(!oldRoom.getRoomID().equals(room.getRoomID()))) {
				String oldBuildID = oldRoom.getBuildID();
				Build build = manageDao.findBuildByID(oldBuildID);
				oldRoom.setBuild(build);
				throw new BindException("该设备已经与其他房间绑定了");
			}
			manageDao.updateRoomByRoomID(room);
			result.setStatus(1);
			result.setMsg("更新成功！");
			return result;
		} catch (BindException e) {
			result.setStatus(2);
			result.setMsg(e.getMessage());
			result.setData(oldRoom);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new InsertAndUpdateException("系统繁忙请稍后再试！",e);
		}
	}

	@Transactional
	@Override
	public MinitorResult<?> deleteRoom(String roomID) {
		MinitorResult<Void> result = new MinitorResult<>();
		try {
			manageDao.deleteRoomByID(roomID);
			result.setStatus(1);
			result.setMsg("删除成功！");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DeleteException("系统繁忙 稍后再试！");
		}
	}

	
}
