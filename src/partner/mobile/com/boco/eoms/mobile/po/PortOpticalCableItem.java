package com.boco.eoms.mobile.po;

/**
 * 端子占用情況
 * 
 * @author whl
 * @version 20161009
 */
public class PortOpticalCableItem {
	
	private String gjxId;
	/**
	 * 端子模块
	 */
	private String module;
	/**
	 * 端子
	 */
	private String port;
	/**
	 * 端子状态
	 */
	private String status;
	/**
	 * 占用者信息
	 */
	private String userInfo;

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getGjxId() {
		return gjxId;
	}

	public void setGjxId(String gjxId) {
		this.gjxId = gjxId;
	}

}
