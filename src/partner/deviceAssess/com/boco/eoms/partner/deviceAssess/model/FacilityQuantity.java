package com.boco.eoms.partner.deviceAssess.model;

import com.boco.eoms.base.model.BaseObject;

/**
 * <p>
 * Title:设备量信息 
 * @author heminxi
 * @version 1.0
 * 
 */
public class FacilityQuantity extends BaseObject {

	/**
	 * 主键
	 */
	private String id;
	
	private String factory;
	
	private String major;
	
	private String deviceType;
	
	private Integer quantity;
	

	
	
	
	
	public String getDeviceType() {
		return deviceType;
	}






	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}






	public String getFactory() {
		return factory;
	}






	public void setFactory(String factory) {
		this.factory = factory;
	}






	public String getId() {
		return id;
	}






	public void setId(String id) {
		this.id = id;
	}






	public String getMajor() {
		return major;
	}






	public void setMajor(String major) {
		this.major = major;
	}






	public Integer getQuantity() {
		return quantity;
	}






	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}






	public boolean equals(Object o) {
		if( o instanceof FacilityQuantity ) {
			FacilityQuantity bigFault=(FacilityQuantity)o;
			if (this.id != null || this.id.equals(bigFault.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	


}