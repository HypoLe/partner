package com.boco.eoms.examonline.form;

import org.apache.struts.action.ActionForm;

public class SelectExamDetailListForm extends ActionForm {
	private String specialtySel; //专业(一级菜单)
	private String wangyou;		//专业（二级菜单）
	private String youhua;		//专业（三级菜单）
	private String manufacturer; //厂家
	private String difficulty3;  //难度（有下级菜单专业）
	private String difficulty;	//难度（无下级菜单专业）
	private String contype;		//题型（选择，多选，。。。）
	
	
	public String getSpecialtySel() {
		return specialtySel;
	}
	public void setSpecialtySel(String specialtySel) {
		this.specialtySel = specialtySel;
	}
	public String getWangyou() {
		return wangyou;
	}
	public void setWangyou(String wangyou) {
		this.wangyou = wangyou;
	}
	public String getYouhua() {
		return youhua;
	}
	public void setYouhua(String youhua) {
		this.youhua = youhua;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getDifficulty3() {
		return difficulty3;
	}
	public void setDifficulty3(String difficulty3) {
		this.difficulty3 = difficulty3;
	}
	public String getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}
	public String getContype() {
		return contype;
	}
	public void setContype(String contype) {
		this.contype = contype;
	}
	
	public boolean sureAllNull(){
		if(null==specialtySel && null==wangyou && null==youhua && null==manufacturer && null==difficulty3 && null==difficulty && null==difficulty){
			return true;
		}else{
			return false;
		}
	}
}
