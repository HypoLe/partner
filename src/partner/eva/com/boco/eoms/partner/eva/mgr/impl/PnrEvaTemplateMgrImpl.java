package com.boco.eoms.partner.eva.mgr.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.system.area.model.TawSystemArea;
import com.boco.eoms.commons.system.area.service.ITawSystemAreaManager;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.partner.eva.dao.IPnrEvaTemplateDao;
import com.boco.eoms.partner.eva.mgr.IPnrEvaTaskDetailMgr;
import com.boco.eoms.partner.eva.mgr.IPnrEvaTaskMgr;
import com.boco.eoms.partner.eva.mgr.IPnrEvaTemplateMgr;
import com.boco.eoms.partner.eva.mgr.IPnrEvaTreeMgr;
import com.boco.eoms.partner.eva.mgr.IPnrEvaWeightMgr;
import com.boco.eoms.partner.eva.model.PnrEvaTask;
import com.boco.eoms.partner.eva.model.PnrEvaTaskDetail;
import com.boco.eoms.partner.eva.model.PnrEvaTemplate;
import com.boco.eoms.partner.eva.model.PnrEvaTree;
import com.boco.eoms.partner.eva.model.PnrEvaWeight;
import com.boco.eoms.partner.eva.util.PnrEvaConstants;
import com.boco.eoms.partner.eva.util.PnrEvaDateUtil;

public class PnrEvaTemplateMgrImpl implements IPnrEvaTemplateMgr {

	public void changeLeaf(String parentNodeId) {
		// TODO Auto-generated method stub
		IPnrEvaTreeMgr treeMgr = (IPnrEvaTreeMgr) ApplicationContextHolder
			.getInstance().getBean("iPnrEvaTreeMgr");
		PnrEvaTree pnrEvaTree = treeMgr.getTreeNodeByNodeId(parentNodeId);
		if(pnrEvaTree.getNodeType().equals(PnrEvaConstants.NODETYPE_TEMPLATE)){//如果上级是模板,则切换叶子节点
		IPnrEvaTemplateMgr templateMgr = (IPnrEvaTemplateMgr) ApplicationContextHolder
		.getInstance().getBean("iPnrEvaTemplateMgr");
		PnrEvaTemplate parentTemplate = templateMgr.getTemplate(pnrEvaTree.getObjectId());
		parentTemplate.setLeaf(PnrEvaConstants.NODE_NOTLEAF);
		pnrEvaTemplateDao.saveObject(parentTemplate);
		}
	}

	private IPnrEvaTemplateDao pnrEvaTemplateDao;

	public IPnrEvaTemplateDao getPnrEvaTemplateDao() {
		return pnrEvaTemplateDao;
	}

	public void setPnrEvaTemplateDao(IPnrEvaTemplateDao pnrEvaTemplateDao) {
		this.pnrEvaTemplateDao = pnrEvaTemplateDao;
	}

	public PnrEvaTemplate getTemplate(String id) {
		return pnrEvaTemplateDao.getTemplate(id);
	}

	public void removeTemplate(PnrEvaTemplate template) {
		pnrEvaTemplateDao.removeTemplate(template);
	}
	
	//根据模版中存储的belongNode，找到属于同一节点的所有模版2009-8-7
	public List getTemplateByblnode(String node){
		return pnrEvaTemplateDao.getTemplateByblnode(node);
	}
//2009-7-20 已激活删除模版的时候，模版记录deleted设置为 1
	public void removeTplLogical(String tplNodeId) {
		IPnrEvaTreeMgr treeMgr = (IPnrEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("iPnrEvaTreeMgr");
		PnrEvaTree tplNode = treeMgr.getTreeNodeByNodeId(tplNodeId);
		treeMgr.removeTreeNodeByNodeId(tplNodeId);
		// 获得节点对应模板 将删除的模版加标志位
		PnrEvaTemplate tpl = getTemplate(tplNode.getObjectId());
		tpl.setDeleted("1");
		saveTemplate(tpl);
	}

	public void removeTplPhysical(String tplNodeId) {
		// 删除树图中对应节点
		IPnrEvaTreeMgr treeMgr = (IPnrEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("iPnrEvaTreeMgr");
		PnrEvaTree tplNode = treeMgr.getTreeNodeByNodeId(tplNodeId);
		treeMgr.removeTreeNodeByNodeId(tplNodeId);

		// 获得节点对应模板
		PnrEvaTemplate tpl = getTemplate(tplNode.getObjectId());

		// 删除模板对应任务
		IPnrEvaTaskMgr taskMgr = (IPnrEvaTaskMgr) ApplicationContextHolder
				.getInstance().getBean("iPnrEvaTaskMgr");
		taskMgr.removeTaskOfTemplate(tpl.getId());
		// 删除模板
		removeTemplate(tpl);
	}

	public void saveTemplate(PnrEvaTemplate template) {
		pnrEvaTemplateDao.saveTemplate(template);
	}

	public void saveTemplateWithNodeAndTask(PnrEvaTemplate template,
			String parentNodeId, String[] orgIds) {
		// 保存模板 并将NodeId存入模板中
		template.setTemplateTypeId(parentNodeId);
		pnrEvaTemplateDao.saveTemplate(template);
		IPnrEvaTreeMgr treeMgr = (IPnrEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("iPnrEvaTreeMgr");
		// 保存任务及相关信息
		IPnrEvaTaskMgr taskMgr = (IPnrEvaTaskMgr) ApplicationContextHolder
				.getInstance().getBean("iPnrEvaTaskMgr");
		// 删除模板对应所有任务
		taskMgr.removeTaskOfTemplate(template.getId());


		// 保存新节点
		PnrEvaTree newNode = new PnrEvaTree();
		newNode.setLeaf(PnrEvaConstants.NODE_LEAF);
		newNode.setNodeId(treeMgr.getMaxNodeId(parentNodeId));
		newNode.setNodeName(template.getTemplateName());//2009-7-18 添加，保存节点名
		newNode.setParentNodeId(parentNodeId);
		newNode.setObjectId(template.getId());
		newNode.setNodeType(PnrEvaConstants.NODETYPE_TEMPLATE);
//		newNode.setIsLock(PnrEvaConstants.UNLOCK);
		newNode.setIsLock(template.getIsLock());
//		newNode.setWeight(PnrEvaConstants.DEFAULT_MAX_WT);
		newNode.setOrgType(template.getExecuteType());
		newNode.setWeight(template.getWeight());
		newNode.setCreateTime(PnrEvaDateUtil.getTimestamp(template.getCreateTime()));
		newNode.setCreatArea(template.getArea());//保存地域信息
		newNode.setCreateUser(template.getCreator());
		treeMgr.saveTreeNode(newNode);
		for (int i = 0; i < orgIds.length; i++) {
			// 保存任务
			PnrEvaTask task = new PnrEvaTask();
			task.setOrgId(orgIds[i]);
			task.setOrgType(template.getOrgType());
			task.setTemplateId(template.getId());
			task.setCreator(template.getCreator());
			task.setCreateTime(template.getCreateTime());
			//任务表里保存节点信息
			task.setNodeId(newNode.getId());
			//如果不是地市考核层面，将executeOrg为空
			task.setExecuteOrg(template.getExecuteOrg());
			taskMgr.saveTask(task);
		}
		// 设置父节点为非叶子节点
		PnrEvaTree parentNode = treeMgr.getTreeNodeByNodeId(parentNodeId);
		parentNode.setLeaf(PnrEvaConstants.NODE_NOTLEAF);
		treeMgr.saveTreeNode(parentNode);
		
		//在模版里保存树节点id
		template.setBelongNode(newNode.getId());
		pnrEvaTemplateDao.saveTemplate(template);
		
	}

	//2009-8-5 已经激活的模版，当指标有修改时，需要保存一个新模版，生效时间就是激活的时间（START_TIME 考核起始时间），旧模版截止时间是新模版的激活时间（END_TIME）。
	public void newTemplateWithTask(PnrEvaTemplate template,HttpServletRequest request,String parentNodeId) {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		IPnrEvaTreeMgr treeMgr = (IPnrEvaTreeMgr) ApplicationContextHolder.getInstance().getBean("IevaTreeMgr");
		
		PnrEvaTemplate tem = new PnrEvaTemplate();		
		tem.setActivated(PnrEvaConstants.TEMPLATE_NOTACTIVATED);
		tem.setCreateTime(StaticMethod.getCurrentDateTime());
		tem.setCreator(sessionform.getUserid());
		tem.setCreatorOrgId(sessionform.getDeptid());
		tem.setCycle(template.getCycle());
		tem.setDeleted(PnrEvaConstants.UNDELETED);
		tem.setOrgType(PnrEvaConstants.ORG_DEPT);
		tem.setProfessional(template.getProfessional());
		tem.setRemark(template.getRemark());
		tem.setTemplateName(template.getTemplateName());
		tem.setTemplateTypeId(template.getTemplateTypeId());
		tem.setTotalScore(template.getTotalScore());
		tem.setBelongNode(template.getBelongNode());
		
		pnrEvaTemplateDao.saveTemplate(tem);		
		
		// 修改对应树节点记录的objectId
		PnrEvaTree tplNode = treeMgr.getTreeNodeByNodeId(parentNodeId);
		tplNode.setObjectId(tem.getId());
		treeMgr.saveTreeNode(tplNode);
		// 保存新任务及相关信息
		IPnrEvaTaskMgr taskMgr = (IPnrEvaTaskMgr) ApplicationContextHolder
				.getInstance().getBean("iPnrEvaTaskMgr");
		// 删除模板对应所有任务
		taskMgr.removeTaskOfTemplate(tem.getId());
		List taskList = taskMgr.listTaskOfTpl(template.getId());
		Iterator taskIt = taskList.iterator();
		while(taskIt.hasNext()){
			PnrEvaTask taskofcopy = (PnrEvaTask)taskIt.next();
			PnrEvaTask task = new PnrEvaTask();
			task.setOrgId(taskofcopy.getOrgId());
			task.setOrgType(tem.getOrgType());
			task.setTemplateId(tem.getId());
			task.setCreator(tem.getCreator());
			task.setCreateTime(tem.getCreateTime());
			task.setNodeId(tem.getBelongNode());
			taskMgr.saveTask(task);
		}
		
	}
	
	public void updateTemplate(PnrEvaTemplate template, String parentNodeId,HttpServletRequest request,
			String[] orgIds) {
		IPnrEvaTreeMgr treeMgr = (IPnrEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("iPnrEvaTreeMgr");
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");

		IPnrEvaTaskMgr taskMgr = (IPnrEvaTaskMgr) ApplicationContextHolder.getInstance().getBean("iPnrEvaTaskMgr");
		PnrEvaTemplate tem = new PnrEvaTemplate();
		if (PnrEvaConstants.TEMPLATE_NOTACTIVATED.equals(template.getActivated())) { // 如果未激活，仅修改模板信息即可
			pnrEvaTemplateDao.saveTemplate(template);
			// 删除模板对应所有任务
			taskMgr.removeTaskOfTemplate(template.getId());
			// 保存新任务及相关信息
			for (int i = 0; i < orgIds.length; i++) {
				// 保存任务
				PnrEvaTask task = new PnrEvaTask();
				task.setOrgId(orgIds[i]);
				task.setOrgType(template.getOrgType());
				task.setTemplateId(template.getId());
				task.setCreator(template.getCreator());
				task.setCreateTime(template.getCreateTime());
				task.setNodeId(template.getBelongNode());
				task.setExecuteOrg(template.getExecuteOrg());
				taskMgr.saveTask(task);
			}
			//修改树的基本信息
			PnrEvaTree tplNode = treeMgr.getTreeNodeByNodeId(parentNodeId);
			tplNode.setWeight(template.getWeight());
			tplNode.setIsLock(template.getIsLock());
			tplNode.setOrgType(template.getExecuteType());
			treeMgr.saveTreeNode(tplNode);	
		} else { // 如果是已激活模板，由于修改会产生新的模板，所以要修改对应树节点记录的objectId
			// 保存新模板			
			tem.setActivated(PnrEvaConstants.TEMPLATE_NOTACTIVATED);
			tem.setCreateTime(StaticMethod.getCurrentDateTime());
			tem.setCreator(sessionform.getUserid());
			tem.setCreatorOrgId(sessionform.getDeptid());
			tem.setCycle(template.getCycle());
			tem.setDeleted(PnrEvaConstants.UNDELETED);
			tem.setOrgType(PnrEvaConstants.ORG_AREA);
			tem.setProfessional(template.getProfessional());
			tem.setRemark(template.getRemark());
			tem.setTemplateName(template.getTemplateName());
			tem.setTemplateTypeId(template.getTemplateTypeId());
			tem.setTotalScore(template.getTotalScore());
			tem.setBelongNode(template.getBelongNode());
			//增加部分属性
			tem.setSumType(template.getSumType());
			tem.setContent(template.getContent());
			tem.setDataSource(template.getDataSource());
			tem.setIsImpersonal(template.getIsImpersonal());
			tem.setEvaluationPhase(template.getEvaluationPhase());
			tem.setEvaluationRole(template.getEvaluationRole());
			tem.setDataType(template.getDataType());
			tem.setLeaf(template.getLeaf());
			tem.setAuditFlag("0");
			tem.setWeight(template.getWeight());
			tem.setExecuteType(template.getExecuteType());
			tem.setExecuteOrg(template.getExecuteOrg());
			pnrEvaTemplateDao.saveTemplate(tem);
			
			// 修改对应树节点记录的objectId
			PnrEvaTree tplNode = treeMgr.getTreeNodeByNodeId(parentNodeId);
			tplNode.setObjectId(tem.getId());
			//修改树的基本信息
			tplNode.setWeight(template.getWeight());
			tplNode.setIsLock(template.getIsLock());
			tplNode.setOrgType(template.getExecuteType());
			treeMgr.saveTreeNode(tplNode);			
			// 删除模板对应所有任务
			taskMgr.removeTaskOfTemplate(tem.getId());
			// 保存新任务及相关信息
			for (int i = 0; i < orgIds.length; i++) {
				// 保存任务
				PnrEvaTask task = new PnrEvaTask();
				task.setOrgId(orgIds[i]);
				task.setOrgType(tem.getOrgType());
				task.setTemplateId(tem.getId());
				task.setCreator(tem.getCreator());
				task.setCreateTime(tem.getCreateTime());
				task.setNodeId(template.getBelongNode());
				task.setExecuteOrg(template.getExecuteOrg());
				taskMgr.saveTask(task);
			}
		}


	}

	public void activeTemplate(String templateId, String tplNodeId) {
		IPnrEvaTreeMgr treeMgr = (IPnrEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("iPnrEvaTreeMgr");
		IPnrEvaTemplateMgr tplMgr = (IPnrEvaTemplateMgr) ApplicationContextHolder
				.getInstance().getBean("iPnrEvaTemplateMgr");
		IPnrEvaTaskMgr taskMgr = (IPnrEvaTaskMgr) ApplicationContextHolder
				.getInstance().getBean("iPnrEvaTaskMgr");
		IPnrEvaTaskDetailMgr taskDetailMgr = (IPnrEvaTaskDetailMgr) ApplicationContextHolder
				.getInstance().getBean("iPnrEvaTaskDetailMgr");
		ITawSystemAreaManager iTawSystemAreaManager = (ITawSystemAreaManager) ApplicationContextHolder
				.getInstance().getBean("ItawSystemAreaManager");
		IPnrEvaWeightMgr weightMgr = (IPnrEvaWeightMgr) ApplicationContextHolder
				.getInstance().getBean("iPnrEvaWeightMgr");
		PnrEvaTree tree = treeMgr.getTreeNodeByNodeId(tplNodeId);
		// 修改模板为激活状态
		PnrEvaTemplate tpl = tplMgr.getTemplate(templateId);
		tpl.setActivated(PnrEvaConstants.TEMPLATE_ACTIVATED);
		tpl.setStartTime(StaticMethod.getCurrentDateTime());//激活时间是模版起始时间
		
		tplMgr.saveTemplate(tpl);
		
		//将与激活模版在同一树节点下的模版设为删除
		String node = tpl.getBelongNode();
		if(node!=null&&!node.equals("")){
			List list = getTemplateByblnode(node);
			if(list!=null){
				for (Iterator it = list.iterator(); it.hasNext();){
					PnrEvaTemplate temp = (PnrEvaTemplate)it.next();
					if(!tpl.getId().equals(temp.getId())){
						temp.setDeleted(PnrEvaConstants.DELETED);
						tplMgr.saveTemplate(temp);
					}
				}
			}
		}

		// 取模板对应任务列表
		List taskList = taskMgr.listTaskOfTpl(templateId);
		for (Iterator taskIt = taskList.iterator(); taskIt.hasNext();) {
			PnrEvaTask task = (PnrEvaTask) taskIt.next();
			// 取模板下所有指标节点
			//如果是叶子模板取模板下所有指标节点
			if(tpl.getLeaf().equals(PnrEvaConstants.NODE_LEAF)){
//				List kpiNodeList = treeMgr.listActNodesByPNodeIdAndArea(tplNodeId, tree.getCreatArea(), PnrEvaConstants.NODETYPE_KPI);
				List kpiNodeList = treeMgr.listActNodesByPNodeIdAndArea(tplNodeId, task.getOrgId(), PnrEvaConstants.NODETYPE_KPI,PnrEvaConstants.ORDER_ASC);
				Map rowMap = treeMgr.getRowspanForNodeList(tplNodeId, PnrEvaConstants.NODEID_BETWEEN_LENGTH, kpiNodeList,PnrEvaConstants.ORDER_DESC);
				// 行列表编号
				int listNo = 1;
				// 模板下属指标最大层数
//				int maxLevel = treeMgr.getMaxLevelOfChildNode(tplNodeId);
//				int maxLevel = treeMgr.getMaxLevelOfChildNodeByArea(tplNodeId,task.getOrgId());
				int maxLevel = StaticMethod.nullObject2int(rowMap.get("maxLevel"));
				for (Iterator kpiNodeIt = kpiNodeList.iterator(); kpiNodeIt
						.hasNext();) {
					PnrEvaTree kpiNode = (PnrEvaTree) kpiNodeIt.next();
					//个性化权重
					float weight = kpiNode.getWeight();
					String leaf = StaticMethod.nullObject2String(rowMap.get(kpiNode.getNodeId()+"_leaf"),"0");
					String areaId = task.getOrgId();
					if(kpiNode.getIsLock().equals(PnrEvaConstants.UNLOCK)){
						PnrEvaWeight pnrEvaWeight = weightMgr.getWeight(areaId, kpiNode.getNodeId());
						if(pnrEvaWeight.getId()!=null){
							weight = pnrEvaWeight.getWeight();
						}
					}
					// 设置任务详细信息
					PnrEvaTaskDetail taskDetail = new PnrEvaTaskDetail();
					taskDetail.setTaskId(task.getId());
					taskDetail.setKpiId(kpiNode.getObjectId());
					taskDetail.setWeight(weight);
					taskDetail.setNodeId(kpiNode.getNodeId());
					taskDetail.setParentNodeId(kpiNode.getParentNodeId());
					taskDetail.setLeaf(leaf);
					taskDetail.setTaskType(PnrEvaConstants.NODETYPE_KPI);
					if (PnrEvaConstants.NODE_LEAF.equals(leaf)) {// 如果是叶子节点
						int currentLevel = (kpiNode.getNodeId().length() - tplNodeId
								.length())
								/ PnrEvaConstants.NODEID_BETWEEN_LENGTH; // 当前节点所在层数
//						taskDetail.setRowspan("1"); // 叶子节点占一行
						taskDetail.setColspan(String.valueOf(maxLevel
								- currentLevel + 1)); // 叶子节点列数 = 最大level -
						// 当前level + 1
						taskDetail.setListNo(String.valueOf(listNo));
						listNo++; // 遍历到叶子节点以后，行号加一
					} else {// 如果是非叶子节点
						taskDetail.setColspan("1"); // 非叶子节点占一列
						taskDetail.setListNo(String.valueOf(listNo));
					}
					taskDetail.setRowspan(StaticMethod.nullObject2String(rowMap.get(kpiNode.getNodeId()+"_row")));
					taskDetail.setArea(areaId);//和该任务的地域相同
					taskDetailMgr.saveTask(taskDetail);
				}
			}else{
			//如果不是叶子模板取模板下所有模板节点
				List tplNodeList = treeMgr.listActNodesByPNodeIdAndArea(tplNodeId, tree.getCreatArea(), PnrEvaConstants.NODETYPE_TEMPLATE,PnrEvaConstants.ORDER_ASC);
				// 行列表编号
				int listNo = 1;
				for (Iterator kpiNodeIt = tplNodeList.iterator(); kpiNodeIt.hasNext();) {
					PnrEvaTree kpiNode = (PnrEvaTree) kpiNodeIt.next();
					//个性化权重
					float weight = kpiNode.getWeight();
					String areaId = task.getOrgId();
					if(kpiNode.getIsLock().equals(PnrEvaConstants.UNLOCK)){
						PnrEvaWeight pnrEvaWeight = weightMgr.getWeight(areaId, kpiNode.getNodeId());
						if(pnrEvaWeight.getId()!=null){
							weight = pnrEvaWeight.getWeight();
						}
					}
					// 设置任务详细信息
					if(kpiNode.getOrgType().equals(tree.getOrgType())){
						PnrEvaTaskDetail rootTaskDetail = new PnrEvaTaskDetail();
						rootTaskDetail.setTaskId(task.getId());
						rootTaskDetail.setTemplateId(kpiNode.getObjectId());
						rootTaskDetail.setWeight(weight);
						rootTaskDetail.setNodeId(kpiNode.getNodeId());
						rootTaskDetail.setParentNodeId(kpiNode.getParentNodeId());
						rootTaskDetail.setLeaf(PnrEvaConstants.NODE_NOTLEAF);//模板汇总下一级
						rootTaskDetail.setRowspan("1"); // 叶子节点所占行数是下属叶子节点数
						rootTaskDetail.setColspan("1"); // 非叶子节点占一列
						rootTaskDetail.setTaskType(PnrEvaConstants.NODETYPE_TEMPLATE);
						rootTaskDetail.setListNo(String.valueOf(listNo));
						rootTaskDetail.setArea("");//为合并表格用,空占位
						taskDetailMgr.saveTask(rootTaskDetail);
						listNo++;
						PnrEvaTaskDetail taskDetail = new PnrEvaTaskDetail();
						taskDetail.setTaskId(task.getId());
						taskDetail.setTemplateId(kpiNode.getObjectId());
						taskDetail.setWeight(weight);
						taskDetail.setNodeId(kpiNode.getNodeId());
						taskDetail.setParentNodeId(kpiNode.getParentNodeId());
						taskDetail.setLeaf("1");//模板汇总下一级
						taskDetail.setRowspan("1"); // 叶子节点所占行数是下属叶子节点数
						taskDetail.setColspan("1"); // 非叶子节点占一列
						taskDetail.setArea(areaId);//和该任务的地域相同
						taskDetail.setTaskType(PnrEvaConstants.NODETYPE_TEMPLATE);
						taskDetail.setListNo(String.valueOf(listNo));
						taskDetailMgr.saveTask(taskDetail);
						listNo++; // 行号加一
					}else{
						List childAreas =  iTawSystemAreaManager.getSonAreaByAreaId(areaId);
						PnrEvaTaskDetail rootTaskDetail = new PnrEvaTaskDetail();
						rootTaskDetail.setTaskId(task.getId());
						rootTaskDetail.setTemplateId(kpiNode.getObjectId());
						rootTaskDetail.setWeight(weight);
						rootTaskDetail.setNodeId(kpiNode.getNodeId());
						rootTaskDetail.setParentNodeId(kpiNode.getParentNodeId());
						rootTaskDetail.setLeaf(PnrEvaConstants.NODE_NOTLEAF);//模板汇总下一级
						rootTaskDetail.setRowspan(String.valueOf(childAreas.size())); // 叶子节点所占行数是下属叶子节点数
						rootTaskDetail.setColspan("1"); // 非叶子节点占一列
						rootTaskDetail.setTaskType(PnrEvaConstants.NODETYPE_TEMPLATE);
						rootTaskDetail.setListNo(String.valueOf(listNo));
						listNo++;
						taskDetailMgr.saveTask(rootTaskDetail);
						for (Iterator childAreasIt = childAreas.iterator(); childAreasIt.hasNext();) {
							TawSystemArea tawSystemArea = (TawSystemArea) childAreasIt.next();
							PnrEvaTaskDetail taskDetail = new PnrEvaTaskDetail();
							taskDetail.setTaskId(task.getId());
							taskDetail.setTemplateId(kpiNode.getObjectId());
							taskDetail.setWeight(weight);
							taskDetail.setNodeId(kpiNode.getNodeId());
							taskDetail.setParentNodeId(kpiNode.getParentNodeId());
							taskDetail.setLeaf(PnrEvaConstants.NODE_LEAF);//模板汇总下一级
							taskDetail.setRowspan("1"); // 叶子节点所占行数是下属叶子节点数
							taskDetail.setColspan("1"); // 非叶子节点占一列
							taskDetail.setArea(tawSystemArea.getAreaid());
							taskDetail.setTaskType(PnrEvaConstants.NODETYPE_TEMPLATE);
							taskDetail.setListNo(String.valueOf(listNo));
							taskDetailMgr.saveTask(taskDetail);
							listNo++; // 行号加一
						}
					}
					
				}
			}


		}
	}

	public String id2Name(String id) {
		return pnrEvaTemplateDao.id2Name(id);
	}

	public Float getTotalWtOfTemplate(String tplNodeId) {
		float totalWt = 0;
		IPnrEvaTreeMgr treeMgr = (IPnrEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("iPnrEvaTreeMgr");
		List list = treeMgr.listChildNodes(tplNodeId,
				PnrEvaConstants.NODETYPE_KPI, PnrEvaConstants.NODE_LEAF);
		for (Iterator it = list.iterator(); it.hasNext();) {
			PnrEvaTree kpiNode = (PnrEvaTree) it.next();
			totalWt += kpiNode.getWeight();
		}
		return Float.valueOf(totalWt);
	}
	public float getTotalWtOfTemplateByArea(String tplNodeId, String area) {
		float totalWt = 0;
		IPnrEvaTreeMgr treeMgr = (IPnrEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("iPnrEvaTreeMgr");
		List list = treeMgr.listNextNodeByPNodeIdAndArea(tplNodeId,area);
		for (Iterator it = list.iterator(); it.hasNext();) {
			PnrEvaTree kpiNode = (PnrEvaTree) it.next();
			totalWt += kpiNode.getWeight();
		}
		return totalWt;
	}

	public List getTemplateByCondition(String where) {	
		return pnrEvaTemplateDao.getTemplateByCondition(where);
	}
	public List getActiveTemplateByExecuteType(String templateType,String executeType){
		return pnrEvaTemplateDao.getActiveTemplateByExecuteType(templateType,executeType);
	}
	public List getNextTemplateByExecuteType(String parentNodeId,String executeType){
		return pnrEvaTemplateDao.getNextTemplateByExecuteType(parentNodeId,executeType);
	}
}
