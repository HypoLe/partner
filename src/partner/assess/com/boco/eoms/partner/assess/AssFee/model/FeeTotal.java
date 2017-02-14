package com.boco.eoms.partner.assess.AssFee.model;

import com.boco.eoms.base.model.BaseObject;

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
 * @author benweiwei
 * @version 1.0
 * 
 */
public class FeeTotal extends BaseObject {

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
	private java.lang.Double totalreaMoney;
   
	public void setTotalreaMoney(java.lang.Double totalreaMoney){
		this.totalreaMoney= totalreaMoney;       
	}
   
	public java.lang.Double getTotalreaMoney(){
		return this.totalreaMoney;
	}

	/**
	 *
	 * 维护费用合计（修改后）
	 *
	 */
	private java.lang.Double totalMoney;
   
	public void setTotalMoney(java.lang.Double totalMoney){
		this.totalMoney= totalMoney;       
	}
   
	public java.lang.Double getTotalMoney(){
		return this.totalMoney;
	}

	/**
	 *
	 * 月基础维护费
	 *
	 */
	private java.lang.Double monthMoney;
   
	public void setMonthMoney(java.lang.Double monthMoney){
		this.monthMoney= monthMoney;       
	}
   
	public java.lang.Double getMonthMoney(){
		return this.monthMoney;
	}

	/**
	 *
	 * 每扣一分扣款金额
	 *
	 */
	private java.lang.Double pointMoney;
   
	public void setPointMoney(java.lang.Double pointMoney){
		this.pointMoney= pointMoney;       
	}
   
	public java.lang.Double getPointMoney(){
		return this.pointMoney;
	}

	/**
	 *
	 * 季度基础维护费
	 *
	 */
	private java.lang.Double quarterMoney;
   
	public void setQuarterMoney(java.lang.Double quarterMoney){
		this.quarterMoney= quarterMoney;       
	}
   
	public java.lang.Double getQuarterMoney(){
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
		if( o instanceof FeeTotal ) {
			FeeTotal feeTotal=(FeeTotal)o;
			if (this.id != null && this.id.equals(feeTotal.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public int hashCode() {
		return 0;
	}
}