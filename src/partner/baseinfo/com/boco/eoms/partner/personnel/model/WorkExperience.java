package com.boco.eoms.partner.personnel.model;

import com.boco.eoms.base.model.BaseObject;
/**
 * 人员工作经历信息
* @ClassName workExperience
 * @Description 
 * @author  fengguangping
 * @date Nov 28, 2012
 */
public class WorkExperience extends BaseObject {
	/**
	 * 主键
	 */
	 private String id;
	 /**
	  * 入职时间
	  */
	 private String entryTime;
	 /**
	  * 离职时间
	  */
	 private String leaveTime;
	 /**
	  * 工作单位
	  */
	 private String company;
	 /**
	  * 工作职责
	  */
	 private String duty;
	 /**
	  * 员工Id
	 */
     private String workerid;
     /**
	  * 员工姓名
	 */
     private String workername;
     /**
      * 员工的部门id
      */
     private  String deptid;
     
     /**
      * 员工的区域id
      */
     private String areaid;
     
 	/**
 	 * 备注
 	 */
     private String memo;
 	/**
 	 * 最终修改时间
 	 */
     private String lastedittime;
 	/**
 	 * 最终修改人ID
 	 */
     private String lastediterid;
 	/**
 	 * 最终修改人姓名
 	 */
     private String lasteditername;
 	/**
 	 * 创建人
 	 */
     private String adduser;
 	/**
 	 * 创建人部门
 	 */
     private String adddept;
 	/**
 	 * 创建时间
 	 */
     private String addtime;
 	/**
 	 * 删除标准  0：未删除 1：删除
 	 */
     private String isdelete;
     /**
      * 系统编号
      */
     private String sysno;
     /**
      * 关联人员身份证号
      */
     private String personCardNo;
     
     
     public String getPersonCardNo() {
		return personCardNo;
	}
	public void setPersonCardNo(String personCardNo) {
		this.personCardNo = personCardNo;
	}
     
     public String getSysno() {
 		return sysno;
 	}
 	public void setSysno(String sysno) {
 		this.sysno = sysno;
 	}
	@Override
	public boolean equals(Object o) {
		if ( o instanceof WorkExperience) {
			try {
				if(((WorkExperience)o).id.equals(this.id))
					return true;
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWorkerid() {
		return workerid;
	}
	public void setWorkerid(String workerid) {
		this.workerid = workerid;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getLastedittime() {
		return lastedittime;
	}
	public void setLastedittime(String lastedittime) {
		this.lastedittime = lastedittime;
	}
	public String getLastediterid() {
		return lastediterid;
	}
	public void setLastediterid(String lastediterid) {
		this.lastediterid = lastediterid;
	}
	public String getLasteditername() {
		return lasteditername;
	}
	public void setLasteditername(String lasteditername) {
		this.lasteditername = lasteditername;
	}
	public String getAdduser() {
		return adduser;
	}
	public void setAdduser(String adduser) {
		this.adduser = adduser;
	}
	public String getAdddept() {
		return adddept;
	}
	public void setAdddept(String adddept) {
		this.adddept = adddept;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public String getIsdelete() {
		return isdelete;
	}
	public void setIsdelete(String isdelete) {
		this.isdelete = isdelete;
	}
	public String getWorkername() {
		return workername;
	}
	public void setWorkername(String workername) {
		this.workername = workername;
	}
	public String getDeptid() {
		return deptid;
	}
	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}
	public String getAreaid() {
		return areaid;
	}
	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}
	public String getEntryTime() {
		return entryTime;
	}
	public void setEntryTime(String entryTime) {
		this.entryTime = entryTime;
	}
	public String getLeaveTime() {
		return leaveTime;
	}
	public void setLeaveTime(String leaveTime) {
		this.leaveTime = leaveTime;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getDuty() {
		return duty;
	}
	public void setDuty(String duty) {
		this.duty = duty;
	}
	
}
