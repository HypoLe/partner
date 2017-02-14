package com.boco.eoms.partner.contact.mgr.impl;

import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.contact.dao.ContactTaskDao;
import com.boco.eoms.partner.contact.mgr.ContactTaskMgr;
import com.boco.eoms.partner.contact.model.ContactTask;
/**
 * <p>
 * Title:ContactTask信息
 * </p>
 * <p>
 * Description:ContactTask信息
 * </p>
 * <p>
 * Aug 24, 2012 10:53:19 AM
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */
public class ContactTaskMgrImpl extends CommonGenericServiceImpl<ContactTask> implements ContactTaskMgr {

	private ContactTaskDao contactTaskDao;
	public ContactTaskDao getContactTaskDao() {
		return contactTaskDao;
	}
	public void setContactTaskDao(ContactTaskDao contactTaskDao) {
		this.contactTaskDao = contactTaskDao;
		this.setCommonGenericDao(contactTaskDao);
	}
}

