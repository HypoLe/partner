package com.boco.eoms.assEva.mgr.impl;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.assEva.dao.IAssEvaTemplateDao;
import com.boco.eoms.assEva.mgr.IAssEvaTaskDetailMgr;
import com.boco.eoms.assEva.mgr.IAssEvaTaskMgr;
import com.boco.eoms.assEva.mgr.IAssEvaTemplateMgr;
import com.boco.eoms.assEva.mgr.IAssEvaTreeMgr;
import com.boco.eoms.assEva.model.AssEvaTask;
import com.boco.eoms.assEva.model.AssEvaTaskDetail;
import com.boco.eoms.assEva.model.AssEvaTemplate;
import com.boco.eoms.assEva.model.AssEvaTree;
import com.boco.eoms.assEva.util.AssEvaConstants;

public class AssEvaTemplateMgrImpl implements IAssEvaTemplateMgr {

	private IAssEvaTemplateDao assEvaTemplateDao;

	public IAssEvaTemplateDao getAssEvaTemplateDao() {
		return assEvaTemplateDao;
	}

	public void setAssEvaTemplateDao(IAssEvaTemplateDao assEvaTemplateDao) {
		this.assEvaTemplateDao = assEvaTemplateDao;
	}

	public AssEvaTemplate getTemplate(String id) {
		return assEvaTemplateDao.getTemplate(id);
	}

	public void removeTemplate(AssEvaTemplate template) {
		assEvaTemplateDao.removeTemplate(template);
	}
	
	//根据模版中存储的belongNode，找到属于同一节点的所有模版2009-8-7
	public List getTemplateByblnode(String node){
		return assEvaTemplateDao.getTemplateByblnode(node);
	}
//2009-7-20 已激活删除模版的时候，模版记录deleted设置为 1
	public void removeTplLogical(String tplNodeId) {
		IAssEvaTreeMgr treeMgr = (IAssEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("IassEvaTreeMgr");
		AssEvaTree tplNode = treeMgr.getTreeNodeByNodeId(tplNodeId);
		treeMgr.removeTreeNodeByNodeId(tplNodeId); 
		// 获得节点对应模板 将删除的模版加标志位
		AssEvaTemplate tpl = getTemplate(tplNode.getObjectId());
		tpl.setDeleted("1");
		saveTemplate(tpl);
	}

	public void removeTplPhysical(String tplNodeId) {
		// 删除树图中对应节点
		IAssEvaTreeMgr treeMgr = (IAssEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("IassEvaTreeMgr");
		AssEvaTree tplNode = treeMgr.getTreeNodeByNodeId(tplNodeId);
		treeMgr.removeTreeNodeByNodeId(tplNodeId);

		// 获得节点对应模板
		AssEvaTemplate tpl = getTemplate(tplNode.getObjectId());

		// 删除模板对应任务
		IAssEvaTaskMgr taskMgr = (IAssEvaTaskMgr) ApplicationContextHolder
				.getInstance().getBean("IassEvaTaskMgr");
		taskMgr.removeTaskOfTemplate(tpl.getId());
		// 删除模板
		removeTemplate(tpl);
	}

	public void saveTemplate(AssEvaTemplate template) {
		assEvaTemplateDao.saveTemplate(template);
	}

	public void saveTemplateWithNodeAndTask(AssEvaTemplate template,
			String parentNodeId, String[] orgIds) {
		// 保存模板 并将NodeId存入模板中
		template.setTemplateTypeId(parentNodeId);
		assEvaTemplateDao.saveTemplate(template);
		IAssEvaTreeMgr treeMgr = (IAssEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("IassEvaTreeMgr");
		// 保存任务及相关信息
		IAssEvaTaskMgr taskMgr = (IAssEvaTaskMgr) ApplicationContextHolder
				.getInstance().getBean("IassEvaTaskMgr");
		// 删除模板对应所有任务
		taskMgr.removeTaskOfTemplate(template.getId());
		for (int i = 0; i < orgIds.length; i++) {
			// 保存任务
			AssEvaTask task = new AssEvaTask();
			task.setOrgId(orgIds[i]);
			task.setOrgType(template.getOrgType());
			task.setTemplateId(template.getId());
			task.setCreator(template.getCreator());
			task.setCreateTime(template.getCreateTime());
			taskMgr.saveTask(task);
		}

		// 保存新节点
		AssEvaTree newNode = new AssEvaTree();
		newNode.setLeaf(AssEvaConstants.NODE_LEAF);
		newNode.setNodeId(treeMgr.getMaxNodeId(parentNodeId));
		newNode.setNodeName(template.getTemplateName());//2009-7-18 添加，保存节点名
		newNode.setParentNodeId(parentNodeId);
		newNode.setObjectId(template.getId());
		newNode.setNodeType(AssEvaConstants.NODETYPE_TEMPLATE);
		newNode.setWeight(AssEvaConstants.DEFAULT_MAX_WT);
		treeMgr.saveTreeNode(newNode);
		
		

		// 设置父节点为非叶子节点
		AssEvaTree parentNode = treeMgr.getTreeNodeByNodeId(parentNodeId);
		parentNode.setLeaf(AssEvaConstants.NODE_NOTLEAF);
		treeMgr.saveTreeNode(parentNode);
		
		//在模版里保存树节点id
		template.setBelongNode(newNode.getId());
		assEvaTemplateDao.saveTemplate(template);
		
	}

	//2009-8-5 已经激活的模版，当指标有修改时，需要保存一个新模版，生效时间就是激活的时间（START_TIME 考核起始时间），旧模版截止时间是新模版的激活时间（END_TIME）。
	public void newTemplateWithTask(AssEvaTemplate template,HttpServletRequest request,String parentNodeId) {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		IAssEvaTreeMgr treeMgr = (IAssEvaTreeMgr) ApplicationContextHolder.getInstance().getBean("IassEvaTreeMgr");
		
		AssEvaTemplate tem = new AssEvaTemplate();		
		tem.setActivated(AssEvaConstants.TEMPLATE_NOTACTIVATED);
		tem.setCreateTime(StaticMethod.getCurrentDateTime());
		tem.setCreator(sessionform.getUserid());
		tem.setCreatorOrgId(sessionform.getDeptid());
		tem.setCycle(template.getCycle());
		tem.setDeleted(AssEvaConstants.UNDELETED);
		tem.setOrgType(AssEvaConstants.ORG_DEPT);
		tem.setProfessional(template.getProfessional());
		tem.setRemark(template.getRemark());
		tem.setTemplateName(template.getTemplateName());
		tem.setTemplateTypeId(template.getTemplateTypeId());
		tem.setTotalScore(template.getTotalScore());
		tem.setBelongNode(template.getBelongNode());
		tem.setParIds(template.getParIds());
		assEvaTemplateDao.saveTemplate(tem);		
		
		// 修改对应树节点记录的objectId
		AssEvaTree tplNode = treeMgr.getTreeNodeByNodeId(parentNodeId);
		tplNode.setObjectId(tem.getId());
		treeMgr.saveTreeNode(tplNode);
		// 保存新任务及相关信息
		IAssEvaTaskMgr taskMgr = (IAssEvaTaskMgr) ApplicationContextHolder
				.getInstance().getBean("IassEvaTaskMgr");
		// 删除模板对应所有任务
		taskMgr.removeTaskOfTemplate(tem.getId());
		List taskList = taskMgr.listTaskOfTpl(template.getId());
		Iterator taskIt = taskList.iterator();
		while(taskIt.hasNext()){
			AssEvaTask taskofcopy = (AssEvaTask)taskIt.next();
			AssEvaTask task = new AssEvaTask();
			task.setOrgId(taskofcopy.getOrgId());
			task.setOrgType(tem.getOrgType());
			task.setTemplateId(tem.getId());
			task.setCreator(tem.getCreator());
			task.setCreateTime(tem.getCreateTime());
			taskMgr.saveTask(task);
		}
		
	}
	
	public void updateTemplate(AssEvaTemplate template, String parentNodeId,HttpServletRequest request,
			String[] orgIds) {
		IAssEvaTreeMgr treeMgr = (IAssEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("IassEvaTreeMgr");
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");

		IAssEvaTaskMgr taskMgr = (IAssEvaTaskMgr) ApplicationContextHolder.getInstance().getBean("IassEvaTaskMgr");
		AssEvaTemplate tem = new AssEvaTemplate();
		if (AssEvaConstants.TEMPLATE_NOTACTIVATED.equals(template.getActivated())) { // 如果未激活，仅修改模板信息即可
			assEvaTemplateDao.saveTemplate(template);
			// 删除模板对应所有任务
			taskMgr.removeTaskOfTemplate(template.getId());
			// 保存新任务及相关信息
			for (int i = 0; i < orgIds.length; i++) {
				// 保存任务
				AssEvaTask task = new AssEvaTask();
				task.setOrgId(orgIds[i]);
				task.setOrgType(template.getOrgType());
				task.setTemplateId(template.getId());
				task.setCreator(template.getCreator());
				task.setCreateTime(template.getCreateTime());
				taskMgr.saveTask(task);
			}
		} else { // 如果是已激活模板，由于修改会产生新的模板，所以要修改对应树节点记录的objectId
			// 保存新模板			
			tem.setActivated(AssEvaConstants.TEMPLATE_NOTACTIVATED);
			tem.setCreateTime(StaticMethod.getCurrentDateTime());
			tem.setCreator(sessionform.getUserid());
			tem.setCreatorOrgId(sessionform.getDeptid());
			tem.setCycle(template.getCycle());
			tem.setDeleted(AssEvaConstants.UNDELETED);
			tem.setOrgType(AssEvaConstants.ORG_DEPT);
			tem.setProfessional(template.getProfessional());
			tem.setRemark(template.getRemark());
			tem.setTemplateName(template.getTemplateName());
			tem.setTemplateTypeId(template.getTemplateTypeId());
			tem.setTotalScore(template.getTotalScore());
			tem.setBelongNode(template.getBelongNode());
			
			assEvaTemplateDao.saveTemplate(tem);
			
			// 修改对应树节点记录的objectId
			AssEvaTree tplNode = treeMgr.getTreeNodeByNodeId(parentNodeId);
			tplNode.setObjectId(tem.getId());
			treeMgr.saveTreeNode(tplNode);			
			// 删除模板对应所有任务
			taskMgr.removeTaskOfTemplate(tem.getId());
			// 保存新任务及相关信息
			for (int i = 0; i < orgIds.length; i++) {
				// 保存任务
				AssEvaTask task = new AssEvaTask();
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
		IAssEvaTreeMgr treeMgr = (IAssEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("IassEvaTreeMgr");
		IAssEvaTemplateMgr tplMgr = (IAssEvaTemplateMgr) ApplicationContextHolder
				.getInstance().getBean("IassEvaTemplateMgr");
		IAssEvaTaskMgr taskMgr = (IAssEvaTaskMgr) ApplicationContextHolder
				.getInstance().getBean("IassEvaTaskMgr");
		IAssEvaTaskDetailMgr taskDetailMgr = (IAssEvaTaskDetailMgr) ApplicationContextHolder
				.getInstance().getBean("IassEvaTaskDetailMgr");

		// 修改模板为激活状态
		AssEvaTemplate tpl = tplMgr.getTemplate(templateId);
		tpl.setActivated(AssEvaConstants.TEMPLATE_ACTIVATED);
		tpl.setStartTime(StaticMethod.getCurrentDateTime());//激活时间是模版起始时间
		
		tplMgr.saveTemplate(tpl);
		
		//将与激活模版在同一树节点下的模版设为删除
		String node = tpl.getBelongNode();
		if(node!=null&&!node.equals("")){
			List list = getTemplateByblnode(node);
			if(list!=null){
				for (Iterator it = list.iterator(); it.hasNext();){
					AssEvaTemplate temp = (AssEvaTemplate)it.next();
					if(!tpl.getId().equals(temp.getId())){
						temp.setDeleted(AssEvaConstants.DELETED);
						tplMgr.saveTemplate(temp);
					}
				}
			}
		}

		// 取模板对应任务列表
		List taskList = taskMgr.listTaskOfTpl(templateId);
		for (Iterator taskIt = taskList.iterator(); taskIt.hasNext();) {
			AssEvaTask task = (AssEvaTask) taskIt.next();
			// 取模板下所有指标节点
			List kpiNodeList = treeMgr.listChildNodes(tplNodeId,
					AssEvaConstants.NODETYPE_KPI, "");
			// 行列表编号
			int listNo = 1;
			// 模板下属指标最大层数
			int maxLevel = treeMgr.getMaxLevelOfChildNode(tplNodeId);
			for (Iterator kpiNodeIt = kpiNodeList.iterator(); kpiNodeIt
					.hasNext();) {
				AssEvaTree kpiNode = (AssEvaTree) kpiNodeIt.next();
				// 设置任务详细信息
				AssEvaTaskDetail taskDetail = new AssEvaTaskDetail();
				taskDetail.setTaskId(task.getId());
				taskDetail.setKpiId(kpiNode.getObjectId());
				taskDetail.setWeight(kpiNode.getWeight());
				taskDetail.setNodeId(kpiNode.getNodeId());
				taskDetail.setParentNodeId(kpiNode.getParentNodeId());
				taskDetail.setLeaf(kpiNode.getLeaf());
				if (AssEvaConstants.NODE_LEAF.equals(kpiNode.getLeaf())) {// 如果是叶子节点
					int currentLevel = (kpiNode.getNodeId().length() - tplNodeId
							.length())
							/ AssEvaConstants.NODEID_BETWEEN_LENGTH; // 当前节点所在层数
					taskDetail.setRowspan("1"); // 叶子节点占一行
					taskDetail.setColspan(String.valueOf(maxLevel
							- currentLevel + 1)); // 叶子节点列数 = 最大level -
					// 当前level + 1
					taskDetail.setListNo(String.valueOf(listNo));
					listNo++; // 遍历到叶子节点以后，行号加一
				} else {// 如果是非叶子节点
					int childLeafNum = treeMgr.listChildNodes(
							kpiNode.getNodeId(), "", AssEvaConstants.NODE_LEAF)
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
		return assEvaTemplateDao.id2Name(id);
	}

	public Float getTotalWtOfTemplate(String tplNodeId) {
		float totalWt = 0;
		IAssEvaTreeMgr treeMgr = (IAssEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("IassEvaTreeMgr");
		List list = treeMgr.listChildNodes(tplNodeId,
				AssEvaConstants.NODETYPE_KPI, AssEvaConstants.NODE_LEAF);
		for (Iterator it = list.iterator(); it.hasNext();) {
			AssEvaTree kpiNode = (AssEvaTree) it.next();
			totalWt += kpiNode.getWeight().floatValue();
		}
		return Float.valueOf(totalWt);
	}
}
