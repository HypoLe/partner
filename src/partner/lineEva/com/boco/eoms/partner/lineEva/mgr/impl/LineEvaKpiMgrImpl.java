package com.boco.eoms.partner.lineEva.mgr.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.UUIDHexGenerator;
import com.boco.eoms.partner.lineEva.dao.ILineEvaKpiDao;
import com.boco.eoms.partner.lineEva.mgr.ILineEvaKpiInstanceMgr;
import com.boco.eoms.partner.lineEva.mgr.ILineEvaKpiMgr;
import com.boco.eoms.partner.lineEva.mgr.ILineEvaTreeMgr;
import com.boco.eoms.partner.lineEva.model.LineEvaKpi;
import com.boco.eoms.partner.lineEva.model.LineEvaKpiInstance;
import com.boco.eoms.partner.lineEva.model.LineEvaTree;
import com.boco.eoms.partner.lineEva.util.LineEvaConstants;
import com.boco.eoms.log4j.Logger;

public class LineEvaKpiMgrImpl implements ILineEvaKpiMgr {

	private ILineEvaKpiDao lineEvaKpiDao;

	public ILineEvaKpiDao getLineEvaKpiDao() {
		return lineEvaKpiDao;
	}

	public void setLineEvaKpiDao(ILineEvaKpiDao lineEvaKpiDao) {
		this.lineEvaKpiDao = lineEvaKpiDao;
	}

	public LineEvaKpi getKpi(String id) {
		return lineEvaKpiDao.getKpi(id);
	}

	public LineEvaKpi getKpi(String id, String deleted) {
		return lineEvaKpiDao.getKpi(id, deleted);
	}

	public LineEvaKpi getKpiByNodeId(String nodeId) {
		return lineEvaKpiDao.getKpiByNodeId(nodeId);
	}

	public ArrayList listKpiOfType(String parentNodeId) {
		return lineEvaKpiDao.listKpiOfType(parentNodeId);
	}

	public void removeKpi(LineEvaKpi kpi) {
		// 假删除指标
		lineEvaKpiDao.removeKpi(kpi);
	}

	public void saveKpi(LineEvaKpi kpi) {
		lineEvaKpiDao.saveKpi(kpi);
	}

	public void saveKpiAndNode(LineEvaKpi kpi, String parentNodeId){
		
	}
	public void saveKpiAndNode(LineEvaKpi kpi, String parentNodeId,String isActived) {
		ILineEvaTreeMgr treeMgr = (ILineEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("IlineEvaTreeMgr");

		if (null == kpi.getId() || "".equals(kpi.getId())) { // 新指标
			// 保存指标
			kpi.setDeleted(LineEvaConstants.UNDELETED);
			lineEvaKpiDao.saveKpi(kpi);

			LineEvaTree lineEvaTree = new LineEvaTree();

			// 生成新节点ID
			String newNodeId = treeMgr.getMaxNodeId(parentNodeId);

			// 保存树节点
			lineEvaTree.setNodeId(newNodeId);
			lineEvaTree.setParentNodeId(parentNodeId);
			lineEvaTree.setNodeName(kpi.getKpiName());
			lineEvaTree.setNodeType(LineEvaConstants.NODETYPE_KPI);
			lineEvaTree.setLeaf(LineEvaConstants.NODE_LEAF);
			lineEvaTree.setObjectId(kpi.getId());
			lineEvaTree.setWeight(kpi.getWeight());
			treeMgr.saveTreeNode(lineEvaTree);

			// 更新父节点leaf
			treeMgr.updateParentNodeLeaf(parentNodeId,
					LineEvaConstants.NODE_NOTLEAF);
		} else { // 修改指标
			// 获得对应树节点
				LineEvaTree node = treeMgr.getNodeByObjId(parentNodeId, kpi.getId());
				// 设置权重
				node.setWeight(kpi.getWeight());
				node.setNodeName(kpi.getKpiName());
//				if(isActived.equals(LineEvaConstants.TEMPLATE_ACTIVATED)){//如果模版激活则复制指标
				LineEvaKpi kpi2 = new LineEvaKpi();
				kpi2.setAlgorithm(kpi.getAlgorithm());
				kpi2.setCreateTime(kpi.getCreateTime());
				kpi2.setCreator(kpi.getCreator());
				kpi2.setCycle(kpi.getCycle());
				kpi2.setDeleted(kpi.getDeleted());
				kpi2.setLineEvaScore(kpi.getLineEvaScore());
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
				lineEvaKpiDao.saveKpi(kpi2);
				logger.info("\n保存kpi后");
				node.setObjectId(kpi2.getId());
				// 更新权重
				treeMgr.saveTreeNode(node);
//			}
//			else{
//				lineEvaKpiDao.saveKpi(kpi);
//			}

		}
	}

	public void removeKpiOfType(final String parentNodeId) {
		lineEvaKpiDao.removeKpiOfType(parentNodeId);
	}

	public Float getScoreOfKpi(String templateId, String kpiId, String date) {
		ILineEvaKpiInstanceMgr instanceMgr = (ILineEvaKpiInstanceMgr) ApplicationContextHolder
				.getInstance().getBean("IlineEvaKpiInstanceMgr");
		LineEvaKpiInstance instance = instanceMgr.getKpiInstance(templateId, kpiId,
				date);
		//return instance.getKpiScore();
		return Float.valueOf(0);
	}

	public String id2Name(String id) {
		return lineEvaKpiDao.id2Name(id);
	}

	public Float getNextLevelUsableWt(String parentNodeId) {
		Float usableWt = LineEvaConstants.DEFAULT_MAX_WT;
		ILineEvaTreeMgr treeMgr = (ILineEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("IlineEvaTreeMgr");
		List list = treeMgr.listNextLevelNode(parentNodeId,
				LineEvaConstants.NODETYPE_KPI);
		for (Iterator it = list.iterator(); it.hasNext();) {
			LineEvaTree node = (LineEvaTree) it.next();
			usableWt = Float.valueOf(usableWt.floatValue()
					- node.getWeight().floatValue());
		}
		if (usableWt.floatValue() < 0) {
			usableWt = new Float(0);
		}
		return usableWt;
	}

	public Float getMinWt(String parentNodeId, String kpiId) {
		ILineEvaTreeMgr treeMgr = (ILineEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("IlineEvaTreeMgr");
		Float minWt = LineEvaConstants.DEFAULT_MIN_WT;
		// 如果是编辑指标
		if (null != kpiId && !"".equals(kpiId)) {
			// 获得当前编辑节点
			LineEvaTree currentNode = treeMgr.getNodeByObjId(parentNodeId, kpiId);
			// 获得当前节点下级节点列表
			List list = treeMgr.listNextLevelNode(currentNode.getNodeId(),
					LineEvaConstants.NODETYPE_KPI);
			// 加上当前节点下级的权重
			for (Iterator it = list.iterator(); it.hasNext();) {
				LineEvaTree node = (LineEvaTree) it.next();
				minWt = Float.valueOf(minWt.floatValue()
						+ node.getWeight().floatValue());
			}
		}
		return minWt;
	}

	public Float getMaxWt(String parentNodeId, String kpiId) {
		ILineEvaTreeMgr treeMgr = (ILineEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("IlineEvaTreeMgr");
		// 可分配最大权重
		Float maxWt = LineEvaConstants.DEFAULT_MAX_WT;
		// 上级节点
		LineEvaTree parentNode = treeMgr.getTreeNodeByNodeId(parentNodeId);
		if (null != parentNode.getId() && !"".equals(parentNode.getId())) {
			maxWt = parentNode.getWeight();
		}
		// 下级kpi列表
		List list = treeMgr.listNextLevelNode(parentNodeId,
				LineEvaConstants.NODETYPE_KPI);
		// 减去下级所有节点权重
		for (Iterator it = list.iterator(); it.hasNext();) {
			LineEvaTree node = (LineEvaTree) it.next();
			maxWt = Float.valueOf(maxWt.floatValue()
					- node.getWeight().floatValue());
		}
		// 如果是编辑指标
		if (null != kpiId && !"".equals(kpiId)) {
			LineEvaTree currentNode = treeMgr.getNodeByObjId(parentNodeId, kpiId);
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
		return lineEvaKpiDao.isunique(kpiName, parentNodeId);
	}
}
