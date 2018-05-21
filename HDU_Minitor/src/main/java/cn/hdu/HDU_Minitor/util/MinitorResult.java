package cn.hdu.HDU_Minitor.util;

import java.io.Serializable;

public class MinitorResult<T> implements Serializable {
	private int status;
	private String msg;
	private T data;
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	public String toString() {
		return "MonitorResult[status:"+status+" msg:"+msg
				+" data:"+data+"]";
		
	}

}
