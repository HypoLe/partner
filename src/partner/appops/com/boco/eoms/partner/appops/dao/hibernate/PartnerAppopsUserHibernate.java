package com.boco.eoms.partner.appops.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;
import com.boco.eoms.commons.system.dict.exceptions.DictDAOException;
import com.boco.eoms.deviceManagement.common.dao.CommonGenericDaoImpl;
import com.boco.eoms.partner.appops.dao.PartnerAppopsUserDao;
import com.boco.eoms.partner.appops.model.IPnrPartnerAppOpsUser;
import com.boco.eoms.partner.baseinfo.dao.PartnerUserDao;

public class PartnerAppopsUserHibernate extends CommonGenericDaoImpl<IPnrPartnerAppOpsUser, String> implements PartnerAppopsUserDao{
	/**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.PartnerUserDao#getPartnerUsers(java.lang.Integer,java.lang.Integer,java.lang.String)
	 *      
	 */ 
	public Map getPartnerUsers(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from IPnrPartnerAppOpsUser partnerUser ";
				if (whereStr != null && whereStr.length() > 0)
					queryStr += whereStr + " and isDelete <> '1'" ;
				else queryStr += " where isDelete <> '1'" ;

				String queryCountStr = "select count(partnerUser.id) " + queryStr;

				int total = ((Integer) session.createQuery(queryCountStr)
						.iterate().next()).intValue();
				Query query = session.createQuery(queryStr);
				query
						.setFirstResult(pageSize.intValue()
								* (curPage.intValue()));
				query.setMaxResults(pageSize.intValue());
				List result = query.list();
				HashMap map = new HashMap();
				map.put("total", new Integer(total));
				map.put("result", result);
				return map;
			}
		};
		return (Map) getHibernateTemplate().execute(callback);
	}

	 /**
	 * 
	 *      
	 */
	public List getPartnerUsers(final String where) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from IPnrPartnerAppOpsUser partnerUser where partnerUser.isDelete <> '1' ";
				if (where != null && where.length() > 0)
					queryStr += where  ;
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}	
	
	public void saveAppopsUser(IPnrPartnerAppOpsUser appopsUser){
		if ((appopsUser.getId() == null) || (appopsUser.getId().equals("")))
			getHibernateTemplate().save(appopsUser);
		else
			getHibernateTemplate().merge(appopsUser);
	}
}
