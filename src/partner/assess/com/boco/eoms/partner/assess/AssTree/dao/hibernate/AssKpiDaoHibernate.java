package com.boco.eoms.partner.assess.AssTree.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.partner.assess.AssTree.dao.IAssKpiDao;
import com.boco.eoms.partner.assess.AssTree.model.AssKpi;
import com.boco.eoms.partner.assess.util.AssConstants;
import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;
/**
 * <p>
 * Title:考核指标dao操作基类
 * </p>
 * <p>
 * Description:考核指标dao操作基类
 * </p>
 * <p>
 * Date:Nov 26, 2010 5:52:06 PM
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
 * 
 */
public abstract class AssKpiDaoHibernate extends BaseDaoHibernate implements IAssKpiDao,
		ID2NameDAO {

	public AssKpi getKpi(String id) {
		AssKpi kpi = (AssKpi) getHibernateTemplate().get(AssKpi.class, id);
		if (null == kpi) {
			throw new ObjectRetrievalFailureException(AssKpi.class, id);
		}
		return kpi;
	}

	public AssKpi getKpi(String id, String deleted) {
		AssKpi kpi = new AssKpi();
		String hql = " from AssKpi kpi where kpi.id='" + id
				+ "' and kpi.deleted='" + deleted + "'";
		List list = getHibernateTemplate().find(hql);
		if (null != list && 0 < list.size()) {
			kpi = (AssKpi) list.get(0);
		}
		return kpi;
	}

	public AssKpi getKpiByNodeId(String nodeId) {
		AssKpi kpi = new AssKpi();
		String hql = " from AssKpi kpi where kpi.nodeId='" + nodeId
				+ "' and kpi.deleted='" + AssConstants.UNDELETED + "'";
		List list = getHibernateTemplate().find(hql);
		if (null != list && 0 < list.size()) {
			kpi = (AssKpi) list.get(0);
		}
		return kpi;
	}

	public ArrayList listKpiOfType(String parentNodeId) {
		return null;
	}

	public void removeKpi(AssKpi kpi) {
		kpi.setDeleted(AssConstants.DELETED);
		saveKpi(kpi);
	}

	public void removeKpiOfType(final String parentNodeId) {
		final String hql = "update AssKpi kpi set kpi.deleted='"
				+ AssConstants.DELETED + "' where kpi.nodeId like '"
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

	public void saveKpi(AssKpi kpi) {
		if (kpi.getId() == null || "".equals(kpi.getId())) {
			getHibernateTemplate().save(kpi);
		} else {
			getHibernateTemplate().merge(kpi);
		}
	}
	
	public void saveNewKpi(AssKpi kpi) {
		getHibernateTemplate().save(kpi);
	}

	public String id2Name(String id) {
		String kpiName = "";
		AssKpi kpi = getKpi(id);
		if (null != kpi.getId() && !"".equals(kpi.getId())) {
			if (null != kpi.getKpiName() && !"".equals(kpi.getKpiName())) {
				kpiName = kpi.getKpiName();
			} else {
				kpiName = AssConstants.NODE_NONAME;
			}
		} else {
			kpiName = AssConstants.NODE_NONAME;
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
				String queryStr = "from AssTree assTree where assTree.nodeName=:nodeName and parentNodeId=:parentNodeId";
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

	public List getAssKpis( final String whereStr ) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "select assKpi from AssKpi assKpi, AssTree assTree where assKpi.id = assTree.objectId ";
				if (whereStr != null && whereStr.length() > 0)
					queryStr += whereStr;
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}		
}
