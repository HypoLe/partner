package com.boco.eoms.partner.assess.AssFee.webapp.form;

import com.boco.eoms.base.webapp.form.BaseForm;

/**
 * <p>
 * Title:计算结果信息
 * </p>
 * <p>
 * Description:计算结果信息
 * </p>
 * <p>
 * Tue Nov 23 16:04:36 CST 2010
 * </p>
 * 
 * @moudle.getAuthor() benweiwei
 * @moudle.getVersion() 1.0
 * 
 */
public class FeeTotalForm extends BaseForm implements java.io.Serializable {

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
	 * 维护费用合计（实际）
	 *
	 */
	private java.lang.String totalreaMoney;
   
	public void setTotalreaMoney(java.lang.String totalreaMoney){
		this.totalreaMoney= totalreaMoney;       
	}
   
	public java.lang.String getTotalreaMoney(){
		return this.totalreaMoney;
	}

	/**
	 *
	 * 维护费用合计（修改后）
	 *
	 */
	private java.lang.String totalMoney;
   
	public void setTotalMoney(java.lang.String totalMoney){
		this.totalMoney= totalMoney;       
	}
   
	public java.lang.String getTotalMoney(){
		return this.totalMoney;
	}

	/**
	 *
	 * 月基础维护费
	 *
	 */
	private java.lang.String monthMoney;
   
	public void setMonthMoney(java.lang.String monthMoney){
		this.monthMoney= monthMoney;       
	}
   
	public java.lang.String getMonthMoney(){
		return this.monthMoney;
	}

	/**
	 *
	 * 每扣一分扣款金额
	 *
	 */
	private java.lang.String pointMoney;
   
	public void setPointMoney(java.lang.String pointMoney){
		this.pointMoney= pointMoney;       
	}
   
	public java.lang.String getPointMoney(){
		return this.pointMoney;
	}

	/**
	 *
	 * 季度基础维护费
	 *
	 */
	private java.lang.String quarterMoney;
   
	public void setQuarterMoney(java.lang.String quarterMoney){
		this.quarterMoney= quarterMoney;       
	}
   
	public java.lang.String getQuarterMoney(){
		return this.quarterMoney;
	}

	/**
	 *
	 * 地市
	 *
	 */
	private java.lang.String areaId;
   
	public void setAreaId(java.lang.String areaId){
		this.areaId= areaId;       
	}
   
	public java.lang.String getAreaId(){
		return this.areaId;
	}

	/**
	 *
	 * 年
	 *
	 */
	private java.lang.String year;
   
	public void setYear(java.lang.String year){
		this.year= year;       
	}
   
	public java.lang.String getYear(){
		return this.year;
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
	
	public boolean equals(Object o) {
		return this == o;
	}

}