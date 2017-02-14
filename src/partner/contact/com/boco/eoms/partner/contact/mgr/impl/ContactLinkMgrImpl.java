package com.boco.eoms.partner.contact.mgr.impl;

import java.util.List;

import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.contact.dao.ContactLinkDao;
import com.boco.eoms.partner.contact.mgr.ContactLinkMgr;
import com.boco.eoms.partner.contact.model.ContactLink;

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
public class ContactLinkMgrImpl extends CommonGenericServiceImpl<ContactLink>  implements ContactLinkMgr {

	private ContactLinkDao contactLinkDao;
	public ContactLinkDao getContactLinkDao() {
		return contactLinkDao;
	}
	public void setContactLinkDao(ContactLinkDao contactLinkDao) {
		this.contactLinkDao = contactLinkDao; 
		this.setCommonGenericDao(contactLinkDao);
	}
	
	public List findHql(String hql) {
		return this.contactLinkDao.findByHql(hql);
	}

}

