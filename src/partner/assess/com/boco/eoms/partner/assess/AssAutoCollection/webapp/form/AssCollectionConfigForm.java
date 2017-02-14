package com.boco.eoms.partner.assess.AssAutoCollection.webapp.form;

import com.boco.eoms.base.webapp.form.BaseForm;

/**
 * <p>
 * Title:采集配置
 * </p>
 * <p>
 * Description:采集配置
 * </p>
 * <p>
 * Thu Mar 31 09:11:04 CST 2011
 * </p>
 * 
 * @moudle.getAuthor() benweiwei
 * @moudle.getVersion() 1.0
 * 
 */
public class AssCollectionConfigForm extends BaseForm implements java.io.Serializable {

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
	 * 采集名称
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
	 * 查询语句
	 *
	 */
	private java.lang.String sql;
   
	public void setSql(java.lang.String sql){
		this.sql= sql;       
	}
   
	public java.lang.String getSql(){
		return this.sql;
	}

	/**
	 *
	 * 对应节点Id
	 *
	 */
	private java.lang.String treeNodeId;
   
	public void setTreeNodeId(java.lang.String treeNodeId){
		this.treeNodeId= treeNodeId;       
	}
   
	public java.lang.String getTreeNodeId(){
		return this.treeNodeId;
	}

	/**
	 *
	 * 时间对应字段
	 *
	 */
	private java.lang.String timeColumn;

	public java.lang.String getTimeColumn() {
		return timeColumn;
	}

	public void setTimeColumn(java.lang.String timeColumn) {
		this.timeColumn = timeColumn;
	}
	
	/**
	 *
	 * 合作伙伴对应字段
	 *
	 */
	private java.lang.String partnerColumn;	
	
	public java.lang.String getPartnerColumn() {
		return partnerColumn;
	}

	public void setPartnerColumn(java.lang.String partnerColumn) {
		this.partnerColumn = partnerColumn;
	}

	/**
	 *
	 * 专业对应字段
	 *
	 */
	private java.lang.String specialtyColumn;

	public java.lang.String getSpecialtyColumn() {
		return specialtyColumn;
	}

	public void setSpecialtyColumn(java.lang.String specialtyColumn) {
		this.specialtyColumn = specialtyColumn;
	}

	/**
	 *
	 * 地市对应字段
	 *
	 */
	private java.lang.String areaColumn;

	public java.lang.String getAreaColumn() {
		return areaColumn;
	}

	public void setAreaColumn(java.lang.String areaColumn) {
		this.areaColumn = areaColumn;
	}	

}