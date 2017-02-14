package com.boco.eoms.partner.baseinfo.model;

/**
 * <p>
 * Title:代维组织入围资质表单
 * </p>
 * <p>
 * Description:代维组织入围资质表单
 * </p>
 * <p>
 * 2012-07-18
 * </p>
 * 
 * @author zhangkeqi
 * @version 1.0
 * 
 */
public class PnrOrgFinalistSheet {
	
	private String id;
	/**组织名称*/
	private String orgName;
	/**组织id*/
	private String orgId;
	/**代维专业*/
	private String speciality;
	/**入围时间*/
	private String finalistTime;
	/**截止时间*/
	private String finalistEndTime;
	/**备注*/
	private String remark;
	/**添加人*/
	private String addUser;
	/**添加时间*/
	private String addTime;
	/**是否删除*/
	private String isdeleted;
	/**系统编号*/
	private String sysno;
	/**部门deptid*/
	private String orgDeptId;
	/**添加部门的areaid*/
	private String areaId;
	/**是否有效:0有效，1无效*/
	private String isEffected;
	public String getIsEffected() {
		return isEffected;
	}
	public void setIsEffected(String isEffected) {
		this.isEffected = isEffected;
	}
	public String getOrgDeptId() {
		return orgDeptId;
	}
	public void setOrgDeptId(String orgDeptId) {
		this.orgDeptId = orgDeptId;
	}
	public String getIsdeleted() {
		return isdeleted;
	}
	public void setIsdeleted(String isdeleted) {
		this.isdeleted = isdeleted;
	}
	public String getSysno() {
		return sysno;
	}
	public void setSysno(String sysno) {
		this.sysno = sysno;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getSpeciality() {
		return speciality;
	}
	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}
	public String getFinalistTime() {
		return finalistTime;
	}
	public void setFinalistTime(String finalistTime) {
		this.finalistTime = finalistTime;
	}
	public String getFinalistEndTime() {
		return finalistEndTime;
	}
	public void setFinalistEndTime(String finalistEndTime) {
		this.finalistEndTime = finalistEndTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAddUser() {
		return addUser;
	}
	public void setAddUser(String addUser) {
		this.addUser = addUser;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	
	

}
