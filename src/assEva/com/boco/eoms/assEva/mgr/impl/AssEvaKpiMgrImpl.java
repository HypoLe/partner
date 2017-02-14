package com.boco.eoms.assEva.mgr.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.UUIDHexGenerator;
import com.boco.eoms.assEva.dao.IAssEvaKpiDao;
import com.boco.eoms.assEva.mgr.IAssEvaKpiInstanceMgr;
import com.boco.eoms.assEva.mgr.IAssEvaKpiMgr;
import com.boco.eoms.assEva.mgr.IAssEvaTreeMgr;
import com.boco.eoms.assEva.model.AssEvaKpi;
import com.boco.eoms.assEva.model.AssEvaKpiInstance;
import com.boco.eoms.assEva.model.AssEvaTree;
import com.boco.eoms.assEva.util.AssEvaConstants;
import com.boco.eoms.log4j.Logger;

public class AssEvaKpiMgrImpl implements IAssEvaKpiMgr {

	private IAssEvaKpiDao assEvaKpiDao;

	public IAssEvaKpiDao getAssEvaKpiDao() {
		return assEvaKpiDao;
	}

	public void setAssEvaKpiDao(IAssEvaKpiDao assEvaKpiDao) {
		this.assEvaKpiDao = assEvaKpiDao;
	}

	public AssEvaKpi getKpi(String id) {
		return assEvaKpiDao.getKpi(id);
	}

	public AssEvaKpi getKpi(String id, String deleted) {
		return assEvaKpiDao.getKpi(id, deleted);
	}

	public AssEvaKpi getKpiByNodeId(String nodeId) {
		return assEvaKpiDao.getKpiByNodeId(nodeId);
	}

	public ArrayList listKpiOfType(String parentNodeId) {
		return assEvaKpiDao.listKpiOfType(parentNodeId);
	}

	public void removeKpi(AssEvaKpi kpi) {
		// 假删除指标
		assEvaKpiDao.removeKpi(kpi);
	}

	public void saveKpi(AssEvaKpi kpi) {
		assEvaKpiDao.saveKpi(kpi);
	}

	public void saveKpiAndNode(AssEvaKpi kpi, String parentNodeId){
		
	}
	public void saveKpiAndNode(AssEvaKpi kpi, String parentNodeId,String isActived) {
		IAssEvaTreeMgr treeMgr = (IAssEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("IassEvaTreeMgr");

		if (null == kpi.getId() || "".equals(kpi.getId())) { // 新指标
			// 保存指标
			kpi.setDeleted(AssEvaConstants.UNDELETED);
			assEvaKpiDao.saveKpi(kpi);

			AssEvaTree assEvaTree = new AssEvaTree();

			// 生成新节点ID
			String newNodeId = treeMgr.getMaxNodeId(parentNodeId);

			// 保存树节点
			assEvaTree.setNodeId(newNodeId);
			assEvaTree.setParentNodeId(parentNodeId);
			assEvaTree.setNodeName(kpi.getKpiName());
			assEvaTree.setNodeType(AssEvaConstants.NODETYPE_KPI);
			assEvaTree.setLeaf(AssEvaConstants.NODE_LEAF);
			assEvaTree.setObjectId(kpi.getId());
			assEvaTree.setWeight(kpi.getWeight());
			treeMgr.saveTreeNode(assEvaTree);

			// 更新父节点leaf
			treeMgr.updateParentNodeLeaf(parentNodeId,
					AssEvaConstants.NODE_NOTLEAF);
		} else { // 修改指标
			// 获得对应树节点
				AssEvaTree node = treeMgr.getNodeByObjId(parentNodeId, kpi.getId());
				// 设置权重
				node.setWeight(kpi.getWeight());
				node.setNodeName(kpi.getKpiName());
//				if(isActived.equals(AssEvaConstants.TEMPLATE_ACTIVATED)){//如果模版激活则复制指标
				AssEvaKpi kpi2 = new AssEvaKpi();
				kpi2.setAlgorithm(kpi.getAlgorithm());
				kpi2.setCreateTime(kpi.getCreateTime());
				kpi2.setCreator(kpi.getCreator());
				kpi2.setCycle(kpi.getCycle());
				kpi2.setDeleted(kpi.getDeleted());
				kpi2.setAssEvaScore(kpi.getAssEvaScore());
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
				assEvaKpiDao.saveKpi(kpi2);
				logger.info("\n保存kpi后");
				node.setObjectId(kpi2.getId());
				// 更新权重
				treeMgr.saveTreeNode(node);
//			}
//			else{
//				assEvaKpiDao.saveKpi(kpi);
//			}

		}
	}

	public void removeKpiOfType(final String parentNodeId) {
		assEvaKpiDao.removeKpiOfType(parentNodeId);
	}

	public Float getScoreOfKpi(String templateId, String kpiId, String date) {
		IAssEvaKpiInstanceMgr instanceMgr = (IAssEvaKpiInstanceMgr) ApplicationContextHolder
				.getInstance().getBean("IassEvaKpiInstanceMgr");
		AssEvaKpiInstance instance = instanceMgr.getKpiInstance(templateId, kpiId,
				date);
		//return instance.getKpiScore();
		return Float.valueOf(0);
	}

	public String id2Name(String id) {
		return assEvaKpiDao.id2Name(id);
	}

	public Float getNextLevelUsableWt(String parentNodeId) {
		Float usableWt = AssEvaConstants.DEFAULT_MAX_WT;
		IAssEvaTreeMgr treeMgr = (IAssEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("IassEvaTreeMgr");
		List list = treeMgr.listNextLevelNode(parentNodeId,
				AssEvaConstants.NODETYPE_KPI);
		for (Iterator it = list.iterator(); it.hasNext();) {
			AssEvaTree node = (AssEvaTree) it.next();
			usableWt = Float.valueOf(usableWt.floatValue()
					- node.getWeight().floatValue());
		}
		if (usableWt.floatValue() < 0) {
			usableWt = new Float(0);
		}
		return usableWt;
	}

	public Float getMinWt(String parentNodeId, String kpiId) {
		IAssEvaTreeMgr treeMgr = (IAssEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("IassEvaTreeMgr");
		Float minWt = AssEvaConstants.DEFAULT_MIN_WT;
		// 如果是编辑指标
		if (null != kpiId && !"".equals(kpiId)) {
			// 获得当前编辑节点
			AssEvaTree currentNode = treeMgr.getNodeByObjId(parentNodeId, kpiId);
			// 获得当前节点下级节点列表
			List list = treeMgr.listNextLevelNode(currentNode.getNodeId(),
					AssEvaConstants.NODETYPE_KPI);
			// 加上当前节点下级的权重
			for (Iterator it = list.iterator(); it.hasNext();) {
				AssEvaTree node = (AssEvaTree) it.next();
				minWt = Float.valueOf(minWt.floatValue()
						+ node.getWeight().floatValue());
			}
		}
		return minWt;
	}

	public Float getMaxWt(String parentNodeId, String kpiId) {
		IAssEvaTreeMgr treeMgr = (IAssEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("IassEvaTreeMgr");
		// 可分配最大权重
		Float maxWt = AssEvaConstants.DEFAULT_MAX_WT;
		// 上级节点
		AssEvaTree parentNode = treeMgr.getTreeNodeByNodeId(parentNodeId);
		if (null != parentNode.getId() && !"".equals(parentNode.getId())) {
			maxWt = parentNode.getWeight();
		}
		// 下级kpi列表
		List list = treeMgr.listNextLevelNode(parentNodeId,
				AssEvaConstants.NODETYPE_KPI);
		// 减去下级所有节点权重
		for (Iterator it = list.iterator(); it.hasNext();) {
			AssEvaTree node = (AssEvaTree) it.next();
			maxWt = Float.valueOf(maxWt.floatValue()
					- node.getWeight().floatValue());
		}
		// 如果是编辑指标
		if (null != kpiId && !"".equals(kpiId)) {
			AssEvaTree currentNode = treeMgr.getNodeByObjId(parentNodeId, kpiId);
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
		return assEvaKpiDao.isunique(kpiName, parentNodeId);
	}
}
