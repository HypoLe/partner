package com.boco.activiti.partner.process.service.impl;

import java.util.List;

import com.boco.activiti.partner.process.dao.IPnerWorkerStateDao;
import com.boco.activiti.partner.process.model.AccreditOrder;
import com.boco.activiti.partner.process.model.WorkerState;
import com.boco.activiti.partner.process.service.IPnrWorkerStateService;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
public class PnrWorkerStateServiceImpl extends CommonGenericServiceImpl<WorkerState> implements IPnrWorkerStateService{

	private IPnerWorkerStateDao pnrWorkerStateDao;
	
	
	public IPnerWorkerStateDao getPnrWorkerStateDao() {
		return pnrWorkerStateDao;
	}

	public void setPnrWorkerStateDao(IPnerWorkerStateDao pnrWorkerStateDao) {
		this.pnrWorkerStateDao = pnrWorkerStateDao;
		this.setCommonGenericDao(pnrWorkerStateDao);
	}
	
	/**
	 * 根据登录人的id和个人状态查出授权记录的
	 * @param workerId
	 * @param state
	 * @return
	 */
	@Override
	public WorkerState getWorkerId(String workerId, String state) {
		String hql = "from WorkerState w where w.workerId='"+workerId+"' and w.state = '"+state+"'";
		List<WorkerState> list = pnrWorkerStateDao.findByHql(hql);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	/***
	 * 根据授权记录id查询所有授权的工单
	 * @param workerStateId
	 * @return
	 */
	@Override
	public List<AccreditOrder> getAccreditOrder(String workerStateId) {
		String hql = "from AccreditOrder a where a.accreditId='"+workerStateId+"'";
					
		return pnrWorkerStateDao.findByHql(hql);
	}
	

	
	
	
	
}
