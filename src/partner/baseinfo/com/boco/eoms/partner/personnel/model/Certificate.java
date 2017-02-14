package com.boco.eoms.partner.personnel.model;

import com.boco.eoms.base.model.BaseObject;
	/**
 * <p>
 * Title:人员证书管理
 * </p>
 * <p>
 * Description:人员证书管理
 * </p>
 * <p>
 * Jul 10, 2012 10:26:49 AM
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */
@SuppressWarnings("serial")
public class Certificate extends BaseObject {
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
 	 * 证书类别
 	 */
     private String type;
     /*为了统计的需要将证书映射为具体字段*/
     /**
      * 电工证数量
      */
     private int egCertType;
     /**
      * 登高证数量
      */
     private int dgCertType;
     /**
      * 制冷证数量
      */
     private int zlCertType;
     /**
      * 驾驶证数量
      */
     private int jsCertType;
     /**
      * 其他证数量
      */
     private int otherCertType;
     /*为了统计的需要将证书映射为具体字段*/
 	/**
 	 * 证书等级
 	 */
     private String level;
 	/**
 	 * 发证机关
 	 */
     private String issueorg;
 	/**
 	 * 颁发时间
 	 */
     private String issuetime;
 	/**
 	 * 有效期
 	 */
     private String validity;
 	/**
 	 * 证书编号
 	 */
     private String codeno;
 	/**
 	 * 证书扫描件 路径
 	 */
     private String imagepath;
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
 	/**
 	 * 删除标准  0：未删除 1：删除
 	 */
     private String isdelete;
     
	@Override
	public boolean equals(Object o) {
		if ( o instanceof Certificate) {
			try {
				if(((Certificate)o).id.equals(this.id))
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getIssueorg() {
		return issueorg;
	}
	public void setIssueorg(String issueorg) {
		this.issueorg = issueorg;
	}
	public String getIssuetime() {
		return issuetime;
	}
	public void setIssuetime(String issuetime) {
		this.issuetime = issuetime;
	}
	public String getValidity() {
		return validity;
	}
	public void setValidity(String validity) {
		this.validity = validity;
	}
	public String getCodeno() {
		return codeno;
	}
	public void setCodeno(String codeno) {
		this.codeno = codeno;
	}
	public String getImagepath() {
		return imagepath;
	}
	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
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
	public int getEgCertType() {
		return egCertType;
	}
	public void setEgCertType(int egCertType) {
		this.egCertType = egCertType;
	}
	public int getDgCertType() {
		return dgCertType;
	}
	public void setDgCertType(int dgCertType) {
		this.dgCertType = dgCertType;
	}
	public int getZlCertType() {
		return zlCertType;
	}
	public void setZlCertType(int zlCertType) {
		this.zlCertType = zlCertType;
	}
	public int getJsCertType() {
		return jsCertType;
	}
	public void setJsCertType(int jsCertType) {
		this.jsCertType = jsCertType;
	}
	public int getOtherCertType() {
		return otherCertType;
	}
	public void setOtherCertType(int otherCertType) {
		this.otherCertType = otherCertType;
	}
}
