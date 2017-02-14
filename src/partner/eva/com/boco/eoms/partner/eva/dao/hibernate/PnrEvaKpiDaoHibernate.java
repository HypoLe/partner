package com.boco.eoms.partner.eva.dao.hibernate;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;
import com.boco.eoms.partner.eva.dao.IPnrEvaKpiDao;
import com.boco.eoms.partner.eva.model.PnrEvaKpi;
import com.boco.eoms.partner.eva.util.PnrEvaConstants;

public class PnrEvaKpiDaoHibernate extends BaseDaoHibernate implements IPnrEvaKpiDao,
		ID2NameDAO {

	public PnrEvaKpi getKpi(String id) {
		PnrEvaKpi kpi = (PnrEvaKpi) getHibernateTemplate().get(PnrEvaKpi.class, id);
		if (null == kpi) {
			throw new ObjectRetrievalFailureException(PnrEvaKpi.class, id);
		}
		return kpi;
	}

	public PnrEvaKpi getKpi(String id, String deleted) {
		PnrEvaKpi kpi = new PnrEvaKpi();
		String hql = " from PnrEvaKpi kpi where kpi.id='" + id
				+ "' and kpi.deleted='" + deleted + "'";
		List list = getHibernateTemplate().find(hql);
		if (null != list && 0 < list.size()) {
			kpi = (PnrEvaKpi) list.get(0);
		}
		return kpi;
	}

	public void removeKpi(PnrEvaKpi kpi) {
		kpi.setDeleted(PnrEvaConstants.DELETED);
		saveKpi(kpi);
	}

	public void saveKpi(PnrEvaKpi kpi) {
		System.out.println("It's running PnrEvaKpiDaoHibernate."); 
		if (kpi.getId() == null || "".equals(kpi.getId())) {
			getHibernateTemplate().save(kpi);
		} else {
			getHibernateTemplate().merge(kpi);
		}
	}
	

	public String id2Name(String id) {
		String kpiName = "";
		PnrEvaKpi kpi = getKpi(id);
		if (null != kpi.getId() && !"".equals(kpi.getId())) {
			if (null != kpi.getKpiName() && !"".equals(kpi.getKpiName())) {
				kpiName = kpi.getKpiName();
			} else {
				kpiName = PnrEvaConstants.NODE_NONAME;
			}
		} else {
			kpiName = PnrEvaConstants.NODE_NONAME;
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
				String queryStr = "from PnrEvaTree evaTree where evaTree.nodeName=:nodeName and parentNodeId=:parentNodeId";
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
