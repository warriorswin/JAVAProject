package cn.hdu.HDU_Minitor.entity;

public class UserBuild {
	private String user_id;
	private String build_id;
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
	 * @return the build_id
	 */
	public String getBuild_id() {
		return build_id;
	}
	/**
	 * @param build_id the build_id to set
	 */
	public void setBuild_id(String build_id) {
		this.build_id = build_id;
	}
	@Override
	public String toString() {
		return "User_Build:[user_id"+user_id+"build_id:"+
						build_id+"]";
	}

}
