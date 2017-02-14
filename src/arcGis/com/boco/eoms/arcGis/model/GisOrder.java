package com.boco.eoms.arcGis.model;

import java.sql.Clob;

public class GisOrder {
	private String id;
	private String X;
	private String Y;
	private String USERNAME;
	private String PHONE;
	private String IMAGE_PATH;
	private String ORDERID;
	private Clob   IMAGE_FILE;
	private String IMAGE_FILE_NAME;
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getX() {
		return X;
	}
	public void setX(String x) {
		X = x;
	}
	public String getY() {
		return Y;
	}
	public void setY(String y) {
		Y = y;
	}
	public String getUSERNAME() {
		return USERNAME;
	}
	public void setUSERNAME(String username) {
		USERNAME = username;
	}
	public String getPHONE() {
		return PHONE;
	}
	public void setPHONE(String phone) {
		PHONE = phone;
	}
	public String getIMAGE_PATH() {
		return IMAGE_PATH;
	}
	public void setIMAGE_PATH(String image_path) {
		IMAGE_PATH = image_path;
	}
	public String getORDERID() {
		return ORDERID;
	}
	public void setORDERID(String orderid) {
		ORDERID = orderid;
	}
	public Clob getIMAGE_FILE() {
		return IMAGE_FILE;
	}
	public void setIMAGE_FILE(Clob image_file) {
		IMAGE_FILE = image_file;
	}
	public String getIMAGE_FILE_NAME() {
		return IMAGE_FILE_NAME;
	}
	public void setIMAGE_FILE_NAME(String image_file_name) {
		IMAGE_FILE_NAME = image_file_name;
	}
	
	
	
}
