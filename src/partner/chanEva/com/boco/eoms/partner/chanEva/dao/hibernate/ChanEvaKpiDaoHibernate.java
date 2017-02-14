package com.boco.eoms.partner.chanEva.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;
import com.boco.eoms.partner.chanEva.dao.IChanEvaKpiDao;
import com.boco.eoms.partner.chanEva.model.ChanEvaKpi;
import com.boco.eoms.partner.chanEva.util.ChanEvaConstants;

public class ChanEvaKpiDaoHibernate extends BaseDaoHibernate implements IChanEvaKpiDao,
		ID2NameDAO {

	public ChanEvaKpi getKpi(String id) {
		ChanEvaKpi kpi = (ChanEvaKpi) getHibernateTemplate().get(ChanEvaKpi.class, id);
		if (null == kpi) {
			throw new ObjectRetrievalFailureException(ChanEvaKpi.class, id);
		}
		return kpi;
	}

	public ChanEvaKpi getKpi(String id, String deleted) {
		ChanEvaKpi kpi = new ChanEvaKpi();
		String hql = " from ChanEvaKpi kpi where kpi.id='" + id
				+ "' and kpi.deleted='" + deleted + "'";
		List list = getHibernateTemplate().find(hql);
		if (null != list && 0 < list.size()) {
			kpi = (ChanEvaKpi) list.get(0);
		}
		return kpi;
	}

	public ChanEvaKpi getKpiByNodeId(String nodeId) {
		ChanEvaKpi kpi = new ChanEvaKpi();
		String hql = " from ChanEvaKpi kpi where kpi.nodeId='" + nodeId
				+ "' and kpi.deleted='" + ChanEvaConstants.UNDELETED + "'";
		List list = getHibernateTemplate().find(hql);
		if (null != list && 0 < list.size()) {
			kpi = (ChanEvaKpi) list.get(0);
		}
		return kpi;
	}

	public ArrayList listKpiOfType(String parentNodeId) {
		return null;
	}

	public void removeKpi(ChanEvaKpi kpi) {
		kpi.setDeleted(ChanEvaConstants.DELETED);
		saveKpi(kpi);
	}

	public void removeKpiOfType(final String parentNodeId) {
		final String hql = "update ChanEvaKpi kpi set kpi.deleted='"
				+ ChanEvaConstants.DELETED + "' where kpi.nodeId like '"
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

	public void saveKpi(ChanEvaKpi kpi) {
		System.out.println("It's running ChanEvaKpiDaoHibernate."); 
		if (kpi.getId() == null || "".equals(kpi.getId())) {
			getHibernateTemplate().save(kpi);
		} else {
			getHibernateTemplate().merge(kpi);
		}
	}
	
	public void saveNewKpi(ChanEvaKpi kpi) {
		getHibernateTemplate().save(kpi);
	}

	public String id2Name(String id) {
		String kpiName = "";
		ChanEvaKpi kpi = getKpi(id);
		if (null != kpi.getId() && !"".equals(kpi.getId())) {
			if (null != kpi.getKpiName() && !"".equals(kpi.getKpiName())) {
				kpiName = kpi.getKpiName();
			} else {
				kpiName = ChanEvaConstants.NODE_NONAME;
			}
		} else {
			kpiName = ChanEvaConstants.NODE_NONAME;
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
				String queryStr = "from ChanEvaTree chanEvaTree where chanEvaTree.nodeName=:nodeName and parentNodeId=:parentNodeId";
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
