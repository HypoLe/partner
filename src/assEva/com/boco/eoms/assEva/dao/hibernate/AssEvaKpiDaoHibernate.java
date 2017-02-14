package com.boco.eoms.assEva.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;
import com.boco.eoms.assEva.dao.IAssEvaKpiDao;
import com.boco.eoms.assEva.model.AssEvaKpi;
import com.boco.eoms.assEva.util.AssEvaConstants;

public class AssEvaKpiDaoHibernate extends BaseDaoHibernate implements IAssEvaKpiDao,
		ID2NameDAO {

	public AssEvaKpi getKpi(String id) {
		AssEvaKpi kpi = (AssEvaKpi) getHibernateTemplate().get(AssEvaKpi.class, id);
		if (null == kpi) {
			throw new ObjectRetrievalFailureException(AssEvaKpi.class, id);
		}
		return kpi;
	}

	public AssEvaKpi getKpi(String id, String deleted) {
		AssEvaKpi kpi = new AssEvaKpi();
		String hql = " from AssEvaKpi kpi where kpi.id='" + id
				+ "' and kpi.deleted='" + deleted + "'";
		List list = getHibernateTemplate().find(hql);
		if (null != list && 0 < list.size()) {
			kpi = (AssEvaKpi) list.get(0);
		}
		return kpi;
	}

	public AssEvaKpi getKpiByNodeId(String nodeId) {
		AssEvaKpi kpi = new AssEvaKpi();
		String hql = " from AssEvaKpi kpi where kpi.nodeId='" + nodeId
				+ "' and kpi.deleted='" + AssEvaConstants.UNDELETED + "'";
		List list = getHibernateTemplate().find(hql);
		if (null != list && 0 < list.size()) {
			kpi = (AssEvaKpi) list.get(0);
		}
		return kpi;
	}

	public ArrayList listKpiOfType(String parentNodeId) {
		return null;
	}

	public void removeKpi(AssEvaKpi kpi) {
		kpi.setDeleted(AssEvaConstants.DELETED);
		saveKpi(kpi);
	}

	public void removeKpiOfType(final String parentNodeId) {
		final String hql = "update AssEvaKpi kpi set kpi.deleted='"
				+ AssEvaConstants.DELETED + "' where kpi.nodeId like '"
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

	public void saveKpi(AssEvaKpi kpi) {
		System.out.println("It's running AssEvaKpiDaoHibernate."); 
		if (kpi.getId() == null || "".equals(kpi.getId())) {
			getHibernateTemplate().save(kpi);
		} else {
			getHibernateTemplate().merge(kpi);
		}
	}
	
	public void saveNewKpi(AssEvaKpi kpi) {
		getHibernateTemplate().save(kpi);
	}

	public String id2Name(String id) {
		String kpiName = "";
		AssEvaKpi kpi = getKpi(id);
		if (null != kpi.getId() && !"".equals(kpi.getId())) {
			if (null != kpi.getKpiName() && !"".equals(kpi.getKpiName())) {
				kpiName = kpi.getKpiName();
			} else {
				kpiName = AssEvaConstants.NODE_NONAME;
			}
		} else {
			kpiName = AssEvaConstants.NODE_NONAME;
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
				String queryStr = "from AssEvaTree assEvaTree where assEvaTree.nodeName=:nodeName and parentNodeId=:parentNodeId";
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
