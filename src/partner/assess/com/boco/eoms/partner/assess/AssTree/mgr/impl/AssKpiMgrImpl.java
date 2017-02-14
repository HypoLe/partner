/**
 * 
 */
package com.boco.eoms.partner.assess.AssTree.mgr.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.log4j.Logger;
import com.boco.eoms.partner.assess.AssAlgorithm.mgr.IAssKpiConfigMgr;
import com.boco.eoms.partner.assess.AssAlgorithm.model.AssKpiConfig;
import com.boco.eoms.partner.assess.AssAlgorithm.util.AssKpiConfigConstants;
import com.boco.eoms.partner.assess.AssTree.dao.IAssKpiDao;
import com.boco.eoms.partner.assess.AssTree.mgr.IAssKpiMgr;
import com.boco.eoms.partner.assess.AssTree.mgr.IAssTreeMgr;
import com.boco.eoms.partner.assess.AssTree.model.AssKpi;
import com.boco.eoms.partner.assess.AssTree.model.AssTree;
import com.boco.eoms.partner.assess.util.AssConstants;

/**
 * <p>
 * Title:指标业务方法类
 * </p>
 * <p>
 * Description:指标业务方法类
 * </p>
 * <p>
 * Date:Nov 26, 2010 8:40:53 AM
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
 * 
 */
public abstract class AssKpiMgrImpl implements IAssKpiMgr {
	
	protected String beenNameForTreeMgr = "";
	protected String beenNameForTemplateMgr = "";
	protected String beenNameForKpiMgr = "";
	protected String beenNameForWeightMgr = "";
	protected String beenNameForTaskMgr = "";
	protected String beenNameForTaskDetailMgr = "";
	protected String beenNameForAssKpiConfirmMgrMgr = "";
	
	private IAssKpiDao assKpiDao;

	public IAssKpiDao getAssKpiDao() {
		return assKpiDao;
	}

	public void setAssKpiDao(IAssKpiDao assKpiDao) {
		this.assKpiDao = assKpiDao;
	}

	public AssKpi getKpi(String id) {
		
		return assKpiDao.getKpi(id);
	}

	public AssKpi getKpi(String id, String deleted) {
		return assKpiDao.getKpi(id, deleted);
	}

	public AssKpi getKpiByNodeId(String nodeId) {
		return assKpiDao.getKpiByNodeId(nodeId);
	}

	public ArrayList listKpiOfType(String parentNodeId) {
		return assKpiDao.listKpiOfType(parentNodeId);
	}

	public void removeKpi(AssKpi kpi) {
		// 假删除指标
		assKpiDao.removeKpi(kpi);
	}

	public void saveKpi(AssKpi kpi) {
		assKpiDao.saveKpi(kpi);
	}

	public void saveKpiAndNode(AssKpi kpi, String parentNodeId){
		
	}
	public void saveKpiAndNode(AssKpi kpi, String parentNodeId,String isActived,String isTotal,String oneTotal) {
		IAssTreeMgr treeMgr = (IAssTreeMgr) ApplicationContextHolder
				.getInstance().getBean(beenNameForTreeMgr);
		
		IAssKpiConfigMgr assKpiConfigMgr = (IAssKpiConfigMgr) ApplicationContextHolder
		.getInstance().getBean(beenNameForAssKpiConfirmMgrMgr);
		if (null == kpi.getId() || "".equals(kpi.getId())) { // 新指标
			// 保存指标
			kpi.setOneTotal(oneTotal);
			kpi.setDeleted(AssConstants.UNDELETED);
			assKpiDao.saveKpi(kpi);

			AssTree assTree = new AssTree();

			// 生成新节点ID
			String newNodeId = treeMgr.getMaxNodeId(parentNodeId);

			// 保存树节点
			assTree.setNodeId(newNodeId);
			assTree.setParentNodeId(parentNodeId);
			assTree.setNodeName(kpi.getKpiName());
			assTree.setNodeType(AssConstants.NODETYPE_KPI);
			assTree.setLeaf(AssConstants.NODE_LEAF);
			assTree.setObjectId(kpi.getId());
			assTree.setWeight(kpi.getWeight());
			assTree.setIsTotal(isTotal);
			assTree.setOneTotal(oneTotal);
			treeMgr.saveTreeNode(assTree);

			// 更新父节点leaf
			treeMgr.updateParentNodeLeaf(parentNodeId,
					AssConstants.NODE_NOTLEAF);
		} else { // 修改指标
			// 获得对应树节点
				AssTree node = treeMgr.getNodeByObjId(parentNodeId, kpi.getId());
				// 设置权重
				node.setWeight(kpi.getWeight());
				node.setNodeName(kpi.getKpiName());
//				if(isActived.equals(AssConstants.TEMPLATE_ACTIVATED)){//如果模版激活则复制指标
				AssKpi kpi2 = new AssKpi();
				kpi2.setAlgorithm(kpi.getAlgorithm());
				kpi2.setCreateTime(kpi.getCreateTime());
				kpi2.setCreator(kpi.getCreator());
				kpi2.setCycle(kpi.getCycle());
				kpi2.setDeleted(kpi.getDeleted());
				kpi2.setAssScore(kpi.getAssScore());
				kpi2.setKpiName(kpi.getKpiName());
				kpi2.setRemark(kpi.getRemark());
				kpi2.setRuleGroupId(kpi.getRuleGroupId());
				kpi2.setThreshold(kpi.getThreshold());
				kpi2.setWeight(kpi.getWeight());
				kpi2.setKpiType(kpi.getKpiType());
				kpi2.setOneTotal(oneTotal);
				kpi2.setIsQuote(kpi.getIsQuote());
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
				assKpiDao.saveKpi(kpi2);
//				增加打分配置
				List assKpiConfigList = assKpiConfigMgr.getAssKpiConfigs(" where kpiId = '"+kpi.getId()+"' order by node_id");
				AssKpiConfig assKpiConfig1 = null;
				String nodeId = "";
				for(int i = 0 ; i<assKpiConfigList.size();i++){
					assKpiConfig1 = (AssKpiConfig)assKpiConfigList.get(i);

					AssKpiConfig assKpiConfig = new AssKpiConfig();
					assKpiConfig.setKpiId(kpi2.getId());
					assKpiConfig.setAlgorithm(assKpiConfig1.getAlgorithm());
					assKpiConfig.setLeaf(assKpiConfig1.getLeaf());
					assKpiConfig.setMaxValue(assKpiConfig1.getMaxValue());
					assKpiConfig.setMinValue(assKpiConfig1.getMinValue());
//					判断父节点是否为根节点，是则生成一个可用的节点id，并保存到assKpiConfig对象中，否
					if("1".equals(assKpiConfig1.getParentNodeId())){
						assKpiConfig.setParentNodeId(assKpiConfig1.getParentNodeId());
					} else {
						assKpiConfig.setParentNodeId(nodeId);
					}
					assKpiConfig.setNodeType(assKpiConfig1.getNodeType());
					assKpiConfig.setNumConfig(assKpiConfig1.getNumConfig());
					assKpiConfig.setNumRelation(assKpiConfig1.getNumRelation());
					assKpiConfig.setRemark(assKpiConfig1.getRemark());
					assKpiConfig.setValueConfig(assKpiConfig1.getValueConfig());
					assKpiConfig.setWeight(assKpiConfig1.getWeight());
					assKpiConfigMgr.saveAssKpiConfig(assKpiConfig);
					if("1".equals(assKpiConfig1.getParentNodeId())){
						nodeId = assKpiConfig.getNodeId();
					}
				}
				
				logger.info("\n保存kpi后");
				node.setObjectId(kpi2.getId());
				node.setIsTotal(isTotal);
				node.setOneTotal(oneTotal);
				// 更新权重
				treeMgr.saveTreeNode(node);
//			}
//			else{
//				assKpiDao.saveKpi(kpi);
//			}

		}
	}

	public void removeKpiOfType(final String parentNodeId) {
		assKpiDao.removeKpiOfType(parentNodeId);
	}


	public String id2Name(String id) {
		return assKpiDao.id2Name(id);
	}

	public Float getNextLevelUsableWt(String parentNodeId) {
		Float usableWt = AssConstants.DEFAULT_MAX_WT;
		IAssTreeMgr treeMgr = (IAssTreeMgr) ApplicationContextHolder
				.getInstance().getBean(beenNameForTreeMgr);
		List list = treeMgr.listNextLevelNode(parentNodeId,
				AssConstants.NODETYPE_KPI);
		for (Iterator it = list.iterator(); it.hasNext();) {
			AssTree node = (AssTree) it.next();
			usableWt = Float.valueOf(usableWt.floatValue()
					- node.getWeight().floatValue());
		}
		if (usableWt.floatValue() < 0) {
			usableWt = new Float(0);
		}
		return usableWt;
	}

	public Float getMinWt(String parentNodeId, String kpiId) {
		IAssTreeMgr treeMgr = (IAssTreeMgr) ApplicationContextHolder
				.getInstance().getBean(beenNameForTreeMgr);
		Float minWt = AssConstants.DEFAULT_MIN_WT;
		// 如果是编辑指标
		if (null != kpiId && !"".equals(kpiId)) {
			// 获得当前编辑节点
			AssTree currentNode = treeMgr.getNodeByObjId(parentNodeId, kpiId);
			// 获得当前节点下级节点列表
			List list = treeMgr.listNextLevelNode(currentNode.getNodeId(),
					AssConstants.NODETYPE_KPI);
			// 加上当前节点下级的权重
			for (Iterator it = list.iterator(); it.hasNext();) {
				AssTree node = (AssTree) it.next();
				minWt = Float.valueOf(minWt.floatValue()
						+ node.getWeight().floatValue());
			}
		}
		return minWt;
	}

	public Float getMaxWt(String parentNodeId, String kpiId) {
		IAssTreeMgr treeMgr = (IAssTreeMgr) ApplicationContextHolder
				.getInstance().getBean(beenNameForTreeMgr);
		// 可分配最大权重
		Float maxWt = AssConstants.DEFAULT_MAX_WT;
		// 上级节点
		AssTree parentNode = treeMgr.getTreeNodeByNodeId(parentNodeId);
		if (null != parentNode.getId() && !"".equals(parentNode.getId())) {
			maxWt = parentNode.getWeight();
		}
		// 下级kpi列表
		List list = treeMgr.listNextLevelNode(parentNodeId,
				AssConstants.NODETYPE_KPI);
		// 减去下级所有节点权重
		for (Iterator it = list.iterator(); it.hasNext();) {
			AssTree node = (AssTree) it.next();
			maxWt = Float.valueOf(maxWt.floatValue()
					- node.getWeight().floatValue());
		}
		// 如果是编辑指标
		if (null != kpiId && !"".equals(kpiId)) {
			AssTree currentNode = treeMgr.getNodeByObjId(parentNodeId, kpiId);
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
		return assKpiDao.isunique(kpiName, parentNodeId);
	}
	
	/**
	 * 按条件得到kpi指标
	 *      
	 */	
	public List getAssKpis( final String whereStr ) {
		return assKpiDao.getAssKpis(whereStr);
	}	
}
