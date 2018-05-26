package cn.hdu.HDU_Minitor.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Room���ʾ��صľ��巿��
 * @author yw
 *
 */
public class Room implements Serializable{
	private static final long serialVersionUID = 1L;
	//�����ID��
	private String roomID;
	//���������
	private String roomName;
	//�����Ӧ�ļ��¥
	private Build build;
	//�����Ӧ���豸����ʱ��String���ʹ���
	//TODO ��Ҫ�޸�
	private String device;
	
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
	
	public String getDevice() {
		return device;
	}
	
	public void setDevice(String device) {
		this.device = device;
	}
	
	@Override
	public String toString() {
		return "Room [roomID=" + roomID + ", roomName=" + roomName + ", build=" + build + ", device=" + device + "]";
	}
	public boolean checkRoom(List<String> roomIDs) {
		if(roomIDs!=null) {
			return roomIDs.contains(this.roomID);
		}else {
			return false;
		}
	}
}
