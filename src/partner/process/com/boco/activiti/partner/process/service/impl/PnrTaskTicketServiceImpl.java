package com.boco.activiti.partner.process.service.impl;

import com.boco.activiti.partner.process.dao.IPnrTaskTicketJDBCDao;
import com.boco.activiti.partner.process.dao.IPnrTroubleTicketJDBCDao;
import com.boco.activiti.partner.process.po.TaskModel;
import com.boco.activiti.partner.process.po.WorkOrderStatisticsModel;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.activiti.partner.process.dao.IPnrTaskTicketDao;
import com.boco.activiti.partner.process.model.PnrTaskTicket;
import com.boco.activiti.partner.process.service.IPnrTaskTicketService;

import java.util.List;

/**

 */
public class PnrTaskTicketServiceImpl extends CommonGenericServiceImpl<PnrTaskTicket> implements IPnrTaskTicketService {

    private IPnrTaskTicketDao pnrTaskTicketDao;
    private IPnrTaskTicketJDBCDao pnrTaskTicketJDBCDao;

    public void setPnrTaskTicketJDBCDao(IPnrTaskTicketJDBCDao pnrTaskTicketJDBCDao) {
        this.pnrTaskTicketJDBCDao = pnrTaskTicketJDBCDao;
    }

    public IPnrTaskTicketDao getPnrTaskTicketDao() {
		return pnrTaskTicketDao;
	}

	public void setPnrTaskTicketDao(IPnrTaskTicketDao pnrTaskTicketDao) {
		this.pnrTaskTicketDao = pnrTaskTicketDao;
		this.setCommonGenericDao(pnrTaskTicketDao);
	}

    public List<WorkOrderStatisticsModel> workOrderStatistics(String beginTime,String endTime,String subType){
        return pnrTaskTicketJDBCDao.workOrderStatistics(beginTime,endTime,subType);
    }
    public List<TaskModel> workOrderQuery(String deptId,String workerid,String beginTime, String endTime, String subType,String theme,String city,int beginNum,int endNum){
        return pnrTaskTicketJDBCDao.workOrderQuery(deptId,workerid,beginTime,endTime,subType,theme,city,beginNum,endNum);
    }
    public int workOrderCount(String deptId,String workerid,String beginTime, String endTime, String subType,String theme,String city){
        return pnrTaskTicketJDBCDao.workOrderCount(deptId,workerid,beginTime,endTime,subType,theme,city);
    }

	@Override
	public void saveOrUpatePerson(String processInstanceId,
			String[] personStrings) {
		// TODO Auto-generated method stub
		pnrTaskTicketJDBCDao.saveOrUpatePerson(processInstanceId,personStrings);
	}

	@Override
	public void saveOrUpateSpecialty(String processInstanceId,
			String[] specialtyStrings) {
		// TODO Auto-generated method stub
		pnrTaskTicketJDBCDao.saveOrUpateSpecialty(processInstanceId,specialtyStrings);

	}

	@Override
	public String getAttachmentNamesByProcessInstanceId(String processInstanceId) {
		// TODO Auto-generated method stub
		return 	pnrTaskTicketJDBCDao.getAttachmentNamesByProcessInstanceId(processInstanceId);

	}

	@Override
	public void saveOrUpateAttachment(String processInstanceId,
			String accessoriesNames) {
		// TODO Auto-generated method stub
		pnrTaskTicketJDBCDao.saveOrUpateAttachment(processInstanceId,accessoriesNames);

	}
}
