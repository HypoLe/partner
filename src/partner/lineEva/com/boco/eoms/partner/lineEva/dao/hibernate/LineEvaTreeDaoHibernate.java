package com.boco.eoms.partner.lineEva.dao.hibernate;

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
import com.boco.eoms.partner.lineEva.dao.ILineEvaTreeDao;
import com.boco.eoms.partner.lineEva.mgr.ILineEvaKpiMgr;
import com.boco.eoms.partner.lineEva.mgr.ILineEvaTemplateMgr;
import com.boco.eoms.partner.lineEva.model.LineEvaKpi;
import com.boco.eoms.partner.lineEva.model.LineEvaTemplate;
import com.boco.eoms.partner.lineEva.model.LineEvaTree;
import com.boco.eoms.partner.lineEva.util.LineEvaConstants;

public class LineEvaTreeDaoHibernate extends BaseDaoHibernate implements
		ILineEvaTreeDao, ID2NameDAO {

	public LineEvaTree getTreeNode(String id) {
		LineEvaTree node = new LineEvaTree();
		String hql = " from LineEvaTree tree where tree.id='" + id + "'";
		List list = getHibernateTemplate().find(hql);
		if (null != list && 0 < list.size()) {
			node = (LineEvaTree) list.get(0);
		}
		return node;
	}

	public String getMaxNodeId(String parentNodeId, int length) {
		String minNodeId = getUsableMinNodeId(parentNodeId, length);
		if (null == minNodeId || "".equals(minNodeId)) {
			minNodeId = LineEvaConstants.NODEID_DEFULT_VALUE;
		}
		return minNodeId;
	}

	private String getUsableMinNodeId(final String parentNodeId, final int len) {
		String minUsableNodeId = "";
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String hql = " select distinct(nodeId) from LineEvaTree where nodeId like '"
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
		if (hm.size() >= Integer.parseInt(LineEvaConstants.NODEID_IF_MAXID)) {
			return minUsableNodeId;
		}
		long nodeIdVar = Long.valueOf(parentNodeId + LineEvaConstants.NODEID_NOSON)
				.longValue();
		while (null != hm.get(String.valueOf(nodeIdVar))) {
			nodeIdVar = nodeIdVar + 1;
		}
		return String.valueOf(nodeIdVar);
	}

	public LineEvaTree getTreeNodeByNodeId(String nodeId) {
		LineEvaTree node = new LineEvaTree();
		String hql = " from LineEvaTree tree where tree.nodeId='" + nodeId + "'";
		List list = getHibernateTemplate().find(hql);
		if (null != list && 0 < list.size()) {
			node = (LineEvaTree) list.get(0);
		}
		return node;
	}

	public LineEvaTree getNodeByObjId(String parentNodeId, String objectId) {
		LineEvaTree node = new LineEvaTree();
		String hql = " from LineEvaTree tree where tree.nodeId like '"
				+ parentNodeId + "%' and tree.objectId='" + objectId + "'";
		List list = getHibernateTemplate().find(hql);
		if (null != list && 0 < list.size()) {
			node = (LineEvaTree) list.get(0);
		}
		return node;
	}

	public ArrayList listNextLevelNode(String parentNodeId, String nodeType) {
		String hql = " from LineEvaTree tree where tree.parentNodeId='"
				+ parentNodeId + "'";
		if (null != nodeType && !"".equals(nodeType)) {
			hql += " and tree.nodeType='" + nodeType + "'";
		}
		hql += " order by tree.nodeId";
		return (ArrayList) getHibernateTemplate().find(hql);
	}

	//判断所给节点是否为第一层KPI，第一层不加以上限要求
	public boolean isFirstKpiLevel(String nodeId) {
		String hql = " from LineEvaTree t where t.nodeType ='KPI' and t.parentNodeId in(select m.nodeId from LineEvaTree m where m.nodeType ='TEMPLATE') and t.nodeId like '"
				+ nodeId + "%'";
		List list = getHibernateTemplate().find(hql);
		if (null != list && 0 < list.size()) {
			return true;
		}
		return false;
	}

	public List listChildNodes(String parentNodeId, String nodeType, String leaf) {
		String hql = " from LineEvaTree tree where tree.parentNodeId like '"
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

	public void removeTreeNode(LineEvaTree lineEvaTreeNode) {
		getHibernateTemplate().delete(lineEvaTreeNode);
	}

	public void removeTreeNodeByNodeId(final String nodeId) {
		final String hql = "delete from LineEvaTree tree where tree.nodeId like '"
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

	public void saveTreeNode(LineEvaTree lineEvaTreeNode) {
		if (lineEvaTreeNode.getId() == null || "".equals(lineEvaTreeNode.getId())) {
			getHibernateTemplate().save(lineEvaTreeNode);
		} else {
			getHibernateTemplate().saveOrUpdate(lineEvaTreeNode);
		}
	}

	public String id2Name(String nodeId) {
		String nodeName = "";
		LineEvaTree node = getTreeNodeByNodeId(nodeId);
		if (null != node.getId() && !"".equals(node.getId())) {
			if (null != node.getNodeName() && !"".equals(node.getNodeName())) {
				nodeName = node.getNodeName();
			} else { // 模板和指标的名称存在各自的表中
				if (LineEvaConstants.NODETYPE_TEMPLATE.equals(node.getNodeType())) {
					ILineEvaTemplateMgr tplMgr = (ILineEvaTemplateMgr) ApplicationContextHolder
							.getInstance().getBean("IlineEvaTemplateMgr");
					LineEvaTemplate tpl = tplMgr.getTemplate(node.getObjectId());
					nodeName = tpl.getTemplateName();
				} else if (LineEvaConstants.NODETYPE_KPI.equals(node.getNodeType())) {
					ILineEvaKpiMgr kpiMgr = (ILineEvaKpiMgr) ApplicationContextHolder
							.getInstance().getBean("IlineEvaKpiMgr");
					LineEvaKpi kpi = kpiMgr.getKpi(node.getObjectId());
					nodeName = kpi.getKpiName();
				} else {
					nodeName = LineEvaConstants.NODE_NONAME;
				}
			}
		} else {
			nodeName = LineEvaConstants.NODE_NONAME;
		}
		return nodeName;
	}

}
