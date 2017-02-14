package com.boco.eoms.partner.inspect.webapp.form;

import org.apache.struts.upload.FormFile;

import com.boco.eoms.base.webapp.form.BaseForm;
import com.boco.eoms.partner.inspect.model.PnrInspectTaskFile;

public class PnrInspectTaskFileForm extends BaseForm {

	PnrInspectTaskFile pnrInspectTaskFile = new PnrInspectTaskFile();
	private FormFile importFile;
	public PnrInspectTaskFile getPnrInspectTaskFile() {
		return pnrInspectTaskFile;
	}
	public void setPnrInspectTaskFile(PnrInspectTaskFile pnrInspectTaskFile) {
		this.pnrInspectTaskFile = pnrInspectTaskFile;
	}
	public FormFile getImportFile() {
		return importFile;
	}
	public void setImportFile(FormFile importFile) {
		this.importFile = importFile;
	}
	
	
}
