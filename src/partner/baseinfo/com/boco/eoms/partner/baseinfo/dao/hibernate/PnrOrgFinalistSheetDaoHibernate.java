package com.boco.eoms.partner.baseinfo.dao.hibernate;


import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;
import com.boco.eoms.commons.system.dict.exceptions.DictDAOException;
import com.boco.eoms.deviceManagement.common.dao.CommonGenericDaoImpl;
import com.boco.eoms.partner.baseinfo.dao.IPnrOrgFinalistSheetDao;
import com.boco.eoms.partner.baseinfo.model.PnrOrgFinalistSheet;

public class PnrOrgFinalistSheetDaoHibernate extends CommonGenericDaoImpl<PnrOrgFinalistSheet, String>
		implements IPnrOrgFinalistSheetDao,ID2NameDAO  {
		public String id2Name(String id) throws DictDAOException {
			return "";
		}
		public List getPnrOrgFinalistSheet(final String where) {
			HibernateCallback callback = new HibernateCallback() {
				public Object doInHibernate(Session session)
						throws HibernateException {
					String queryStr = "from PnrOrgFinalistSheet  orgs  where 1=1 ";
					if (where != null && where.length() > 0)
						queryStr += where;
					Query query = session.createQuery(queryStr);
					return query.list();
				}
			};
			return (List) getHibernateTemplate().execute(callback);
		}
}