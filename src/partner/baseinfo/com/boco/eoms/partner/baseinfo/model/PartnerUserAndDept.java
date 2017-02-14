package com.boco.eoms.partner.baseinfo.model;

import com.boco.eoms.base.model.BaseObject;

/**
 * 故障工单调度人管理
 * @author dell
 *
 */
public class PartnerUserAndDept extends BaseObject {

	/**
	 * ���
	 */
	private String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 *
	 * ��Ա����
	 *
	 */
	private java.lang.String name;
   
	public void setName(java.lang.String name){
		this.name= name;       
	}
   
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 *
	 * ����ID
	 *
	 */
	private java.lang.String userId;
   
	public void setUserId(java.lang.String userId){
		this.userId= userId;       
	}
   
	public java.lang.String getUserId(){
		return this.userId;
	}

	/**
	 *
	 * ����˾
	 *
	 */
	private java.lang.String deptNames;
   
	public void setDeptNames(java.lang.String deptName){
		this.deptNames= deptName;       
	}
   
	public java.lang.String getDeptNames(){
		return this.deptNames;
	}
	
	
	/**
	 * 组织ID 

	 */
	private java.lang.String deptId;

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	
	
	public boolean equals(Object o) {
		if( o instanceof PartnerUserAndDept ) {
			PartnerUserAndDept partnerUser=(PartnerUserAndDept)o;
			if (this.id != null && this.id.equals(partnerUser.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}