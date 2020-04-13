package com.huiuoo.pc.common.response;

public class CommonReturnType {

	private String status;

	private Object data;

	public static CommonReturnType create(Object data) {
		return CommonReturnType.create(data,"success");
	}

	public static CommonReturnType create(Object data, String status) {
		CommonReturnType com = new CommonReturnType();
		com.setStatus(status);
		com.setData(data);
		return com;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
