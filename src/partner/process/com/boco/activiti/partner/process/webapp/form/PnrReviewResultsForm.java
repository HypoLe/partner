/*
 * 
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.boco.activiti.partner.process.webapp.form;

import org.apache.struts.upload.FormFile;

import com.boco.eoms.base.webapp.form.BaseForm;

/**
 * 
 * @author WANGJUN
 * 
 */
public class PnrReviewResultsForm extends BaseForm implements
		java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private FormFile importFile; // 数据的导入

	public FormFile getImportFile() {
		return importFile;
	}

	public void setImportFile(FormFile importFile) {
		this.importFile = importFile;
	}

}
