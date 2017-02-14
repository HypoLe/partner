package com.boco.eoms.partner.personnel.webapp.form;

import org.apache.struts.upload.FormFile;

import com.boco.eoms.base.webapp.form.BaseForm;
/**
 * <p>
 * Title:代维人员资质信息管理
 * </p>
 * <p>
 * Description:代维人员资质信息管理
 * </p>
 * <p>
 * Jul 16, 2012 9:26:49 AM
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */
@SuppressWarnings("serial")
public class DWInfoForm extends BaseForm {
	/**
	 * 主键
	 */
	 private String id;
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
	  * 所属组织
	  */
	 private String  group;
	 /**
	  * 代维专业
	  */
	 private String    professional;
	   /**
	    * 技能等级
	    */
	 private String    skilllevel;
	 /*为了统计需要将技能等级映射到相应的等级 begin*/
	 /**
	  * 初级
	  */
	 private int juniorSkillLevel;
	 /**
	  * 中级
	  */
	 private int middleSkillLevel;
	 /**
	  * 高级
	  */
	 private int advancedSkillLevel;
	 /*为了统计需要将技能等级映射到相应的等级 end*/
	   /**
	    * 岗位
	    */
	 private String    duty;
	   /**
	    * 拥有的账户
	    */
	 private String    accountno;
	   /**
	    * 资格资质列表
	    */
	 private String    qualificationlist;
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
      * 身份证
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
  	/**
  	 * 文件上传
  	 */
     private FormFile importFile;
     
     
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getProfessional() {
		return professional;
	}
	public void setProfessional(String professional) {
		this.professional = professional;
	}
	public String getDuty() {
		return duty;
	}
	public void setDuty(String duty) {
		this.duty = duty;
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
	public String getSkilllevel() {
		return skilllevel;
	}
	public void setSkilllevel(String skilllevel) {
		this.skilllevel = skilllevel;
	}
	public String getAccountno() {
		return accountno;
	}
	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}
	public String getQualificationlist() {
		return qualificationlist;
	}
	public void setQualificationlist(String qualificationlist) {
		this.qualificationlist = qualificationlist;
	}
	public FormFile getImportFile() {
		return importFile;
	}
	public void setImportFile(FormFile importFile) {
		this.importFile = importFile;
	}
	public String getWorkerid() {
		return workerid;
	}
	public void setWorkerid(String workerid) {
		this.workerid = workerid;
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
     
     
 
}
