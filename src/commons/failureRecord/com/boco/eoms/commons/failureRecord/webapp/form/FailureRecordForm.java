package com.boco.eoms.commons.failureRecord.webapp.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import com.boco.eoms.base.webapp.form.BaseForm;

/**
 * Generated by XDoclet/actionform. This class can be further processed with
 * XDoclet/webdoclet/strutsconfigxml and XDoclet/webdoclet/strutsvalidationxml.
 * 
 * @struts.form name="tawSupplierkpiInfoForm"
 */
public class FailureRecordForm extends BaseForm implements
		java.io.Serializable {

	private String id="";
	private String alarmed="";
	private String title="";
	private String faultstarttime="";
	private String faultendtime="";
	private String faulttype1="";
	private String faulttype2="";
	private String faulttype3="";
	private String faulttype4="";
	private String faultregion="";
	private String faultjudge="";
	private String sheettemplatename="";
	private String faulttype5="";
	private String faultdetail="";
	private String todeptid="";
	private String todutyroom="";
	private String odutyroomid="";
	private String todutyroomen="";
	private String faultsource="";
	private String faultstatus="";


	/**
	 * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		// reset any boolean data types to false
	    this.id="";
	    this.alarmed="";
	    this.title="";
	    this.faultstarttime="";
	    this.faultendtime="";
	    this.faulttype1="";
	    this.faulttype2="";
	    this.faulttype3="";
	    this.faulttype4="";
	    this.faultregion="";
	    this.faultjudge="";
	    this.sheettemplatename="";
	    this.faulttype5="";
	    this.faultdetail="";
	    this.todeptid="";
	    this.todutyroom="";
	    this.odutyroomid="";
	    this.todutyroomen="";
	    this.faultsource="";
	    this.faultstatus="";


	}


	public String getAlarmed() {
		return alarmed;
	}


	public void setAlarmed(String alarmed) {
		this.alarmed = alarmed;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getFaultstarttime() {
		return faultstarttime;
	}


	public void setFaultstarttime(String faultstarttime) {
		this.faultstarttime = faultstarttime;
	}


	public String getFaultendtime() {
		return faultendtime;
	}


	public void setFaultendtime(String faultendtime) {
		this.faultendtime = faultendtime;
	}


	public String getFaulttype1() {
		return faulttype1;
	}


	public void setFaulttype1(String faulttype1) {
		this.faulttype1 = faulttype1;
	}


	public String getFaulttype2() {
		return faulttype2;
	}


	public void setFaulttype2(String faulttype2) {
		this.faulttype2 = faulttype2;
	}


	public String getFaulttype3() {
		return faulttype3;
	}


	public void setFaulttype3(String faulttype3) {
		this.faulttype3 = faulttype3;
	}


	public String getFaulttype4() {
		return faulttype4;
	}


	public void setFaulttype4(String faulttype4) {
		this.faulttype4 = faulttype4;
	}


	public String getFaultregion() {
		return faultregion;
	}


	public void setFaultregion(String faultregion) {
		this.faultregion = faultregion;
	}


	public String getFaultjudge() {
		return faultjudge;
	}


	public void setFaultjudge(String faultjudge) {
		this.faultjudge = faultjudge;
	}


	public String getSheettemplatename() {
		return sheettemplatename;
	}


	public void setSheettemplatename(String sheettemplatename) {
		this.sheettemplatename = sheettemplatename;
	}


	public String getFaulttype5() {
		return faulttype5;
	}


	public void setFaulttype5(String faulttype5) {
		this.faulttype5 = faulttype5;
	}


	public String getFaultdetail() {
		return faultdetail;
	}


	public void setFaultdetail(String faultdetail) {
		this.faultdetail = faultdetail;
	}


	public String getTodeptid() {
		return todeptid;
	}


	public void setTodeptid(String todeptid) {
		this.todeptid = todeptid;
	}


	public String getTodutyroom() {
		return todutyroom;
	}


	public void setTodutyroom(String todutyroom) {
		this.todutyroom = todutyroom;
	}


	public String getOdutyroomid() {
		return odutyroomid;
	}


	public void setOdutyroomid(String odutyroomid) {
		this.odutyroomid = odutyroomid;
	}


	public String getTodutyroomen() {
		return todutyroomen;
	}


	public void setTodutyroomen(String todutyroomen) {
		this.todutyroomen = todutyroomen;
	}


	public String getFaultsource() {
		return faultsource;
	}


	public void setFaultsource(String faultsource) {
		this.faultsource = faultsource;
	}


	public String getFaultstatus() {
		return faultstatus;
	}


	public void setFaultstatus(String faultstatus) {
		this.faultstatus = faultstatus;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	




	

}
