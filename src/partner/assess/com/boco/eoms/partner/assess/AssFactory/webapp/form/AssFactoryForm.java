package com.boco.eoms.partner.assess.AssFactory.webapp.form;


import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.webapp.form.BaseForm;

public class AssFactoryForm extends BaseForm implements java.io.Serializable {

	
	
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
	private String factory ;
	
	/**
	 *厂商名称 
	 */
	private String factoryName ;
	
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



	public String getFactory() {
		return factory;
	}



	public void setFactory(String factory) {
		this.factory = factory;
	}



	public String getFactoryName() {
		return factoryName;
	}



	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
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

	public boolean equals(Object o) {
		return this == o;
	}

}
