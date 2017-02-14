package com.boco.activiti.partner.process.webapp.form;

import com.boco.eoms.base.webapp.form.BaseForm;

/**
 * 会审人员表单
 * @author WangJun
 *
 */

public class PnrActReviewStaffForm extends BaseForm implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// 主键ID
	private String id;
	
	// 地市ID
	private String cityId;
	
	// 地市NANE
	private String cityName;
	
	// 用户ID
	private String userId;
	
	// 用户NAME
	private String userName;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
