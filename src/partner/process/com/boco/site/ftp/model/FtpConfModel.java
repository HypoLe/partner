package com.boco.site.ftp.model;

/**
 * FTP配置模板
 * 
 * @author 朱成旭
 * 
 */
public class FtpConfModel {
	/**
	 * IP
	 */
	private String ip;
	/**
	 * 端口
	 */
	private int port;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 路径
	 */
	private String url;
	/**
	 * 本地路径
	 */
	private String localpath;

	/**
	 * 
	 * @return IP
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * 
	 * @param ip
	 *            IP
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * 
	 * @return 端口
	 */
	public int getPort() {
		return port;
	}

	/**
	 * 
	 * @param port
	 *            端口
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * 
	 * @return 用户名
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 
	 * @param userName
	 *            用户名
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 
	 * @return 密码
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 
	 * @param password
	 *            密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 
	 * @return 路径
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 
	 * @param url
	 *            路径
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 
	 * @return 本地路径
	 */
	public String getLocalpath() {
		return localpath;
	}

	/**
	 * 
	 * @param localpath
	 *            本地路径
	 */
	public void setLocalpath(String localpath) {
		this.localpath = localpath;
	}

}
