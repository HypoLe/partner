package com.boco.activiti.partner.process.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.boco.activiti.partner.process.model.PnrActRuCountersign;
import com.boco.activiti.partner.process.model.PnrAndroidPhotoFile;
import com.boco.activiti.partner.process.model.PnrTransferOffice;
import com.boco.activiti.partner.process.po.AndroidQuery;
import com.boco.activiti.partner.process.po.AndroidWorkOrderTask;
import com.boco.activiti.partner.process.po.ConditionQueryModel;
import com.boco.activiti.partner.process.po.CycleCollarReportModel;
import com.boco.activiti.partner.process.po.MaleGuestSelectAttribute;
import com.boco.activiti.partner.process.po.MaleSceneDetailModel;
import com.boco.activiti.partner.process.po.MaleSceneStatisticsModel;
import com.boco.activiti.partner.process.po.MaterialQuantityModel;
import com.boco.activiti.partner.process.po.StatisticsMaterialInforModel;
import com.boco.activiti.partner.process.po.TaskModel;
import com.boco.activiti.partner.process.po.TransferMaleGuestFlag;
import com.boco.activiti.partner.process.po.TransferMaleGuestReturn;
import com.boco.activiti.partner.process.po.WorkOrderStatisticsModel;
import com.boco.eoms.base.webapp.form.PersonNameUrlForm;
import com.boco.eoms.commons.accessories.webapp.form.TawCommonsAccessoriesForm;

/**
 
 */
public interface IPnrTransferOfficeJDBCDao {
   /**
     * 故障工单与附件关系表
     * @param processInstanceId
     * @param accessoriesNames
     * @return void
     */
    public void saveOrUpateAttachment(String processInstanceId,String accessoriesNames);
    
    /**
     * 故障工单与附件关系表--只保存，不删除
     * @param processInstanceId
     * @param accessoriesNames
     * @return void
     */
    public void saveAttachment(String processInstanceId,String accessoriesNames,String step);
    
	/**
	 * 多个附件框，上传附件方法
	 * @param processInstanceId
	 * @param accessoriesNames
	 * @param step
	 * @param linkName
	 * @param attributeName
	 */
	public void saveMultipleAttachments(String processInstanceId,
			String accessoriesNames,String step,String linkName,String attributeName);
	
	/**
	 * 多附件回显
	 * @param processInstanceId	流程号
	 * @param step	所在环节步骤值
	 * @param linkName	所在环节KEY
	 * @param attributeName	界面中附件插件的 idField和name（保持一致）
	 * @return
	 */
	public Map<String,String> echoMultipleAttachments(String processInstanceId,String step,String linkName,String attributeName);
    
    /**
     * 通过流程号找到附件的英文名称
     * @param processInstanceId
     * @return String
     */
    public String getAttachmentNamesByProcessInstanceId(String processInstanceId);
    public List<TaskModel> workOrderQuery(String deptId,String workerid,String beginTime, String endTime, String subType,String theme,String city,int beginNum,int endNum);
    public List<TaskModel> workOrderQuery1(String flag,String deptId,String workerid,String beginTime, String endTime, String subType,String theme,String city,int beginNum,int endNum);
    public List<TaskModel> workOrderQueryAdmin(String userid,String flag,String deptId,String workerid,String beginTime, String endTime, String subType,String theme,String city,int beginNum,int endNum);
    public int workOrderCount(String deptId,String workerid,String beginTime, String endTime, String subType,String theme,String city);
    public int workOrderCount1(String flag,String deptId,String workerid,String beginTime, String endTime, String subType,String theme,String city);
    public int workOrderCountAdmin(String userId,String flag,String deptId,String workerid,String beginTime, String endTime, String subType,String theme,String city);
    public String getLoginPersonStatus(String userId,PnrTransferOffice pnrTransferOffice);
    /**
     * 预检预修工单--获取登录人所在环节
      * @author wangyue
      * @title: getLoginPersonStatusToPrecheck
      * @date Nov 25, 2014 2:21:33 PM
      * @param userId
      * @param pnrTransferOffice
      * @return String
     */
    public String getLoginPersonStatusToPrecheck(String userId,PnrTransferOffice pnrTransferOffice);
    /**
     * 新预检预修工单--登录人在工单所处环节
     * @param userId
     * @param pnrTransferOffice
     * @return
     */
    public String getLoginPersonStatusToNewPrecheck(String userId,PnrTransferOffice pnrTransferOffice);
    
    
    public List<TaskModel> workOrderMaleGuestQuery(String searchRange,String userId,String flag,String deptId,String workerid,String beginTime, String endTime, String subType,String theme,String city,int beginNum,int endNum);
    public int workOrderMaleGuestCount(String searchRange,String userId,String flag,String deptId,String workerid,String beginTime, String endTime, String subType,String theme,String city);
    /**
     * 
      * @author wangyue
      * @title: getMaleGuestFlagData
      * @date Sep 15, 2014 3:22:07 PM
      * @param workOrderNo 工单编号
      * @return TransferMaleGuestInform 工单状态类对象
     */
    public TransferMaleGuestFlag getMaleGuestFlagData(String workOrderNo);
    
    /**
     * 获取工单回单xml信息
      * @author wangyue
      * @title: getMaleGuestReturnData
      * @date Sep 16, 2014 10:02:15 AM
      * @param workOrderNo
      * @return TransferMaleGuestReturn
     */
    public TransferMaleGuestReturn getMaleGuestReturnData(String workOrderNo);
    /**
     * 根据登录人id,查询出属于该登录人的传输局权限
      * @author wangyue
      * @title: getLoginRole
      * @date Sep 19, 2014 8:56:50 AM
      * @return List
     */
    public List<PersonNameUrlForm> getLoginRole(String userId);
    
    public List<WorkOrderStatisticsModel> transferOfficeStatistics();
    public String calculateThePercentage(Double a, Double b);
    /**
     * 根据工单id查询公客工单编号
      * @author wangyue
      * @title: getMaleGuestNumberByThemeId
      * @date Sep 30, 2014 10:25:52 AM
      * @param themeId
      * @return String
     */
    public String getMaleGuestNumberByThemeId(String themeId);
    
    /**
	 * 工单管理-传输局工单-抢修工单-待回复工单 集合条数
	 * 
	 * @param userId
	 *            用户ID
	 * @param sendStartTime
	 *            派单开始时间
	 * @param sendEndTime
	 *            派单结束时间
	 * @param wsNum
	 *            工单号
	 * @param wsTitle
	 *            工单主题
	 * @param status
	 *            当前状态
	 * @return
	 */
	public int getToreplyJobOfEmergencyJobCount(String userId,
			String sendStartTime, String sendEndTime, String wsNum,
			String wsTitle, String status);

	/**
	 * 工单管理-传输局工单-抢修工单-待回复工单 集合
	 * 
	 * @param userId
	 *            用户ID
	 * @param sendStartTime
	 *            派单开始时间
	 * @param sendEndTime
	 *            派单结束时间
	 * @param wsNum
	 *            工单号
	 * @param wsTitle
	 *            工单主题
	 * @param status
	 *            当前状态
	 * @param firstResult
	 * @param endResult
	 * @param pageSize
	 * @return
	 */
	public List<TaskModel> getToreplyJobOfEmergencyJobList(String userId,
			String sendStartTime, String sendEndTime, String wsNum,
			String wsTitle, String status, int firstResult, int endResult,
			int pageSize);
	
	/**
	 * 工单管理-传输局工单-预检预修工单-待回复工单 集合条数
	 * 
	 * @param userId
	 *            用户ID
	 * @param sendStartTime
	 *            派单开始时间
	 * @param sendEndTime
	 *            派单结束时间
	 * @param wsNum
	 *            工单号
	 * @param wsTitle
	 *            工单主题
	 * @param status
	 *            当前状态
	 * @return
	 */
	public int getToreplyJobOfPreflightPreparationJobCount(String userId,
			String sendStartTime, String sendEndTime, String wsNum,
			String wsTitle, String status);

	/**
	 * 工单管理-传输局工单-预检预修工单-待回复工单 集合
	 * 
	 * @param userId
	 *            用户ID
	 * @param sendStartTime
	 *            派单开始时间
	 * @param sendEndTime
	 *            派单结束时间
	 * @param wsNum
	 *            工单号
	 * @param wsTitle
	 *            工单主题
	 * @param status
	 *            当前状态
	 * @param firstResult
	 * @param endResult
	 * @param pageSize
	 * @return
	 */
	public List<TaskModel> getToreplyJobOfPreflightPreparationJobList(String userId,
			String sendStartTime, String sendEndTime, String wsNum,
			String wsTitle, String status, int firstResult, int endResult,
			int pageSize);
	
	/**
	 * 工单管理-"公客-传输局工单"-待回复工单 集合条数
	 * 
	 * @param userId
	 *            用户ID
	 * @param sendStartTime
	 *            派单开始时间
	 * @param sendEndTime
	 *            派单结束时间
	 * @param wsNum
	 *            工单号
	 * @param wsTitle
	 *            工单主题
	 * @param status
	 *            当前状态
	 * @return
	 */
	public int getToreplyJobOfMaleGuestTransmissionBureauJobCount(String userId,
			MaleGuestSelectAttribute selectAttribute);
	public int getToreplyJobOfMaleGuestTransmissionBureauJobNoCount(String userId,
			MaleGuestSelectAttribute selectAttribute);

	/**
	 * 工单管理-"公客-传输局工单"-待回复工单 集合
	 * 
	 * @param userId
	 *            用户ID
	 * @param sendStartTime
	 *            派单开始时间
	 * @param sendEndTime
	 *            派单结束时间
	 * @param wsNum
	 *            工单号
	 * @param wsTitle
	 *            工单主题
	 * @param status
	 *            当前状态
	 * @param firstResult
	 * @param endResult
	 * @param pageSize
	 * @return
	 */
	public List<TaskModel> getToreplyJobOfMaleGuestTransmissionBureauJobList(String userId,
			MaleGuestSelectAttribute selectAttribute, int firstResult, int endResult,
			int pageSize);
	/**
	 * 工单管理-"公客-传输局工单"-未归集-归集集合
	 * 
	 * @param userId
	 *            用户ID
	 * @param sendStartTime
	 *            派单开始时间
	 * @param sendEndTime
	 *            派单结束时间
	 * @param wsNum
	 *            工单号
	 * @param wsTitle
	 *            工单主题
	 * @param status
	 *            当前状态
	 * @param firstResult
	 * @param endResult
	 * @param pageSize
	 * @return
	 */
	public List<TaskModel> getToreplyJobOfMaleGuestTransmissionBureauJobNoList(String userId,
			MaleGuestSelectAttribute selectAttribute, int firstResult, int endResult,
			int pageSize);
	/**
	 * 工单管理-"公客-传输局工单"-归集工单 集合
	 * 
	 * @param userId
	 *            用户ID
	 * @param sendStartTime
	 *            派单开始时间
	 * @param sendEndTime
	 *            派单结束时间
	 * @param wsNum
	 *            工单号
	 * @param wsTitle
	 *            工单主题
	 * @param status
	 *            当前状态
	 * @param firstResult
	 * @param endResult
	 * @param pageSize
	 * @return
	 */
	public List<TaskModel> getToreplyJobOfMaleGuestTransmissionBureauJobParentList(String userId,
			MaleGuestSelectAttribute selectAttribute, int firstResult, int endResult,
			int pageSize);
	
	/**
	 * 工单管理-"公客-传输局工单"-未归集工单 集合
	 * 
	 * @param userId
	 *            用户ID
	 * @param sendStartTime
	 *            派单开始时间
	 * @param sendEndTime
	 *            派单结束时间
	 * @param wsNum
	 *            工单号
	 * @param wsTitle
	 *            工单主题
	 * @param status
	 *            当前状态
	 * @param firstResult
	 * @param endResult
	 * @param pageSize
	 * @return
	 */
	public List<TaskModel> getToreplyJobOfMaleGuestTransmissionBureauJobParentNoList(String userId,
			MaleGuestSelectAttribute selectAttribute, int firstResult, int endResult,
			int pageSize);
	
	/**
	 * 工单管理--传输局工单-公客工单-由我创建--集合条数
	 * @param userId
	 * @param selectAttribute
	 * @return
	 */
	public int getMaleGuestCountByInitiator(String userId,MaleGuestSelectAttribute selectAttribute);
	
	
	/**
	 * 工单管理--传输局工单--公客工单--由我创建 集合
	 * @param userId
	 * @param selectAttribute
	 * @param firstResult
	 * @param endResult
	 * @param pageSize
	 * @return
	 */
	public List<TaskModel> getMaleGuestByInitiator(String userId,MaleGuestSelectAttribute selectAttribute,int firstResult, int endResult,
			int pageSize);
	/**
     * 获得区县外包人员
      * @author wangyue
      * @title: getEpiboly
      * @date Oct 27, 2014 4:56:38 PM
      * @param areaId 地市id
      * @param country  区县
      * @return List<TawSystemUser>
     */
    public List getEpiboly(String areaId,String country,String term);



  /**
     * 获得市传输局人员
      * @author wangyue
      * @title: getTransferOfficePerson
      * @date Oct 27, 2014 4:55:55 PM
      * @param city  地市
      * @return List<TawSystemUser>
     */
    public List getTransferOfficePerson(String city);
    /**
     * 根据工单号判断是否存在
      * @author wangyue
      * @title: judgeWorkOrderIsExit
      * @date Nov 12, 2014 5:03:41 PM
      * @param workOrderNumber
      * @return List
     */
    public List judgeWorkOrderIsExit(String workOrderNumber);
    
    public List getWorkOrderByWorkNumber(String workOrderNumber);
    
    /**
     * 返回附件列表
      * @author wangyue
      * @title: getSheetAccessoriesByPid
      * @date Nov 24, 2014 6:26:27 PM
      * @param processInstanceId
      * @return List<TawCommonsAccessoriesForm>
     */
    public List<TawCommonsAccessoriesForm> getSheetAccessoriesByPid(String processInstanceId);
    
    /**
      返回id串
     */
    public String getSheetAccessoriesByCity(String cityId);
    /**
    返回id串
   */
   public Map<String,String> getSheetAccessoriesByName(String cnames);
    /**
     * 新预检预修工单--附件列表
      * @author wangyue
      * @title: getSheetAccessoriesByPidToPrecheck
      * @date Mar 5, 2015 9:18:45 AM
      * @param processInstanceId
      * @return List<TawCommonsAccessoriesForm>
     */
    public List<TawCommonsAccessoriesForm> getSheetAccessoriesByPidToPrecheck(String processInstanceId);
    
    /**
     * ol-bbu机房优化流程--附件显示
     * 
     * @param processInstanceId
     * @return
     * @throws Exception
     */
    public List<TawCommonsAccessoriesForm> showPnrOltBbuRoomSheetAccessoriesList(String processInstanceId);
    
    
    /*
     * 大修改造--附件列表
     */
    public List<TawCommonsAccessoriesForm> getAccessoriesList(String processInstanceId,String flag);
    
    /**
     * 机房拆除流程--附件显示
     * @param processInstanceId
     * @param flag
     * @return
     * @throws Exception
     */
     public List<TawCommonsAccessoriesForm> getRoomDemolitionAccessoriesList(String processInstanceId,String flag);
    
    /**
     * 根据公客工单号判断该工单是否处理完--公客催单接口用
     * @param maleGuestNumber公客工单号
     * @return
     */
    public String judgeOrderIsDo(String maleGuestNumber);
    /***
	 * 工单管理--传输局工单--公客工单--已处理工单（包括已归档的工单） 集合
	 * @param userId
	 * @param selectAttribute
	 * @param firstResult
	 * @param endResult
	 * @param pageSize
	 * @return
	 */
	public List<TaskModel> getMaleGuestByIntiatorOrAssignee(String userId,MaleGuestSelectAttribute selectAttribute,int firstResult, int endResult,
			int pageSize);
	 /***
	  * 工单管理--传输局工单--公客工单--已处理工单（包括已归档的工单） 集合总数
	  * @param userId
	  * @param selectAttribute
	  * @return
	  */
	public int getMaleGuestCountByInitiatorOrAssignee(String userId,MaleGuestSelectAttribute selectAttribute);
	
	/**
	 * 工单管理--传输局工单--公客工单--抓回功能（只查找具备抓回功能的工单） 集合总数
	 * @param userId
	 * @param selectAttribute
	 * @return
	 */
	public int getMaleGeustCountToGetBack(String userId,MaleGuestSelectAttribute selectAttribute);
	/**
	 * 工单管理--传输局工单--公客工单--抓回功能（只查找具备抓回功能的工单） 集合
	 * @param userId
	 * @param selectAttribute
	 * @param firstResult
	 * @param endResult
	 * @param pageSize
	 * @return
	 */
	public List<TaskModel> getMaleGeustToGetBack(String userId,MaleGuestSelectAttribute selectAttribute,int firstResult, int endResult,
			int pageSize);
	/**
	 *  插入运行时的会审辅助数据
	 * @param pnrActRuCountersign
	 * @return
	 */
	public boolean insertCountersignInfo(PnrActRuCountersign pnrActRuCountersign) throws Exception;
	/**
	 *  会审结束，删除数据
	 * @param pnrActRuCountersign
	 * @return
	 */
	public boolean deleteCountersignInfo(String processInstanceId);
	/**
	 * 会审的数量
	 * @param processInstanceId
	 * @return
	 */
	public int getCountersignCountByProcessInstanceId(String processInstanceId);
	
	/**
	 * 工单管理--大修改造工单--获取登录人身份
	  * @author wangyue
	  * @title: getLoginPersonStatusToOverhaulRemake
	  * @date Feb 2, 2015 11:26:03 AM
	  * @return String
	 */
	public String getLoginPersonStatusToOverhaulRemake(String userId,
			PnrTransferOffice pnrTransferOffice);
	
	/**
	 * 序列-工单个数
	 */
	
	public int  getCurrDateSheetCountNum(String sequenceName);
	
	/**
	 * 根据流程ID和环节的ID值查询附件
	 * @param processInstanceId 流程ID
	 * @param TaskDefinitionKey 环节的ID
	 * @return
	 */
	public String getAttachmentNamesByProcessInstanceIdAndUserTaskId(String processInstanceId,String userTaskId);
	
	/**
	 * 根据流程ID和步骤值删除附件
	 * @param processInstanceId 流程ID
	 * @param Step 步骤值
	 * @return
	 */
	public void deleteAttachmentNamesByProcessInstanceIdAndStep(String processInstanceId,
			String Step) ;
	/**
	 * 预检预修及大修改造：事前照片平均经纬度
	 * 
	 */
	public Map<String, String> getAvgByProcessInstanceId(String processInstanceId);
	
	/**
	 * 
	 * @param processKey
	 * @return
	 */
	public Map<String, String> getAndroidLimitDistance(String processKey);
	
	/**
	 * 施工队之后的审核环节界面上的距离显示，没有距离默认为"无"
	 * @param processInstanceId
	 * @param type
	 * @return
	 */
	public String getDistanceShow(String processInstanceId,String type);
	/**
	 *  Android获取待处理工单总数--手机端工单条件查询
	 * @param androidQuery 条件查询
	 * @return
	 */
	public int getAndroidTaskCount(AndroidQuery androidQuery);
	/**
	 *  Android获取待处理工单集合--手机端工单条件查询
	 * @param androidQuery
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<AndroidWorkOrderTask> getAndroidTaskList(AndroidQuery androidQuery,int pageIndex,int pageSize );
	public List<AndroidWorkOrderTask> getAndroidTaskListTogether(AndroidQuery androidQuery,int pageIndex,int pageSize );
	/**
	 * 工单管理-"公客-传输局工单"-手动归档工单 集合
	 */
	public List<TaskModel> manualArchiveList(String userId,String startDate,String endDate,
			MaleGuestSelectAttribute selectAttribute, int firstResult, int endResult,
			int pageSize);
	/**
	 * 工单管理-"公客-传输局工单"-手动归档工单 集合条数
	 */
	public int manualArchiveCount(String userId,String startDate,String endDate,
			MaleGuestSelectAttribute selectAttribute);
	
	/**
	 * 本地网-预检预修-按工单号查询工单详情
	 */
	public List<PnrTransferOffice> getWorkOrderDetails(String processInstanceId);
	
	//归集工单列表统计数
	public int querySingleImputationCount(String userId,MaleGuestSelectAttribute selectAttribute);
	
	//归集工单列表统计数
	public List<TaskModel> querySingleImputationList(String userId,MaleGuestSelectAttribute selectAttribute, int firstResult, int endResult,int pageSize);
	
	//未归集工单列表统计数
	public int queryNoSingleImputationCount(String userId,MaleGuestSelectAttribute selectAttribute);
	
	//未归集工单列表统计数
	public List<TaskModel> queryNoSingleImputationList(String userId,MaleGuestSelectAttribute selectAttribute, int firstResult, int endResult,int pageSize);
    //归集工单校验---接口生成工单:按照局站编号，一定时间内(7*24)，在处理环节未处理的，有两个且以上，没有归集工单，---》生成归集工单，update 子工单。
	public Map mailGuestTogetherVerification(String siteCd);
    //将子工单设置 归集的工单号
	public void updateProcessInstanceIdForSubprocess(List sublist,String tprocessInstanceId);

	
	//抢修工单审核 一次核验统计数
	public int getFirstVerifyCount(String userId,MaleGuestSelectAttribute selectAttribute);
	//抢修工单审核 一次核验列表
	public List<TaskModel> getFirstVerifyList(String userId,MaleGuestSelectAttribute selectAttribute, int firstResult, int endResult,int pageSize);
	
	//抢修工单审核 二次抽检统计数
	public int getSecondInspectionCount(String userId,MaleGuestSelectAttribute selectAttribute);
	//抢修工单审核 二次抽检列表
	public List<TaskModel> getSecondInspectionList(String userId,MaleGuestSelectAttribute selectAttribute, int firstResult, int endResult,int pageSize);
	
	//抢修工单审核 抢修材料周期领用表统计数
	public int getRepairMaterialCycleTableCount(String userId,MaleGuestSelectAttribute selectAttribute);
	//抢修工单审核 抢修材料周期领用表列表
	public List<TaskModel> getRepairMaterialCycleTableList(String userId,MaleGuestSelectAttribute selectAttribute, int firstResult, int endResult,int pageSize);
	
	//更新报表审批状态
	public void updateApproveFlag(Map<String,String> param);
	
	//获取报表未审批的工单个数
	public int getNotApprovedSheetCount(MaleGuestSelectAttribute selectAttribute);
	
	//查询审批人签字
	public String getApprovalSign(MaleGuestSelectAttribute selectAttribute);
	
	//提交审批人签字
	public int submitApprovalSign(MaleGuestSelectAttribute selectAttribute);
	
	//插入周期领用表
	public void insertCycleCollarReport(List<CycleCollarReportModel> reportList,String userId,MaleGuestSelectAttribute selectAttribute);
	
	//获取处理人（公用）
	public String getDealingPeopleOfRepair(String areaId,String roleId);
	
	//去周期领用表的报表编号
//	public List<String> getReportNumbers(String userId,MaleGuestSelectAttribute selectAttribute);
//	
//	//生成周期领用表，报表编号+开始日期+结束日期+所属日期
//	public List<CycleCollarReportModel> getCycleCollarReportMsg(String reportNumber,String userId,MaleGuestSelectAttribute selectAttribute);
	
	//根据开始日期和结束日期，查询这个开始日期和这个结束日期已经生成的周期领用表 条数
	public int getCycleCollarReportCountByStartDateAndEndDate(String userId,MaleGuestSelectAttribute selectAttribute);
	
	//根据开始日期和结束日期，查询这个开始日期和这个结束日期已经生成的周期领用表 集合
	public List<TaskModel> getCycleCollarReportListByStartDateAndEndDate(String userId,MaleGuestSelectAttribute selectAttribute,int firstResult,int endResult,int pageSize);

	//根据开始日期和结束日期，在这个时间范围内已经生成的周期领用表（用开始日期和结束日期查询不到数据的时候）
	public List<CycleCollarReportModel> getCycleCollarReportListByHomeDate(String userId,MaleGuestSelectAttribute selectAttribute);
	
	//周期领用表，获取报表提交人
	public String getSubmitUserIdOfCycleCollarReport(String userId,MaleGuestSelectAttribute selectAttribute);
	
	//获取周期领用表的部分字段的值（公用）
	public String getAttributeValueOfCycleCollarReport(String userId,MaleGuestSelectAttribute selectAttribute,String flag);
	
	//提交周期领用表
	public void submitCreateCycleCollarReport(String userId,String reportNumber,String fuJianVal,MaleGuestSelectAttribute selectAttribute,String accessorieId,String accessorieName);
	
	//取周期领用表上传的附件信息
	public String getAttachmentsOfCycleCollarReport(String reportNumber);
	//取二次核验已提交报表中的超时工单
	public List<String> getTimeOverListOfSecond(MaleGuestSelectAttribute selectAttribute);
	
	//获取一次核验，二次抽检的部分字段的值（公用）
	public String getAttributeValueOfFirstAndSecond(String userId,MaleGuestSelectAttribute selectAttribute,String flag);
	
	//施工照片补录 照片总数
	public int getMakeupPhotosCount(String userId,MaleGuestSelectAttribute selectAttribute);
	
	//施工照片补录 照片集合
	public List<TaskModel> getMakeupPhotosList(String userId,MaleGuestSelectAttribute selectAttribute, int firstResult, int endResult,int pageSize);
	
	//查询补录照片
	public List<PnrAndroidPhotoFile> getPrecheckPhotoes(Map<String,String> param);
	
	//获取照片的最小时间
	public String getMinDateOfPhoto(String photoesIds,Map<String,String> param);
	
	//查看现场照片列表
	public List<PnrAndroidPhotoFile> getLiveCameraList(Map<String,String> param);
	
	//查看归集工单子工单
	public List<TaskModel> getSubWorkOrderOfSingleImputationList(String userId,MaleGuestSelectAttribute selectAttribute, int firstResult, int endResult,int pageSize);
	
	//查询主场景名
	public String getMainScenesNameById(String id);
	
	//查询子场景名
	public String getCopyScenesNameById(String id);
	
	//审批位置统计信息（材料金额、电杆、光缆数量、接头盒数量）
	public List<StatisticsMaterialInforModel> statisticsMaterialInfor(String userId,MaleGuestSelectAttribute selectAttribute,String reportFlag);
	
	//显示公客场景模板明细
	public List<MaleSceneDetailModel> getMaleSceneDetail(String processInstanceId,String linkFlag,Map<String,String> param);
	
	//汇总公客场景模板审批材料统计 总金额统计、电杆数、接头盒数、光缆长度 
	//当为一次核验列表显示时：有一次核验场景模板数据用一次核验的，没有取施工队的场景模板数据。
	//当为二次抽检列表显示时：有二次抽检场景模板数据用二次抽检的，没有找一次核验的，一次核验没有就找施工队的。
	//当为周期领用表，同二次抽检，因为周期领用表不允许修改场景模板。
	public MaleSceneStatisticsModel getMaleSceneStatistics(String processInstanceId,String reportFlag,String operationType,Map<String,String> param);
	
	//取公客归集工单子工单的最小时间（最小告警发生时间或者最小派单时间）
	public Date getMinDateForSubWorkOrder(String mainProcessInstanceId,List sublist,String flag,Map<String,String> param);
	
	//生成周期领用表的材料数量统计
	public MaterialQuantityModel getMaterialQuantityOfCycleReport(String userId,MaleGuestSelectAttribute selectAttribute,Map<String,String> param);
	
	//抢修工单 工单查询条数
	public int getWorkOrderQueryOfTransferOfficeListCount(String areaid,String userid, String flag,String processKey,ConditionQueryModel conditionQueryModel);
	
	//抢修工单 工单结果集
	public List<TaskModel> getWorkOrderQueryOfTransferOfficeList(String areaid,String userid,String flag, String processKey,ConditionQueryModel conditionQueryModel, 
			int firstResult,int endResult, int pageSize);
	
	//查询 周期领用表提交审批后没有归档的工单 统计数
	public int manualBatchArchiveForCycleReportCount(String userId,MaleGuestSelectAttribute selectAttribute);
	
	//查询 周期领用表提交审批后没有归档的工单 列表
	public List<TaskModel> manualBatchArchiveForCycleReportList(String userId,MaleGuestSelectAttribute selectAttribute,int firstResult,int endResult,int pageSize);
	
	/**
	 * 	 通过报表编号查询周期领用表的条数
	 	 * @author WANGJUN
	 	 * @title: getCycleReportCountByReportNumber
	 	 * @date Jun 30, 2016 10:43:01 AM
	 	 * @param reportNumber 报表编号
	 	 * @return int
	 */
	public int getCycleReportCountByReportNumber(String reportNumber);
	
	/**
	 * 	 通过报表编号查询验证表里是否有数据
	 	 * @author WANGJUN
	 	 * @title: getVerifiedCountByReportNumber
	 	 * @date Jun 30, 2016 11:21:33 AM
	 	 * @param reportNumber
	 	 * @return int
	 */
	public int getVerifiedCountByReportNumber(String reportNumber);
	

	/**
	 *   插入验证信息
	 	 * @author WANGJUN
	 	 * @title: insertVerifiedNumber
	 	 * @date Jun 30, 2016 1:40:37 PM
	 	 * @param reportNumber
	 	 * @param type void
	 */
	public boolean insertVerifiedNumber(String reportNumber,String type);
	
	/**
	 * 	 用周期领用表报表编号查询对应的开始时间、结束时间、区县
	 	 * @author WANGJUN
	 	 * @title: getTimeCountyByReportNum
	 	 * @date Jun 30, 2016 3:26:12 PM
	 	 * @param reportNum
	 	 * @return Map<String,String>
	 */
	public Map<String,String> getTimeCountyByReportNum(String reportNum);
	
	/**
	 * 	 用报表编号取提交时间
	 	 * @author WANGJUN
	 	 * @title: getSubmitDateByReportNum
	 	 * @date Jul 6, 2016 10:30:23 AM
	 	 * @param reportNum
	 	 * @return Date
	 */
	public Date getSubmitDateByReportNum(String reportNum);
	
	/**
	 * 	 手机端 省公司抽检已标记的工单列表条数
	 	 * @author XuJinLiang
	 	 * @title: getAndroidWorkOrderCount
	 	 * @date Aug 22, 2016 5:58:42 PM
	 	 * @param sampling_userid 标记人
	 	 * @return int
	 */
	public int getAndroidWorkOrderCount(String sampling_userid);

	/**
	 * 	 手机端 省公司抽检已标记的工单列表明细
	 	 * @author XuJinLiang
	 	 * @title: getAndroidWorkOrderList
	 	 * @date Aug 22, 2016 5:56:52 PM
	 	 * @param sampling_userid 标记人
	 	 * @param pageIndexTemp 
	 	 * @param pageIndex
	 	 * @return List<AndroidWorkOrderTask>
	 */
	public List<AndroidWorkOrderTask> getAndroidWorkOrderList(String sampling_userid,int pageIndexTemp,int pageIndex);
	
	/**
	 * 	 手机端 省公司抽检保存抽检意见
	 	 * @author XuJinLiang
	 	 * @title: updateWorkOrderOpinion
	 	 * @date Aug 22, 2016 5:59:18 PM
	 	 * @param process_instance_id 工单号
	 	 * @param opinion 抽检意见
	 	 * @return int 更新的条数
	 */
	public int updateWorkOrderOpinion(String process_instance_id,String opinion);
	
	/**
	 *   公客工单查询 条数 20161222
	 	 * @author WangJun
	 	 * @title: getMaleGuestWorkOrderQueryCount
	 	 * @date Dec 22, 2016 3:34:28 PM
	 	 * @param areaid
	 	 * @param userid
	 	 * @param conditionQueryModel
	 	 * @return int
	 */
	public int getMaleGuestWorkOrderQueryCount(String areaid,String userid,ConditionQueryModel conditionQueryModel);
	
	/**
	 *   公客工单查询 列表 20161222
	 	 * @author WangJun
	 	 * @title: getMaleGuestWorkOrderQueryList
	 	 * @date Dec 22, 2016 3:35:58 PM
	 	 * @param areaid
	 	 * @param userid
	 	 * @param conditionQueryModel
	 	 * @param firstResult
	 	 * @param endResult
	 	 * @param pageSize
	 	 * @return List<TaskModel>
	 */
	public List<TaskModel> getMaleGuestWorkOrderQueryList(String areaid,String userid,ConditionQueryModel conditionQueryModel, int firstResult,int endResult, int pageSize);
	
}
