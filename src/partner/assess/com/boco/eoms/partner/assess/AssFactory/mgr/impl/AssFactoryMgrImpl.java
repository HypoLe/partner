package com.boco.eoms.partner.assess.AssFactory.mgr.impl;

import java.util.ArrayList;
import java.util.List;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.partner.assess.AssFactory.dao.IAssFactoryDao;
import com.boco.eoms.partner.assess.AssFactory.mgr.IAssFactoryMgr;
import com.boco.eoms.partner.assess.AssFactory.model.AssFactory;
import com.boco.eoms.partner.assess.AssTree.mgr.IAssTreeMgr;
import com.boco.eoms.partner.assess.AssTree.model.AssTree;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;

public abstract class AssFactoryMgrImpl implements IAssFactoryMgr {

	private IAssFactoryDao assFactoryDao;

	public IAssFactoryDao getAssFactoryDao() {
		return assFactoryDao;
	}


	public void setAssFactoryDao(IAssFactoryDao assFactoryDao) {
		this.assFactoryDao = assFactoryDao;
	}


	public List getAllKpiFactory(String templateId) {
		return assFactoryDao.getAllKpiFactory(templateId);
	}


	public List getAllKpiFactoryByNodeId(String nodeId) {
		return assFactoryDao.getAllKpiFactoryByNodeId(nodeId);
	}

	public void removeKpiFactory(AssFactory kpiFactory) {
		assFactoryDao.removeKpiFactory(kpiFactory);
		
	}


	public void saveKpiFactory(AssFactory kpiFactory) {
		assFactoryDao.saveKpiFactory(kpiFactory);
		
	}


	public void updateKpiFactory(AssFactory kpiFactory) {
		assFactoryDao.updateKpiFactory(kpiFactory);
		
	}
	
	public AssFactory getKpiFactory(String id) {
		return assFactoryDao.getKpiFactory(id);
	}


	public String findTemplateId(String nodeId) {
		// 先根据节点Id找到节点数，再从节点树对象中取出模板Id
		IAssTreeMgr treeMgr = (IAssTreeMgr) ApplicationContextHolder
		.getInstance().getBean("IlineAssTreeMgr");
		AssTree assTree = treeMgr.getTreeNodeByNodeId(nodeId);
		String templateId = assTree.getObjectId();
		return templateId;
	}
	
	public AssFactory getKpiFactoryByFactory(String factory) {
		return assFactoryDao.getKpiFactoryByFactory(factory);
	}
	
	public List getPartnerDepts(String city,String specialty) {
		StringBuffer where = new StringBuffer();
		if(!"".equals(city)){
			where.append(" and areaId = '");
			where.append(city);
			where.append("' ");
		}
		where.append(" and specialty = '");
		where.append(specialty);
		where.append("'");
		List partnerList = assFactoryDao.getPartnerDepts(where.toString());
		List parIdList = new ArrayList();
		PartnerDept partnerDept = null;
		for (int i = 0; i < partnerList.size(); i++) {
			partnerDept = (PartnerDept)partnerList.get(i);
			if(!parIdList.contains(partnerDept.getInterfaceHeadId())){
				parIdList.add(partnerDept.getInterfaceHeadId());
			}
		}
		return parIdList;
	}	
}

