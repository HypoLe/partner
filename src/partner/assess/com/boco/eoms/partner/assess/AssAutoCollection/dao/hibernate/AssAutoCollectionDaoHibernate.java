package com.boco.eoms.partner.assess.AssAutoCollection.dao.hibernate;

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
import com.boco.eoms.partner.assess.AssAutoCollection.dao.AssAutoCollectionDao;
import com.boco.eoms.partner.assess.AssAutoCollection.model.AssAutoCollection;
import com.boco.eoms.partner.assess.AssAutoCollection.util.AssAutoCollectionConstants;


/**
 * <p>
 * Title:kpi自动采集 dao的hibernate实现
 * </p>
 * <p>
 * Description:kpi自动采集
 * </p>
 * <p>
 * Tue Mar 29 17:39:54 CST 2011
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public class AssAutoCollectionDaoHibernate extends BaseDaoHibernate implements AssAutoCollectionDao,
		ID2NameDAO {
    
    /**
	 *
	 * @see com.boco.eoms.parnter.asseva.assautocollection.AssAutoCollectionDao#getAssAutoCollection(java.lang.String)
	 *
	 */
	public AssAutoCollection getAssAutoCollection(final String id) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from AssAutoCollection assAutoCollection where assAutoCollection.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (AssAutoCollection) result.iterator().next();
				} else {
					return new AssAutoCollection();
				}
			}
		};
		return (AssAutoCollection) getHibernateTemplate().execute(callback);
	}
    
	/**
	 * 
	 * @see com.boco.eoms.parnter.asseva.assautocollection.AssAutoCollectionDao#saveAssAutoCollections(com.boco.eoms.parnter.asseva.assautocollection.AssAutoCollection)
	 *      
	 */
	public void saveAssAutoCollection(final AssAutoCollection assAutoCollection) {
		if ((assAutoCollection.getId() == null) || (assAutoCollection.getId().equals("")))
			getHibernateTemplate().save(assAutoCollection);
		else
			getHibernateTemplate().saveOrUpdate(assAutoCollection);
	}
	
	/**
	 * 
	 * @see com.boco.eoms.commons.system.dict.dao.ID2NameDAO#id2Name(java.lang.String)
	 *      
	 */
	public String id2Name(String id) throws DictDAOException {
		AssAutoCollection assAutoCollection = this.getAssAutoCollectionByNodeId(id);
		if(assAutoCollection==null){
			return "";
		}
		return assAutoCollection.getNodeName();
	}
    
    public AssAutoCollection getAssAutoCollectionByNodeId(final String nodeId) {
    	AssAutoCollection assAutoCollection = new AssAutoCollection();
		String hql = " from AssAutoCollection assAutoCollection where assAutoCollection.nodeId='" + nodeId + "'";
		List list = getHibernateTemplate().find(hql);
		if (list.iterator().hasNext()) {
			assAutoCollection = (AssAutoCollection) list.iterator().next();
		}
		return assAutoCollection;
    }
    
    public void removeAssAutoCollectionByNodeId(final String nodeId) {
    	final String hql = "delete from AssAutoCollection assAutoCollection where assAutoCollection.nodeId like '"
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
    
    public List getNextLevelAssAutoCollections(final String parentNodeId) {
    	String hql = " from AssAutoCollection assAutoCollection where assAutoCollection.parentNodeId='"
				+ parentNodeId + "'";
		hql += " order by assAutoCollection.nodeId";
		return getHibernateTemplate().find(hql);
    }
    
    public String getUsableNodeId(final String parentNodeId, final int length) {
    	String minNodeId = getUsableMinNodeId(parentNodeId, length);
		if (null == minNodeId || "".equals(minNodeId)) {
			minNodeId = AssAutoCollectionConstants.NODEID_DEFAULT_VALUE;
		}
		return minNodeId;
    }
    
    private String getUsableMinNodeId(final String parentNodeId, final int len) {
		String minUsableNodeId = "";
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String hql = " select distinct(nodeId) from AssAutoCollection assAutoCollection where assAutoCollection.nodeId like '"
						+ parentNodeId + "%' and length(assAutoCollection.nodeId)='" + len + "'";
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
		if (hm.size() >= Integer.parseInt(AssAutoCollectionConstants.NODEID_IF_MAXID)) {
			return minUsableNodeId;
		}
		long nodeIdVar = Long.valueOf(parentNodeId + AssAutoCollectionConstants.NODEID_NOSON)
				.longValue();
		while (null != hm.get(String.valueOf(nodeIdVar))) {
			nodeIdVar = nodeIdVar + 1;
		}
		return String.valueOf(nodeIdVar);
	}
	
	public List getChildNodes(final String parentNodeId) {
		String hql = " from AssAutoCollection assAutoCollection where assAutoCollection.parentNodeId like '"
				+ parentNodeId + "%'";
		hql += " order by assAutoCollection.nodeId";
		return (List) getHibernateTemplate().find(hql);
	}

}