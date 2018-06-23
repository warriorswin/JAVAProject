package cn.hdu.HDU_Minitor.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Room���ʾ��صľ��巿��
 * @author yw
 *
 */
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Room implements Serializable{
	private static final long serialVersionUID = 1L;
	//�����ID��
	private String roomID;
	//���������
	private String roomName;
	//¥��id
	private String buildID;
	//�豸id
	private String deviceID;
	//�����Ӧ�ļ��¥
	private Build build;
	//�����Ӧ���豸��Ϣ redis
	private Device device;
	//���ݿ������и��豸��Ϣ MySQL
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
