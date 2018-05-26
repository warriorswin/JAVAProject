package cn.hdu.HDU_Minitor.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Build���ʾ���ּ��¥
 * @author yw
 *
 */
public class Build implements Serializable{
	private static final long serialVersionUID = 1L;
	
	//���¥��ID
	private String buildID;
	//���¥��¥��
	private String buildNumber;
	//���¥������
	private String buildName;
	//���¥����صľ��巿��
	private List<Room> rooms;
	//���¥�ĸ�����
	private List<User> users;
	
	public String getBuildID() {
		return buildID;
	}
	
	public void setBuildID(String buildID) {
		this.buildID = buildID;
	}
	
	public String getBuildNumber() {
		return buildNumber;
	}
	
	public void setBuildNumber(String buildNumber) {
		this.buildNumber = buildNumber;
	}
	
	public String getBuildName() {
		return buildName;
	}
	
	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}
	
	public List<User> getUsers() {
		return users;
	}
	
	public void setUsers(List<User> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "Build [buildID=" + buildID + ", buildNumber=" + buildNumber + ", buildName=" + buildName + ", rooms="
				+ rooms + ", users=" + users + "]";
	}
	
	public boolean checkBuild(List<String> buildIDs) {
		if(buildIDs!=null) {
			return buildIDs.contains(this.buildID);
		}else {
			return false;
		}
	}

	
}
