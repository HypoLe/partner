package com.boco.eoms.partner.assess.AssFactory.dao.hibernate;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.partner.assess.AssFactory.dao.IAssFactoryDao;
import com.boco.eoms.partner.assess.AssFactory.model.AssFactory;

public abstract class AssFactoryDaoHibernate extends BaseDaoHibernate implements IAssFactoryDao{

	
	public List getAllKpiFactory(final String templateId) {
		List all = getHibernateTemplate().executeFind(new HibernateCallback(){
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				String hql = "from AssFactory assFactory where assFactory.templateId = ?";
				Query q = session.createQuery(hql);
				q.setString(0, templateId);
				return q.list();
			}
		});
		return all;
	}
	
	public List getAllKpiFactoryByNodeId(final String nodeId) {
		List all = getHibernateTemplate().executeFind(new HibernateCallback(){
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				String hql = "from AssFactory assFactory where assFactory.nodeId = ?";
				Query q = session.createQuery(hql);
				q.setString(0, nodeId);
				return q.list();
			}
		});
		return all;
	}

	public void removeKpiFactory(AssFactory kpiFactory) {
		getHibernateTemplate().delete(kpiFactory);	
	}

	public void saveKpiFactory(AssFactory kpiFactory) {
		if (null == kpiFactory.getId() || "".equals(kpiFactory.getId())) {
			getHibernateTemplate().save(kpiFactory);
		} else {
			getHibernateTemplate().merge(kpiFactory);
		}	
	}

	public void updateKpiFactory(AssFactory kpiFactory) {
		getHibernateTemplate().update(kpiFactory);
	}

	public AssFactory getKpiFactory(String id) {
		AssFactory assFactory = (AssFactory) getHibernateTemplate().get(AssFactory.class,id);
		if (null == assFactory) {
			throw new ObjectRetrievalFailureException(AssFactory.class, id);
		}
		return assFactory;
	}
	
	public AssFactory getKpiFactoryByFactory(final String factory) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from AssFactory where factory = ? ";
				Query query = session.createQuery(queryStr);
				query.setString(0, factory);
				List list = query.list();
				if (list.iterator().hasNext()) {
					return (AssFactory) list.iterator().next();
				}
				return new AssFactory();
			}
		};
		return (AssFactory) getHibernateTemplate().execute(callback);
	}

	public List getPartnerDepts(final String where) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PartnerDept partnerDept where deleted <> '1'";
				if (where != null && where.length() > 0)
					queryStr += where;
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}	
}
