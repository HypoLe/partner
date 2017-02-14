package com.boco.eoms.partner.eva.model;

import com.boco.eoms.base.model.BaseObject;

/**
 * <p>
 * Title:考核厂家关联
 * </p>
 * <p>
 * Description:记录各关联厂家的信息
 * </p>
 * <p>
 * Date:Oct 27, 2009 上午 11：26
 * </p>
 * 
 * @author 贾智会
 * @version 1.0
 * 
 */
public class PnrEvaKpiFactury extends BaseObject{

	/**
	 * 主键
	 */
	private String id;
	
	/**
	 *模板Id 
	 */
	private String templateId ;
	
	/**
	 *厂商 
	 */
	private String factury ;
	
	/**
	 *厂商名称 
	 */
	private String facturyName ;
	
	/**
	 *节点Id 
	 */
	private String nodeId ;
	

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getFactury() {
		return factury;
	}

	public void setFactury(String factury) {
		this.factury = factury;
	}

	public String getFacturyName() {
		return facturyName;
	}

	public void setFacturyName(String facturyName) {
		this.facturyName = facturyName;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public boolean equals(Object o) {
		return false;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
