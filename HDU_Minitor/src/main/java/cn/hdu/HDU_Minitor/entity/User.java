package cn.hdu.HDU_Minitor.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class User implements Serializable{
	
	private static final long serialVersionUID = 1L;
	//用户的id
	private String user_id;
	//用户的电话
	private String user_phone;
	//用户的密码
	private String user_password;
	//用户的名字
	private String user_name;
	//用户所监控的监控楼
	private List<Build> builds;
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
	 * @return the user_phone
	 */
	public String getUser_phone() {
		return user_phone;
	}
	/**
	 * @param user_phone the user_phone to set
	 */
	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}
	/**
	 * @return the user_password
	 */
	public String getUser_password() {
		return user_password;
	}
	/**
	 * @param user_password the user_password to set
	 */
	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}
	/**
	 * @return the user_name
	 */
	public String getUser_name() {
		return user_name;
	}
	/**
	 * @param user_name the user_name to set
	 */
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
	public List<Build> getBuilds() {
		return builds;
	}
	public void setBuilds(List<Build> builds) {
		this.builds = builds;
	}
	
	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", user_phone=" + user_phone + ", user_password=" + user_password
				+ ", user_name=" + user_name + ", builds=" + builds + "]";
	}
	
	
	public Map<String, String> checkUser(User user){
		Map<String,String> result = new HashMap<String,String>();
		result.put("user_id", this.user_id);
		if(!this.user_phone.equals(user.getUser_phone())) {
			result.put("user_phone", this.user_phone);
		}
		if(!this.user_password.equals(user.getUser_password())) {
			result.put("user_password", this.user_password);
		}
		if(!this.user_name.equals(user.getUser_name())) {
			result.put("user_name", this.user_name);
		}
		return result;
	}
	
	
	

}
