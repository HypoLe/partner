package com.boco.eoms.partner.siteEva.mgr.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.UUIDHexGenerator;
import com.boco.eoms.partner.siteEva.dao.ISiteEvaKpiDao;
import com.boco.eoms.partner.siteEva.mgr.ISiteEvaKpiInstanceMgr;
import com.boco.eoms.partner.siteEva.mgr.ISiteEvaKpiMgr;
import com.boco.eoms.partner.siteEva.mgr.ISiteEvaTreeMgr;
import com.boco.eoms.partner.siteEva.model.SiteEvaKpi;
import com.boco.eoms.partner.siteEva.model.SiteEvaKpiInstance;
import com.boco.eoms.partner.siteEva.model.SiteEvaTree;
import com.boco.eoms.partner.siteEva.util.SiteEvaConstants;
import com.boco.eoms.log4j.Logger;

public class SiteEvaKpiMgrImpl implements ISiteEvaKpiMgr {

	private ISiteEvaKpiDao siteEvaKpiDao;

	public ISiteEvaKpiDao getSiteEvaKpiDao() {
		return siteEvaKpiDao;
	}
 
	public void setSiteEvaKpiDao(ISiteEvaKpiDao siteEvaKpiDao) {
		this.siteEvaKpiDao = siteEvaKpiDao;
	}

	public SiteEvaKpi getKpi(String id) {
		return siteEvaKpiDao.getKpi(id);
	}

	public SiteEvaKpi getKpi(String id, String deleted) {
		return siteEvaKpiDao.getKpi(id, deleted);
	}

	public SiteEvaKpi getKpiByNodeId(String nodeId) {
		return siteEvaKpiDao.getKpiByNodeId(nodeId);
	}

	public ArrayList listKpiOfType(String parentNodeId) {
		return siteEvaKpiDao.listKpiOfType(parentNodeId);
	}

	public void removeKpi(SiteEvaKpi kpi) {
		// 假删除指标
		siteEvaKpiDao.removeKpi(kpi);
	}

	public void saveKpi(SiteEvaKpi kpi) {
		siteEvaKpiDao.saveKpi(kpi);
	}

	public void saveKpiAndNode(SiteEvaKpi kpi, String parentNodeId){
		
	}
	public void saveKpiAndNode(SiteEvaKpi kpi, String parentNodeId,String isActived) {
		ISiteEvaTreeMgr treeMgr = (ISiteEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("IsiteEvaTreeMgr");

		if (null == kpi.getId() || "".equals(kpi.getId())) { // 新指标
			// 保存指标
			kpi.setDeleted(SiteEvaConstants.UNDELETED);
			siteEvaKpiDao.saveKpi(kpi);

			SiteEvaTree siteEvaTree = new SiteEvaTree();

			// 生成新节点ID
			String newNodeId = treeMgr.getMaxNodeId(parentNodeId);

			// 保存树节点
			siteEvaTree.setNodeId(newNodeId);
			siteEvaTree.setParentNodeId(parentNodeId);
			siteEvaTree.setNodeName(kpi.getKpiName());
			siteEvaTree.setNodeType(SiteEvaConstants.NODETYPE_KPI);
			siteEvaTree.setLeaf(SiteEvaConstants.NODE_LEAF);
			siteEvaTree.setObjectId(kpi.getId());
			siteEvaTree.setWeight(kpi.getWeight());
			treeMgr.saveTreeNode(siteEvaTree);

			// 更新父节点leaf
			treeMgr.updateParentNodeLeaf(parentNodeId,
					SiteEvaConstants.NODE_NOTLEAF);
		} else { // 修改指标
			// 获得对应树节点
				SiteEvaTree node = treeMgr.getNodeByObjId(parentNodeId, kpi.getId());
				// 设置权重
				node.setWeight(kpi.getWeight());
				node.setNodeName(kpi.getKpiName());
//				if(isActived.equals(SiteEvaConstants.TEMPLATE_ACTIVATED)){//如果模版激活则复制指标
				SiteEvaKpi kpi2 = new SiteEvaKpi();
				kpi2.setAlgorithm(kpi.getAlgorithm());
				kpi2.setCreateTime(kpi.getCreateTime());
				kpi2.setCreator(kpi.getCreator());
				kpi2.setCycle(kpi.getCycle());
				kpi2.setDeleted(kpi.getDeleted());
				kpi2.setSiteEvaScore(kpi.getSiteEvaScore());
				kpi2.setKpiName(kpi.getKpiName());
				kpi2.setRemark(kpi.getRemark());
				kpi2.setRuleGroupId(kpi.getRuleGroupId());
				kpi2.setThreshold(kpi.getThreshold());
				kpi2.setWeight(kpi.getWeight());
				
	//			// 保存新指标
	//			UUIDHexGenerator uu = UUIDHexGenerator.getInstance();
	//			try {
	//				//kpi.setId(uu.getID());
	//			} catch (Exception e) {
	//				// TODO Auto-generated catch block
	//				e.printStackTrace();
	//			}
				Logger logger = Logger.getLogger(this.getClass());
				logger.info("\n保存kpi前");
				siteEvaKpiDao.saveKpi(kpi2);
				logger.info("\n保存kpi后");
				node.setObjectId(kpi2.getId());
				// 更新权重
				treeMgr.saveTreeNode(node);
//			}
//			else{
//				siteEvaKpiDao.saveKpi(kpi);
//			}

		}
	}

	public void removeKpiOfType(final String parentNodeId) {
		siteEvaKpiDao.removeKpiOfType(parentNodeId);
	}

	public Float getScoreOfKpi(String templateId, String kpiId, String date) {
		ISiteEvaKpiInstanceMgr instanceMgr = (ISiteEvaKpiInstanceMgr) ApplicationContextHolder
				.getInstance().getBean("IsiteEvaKpiInstanceMgr");
		SiteEvaKpiInstance instance = instanceMgr.getKpiInstance(templateId, kpiId,
				date);
		//return instance.getKpiScore();
		return Float.valueOf(0);
	}

	public String id2Name(String id) {
		return siteEvaKpiDao.id2Name(id);
	}

	public Float getNextLevelUsableWt(String parentNodeId) {
		Float usableWt = SiteEvaConstants.DEFAULT_MAX_WT;
		ISiteEvaTreeMgr treeMgr = (ISiteEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("IsiteEvaTreeMgr");
		List list = treeMgr.listNextLevelNode(parentNodeId,
				SiteEvaConstants.NODETYPE_KPI);
		for (Iterator it = list.iterator(); it.hasNext();) {
			SiteEvaTree node = (SiteEvaTree) it.next();
			usableWt = Float.valueOf(usableWt.floatValue()
					- node.getWeight().floatValue());
		}
		if (usableWt.floatValue() < 0) {
			usableWt = new Float(0);
		}
		return usableWt;
	}

	public Float getMinWt(String parentNodeId, String kpiId) {
		ISiteEvaTreeMgr treeMgr = (ISiteEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("IsiteEvaTreeMgr");
		Float minWt = SiteEvaConstants.DEFAULT_MIN_WT;
		// 如果是编辑指标
		if (null != kpiId && !"".equals(kpiId)) {
			// 获得当前编辑节点
			SiteEvaTree currentNode = treeMgr.getNodeByObjId(parentNodeId, kpiId);
			// 获得当前节点下级节点列表
			List list = treeMgr.listNextLevelNode(currentNode.getNodeId(),
					SiteEvaConstants.NODETYPE_KPI);
			// 加上当前节点下级的权重
			for (Iterator it = list.iterator(); it.hasNext();) {
				SiteEvaTree node = (SiteEvaTree) it.next();
				minWt = Float.valueOf(minWt.floatValue()
						+ node.getWeight().floatValue());
			}
		}
		return minWt;
	}

	public Float getMaxWt(String parentNodeId, String kpiId) {
		ISiteEvaTreeMgr treeMgr = (ISiteEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("IsiteEvaTreeMgr");
		// 可分配最大权重
		Float maxWt = SiteEvaConstants.DEFAULT_MAX_WT;
		// 上级节点
		SiteEvaTree parentNode = treeMgr.getTreeNodeByNodeId(parentNodeId);
		if (null != parentNode.getId() && !"".equals(parentNode.getId())) {
			maxWt = parentNode.getWeight();
		}
		// 下级kpi列表
		List list = treeMgr.listNextLevelNode(parentNodeId,
				SiteEvaConstants.NODETYPE_KPI);
		// 减去下级所有节点权重
		for (Iterator it = list.iterator(); it.hasNext();) {
			SiteEvaTree node = (SiteEvaTree) it.next();
			maxWt = Float.valueOf(maxWt.floatValue()
					- node.getWeight().floatValue());
		}
		// 如果是编辑指标
		if (null != kpiId && !"".equals(kpiId)) {
			SiteEvaTree currentNode = treeMgr.getNodeByObjId(parentNodeId, kpiId);
			maxWt = Float.valueOf(maxWt.floatValue()
					+ currentNode.getWeight().floatValue());
		}
		return maxWt;
	}
	/**
	 * 判断指标名称是否唯一
	 * 
	 *      
	 */
	public Boolean isunique(final String kpiName,final String parentNodeId){
		return siteEvaKpiDao.isunique(kpiName, parentNodeId);
	}
}
