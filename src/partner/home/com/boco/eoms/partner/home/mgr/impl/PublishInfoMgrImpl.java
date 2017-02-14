package com.boco.eoms.partner.home.mgr.impl;

import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.home.dao.PublishInfoDao;
import com.boco.eoms.partner.home.mgr.PublishInfoMgr;
import com.boco.eoms.partner.home.model.PublishInfo;
/**
 * <p>
 * Title:公告基本信息
 * </p>
 * <p>
 * Description:公告基本信息
 * </p>
 * <p>
 * Aug 24, 2012 10:53:19 AM
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */
public class PublishInfoMgrImpl extends CommonGenericServiceImpl<PublishInfo> implements PublishInfoMgr {

	private PublishInfoDao publishInfoDao;
	public PublishInfoDao getPublishInfoDao() {
		return publishInfoDao;
	}
	public void setPublishInfoDao(PublishInfoDao publishInfoDao) {
		this.publishInfoDao = publishInfoDao;
		this.setCommonGenericDao(publishInfoDao);
	}
}

