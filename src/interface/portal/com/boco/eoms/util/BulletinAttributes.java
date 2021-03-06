package com.boco.eoms.util;

/**
 * <p>
 * Title:值班属性配置类
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Date:Apr 15, 2008 1:42:00 PM
 * </p>
 * 
 * @author 曲静波
 * @version 1.0
 * 
 */
public class BulletinAttributes {

	private String webserviceurl;
	private String bulletinHttpPortAddress;//CRM接口地址
	private String bulletinSerSupplier;//服务提供方
	private String bulletinSerCaller;//服务调用方
	private String bulletinCallerPwd;//服务调用方密码(可选)
	private String bulletinForumsId;//信息专题id
	private String bulletinForumsIdTwo; //另一个信息专题id
	private String bulletinSerialNoType;//公告类型
	private String bulletinAccessories;//附件存放地址
	//其他接口服务提供方
	private String bulletionHttpPortAddressTwo;
	
	private String bulletinRootPath;//系统的根路径
	
	private String callMethod;//调用的服务方式
	private String callMethodTwo; //第二个调用的服务方式
	

	public String getCallMethodTwo() {
		return callMethodTwo;
	}

	public void setCallMethodTwo(String callMethodTwo) {
		this.callMethodTwo = callMethodTwo;
	}

	public String getBulletinHttpPortAddress() {
		return bulletinHttpPortAddress;
	}

	public void setBulletinHttpPortAddress(String bulletinHttpPortAddress) {
		this.bulletinHttpPortAddress = bulletinHttpPortAddress;
	}

	public String getBulletinSerSupplier() {
		return bulletinSerSupplier;
	}

	public void setBulletinSerSupplier(String bulletinSerSupplier) {
		this.bulletinSerSupplier = bulletinSerSupplier;
	}

	public String getBulletinSerCaller() {
		return bulletinSerCaller;
	}

	public void setBulletinSerCaller(String bulletinSerCaller) {
		this.bulletinSerCaller = bulletinSerCaller;
	}

	public String getBulletinCallerPwd() {
		return bulletinCallerPwd;
	}

	public void setBulletinCallerPwd(String bulletinCallerPwd) {
		this.bulletinCallerPwd = bulletinCallerPwd;
	}

	public String getWebserviceurl() {
		return webserviceurl;
	}

	public void setWebserviceurl(String webserviceurl) {
		this.webserviceurl = webserviceurl;
	}

	public String getBulletinSerialNoType() {
		return bulletinSerialNoType;
	}

	public void setBulletinSerialNoType(String bulletinSerialNoType) {
		this.bulletinSerialNoType = bulletinSerialNoType;
	}

	public String getBulletinAccessories() {
		return bulletinAccessories;
	}

	public void setBulletinAccessories(String bulletinAccessories) {
		this.bulletinAccessories = bulletinAccessories;
	}

	public String getBulletinRootPath() {
		return bulletinRootPath;
	}


	public void setBulletinRootPath(String bulletinRootPath) {
		this.bulletinRootPath = bulletinRootPath;
	}

	public String getCallMethod() {
		return callMethod;
	}

	public void setCallMethod(String callMethod) {
		this.callMethod = callMethod;
	}


	public String getBulletinForumsIdTwo() {
		return bulletinForumsIdTwo;
	}

	public void setBulletinForumsIdTwo(String bulletinForumsIdTwo) {
		this.bulletinForumsIdTwo = bulletinForumsIdTwo;
	}

	public String getBulletinForumsId() {
		return bulletinForumsId;
	}

	public void setBulletinForumsId(String bulletinForumsId) {
		this.bulletinForumsId = bulletinForumsId;
	}

	public String getBulletionHttpPortAddressTwo() {
		return bulletionHttpPortAddressTwo;
	}

	public void setBulletionHttpPortAddressTwo(String bulletionHttpPortAddressTwo) {
		this.bulletionHttpPortAddressTwo = bulletionHttpPortAddressTwo;
	}

}
