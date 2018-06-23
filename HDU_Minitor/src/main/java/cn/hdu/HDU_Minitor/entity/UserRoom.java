package cn.hdu.HDU_Minitor.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class UserRoom {
	private String user_id;
	private String room_id;
	/**
	 * @return the user_id
	 */
	public String getUser_id() {
		return user_id;
	}
	/**
	 * @param user_id the user_id to set
	 */
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	/**
	 * @return the room_id
	 */
	public String getRoom_id() {
		return room_id;
	}
	/**
	 * @param room_id the room_id to set
	 */
	public void setRoom_id(String room_id) {
		this.room_id = room_id;
	}
	
	@Override
	public String toString() {
		return "UserRoom:[user_id:"+user_id+",room_id:"+room_id+"]";
	}
	

}
