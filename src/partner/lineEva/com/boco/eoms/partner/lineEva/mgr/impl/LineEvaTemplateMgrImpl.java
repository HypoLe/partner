package com.boco.eoms.partner.lineEva.mgr.impl;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.partner.lineEva.dao.ILineEvaTemplateDao;
import com.boco.eoms.partner.lineEva.mgr.ILineEvaTaskDetailMgr;
import com.boco.eoms.partner.lineEva.mgr.ILineEvaTaskMgr;
import com.boco.eoms.partner.lineEva.mgr.ILineEvaTemplateMgr;
import com.boco.eoms.partner.lineEva.mgr.ILineEvaTreeMgr;
import com.boco.eoms.partner.lineEva.model.LineEvaTask;
import com.boco.eoms.partner.lineEva.model.LineEvaTaskDetail;
import com.boco.eoms.partner.lineEva.model.LineEvaTemplate;
import com.boco.eoms.partner.lineEva.model.LineEvaTree;
import com.boco.eoms.partner.lineEva.util.LineEvaConstants;

public class LineEvaTemplateMgrImpl implements ILineEvaTemplateMgr {

	private ILineEvaTemplateDao lineEvaTemplateDao;

	public ILineEvaTemplateDao getLineEvaTemplateDao() {
		return lineEvaTemplateDao;
	}

	public void setLineEvaTemplateDao(ILineEvaTemplateDao lineEvaTemplateDao) {
		this.lineEvaTemplateDao = lineEvaTemplateDao;
	}

	public LineEvaTemplate getTemplate(String id) {
		return lineEvaTemplateDao.getTemplate(id);
	}

	public void removeTemplate(LineEvaTemplate template) {
		lineEvaTemplateDao.removeTemplate(template);
	}
	
	//根据模版中存储的belongNode，找到属于同一节点的所有模版2009-8-7
	public List getTemplateByblnode(String node){
		return lineEvaTemplateDao.getTemplateByblnode(node);
	}
//2009-7-20 已激活删除模版的时候，模版记录deleted设置为 1
	public void removeTplLogical(String tplNodeId) {
		ILineEvaTreeMgr treeMgr = (ILineEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("IlineEvaTreeMgr");
		LineEvaTree tplNode = treeMgr.getTreeNodeByNodeId(tplNodeId);
		treeMgr.removeTreeNodeByNodeId(tplNodeId); 
		// 获得节点对应模板 将删除的模版加标志位
		LineEvaTemplate tpl = getTemplate(tplNode.getObjectId());
		tpl.setDeleted("1");
		saveTemplate(tpl);
	}

	public void removeTplPhysical(String tplNodeId) {
		// 删除树图中对应节点
		ILineEvaTreeMgr treeMgr = (ILineEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("IlineEvaTreeMgr");
		LineEvaTree tplNode = treeMgr.getTreeNodeByNodeId(tplNodeId);
		treeMgr.removeTreeNodeByNodeId(tplNodeId);

		// 获得节点对应模板
		LineEvaTemplate tpl = getTemplate(tplNode.getObjectId());

		// 删除模板对应任务
		ILineEvaTaskMgr taskMgr = (ILineEvaTaskMgr) ApplicationContextHolder
				.getInstance().getBean("IlineEvaTaskMgr");
		taskMgr.removeTaskOfTemplate(tpl.getId());
		// 删除模板
		removeTemplate(tpl);
	}

	public void saveTemplate(LineEvaTemplate template) {
		lineEvaTemplateDao.saveTemplate(template);
	}

	public void saveTemplateWithNodeAndTask(LineEvaTemplate template,
			String parentNodeId, String[] orgIds) {
		// 保存模板 并将NodeId存入模板中
		template.setTemplateTypeId(parentNodeId);
		lineEvaTemplateDao.saveTemplate(template);
		ILineEvaTreeMgr treeMgr = (ILineEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("IlineEvaTreeMgr");
		// 保存任务及相关信息
		ILineEvaTaskMgr taskMgr = (ILineEvaTaskMgr) ApplicationContextHolder
				.getInstance().getBean("IlineEvaTaskMgr");
		// 删除模板对应所有任务
		taskMgr.removeTaskOfTemplate(template.getId());
		for (int i = 0; i < orgIds.length; i++) {
			// 保存任务
			LineEvaTask task = new LineEvaTask();
			task.setOrgId(orgIds[i]);
			task.setOrgType(template.getOrgType());
			task.setTemplateId(template.getId());
			task.setCreator(template.getCreator());
			task.setCreateTime(template.getCreateTime());
			taskMgr.saveTask(task);
		}

		// 保存新节点
		LineEvaTree newNode = new LineEvaTree();
		newNode.setLeaf(LineEvaConstants.NODE_LEAF);
		newNode.setNodeId(treeMgr.getMaxNodeId(parentNodeId));
		newNode.setNodeName(template.getTemplateName());//2009-7-18 添加，保存节点名
		newNode.setParentNodeId(parentNodeId);
		newNode.setObjectId(template.getId());
		newNode.setNodeType(LineEvaConstants.NODETYPE_TEMPLATE);
		newNode.setWeight(LineEvaConstants.DEFAULT_MAX_WT);
		treeMgr.saveTreeNode(newNode);
		
		

		// 设置父节点为非叶子节点
		LineEvaTree parentNode = treeMgr.getTreeNodeByNodeId(parentNodeId);
		parentNode.setLeaf(LineEvaConstants.NODE_NOTLEAF);
		treeMgr.saveTreeNode(parentNode);
		
		//在模版里保存树节点id
		template.setBelongNode(newNode.getId());
		lineEvaTemplateDao.saveTemplate(template);
		
	}

	//2009-8-5 已经激活的模版，当指标有修改时，需要保存一个新模版，生效时间就是激活的时间（START_TIME 考核起始时间），旧模版截止时间是新模版的激活时间（END_TIME）。
	public void newTemplateWithTask(LineEvaTemplate template,HttpServletRequest request,String parentNodeId) {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		ILineEvaTreeMgr treeMgr = (ILineEvaTreeMgr) ApplicationContextHolder.getInstance().getBean("IlineEvaTreeMgr");
		
		LineEvaTemplate tem = new LineEvaTemplate();		
		tem.setActivated(LineEvaConstants.TEMPLATE_NOTACTIVATED);
		tem.setCreateTime(StaticMethod.getCurrentDateTime());
		tem.setCreator(sessionform.getUserid());
		tem.setCreatorOrgId(sessionform.getDeptid());
		tem.setCycle(template.getCycle());
		tem.setDeleted(LineEvaConstants.UNDELETED);
		tem.setOrgType(LineEvaConstants.ORG_DEPT);
		tem.setProfessional(template.getProfessional());
		tem.setRemark(template.getRemark());
		tem.setTemplateName(template.getTemplateName());
		tem.setTemplateTypeId(template.getTemplateTypeId());
		tem.setTotalScore(template.getTotalScore());
		tem.setBelongNode(template.getBelongNode());
		tem.setParIds(template.getParIds());
		lineEvaTemplateDao.saveTemplate(tem);		
		
		// 修改对应树节点记录的objectId
		LineEvaTree tplNode = treeMgr.getTreeNodeByNodeId(parentNodeId);
		tplNode.setObjectId(tem.getId());
		treeMgr.saveTreeNode(tplNode);
		// 保存新任务及相关信息
		ILineEvaTaskMgr taskMgr = (ILineEvaTaskMgr) ApplicationContextHolder
				.getInstance().getBean("IlineEvaTaskMgr");
		// 删除模板对应所有任务
		taskMgr.removeTaskOfTemplate(tem.getId());
		List taskList = taskMgr.listTaskOfTpl(template.getId());
		Iterator taskIt = taskList.iterator();
		while(taskIt.hasNext()){
			LineEvaTask taskofcopy = (LineEvaTask)taskIt.next();
			LineEvaTask task = new LineEvaTask();
			task.setOrgId(taskofcopy.getOrgId());
			task.setOrgType(tem.getOrgType());
			task.setTemplateId(tem.getId());
			task.setCreator(tem.getCreator());
			task.setCreateTime(tem.getCreateTime());
			taskMgr.saveTask(task);
		}
		
	}
	
	public void updateTemplate(LineEvaTemplate template, String parentNodeId,HttpServletRequest request,
			String[] orgIds) {
		ILineEvaTreeMgr treeMgr = (ILineEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("IlineEvaTreeMgr");
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");

		ILineEvaTaskMgr taskMgr = (ILineEvaTaskMgr) ApplicationContextHolder.getInstance().getBean("IlineEvaTaskMgr");
		LineEvaTemplate tem = new LineEvaTemplate();
		if (LineEvaConstants.TEMPLATE_NOTACTIVATED.equals(template.getActivated())) { // 如果未激活，仅修改模板信息即可
			lineEvaTemplateDao.saveTemplate(template);
			// 删除模板对应所有任务
			taskMgr.removeTaskOfTemplate(template.getId());
			// 保存新任务及相关信息
			for (int i = 0; i < orgIds.length; i++) {
				// 保存任务
				LineEvaTask task = new LineEvaTask();
				task.setOrgId(orgIds[i]);
				task.setOrgType(template.getOrgType());
				task.setTemplateId(template.getId());
				task.setCreator(template.getCreator());
				task.setCreateTime(template.getCreateTime());
				taskMgr.saveTask(task);
			}
		} else { // 如果是已激活模板，由于修改会产生新的模板，所以要修改对应树节点记录的objectId
			// 保存新模板			
			tem.setActivated(LineEvaConstants.TEMPLATE_NOTACTIVATED);
			tem.setCreateTime(StaticMethod.getCurrentDateTime());
			tem.setCreator(sessionform.getUserid());
			tem.setCreatorOrgId(sessionform.getDeptid());
			tem.setCycle(template.getCycle());
			tem.setDeleted(LineEvaConstants.UNDELETED);
			tem.setOrgType(LineEvaConstants.ORG_DEPT);
			tem.setProfessional(template.getProfessional());
			tem.setRemark(template.getRemark());
			tem.setTemplateName(template.getTemplateName());
			tem.setTemplateTypeId(template.getTemplateTypeId());
			tem.setTotalScore(template.getTotalScore());
			tem.setBelongNode(template.getBelongNode());
			
			lineEvaTemplateDao.saveTemplate(tem);
			
			// 修改对应树节点记录的objectId
			LineEvaTree tplNode = treeMgr.getTreeNodeByNodeId(parentNodeId);
			tplNode.setObjectId(tem.getId());
			treeMgr.saveTreeNode(tplNode);			
			// 删除模板对应所有任务
			taskMgr.removeTaskOfTemplate(tem.getId());
			// 保存新任务及相关信息
			for (int i = 0; i < orgIds.length; i++) {
				// 保存任务
				LineEvaTask task = new LineEvaTask();
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
		ILineEvaTreeMgr treeMgr = (ILineEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("IlineEvaTreeMgr");
		ILineEvaTemplateMgr tplMgr = (ILineEvaTemplateMgr) ApplicationContextHolder
				.getInstance().getBean("IlineEvaTemplateMgr");
		ILineEvaTaskMgr taskMgr = (ILineEvaTaskMgr) ApplicationContextHolder
				.getInstance().getBean("IlineEvaTaskMgr");
		ILineEvaTaskDetailMgr taskDetailMgr = (ILineEvaTaskDetailMgr) ApplicationContextHolder
				.getInstance().getBean("IlineEvaTaskDetailMgr");

		// 修改模板为激活状态
		LineEvaTemplate tpl = tplMgr.getTemplate(templateId);
		tpl.setActivated(LineEvaConstants.TEMPLATE_ACTIVATED);
		tpl.setStartTime(StaticMethod.getCurrentDateTime());//激活时间是模版起始时间
		
		tplMgr.saveTemplate(tpl);
		
		//将与激活模版在同一树节点下的模版设为删除
		String node = tpl.getBelongNode();
		if(node!=null&&!node.equals("")){
			List list = getTemplateByblnode(node);
			if(list!=null){
				for (Iterator it = list.iterator(); it.hasNext();){
					LineEvaTemplate temp = (LineEvaTemplate)it.next();
					if(!tpl.getId().equals(temp.getId())){
						temp.setDeleted(LineEvaConstants.DELETED);
						tplMgr.saveTemplate(temp);
					}
				}
			}
		}

		// 取模板对应任务列表
		List taskList = taskMgr.listTaskOfTpl(templateId);
		for (Iterator taskIt = taskList.iterator(); taskIt.hasNext();) {
			LineEvaTask task = (LineEvaTask) taskIt.next();
			// 取模板下所有指标节点
			List kpiNodeList = treeMgr.listChildNodes(tplNodeId,
					LineEvaConstants.NODETYPE_KPI, "");
			// 行列表编号
			int listNo = 1;
			// 模板下属指标最大层数
			int maxLevel = treeMgr.getMaxLevelOfChildNode(tplNodeId);
			for (Iterator kpiNodeIt = kpiNodeList.iterator(); kpiNodeIt
					.hasNext();) {
				LineEvaTree kpiNode = (LineEvaTree) kpiNodeIt.next();
				// 设置任务详细信息
				LineEvaTaskDetail taskDetail = new LineEvaTaskDetail();
				taskDetail.setTaskId(task.getId());
				taskDetail.setKpiId(kpiNode.getObjectId());
				taskDetail.setWeight(kpiNode.getWeight());
				taskDetail.setNodeId(kpiNode.getNodeId());
				taskDetail.setParentNodeId(kpiNode.getParentNodeId());
				taskDetail.setLeaf(kpiNode.getLeaf());
				if (LineEvaConstants.NODE_LEAF.equals(kpiNode.getLeaf())) {// 如果是叶子节点
					int currentLevel = (kpiNode.getNodeId().length() - tplNodeId
							.length())
							/ LineEvaConstants.NODEID_BETWEEN_LENGTH; // 当前节点所在层数
					taskDetail.setRowspan("1"); // 叶子节点占一行
					taskDetail.setColspan(String.valueOf(maxLevel
							- currentLevel + 1)); // 叶子节点列数 = 最大level -
					// 当前level + 1
					taskDetail.setListNo(String.valueOf(listNo));
					listNo++; // 遍历到叶子节点以后，行号加一
				} else {// 如果是非叶子节点
					int childLeafNum = treeMgr.listChildNodes(
							kpiNode.getNodeId(), "", LineEvaConstants.NODE_LEAF)
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
		return lineEvaTemplateDao.id2Name(id);
	}

	public Float getTotalWtOfTemplate(String tplNodeId) {
		float totalWt = 0;
		ILineEvaTreeMgr treeMgr = (ILineEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("IlineEvaTreeMgr");
		List list = treeMgr.listChildNodes(tplNodeId,
				LineEvaConstants.NODETYPE_KPI, LineEvaConstants.NODE_LEAF);
		for (Iterator it = list.iterator(); it.hasNext();) {
			LineEvaTree kpiNode = (LineEvaTree) it.next();
			totalWt += kpiNode.getWeight().floatValue();
		}
		return Float.valueOf(totalWt);
	}
}
