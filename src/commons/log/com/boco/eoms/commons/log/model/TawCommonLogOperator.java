package com.boco.eoms.commons.log.model;

import java.io.Serializable;

import com.boco.eoms.base.model.BaseObject;

/**
 * This class is used to generate the Struts Validator Form as well as the This
 * class is used to generate Spring Validation rules as well as the Hibernate
 * mapping file.
 * 
 * <p>
 * <a href="TawCommonLogOperator.java.html"><i>View Source</i></a>
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a> Updated by
 *         Dan Kibler (dan@getrolling.com) Extended to implement Acegi
 *         UserDetails interface by David Carter david@carter.net
 * 
 * @struts.form include-all="true" extends="BaseForm"
 * @hibernate.class table="taw_common_logoperator"
 */
public class TawCommonLogOperator extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	private String id;

	private String userid;
	
	private String username;
	
	private String deptid;
	
	private String deptname;

	private String operid;

	private String opername;
	
	private String areaid;
	
	private String areaname;

	private String modelid;

	private String modelname;

	private String operremark;

	private String remoteip;

	private String issucess;

	private String beginnotetime;

	private String notemessage;

	private String currentday;

	private String currentmonth;

	private String operatetime;

	private String bzremark;

	private String url;

	/**
	 * @hibernate.property length="10"
	 * @eoms.show
	 * @eoms.cn name="业务触发时间"
	 * @return
	 */
	public String getBeginnotetime() {
		return beginnotetime;
	}

	public void setBeginnotetime(String beginnotetime) {
		this.beginnotetime = beginnotetime;
	}

	/**
	 * @hibernate.property length="200"
	 * @eoms.show
	 * @eoms.cn name="业务描述"
	 * @return
	 */
	public String getBzremark() {
		return bzremark;
	}

	public void setBzremark(String bzremark) {
		this.bzremark = bzremark;
	}

	/**
	 * @hibernate.property length="50"
	 * @eoms.show
	 * @eoms.cn name="操作所在的天"
	 * @return
	 */
	public String getCurrentday() {
		return currentday;
	}

	public void setCurrentday(String currentday) {
		this.currentday = currentday;
	}

	/**
	 * @hibernate.property length="200"
	 * @eoms.show
	 * @eoms.cn name="操作所在的月"
	 * @return
	 */
	public String getCurrentmonth() {
		return currentmonth;
	}

	public void setCurrentmonth(String currentmonth) {
		this.currentmonth = currentmonth;
	}

	/**
	 * @hibernate.id column="id" generator-class="uuid.hex" unsaved-value="null"
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @hibernate.property length="2"
	 * @eoms.show
	 * @eoms.cn name="是否成功"
	 * @return
	 */
	public String getIssucess() {
		return issucess;
	}

	public void setIssucess(String issucess) {
		this.issucess = issucess;
	}

	/**
	 * @hibernate.property length="50"
	 * @eoms.show
	 * @eoms.cn name="操作所属模块ID"
	 * @return
	 */
	public String getModelid() {
		return modelid;
	}

	public void setModelid(String modelid) {
		this.modelid = modelid;
	}

	/**
	 * @hibernate.property length="50"
	 * @eoms.show
	 * @eoms.cn name="操作所在模块NAME"
	 * @return
	 */
	public String getModelname() {
		return modelname;
	}

	public void setModelname(String modelname) {
		this.modelname = modelname;
	}

	/**
	 * @hibernate.property length="500"
	 * @eoms.show
	 * @eoms.cn name="操作信息记录"
	 * @return
	 */
	public String getNotemessage() {
		return notemessage;
	}

	public void setNotemessage(String notemessage) {
		this.notemessage = notemessage;
	}

	/**
	 * @hibernate.property length="20"
	 * @eoms.show
	 * @eoms.cn name="操作发生的具体时间"
	 * @return
	 */
	public String getOperatetime() {
		return operatetime;
	}

	public void setOperatetime(String operatetime) {
		this.operatetime = operatetime;
	}

	/**
	 * @hibernate.property length="200"
	 * @eoms.show
	 * @eoms.cn name="操作的业务ID"
	 * @return
	 */
	public String getOperid() {
		return operid;
	}

	public void setOperid(String operid) {
		this.operid = operid;
	}

	/**
	 * @hibernate.property length="200"
	 * @eoms.show
	 * @eoms.cn name="操作所在子模块名称"
	 * @return
	 */
	public String getOpername() {
		return opername;
	}

	public void setOpername(String opername) {
		this.opername = opername;
	}

	/**
	 * @hibernate.property length="200"
	 * @eoms.show
	 * @eoms.cn name="操作的描述"
	 * @return
	 */
	public String getOperremark() {
		return operremark;
	}

	public void setOperremark(String operremark) {
		this.operremark = operremark;
	}

	/**
	 * @hibernate.property length="50"
	 * @eoms.show
	 * @eoms.cn name="操作的IP地址"
	 * @return
	 */
	public String getRemoteip() {
		return remoteip;
	}

	public void setRemoteip(String remoteip) {
		this.remoteip = remoteip;
	}

	/**
	 * @hibernate.property length="200"
	 * @eoms.show
	 * @eoms.cn name="操作执行的路径"
	 * @return
	 */
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @hibernate.property length="200"
	 * @eoms.show
	 * @eoms.cn name="操作者ID"
	 * @return
	 */
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	/**
	 * @hibernate.property length="20"
	 * @eoms.show
	 * @eoms.cn name="操作者部门ID"
	 * @return
	 */
	public String getDeptid() {
		return deptid;
	}


	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	/**
	 * @hibernate.property length="100"
	 * @eoms.show
	 * @eoms.cn name="操作者部门名称"
	 * @return
	 */
	public String getDeptname() {
		return deptname;
	}


	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	/**
	 * @hibernate.property length="50"
	 * @eoms.show
	 * @eoms.cn name="操作者名字"
	 * @return
	 */
	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @hibernate.property length="50"
	 * @eoms.show
	 * @eoms.cn name="地州ID"
	 * @return
	 */
	public String getAreaid() {
		return areaid;
	}


	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	/**
	 * @hibernate.property length="100"
	 * @eoms.show
	 * @eoms.cn name="地州名称"
	 * @return
	 */
	public String getAreaname() {
		return areaname;
	}


	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}
	
	
	
}
