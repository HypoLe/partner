package com.boco.eoms.partner.sheet.commontask.model;

import com.boco.eoms.sheet.base.model.BaseLink;

/** 
 * Description: 通用任务工单LINK
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Jul 23, 2012 3:26:12 PM 
 */
 public class PnrCommonTaskLink extends BaseLink {
	
	private static final long serialVersionUID = 1L;

	/**
 	* 保存派发对象
	*/
	private java.lang.String linkSendObject;
	
	/** 归档回退次数 */
	private Integer mainBackTime;
	
	/**
 	* 还需要加一个响应时间（分钟）
	*/  
	
	public java.lang.String getLinkSendObject() {
		return linkSendObject;
	}

	public void setLinkSendObject(java.lang.String linkSendObject) {
		this.linkSendObject = linkSendObject;
	}

	public Integer getMainBackTime() {
		return mainBackTime;
	}

	public void setMainBackTime(Integer mainBackTime) {
		this.mainBackTime = mainBackTime;
	}
	
	
}