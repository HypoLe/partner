package com.boco.activiti.partner.process.dao;

import java.util.List;
import java.util.Map;

import com.boco.activiti.partner.process.model.PnrSmsSendEntity;
import com.boco.activiti.partner.process.po.TaskModel;
import com.boco.activiti.partner.process.po.WorkOrderStatisticsModel;

/**
 
 */
public interface IPnrTroubleTicketJDBCDao {
    public List<WorkOrderStatisticsModel> workOrderStatistics(String beginTime, String endTime, String type);
    public List<TaskModel> workOrderQuery(String deptId,String workerid,String beginTime, String endTime, String subType,String theme,String city,int beginNum,int endNum);
    public int workOrderCount(String deptId,String workerid,String beginTime, String endTime, String subType,String theme,String city);
    /**
     * 故障工单处理人保存关系表
     * @param processInstanceId
     * @param personStrings
     * @return void
     */
    public void saveOrUpatePerson(String processInstanceId,String[] personStrings);
    /**
     * 故障工单处理专业关系表
     * @param processInstanceId
     * @param personStrings
     * @return void
     */
    public void saveOrUpateSpecialty(String processInstanceId,String[] specialtyStrings);
    /**
     * 故障工单发出去后短信通知
     * @return 
     */
    public void saveSendContext(PnrSmsSendEntity p);
    /**
     * 故障工单与附件关系表
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
   /**
    * 通过区县静态表找到现场综合维护的地市、区县信息ID
    * @param gkCountryName
    * @return
    */
    public Map<String,String> getCityOrCoruntryIdByGkCountryName(String gkCountryName);
    
}
