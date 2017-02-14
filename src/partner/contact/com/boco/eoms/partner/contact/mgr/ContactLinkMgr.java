package com.boco.eoms.partner.contact.mgr;

import java.util.List;

import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
import com.boco.eoms.partner.contact.model.ContactLink;
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
public interface ContactLinkMgr extends CommonGenericService<ContactLink> {
	public List findHql(String hql) ;
}
