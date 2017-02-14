package com.boco.eoms.partner.netresource.action.form;

import org.apache.struts.upload.FormFile;

import com.boco.eoms.base.webapp.form.BaseForm;

/** 
 * Description:  
 * Copyright:   Copyright (c)2013
 * Company:     BOCO 
 * @author:     chenbing 
 * @version:    1.0 
 */
public class PnrbsbtResForm extends BaseForm {

	private FormFile importFile;                    //数据的导入


	public FormFile getImportFile() {
		return importFile;
	}

	public void setImportFile(FormFile importFile) {
		this.importFile = importFile;
	}

	
}
