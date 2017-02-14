package com.boco.eoms.partner.lineEva.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;
import com.boco.eoms.partner.lineEva.dao.ILineEvaKpiDao;
import com.boco.eoms.partner.lineEva.model.LineEvaKpi;
import com.boco.eoms.partner.lineEva.util.LineEvaConstants;

public class LineEvaKpiDaoHibernate extends BaseDaoHibernate implements ILineEvaKpiDao,
		ID2NameDAO {

	public LineEvaKpi getKpi(String id) {
		LineEvaKpi kpi = (LineEvaKpi) getHibernateTemplate().get(LineEvaKpi.class, id);
		if (null == kpi) {
			throw new ObjectRetrievalFailureException(LineEvaKpi.class, id);
		}
		return kpi;
	}

	public LineEvaKpi getKpi(String id, String deleted) {
		LineEvaKpi kpi = new LineEvaKpi();
		String hql = " from LineEvaKpi kpi where kpi.id='" + id
				+ "' and kpi.deleted='" + deleted + "'";
		List list = getHibernateTemplate().find(hql);
		if (null != list && 0 < list.size()) {
			kpi = (LineEvaKpi) list.get(0);
		}
		return kpi;
	}

	public LineEvaKpi getKpiByNodeId(String nodeId) {
		LineEvaKpi kpi = new LineEvaKpi();
		String hql = " from LineEvaKpi kpi where kpi.nodeId='" + nodeId
				+ "' and kpi.deleted='" + LineEvaConstants.UNDELETED + "'";
		List list = getHibernateTemplate().find(hql);
		if (null != list && 0 < list.size()) {
			kpi = (LineEvaKpi) list.get(0);
		}
		return kpi;
	}

	public ArrayList listKpiOfType(String parentNodeId) {
		return null;
	}

	public void removeKpi(LineEvaKpi kpi) {
		kpi.setDeleted(LineEvaConstants.DELETED);
		saveKpi(kpi);
	}

	public void removeKpiOfType(final String parentNodeId) {
		final String hql = "update LineEvaKpi kpi set kpi.deleted='"
				+ LineEvaConstants.DELETED + "' where kpi.nodeId like '"
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

	public void saveKpi(LineEvaKpi kpi) {
		System.out.println("It's running LineEvaKpiDaoHibernate."); 
		if (kpi.getId() == null || "".equals(kpi.getId())) {
			getHibernateTemplate().save(kpi);
		} else {
			getHibernateTemplate().merge(kpi);
		}
	}
	
	public void saveNewKpi(LineEvaKpi kpi) {
		getHibernateTemplate().save(kpi);
	}

	public String id2Name(String id) {
		String kpiName = "";
		LineEvaKpi kpi = getKpi(id);
		if (null != kpi.getId() && !"".equals(kpi.getId())) {
			if (null != kpi.getKpiName() && !"".equals(kpi.getKpiName())) {
				kpiName = kpi.getKpiName();
			} else {
				kpiName = LineEvaConstants.NODE_NONAME;
			}
		} else {
			kpiName = LineEvaConstants.NODE_NONAME;
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
				String queryStr = "from LineEvaTree lineEvaTree where lineEvaTree.nodeName=:nodeName and parentNodeId=:parentNodeId";
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
