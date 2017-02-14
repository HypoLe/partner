package com.boco.eoms.partner.assess.AssWeight.webapp.form;

import com.boco.eoms.base.webapp.form.BaseForm;

/**
 * <p>
 * Title:地市配置权重
 * </p>
 * <p>
 * Description:地市配置权重
 * </p>
 * <p>
 * Tue Feb 22 15:42:16 CST 2011
 * </p>
 * 
 * @moudle.getAuthor() benweiwei
 * @moudle.getVersion() 1.0
 * 
 */
public class CityWeightForm extends BaseForm implements java.io.Serializable {

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
	 * 权重
	 *
	 */
	private java.lang.String weight;
   
	public void setWeight(java.lang.String weight){
		this.weight= weight;       
	}
   
	public java.lang.String getWeight(){
		return this.weight;
	}

	/**
	 *
	 * 模板主键
	 *
	 */
	private java.lang.String templateId;
   
	public void setTemplateId(java.lang.String templateId){
		this.templateId= templateId;       
	}
   
	public java.lang.String getTemplateId(){
		return this.templateId;
	}

	/**
	 *
	 * 地市主键
	 *
	 */
	private java.lang.String city;
   
	public void setCity(java.lang.String city){
		this.city= city;       
	}
   
	public java.lang.String getCity(){
		return this.city;
	}

	/**
	 *
	 * 创建人
	 *
	 */
	private java.lang.String creator;
   
	public void setCreator(java.lang.String creator){
		this.creator= creator;       
	}
   
	public java.lang.String getCreator(){
		return this.creator;
	}

	/**
	 *
	 * 创建时间
	 *
	 */
	private java.lang.String createTime;
   
	public void setCreateTime(java.lang.String createTime){
		this.createTime= createTime;       
	}
   
	public java.lang.String getCreateTime(){
		return this.createTime;
	}

	/**
	 *
	 * 删除标志位
	 *
	 */
	private java.lang.String deleted;
   
	public void setDeleted(java.lang.String deleted){
		this.deleted= deleted;       
	}
   
	public java.lang.String getDeleted(){
		return this.deleted;
	}

	/**
	 *
	 * 模板树对应id
	 *
	 */
	private java.lang.String templateTreeId;
	
	public java.lang.String getTemplateTreeId() {
		return templateTreeId;
	}

	public void setTemplateTreeId(java.lang.String templateTreeId) {
		this.templateTreeId = templateTreeId;
	}
}