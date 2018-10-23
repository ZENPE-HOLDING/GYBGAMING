package com.wyj.web3j.common.json;

public class ReturnJson<T> {
	private boolean success = true;
	private String errMsg ="";
	private String code = "0000";
	private T result;
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public T getResult() {
		return result;
	}
	public void setResult(T result) {
		this.result = result;
	}

	
}
