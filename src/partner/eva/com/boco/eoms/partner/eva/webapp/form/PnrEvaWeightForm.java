package com.boco.eoms.partner.eva.webapp.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.webapp.form.BaseForm;

public class PnrEvaWeightForm extends BaseForm implements java.io.Serializable {

	
	/**
	 * 主键
	 */
	protected String id;
	
	/**
	 *节点Id 
	 */
	protected String nodeId ;
	
	/**
	 *地市的个性权重 
	 */
	protected float weight ;
	
	
	/**
	 *地域 
	 */
	private String area ;
	

	public String getArea() {
		return area;
	}


	public void setArea(String area) {
		this.area = area;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getNodeId() {
		return nodeId;
	}


	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}


	public float getWeight() {
		return weight;
	}


	public void setWeight(float weight) {
		this.weight = weight;
	}



	/**
	 * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		// reset any boolean data types to false

	}

	

}
