package com.boco.eoms.partner.contact.mgr;

import java.util.List;

import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
import com.boco.eoms.partner.contact.model.ContactMain;
/**
 * <p>
 * Title:联系函基本信息
 * </p>
 * <p>
 * Description:联系函基本信息
 * </p>
 * <p>
 * Aug 24, 2012 10:53:19 AM
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */
public interface ContactMainMgr extends CommonGenericService<ContactMain> {
	
	public List getDataBySql(final Class entryClass,final String alias,String sql);
}
