package cn.hdu.HDU_Minitor.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Room类表示监控的具体房间
 * @author yw
 *
 */
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Room implements Serializable{
	private static final long serialVersionUID = 1L;
	//房间的ID号
	private String roomID;
	//房间的名字
	private String roomName;
	//楼栋id
	private String buildID;
	//设备id
	private String deviceID;
	//房间对应的监控楼
	private Build build;
	//房间对应的设备信息 redis
	private Device device;
	//数据库中所有该设备信息 MySQL
	private List<Device> devices;
	/**
	 * @return the devices
	 */
	public List<Device> getDevices() {
		return devices;
	}

	/**
	 * @param devices the devices to set
	 */
	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the buildID
	 */
	public String getBuildID() {
		return buildID;
	}

	/**
	 * @param buildID the buildID to set
	 */
	public void setBuildID(String buildID) {
		this.buildID = buildID;
	}

	/**
	 * @return the deviceID
	 */
	public String getDeviceID() {
		return deviceID;
	}

	/**
	 * @param deviceID the deviceID to set
	 */
	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	
	
	public String getRoomID() {
		return roomID;
	}
	
	public void setRoomID(String roomID) {
		this.roomID = roomID;
	}
	
	public String getRoomName() {
		return roomName;
	}
	
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	
	public Build getBuild() {
		return build;
	}
	
	public void setBuild(Build build) {
		this.build = build;
	}
	
	public Device getDevice() {
		return device;
	}
	
	public void setDevice(Device device) {
		this.device = device;
	}
	
	
	@Override
	public String toString() {
		return "Room [roomID=" + roomID + ", roomName=" + roomName + ", buildID=" + buildID + ", deviceID=" + deviceID
				+ ", build=" + build + ", device=" + device + "]";
	}

	public boolean checkRoom(List<String> roomIDs) {
		if(roomIDs!=null) {
			return roomIDs.contains(this.roomID);
		}else {
			return false;
		}
	}


}
