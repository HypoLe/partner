package com.boco.eoms.partner.inspect.model;

import java.sql.Clob;

import com.boco.eoms.base.model.BaseObject;

/**
 * <p>
 * Title:属地化巡检文件上传（Base64转码）
 * </p>
 * <p>
 * Description:属地化巡检文件上传（Base64转码）结构
 * </p>
 * <p>
 * Date:2012-8-20 上午11:02:55
 * </p>
 * 
 * @author lee
 * @version 1.0
 * 
 */

public class PnrInspectTaskFile extends BaseObject {

	/** 主键 */
	protected String id;
	/** 计划,资源,巡检项,id */
	protected String file_id;
	/** 计划(plan),资源(res),巡检项(planItem)*/
	protected String idType;
	/** 文件Base64数据 */
	private Clob fileData;
	/** 上传文件类型 */
	private String fileType;//png  or mp3   png图片文件,mp3音频文件
	/**上传时间*/
	private String createTime;
	/**修改时间*/
	private String modifyTime;
	//图片巡检类型
	private String photoType;//0,签退,1签到,2巡检图片,3服务器上传
	//图片路径
	private String path;//图片的路径


	


	public String getFile_id() {
		return file_id;
	}



	public void setFile_id(String file_id) {
		this.file_id = file_id;
	}



	public String getPhotoType() {
		return photoType;
	}



	public void setPhotoType(String photoType) {
		this.photoType = photoType;
	}



	public String getCreateTime() {
		return createTime;
	}



	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}



	public String getModifyTime() {
		return modifyTime;
	}



	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}







	public Clob getFileData() {
		return fileData;
	}



	public void setFileData(Clob fileData) {
		this.fileData = fileData;
	}



	public boolean equals(Object o) {
		return false;
	}



	public String getFileType() {
		return fileType;
	}



	public void setFileType(String fileType) {
		this.fileType = fileType;
	}







	public String getIdType() {
		return idType;
	}



	public void setIdType(String idType) {
		this.idType = idType;
	}



	public String getPath() {
		return path;
	}



	public void setPath(String path) {
		this.path = path;
	}


	



}
