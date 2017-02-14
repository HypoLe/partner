package com.boco.eoms.partner.netresource.model.trans;

import com.boco.eoms.partner.netresource.model.EomsModel;

/**
 * 类说明：网络资源--传输线路--管道
 * 创建人： zhangkeqi
 * 创建时间：2012-11-27 18:51:08
 */
public class IrmsTransPipe extends EomsModel{

	/**主键*/
	private String id;
		
	/**管道名称，作为业务主键，命名要求唯一。[例]重庆渝中区石桥铺-两路口SL管道 或者 重庆渝中区中山四路ZS管道*/
	private String pipeName;
		
	/**关联到【空间资源】-【区域】表-【区域名称】*/
	private String relatedArea;
		
	/**设备当前所处状态，在下拉框中选择，枚举值：工程、现网、退网、空载。*/
	private String status;
		
	/**管道对应的工程名称。例：本地接入网12期。*/
	private String projectName;
		
	/**管道长度，单位：米。*/
	private String length;
		
	/**竣工时间：yyyy-MM-dd*/
	private String completeTime;
		
	/**备注*/
	private String remark;
		
	/**创建时间*/
	private String createTime;
		
	/**所属区域ID*/
	private String relatedAreaId;
		

	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public String getPipeName() {
		return this.pipeName;
	}
	
	public void setPipeName(String pipeName) {
		this.pipeName = pipeName;
	}
	public String getRelatedArea() {
		return this.relatedArea;
	}
	
	public void setRelatedArea(String relatedArea) {
		this.relatedArea = relatedArea;
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
	public String getCompleteTime() {
		return this.completeTime;
	}
	
	public void setCompleteTime(String completeTime) {
		this.completeTime = completeTime;
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
	public String getRelatedAreaId() {
		return this.relatedAreaId;
	}
	
	public void setRelatedAreaId(String relatedAreaId) {
		this.relatedAreaId = relatedAreaId;
	}
	
}
