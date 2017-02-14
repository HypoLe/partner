package com.boco.eoms.partner.assess.AssFactory.model;

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
public class AssFactory extends BaseObject{

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

	public boolean equals(Object o) {
		return this == o;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
