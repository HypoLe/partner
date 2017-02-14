package com.boco.eoms.partner.assess.AssAlgorithm.dao.hibernate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;
import com.boco.eoms.commons.system.dict.exceptions.DictDAOException;
import com.boco.eoms.partner.assess.AssAlgorithm.dao.IAssKpiConfigDao;
import com.boco.eoms.partner.assess.AssAlgorithm.model.AssKpiConfig;
import com.boco.eoms.partner.assess.AssAlgorithm.util.AssKpiConfigConstants;


/**
 * <p>
 * Title:指标打分配置 dao的hibernate实现
 * </p>
 * <p>
 * Description:指标打分配置
 * </p>
 * <p>
 * Tue Nov 16 09:08:10 CST 2010
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public abstract class AssKpiConfigDaoHibernate extends BaseDaoHibernate implements IAssKpiConfigDao,
		ID2NameDAO {
    
	public AssKpiConfig getAssKpiConfig(final String id) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from AssKpiConfig assKpiConfig where assKpiConfig.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (AssKpiConfig) result.iterator().next();
				} else {
					return new AssKpiConfig();
				}
			}
		};
		return (AssKpiConfig) getHibernateTemplate().execute(callback);
	}
    
	public void saveAssKpiConfig(final AssKpiConfig assKpiConfig) {
		if ((assKpiConfig.getId() == null) || (assKpiConfig.getId().equals("")))
			getHibernateTemplate().save(assKpiConfig);
		else
			getHibernateTemplate().saveOrUpdate(assKpiConfig);
	}
	
	public String id2Name(String id) throws DictDAOException {
		AssKpiConfig assKpiConfig = this.getAssKpiConfigByNodeId(id);
		if(assKpiConfig==null){
			return "";
		}
		return "";
	}
    
    public AssKpiConfig getAssKpiConfigByNodeId(final String nodeId) {
    	AssKpiConfig assKpiConfig = new AssKpiConfig();
		String hql = " from AssKpiConfig assKpiConfig where assKpiConfig.nodeId='" + nodeId + "'";
		List list = getHibernateTemplate().find(hql);
		if (list.iterator().hasNext()) {
			assKpiConfig = (AssKpiConfig) list.iterator().next();
		}
		return assKpiConfig;
    }
    
    public void removeAssKpiConfigByNodeId(final String nodeId) {
    	final String hql = "delete from AssKpiConfig assKpiConfig where assKpiConfig.nodeId like '"
				+ nodeId + "%'";
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
    
    public List getNextLevelAssKpiConfigs(final String parentNodeId) {
    	String hql = " from AssKpiConfig assKpiConfig where assKpiConfig.parentNodeId='"
				+ parentNodeId + "'";
		hql += " order by assKpiConfig.nodeId";
		return getHibernateTemplate().find(hql);
    }
    
    public String getUsableNodeId(final String parentNodeId, final int length) {
    	String minNodeId = getUsableMinNodeId(parentNodeId, length);
		if (null == minNodeId || "".equals(minNodeId)) {
			minNodeId = AssKpiConfigConstants.NODEID_DEFAULT_VALUE;
		}
		return minNodeId;
    }
    
    private String getUsableMinNodeId(final String parentNodeId, final int len) {
		String minUsableNodeId = "";
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String hql = " select distinct(nodeId) from AssKpiConfig assKpiConfig where assKpiConfig.nodeId like '"
						+ parentNodeId + "%' and length(assKpiConfig.nodeId)='" + len + "'";
				Query query = session.createQuery(hql);
				return query.list();
			}
		};
		List list = (List) getHibernateTemplate().execute(callback);
		HashMap hm = new HashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			String nodeId = it.next().toString();
			hm.put(nodeId, nodeId);
		}
		// 防止下面的while出现死循环,如果所有Id全部被占用则返回一个空值
		if (hm.size() >= Integer.parseInt(AssKpiConfigConstants.NODEID_IF_MAXID)) {
			return minUsableNodeId;
		}
		long nodeIdVar = Long.valueOf(parentNodeId + AssKpiConfigConstants.NODEID_NOSON)
				.longValue();
		while (null != hm.get(String.valueOf(nodeIdVar))) {
			nodeIdVar = nodeIdVar + 1;
		}
		return String.valueOf(nodeIdVar);
	}
	
	public List getChildNodes(final String parentNodeId) {
		String hql = " from AssKpiConfig assKpiConfig where assKpiConfig.parentNodeId like '"
				+ parentNodeId + "%'";
		hql += " order by assKpiConfig.nodeId";
		return (List) getHibernateTemplate().find(hql);
	}
	
	public List getAssKpiConfigs( final String whereStr ) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from AssKpiConfig assKpiConfig ";
				if (whereStr != null && whereStr.length() > 0)
					queryStr += whereStr;
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}		
	
	public List getConfigsByKpiId( final String kpiId, final String nodeType) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				StringBuffer queryStr = new StringBuffer();
				queryStr.append("from AssKpiConfig config where 1=1 ");
				if (kpiId != null && !kpiId.equals("")){
					queryStr.append(" and config.kpiId ='");
					queryStr.append(kpiId);
					queryStr.append("' ");
				}
				if (nodeType != null && !nodeType.equals("")){
					queryStr.append(" and config.nodeType ='");
					queryStr.append(nodeType);
					queryStr.append("' ");
				}
				queryStr.append(" order by config.nodeId ");
				Query query = session.createQuery(queryStr.toString());
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}	
}