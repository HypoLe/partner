package com.boco.eoms.partner.eva.webapp.form;


import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.webapp.form.BaseForm;

public class PnrEvaKpiFacturyForm extends BaseForm implements java.io.Serializable {

	
	
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



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



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



	/**
	 * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		// reset any boolean data types to false

	}

	

}
