package com.boco.eoms.partner.property.webapp.form;

import java.util.Date;

import org.apache.struts.upload.FormFile;

import com.boco.eoms.base.webapp.form.BaseForm;

/**
 * 类说明：电费费用记录物业合同
 * 创建人： fengguangping
 * 创建时间：2012-08-27 16:57:47
 */
public class PnrPropertyAgreementForm extends BaseForm{

		
	private FormFile importFile;
	
	public FormFile getImportFile() {
		return importFile;
	}

	public void setImportFile(FormFile importFile) {
		this.importFile = importFile;
	}
	
}
