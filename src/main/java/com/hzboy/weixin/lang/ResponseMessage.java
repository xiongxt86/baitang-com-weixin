package com.hzboy.weixin.lang;


/**
 * 响应
 * @ClassName: ResponseMessage 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author xiongxiaotun@126.com 
 * @date 2015年4月24日 下午1:26:54 
 *
 */
public class ResponseMessage {
	/**
	 * 响应状态
	 */
	private boolean status;
	/**
	 * 响应码
	 */
	private int code;
	/**
	 * 响应数据
	 */
	private Object message;
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public Object getMessage() {
		return message;
	}
	public void setMessage(Object message) {
		this.message = message;
	}
	
}
