package com.boco.eoms.partner.eva.dao.hibernate;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.partner.eva.dao.IPnrEvaKpiFacturyDao;
import com.boco.eoms.partner.eva.model.PnrEvaKpiFactury;

public class PnrEvaKpiFacturyDaoHibernate extends BaseDaoHibernate implements IPnrEvaKpiFacturyDao{

	
	public List getAllKpiFactury(final String templateId) {
		List all = getHibernateTemplate().executeFind(new HibernateCallback(){
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				String hql = "from PnrEvaKpiFactury pnrEvaKpiFactury where pnrEvaKpiFactury.templateId = ?";
				Query q = session.createQuery(hql);
				q.setString(0, templateId);
				return q.list();
			}
		});
		return all;
	}
	
	public List getAllKpiFacturyByNodeId(final String nodeId) {
		List all = getHibernateTemplate().executeFind(new HibernateCallback(){
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				String hql = "from PnrEvaKpiFactury pnrEvaKpiFactury where pnrEvaKpiFactury.nodeId = ?";
				Query q = session.createQuery(hql);
				q.setString(0, nodeId);
				return q.list();
			}
		});
		return all;
	}

	public void removeKpiFactury(PnrEvaKpiFactury kpiFactury) {
		getHibernateTemplate().delete(kpiFactury);	
	}

	public void saveKpiFactury(PnrEvaKpiFactury kpiFactury) {
		if (null == kpiFactury.getId() || "".equals(kpiFactury.getId())) {
			getHibernateTemplate().save(kpiFactury);
		} else {
			getHibernateTemplate().merge(kpiFactury);
		}	
	}

	public void updateKpiFactury(PnrEvaKpiFactury kpiFactury) {
		getHibernateTemplate().update(kpiFactury);
	}

	public PnrEvaKpiFactury getKpiFactury(String id) {
		// TODO Auto-generated method stub
		PnrEvaKpiFactury pnrEvaKpiFactury = (PnrEvaKpiFactury) getHibernateTemplate().get(PnrEvaKpiFactury.class,id);
		if (null == pnrEvaKpiFactury) {
			throw new ObjectRetrievalFailureException(PnrEvaKpiFactury.class, id);
		}
		return pnrEvaKpiFactury;
	}
	
	public PnrEvaKpiFactury getKpiFacturyByFactury(final String factury) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PnrEvaKpiFactury where factury = ? ";
				Query query = session.createQuery(queryStr);
				query.setString(0, factury);
				List list = query.list();
				if (list.iterator().hasNext()) {
					return (PnrEvaKpiFactury) list.iterator().next();
				}
				return new PnrEvaKpiFactury();
			}
		};
		return (PnrEvaKpiFactury) getHibernateTemplate().execute(callback);
	}

	
}
