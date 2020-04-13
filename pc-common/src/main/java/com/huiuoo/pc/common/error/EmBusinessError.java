package com.huiuoo.pc.common.error;

public enum EmBusinessError implements CommonError{
	
	//通用错误类型10000
	UNKNOW_ERROR(10001,"未知错误"),
	REQUEST_PARAM_ERROR(10002,"参数缺失异常"),
	METHOD_NOT_SUPPORETD(10003,"请求方法类型错误"),
	NULL_POINTER(10004,"空指针异常"),
	UPLOAD_FILE_ERROR(10005,"上传文件发生异常"),
	SEND_MESSAGE_CODE_ERROR(10006,"发送短信验证码发生异常"),
	SEND_MESSAGE_CODE_FORBID(10008,"频繁获取验证码"),

	//用户错误类型20000
	CAN_NOT_FIND_RECORD(20001,"未找到该实例"),
	STATUS_IS_LOCKOUT(20002,"账号已被锁定"),
	STATUS_TOKEN_INVALID(20003,"token无效"),
	STATUS_IS_ERROR(20004,"token错误"),
	STATUS_TOKEN_TIMEOUT(20005,"token已过期"),
	SMSCODE_IS_ERROR(20006,"验证码错误"),
	PHONE_IS_EXIST(20007,"手机号已被注册"),
	 OAUTH_IS_EXIST(20008,"授权账户已存在");

	private int errCode;
	private String errMsg;

	 EmBusinessError(int errCode, String errMsg) {
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

	@Override
	public int getErrCode() {
		return errCode;
	}

	@Override
	public String getErrMsg() {
		return errMsg;
	}

	@Override
	public CommonError setErrMsg(String errMsg) {
		this.errMsg = errMsg;
		return this;
	}

}
