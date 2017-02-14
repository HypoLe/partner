package com.boco.eoms.base.webapp.form;
/**
 * 
  * @author wangyue
  * @ClassName: PersonNameUrlForm
  * @Copyright 亿阳信通
  * @date Sep 18, 2014 5:44:46 PM
  * @description 传输局个人工作台记录权限和url的实体类
 */
public class PersonNameUrlForm {
	
	/**传输局工单下权限名称*/
	private String name ;
	/**传输局工单下权限url*/
	private String url;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
