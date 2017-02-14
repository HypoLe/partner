package com.boco.eoms.netresource.line.model;

import org.apache.struts.upload.FormFile;

import com.boco.eoms.base.webapp.form.BaseForm;

/**
 * 线路信息导入Form
* @Title: 
* 
* @Description: TODO
* 
* @author WangGuangping  
* 
* @date Feb 19, 2012 3:12:14 PM
* 
* @version V1.0   
*
 */

public class ImportLinesForm extends BaseForm implements java.io.Serializable  {
	
	private FormFile importFile;

	public FormFile getImportFile() {
		return importFile;
	}

	public void setImportFile(FormFile importFile) {
		this.importFile = importFile;
	}


	

}
