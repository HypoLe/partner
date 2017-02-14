package com.boco.eoms.parter.baseinfo.fee.webapp.form;

import com.boco.eoms.base.webapp.form.BaseForm;

/**
 * <p>
 * Title:合作伙伴费用单位
 * </p>
 * <p>
 * Description:合作伙伴费用单位
 * </p>
 * <p>
 * Tue Sep 08 09:38:34 CST 2009
 * </p>
 * 
 * @moudle.getAuthor() lvweihua
 * @moudle.getVersion() 3.5
 * 
 */
public class PartnerFeeUnitForm extends BaseForm implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键
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
	 * 单位名称
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
	 * 创建单位
	 *
	 */
	private java.lang.String createDate;
   
	public void setCreateDate(java.lang.String createDate){
		this.createDate= createDate;       
	}
   
	public java.lang.String getCreateDate(){
		return this.createDate;
	}

	/**
	 *
	 * 创建人
	 *
	 */
	private java.lang.String createUser;
   
	public void setCreateUser(java.lang.String createUser){
		this.createUser= createUser;       
	}
   
	public java.lang.String getCreateUser(){
		return this.createUser;
	}

	/**
	 *
	 * 创建部门
	 *
	 */
	private java.lang.String createDept;
   
	public void setCreateDept(java.lang.String createDept){
		this.createDept= createDept;       
	}
   
	public java.lang.String getCreateDept(){
		return this.createDept;
	}

	/**
	 *
	 * 是否已经删除
	 *
	 */
	private java.lang.String isDelete;
   
	public void setIsDelete(java.lang.String isDelete){
		this.isDelete= isDelete;       
	}
   
	public java.lang.String getIsDelete(){
		return this.isDelete;
	}

	/**
	 *
	 * 备注
	 *
	 */
	private java.lang.String remark;
   
	public void setRemark(java.lang.String remark){
		this.remark= remark;       
	}
   
	public java.lang.String getRemark(){
		return this.remark;
	}

}