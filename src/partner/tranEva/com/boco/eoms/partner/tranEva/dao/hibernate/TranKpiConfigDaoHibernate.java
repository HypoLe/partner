package com.boco.eoms.partner.tranEva.dao.hibernate;

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
import com.boco.eoms.partner.tranEva.dao.ITranKpiConfigDao;
import com.boco.eoms.partner.tranEva.model.TranKpiConfig;
import com.boco.eoms.partner.tranEva.util.TranKpiConfigConstants;


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
public class TranKpiConfigDaoHibernate extends BaseDaoHibernate implements ITranKpiConfigDao,
		ID2NameDAO {
    
    /**
	 *
	 * @see com.boco.eoms.partner.traneva.ITranKpiConfigDao#getTranKpiConfig(java.lang.String)
	 *
	 */
	public TranKpiConfig getTranKpiConfig(final String id) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from TranKpiConfig tranKpiConfig where tranKpiConfig.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (TranKpiConfig) result.iterator().next();
				} else {
					return new TranKpiConfig();
				}
			}
		};
		return (TranKpiConfig) getHibernateTemplate().execute(callback);
	}
    
	/**
	 * 
	 * @see com.boco.eoms.partner.traneva.ITranKpiConfigDao#saveTranKpiConfigs(com.boco.eoms.partner.traneva.TranKpiConfig)
	 *      
	 */
	public void saveTranKpiConfig(final TranKpiConfig tranKpiConfig) {
		if ((tranKpiConfig.getId() == null) || (tranKpiConfig.getId().equals("")))
			getHibernateTemplate().save(tranKpiConfig);
		else
			getHibernateTemplate().saveOrUpdate(tranKpiConfig);
	}
	
	/**
	 * 
	 * @see com.boco.eoms.commons.system.dict.dao.ID2NameDAO#id2Name(java.lang.String)
	 *      
	 */
	public String id2Name(String id) throws DictDAOException {
		TranKpiConfig tranKpiConfig = this.getTranKpiConfigByNodeId(id);
		if(tranKpiConfig==null){
			return "";
		}
		return "";
	}
    
    public TranKpiConfig getTranKpiConfigByNodeId(final String nodeId) {
    	TranKpiConfig tranKpiConfig = new TranKpiConfig();
		String hql = " from TranKpiConfig tranKpiConfig where tranKpiConfig.nodeId='" + nodeId + "'";
		List list = getHibernateTemplate().find(hql);
		if (list.iterator().hasNext()) {
			tranKpiConfig = (TranKpiConfig) list.iterator().next();
		}
		return tranKpiConfig;
    }
    
    public void removeTranKpiConfigByNodeId(final String nodeId) {
    	final String hql = "delete from TranKpiConfig tranKpiConfig where tranKpiConfig.nodeId like '"
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
    
    public List getNextLevelTranKpiConfigs(final String parentNodeId) {
    	String hql = " from TranKpiConfig tranKpiConfig where tranKpiConfig.parentNodeId='"
				+ parentNodeId + "'";
		hql += " order by tranKpiConfig.nodeId";
		return getHibernateTemplate().find(hql);
    }
    
    public String getUsableNodeId(final String parentNodeId, final int length) {
    	String minNodeId = getUsableMinNodeId(parentNodeId, length);
		if (null == minNodeId || "".equals(minNodeId)) {
			minNodeId = TranKpiConfigConstants.NODEID_DEFAULT_VALUE;
		}
		return minNodeId;
    }
    
    private String getUsableMinNodeId(final String parentNodeId, final int len) {
		String minUsableNodeId = "";
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String hql = " select distinct(nodeId) from TranKpiConfig tranKpiConfig where tranKpiConfig.nodeId like '"
						+ parentNodeId + "%' and length(tranKpiConfig.nodeId)='" + len + "'";
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
		if (hm.size() >= Integer.parseInt(TranKpiConfigConstants.NODEID_IF_MAXID)) {
			return minUsableNodeId;
		}
		long nodeIdVar = Long.valueOf(parentNodeId + TranKpiConfigConstants.NODEID_NOSON)
				.longValue();
		while (null != hm.get(String.valueOf(nodeIdVar))) {
			nodeIdVar = nodeIdVar + 1;
		}
		return String.valueOf(nodeIdVar);
	}
	
	public List getChildNodes(final String parentNodeId) {
		String hql = " from TranKpiConfig tranKpiConfig where tranKpiConfig.parentNodeId like '"
				+ parentNodeId + "%'";
		hql += " order by tranKpiConfig.nodeId";
		return (List) getHibernateTemplate().find(hql);
	}
	
	public List getTranKpiConfigs( final String whereStr ) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from TranKpiConfig tranKpiConfig ";
				if (whereStr != null && whereStr.length() > 0)
					queryStr += whereStr;
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}		

}