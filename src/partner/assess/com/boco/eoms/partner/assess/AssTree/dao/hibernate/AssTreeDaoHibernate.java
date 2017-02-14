/**
 * 
 */
package com.boco.eoms.partner.assess.AssTree.dao.hibernate;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.partner.assess.AssTree.dao.IAssTreeDao;
import com.boco.eoms.partner.assess.AssTree.model.AssTree;
import com.boco.eoms.partner.assess.util.AssConstants;
import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;

/**
 * <p>
 * Title:后评估模板树dao操作基类
 * </p>
 * <p>
 * Description:后评估模板树dao操作基类
 * </p>
 * <p>
 * Date:Nov 23, 2010 1:56:46 PM
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
 * 
 */
public abstract class AssTreeDaoHibernate extends BaseDaoHibernate implements
	IAssTreeDao, ID2NameDAO {



	public AssTree getTreeNode(String id) {
		AssTree node = new AssTree();
		String hql = " from AssTree tree where tree.id='" + id + "'";
		List list = getHibernateTemplate().find(hql);
		if (null != list && 0 < list.size()) {
			node = (AssTree) list.get(0);
		}
		return node;
	}

	public String getMaxNodeId(String parentNodeId, int length) {
		String minNodeId = getUsableMinNodeId(parentNodeId, length);
		if (null == minNodeId || "".equals(minNodeId)) {
			minNodeId = AssConstants.NODEID_DEFULT_VALUE;
		}
		return minNodeId;
	}

	private String getUsableMinNodeId(final String parentNodeId, final int len) {
		String minUsableNodeId = "";
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String hql = " select distinct(nodeId) from AssTree where nodeId like '"
						+ parentNodeId + "%' and length(nodeId)='" + len + "'";
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
		if (hm.size() >= Integer.parseInt(AssConstants.NODEID_IF_MAXID)) {
			return minUsableNodeId;
		}
		long nodeIdVar = Long.valueOf(parentNodeId + AssConstants.NODEID_NOSON)
				.longValue();
		while (null != hm.get(String.valueOf(nodeIdVar))) {
			nodeIdVar = nodeIdVar + 1;
		}
		return String.valueOf(nodeIdVar);
	}

	public AssTree getTreeNodeByNodeId(String nodeId) {
		AssTree node = new AssTree();
		String hql = " from AssTree tree where tree.nodeId='" + nodeId + "'";
		List list = getHibernateTemplate().find(hql);
		if (null != list && 0 < list.size()) {
			node = (AssTree) list.get(0);
		}
		return node;
	}

	public AssTree getNodeByObjId(String parentNodeId, String objectId) {
		AssTree node = new AssTree();
		String hql = " from AssTree tree where tree.nodeId like '"
				+ parentNodeId + "%' and tree.objectId='" + objectId + "'";
		List list = getHibernateTemplate().find(hql);
		if (null != list && 0 < list.size()) {
			node = (AssTree) list.get(0);
		}
		return node;
	}

	public ArrayList listNextLevelNode(String parentNodeId, String nodeType) {
		String hql = " from AssTree tree where tree.parentNodeId='"
				+ parentNodeId + "'";
		if (null != nodeType && !"".equals(nodeType)) {
			hql += " and tree.nodeType='" + nodeType + "'";
		}
		hql += " order by tree.nodeId";
		return (ArrayList) getHibernateTemplate().find(hql);
	}

	//判断所给节点是否为第一层KPI，第一层不加以上限要求
	public boolean isFirstKpiLevel(String nodeId) {
		String hql = " from AssTree t where t.nodeType ='KPI' and t.parentNodeId in(select m.nodeId from AssTree m where m.nodeType ='TEMPLATE') and t.nodeId like '"
				+ nodeId + "%'";
		List list = getHibernateTemplate().find(hql);
		if (null != list && 0 < list.size()) {
			return true;
		}
		return false;
	}

	public List listChildNodes(String parentNodeId, String nodeType, String leaf) {
		String hql = " from AssTree tree where tree.parentNodeId like '"
				+ parentNodeId + "%'";
		if (null != nodeType && !"".equals(nodeType)) {
			hql += " and tree.nodeType='" + nodeType + "'";
		}
		if (null != leaf && !"".equals(leaf)) {
			hql += " and tree.leaf='" + leaf + "'";
		}
		hql += " order by tree.nodeId";
		return (ArrayList) getHibernateTemplate().find(hql);
	}

	public void removeTreeNode(AssTree assTreeNode) {
		getHibernateTemplate().delete(assTreeNode);
	}

	public void removeTreeNodeByNodeId(final String nodeId) {
		final String hql = "delete from AssTree tree where tree.nodeId like '"
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

	public void saveTreeNode(AssTree assTreeNode) {
		if (assTreeNode.getId() == null || "".equals(assTreeNode.getId())) {
			getHibernateTemplate().save(assTreeNode);
		} else {
			getHibernateTemplate().saveOrUpdate(assTreeNode);
		}
	}
	
	public List listChildNodesByTotal(String parentNodeId) {
		String hql = " from AssTree tree where tree.parentNodeId like '"
				+ parentNodeId + "%' and isTotal = 'Y'";
		hql += " order by tree.nodeId";
		return (List) getHibernateTemplate().find(hql);
	}	
}
