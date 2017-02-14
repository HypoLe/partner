package com.boco.eoms.partner.inspect.model;

import com.boco.eoms.base.model.BaseObject;


/**
 * 
 * Description:  巡检模板
 * Copyright:   Copyright (c)2009 
 * Company:     boco 
 * @author:     lee 
 * @version:    1.0 
 * Create at:   Jul 11, 2012 3:31:36 PM
 */

public class InspectTemplate extends BaseObject {

	private static final long serialVersionUID = 1L;


	/**
     *
     * 模版主键
     *
     */
    java.lang.String id;


    /**
     *
     * 模版名称
     *
     */
    java.lang.String templateName;

    /**
     *
     * 制作单位
     *
     */
    java.lang.String dept;

    /**
     *
     * 专业
     *
     */
    java.lang.String specialty;
    
    /**
     *
     * 模版概述
     *
     */
    java.lang.String content;
    /**
     *
     * 资源类型
     *
     */
    java.lang.String resType;
    java.lang.String addTime;
    java.lang.String addUser;
	private Integer status;           //是否可用(0否 1是)
	private Integer itemNum;         //模板项数

    public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	public java.lang.String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(java.lang.String templateName) {
		this.templateName = templateName;
	}

	public java.lang.String getDept() {
		return dept;
	}

	public void setDept(java.lang.String dept) {
		this.dept = dept;
	}

	public java.lang.String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(java.lang.String specialty) {
		this.specialty = specialty;
	}

	public java.lang.String getContent() {
		return content;
	}

	public void setContent(java.lang.String content) {
		this.content = content;
	}

	


	public java.lang.String getAddTime() {
		return addTime;
	}

	public void setAddTime(java.lang.String addTime) {
		this.addTime = addTime;
	}

	public java.lang.String getAddUser() {
		return addUser;
	}

	public void setAddUser(java.lang.String addUser) {
		this.addUser = addUser;
	}

	public boolean equals(Object o) {
        if( o instanceof InspectTemplate ) {
        	InspectTemplate inspectTemplate=(InspectTemplate)o;
            if (this.id != null || this.id.equals(inspectTemplate.getId())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

	public java.lang.String getResType() {
		return resType;
	}

	public void setResType(java.lang.String resType) {
		this.resType = resType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getItemNum() {
		return itemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

}