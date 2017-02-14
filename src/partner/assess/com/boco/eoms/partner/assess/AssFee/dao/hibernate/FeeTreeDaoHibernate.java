package com.boco.eoms.partner.assess.AssFee.dao.hibernate;

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
import com.boco.eoms.partner.assess.AssFee.dao.IFeeTreeDao;
import com.boco.eoms.partner.assess.AssFee.model.FeeTree;
import com.boco.eoms.partner.assess.AssFee.util.FeeTreeConstants;


/**
 * <p>
 * Title:考核线路信息树 dao的hibernate实现
 * </p>
 * <p>
 * Description:考核线路信息树
 * </p>
 * <p>
 * Tue Nov 23 08:36:47 CST 2010
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public class FeeTreeDaoHibernate extends BaseDaoHibernate implements IFeeTreeDao,
		ID2NameDAO {
    

	public FeeTree getFeeTree(final String id) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from FeeTree feeTree where feeTree.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (FeeTree) result.iterator().next();
				} else {
					return new FeeTree();
				}
			}
		};
		return (FeeTree) getHibernateTemplate().execute(callback);
	}
    

	public void saveFeeTree(final FeeTree feeTree) {
		if ((feeTree.getId() == null) || (feeTree.getId().equals("")))
			getHibernateTemplate().save(feeTree);
		else
			getHibernateTemplate().saveOrUpdate(feeTree);
	}
	

	public String id2Name(String id) throws DictDAOException {
		FeeTree feeTree = this.getFeeTreeByNodeId(id);
		if(feeTree==null){
			return "";
		}
		return feeTree.getNodeName();
	}
    
    public FeeTree getFeeTreeByNodeId(final String nodeId) {
    	FeeTree feeTree = new FeeTree();
		String hql = " from FeeTree feeTree where feeTree.nodeId='" + nodeId + "'";
		List list = getHibernateTemplate().find(hql);
		if (list.iterator().hasNext()) {
			feeTree = (FeeTree) list.iterator().next();
		}
		return feeTree;
    }
    
    public void removeFeeTreeByNodeId(final String nodeId) {
    	final String hql = "delete from FeeTree feeTree where feeTree.nodeId like '"
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
    
    public List getNextLevelFeeTrees(final String parentNodeId) {
    	String hql = " from FeeTree feeTree where feeTree.parentNodeId='"
				+ parentNodeId + "'";
		hql += " order by feeTree.nodeId";
		return getHibernateTemplate().find(hql);
    }
    
    public String getUsableNodeId(final String parentNodeId, final int length) {
    	String minNodeId = getUsableMinNodeId(parentNodeId, length);
		if (null == minNodeId || "".equals(minNodeId)) {
			minNodeId = FeeTreeConstants.NODEID_DEFAULT_VALUE;
		}
		return minNodeId;
    }
    
    private String getUsableMinNodeId(final String parentNodeId, final int len) {
		String minUsableNodeId = "";
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String hql = " select distinct(nodeId) from FeeTree feeTree where feeTree.nodeId like '"
						+ parentNodeId + "%' and length(feeTree.nodeId)='" + len + "'";
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
		if (hm.size() >= Integer.parseInt(FeeTreeConstants.NODEID_IF_MAXID)) {
			return minUsableNodeId;
		}
		long nodeIdVar = Long.valueOf(parentNodeId + FeeTreeConstants.NODEID_NOSON)
				.longValue();
		while (null != hm.get(String.valueOf(nodeIdVar))) {
			nodeIdVar = nodeIdVar + 1;
		}
		return String.valueOf(nodeIdVar);
	}
	
	public List getChildNodes(final String parentNodeId) {
		String hql = " from FeeTree feeTree where feeTree.parentNodeId like '"
				+ parentNodeId + "%'";
		hql += " order by feeTree.nodeId";
		return (List) getHibernateTemplate().find(hql);
	}

}