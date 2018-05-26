package cn.hdu.HDU_Minitor.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Build类表示火灾监控楼
 * @author yw
 *
 */
public class Build implements Serializable{
	private static final long serialVersionUID = 1L;
	
	//监控楼的ID
	private String buildID;
	//监控楼的楼号
	private String buildNumber;
	//监控楼的名字
	private String buildName;
	//监控楼所监控的具体房间
	private List<Room> rooms;
	//监控楼的负责人
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
