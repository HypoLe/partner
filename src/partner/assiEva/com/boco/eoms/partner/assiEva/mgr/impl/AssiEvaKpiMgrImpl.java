package com.boco.eoms.partner.assiEva.mgr.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.UUIDHexGenerator;
import com.boco.eoms.partner.assiEva.dao.IAssiEvaKpiDao;
import com.boco.eoms.partner.assiEva.mgr.IAssiEvaKpiInstanceMgr;
import com.boco.eoms.partner.assiEva.mgr.IAssiEvaKpiMgr;
import com.boco.eoms.partner.assiEva.mgr.IAssiEvaTreeMgr;
import com.boco.eoms.partner.assiEva.model.AssiEvaKpi;
import com.boco.eoms.partner.assiEva.model.AssiEvaKpiInstance;
import com.boco.eoms.partner.assiEva.model.AssiEvaTree;
import com.boco.eoms.partner.assiEva.util.AssiEvaConstants;
import com.boco.eoms.log4j.Logger;

public class AssiEvaKpiMgrImpl implements IAssiEvaKpiMgr {

	private IAssiEvaKpiDao assiEvaKpiDao;

	public IAssiEvaKpiDao getAssiEvaKpiDao() {
		return assiEvaKpiDao;
	}

	public void setAssiEvaKpiDao(IAssiEvaKpiDao assiEvaKpiDao) {
		this.assiEvaKpiDao = assiEvaKpiDao;
	}

	public AssiEvaKpi getKpi(String id) {
		return assiEvaKpiDao.getKpi(id);
	}

	public AssiEvaKpi getKpi(String id, String deleted) {
		return assiEvaKpiDao.getKpi(id, deleted);
	}

	public AssiEvaKpi getKpiByNodeId(String nodeId) {
		return assiEvaKpiDao.getKpiByNodeId(nodeId);
	}

	public ArrayList listKpiOfType(String parentNodeId) {
		return assiEvaKpiDao.listKpiOfType(parentNodeId);
	}

	public void removeKpi(AssiEvaKpi kpi) {
		// 假删除指标
		assiEvaKpiDao.removeKpi(kpi);
	}

	public void saveKpi(AssiEvaKpi kpi) {
		assiEvaKpiDao.saveKpi(kpi);
	}

	public void saveKpiAndNode(AssiEvaKpi kpi, String parentNodeId){
		
	}
	public void saveKpiAndNode(AssiEvaKpi kpi, String parentNodeId,String isActived) {
		IAssiEvaTreeMgr treeMgr = (IAssiEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("IassiEvaTreeMgr");

		if (null == kpi.getId() || "".equals(kpi.getId())) { // 新指标
			// 保存指标
			kpi.setDeleted(AssiEvaConstants.UNDELETED);
			assiEvaKpiDao.saveKpi(kpi);

			AssiEvaTree assiEvaTree = new AssiEvaTree();

			// 生成新节点ID
			String newNodeId = treeMgr.getMaxNodeId(parentNodeId);

			// 保存树节点
			assiEvaTree.setNodeId(newNodeId);
			assiEvaTree.setParentNodeId(parentNodeId);
			assiEvaTree.setNodeName(kpi.getKpiName());
			assiEvaTree.setNodeType(AssiEvaConstants.NODETYPE_KPI);
			assiEvaTree.setLeaf(AssiEvaConstants.NODE_LEAF);
			assiEvaTree.setObjectId(kpi.getId());
			assiEvaTree.setWeight(kpi.getWeight());
			treeMgr.saveTreeNode(assiEvaTree);

			// 更新父节点leaf
			treeMgr.updateParentNodeLeaf(parentNodeId,
					AssiEvaConstants.NODE_NOTLEAF);
		} else { // 修改指标
			// 获得对应树节点
				AssiEvaTree node = treeMgr.getNodeByObjId(parentNodeId, kpi.getId());
				// 设置权重
				node.setWeight(kpi.getWeight());
				node.setNodeName(kpi.getKpiName());
//				if(isActived.equals(AssiEvaConstants.TEMPLATE_ACTIVATED)){//如果模版激活则复制指标
				AssiEvaKpi kpi2 = new AssiEvaKpi();
				kpi2.setAlgorithm(kpi.getAlgorithm());
				kpi2.setCreateTime(kpi.getCreateTime());
				kpi2.setCreator(kpi.getCreator());
				kpi2.setCycle(kpi.getCycle());
				kpi2.setDeleted(kpi.getDeleted());
				kpi2.setAssiEvaScore(kpi.getAssiEvaScore());
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
				assiEvaKpiDao.saveKpi(kpi2);
				logger.info("\n保存kpi后");
				node.setObjectId(kpi2.getId());
				// 更新权重
				treeMgr.saveTreeNode(node);
//			}
//			else{
//				assiEvaKpiDao.saveKpi(kpi);
//			}

		}
	}

	public void removeKpiOfType(final String parentNodeId) {
		assiEvaKpiDao.removeKpiOfType(parentNodeId);
	}

	public Float getScoreOfKpi(String templateId, String kpiId, String date) {
		IAssiEvaKpiInstanceMgr instanceMgr = (IAssiEvaKpiInstanceMgr) ApplicationContextHolder
				.getInstance().getBean("IassiEvaKpiInstanceMgr");
		AssiEvaKpiInstance instance = instanceMgr.getKpiInstance(templateId, kpiId,
				date);
		//return instance.getKpiScore();
		return Float.valueOf(0);
	}

	public String id2Name(String id) {
		return assiEvaKpiDao.id2Name(id);
	}

	public Float getNextLevelUsableWt(String parentNodeId) {
		Float usableWt = AssiEvaConstants.DEFAULT_MAX_WT;
		IAssiEvaTreeMgr treeMgr = (IAssiEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("IassiEvaTreeMgr");
		List list = treeMgr.listNextLevelNode(parentNodeId,
				AssiEvaConstants.NODETYPE_KPI);
		for (Iterator it = list.iterator(); it.hasNext();) {
			AssiEvaTree node = (AssiEvaTree) it.next();
			usableWt = Float.valueOf(usableWt.floatValue()
					- node.getWeight().floatValue());
		}
		if (usableWt.floatValue() < 0) {
			usableWt = new Float(0);
		}
		return usableWt;
	}

	public Float getMinWt(String parentNodeId, String kpiId) {
		IAssiEvaTreeMgr treeMgr = (IAssiEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("IassiEvaTreeMgr");
		Float minWt = AssiEvaConstants.DEFAULT_MIN_WT;
		// 如果是编辑指标
		if (null != kpiId && !"".equals(kpiId)) {
			// 获得当前编辑节点
			AssiEvaTree currentNode = treeMgr.getNodeByObjId(parentNodeId, kpiId);
			// 获得当前节点下级节点列表
			List list = treeMgr.listNextLevelNode(currentNode.getNodeId(),
					AssiEvaConstants.NODETYPE_KPI);
			// 加上当前节点下级的权重
			for (Iterator it = list.iterator(); it.hasNext();) {
				AssiEvaTree node = (AssiEvaTree) it.next();
				minWt = Float.valueOf(minWt.floatValue()
						+ node.getWeight().floatValue());
			}
		}
		return minWt;
	}

	public Float getMaxWt(String parentNodeId, String kpiId) {
		IAssiEvaTreeMgr treeMgr = (IAssiEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("IassiEvaTreeMgr");
		// 可分配最大权重
		Float maxWt = AssiEvaConstants.DEFAULT_MAX_WT;
		// 上级节点
		AssiEvaTree parentNode = treeMgr.getTreeNodeByNodeId(parentNodeId);
		if (null != parentNode.getId() && !"".equals(parentNode.getId())) {
			maxWt = parentNode.getWeight();
		}
		// 下级kpi列表
		List list = treeMgr.listNextLevelNode(parentNodeId,
				AssiEvaConstants.NODETYPE_KPI);
		// 减去下级所有节点权重
		for (Iterator it = list.iterator(); it.hasNext();) {
			AssiEvaTree node = (AssiEvaTree) it.next();
			maxWt = Float.valueOf(maxWt.floatValue()
					- node.getWeight().floatValue());
		}
		// 如果是编辑指标
		if (null != kpiId && !"".equals(kpiId)) {
			AssiEvaTree currentNode = treeMgr.getNodeByObjId(parentNodeId, kpiId);
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
		return assiEvaKpiDao.isunique(kpiName, parentNodeId);
	}
}
