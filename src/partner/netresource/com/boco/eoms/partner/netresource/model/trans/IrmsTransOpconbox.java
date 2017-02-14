package com.boco.eoms.partner.netresource.model.trans;

import com.boco.eoms.partner.netresource.model.EomsModel;

/**
 * 类说明：网络资源--传输线路--光电接头盒
 * 创建人： zhangkeqi
 * 创建时间：2012-11-27 18:51:08
 */
public class IrmsTransOpconbox extends EomsModel{

	/**主键*/
	private String id;
		
	/**光电接头盒名称，作为业务主键，命名要求唯一。用于对配线光缆/电缆进行终接、保护和调度管理的设备，具有固定、熔接、余留盘绕、配线调度等功能。[例]重庆沙坪坝区逸园-石板光缆YS-35号杆/GZT1   重庆沙坪坝区逸园-石板光缆YS-102号杆/GT1    重庆沙坪坝区逸园基站/GZDH001*/
	private String boxName;
		
	/**光电接头盒、光终端盒*/
	private String boxType;
		
	/**关联到【空间资源】-【区域】表-【区域名称】*/
	private String relatedDistrict;
		
	/**可以为机房、人手井、电杆。关联到【空间资源】-【机房】表，或者【人手井】表-【人手井名称】、或者【电杆】-【电杆名称】。*/
	private String boxObject;
		
	/**设备的供货厂家*/
	private String vendor;
		
	/**设备当前所处状态，在下拉框中选择，包括：工程、在网、退网。*/
	private String status;
		
	/**直通、分歧*/
	private String connectWay;
		
	/**容量*/
	private String capacity;
		
	/**备注*/
	private String remark;
		
	/**创建时间*/
	private String createTime;
		
	/**所属区域ID*/
	private String relatedDistirctId;
		
	/**所属对象ID*/
	private String boxObjectId;
		

	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public String getBoxName() {
		return this.boxName;
	}
	
	public void setBoxName(String boxName) {
		this.boxName = boxName;
	}
	public String getBoxType() {
		return this.boxType;
	}
	
	public void setBoxType(String boxType) {
		this.boxType = boxType;
	}
	public String getRelatedDistrict() {
		return this.relatedDistrict;
	}
	
	public void setRelatedDistrict(String relatedDistrict) {
		this.relatedDistrict = relatedDistrict;
	}
	public String getBoxObject() {
		return this.boxObject;
	}
	
	public void setBoxObject(String boxObject) {
		this.boxObject = boxObject;
	}
	public String getVendor() {
		return this.vendor;
	}
	
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	public String getConnectWay() {
		return this.connectWay;
	}
	
	public void setConnectWay(String connectWay) {
		this.connectWay = connectWay;
	}
	public String getCapacity() {
		return this.capacity;
	}
	
	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getRelatedDistirctId() {
		return this.relatedDistirctId;
	}
	
	public void setRelatedDistirctId(String relatedDistirctId) {
		this.relatedDistirctId = relatedDistirctId;
	}
	public String getBoxObjectId() {
		return this.boxObjectId;
	}
	
	public void setBoxObjectId(String boxObjectId) {
		this.boxObjectId = boxObjectId;
	}
	
}
