package com.boco.activiti.partner.process.model;

import java.io.Serializable;
/**
 * 
   * @author wangyue
   * @ClassName: PnrPrecheckPhoto
   * @Version 版本 
   * @Copyright boco
   * @date Mar 17, 2015 2:06:19 PM
   * @description 预检预修工单和图片关联表
 */
public class PnrPrecheckPhoto implements Serializable{
	/**主键*/
	private String id;
	/**工单流程id*/
	private String processInstanceId;
	/**照片id*/
	private String photoId;
	/**照片名称*/
	private String photoName;
	//照片标识
	private String photoFlag;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getPhotoId() {
		return photoId;
	}

	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}

	public String getPhotoName() {
		return photoName;
	}

	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}

	public String getPhotoFlag() {
		return photoFlag;
	}

	public void setPhotoFlag(String photoFlag) {
		this.photoFlag = photoFlag;
	}
}
