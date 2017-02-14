package com.boco.activiti.partner.process.service.impl;

import com.boco.activiti.partner.process.dao.IJunctionBoxCheckPhotoDao;
import com.boco.activiti.partner.process.model.JunctionBoxCheckPhoto;
import com.boco.activiti.partner.process.service.IJunctionBoxCheckPhotoService;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;

public class JunctionBoxCheckPhotoServiceImpl extends
		CommonGenericServiceImpl<JunctionBoxCheckPhoto> implements
		IJunctionBoxCheckPhotoService {

	private IJunctionBoxCheckPhotoDao junctionBoxCheckPhotoDao;

	public IJunctionBoxCheckPhotoDao getJunctionBoxCheckPhotoDao() {
		return junctionBoxCheckPhotoDao;
	}

	public void setJunctionBoxCheckPhotoDao(
			IJunctionBoxCheckPhotoDao junctionBoxCheckPhotoDao) {
		this.junctionBoxCheckPhotoDao = junctionBoxCheckPhotoDao;
		this.setCommonGenericDao(junctionBoxCheckPhotoDao);
	}

}