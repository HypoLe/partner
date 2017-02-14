package com.boco.eoms.partner.contact.dao;

import java.util.List;

import com.boco.eoms.deviceManagement.common.dao.CommonGenericDao;
import com.boco.eoms.partner.contact.model.ContactMain;
/**
 * <p>
 * Title:联系函 基本信息
 * </p>
 * <p>
 * Description:联系函 基本信息
 * </p>
 * <p>
 * Oct 24, 2012 10:53:19 AM
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */
public interface ContactMainDao extends CommonGenericDao<ContactMain,String> {
	public List getDataBySql(final Class entryClass,final String alias,String sql);
}
