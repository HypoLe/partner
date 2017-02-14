package com.boco.eoms.partner.home.mgr.impl;

import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.home.dao.LinkDao;
import com.boco.eoms.partner.home.mgr.LinkMgr;
import com.boco.eoms.partner.home.model.PublishLink;
/**
 * <p>
 * Title:Link信息
 * </p>
 * <p>
 * Description:Link信息
 * </p>
 * <p>
 * Aug 24, 2012 10:53:19 AM
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */
public class LinkMgrImpl extends CommonGenericServiceImpl<PublishLink> implements LinkMgr {

	private LinkDao publishDao;
	public LinkDao getLinkDao() {
		return publishDao;
	}
	public void setLinkDao(LinkDao publishDao) {
		this.publishDao = publishDao;
		this.setCommonGenericDao(publishDao);
	}
}

