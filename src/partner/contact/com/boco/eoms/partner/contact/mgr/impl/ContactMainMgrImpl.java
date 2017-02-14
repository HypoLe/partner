package com.boco.eoms.partner.contact.mgr.impl;

import java.util.List;

import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.contact.dao.ContactMainDao;
import com.boco.eoms.partner.contact.mgr.ContactMainMgr;
import com.boco.eoms.partner.contact.model.ContactMain;
/**
 * <p>
 * Title:联系函 基本信息
 * </p>
 * <p>
 * Description:联系函 基本信息
 * </p>
 * <p>
 * Aug 24, 2012 10:53:19 AM
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */
public class ContactMainMgrImpl extends CommonGenericServiceImpl<ContactMain> implements ContactMainMgr {

	private ContactMainDao contactMainDao;
	public ContactMainDao getContactMainDao() {
		return contactMainDao;
	}
	public void setContactMainDao(ContactMainDao contactMainDao) {
		this.contactMainDao = contactMainDao;
		this.setCommonGenericDao(contactMainDao);
	}
	
	public List getDataBySql(final Class entryClass,final String alias,String sql){
		return contactMainDao.getDataBySql(entryClass, alias, sql);
	}
}

