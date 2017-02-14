package com.boco.eoms.eva.mgr.impl;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.eva.dao.IEvaTemplateDao;
import com.boco.eoms.eva.mgr.IEvaReportInfoMgr;
import com.boco.eoms.eva.mgr.IEvaTaskDetailMgr;
import com.boco.eoms.eva.mgr.IEvaTaskMgr;
import com.boco.eoms.eva.mgr.IEvaTemplateMgr;
import com.boco.eoms.eva.mgr.IEvaTreeMgr;
import com.boco.eoms.eva.model.EvaReportInfo;
import com.boco.eoms.eva.model.EvaTask;
import com.boco.eoms.eva.model.EvaTaskDetail;
import com.boco.eoms.eva.model.EvaTemplate;
import com.boco.eoms.eva.model.EvaTree;
import com.boco.eoms.eva.util.DateTimeUtil;
import com.boco.eoms.eva.util.EvaConstants;

public class EvaTemplateMgrImpl implements IEvaTemplateMgr {

	private IEvaTemplateDao evaTemplateDao;

	public IEvaTemplateDao getEvaTemplateDao() {
		return evaTemplateDao;
	}

	public void setEvaTemplateDao(IEvaTemplateDao evaTemplateDao) {
		this.evaTemplateDao = evaTemplateDao;
	}

	public EvaTemplate getTemplate(String id) {
		return evaTemplateDao.getTemplate(id);
	}

	public void removeTemplate(EvaTemplate template) {
		evaTemplateDao.removeTemplate(template);
	}
	
	//根据模版中存储的belongNode，找到属于同一节点的所有模版2009-8-7
	public List getTemplateByblnode(String node,String del){
		return evaTemplateDao.getTemplateByblnode(node,del);
	}
//2009-7-20 已激活删除模版的时候，模版记录deleted设置为 1
	public void removeTplLogical(String tplNodeId) {
		IEvaTreeMgr treeMgr = (IEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("IevaTreeMgr");
		EvaTree tplNode = treeMgr.getTreeNodeByNodeId(tplNodeId);
		treeMgr.removeTreeNodeByNodeId(tplNodeId);
		// 获得节点对应模板 将删除的模版加标志位
		EvaTemplate tpl = getTemplate(tplNode.getObjectId());
		tpl.setDeleted("1");
		saveTemplate(tpl);
	}

	public void removeTplPhysical(String tplNodeId) {
		// 删除树图中对应节点
		IEvaTreeMgr treeMgr = (IEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("IevaTreeMgr");
		EvaTree tplNode = treeMgr.getTreeNodeByNodeId(tplNodeId);
		treeMgr.removeTreeNodeByNodeId(tplNodeId);

		// 获得节点对应模板
		EvaTemplate tpl = getTemplate(tplNode.getObjectId());

		// 删除模板对应任务
		IEvaTaskMgr taskMgr = (IEvaTaskMgr) ApplicationContextHolder
				.getInstance().getBean("IevaTaskMgr");
		taskMgr.removeTaskOfTemplate(tpl.getId());
		// 删除模板
		removeTemplate(tpl);
	}

	public void saveTemplate(EvaTemplate template) {
		evaTemplateDao.saveTemplate(template);
	}

	public void saveTemplateWithNodeAndTask(EvaTemplate template,
			String parentNodeId, String[] orgIds) {
		// 保存模板 并将NodeId存入模板中
		template.setTemplateTypeId(parentNodeId);
		evaTemplateDao.saveTemplate(template);
		IEvaTreeMgr treeMgr = (IEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("IevaTreeMgr");
		// 保存任务及相关信息
		IEvaTaskMgr taskMgr = (IEvaTaskMgr) ApplicationContextHolder
				.getInstance().getBean("IevaTaskMgr");
		
		// 删除模板对应所有任务
		taskMgr.removeTaskOfTemplate(template.getId());
		for (int i = 0; i < orgIds.length; i++) {
			// 保存任务
			EvaTask task = new EvaTask();
			task.setOrgId(orgIds[i]);
			task.setOrgType(template.getOrgType());
			task.setTemplateId(template.getId());
			task.setCreator(template.getCreator());
			task.setCreateTime(template.getCreateTime());
			taskMgr.saveTask(task);
		}

		// 保存新节点
		EvaTree newNode = new EvaTree();
		newNode.setLeaf(EvaConstants.NODE_LEAF);
		newNode.setNodeId(treeMgr.getMaxNodeId(parentNodeId));
		newNode.setNodeName(template.getTemplateName());//2009-7-18 添加，保存节点名
		newNode.setParentNodeId(parentNodeId);
		newNode.setObjectId(template.getId());
		newNode.setNodeType(EvaConstants.NODETYPE_TEMPLATE);
		newNode.setWeight(EvaConstants.DEFAULT_MAX_WT);
		treeMgr.saveTreeNode(newNode);
		
		

		// 设置父节点为非叶子节点
		EvaTree parentNode = treeMgr.getTreeNodeByNodeId(parentNodeId);
		parentNode.setLeaf(EvaConstants.NODE_NOTLEAF);
		treeMgr.saveTreeNode(parentNode);
		
		//在模版里保存树节点id
		template.setBelongNode(newNode.getId());
		evaTemplateDao.saveTemplate(template);
		
		

		
		

	}

	//2009-8-5 已经激活的模版，当指标有修改时，需要保存一个新模版，生效时间就是激活的时间（START_TIME 考核起始时间），旧模版截止时间是新模版的激活时间（END_TIME）。
	public void newTemplateWithTask(EvaTemplate template,HttpServletRequest request,String parentNodeId) {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		IEvaTreeMgr treeMgr = (IEvaTreeMgr) ApplicationContextHolder.getInstance().getBean("IevaTreeMgr");
		
		EvaTemplate tem = new EvaTemplate();		
		tem.setActivated(EvaConstants.TEMPLATE_NOTACTIVATED);
		tem.setCreateTime(StaticMethod.getCurrentDateTime());
		tem.setCreator(sessionform.getUserid());
		tem.setCreatorOrgId(sessionform.getDeptid());
		tem.setCycle(template.getCycle());
		tem.setDeleted(EvaConstants.UNDELETED);
		tem.setOrgType(EvaConstants.ORG_DEPT);
		tem.setProfessional(template.getProfessional());
		tem.setRemark(template.getRemark());
		tem.setTemplateName(template.getTemplateName());
		tem.setTemplateTypeId(template.getTemplateTypeId());
		tem.setTotalScore(template.getTotalScore());
		tem.setBelongNode(template.getBelongNode());
		
		evaTemplateDao.saveTemplate(tem);		
		
		// 修改对应树节点记录的objectId
		EvaTree tplNode = treeMgr.getTreeNodeByNodeId(parentNodeId);
		tplNode.setObjectId(tem.getId());
		treeMgr.saveTreeNode(tplNode);
		// 保存新任务及相关信息
		IEvaTaskMgr taskMgr = (IEvaTaskMgr) ApplicationContextHolder
				.getInstance().getBean("IevaTaskMgr");
		// 删除模板对应所有任务
		taskMgr.removeTaskOfTemplate(tem.getId());
		List taskList = taskMgr.listTaskOfTpl(template.getId());
		Iterator taskIt = taskList.iterator();
		while(taskIt.hasNext()){
			EvaTask taskofcopy = (EvaTask)taskIt.next();
			EvaTask task = new EvaTask();
			task.setOrgId(taskofcopy.getOrgId());
			task.setOrgType(tem.getOrgType());
			task.setTemplateId(tem.getId());
			task.setCreator(tem.getCreator());
			task.setCreateTime(tem.getCreateTime());
			taskMgr.saveTask(task);
		}
		
	}
	
	public void updateTemplate(EvaTemplate template, String parentNodeId,HttpServletRequest request,
			String[] orgIds) {
		IEvaTreeMgr treeMgr = (IEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("IevaTreeMgr");
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");

		IEvaTaskMgr taskMgr = (IEvaTaskMgr) ApplicationContextHolder.getInstance().getBean("IevaTaskMgr");
		EvaTemplate tem = new EvaTemplate();
		if (EvaConstants.TEMPLATE_NOTACTIVATED.equals(template.getActivated())) { // 如果未激活，仅修改模板信息即可
			evaTemplateDao.saveTemplate(template);
			// 删除模板对应所有任务
			taskMgr.removeTaskOfTemplate(template.getId());
			// 保存新任务及相关信息
			for (int i = 0; i < orgIds.length; i++) {
				// 保存任务
				EvaTask task = new EvaTask();
				task.setOrgId(orgIds[i]);
				task.setOrgType(template.getOrgType());
				task.setTemplateId(template.getId());
				task.setCreator(template.getCreator());
				task.setCreateTime(template.getCreateTime());
				taskMgr.saveTask(task);
			}
		} else { // 如果是已激活模板，由于修改会产生新的模板，所以要修改对应树节点记录的objectId
			// 保存新模板			
			tem.setActivated(EvaConstants.TEMPLATE_NOTACTIVATED);
			tem.setCreateTime(StaticMethod.getCurrentDateTime());
			tem.setCreator(sessionform.getUserid());
			tem.setCreatorOrgId(sessionform.getDeptid());
			tem.setCycle(template.getCycle());
			tem.setDeleted(EvaConstants.UNDELETED);
			tem.setOrgType(EvaConstants.ORG_DEPT);
			tem.setProfessional(template.getProfessional());
			tem.setRemark(template.getRemark());
			tem.setTemplateName(template.getTemplateName());
			tem.setTemplateTypeId(template.getTemplateTypeId());
			tem.setTotalScore(template.getTotalScore());
			tem.setBelongNode(template.getBelongNode());
			
			evaTemplateDao.saveTemplate(tem);
			
			// 修改对应树节点记录的objectId
			EvaTree tplNode = treeMgr.getTreeNodeByNodeId(parentNodeId);
			tplNode.setObjectId(tem.getId());
			treeMgr.saveTreeNode(tplNode);			
			// 删除模板对应所有任务
			taskMgr.removeTaskOfTemplate(tem.getId());
			// 保存新任务及相关信息
			for (int i = 0; i < orgIds.length; i++) {
				// 保存任务
				EvaTask task = new EvaTask();
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
		IEvaTreeMgr treeMgr = (IEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("IevaTreeMgr");
		IEvaTemplateMgr tplMgr = (IEvaTemplateMgr) ApplicationContextHolder
				.getInstance().getBean("IevaTemplateMgr");
		IEvaTaskMgr taskMgr = (IEvaTaskMgr) ApplicationContextHolder
				.getInstance().getBean("IevaTaskMgr");
		IEvaTaskDetailMgr taskDetailMgr = (IEvaTaskDetailMgr) ApplicationContextHolder
				.getInstance().getBean("IevaTaskDetailMgr");
		IEvaReportInfoMgr evaReportInfoMgr = (IEvaReportInfoMgr) ApplicationContextHolder
		.getInstance().getBean("IevaReportInfoMgr");
		// 修改模板为激活状态
		EvaTemplate tpl = tplMgr.getTemplate(templateId);
		tpl.setActivated(EvaConstants.TEMPLATE_ACTIVATED);
		if(tpl.getStartTime()==null&"".equals(tpl.getStartTime())){
			tpl.setStartTime(StaticMethod.getCurrentDateTime());//激活时间是模版起始时间
		}
		tplMgr.saveTemplate(tpl);
		
		//将与激活模版在同一树节点下的模版设为删除
		String node = tpl.getBelongNode();
		if(node!=null&&!node.equals("")){
			List list = getTemplateByblnode(node,"0");
			if(list!=null){
				for (Iterator it = list.iterator(); it.hasNext();){
					EvaTemplate temp = (EvaTemplate)it.next();
					if(!tpl.getId().equals(temp.getId())){
						temp.setDeleted(EvaConstants.DELETED);
						tplMgr.saveTemplate(temp);
					}
				}
			}
		}

		// 取模板对应任务列表
		List taskList = taskMgr.listTaskOfTpl(templateId);
		for (Iterator taskIt = taskList.iterator(); taskIt.hasNext();) {
			EvaTask task = (EvaTask) taskIt.next();
			// 取模板下所有指标节点
			List kpiNodeList = treeMgr.listChildNodes(tplNodeId,
					EvaConstants.NODETYPE_KPI, "");
			// 行列表编号
			int listNo = 1;
			// 模板下属指标最大层数
			int maxLevel = treeMgr.getMaxLevelOfChildNode(tplNodeId);
			for (Iterator kpiNodeIt = kpiNodeList.iterator(); kpiNodeIt
					.hasNext();) {
				EvaTree kpiNode = (EvaTree) kpiNodeIt.next();
				// 设置任务详细信息
				EvaTaskDetail taskDetail = new EvaTaskDetail();
				taskDetail.setTaskId(task.getId());
				taskDetail.setKpiId(kpiNode.getObjectId());
				taskDetail.setWeight(kpiNode.getWeight());
				taskDetail.setNodeId(kpiNode.getNodeId());
				taskDetail.setParentNodeId(kpiNode.getParentNodeId());
				taskDetail.setLeaf(kpiNode.getLeaf());
				if (EvaConstants.NODE_LEAF.equals(kpiNode.getLeaf())) {// 如果是叶子节点
					int currentLevel = (kpiNode.getNodeId().length() - tplNodeId
							.length())
							/ EvaConstants.NODEID_BETWEEN_LENGTH; // 当前节点所在层数
					taskDetail.setRowspan("1"); // 叶子节点占一行
					taskDetail.setColspan(String.valueOf(maxLevel
							- currentLevel + 1)); // 叶子节点列数 = 最大level -
					// 当前level + 1
					taskDetail.setListNo(String.valueOf(listNo));
					listNo++; // 遍历到叶子节点以后，行号加一
				} else {// 如果是非叶子节点
					int childLeafNum = treeMgr.listChildNodes(
							kpiNode.getNodeId(), "", EvaConstants.NODE_LEAF)
							.size();// 当前节点下的叶子节点数量
					taskDetail.setRowspan(String.valueOf(childLeafNum)); // 叶子节点所占行数是下属叶子节点数
					taskDetail.setColspan("1"); // 非叶子节点占一列
					taskDetail.setListNo(String.valueOf(listNo));
				}
				taskDetailMgr.saveTask(taskDetail);
			}
			//激活模板时保存指标考核实例任务（计划时间内的）
			List timeStrList = DateTimeUtil.getAllStrByTimes(tpl.getCycle(),tpl.getStartTime(),tpl.getEndTime());
			String timeStr = "";
			String num ="";
			for(int j=0;j<timeStrList.size();j++){
				if(j<9){
					num = "0"+(j+1);
				}else{
					num = ""+(j+1);
				}
				timeStr = String.valueOf(timeStrList.get(j));
				EvaReportInfo eri = new EvaReportInfo();
				eri.setCreateTime(StaticMethod.getLocalString());
				eri.setIsPublish("0");//草稿状态
				eri.setPartnerId(tpl.getPartnerDept());
				eri.setPartnerName(tpl.getPartnerDeptName());
				eri.setTaskId(task.getId());
				eri.setTime(num);
				eri.setEvaTime(timeStr);
				eri.setTimeType(tpl.getCycle());
				evaReportInfoMgr.saveEvaReportInfo(eri);
			}
		}
	}

	public String id2Name(String id) {
		return evaTemplateDao.id2Name(id);
	}

	public Float getTotalWtOfTemplate(String tplNodeId) {
		float totalWt = 0;
		IEvaTreeMgr treeMgr = (IEvaTreeMgr) ApplicationContextHolder
				.getInstance().getBean("IevaTreeMgr");
		List list = treeMgr.listChildNodes(tplNodeId,
				EvaConstants.NODETYPE_KPI, EvaConstants.NODE_LEAF);
		for (Iterator it = list.iterator(); it.hasNext();) {
			EvaTree kpiNode = (EvaTree) it.next();
			totalWt += kpiNode.getWeight().floatValue();
		}
		return Float.valueOf(totalWt);
	}
	public List getTemplates(String where){
		
		return evaTemplateDao.getTemplates(where);
	}	
}
