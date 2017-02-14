package com.boco.eoms.partner.siteEva.dao.hibernate;

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
import com.boco.eoms.partner.siteEva.dao.ISiteEvaTreeDao;
import com.boco.eoms.partner.siteEva.mgr.ISiteEvaKpiMgr;
import com.boco.eoms.partner.siteEva.mgr.ISiteEvaTemplateMgr;
import com.boco.eoms.partner.siteEva.model.SiteEvaKpi;
import com.boco.eoms.partner.siteEva.model.SiteEvaTemplate;
import com.boco.eoms.partner.siteEva.model.SiteEvaTree;
import com.boco.eoms.partner.siteEva.util.SiteEvaConstants;

public class SiteEvaTreeDaoHibernate extends BaseDaoHibernate implements
		ISiteEvaTreeDao, ID2NameDAO {

	public SiteEvaTree getTreeNode(String id) {
		SiteEvaTree node = new SiteEvaTree();
		String hql = " from SiteEvaTree tree where tree.id='" + id + "'";
		List list = getHibernateTemplate().find(hql);
		if (null != list && 0 < list.size()) {
			node = (SiteEvaTree) list.get(0);
		}
		return node; 
	}

	public String getMaxNodeId(String parentNodeId, int length) {
		String minNodeId = getUsableMinNodeId(parentNodeId, length);
		if (null == minNodeId || "".equals(minNodeId)) {
			minNodeId = SiteEvaConstants.NODEID_DEFULT_VALUE;
		}
		return minNodeId;
	}

	private String getUsableMinNodeId(final String parentNodeId, final int len) {
		String minUsableNodeId = "";
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String hql = " select distinct(nodeId) from SiteEvaTree where nodeId like '"
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
		if (hm.size() >= Integer.parseInt(SiteEvaConstants.NODEID_IF_MAXID)) {
			return minUsableNodeId;
		}
		long nodeIdVar = Long.valueOf(parentNodeId + SiteEvaConstants.NODEID_NOSON)
				.longValue();
		while (null != hm.get(String.valueOf(nodeIdVar))) {
			nodeIdVar = nodeIdVar + 1;
		}
		return String.valueOf(nodeIdVar);
	}

	public SiteEvaTree getTreeNodeByNodeId(String nodeId) {
		SiteEvaTree node = new SiteEvaTree();
		String hql = " from SiteEvaTree tree where tree.nodeId='" + nodeId + "'";
		List list = getHibernateTemplate().find(hql);
		if (null != list && 0 < list.size()) {
			node = (SiteEvaTree) list.get(0);
		}
		return node;
	}

	public SiteEvaTree getNodeByObjId(String parentNodeId, String objectId) {
		SiteEvaTree node = new SiteEvaTree();
		String hql = " from SiteEvaTree tree where tree.nodeId like '"
				+ parentNodeId + "%' and tree.objectId='" + objectId + "'";
		List list = getHibernateTemplate().find(hql);
		if (null != list && 0 < list.size()) {
			node = (SiteEvaTree) list.get(0);
		}
		return node;
	}

	public ArrayList listNextLevelNode(String parentNodeId, String nodeType) {
		String hql = " from SiteEvaTree tree where tree.parentNodeId='"
				+ parentNodeId + "'";
		if (null != nodeType && !"".equals(nodeType)) {
			hql += " and tree.nodeType='" + nodeType + "'";
		}
		hql += " order by tree.nodeId";
		return (ArrayList) getHibernateTemplate().find(hql);
	}

	//判断所给节点是否为第一层KPI，第一层不加以上限要求
	public boolean isFirstKpiLevel(String nodeId) {
		String hql = " from SiteEvaTree t where t.nodeType ='KPI' and t.parentNodeId in(select m.nodeId from SiteEvaTree m where m.nodeType ='TEMPLATE') and t.nodeId like '"
				+ nodeId + "%'";
		List list = getHibernateTemplate().find(hql);
		if (null != list && 0 < list.size()) {
			return true;
		}
		return false;
	}

	public List listChildNodes(String parentNodeId, String nodeType, String leaf) {
		String hql = " from SiteEvaTree tree where tree.parentNodeId like '"
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

	public void removeTreeNode(SiteEvaTree siteEvaTreeNode) {
		getHibernateTemplate().delete(siteEvaTreeNode);
	}

	public void removeTreeNodeByNodeId(final String nodeId) {
		final String hql = "delete from SiteEvaTree tree where tree.nodeId like '"
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

	public void saveTreeNode(SiteEvaTree siteEvaTreeNode) {
		if (siteEvaTreeNode.getId() == null || "".equals(siteEvaTreeNode.getId())) {
			getHibernateTemplate().save(siteEvaTreeNode);
		} else {
			getHibernateTemplate().saveOrUpdate(siteEvaTreeNode);
		}
	}

	public String id2Name(String nodeId) {
		String nodeName = "";
		SiteEvaTree node = getTreeNodeByNodeId(nodeId);
		if (null != node.getId() && !"".equals(node.getId())) {
			if (null != node.getNodeName() && !"".equals(node.getNodeName())) {
				nodeName = node.getNodeName();
			} else { // 模板和指标的名称存在各自的表中
				if (SiteEvaConstants.NODETYPE_TEMPLATE.equals(node.getNodeType())) {
					ISiteEvaTemplateMgr tplMgr = (ISiteEvaTemplateMgr) ApplicationContextHolder
							.getInstance().getBean("IsiteEvaTemplateMgr");
					SiteEvaTemplate tpl = tplMgr.getTemplate(node.getObjectId());
					nodeName = tpl.getTemplateName();
				} else if (SiteEvaConstants.NODETYPE_KPI.equals(node.getNodeType())) {
					ISiteEvaKpiMgr kpiMgr = (ISiteEvaKpiMgr) ApplicationContextHolder
							.getInstance().getBean("IsiteEvaKpiMgr");
					SiteEvaKpi kpi = kpiMgr.getKpi(node.getObjectId());
					nodeName = kpi.getKpiName();
				} else {
					nodeName = SiteEvaConstants.NODE_NONAME;
				}
			}
		} else {
			nodeName = SiteEvaConstants.NODE_NONAME;
		}
		return nodeName;
	}

}
