package com.boco.activiti.partner.process.model;

import java.io.Serializable;

/**
 * 与运维商城的接口之间的配置信息
 * @author chenbing 
 *
 */
public class OperationShopConfigModel implements Serializable{
	//例如 http://192.168.10.39:8088/worksheet/boco
	private String visitHtmlPathPrefix;
	//例如 /usr/boco/tomcat/apache-tomcat-6.0.24/webapps/worksheet/boco
	private String placeHtmlPathPrefix;
	//项目正式的访问地址
	private String visitProjectUrl;
	//图片服务器地址
	private String imgServerUrl;
	
	
	public String getImgServerUrl() {
		return imgServerUrl;
	}
	public void setImgServerUrl(String imgServerUrl) {
		this.imgServerUrl = imgServerUrl;
	}
	public String getVisitHtmlPathPrefix() {
		return visitHtmlPathPrefix;
	}
	public void setVisitHtmlPathPrefix(String visitHtmlPathPrefix) {
		this.visitHtmlPathPrefix = visitHtmlPathPrefix;
	}
	public String getPlaceHtmlPathPrefix() {
		return placeHtmlPathPrefix;
	}
	public void setPlaceHtmlPathPrefix(String placeHtmlPathPrefix) {
		this.placeHtmlPathPrefix = placeHtmlPathPrefix;
	}
	
	public String getVisitProjectUrl() {
		return visitProjectUrl;
	}
	public void setVisitProjectUrl(String visitProjectUrl) {
		this.visitProjectUrl = visitProjectUrl;
	}
	public OperationShopConfigModel() {
	
	}
	
	
	
}
