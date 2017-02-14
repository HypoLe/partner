package com.boco.eoms.partner.tranEva.mgr.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.UUIDHexGenerator;
import com.boco.eoms.partner.tranEva.dao.ITranEvaKpiDao;
import com.boco.eoms.partner.tranEva.mgr.ITranEvaKpiInstanceMgr;
import com.boco.eoms.partner.tranEva.mgr.ITranEvaKpiMgr;
import com.boco.eoms.partner.tranEva.mgr.ITranEvaTreeMgr;
import com.boco.eoms.partner.tranEva.model.TranEvaKpi;
import com.boco.eoms.partner.tranEva.model.TranEvaKpiInstance;
import com.boco.eoms.partner.tranEva.model.TranEvaTree;
import com.boco.eoms.partner.tranEva.util.TranEvaConstants;
import com.boco.eoms.log4j.Logger;

public class TranEvaKpiMgrImpl implements ITranEvaKpiMgr {

	private ITranEvaKpiDao tranEvaKpiDao;

	public ITranEvaKpiDao getTranEvaKpiDao() {
		return tranEvaKpiDao;
	}

	public void setTranEvaKpiDao(ITranEvaKpiDao tranEvaKpiDao) {
		this.tranEvaKpiDao = tranEvaKpiDao;
	}

	public TranEvaKpi getKpi(String id) {
		return tranEvaKpiDao.getKpi(id);
	}

	public TranEvaKpi getKpi(String id, String deleted) {
		return tranEvaKpiDao.getKpi(id, deleted);
	}

	public TranEvaKpi getKpiByNodeId(String nodeId) {
		return tranEvaKpiDao.getKpiByNodeId(nodeId);
	}

	public ArrayList listKpiOfType(String parentNodeId) {
		return tranEvaKpiDao.listKpiOfType(parentNodeId);
	}

	public void removeKpi(TranEvaKpi kpi) {
		// 假删除指标
		tranEvaKpiDao.removeKpi(kpi);
	}

	public void saveKpi(TranEvaKpi kpi) {
		tranEvaKpiDao.saveKpi(kpi);
	}

	public void saveKpiAndNode(TranEvaKpi kpi, String parentNodeId){
		
	}
	public void saveKpiAndNode(TranEvaKpi kpi, String parentNodeId,String isActived) {
		ITranEvaTreeMgr treeMgr = (ITranEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("ItranEvaTreeMgr");

		if (null == kpi.getId() || "".equals(kpi.getId())) { // 新指标
			// 保存指标
			kpi.setDeleted(TranEvaConstants.UNDELETED);
			tranEvaKpiDao.saveKpi(kpi);

			TranEvaTree tranEvaTree = new TranEvaTree();

			// 生成新节点ID
			String newNodeId = treeMgr.getMaxNodeId(parentNodeId);

			// 保存树节点
			tranEvaTree.setNodeId(newNodeId);
			tranEvaTree.setParentNodeId(parentNodeId);
			tranEvaTree.setNodeName(kpi.getKpiName());
			tranEvaTree.setNodeType(TranEvaConstants.NODETYPE_KPI);
			tranEvaTree.setLeaf(TranEvaConstants.NODE_LEAF);
			tranEvaTree.setObjectId(kpi.getId());
			tranEvaTree.setWeight(kpi.getWeight());
			treeMgr.saveTreeNode(tranEvaTree);

			// 更新父节点leaf
			treeMgr.updateParentNodeLeaf(parentNodeId,
					TranEvaConstants.NODE_NOTLEAF);
		} else { // 修改指标
			// 获得对应树节点
				TranEvaTree node = treeMgr.getNodeByObjId(parentNodeId, kpi.getId());
				// 设置权重
				node.setWeight(kpi.getWeight());
				node.setNodeName(kpi.getKpiName());
//				if(isActived.equals(TranEvaConstants.TEMPLATE_ACTIVATED)){//如果模版激活则复制指标
				TranEvaKpi kpi2 = new TranEvaKpi();
				kpi2.setAlgorithm(kpi.getAlgorithm());
				kpi2.setCreateTime(kpi.getCreateTime());
				kpi2.setCreator(kpi.getCreator());
				kpi2.setCycle(kpi.getCycle());
				kpi2.setDeleted(kpi.getDeleted());
				kpi2.setTranEvaScore(kpi.getTranEvaScore());
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
				tranEvaKpiDao.saveKpi(kpi2);
				logger.info("\n保存kpi后");
				node.setObjectId(kpi2.getId());
				// 更新权重
				treeMgr.saveTreeNode(node);
//			}
//			else{
//				tranEvaKpiDao.saveKpi(kpi);
//			}

		}
	}

	public void removeKpiOfType(final String parentNodeId) {
		tranEvaKpiDao.removeKpiOfType(parentNodeId);
	}

	public Float getScoreOfKpi(String templateId, String kpiId, String date) {
		ITranEvaKpiInstanceMgr instanceMgr = (ITranEvaKpiInstanceMgr) ApplicationContextHolder
				.getInstance().getBean("ItranEvaKpiInstanceMgr");
		TranEvaKpiInstance instance = instanceMgr.getKpiInstance(templateId, kpiId,
				date);
		//return instance.getKpiScore();
		return Float.valueOf(0);
	}

	public String id2Name(String id) {
		return tranEvaKpiDao.id2Name(id);
	}

	public Float getNextLevelUsableWt(String parentNodeId) {
		Float usableWt = TranEvaConstants.DEFAULT_MAX_WT;
		ITranEvaTreeMgr treeMgr = (ITranEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("ItranEvaTreeMgr");
		List list = treeMgr.listNextLevelNode(parentNodeId,
				TranEvaConstants.NODETYPE_KPI);
		for (Iterator it = list.iterator(); it.hasNext();) {
			TranEvaTree node = (TranEvaTree) it.next();
			usableWt = Float.valueOf(usableWt.floatValue()
					- node.getWeight().floatValue());
		}
		if (usableWt.floatValue() < 0) {
			usableWt = new Float(0);
		}
		return usableWt;
	}

	public Float getMinWt(String parentNodeId, String kpiId) {
		ITranEvaTreeMgr treeMgr = (ITranEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("ItranEvaTreeMgr");
		Float minWt = TranEvaConstants.DEFAULT_MIN_WT;
		// 如果是编辑指标
		if (null != kpiId && !"".equals(kpiId)) {
			// 获得当前编辑节点
			TranEvaTree currentNode = treeMgr.getNodeByObjId(parentNodeId, kpiId);
			// 获得当前节点下级节点列表
			List list = treeMgr.listNextLevelNode(currentNode.getNodeId(),
					TranEvaConstants.NODETYPE_KPI);
			// 加上当前节点下级的权重
			for (Iterator it = list.iterator(); it.hasNext();) {
				TranEvaTree node = (TranEvaTree) it.next();
				minWt = Float.valueOf(minWt.floatValue()
						+ node.getWeight().floatValue());
			}
		}
		return minWt;
	}

	public Float getMaxWt(String parentNodeId, String kpiId) {
		ITranEvaTreeMgr treeMgr = (ITranEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("ItranEvaTreeMgr");
		// 可分配最大权重
		Float maxWt = TranEvaConstants.DEFAULT_MAX_WT;
		// 上级节点
		TranEvaTree parentNode = treeMgr.getTreeNodeByNodeId(parentNodeId);
		if (null != parentNode.getId() && !"".equals(parentNode.getId())) {
			maxWt = parentNode.getWeight();
		}
		// 下级kpi列表
		List list = treeMgr.listNextLevelNode(parentNodeId,
				TranEvaConstants.NODETYPE_KPI);
		// 减去下级所有节点权重
		for (Iterator it = list.iterator(); it.hasNext();) {
			TranEvaTree node = (TranEvaTree) it.next();
			maxWt = Float.valueOf(maxWt.floatValue()
					- node.getWeight().floatValue());
		}
		// 如果是编辑指标
		if (null != kpiId && !"".equals(kpiId)) {
			TranEvaTree currentNode = treeMgr.getNodeByObjId(parentNodeId, kpiId);
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
		return tranEvaKpiDao.isunique(kpiName, parentNodeId);
	}
}
