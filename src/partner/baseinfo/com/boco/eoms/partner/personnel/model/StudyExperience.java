package com.boco.eoms.partner.personnel.model;

import com.boco.eoms.base.model.BaseObject;
	/**
 * <p>
 * Title:人员学习经历管理
 * </p>
 * <p>
 * Description:人员学习经历管理
 * </p>
 * <p>
 * Jul 19, 2012 10:26:49 AM
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */
@SuppressWarnings("serial")
public class StudyExperience extends BaseObject {
	/**
	 * 主键
	 */
	 private String id;
	/**
	 * 入学时间
	 */
	 private String  intime;
	 /**
	  * 毕业时间
	  */
	 private String  leavetime;
	 /**
	 * 毕业院校
	 */	 
	 private String  university;
	 /**
	  * 所学专业
	  */
	 private String  professional;
	 /**
	  * 获取学历
	  */
	 private String  degree;
	 /*为了统计需要将学历映射到相应的等级 begin*/
	 /**
	  * 初中及以下
	  */
	 private int juniorDegree;
	 /**
	  * 中专
	  */
	 private int technicalDegree;
	 /**
	  * 高中
	  */
	 private int seniorDegree;
	 /**
	  * 大专
	  */
	 private int collegeDegree;
	 /**
	  * 本科
	  */
	 private int undergraduateDegree;
	 /**
	  * 硕士及以上
	  */
	 private int masterDegree;
	 /*为了统计需要将学历等级映射到相应的等级 end*/
	 /**
	  * 毕业证书编码
	  */
	 private String  code;
	 /**
	  * 毕业证书扫描件路径
	  */
	 private String  imagepath;
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
		if ( o instanceof StudyExperience) {
			try {
				if(((StudyExperience)o).id.equals(this.id))
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
	public String getUniversity() {
		return university;
	}
	public void setUniversity(String university) {
		this.university = university;
	}
	public String getProfessional() {
		return professional;
	}
	public void setProfessional(String professional) {
		this.professional = professional;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getIntime() {
		return intime;
	}
	public void setIntime(String intime) {
		this.intime = intime;
	}
	public String getLeavetime() {
		return leavetime;
	}
	public void setLeavetime(String leavetime) {
		this.leavetime = leavetime;
	}
	public String getImagepath() {
		return imagepath;
	}
	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
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
	public int getJuniorDegree() {
		return juniorDegree;
	}
	public void setJuniorDegree(int juniorDegree) {
		this.juniorDegree = juniorDegree;
	}
	public int getTechnicalDegree() {
		return technicalDegree;
	}
	public void setTechnicalDegree(int technicalDegree) {
		this.technicalDegree = technicalDegree;
	}
	public int getSeniorDegree() {
		return seniorDegree;
	}
	public void setSeniorDegree(int seniorDegree) {
		this.seniorDegree = seniorDegree;
	}
	public int getCollegeDegree() {
		return collegeDegree;
	}
	public void setCollegeDegree(int collegeDegree) {
		this.collegeDegree = collegeDegree;
	}
	public int getUndergraduateDegree() {
		return undergraduateDegree;
	}
	public void setUndergraduateDegree(int undergraduateDegree) {
		this.undergraduateDegree = undergraduateDegree;
	}
	public int getMasterDegree() {
		return masterDegree;
	}
	public void setMasterDegree(int masterDegree) {
		this.masterDegree = masterDegree;
	}
	
}
