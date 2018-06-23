package cn.hdu.HDU_Minitor.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Device implements Serializable{
	
	private String device_id; //设备id,设备上传
	private String device_ic; //当前漏电流
	private String device_it; //漏电流阈值
	private String device_tc; //当前温度
	private String device_tt; //温度阈值
	private String device_J;  //设备经度
	private String device_W;  //设备维度
	private String device_time; //上报信息时间
	private String device_status;//设备状态
	/**
	 * @return the deivce_status
	 */
	public String getDevice_status() {
		return device_status;
	}
	/**
	 * @param deivce_status the deivce_status to set
	 */
	public void setDevice_status(String device_status) {
		this.device_status = device_status;
	}
	/**
	 * @return the device_id
	 */
	public String getDevice_id() {
		return device_id;
	}
	/**
	 * @param device_id the device_id to set
	 */
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	/**
	 * @return the device_ic
	 */
	public String getDevice_ic() {
		return device_ic;
	}
	/**
	 * @param device_ic the device_ic to set
	 */
	public void setDevice_ic(String device_ic) {
		this.device_ic = device_ic;
	}
	/**
	 * @return the device_it
	 */
	public String getDevice_it() {
		return device_it;
	}
	/**
	 * @param device_it the device_it to set
	 */
	public void setDevice_it(String device_it) {
		this.device_it = device_it;
	}
	/**
	 * @return the device_tc
	 */
	public String getDevice_tc() {
		return device_tc;
	}
	/**
	 * @param device_tc the device_tc to set
	 */
	public void setDevice_tc(String device_tc) {
		this.device_tc = device_tc;
	}
	/**
	 * @return the device_tt
	 */
	public String getDevice_tt() {
		return device_tt;
	}
	/**
	 * @param device_tt the device_tt to set
	 */
	public void setDevice_tt(String device_tt) {
		this.device_tt = device_tt;
	}
	/**
	 * @return the device_J
	 */
	public String getDevice_J() {
		return device_J;
	}
	/**
	 * @param device_J the device_J to set
	 */
	public void setDevice_J(String device_J) {
		this.device_J = device_J;
	}
	/**
	 * @return the device_W
	 */
	public String getDevice_W() {
		return device_W;
	}
	/**
	 * @param device_W the device_W to set
	 */
	public void setDevice_W(String device_W) {
		this.device_W = device_W;
	}
	/**
	 * @return the device_time
	 */
	public String getDevice_time() {
		return device_time;
	}
	/**
	 * @param device_time the device_time to set
	 */
	public void setDevice_time(String device_time) {
		this.device_time = device_time;
	}
	
	@Override
	public String toString() {
		return "device[device_id:"+device_id+" device_ic:"+
				device_ic+" device_it:"+device_it+" device_tc:"+
				device_tc+" device_tt:"+device_tt+" device_time:"+device_time+
				" device_status:"+device_status+"]";
	}
	
}

