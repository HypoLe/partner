package com.boco.activiti.partner.process.service;

import com.boco.activiti.partner.process.po.TaskModel;
import com.boco.activiti.partner.process.po.WorkOrderStatisticsModel;
import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
import com.boco.activiti.partner.process.model.PnrTaskTicket;

import java.util.List;

/**
 
 */
public interface IPnrTaskTicketService extends CommonGenericService<PnrTaskTicket> {
    public List<WorkOrderStatisticsModel> workOrderStatistics(String beginTime,String endTime,String subType);
    public List<TaskModel> workOrderQuery(String deptId,String workerid,String beginTime, String endTime, String subType,String theme,String city,int beginNum,int endNum);
    public int  workOrderCount(String deptId,String workerid,String beginTime, String endTime, String subType,String theme,String city );
    /**
     * 任务工单处理人保存关系表
     * @param processInstanceId
     * @param personStrings
     * @return void
     */
    public void saveOrUpatePerson(String processInstanceId,String[] personStrings);
    /**
     * 任务工单处理专业关系表
     * @param processInstanceId
     * @param personStrings
     * @return void
     */
    public void saveOrUpateSpecialty(String processInstanceId,String[] specialtyStrings);
    /**
     * 任务工单与附件关系表
     * @param processInstanceId
     * @param accessoriesNames
     * @return void
     */
    public void saveOrUpateAttachment(String processInstanceId,String accessoriesNames);
    /**
     * 通过流程号找到附件的英文名称
     * @param processInstanceId
     * @return String
     */
    public String getAttachmentNamesByProcessInstanceId(String processInstanceId);
    
}
