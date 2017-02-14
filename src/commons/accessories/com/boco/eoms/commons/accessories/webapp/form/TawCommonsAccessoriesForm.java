package com.boco.eoms.commons.accessories.webapp.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import com.boco.eoms.base.webapp.form.BaseForm;

/**
 * Generated by XDoclet/actionform. This class can be further processed with
 * XDoclet/webdoclet/strutsconfigxml and XDoclet/webdoclet/strutsvalidationxml.
 * 
 * @struts.form name="tawCommonsAccessoriesForm"
 */
public class TawCommonsAccessoriesForm extends BaseForm implements
		java.io.Serializable {
    /**
     * 附件实际名称
     */
	protected String accessoriesCnName;
	 /**
     * 附件存储名称
     */
	protected String accessoriesName;
	 /**
     * 附件存放路径
     */
	protected String accessoriesPath;
	 /**
     * 附件大小
     */
	protected String accessoriesSize;
	 /**
     * 附件上传者
     */
	protected String accessoriesUploader;
	 /**
     * 附件上传时间
     */
	protected String accessoriesUploadTime;
	
	/**
	 * date
	 */
	protected Date accessoriesUploadDate;
	 /**
     * ID
     */
	protected String id;
	 /**
     * 应用模块ID
     */
	protected String appId;
	
	/**
	 * operation type 
	 * 不用持久化
	 */
	protected String activeTemplateId;
	

	


	public Date getAccessoriesUploadDate() {
		return accessoriesUploadDate;
	}

	public void setAccessoriesUploadDate(Date accessoriesUploadDate) {
		this.accessoriesUploadDate = accessoriesUploadDate;
	}

	public String getActiveTemplateId() {
		return activeTemplateId;
	}

	public void setActiveTemplateId(String activeTemplateId) {
		this.activeTemplateId = activeTemplateId;
	}

	/** Default empty constructor. */
	public TawCommonsAccessoriesForm() {
	}

	public String getAccessoriesCnName() {
		return this.accessoriesCnName;
	}

	/**
	 */

	public void setAccessoriesCnName(String accessoriesCnName) {
		this.accessoriesCnName = accessoriesCnName;
	}

	public String getAccessoriesName() {
		return this.accessoriesName;
	}

	/**
	 */

	public void setAccessoriesName(String accessoriesName) {
		this.accessoriesName = accessoriesName;
	}

	public String getAccessoriesPath() {
		return this.accessoriesPath;
	}

	/**
	 */

	public void setAccessoriesPath(String accessoriesPath) {
		this.accessoriesPath = accessoriesPath;
	}

	public String getAccessoriesSize() {
		return this.accessoriesSize;
	}

	/**
	 */

	public void setAccessoriesSize(String accessoriesSize) {
		this.accessoriesSize = accessoriesSize;
	}

	public String getAccessoriesUploader() {
		return this.accessoriesUploader;
	}

	/**
	 */

	public void setAccessoriesUploader(String accessoriesUploader) {
		this.accessoriesUploader = accessoriesUploader;
	}

	public String getAccessoriesUploadTime() {
		return this.accessoriesUploadTime;
	}

	/**
	 */

	public void setAccessoriesUploadTime(String accessoriesUploadTime) {
		this.accessoriesUploadTime = accessoriesUploadTime;
	}

	public String getId() {
		return this.id;
	}

	/**
	 */

	public void setId(String id) {
		this.id = id;
	}

	public String getAppId() {
		return this.appId;
	}

	/**
	 */

	public void setAppId(String appId) {
		this.appId = appId;
	}

	/*
	 * To add non XDoclet-generated methods, create a file named
	 * xdoclet-TawCommonsAccessoriesForm.java containing the additional code and
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