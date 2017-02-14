package com.boco.eoms.partner.eva.mgr.impl;

import java.util.List;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.partner.eva.dao.IPnrEvaKpiFacturyDao;
import com.boco.eoms.partner.eva.mgr.IPnrEvaKpiFacturyMgr;
import com.boco.eoms.partner.eva.mgr.IPnrEvaTreeMgr;
import com.boco.eoms.partner.eva.model.PnrEvaKpiFactury;
import com.boco.eoms.partner.eva.model.PnrEvaTree;

public class PnrEvaKpiFacturyMgrImpl implements IPnrEvaKpiFacturyMgr {



	private IPnrEvaKpiFacturyDao pnrEvaKpiFacturyDao;
	
	


	public IPnrEvaKpiFacturyDao getPnrEvaKpiFacturyDao() {
		return pnrEvaKpiFacturyDao;
	}


	public void setPnrEvaKpiFacturyDao(IPnrEvaKpiFacturyDao pnrEvaKpiFacturyDao) {
		this.pnrEvaKpiFacturyDao = pnrEvaKpiFacturyDao;
	}


	public List getAllKpiFactury(String templateId) {
		// TODO Auto-generated method stub
		return pnrEvaKpiFacturyDao.getAllKpiFactury(templateId);
	}


	public List getAllKpiFacturyByNodeId(String nodeId) {
		// TODO Auto-generated method stub
		return pnrEvaKpiFacturyDao.getAllKpiFacturyByNodeId(nodeId);
	}

	public void removeKpiFactury(PnrEvaKpiFactury kpiFactury) {
		pnrEvaKpiFacturyDao.removeKpiFactury(kpiFactury);
		
	}


	public void saveKpiFactury(PnrEvaKpiFactury kpiFactury) {
		pnrEvaKpiFacturyDao.saveKpiFactury(kpiFactury);
		
	}


	public void updateKpiFactury(PnrEvaKpiFactury kpiFactury) {
		pnrEvaKpiFacturyDao.updateKpiFactury(kpiFactury);
		
	}
	
	public PnrEvaKpiFactury getKpiFactury(String id) {
		// TODO Auto-generated method stub
		return pnrEvaKpiFacturyDao.getKpiFactury(id);
	}


	public String findTemplateId(String nodeId) {
		// 先根据节点Id找到节点数，再从节点树对象中取出模板Id
		IPnrEvaTreeMgr treeMgr = (IPnrEvaTreeMgr) ApplicationContextHolder
		.getInstance().getBean("iPnrEvaTreeMgr");
		PnrEvaTree pnrEvaTree = treeMgr.getTreeNodeByNodeId(nodeId);
		String templateId = pnrEvaTree.getObjectId();
		return templateId;
	}
	
	public PnrEvaKpiFactury getKpiFacturyByFactury(String factury) {
		return pnrEvaKpiFacturyDao.getKpiFacturyByFactury(factury);
	}
	
}
