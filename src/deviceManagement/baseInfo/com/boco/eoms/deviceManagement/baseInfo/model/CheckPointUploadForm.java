package com.boco.eoms.deviceManagement.baseInfo.model;

import org.apache.struts.upload.FormFile;

import com.boco.eoms.base.webapp.form.BaseForm;

public class CheckPointUploadForm extends BaseForm implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private FormFile importFile;

	public FormFile getImportFile() {
		return importFile;
	}

	public void setImportFile(FormFile importFile) {
		this.importFile = importFile;
	}

}
