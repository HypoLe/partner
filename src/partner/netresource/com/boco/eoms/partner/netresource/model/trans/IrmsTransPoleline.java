package com.boco.eoms.partner.netresource.model.trans;

import com.boco.eoms.partner.netresource.model.EomsModel;

/**
 * 类说明：网络资源--传输线路--杆路
 * 创建人： zhangkeqi
 * 创建时间：2012-11-27 18:51:07
 */
public class IrmsTransPoleline extends EomsModel{

	/**主键*/
	private String id;
		
	/**杆路名称，作为业务主键，命名要求唯一。杆路系统中所有杆路段的集合[例]重庆沙坪坝区逸园-石板YS杆路*/
	private String polelineName;
		
	/**关联到【空间资源】-【区域】表-【区域名称】*/
	private String relatedDistrict;
		
	/**设备当前所处状态，枚举值:工程、在网、退网、空载。*/
	private String status;
		
	/**对应工程名称。例：本地接入网12期。*/
	private String projectName;
		
	/**杆路长度，单位：米。例：12345。*/
	private String length;
		
	/**格式:yyyy-mm-dd*/
	private String completedDate;
		
	/**备注*/
	private String remark;
		
	/**创建时间*/
	private String createTime;
		
	/**所属区域ID*/
	private String relatedDistirctId;
		

	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public String getPolelineName() {
		return this.polelineName;
	}
	
	public void setPolelineName(String polelineName) {
		this.polelineName = polelineName;
	}
	public String getRelatedDistrict() {
		return this.relatedDistrict;
	}
	
	public void setRelatedDistrict(String relatedDistrict) {
		this.relatedDistrict = relatedDistrict;
	}
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	public String getProjectName() {
		return this.projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getLength() {
		return this.length;
	}
	
	public void setLength(String length) {
		this.length = length;
	}
	public String getCompletedDate() {
		return this.completedDate;
	}
	
	public void setCompletedDate(String completedDate) {
		this.completedDate = completedDate;
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
	
}
