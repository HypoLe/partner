package com.boco.eoms.partner.eva.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;
import com.boco.eoms.partner.eva.dao.IPnrEvaTreeDao;
import com.boco.eoms.partner.eva.mgr.IPnrEvaKpiMgr;
import com.boco.eoms.partner.eva.mgr.IPnrEvaTemplateMgr;
import com.boco.eoms.partner.eva.model.PnrEvaKpi;
import com.boco.eoms.partner.eva.model.PnrEvaTemplate;
import com.boco.eoms.partner.eva.model.PnrEvaTree;
import com.boco.eoms.partner.eva.util.PnrEvaConstants;

public class PnrEvaTreeDaoHibernate extends BaseDaoHibernate implements
		IPnrEvaTreeDao, ID2NameDAO {

	public PnrEvaTree getEvaTreeByObjceId(final String objectId) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PnrEvaTree where objectId = ? ";
				Query query = session.createQuery(queryStr);
				query.setString(0, objectId);
				List list = query.list();
				if (list.iterator().hasNext()) {
					return (PnrEvaTree) list.iterator().next();
				}
				return new PnrEvaTree();
			}
		};
		return (PnrEvaTree) getHibernateTemplate().execute(callback);
	}

	public PnrEvaTree getTreeNode(String id) {
		PnrEvaTree node = new PnrEvaTree();
		String hql = " from PnrEvaTree tree where tree.id='" + id + "'";
		List list = getHibernateTemplate().find(hql);
		if (null != list && 0 < list.size()) {
			node = (PnrEvaTree) list.get(0);
		}
		return node;
	}

	public String getMaxNodeId(String parentNodeId, int length) {
		String minNodeId = getUsableMinNodeId(parentNodeId, length);
		if (null == minNodeId || "".equals(minNodeId)) {
			minNodeId = PnrEvaConstants.NODEID_DEFULT_VALUE;
		}
		return minNodeId;
	}
	private String getUsableMinNodeId(final String parentNodeId, final int len) {
		String minUsableNodeId = "";
		String nodeId ="";
		int maxNum = 0;
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String hql = " select distinct(nodeId) from PnrEvaTree where nodeId like '"
						+ parentNodeId + "%' and length(nodeId)='" + len + "' order by nodeId desc";
				Query query = session.createQuery(hql);
				return query.list();
			}
		};
		List list = (List) getHibernateTemplate().execute(callback);
		if(list.size()>0){
			nodeId = list.get(0).toString();
		}
		if(!("").equals(nodeId)){
		maxNum = Integer.parseInt(nodeId.substring(parentNodeId.length(),nodeId.length()));
		}
		long nodeIdVar = maxNum+1;
		if(nodeIdVar>Long.parseLong(PnrEvaConstants.NODEID_IF_MAXID)){
			return minUsableNodeId;
		}
		int zeroNum = PnrEvaConstants.NODEID_BETWEEN_LENGTH-String.valueOf(nodeIdVar).length();
		String zeroStr = "";
		for(int i=0;i<zeroNum;i++){
			zeroStr += 0;
		}
		String usableMinNodeId = parentNodeId + zeroStr + nodeIdVar;
		return usableMinNodeId;
	}
	public PnrEvaTree getTreeNodeByNodeId(String nodeId) {
		PnrEvaTree node = new PnrEvaTree();
		String hql = " from PnrEvaTree tree where tree.nodeId='" + nodeId + "'";
		List list = getHibernateTemplate().find(hql);
		if (null != list && 0 < list.size()) {
			node = (PnrEvaTree) list.get(0);
		}
		return node;
	}

	public PnrEvaTree getNodeByObjId(String parentNodeId, String objectId) {
		PnrEvaTree node = new PnrEvaTree();
		String hql = " from PnrEvaTree tree where tree.nodeId like '"
				+ parentNodeId + "%' and tree.objectId='" + objectId + "'";
		List list = getHibernateTemplate().find(hql);
		if (null != list && 0 < list.size()) {
			node = (PnrEvaTree) list.get(0);
		}
		return node;
	}

	public ArrayList listNextLevelNode(String parentNodeId, String nodeType) {
		String hql = " from PnrEvaTree tree where tree.parentNodeId='"
				+ parentNodeId + "'";
		if (null != nodeType && !"".equals(nodeType)) {
			hql += " and tree.nodeType='" + nodeType + "'";
		}
		hql += " order by tree.nodeId";
		return (ArrayList) getHibernateTemplate().find(hql);
	}

	//判断所给节点是否为第一层KPI，第一层不加以上限要求
	public boolean isFirstKpiLevel(String nodeId) {
		String hql = " from PnrEvaTree t where t.nodeType ='KPI' and t.parentNodeId in(select m.nodeId from PnrEvaTree m where m.nodeType ='TEMPLATE') and t.nodeId like '"
				+ nodeId + "%'";
		List list = getHibernateTemplate().find(hql);
		if (null != list && 0 < list.size()) {
			return true;
		}
		return false;
	}

	public List listChildNodes(String parentNodeId, String nodeType, String leaf) {
		String hql = " from PnrEvaTree tree where tree.parentNodeId like '"
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

	public void removeTreeNode(PnrEvaTree evaTreeNode) {
		getHibernateTemplate().delete(evaTreeNode);
	}

	public void removeTreeNodeByNodeId(final String nodeId) {
		final String hql = "delete from PnrEvaTree tree where tree.nodeId like '"
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

	public void saveTreeNode(PnrEvaTree evaTreeNode) {
		if (evaTreeNode.getId() == null || "".equals(evaTreeNode.getId())) {
			getHibernateTemplate().save(evaTreeNode);
		} else {
			getHibernateTemplate().saveOrUpdate(evaTreeNode);
		}
	}

	public String id2Name(String nodeId) {
		String nodeName = "";
		PnrEvaTree node = getTreeNodeByNodeId(nodeId);
		if (null != node.getId() && !"".equals(node.getId())) {
			if (null != node.getNodeName() && !"".equals(node.getNodeName())) {
				nodeName = node.getNodeName();
			} else { // 模板和指标的名称存在各自的表中
				if (PnrEvaConstants.NODETYPE_TEMPLATE.equals(node.getNodeType())) {
					IPnrEvaTemplateMgr tplMgr = (IPnrEvaTemplateMgr) ApplicationContextHolder
							.getInstance().getBean("iPnrEvaTemplateMgr");
					PnrEvaTemplate tpl = tplMgr.getTemplate(node.getObjectId());
					nodeName = tpl.getTemplateName();
				} else if (PnrEvaConstants.NODETYPE_KPI.equals(node.getNodeType())) {
					IPnrEvaKpiMgr kpiMgr = (IPnrEvaKpiMgr) ApplicationContextHolder
							.getInstance().getBean("iPnrEvaKpiMgr");
					PnrEvaKpi kpi = kpiMgr.getKpi(node.getObjectId());
					nodeName = kpi.getKpiName();
				} else {
					nodeName = PnrEvaConstants.NODE_NONAME;
				}
			}
		} else {
			nodeName = PnrEvaConstants.NODE_NONAME;
		}
		return nodeName;
	}
	
	public List listNextNodeByPNodeIdAndArea(String nodeId, String areaStr) {
		String hql = " from PnrEvaTree tree where tree.parentNodeId='"
				+ nodeId + "'";
		if (null != areaStr && !"".equals(areaStr)) {
			hql += " and tree.creatArea in (" + areaStr + ")";
		}
		hql += " order by tree.nodeId";
		return (List) getHibernateTemplate().find(hql);
	}
	
	public List listWeightByPNodeIdAndArea(String nodeId, String areaStr, String childType) {
		String hql = " select weight from PnrEvaWeight weight,PnrEvaTree tree where weight.nodeId = tree.nodeId ";
			if(childType.equals(PnrEvaConstants.NEXT_CHILD_NODE)){
				hql +=" and tree.parentNodeId='"+ nodeId + "'";
			}else{
				hql +=" and tree.nodeId like '"+ nodeId + "%'";
			}
		if (null != areaStr && !"".equals(areaStr)) {
			hql += " and weight.area in (" + areaStr + ")";
		}
		hql += " order by weight.area";
		return (List) getHibernateTemplate().find(hql);
	}
	
	public List listActNodesByPNodeIdAndArea(String nodeId, String areaStr, String nodeType, String order) {
		String hql = " from PnrEvaTree tree where tree.nodeType='"+nodeType+"'";
			if(nodeType.equals(PnrEvaConstants.NODETYPE_TEMPLATE)){
				hql += " and tree.parentNodeId='"+ nodeId + "'";
			}else{
				hql += " and tree.nodeId like '"+ nodeId + "%'";
			}
		if (null != areaStr && !"".equals(areaStr)) {
			hql += " and tree.creatArea in (" + areaStr + ")";
		}
		if (null != order && !"".equals(order)) {
			hql += " order by tree.nodeId "+order;
		}
		hql += " order by tree.nodeId";
		return (List) getHibernateTemplate().find(hql);
	}
}
