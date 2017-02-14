package com.boco.eoms.partner.chanEva.mgr.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.UUIDHexGenerator;
import com.boco.eoms.partner.chanEva.dao.IChanEvaKpiDao;
import com.boco.eoms.partner.chanEva.mgr.IChanEvaKpiInstanceMgr;
import com.boco.eoms.partner.chanEva.mgr.IChanEvaKpiMgr;
import com.boco.eoms.partner.chanEva.mgr.IChanEvaTreeMgr;
import com.boco.eoms.partner.chanEva.model.ChanEvaKpi;
import com.boco.eoms.partner.chanEva.model.ChanEvaKpiInstance;
import com.boco.eoms.partner.chanEva.model.ChanEvaTree;
import com.boco.eoms.partner.chanEva.util.ChanEvaConstants;
import com.boco.eoms.log4j.Logger;

public class ChanEvaKpiMgrImpl implements IChanEvaKpiMgr {

	private IChanEvaKpiDao chanEvaKpiDao;

	public IChanEvaKpiDao getChanEvaKpiDao() {
		return chanEvaKpiDao;
	}

	public void setChanEvaKpiDao(IChanEvaKpiDao chanEvaKpiDao) {
		this.chanEvaKpiDao = chanEvaKpiDao;
	}

	public ChanEvaKpi getKpi(String id) {
		return chanEvaKpiDao.getKpi(id);
	}

	public ChanEvaKpi getKpi(String id, String deleted) {
		return chanEvaKpiDao.getKpi(id, deleted);
	}

	public ChanEvaKpi getKpiByNodeId(String nodeId) {
		return chanEvaKpiDao.getKpiByNodeId(nodeId);
	}

	public ArrayList listKpiOfType(String parentNodeId) {
		return chanEvaKpiDao.listKpiOfType(parentNodeId);
	}

	public void removeKpi(ChanEvaKpi kpi) {
		// 假删除指标
		chanEvaKpiDao.removeKpi(kpi);
	}

	public void saveKpi(ChanEvaKpi kpi) {
		chanEvaKpiDao.saveKpi(kpi);
	}

	public void saveKpiAndNode(ChanEvaKpi kpi, String parentNodeId){
		
	}
	public void saveKpiAndNode(ChanEvaKpi kpi, String parentNodeId,String isActived) {
		IChanEvaTreeMgr treeMgr = (IChanEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("IchanEvaTreeMgr");

		if (null == kpi.getId() || "".equals(kpi.getId())) { // 新指标
			// 保存指标
			kpi.setDeleted(ChanEvaConstants.UNDELETED);
			chanEvaKpiDao.saveKpi(kpi);

			ChanEvaTree chanEvaTree = new ChanEvaTree();

			// 生成新节点ID
			String newNodeId = treeMgr.getMaxNodeId(parentNodeId);

			// 保存树节点
			chanEvaTree.setNodeId(newNodeId);
			chanEvaTree.setParentNodeId(parentNodeId);
			chanEvaTree.setNodeName(kpi.getKpiName());
			chanEvaTree.setNodeType(ChanEvaConstants.NODETYPE_KPI);
			chanEvaTree.setLeaf(ChanEvaConstants.NODE_LEAF);
			chanEvaTree.setObjectId(kpi.getId());
			chanEvaTree.setWeight(kpi.getWeight());
			treeMgr.saveTreeNode(chanEvaTree);

			// 更新父节点leaf
			treeMgr.updateParentNodeLeaf(parentNodeId,
					ChanEvaConstants.NODE_NOTLEAF);
		} else { // 修改指标
			// 获得对应树节点
				ChanEvaTree node = treeMgr.getNodeByObjId(parentNodeId, kpi.getId());
				// 设置权重
				node.setWeight(kpi.getWeight());
				node.setNodeName(kpi.getKpiName());
//				if(isActived.equals(ChanEvaConstants.TEMPLATE_ACTIVATED)){//如果模版激活则复制指标
				ChanEvaKpi kpi2 = new ChanEvaKpi();
				kpi2.setAlgorithm(kpi.getAlgorithm());
				kpi2.setCreateTime(kpi.getCreateTime());
				kpi2.setCreator(kpi.getCreator());
				kpi2.setCycle(kpi.getCycle());
				kpi2.setDeleted(kpi.getDeleted());
				kpi2.setChanEvaScore(kpi.getChanEvaScore());
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
				chanEvaKpiDao.saveKpi(kpi2);
				logger.info("\n保存kpi后");
				node.setObjectId(kpi2.getId());
				// 更新权重
				treeMgr.saveTreeNode(node);
//			}
//			else{
//				chanEvaKpiDao.saveKpi(kpi);
//			}

		}
	}

	public void removeKpiOfType(final String parentNodeId) {
		chanEvaKpiDao.removeKpiOfType(parentNodeId);
	}

	public Float getScoreOfKpi(String templateId, String kpiId, String date) {
		IChanEvaKpiInstanceMgr instanceMgr = (IChanEvaKpiInstanceMgr) ApplicationContextHolder
				.getInstance().getBean("IchanEvaKpiInstanceMgr");
		ChanEvaKpiInstance instance = instanceMgr.getKpiInstance(templateId, kpiId,
				date);
		//return instance.getKpiScore();
		return Float.valueOf(0);
	}

	public String id2Name(String id) {
		return chanEvaKpiDao.id2Name(id);
	}

	public Float getNextLevelUsableWt(String parentNodeId) {
		Float usableWt = ChanEvaConstants.DEFAULT_MAX_WT;
		IChanEvaTreeMgr treeMgr = (IChanEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("IchanEvaTreeMgr");
		List list = treeMgr.listNextLevelNode(parentNodeId,
				ChanEvaConstants.NODETYPE_KPI);
		for (Iterator it = list.iterator(); it.hasNext();) {
			ChanEvaTree node = (ChanEvaTree) it.next();
			usableWt = Float.valueOf(usableWt.floatValue()
					- node.getWeight().floatValue());
		}
		if (usableWt.floatValue() < 0) {
			usableWt = new Float(0);
		}
		return usableWt;
	}

	public Float getMinWt(String parentNodeId, String kpiId) {
		IChanEvaTreeMgr treeMgr = (IChanEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("IchanEvaTreeMgr");
		Float minWt = ChanEvaConstants.DEFAULT_MIN_WT;
		// 如果是编辑指标
		if (null != kpiId && !"".equals(kpiId)) {
			// 获得当前编辑节点
			ChanEvaTree currentNode = treeMgr.getNodeByObjId(parentNodeId, kpiId);
			// 获得当前节点下级节点列表
			List list = treeMgr.listNextLevelNode(currentNode.getNodeId(),
					ChanEvaConstants.NODETYPE_KPI);
			// 加上当前节点下级的权重
			for (Iterator it = list.iterator(); it.hasNext();) {
				ChanEvaTree node = (ChanEvaTree) it.next();
				minWt = Float.valueOf(minWt.floatValue()
						+ node.getWeight().floatValue());
			}
		}
		return minWt;
	}

	public Float getMaxWt(String parentNodeId, String kpiId) {
		IChanEvaTreeMgr treeMgr = (IChanEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("IchanEvaTreeMgr");
		// 可分配最大权重
		Float maxWt = ChanEvaConstants.DEFAULT_MAX_WT;
		// 上级节点
		ChanEvaTree parentNode = treeMgr.getTreeNodeByNodeId(parentNodeId);
		if (null != parentNode.getId() && !"".equals(parentNode.getId())) {
			maxWt = parentNode.getWeight();
		}
		// 下级kpi列表
		List list = treeMgr.listNextLevelNode(parentNodeId,
				ChanEvaConstants.NODETYPE_KPI);
		// 减去下级所有节点权重
		for (Iterator it = list.iterator(); it.hasNext();) {
			ChanEvaTree node = (ChanEvaTree) it.next();
			maxWt = Float.valueOf(maxWt.floatValue()
					- node.getWeight().floatValue());
		}
		// 如果是编辑指标
		if (null != kpiId && !"".equals(kpiId)) {
			ChanEvaTree currentNode = treeMgr.getNodeByObjId(parentNodeId, kpiId);
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
		return chanEvaKpiDao.isunique(kpiName, parentNodeId);
	}
}
