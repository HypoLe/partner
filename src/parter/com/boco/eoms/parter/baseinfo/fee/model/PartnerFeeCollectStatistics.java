package com.boco.eoms.parter.baseinfo.fee.model;

import java.util.Date;

import com.boco.eoms.base.model.BaseObject;

/**
 * <p>
 * Title:合作伙伴按收款单位统计
 * </p>
 * <p>
 * Description:合作伙伴按收款单位统计
 * </p>
 * <p>
 * Wed Sep 09 11:22:35 CST 2009
 * </p>
 * 
 * @author lvweihua
 * @version 3.5
 * 
 */
public class PartnerFeeCollectStatistics extends BaseObject {
	
	
	/**
	 * 主键
	 */
	private String id;
	
	/**
	 * 收款单位名称
	 */
	private String collectUnit;
	
	/**
	 * 总金额
	 * 
	 */
	private Integer countFee;
	
	/**
	 * 统计开始时间
	 */
	private String beginDate;
	
	/**
	 * 统计的结束时间
	 */
	private String endDate;
	
	public boolean equals(Object o) {
		if( o instanceof PartnerFeeCollectStatistics ) {
			PartnerFeeCollectStatistics partnerFeeCollectStatistics=(PartnerFeeCollectStatistics)o;
			if (this.id != null || this.id.equals(partnerFeeCollectStatistics.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getCollectUnit() {
		return collectUnit;
	}

	public void setCollectUnit(String collectUnit) {
		this.collectUnit = collectUnit;
	}

	public Integer getCountFee() {
		return countFee;
	}

	public void setCountFee(Integer countFee) {
		this.countFee = countFee;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


}
