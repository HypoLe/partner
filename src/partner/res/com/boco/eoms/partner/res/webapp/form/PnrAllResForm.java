package com.boco.eoms.partner.res.webapp.form;

import org.apache.struts.upload.FormFile;

import com.boco.eoms.base.webapp.form.BaseForm;
import com.boco.eoms.partner.netresource.model.bs.BsBtResource;
import com.boco.eoms.partner.res.model.PnrResConfig;
import com.boco.eoms.partner.res.model.PnrResConfigStation;
import com.boco.eoms.partner.res.model.PnrResFamilyBroadband;
import com.boco.eoms.partner.res.model.PnrResIron;
import com.boco.eoms.partner.res.model.PnrResJieke;
import com.boco.eoms.partner.res.model.PnrResLine;
import com.boco.eoms.partner.res.model.PnrResRepeaters;
import com.boco.eoms.partner.res.model.PnrResWlan;

/** 
 * Description:  
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liaojiming 
 * @version:    1.0 
 */
public class PnrAllResForm extends BaseForm {

	PnrResConfig pnrResConfig =  new PnrResConfig();
	private FormFile importFile;                    //数据的导入
	PnrResConfigStation pnrResConfigStation = new PnrResConfigStation();
	PnrResIron pnrResIron = new PnrResIron();
	PnrResJieke pnrResJieke = new PnrResJieke();
	PnrResLine pnrResLine = new PnrResLine();
	PnrResRepeaters pnrResRepeaters = new PnrResRepeaters();
	PnrResWlan pnrResWlan = new PnrResWlan();
	PnrResFamilyBroadband pnrResFamilyBroadband = new PnrResFamilyBroadband();
	

	public PnrResConfigStation getPnrResConfigStation() {
		return pnrResConfigStation;
	}

	public void setPnrResConfigStation(PnrResConfigStation pnrResConfigStation) {
		this.pnrResConfigStation = pnrResConfigStation;
	}

	public PnrResIron getPnrResIron() {
		return pnrResIron;
	}

	public void setPnrResIron(PnrResIron pnrResIron) {
		this.pnrResIron = pnrResIron;
	}

	public PnrResJieke getPnrResJieke() {
		return pnrResJieke;
	}

	public void setPnrResJieke(PnrResJieke pnrResJieke) {
		this.pnrResJieke = pnrResJieke;
	}

	public PnrResLine getPnrResLine() {
		return pnrResLine;
	}

	public void setPnrResLine(PnrResLine pnrResLine) {
		this.pnrResLine = pnrResLine;
	}

	public PnrResRepeaters getPnrResRepeaters() {
		return pnrResRepeaters;
	}

	public void setPnrResRepeaters(PnrResRepeaters pnrResRepeaters) {
		this.pnrResRepeaters = pnrResRepeaters;
	}

	public PnrResConfig getPnrResConfig() {
		return pnrResConfig;
	}

	public void setPnrResConfig(PnrResConfig pnrResConfig) {
		this.pnrResConfig = pnrResConfig;
	}
	
	public PnrResWlan getPnrResWlan() {
		return pnrResWlan;
	}

	public void setPnrResWlan(PnrResWlan pnrResWlan) {
		this.pnrResWlan = pnrResWlan;
	}

	public FormFile getImportFile() {
		return importFile;
	}

	public void setImportFile(FormFile importFile) {
		this.importFile = importFile;
	}

	public PnrResFamilyBroadband getPnrResFamilyBroadband() {
		return pnrResFamilyBroadband;
	}

	public void setPnrResFamilyBroadband(PnrResFamilyBroadband pnrResFamilyBroadband) {
		this.pnrResFamilyBroadband = pnrResFamilyBroadband;
	}
	
	
}
