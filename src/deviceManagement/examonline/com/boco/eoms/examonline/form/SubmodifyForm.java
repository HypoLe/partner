package com.boco.eoms.examonline.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

/** 
 * Description:  
 * Copyright:   Copyright (c)2011 
 * Company:     BOCO 
 * @author:     LiuChang 
 * @version:    1.0 
 * Create at:   Jul 15, 2011 2:48:04 PM 
 */
public class SubmodifyForm extends ActionForm{
	
	private String title;
	private String options;
	private String result;
	private int difficulty;
	private int specialty;       
	private int manufacturer;    //厂商
	private String comment;
	private String image; //图片
	
	private FormFile uploadFile;
	
	public FormFile getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(FormFile uploadFile) {
		this.uploadFile = uploadFile;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getOptions() {
		return options;
	}
	public void setOptions(String options) {
		this.options = options;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public int getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
	public int getSpecialty() {
		return specialty;
	}
	public void setSpecialty(int specialty) {
		this.specialty = specialty;
	}
	public int getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(int manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
}
