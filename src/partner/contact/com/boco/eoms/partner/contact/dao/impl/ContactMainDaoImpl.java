package com.boco.eoms.partner.contact.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.boco.eoms.deviceManagement.common.dao.CommonGenericDaoImpl;
import com.boco.eoms.partner.contact.dao.ContactMainDao;
import com.boco.eoms.partner.contact.model.ContactMain;
/**
 * <p>
 * Title: 联系函 基本信息
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
public class ContactMainDaoImpl extends CommonGenericDaoImpl<ContactMain,String> implements ContactMainDao {
	public List getDataBySql(final Class entryClass,final String alias,String sql){
		 Session session=super.getSession();
		 SQLQuery query=session.createSQLQuery(sql);
		 query.addEntity(alias,entryClass);
		 return query.list();
	}
}
