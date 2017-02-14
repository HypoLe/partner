package com.boco.eoms.partner.appops.webapp.form;

import java.util.Date;

import org.apache.struts.upload.FormFile;

import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.base.webapp.form.BaseForm;

/**
 * 
  * @Copyright 亿阳信通
 */
public class IPnrPartnerAppOpsUserForm extends BaseForm implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FormFile importFile;

	public FormFile getImportFile() {
		return importFile;
	}

	public void setImportFile(FormFile importFile) {
		this.importFile = importFile;
	}
}