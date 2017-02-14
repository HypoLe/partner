package com.boco.eoms.partner.assiEva.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;
import com.boco.eoms.partner.assiEva.dao.IAssiEvaKpiDao;
import com.boco.eoms.partner.assiEva.model.AssiEvaKpi;
import com.boco.eoms.partner.assiEva.util.AssiEvaConstants;

public class AssiEvaKpiDaoHibernate extends BaseDaoHibernate implements IAssiEvaKpiDao,
		ID2NameDAO {

	public AssiEvaKpi getKpi(String id) {
		AssiEvaKpi kpi = (AssiEvaKpi) getHibernateTemplate().get(AssiEvaKpi.class, id);
		if (null == kpi) {
			throw new ObjectRetrievalFailureException(AssiEvaKpi.class, id);
		}
		return kpi;
	}

	public AssiEvaKpi getKpi(String id, String deleted) {
		AssiEvaKpi kpi = new AssiEvaKpi();
		String hql = " from AssiEvaKpi kpi where kpi.id='" + id
				+ "' and kpi.deleted='" + deleted + "'";
		List list = getHibernateTemplate().find(hql);
		if (null != list && 0 < list.size()) {
			kpi = (AssiEvaKpi) list.get(0);
		}
		return kpi;
	}

	public AssiEvaKpi getKpiByNodeId(String nodeId) {
		AssiEvaKpi kpi = new AssiEvaKpi();
		String hql = " from AssiEvaKpi kpi where kpi.nodeId='" + nodeId
				+ "' and kpi.deleted='" + AssiEvaConstants.UNDELETED + "'";
		List list = getHibernateTemplate().find(hql);
		if (null != list && 0 < list.size()) {
			kpi = (AssiEvaKpi) list.get(0);
		}
		return kpi;
	}

	public ArrayList listKpiOfType(String parentNodeId) {
		return null;
	}

	public void removeKpi(AssiEvaKpi kpi) {
		kpi.setDeleted(AssiEvaConstants.DELETED);
		saveKpi(kpi);
	}

	public void removeKpiOfType(final String parentNodeId) {
		final String hql = "update AssiEvaKpi kpi set kpi.deleted='"
				+ AssiEvaConstants.DELETED + "' where kpi.nodeId like '"
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

	public void saveKpi(AssiEvaKpi kpi) {
		System.out.println("It's running AssiEvaKpiDaoHibernate."); 
		if (kpi.getId() == null || "".equals(kpi.getId())) {
			getHibernateTemplate().save(kpi);
		} else {
			getHibernateTemplate().merge(kpi);
		}
	}
	
	public void saveNewKpi(AssiEvaKpi kpi) {
		getHibernateTemplate().save(kpi);
	}

	public String id2Name(String id) {
		String kpiName = "";
		AssiEvaKpi kpi = getKpi(id);
		if (null != kpi.getId() && !"".equals(kpi.getId())) {
			if (null != kpi.getKpiName() && !"".equals(kpi.getKpiName())) {
				kpiName = kpi.getKpiName();
			} else {
				kpiName = AssiEvaConstants.NODE_NONAME;
			}
		} else {
			kpiName = AssiEvaConstants.NODE_NONAME;
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
				String queryStr = "from AssiEvaTree assiEvaTree where assiEvaTree.nodeName=:nodeName and parentNodeId=:parentNodeId";
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
