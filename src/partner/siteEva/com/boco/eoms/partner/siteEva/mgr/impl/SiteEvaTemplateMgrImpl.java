package com.boco.eoms.partner.siteEva.mgr.impl;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.partner.siteEva.dao.ISiteEvaTemplateDao;
import com.boco.eoms.partner.siteEva.mgr.ISiteEvaTaskDetailMgr;
import com.boco.eoms.partner.siteEva.mgr.ISiteEvaTaskMgr;
import com.boco.eoms.partner.siteEva.mgr.ISiteEvaTemplateMgr;
import com.boco.eoms.partner.siteEva.mgr.ISiteEvaTreeMgr;
import com.boco.eoms.partner.siteEva.model.SiteEvaTask;
import com.boco.eoms.partner.siteEva.model.SiteEvaTaskDetail;
import com.boco.eoms.partner.siteEva.model.SiteEvaTemplate;
import com.boco.eoms.partner.siteEva.model.SiteEvaTree;
import com.boco.eoms.partner.siteEva.util.SiteEvaConstants;

public class SiteEvaTemplateMgrImpl implements ISiteEvaTemplateMgr {

	private ISiteEvaTemplateDao siteEvaTemplateDao;

	public ISiteEvaTemplateDao getSiteEvaTemplateDao() {
		return siteEvaTemplateDao;
	}

	public void setSiteEvaTemplateDao(ISiteEvaTemplateDao siteEvaTemplateDao) {
		this.siteEvaTemplateDao = siteEvaTemplateDao;
	}

	public SiteEvaTemplate getTemplate(String id) {
		return siteEvaTemplateDao.getTemplate(id);
	}

	public void removeTemplate(SiteEvaTemplate template) {
		siteEvaTemplateDao.removeTemplate(template);
	}
	
	//根据模版中存储的belongNode，找到属于同一节点的所有模版2009-8-7
	public List getTemplateByblnode(String node){
		return siteEvaTemplateDao.getTemplateByblnode(node);
	}
//2009-7-20 已激活删除模版的时候，模版记录deleted设置为 1
	public void removeTplLogical(String tplNodeId) {
		ISiteEvaTreeMgr treeMgr = (ISiteEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("IsiteEvaTreeMgr");
		SiteEvaTree tplNode = treeMgr.getTreeNodeByNodeId(tplNodeId);
		treeMgr.removeTreeNodeByNodeId(tplNodeId); 
		// 获得节点对应模板 将删除的模版加标志位
		SiteEvaTemplate tpl = getTemplate(tplNode.getObjectId());
		tpl.setDeleted("1");
		saveTemplate(tpl);
	}

	public void removeTplPhysical(String tplNodeId) {
		// 删除树图中对应节点
		ISiteEvaTreeMgr treeMgr = (ISiteEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("IsiteEvaTreeMgr");
		SiteEvaTree tplNode = treeMgr.getTreeNodeByNodeId(tplNodeId);
		treeMgr.removeTreeNodeByNodeId(tplNodeId);

		// 获得节点对应模板
		SiteEvaTemplate tpl = getTemplate(tplNode.getObjectId());

		// 删除模板对应任务
		ISiteEvaTaskMgr taskMgr = (ISiteEvaTaskMgr) ApplicationContextHolder
				.getInstance().getBean("IsiteEvaTaskMgr");
		taskMgr.removeTaskOfTemplate(tpl.getId());
		// 删除模板
		removeTemplate(tpl);
	}

	public void saveTemplate(SiteEvaTemplate template) {
		siteEvaTemplateDao.saveTemplate(template);
	}

	public void saveTemplateWithNodeAndTask(SiteEvaTemplate template,
			String parentNodeId, String[] orgIds) {
		// 保存模板 并将NodeId存入模板中
		template.setTemplateTypeId(parentNodeId);
		siteEvaTemplateDao.saveTemplate(template);
		ISiteEvaTreeMgr treeMgr = (ISiteEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("IsiteEvaTreeMgr");
		// 保存任务及相关信息
		ISiteEvaTaskMgr taskMgr = (ISiteEvaTaskMgr) ApplicationContextHolder
				.getInstance().getBean("IsiteEvaTaskMgr");
		// 删除模板对应所有任务
		taskMgr.removeTaskOfTemplate(template.getId());
		for (int i = 0; i < orgIds.length; i++) {
			// 保存任务
			SiteEvaTask task = new SiteEvaTask();
			task.setOrgId(orgIds[i]);
			task.setOrgType(template.getOrgType());
			task.setTemplateId(template.getId());
			task.setCreator(template.getCreator());
			task.setCreateTime(template.getCreateTime());
			taskMgr.saveTask(task);
		}

		// 保存新节点
		SiteEvaTree newNode = new SiteEvaTree();
		newNode.setLeaf(SiteEvaConstants.NODE_LEAF);
		newNode.setNodeId(treeMgr.getMaxNodeId(parentNodeId));
		newNode.setNodeName(template.getTemplateName());//2009-7-18 添加，保存节点名
		newNode.setParentNodeId(parentNodeId);
		newNode.setObjectId(template.getId());
		newNode.setNodeType(SiteEvaConstants.NODETYPE_TEMPLATE);
		newNode.setWeight(SiteEvaConstants.DEFAULT_MAX_WT);
		treeMgr.saveTreeNode(newNode);
		
		

		// 设置父节点为非叶子节点
		SiteEvaTree parentNode = treeMgr.getTreeNodeByNodeId(parentNodeId);
		parentNode.setLeaf(SiteEvaConstants.NODE_NOTLEAF);
		treeMgr.saveTreeNode(parentNode);
		
		//在模版里保存树节点id
		template.setBelongNode(newNode.getId());
		siteEvaTemplateDao.saveTemplate(template);
		
	}

	//2009-8-5 已经激活的模版，当指标有修改时，需要保存一个新模版，生效时间就是激活的时间（START_TIME 考核起始时间），旧模版截止时间是新模版的激活时间（END_TIME）。
	public void newTemplateWithTask(SiteEvaTemplate template,HttpServletRequest request,String parentNodeId) {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		ISiteEvaTreeMgr treeMgr = (ISiteEvaTreeMgr) ApplicationContextHolder.getInstance().getBean("IsiteEvaTreeMgr");
		
		SiteEvaTemplate tem = new SiteEvaTemplate();		
		tem.setActivated(SiteEvaConstants.TEMPLATE_NOTACTIVATED);
		tem.setCreateTime(StaticMethod.getCurrentDateTime());
		tem.setCreator(sessionform.getUserid());
		tem.setCreatorOrgId(sessionform.getDeptid());
		tem.setCycle(template.getCycle());
		tem.setDeleted(SiteEvaConstants.UNDELETED);
		tem.setOrgType(SiteEvaConstants.ORG_DEPT);
		tem.setProfessional(template.getProfessional());
		tem.setRemark(template.getRemark());
		tem.setTemplateName(template.getTemplateName());
		tem.setTemplateTypeId(template.getTemplateTypeId());
		tem.setTotalScore(template.getTotalScore());
		tem.setBelongNode(template.getBelongNode());
		tem.setParIds(template.getParIds());
		siteEvaTemplateDao.saveTemplate(tem);		
		
		// 修改对应树节点记录的objectId
		SiteEvaTree tplNode = treeMgr.getTreeNodeByNodeId(parentNodeId);
		tplNode.setObjectId(tem.getId());
		treeMgr.saveTreeNode(tplNode);
		// 保存新任务及相关信息
		ISiteEvaTaskMgr taskMgr = (ISiteEvaTaskMgr) ApplicationContextHolder
				.getInstance().getBean("IsiteEvaTaskMgr");
		// 删除模板对应所有任务
		taskMgr.removeTaskOfTemplate(tem.getId());
		List taskList = taskMgr.listTaskOfTpl(template.getId());
		Iterator taskIt = taskList.iterator();
		while(taskIt.hasNext()){
			SiteEvaTask taskofcopy = (SiteEvaTask)taskIt.next();
			SiteEvaTask task = new SiteEvaTask();
			task.setOrgId(taskofcopy.getOrgId());
			task.setOrgType(tem.getOrgType());
			task.setTemplateId(tem.getId());
			task.setCreator(tem.getCreator());
			task.setCreateTime(tem.getCreateTime());
			taskMgr.saveTask(task);
		}
		
	}
	
	public void updateTemplate(SiteEvaTemplate template, String parentNodeId,HttpServletRequest request,
			String[] orgIds) {
		ISiteEvaTreeMgr treeMgr = (ISiteEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("IsiteEvaTreeMgr");
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");

		ISiteEvaTaskMgr taskMgr = (ISiteEvaTaskMgr) ApplicationContextHolder.getInstance().getBean("IsiteEvaTaskMgr");
		SiteEvaTemplate tem = new SiteEvaTemplate();
		if (SiteEvaConstants.TEMPLATE_NOTACTIVATED.equals(template.getActivated())) { // 如果未激活，仅修改模板信息即可
			siteEvaTemplateDao.saveTemplate(template);
			// 删除模板对应所有任务
			taskMgr.removeTaskOfTemplate(template.getId());
			// 保存新任务及相关信息
			for (int i = 0; i < orgIds.length; i++) {
				// 保存任务
				SiteEvaTask task = new SiteEvaTask();
				task.setOrgId(orgIds[i]);
				task.setOrgType(template.getOrgType());
				task.setTemplateId(template.getId());
				task.setCreator(template.getCreator());
				task.setCreateTime(template.getCreateTime());
				taskMgr.saveTask(task);
			}
		} else { // 如果是已激活模板，由于修改会产生新的模板，所以要修改对应树节点记录的objectId
			// 保存新模板			
			tem.setActivated(SiteEvaConstants.TEMPLATE_NOTACTIVATED);
			tem.setCreateTime(StaticMethod.getCurrentDateTime());
			tem.setCreator(sessionform.getUserid());
			tem.setCreatorOrgId(sessionform.getDeptid());
			tem.setCycle(template.getCycle());
			tem.setDeleted(SiteEvaConstants.UNDELETED);
			tem.setOrgType(SiteEvaConstants.ORG_DEPT);
			tem.setProfessional(template.getProfessional());
			tem.setRemark(template.getRemark());
			tem.setTemplateName(template.getTemplateName());
			tem.setTemplateTypeId(template.getTemplateTypeId());
			tem.setTotalScore(template.getTotalScore());
			tem.setBelongNode(template.getBelongNode());
			
			siteEvaTemplateDao.saveTemplate(tem);
			
			// 修改对应树节点记录的objectId
			SiteEvaTree tplNode = treeMgr.getTreeNodeByNodeId(parentNodeId);
			tplNode.setObjectId(tem.getId());
			treeMgr.saveTreeNode(tplNode);			
			// 删除模板对应所有任务
			taskMgr.removeTaskOfTemplate(tem.getId());
			// 保存新任务及相关信息
			for (int i = 0; i < orgIds.length; i++) {
				// 保存任务
				SiteEvaTask task = new SiteEvaTask();
				task.setOrgId(orgIds[i]);
				task.setOrgType(tem.getOrgType());
				task.setTemplateId(tem.getId());
				task.setCreator(tem.getCreator());
				task.setCreateTime(tem.getCreateTime());
				taskMgr.saveTask(task);
			}
		}


	}

	public void activeTemplate(String templateId, String tplNodeId) {
		ISiteEvaTreeMgr treeMgr = (ISiteEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("IsiteEvaTreeMgr");
		ISiteEvaTemplateMgr tplMgr = (ISiteEvaTemplateMgr) ApplicationContextHolder
				.getInstance().getBean("IsiteEvaTemplateMgr");
		ISiteEvaTaskMgr taskMgr = (ISiteEvaTaskMgr) ApplicationContextHolder
				.getInstance().getBean("IsiteEvaTaskMgr");
		ISiteEvaTaskDetailMgr taskDetailMgr = (ISiteEvaTaskDetailMgr) ApplicationContextHolder
				.getInstance().getBean("IsiteEvaTaskDetailMgr");

		// 修改模板为激活状态
		SiteEvaTemplate tpl = tplMgr.getTemplate(templateId);
		tpl.setActivated(SiteEvaConstants.TEMPLATE_ACTIVATED);
		tpl.setStartTime(StaticMethod.getCurrentDateTime());//激活时间是模版起始时间
		
		tplMgr.saveTemplate(tpl);
		
		//将与激活模版在同一树节点下的模版设为删除
		String node = tpl.getBelongNode();
		if(node!=null&&!node.equals("")){
			List list = getTemplateByblnode(node);
			if(list!=null){
				for (Iterator it = list.iterator(); it.hasNext();){
					SiteEvaTemplate temp = (SiteEvaTemplate)it.next();
					if(!tpl.getId().equals(temp.getId())){
						temp.setDeleted(SiteEvaConstants.DELETED);
						tplMgr.saveTemplate(temp);
					}
				}
			}
		}

		// 取模板对应任务列表
		List taskList = taskMgr.listTaskOfTpl(templateId);
		for (Iterator taskIt = taskList.iterator(); taskIt.hasNext();) {
			SiteEvaTask task = (SiteEvaTask) taskIt.next();
			// 取模板下所有指标节点
			List kpiNodeList = treeMgr.listChildNodes(tplNodeId,
					SiteEvaConstants.NODETYPE_KPI, "");
			// 行列表编号
			int listNo = 1;
			// 模板下属指标最大层数
			int maxLevel = treeMgr.getMaxLevelOfChildNode(tplNodeId);
			for (Iterator kpiNodeIt = kpiNodeList.iterator(); kpiNodeIt
					.hasNext();) {
				SiteEvaTree kpiNode = (SiteEvaTree) kpiNodeIt.next();
				// 设置任务详细信息
				SiteEvaTaskDetail taskDetail = new SiteEvaTaskDetail();
				taskDetail.setTaskId(task.getId());
				taskDetail.setKpiId(kpiNode.getObjectId());
				taskDetail.setWeight(kpiNode.getWeight());
				taskDetail.setNodeId(kpiNode.getNodeId());
				taskDetail.setParentNodeId(kpiNode.getParentNodeId());
				taskDetail.setLeaf(kpiNode.getLeaf());
				if (SiteEvaConstants.NODE_LEAF.equals(kpiNode.getLeaf())) {// 如果是叶子节点
					int currentLevel = (kpiNode.getNodeId().length() - tplNodeId
							.length())
							/ SiteEvaConstants.NODEID_BETWEEN_LENGTH; // 当前节点所在层数
					taskDetail.setRowspan("1"); // 叶子节点占一行
					taskDetail.setColspan(String.valueOf(maxLevel
							- currentLevel + 1)); // 叶子节点列数 = 最大level -
					// 当前level + 1
					taskDetail.setListNo(String.valueOf(listNo));
					listNo++; // 遍历到叶子节点以后，行号加一
				} else {// 如果是非叶子节点
					int childLeafNum = treeMgr.listChildNodes(
							kpiNode.getNodeId(), "", SiteEvaConstants.NODE_LEAF)
							.size();// 当前节点下的叶子节点数量
					taskDetail.setRowspan(String.valueOf(childLeafNum)); // 叶子节点所占行数是下属叶子节点数
					taskDetail.setColspan("1"); // 非叶子节点占一列
					taskDetail.setListNo(String.valueOf(listNo));
				}
				taskDetailMgr.saveTask(taskDetail);
			}
		}
	}

	public String id2Name(String id) {
		return siteEvaTemplateDao.id2Name(id);
	}

	public Float getTotalWtOfTemplate(String tplNodeId) {
		float totalWt = 0;
		ISiteEvaTreeMgr treeMgr = (ISiteEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("IsiteEvaTreeMgr");
		List list = treeMgr.listChildNodes(tplNodeId,
				SiteEvaConstants.NODETYPE_KPI, SiteEvaConstants.NODE_LEAF);
		for (Iterator it = list.iterator(); it.hasNext();) {
			SiteEvaTree kpiNode = (SiteEvaTree) it.next();
			totalWt += kpiNode.getWeight().floatValue();
		}
		return Float.valueOf(totalWt);
	}
}
