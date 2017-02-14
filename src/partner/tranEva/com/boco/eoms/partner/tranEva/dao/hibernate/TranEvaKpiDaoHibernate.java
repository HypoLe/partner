package com.boco.eoms.partner.tranEva.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;
import com.boco.eoms.partner.tranEva.dao.ITranEvaKpiDao;
import com.boco.eoms.partner.tranEva.model.TranEvaKpi;
import com.boco.eoms.partner.tranEva.util.TranEvaConstants;

public class TranEvaKpiDaoHibernate extends BaseDaoHibernate implements ITranEvaKpiDao,
		ID2NameDAO {

	public TranEvaKpi getKpi(String id) {
		TranEvaKpi kpi = (TranEvaKpi) getHibernateTemplate().get(TranEvaKpi.class, id);
		if (null == kpi) {
			throw new ObjectRetrievalFailureException(TranEvaKpi.class, id);
		}
		return kpi;
	}

	public TranEvaKpi getKpi(String id, String deleted) {
		TranEvaKpi kpi = new TranEvaKpi();
		String hql = " from TranEvaKpi kpi where kpi.id='" + id
				+ "' and kpi.deleted='" + deleted + "'";
		List list = getHibernateTemplate().find(hql);
		if (null != list && 0 < list.size()) {
			kpi = (TranEvaKpi) list.get(0);
		}
		return kpi;
	}

	public TranEvaKpi getKpiByNodeId(String nodeId) {
		TranEvaKpi kpi = new TranEvaKpi();
		String hql = " from TranEvaKpi kpi where kpi.nodeId='" + nodeId
				+ "' and kpi.deleted='" + TranEvaConstants.UNDELETED + "'";
		List list = getHibernateTemplate().find(hql);
		if (null != list && 0 < list.size()) {
			kpi = (TranEvaKpi) list.get(0);
		}
		return kpi;
	}

	public ArrayList listKpiOfType(String parentNodeId) {
		return null;
	}

	public void removeKpi(TranEvaKpi kpi) {
		kpi.setDeleted(TranEvaConstants.DELETED);
		saveKpi(kpi);
	}

	public void removeKpiOfType(final String parentNodeId) {
		final String hql = "update TranEvaKpi kpi set kpi.deleted='"
				+ TranEvaConstants.DELETED + "' where kpi.nodeId like '"
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

	public void saveKpi(TranEvaKpi kpi) {
		System.out.println("It's running TranEvaKpiDaoHibernate."); 
		if (kpi.getId() == null || "".equals(kpi.getId())) {
			getHibernateTemplate().save(kpi);
		} else {
			getHibernateTemplate().merge(kpi);
		}
	}
	
	public void saveNewKpi(TranEvaKpi kpi) {
		getHibernateTemplate().save(kpi);
	}

	public String id2Name(String id) {
		String kpiName = "";
		TranEvaKpi kpi = getKpi(id);
		if (null != kpi.getId() && !"".equals(kpi.getId())) {
			if (null != kpi.getKpiName() && !"".equals(kpi.getKpiName())) {
				kpiName = kpi.getKpiName();
			} else {
				kpiName = TranEvaConstants.NODE_NONAME;
			}
		} else {
			kpiName = TranEvaConstants.NODE_NONAME;
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
				String queryStr = "from TranEvaTree tranEvaTree where tranEvaTree.nodeName=:nodeName and parentNodeId=:parentNodeId";
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
