package com.boco.eoms.partner.assiEva.dao.hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;
import com.boco.eoms.partner.assiEva.dao.IAssiEvaTreeDao;
import com.boco.eoms.partner.assiEva.mgr.IAssiEvaKpiMgr;
import com.boco.eoms.partner.assiEva.mgr.IAssiEvaTemplateMgr;
import com.boco.eoms.partner.assiEva.model.AssiEvaKpi;
import com.boco.eoms.partner.assiEva.model.AssiEvaTemplate;
import com.boco.eoms.partner.assiEva.model.AssiEvaTree;
import com.boco.eoms.partner.assiEva.util.AssiEvaConstants;

public class AssiEvaTreeDaoHibernate extends BaseDaoHibernate implements
		IAssiEvaTreeDao, ID2NameDAO {

	public AssiEvaTree getTreeNode(String id) {
		AssiEvaTree node = new AssiEvaTree();
		String hql = " from AssiEvaTree tree where tree.id='" + id + "'";
		List list = getHibernateTemplate().find(hql);
		if (null != list && 0 < list.size()) {
			node = (AssiEvaTree) list.get(0);
		}
		return node;
	}

	public String getMaxNodeId(String parentNodeId, int length) {
		String minNodeId = getUsableMinNodeId(parentNodeId, length);
		if (null == minNodeId || "".equals(minNodeId)) {
			minNodeId = AssiEvaConstants.NODEID_DEFULT_VALUE;
		}
		return minNodeId;
	}

	private String getUsableMinNodeId(final String parentNodeId, final int len) {
		String minUsableNodeId = "";
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String hql = " select distinct(nodeId) from AssiEvaTree where nodeId like '"
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
		if (hm.size() >= Integer.parseInt(AssiEvaConstants.NODEID_IF_MAXID)) {
			return minUsableNodeId;
		}
		long nodeIdVar = Long.valueOf(parentNodeId + AssiEvaConstants.NODEID_NOSON)
				.longValue();
		while (null != hm.get(String.valueOf(nodeIdVar))) {
			nodeIdVar = nodeIdVar + 1;
		}
		return String.valueOf(nodeIdVar);
	}

	public AssiEvaTree getTreeNodeByNodeId(String nodeId) {
		AssiEvaTree node = new AssiEvaTree();
		String hql = " from AssiEvaTree tree where tree.nodeId='" + nodeId + "'";
		List list = getHibernateTemplate().find(hql);
		if (null != list && 0 < list.size()) {
			node = (AssiEvaTree) list.get(0);
		}
		return node;
	}

	public AssiEvaTree getNodeByObjId(String parentNodeId, String objectId) {
		AssiEvaTree node = new AssiEvaTree();
		String hql = " from AssiEvaTree tree where tree.nodeId like '"
				+ parentNodeId + "%' and tree.objectId='" + objectId + "'";
		List list = getHibernateTemplate().find(hql);
		if (null != list && 0 < list.size()) {
			node = (AssiEvaTree) list.get(0);
		}
		return node;
	}

	public ArrayList listNextLevelNode(String parentNodeId, String nodeType) {
		String hql = " from AssiEvaTree tree where tree.parentNodeId='"
				+ parentNodeId + "'";
		if (null != nodeType && !"".equals(nodeType)) {
			hql += " and tree.nodeType='" + nodeType + "'";
		}
		hql += " order by tree.nodeId";
		return (ArrayList) getHibernateTemplate().find(hql);
	}

	//判断所给节点是否为第一层KPI，第一层不加以上限要求
	public boolean isFirstKpiLevel(String nodeId) {
		String hql = " from AssiEvaTree t where t.nodeType ='KPI' and t.parentNodeId in(select m.nodeId from AssiEvaTree m where m.nodeType ='TEMPLATE') and t.nodeId like '"
				+ nodeId + "%'";
		List list = getHibernateTemplate().find(hql);
		if (null != list && 0 < list.size()) {
			return true;
		}
		return false;
	}

	public List listChildNodes(String parentNodeId, String nodeType, String leaf) {
		String hql = " from AssiEvaTree tree where tree.parentNodeId like '"
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

	public void removeTreeNode(AssiEvaTree assiEvaTreeNode) {
		getHibernateTemplate().delete(assiEvaTreeNode);
	}

	public void removeTreeNodeByNodeId(final String nodeId) {
		final String hql = "delete from AssiEvaTree tree where tree.nodeId like '"
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

	public void saveTreeNode(AssiEvaTree assiEvaTreeNode) {
		if (assiEvaTreeNode.getId() == null || "".equals(assiEvaTreeNode.getId())) {
			getHibernateTemplate().save(assiEvaTreeNode);
		} else {
			getHibernateTemplate().saveOrUpdate(assiEvaTreeNode);
		}
	}

	public String id2Name(String nodeId) {
		String nodeName = "";
		AssiEvaTree node = getTreeNodeByNodeId(nodeId);
		if (null != node.getId() && !"".equals(node.getId())) {
			if (null != node.getNodeName() && !"".equals(node.getNodeName())) {
				nodeName = node.getNodeName();
			} else { // 模板和指标的名称存在各自的表中
				if (AssiEvaConstants.NODETYPE_TEMPLATE.equals(node.getNodeType())) {
					IAssiEvaTemplateMgr tplMgr = (IAssiEvaTemplateMgr) ApplicationContextHolder
							.getInstance().getBean("IassiEvaTemplateMgr");
					AssiEvaTemplate tpl = tplMgr.getTemplate(node.getObjectId());
					nodeName = tpl.getTemplateName();
				} else if (AssiEvaConstants.NODETYPE_KPI.equals(node.getNodeType())) {
					IAssiEvaKpiMgr kpiMgr = (IAssiEvaKpiMgr) ApplicationContextHolder
							.getInstance().getBean("IassiEvaKpiMgr");
					AssiEvaKpi kpi = kpiMgr.getKpi(node.getObjectId());
					nodeName = kpi.getKpiName();
				} else {
					nodeName = AssiEvaConstants.NODE_NONAME;
				}
			}
		} else {
			nodeName = AssiEvaConstants.NODE_NONAME;
		}
		return nodeName;
	}

}
