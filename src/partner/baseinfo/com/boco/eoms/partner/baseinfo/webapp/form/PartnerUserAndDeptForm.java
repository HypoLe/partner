package com.boco.eoms.partner.baseinfo.webapp.form;

import com.boco.eoms.base.webapp.form.BaseForm;

/**
 * <p>
 * Title:��Ա�������
 * </p>
 * <p>
 * Description:��Ա�������
 * </p>
 * <p>
 * Tue Mar 10 16:24:32 CST 2009
 * </p>
 * 
 * @moudle.getAuthor() liujinlong
 * @moudle.getVersion() 3.5
 * 
 */
public class PartnerUserAndDeptForm extends BaseForm implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
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

	 */
	private java.lang.String deptId;

	public java.lang.String getDeptId() {
		return deptId;
	}

	public void setDeptId(java.lang.String deptId) {
		this.deptId = deptId;
	}
	   
	
	

}