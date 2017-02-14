package com.boco.eoms.commons.system.priv.webapp.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import com.boco.eoms.base.webapp.form.BaseForm;

/**
 * Generated by XDoclet/actionform. This class can be further processed with
 * XDoclet/webdoclet/strutsconfigxml and XDoclet/webdoclet/strutsvalidationxml.
 * 
 * @struts.form name="tawSystemPrivMenuForm"
 */
public class TawSystemPrivMenuForm extends BaseForm implements
		java.io.Serializable {

	protected String name;

	protected String ownerId;

	protected String remark;

	protected String privid;
	
	protected String nature; //平台性质 add by gongyufeng 新增wap和eoms对菜单的区别

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	public String getName() {
		return this.name;
	}

	/**
	 * @struts.validator type="required"
	 */

	public void setName(String name) {
		this.name = name;
	}

	public String getOwnerId() {
		return this.ownerId;
	}

	/**
	 * @struts.validator type="required"
	 */

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getRemark() {
		return this.remark;
	}

	/**
	 */

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPrivid() {
		return this.privid;
	}

	/**
	 * @struts.validator type="required"
	 */

	public void setPrivid(String privid) {
		this.privid = privid;
	}

	/*
	 * To add non XDoclet-generated methods, create a file named
	 * xdoclet-TawSystemPrivMenuForm.java containing the additional code and
	 * place it in your metadata/web directory.
	 */
	/**
	 * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		// reset any boolean data types to false

	}

}
