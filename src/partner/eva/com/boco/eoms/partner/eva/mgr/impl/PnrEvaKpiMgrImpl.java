package com.boco.eoms.partner.eva.mgr.impl;

import java.util.Iterator;
import java.util.List;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.partner.eva.dao.IPnrEvaKpiDao;
import com.boco.eoms.partner.eva.mgr.IPnrEvaKpiMgr;
import com.boco.eoms.partner.eva.mgr.IPnrEvaTreeMgr;
import com.boco.eoms.partner.eva.model.PnrEvaKpi;
import com.boco.eoms.partner.eva.model.PnrEvaTree;
import com.boco.eoms.partner.eva.util.PnrEvaConstants;
import com.boco.eoms.partner.eva.util.PnrEvaDateUtil;
import com.boco.eoms.log4j.Logger;

public class PnrEvaKpiMgrImpl implements IPnrEvaKpiMgr {

	private IPnrEvaKpiDao pnrEvaKpiDao;

	public IPnrEvaKpiDao getPnrEvaKpiDao() {
		return pnrEvaKpiDao;
	}

	public void setPnrEvaKpiDao(IPnrEvaKpiDao pnrEvaKpiDao) {
		this.pnrEvaKpiDao = pnrEvaKpiDao;
	}

	public PnrEvaKpi getKpi(String id) {
		return pnrEvaKpiDao.getKpi(id);
	}

	public PnrEvaKpi getKpi(String id, String deleted) {
		return pnrEvaKpiDao.getKpi(id, deleted);
	}


	public void removeKpi(PnrEvaKpi kpi) {
		// 假删除指标
		pnrEvaKpiDao.removeKpi(kpi);
	}

	public void saveKpi(PnrEvaKpi kpi) {
		pnrEvaKpiDao.saveKpi(kpi);
	}

	public void saveKpiAndNode(PnrEvaKpi kpi, String parentNodeId){
		
	}
	public void saveKpiAndNode(PnrEvaKpi kpi, String parentNodeId,String isActived) {
		IPnrEvaTreeMgr treeMgr = (IPnrEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("iPnrEvaTreeMgr");

		if (null == kpi.getId() || "".equals(kpi.getId())) { // 新指标
			// 保存指标
			kpi.setDeleted(PnrEvaConstants.UNDELETED);
			pnrEvaKpiDao.saveKpi(kpi);

			PnrEvaTree evaTree = new PnrEvaTree();

			// 生成新节点ID
			String newNodeId = treeMgr.getMaxNodeId(parentNodeId);

			// 保存树节点
			evaTree.setNodeId(newNodeId);
			evaTree.setParentNodeId(parentNodeId);
			evaTree.setNodeName(kpi.getKpiName());
			evaTree.setNodeType(PnrEvaConstants.NODETYPE_KPI);
			evaTree.setIsLock(PnrEvaConstants.UNLOCK);
			evaTree.setLeaf(PnrEvaConstants.NODE_LEAF);
			evaTree.setObjectId(kpi.getId());
			evaTree.setWeight(kpi.getWeight());
			evaTree.setCreateTime(PnrEvaDateUtil.getTimestamp(kpi.getCreateTime()));
			evaTree.setCreateUser(kpi.getCreator());
			evaTree.setCreatArea(kpi.getArea());
			evaTree.setCreatArea(kpi.getArea());
			treeMgr.saveTreeNode(evaTree);

			// 更新父节点leaf
			treeMgr.updateParentNodeLeaf(parentNodeId,
					PnrEvaConstants.NODE_NOTLEAF);
		} else { // 修改指标
			// 获得对应树节点
				PnrEvaTree node = treeMgr.getNodeByObjId(parentNodeId, kpi.getId());
				// 设置权重
				node.setWeight(kpi.getWeight());
				node.setNodeName(kpi.getKpiName());
//				if(isActived.equals(EvaConstants.TEMPLATE_ACTIVATED)){//如果模版激活则复制指标
				PnrEvaKpi kpi2 = new PnrEvaKpi();
				kpi2.setAlgorithm(kpi.getAlgorithm());
				kpi2.setCreateTime(kpi.getCreateTime());
				kpi2.setCreator(kpi.getCreator());
				kpi2.setCycle(kpi.getCycle());
				kpi2.setDeleted(kpi.getDeleted());
				kpi2.setEvaScore(kpi.getEvaScore());
				kpi2.setKpiName(kpi.getKpiName());
				kpi2.setRemark(kpi.getRemark());
				kpi2.setRuleGroupId(kpi.getRuleGroupId());
				kpi2.setThreshold(kpi.getThreshold());
				kpi2.setWeight(kpi.getWeight());
				kpi2.setDefaultValue(kpi.getDefaultValue());
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
				pnrEvaKpiDao.saveKpi(kpi2);
				logger.info("\n保存kpi后");
				node.setObjectId(kpi2.getId());
				// 更新权重
				treeMgr.saveTreeNode(node);
//			}
//			else{
//				pnrEvaKpiDao.saveKpi(kpi);
//			}

		}
	}


	public float getScoreOfKpi(String templateId, String kpiId, String date) {
//		IPnrEvaKpiInstanceMgr instanceMgr = (IPnrEvaKpiInstanceMgr) ApplicationContextHolder
//				.getInstance().getBean("iPnrEvaKpiInstanceMgr");
//		PnrEvaKpiInstance instance = instanceMgr.getKpiInstance(templateId, kpiId,date);
//      return instance.getKpiScore();
		return 0;
	}

	public String id2Name(String id) {
		return pnrEvaKpiDao.id2Name(id);
	}

	public float getNextLevelUsableWt(String parentNodeId) {
		float usableWt = PnrEvaConstants.DEFAULT_MAX_WT;
		IPnrEvaTreeMgr treeMgr = (IPnrEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("iPnrEvaTreeMgr");
		List list = treeMgr.listNextLevelNode(parentNodeId,
				PnrEvaConstants.NODETYPE_KPI);
		for (Iterator it = list.iterator(); it.hasNext();) {
			PnrEvaTree node = (PnrEvaTree) it.next();
			usableWt = usableWt - node.getWeight();
		}
		if (usableWt < 0) {
			usableWt = 0;
		}
		return usableWt;
	}

	public float getMinWt(String parentNodeId, String kpiId) {
		IPnrEvaTreeMgr treeMgr = (IPnrEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("iPnrEvaTreeMgr");
		float minWt = PnrEvaConstants.DEFAULT_MIN_WT;
		// 如果是编辑指标
		if (null != kpiId && !"".equals(kpiId)) {
			// 获得当前编辑节点
			PnrEvaTree currentNode = treeMgr.getNodeByObjId(parentNodeId, kpiId);
			// 获得当前节点下级节点列表
			List list = treeMgr.listNextLevelNode(currentNode.getNodeId(),
					PnrEvaConstants.NODETYPE_KPI);
			// 加上当前节点下级的权重
			for (Iterator it = list.iterator(); it.hasNext();) {
				PnrEvaTree node = (PnrEvaTree) it.next();
				minWt = minWt+ node.getWeight();
			}
		}
		return minWt;
	}

	public float getMaxWt(String parentNodeId, String kpiId) {
		IPnrEvaTreeMgr treeMgr = (IPnrEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("iPnrEvaTreeMgr");
		// 可分配最大权重
		float maxWt = PnrEvaConstants.DEFAULT_MAX_WT;
		// 上级节点
		PnrEvaTree parentNode = treeMgr.getTreeNodeByNodeId(parentNodeId);
		if (null != parentNode.getId() && !"".equals(parentNode.getId())) {
			maxWt = parentNode.getWeight();
		}
		// 下级kpi列表
		List list = treeMgr.listNextLevelNode(parentNodeId,
				PnrEvaConstants.NODETYPE_KPI);
		// 减去下级所有节点权重
		for (Iterator it = list.iterator(); it.hasNext();) {
			PnrEvaTree node = (PnrEvaTree) it.next();
			maxWt = maxWt - node.getWeight();
		}
		// 如果是编辑指标
		if (null != kpiId && !"".equals(kpiId)) {
			PnrEvaTree currentNode = treeMgr.getNodeByObjId(parentNodeId, kpiId);
			maxWt = maxWt + currentNode.getWeight();
		}
		return maxWt;
	}
	/**
	 * 判断指标名称是否唯一
	 * 
	 *      
	 */
	public Boolean isunique(final String kpiName,final String parentNodeId){
		return pnrEvaKpiDao.isunique(kpiName, parentNodeId);
	}
}
