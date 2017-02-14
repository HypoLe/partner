package com.boco.eoms.partner.personnel.webapp.form;

import org.apache.struts.upload.FormFile;

import com.boco.eoms.base.webapp.form.BaseForm;
	/**
 * <p>
 * Title:人员奖励管理
 * </p>
 * <p>
 * Description:人员奖励管理
 * </p>
 * <p>
 * Jul 12, 2012 2:53:19 PM
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */
@SuppressWarnings("serial")
public class RewardForm extends BaseForm {
	/**
	 * 主键
	 */
	 private String id;
	 /**
	  * 员工Id
	 */
     private String workerid;
     /**
      * 员工的部门id
      */
     private  String deptid;
     
     /**
      * 员工的区域id
      */
     private String areaid;
     
	 /**
	  * 员工姓名
	 */
     private String workername;
 	/**
 	 * 曾获奖励
 	 */
     private String reward;
     /**
      * 获奖时间
      */
     private String awardtime;
     /**
      * 颁奖单位
      */
     private String awarddept;
     
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
      * 附件路径
      */
     private String imagepath;
     
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
     private String  personCardNo;
     
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
    private FormFile importFile;
 	public FormFile getImportFile() {
 		return importFile;
 	}
 	public void setImportFile(FormFile importFile) {
 		this.importFile = importFile;
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
	public String getReward() {
		return reward;
	}
	public void setReward(String reward) {
		this.reward = reward;
	}
	public String getWorkername() {
		return workername;
	}
	public void setWorkername(String workername) {
		this.workername = workername;
	}
	public String getAwardtime() {
		return awardtime;
	}
	public void setAwardtime(String awardtime) {
		this.awardtime = awardtime;
	}
	public String getAwarddept() {
		return awarddept;
	}
	public void setAwarddept(String awarddept) {
		this.awarddept = awarddept;
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
	public String getImagepath() {
		return imagepath;
	}
	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
	}
 
}
