package com.huiuoo.pc.common.error;


public interface CommonError {

	int getErrCode();
	String getErrMsg();
	CommonError setErrMsg(String errMsg);
	
}
