package com.boco.eoms.partner.eva.mgr.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.apache.commons.beanutils.BeanUtils;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.eva.dao.IPnrEvaTreeDao;
import com.boco.eoms.partner.eva.mgr.IPnrEvaTemplateMgr;
import com.boco.eoms.partner.eva.mgr.IPnrEvaTreeMgr;
import com.boco.eoms.partner.eva.mgr.IPnrEvaWeightMgr;
import com.boco.eoms.partner.eva.model.PnrEvaTemplate;
import com.boco.eoms.partner.eva.model.PnrEvaTree;
import com.boco.eoms.partner.eva.model.PnrEvaWeight;
import com.boco.eoms.partner.eva.util.PnrEvaConstants;
import com.boco.eoms.partner.eva.util.PnrEvaRoleIdList;

public class PnrEvaTreeMgrImpl implements IPnrEvaTreeMgr {

	public PnrEvaTree getEvaTreeByObjceId(String objectId) {
		return pnrEvaTreeDao.getEvaTreeByObjceId(objectId);
	}
	private IPnrEvaTreeDao pnrEvaTreeDao;

	public IPnrEvaTreeDao getPnrEvaTreeDao() {
		return pnrEvaTreeDao;
	}

	public void setPnrEvaTreeDao(IPnrEvaTreeDao pnrEvaTreeDao) {
		this.pnrEvaTreeDao = pnrEvaTreeDao;
	}

	public PnrEvaTree getTreeNode(String id) {
		return pnrEvaTreeDao.getTreeNode(id);
	}

	public String getMaxNodeId(String parentNodeId) {
		return pnrEvaTreeDao.getMaxNodeId(parentNodeId, parentNodeId.length()
				+ PnrEvaConstants.NODEID_BETWEEN_LENGTH);
	}

	public PnrEvaTree getTreeNodeByNodeId(String nodeId) {
		return pnrEvaTreeDao.getTreeNodeByNodeId(nodeId);
	}

	public boolean isHaveSameLevel(String parentNodeId, String nodeType) {
		boolean flag = false;
		List list = pnrEvaTreeDao.listNextLevelNode(parentNodeId, nodeType);
		if (null != list && 0 < list.size()) {
			flag = true;
		}
		return flag;
	}

	public ArrayList listNextLevelNode(String parentNodeId, String nodeType) {
		return pnrEvaTreeDao.listNextLevelNode(parentNodeId, nodeType);
	}
	
	
	public boolean isFirstKpiLevel(String nodeId){
		return pnrEvaTreeDao.isFirstKpiLevel(nodeId);
	}

	public void removeTreeNodeByNodeId(final String nodeId) {
		// 获得节点对应所有叶子模板 将删除的模版加标志位
		List nodeList = pnrEvaTreeDao.listChildNodes(nodeId, PnrEvaConstants.NODETYPE_TEMPLATE, "");
		PnrEvaTree childNode = new PnrEvaTree();
		PnrEvaTemplate tpl = new PnrEvaTemplate();
		IPnrEvaTemplateMgr templateMgr = (IPnrEvaTemplateMgr) ApplicationContextHolder.getInstance().getBean("iPnrEvaTemplateMgr");
		for(int i=0;i<nodeList.size();i++){
			childNode = (PnrEvaTree)nodeList.get(i);
			tpl = templateMgr.getTemplate(childNode.getObjectId());
			tpl.setDeleted("1");
			templateMgr.saveTemplate(tpl);
		}
		// 删除当前节点及下级节点
		pnrEvaTreeDao.removeTreeNodeByNodeId(nodeId);
		// 得到父节点
		String parentNodeId = getTreeNodeByNodeId(nodeId).getParentNodeId();
		if (!PnrEvaConstants.TREE_ROOT_ID.equals(parentNodeId)) {
			PnrEvaTree parentNode = getTreeNodeByNodeId(parentNodeId);
			// 如果删除后没有同级节点则父节点为leaf
			if (isHaveSameLevel(parentNode.getNodeId(), "")) {
				updateParentNodeLeaf(parentNode.getNodeId(),
						PnrEvaConstants.NODE_LEAF);
			}
		}
	}

	public void saveTreeNode(PnrEvaTree evaTreeNode) {
		pnrEvaTreeDao.saveTreeNode(evaTreeNode);
	}

	public void updateParentNodeLeaf(String nodeId, String leaf) {
		PnrEvaTree node = pnrEvaTreeDao.getTreeNodeByNodeId(nodeId);
		node.setLeaf(leaf);
		pnrEvaTreeDao.saveTreeNode(node);
	}

	public String id2Name(String nodeId) {
		return pnrEvaTreeDao.id2Name(nodeId);
	}

	public void removeTreeNode(PnrEvaTree evaTreeNode) {
		pnrEvaTreeDao.removeTreeNode(evaTreeNode);
	}

	public List listChildNodes(String parentNodeId, String nodeType, String leaf) {
		return pnrEvaTreeDao.listChildNodes(parentNodeId, nodeType, leaf);
	}

	public PnrEvaTree getNodeByObjId(String parentNodeId, String objectId) {
		return pnrEvaTreeDao.getNodeByObjId(parentNodeId, objectId);
	}

	public int getMaxLevelOfChildNode(String parentNodeId) {
		int maxLevel = 0;
		// 最大深度的节点一定是叶子节点
		List sonList = listChildNodes(parentNodeId, "", PnrEvaConstants.NODE_LEAF);
		String maxNodeId = parentNodeId;
		for (Iterator sonIt = sonList.iterator(); sonIt.hasNext();) {
			PnrEvaTree childNode = (PnrEvaTree) sonIt.next();
			if (childNode.getNodeId().length() > maxNodeId.length()) {
				maxNodeId = childNode.getNodeId();
			}
		}
		maxLevel = (maxNodeId.length() - parentNodeId.length())
				/ PnrEvaConstants.NODEID_BETWEEN_LENGTH;
		return maxLevel;
	}


	public List listNextNodeByPNodeIdAndArea(String nodeId, String area){
		PnrEvaRoleIdList roleIdList = (PnrEvaRoleIdList)ApplicationContextHolder
		.getInstance().getBean("pnrEvaRoleIdList");
		String rootArea = roleIdList.getRootAreaId();
		String areaStr = rootArea;
		//根据地域信息查出该地域以及上级所有地域信息集合
		int areaNum = (area.length()-rootArea.length())/2;
		for(int i=0;i<areaNum;i++){
			areaStr = areaStr + ",'"+area.substring(0,rootArea.length()+areaNum*2)+"'";
		}
		List nextNodes = pnrEvaTreeDao.listNextNodeByPNodeIdAndArea(nodeId, areaStr);
		// 根据地域信息将节点权重更新为个性权重
		Map areaWeight = listWeightByPNodeIdAndArea(nodeId, areaStr,PnrEvaConstants.NEXT_CHILD_NODE);
		List tplList =new ArrayList();
		for(Iterator tplIt = nextNodes.iterator(); tplIt.hasNext();){
			PnrEvaTree evaTree = (PnrEvaTree) tplIt.next();
			PnrEvaTree newEvaTree = new PnrEvaTree();
			try{
			BeanUtils.copyProperties(newEvaTree, evaTree);
			}catch(Exception e) {
				e.printStackTrace();
			}
			if(areaWeight.get(newEvaTree.getNodeId())!=null
					&&newEvaTree.getIsLock().equals(PnrEvaConstants.UNLOCK)){
//			if(areaWeight.get(newEvaTree.getNodeId())!=null){
				float weight = Float.parseFloat(areaWeight.get(newEvaTree.getNodeId()).toString());
				newEvaTree.setWeight(weight);
			}
			tplList.add(newEvaTree);
		}
		
		return tplList;
	}
	
	public Map listWeightByPNodeIdAndArea(String nodeId, String areaStr, String childType){
		List areaWeight = pnrEvaTreeDao.listWeightByPNodeIdAndArea(nodeId, areaStr, childType);
		Map weightMap = new HashMap();
		for(int i=0; i<areaWeight.size(); i++){
			PnrEvaWeight pnrEvaWeight = (PnrEvaWeight)areaWeight.get(i);
			weightMap.put(pnrEvaWeight.getNodeId(), String.valueOf(pnrEvaWeight.getWeight()));
		}
		return weightMap;
	}
	
	public List listActNodesByPNodeIdAndArea(String nodeId, String area, String nodeType, String order){
		PnrEvaRoleIdList roleIdList = (PnrEvaRoleIdList)ApplicationContextHolder
		.getInstance().getBean("pnrEvaRoleIdList");
		String rootArea = roleIdList.getRootAreaId();
		String areaStr = rootArea;
		//根据地域信息查出该地域以及上级所有地域信息集合
		int areaNum = (area.length()-rootArea.length())/2;
		for(int i=0;i<areaNum;i++){
			areaStr = areaStr + ",'"+area.substring(0,rootArea.length()+areaNum*2)+"'";
		}
		List nextNodes = pnrEvaTreeDao.listActNodesByPNodeIdAndArea(nodeId, areaStr, nodeType, order);
		// 根据地域信息将节点权重更新为个性权重
		Map areaWeight = listWeightByPNodeIdAndArea(nodeId, areaStr,PnrEvaConstants.ALL_CHILD_NODE);
		List tplList =new ArrayList();
		for(Iterator tplIt = nextNodes.iterator(); tplIt.hasNext();){
			PnrEvaTree evaTree = (PnrEvaTree) tplIt.next();
			if(areaWeight.get(evaTree.getNodeId())!=null){
				float weight = Float.parseFloat(areaWeight.get(evaTree.getNodeId()).toString());
				evaTree.setWeight(weight);
			}
			tplList.add(evaTree);
		}
		
		return tplList;
	}
	
	
	public int getMaxLevelOfChildNodeByArea(String parentNodeId,String area) {
		int maxLevel = 0;
		// 最大深度的节点一定是叶子节点
		List sonList = listActNodesByPNodeIdAndArea(parentNodeId,area,PnrEvaConstants.NODETYPE_KPI,"");
		String maxNodeId = parentNodeId;
		for (Iterator sonIt = sonList.iterator(); sonIt.hasNext();) {
			PnrEvaTree childNode = (PnrEvaTree) sonIt.next();
			if (childNode.getNodeId().length() > maxNodeId.length()) {
				maxNodeId = childNode.getNodeId();
			}
		}
		maxLevel = (maxNodeId.length() - parentNodeId.length())
				/ PnrEvaConstants.NODEID_BETWEEN_LENGTH;
		return maxLevel;
	}
	
	
	public float getMinWt(String nodeId, String areaId) {
		// 下级节点列表
		List list = listNextNodeByPNodeIdAndArea(nodeId,areaId);
		float minWt = PnrEvaConstants.DEFAULT_MIN_WT;
			// 加上当前节点下级的权重
		for (Iterator it = list.iterator(); it.hasNext();) {
			PnrEvaTree childNode = (PnrEvaTree) it.next();
			minWt = minWt+ childNode.getWeight();
		}
		return minWt;
	}

	public float getMaxWt(String parentNodeId, String areaId, String nodeType) {
		// 可分配最大权重
		float maxWt = PnrEvaConstants.DEFAULT_MAX_WT;
		// 如果节点是指标则则最大权重  福建需求当每级指标节点权重和要求都为100时注释下面代码
		if(PnrEvaConstants.NODETYPE_KPI.equals(nodeType)){
			PnrEvaTree pnrEvaTree = getTreeNodeByNodeId(parentNodeId);
			if(!PnrEvaConstants.NODETYPE_TEMPLATE.equals(pnrEvaTree.getNodeType())){
				maxWt = pnrEvaTree.getWeight();
				IPnrEvaWeightMgr weightMgr = (IPnrEvaWeightMgr) ApplicationContextHolder.getInstance().getBean("iPnrEvaWeightMgr");
				PnrEvaWeight pnrEvaWeight = weightMgr.getWeight(areaId, parentNodeId);
				if(pnrEvaWeight.getId()!=null)
					maxWt = pnrEvaWeight.getWeight();
				}
		}
		// 下级节点列表
		List list = listNextNodeByPNodeIdAndArea(parentNodeId,areaId);
		
		// 减去下级所有节点权重
		for (Iterator it = list.iterator(); it.hasNext();) {
			PnrEvaTree node = (PnrEvaTree) it.next();
			maxWt = maxWt - node.getWeight();
		}
		return maxWt;
	}
	
	public PnrEvaTree getLeafNodeBySubNodeIdAndNodeType(String nodeId, String nodeType){
		PnrEvaTree pnrEvaTree = getTreeNodeByNodeId(nodeId);;
		while(!pnrEvaTree.getNodeType().equals(nodeType)
				||pnrEvaTree.getNodeId().equals(PnrEvaConstants.TREE_ROOT_ID)){
			pnrEvaTree = getTreeNodeByNodeId(pnrEvaTree.getParentNodeId());
		}
		return pnrEvaTree;
	}
	public Map getRowspanForNodeList(String parentNodeId, int length, List nodeList,String order){
		Map rowMap = new HashMap();
		PnrEvaTree pnrEvaTree  = null;
		int pNodeIdSize = parentNodeId.length();
		int nodeIdSize = 0;
		int maxNodeIdSize = 0;
		String nodeId = "";
		if(order.equals(PnrEvaConstants.ORDER_ASC)){
		for(int i=0;i<nodeList.size();i++){
			pnrEvaTree = (PnrEvaTree)nodeList.get(i);
			nodeIdSize = pnrEvaTree.getNodeId().length();
			if(rowMap.get(pnrEvaTree.getNodeId()+"_row")==null){
				if(maxNodeIdSize<nodeIdSize){
					maxNodeIdSize = nodeIdSize;
				}
				rowMap.put(pnrEvaTree.getNodeId()+"_leaf", "1");
			for(int j=nodeIdSize;j>pNodeIdSize;j-=length){
				nodeId = pnrEvaTree.getNodeId().substring(0, j);
				int rowNum = StaticMethod.nullObject2int(rowMap.get(nodeId+"_row"));
				rowMap.put(nodeId+"_row", String.valueOf(rowNum+1));
				}
			}	
		}
		}else{
			for(int i=nodeList.size();i>0;i--){
				pnrEvaTree = (PnrEvaTree)nodeList.get(i-1);
				nodeIdSize = pnrEvaTree.getNodeId().length();
				if(rowMap.get(pnrEvaTree.getNodeId()+"_row")==null){
					if(maxNodeIdSize<nodeIdSize){
						maxNodeIdSize = nodeIdSize;
					}
					rowMap.put(pnrEvaTree.getNodeId()+"_leaf", "1");
				for(int j=nodeIdSize;j>pNodeIdSize;j-=length){
					nodeId = pnrEvaTree.getNodeId().substring(0, j);
					int rowNum = StaticMethod.nullObject2int(rowMap.get(nodeId+"_row"));
					rowMap.put(nodeId+"_row", String.valueOf(rowNum+1));
					}
				}	
			}
		}
		int maxLevel = (maxNodeIdSize - pNodeIdSize)/ PnrEvaConstants.NODEID_BETWEEN_LENGTH;
		rowMap.put("maxLevel", String.valueOf(maxLevel));
		return rowMap;
	}
}
