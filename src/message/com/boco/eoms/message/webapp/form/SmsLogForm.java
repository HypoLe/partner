package com.boco.eoms.message.webapp.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import com.boco.eoms.base.webapp.form.BaseForm;

/**
 * Generated by XDoclet/actionform. This class can be further processed with
 * XDoclet/webdoclet/strutsconfigxml and XDoclet/webdoclet/strutsvalidationxml.
 * 
 * @struts.form name="smsLogForm"
 */
public class SmsLogForm extends BaseForm implements java.io.Serializable {

	protected String content;

	protected String dispatchTime;

	protected String id;

	protected String insertTime;

	protected String mobile;

	protected String msgType;

	protected String email;

	protected String buizId;

	protected String monitorId;

	protected String reason;

	protected String receiverId;

	protected String serviceId;

	protected String serviceName;

	protected String success;

	/** Default empty constructor. */
	public SmsLogForm() {
	}

	public String getContent() {
		return this.content;
	}

	/**
	 * @struts.validator type="required"
	 */

	public void setContent(String content) {
		this.content = content;
	}

	public String getDispatchTime() {
		return this.dispatchTime;
	}

	/**
	 * @struts.validator type="required"
	 */

	public void setDispatchTime(String dispatchTime) {
		this.dispatchTime = dispatchTime;
	}

	public String getId() {
		return this.id;
	}

	/**
	 */

	public void setId(String id) {
		this.id = id;
	}

	public String getInsertTime() {
		return this.insertTime;
	}

	/**
	 * @struts.validator type="required"
	 */

	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}

	public String getMobile() {
		return this.mobile;
	}

	/**
	 * @struts.validator type="required"
	 */

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getBuizId() {
		return this.buizId;
	}

	/**
	 * @struts.validator type="required"
	 */

	public void setBuizId(String buizId) {
		this.buizId = buizId;
	}

	public String getMonitorId() {
		return this.monitorId;
	}

	/**
	 * @struts.validator type="required"
	 */

	public void setMonitorId(String monitorId) {
		this.monitorId = monitorId;
	}

	public String getReason() {
		return this.reason;
	}

	/**
	 * @struts.validator type="required"
	 */

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getReceiverId() {
		return this.receiverId;
	}

	/**
	 * @struts.validator type="required"
	 */

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public String getServiceId() {
		return this.serviceId;
	}

	/**
	 * @struts.validator type="required"
	 */

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceName() {
		return this.serviceName;
	}

	/**
	 * @struts.validator type="required"
	 */

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getSuccess() {
		return this.success;
	}

	/**
	 * @struts.validator type="required"
	 */

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	/*
	 * To add non XDoclet-generated methods, create a file named
	 * xdoclet-SmsLogForm.java containing the additional code and place it in
	 * your metadata/web directory.
	 */
	/**
	 * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		// reset any boolean data types to false

	}

}
