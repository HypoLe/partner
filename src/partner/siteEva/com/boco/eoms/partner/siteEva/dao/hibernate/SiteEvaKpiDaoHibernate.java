package com.boco.eoms.partner.siteEva.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;
import com.boco.eoms.partner.siteEva.dao.ISiteEvaKpiDao;
import com.boco.eoms.partner.siteEva.model.SiteEvaKpi;
import com.boco.eoms.partner.siteEva.util.SiteEvaConstants;

public class SiteEvaKpiDaoHibernate extends BaseDaoHibernate implements ISiteEvaKpiDao,
		ID2NameDAO {

	public SiteEvaKpi getKpi(String id) {
		SiteEvaKpi kpi = (SiteEvaKpi) getHibernateTemplate().get(SiteEvaKpi.class, id);
		if (null == kpi) {
			throw new ObjectRetrievalFailureException(SiteEvaKpi.class, id);
		}
		return kpi;
	} 

	public SiteEvaKpi getKpi(String id, String deleted) {
		SiteEvaKpi kpi = new SiteEvaKpi();
		String hql = " from SiteEvaKpi kpi where kpi.id='" + id
				+ "' and kpi.deleted='" + deleted + "'";
		List list = getHibernateTemplate().find(hql);
		if (null != list && 0 < list.size()) {
			kpi = (SiteEvaKpi) list.get(0);
		}
		return kpi;
	}

	public SiteEvaKpi getKpiByNodeId(String nodeId) {
		SiteEvaKpi kpi = new SiteEvaKpi();
		String hql = " from SiteEvaKpi kpi where kpi.nodeId='" + nodeId
				+ "' and kpi.deleted='" + SiteEvaConstants.UNDELETED + "'";
		List list = getHibernateTemplate().find(hql);
		if (null != list && 0 < list.size()) {
			kpi = (SiteEvaKpi) list.get(0);
		}
		return kpi;
	}

	public ArrayList listKpiOfType(String parentNodeId) {
		return null;
	}

	public void removeKpi(SiteEvaKpi kpi) {
		kpi.setDeleted(SiteEvaConstants.DELETED);
		saveKpi(kpi);
	}

	public void removeKpiOfType(final String parentNodeId) {
		final String hql = "update SiteEvaKpi kpi set kpi.deleted='"
				+ SiteEvaConstants.DELETED + "' where kpi.nodeId like '"
				+ parentNodeId + "%'";
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				Query query = session.createQuery(hql);
				query.executeUpdate();
				return null;
			}
		};
		getHibernateTemplate().execute(callback);
	}

	public void saveKpi(SiteEvaKpi kpi) {
		System.out.println("It's running SiteEvaKpiDaoHibernate."); 
		if (kpi.getId() == null || "".equals(kpi.getId())) {
			getHibernateTemplate().save(kpi);
		} else {
			getHibernateTemplate().merge(kpi);
		}
	}
	
	public void saveNewKpi(SiteEvaKpi kpi) {
		getHibernateTemplate().save(kpi);
	}

	public String id2Name(String id) {
		String kpiName = "";
		SiteEvaKpi kpi = getKpi(id);
		if (null != kpi.getId() && !"".equals(kpi.getId())) {
			if (null != kpi.getKpiName() && !"".equals(kpi.getKpiName())) {
				kpiName = kpi.getKpiName();
			} else {
				kpiName = SiteEvaConstants.NODE_NONAME;
			}
		} else {
			kpiName = SiteEvaConstants.NODE_NONAME;
		}
		return kpiName;
	}
	
	/**
	 * 判断指标名称是否唯一
	 * 
	 *      
	 */
	public Boolean isunique(final String kpiName,final String parentNodeId) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from SiteEvaTree siteEvaTree where siteEvaTree.nodeName=:nodeName and parentNodeId=:parentNodeId";
				Query query = session.createQuery(queryStr);
				query.setString("nodeName", kpiName);
				query.setString("parentNodeId", parentNodeId);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return Boolean.valueOf(false);
				} else {
					return Boolean.valueOf(true);
				}
			}
		};
		return (Boolean) getHibernateTemplate().execute(callback);
	}

}
