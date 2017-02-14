package com.boco.eoms.partner.netresource.model.trans;

import com.boco.eoms.partner.netresource.model.EomsModel;

/**
 * 类说明：网络资源--传输线路--直埋
 * 创建人： zhangkeqi
 * 创建时间：2012-11-27 18:51:08
 */
public class IrmsTransDirbury extends EomsModel{

	/**主键*/
	private String id;
		
	/**由多个标石组成的路由称为直埋，即标石路由.作为业务主键，命名要求唯一。[例]重庆彭水-黔江PQ标石路由*/
	private String dirburyName;
		
	/**关联到【空间资源】-【区域】表-【区域名称】*/
	private String relatedDistrict;
		
	/**设备当前所处状态，未知、设计、在建、竣工、废弃、维护*/
	private String status;
		
	/**对应工程名称。例：本地接入网12期。*/
	private String projectName;
		
	/**直埋的长度。单位：米。例：12345。*/
	private String length;
		
	/**竣工时间。例：2004-01-02*/
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
	public String getDirburyName() {
		return this.dirburyName;
	}
	
	public void setDirburyName(String dirburyName) {
		this.dirburyName = dirburyName;
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
