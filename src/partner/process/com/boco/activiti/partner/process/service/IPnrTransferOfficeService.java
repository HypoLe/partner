package com.boco.activiti.partner.process.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.boco.activiti.partner.process.model.PnrActRuCountersign;
import com.boco.activiti.partner.process.model.PnrAndroidPhotoFile;
import com.boco.activiti.partner.process.model.PnrTransferOffice;
import com.boco.activiti.partner.process.model.TransferMaleGuestInform;
import com.boco.activiti.partner.process.po.AndroidQuery;
import com.boco.activiti.partner.process.po.AndroidWorkOrderTask;
import com.boco.activiti.partner.process.po.ConditionQueryModel;
import com.boco.activiti.partner.process.po.CycleCollarReportModel;
import com.boco.activiti.partner.process.po.MaleGuestSelectAttribute;
import com.boco.activiti.partner.process.po.MaleSceneDetailModel;
import com.boco.activiti.partner.process.po.MaleSceneStatisticsModel;
import com.boco.activiti.partner.process.po.MaterialQuantityModel;
import com.boco.activiti.partner.process.po.ParameterModel;
import com.boco.activiti.partner.process.po.StatisticsMaterialInforModel;
import com.boco.activiti.partner.process.po.TaskModel;
import com.boco.activiti.partner.process.po.TransferMaleGuestFlag;
import com.boco.activiti.partner.process.po.TransferMaleGuestReturn;
import com.boco.activiti.partner.process.po.WorkOrderStatisticsModel;
import com.boco.eoms.base.webapp.form.PersonNameUrlForm;
import com.boco.eoms.commons.accessories.webapp.form.TawCommonsAccessoriesForm;
import com.boco.eoms.deviceManagement.common.service.CommonGenericService;

public interface IPnrTransferOfficeService extends
		CommonGenericService<PnrTransferOffice> {
	/**
	 * 故障工单与附件关系表
	 * 
	 * @param processInstanceId
	 * @param accessoriesNames
	 * @return void
	 */
	public void saveOrUpateAttachment(String processInstanceId,
			String accessoriesNames);
	
	/**
	 * 故障工单与附件关系表--只保存，不删除
	 * 
	 * @param processInstanceId
	 * @param accessoriesNames
	 * @param step
	 * @return void
	 */
	public void saveAttachment(String processInstanceId,
			String accessoriesNames,String step);
	
	/**
	 * 多个附件框，上传附件方法
	 * @param processInstanceId 流程号
	 * @param accessoriesNames 附件名
	 * @param step 所在环节步骤值
	 * @param linkName 所在环节KEY
	 * @param attributeName 界面中附件插件的 idField和name（保持一致）
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
	 * 
	 * @param processInstanceId
	 * @return String
	 */
	public String getAttachmentNamesByProcessInstanceId(String processInstanceId);

	public List<TaskModel> workOrderQuery(String deptId, String workerid,
			String beginTime, String endTime, String subType, String theme,
			String city, int beginNum, int endNum);

	public List<TaskModel> workOrderQuery1(String flag, String deptId,
			String workerid, String beginTime, String endTime, String subType,
			String theme, String city, int beginNum, int endNum);

	public List<TaskModel> workOrderQueryAdmin(String userId, String flag,
			String deptId, String workerid, String beginTime, String endTime,
			String subType, String theme, String city, int beginNum, int endNum);

	public int workOrderCount(String deptId, String workerid, String beginTime,
			String endTime, String subType, String theme, String city);

	public int workOrderCount1(String flag, String deptId, String workerid,
			String beginTime, String endTime, String subType, String theme,
			String city);

	public int workOrderCountAdmin(String userId, String flag, String deptId,
			String workerid, String beginTime, String endTime, String subType,
			String theme, String city);

	/**
	 * 公客故障工单查询
	 * 
	 * @author wangyue
	 * @title: workOrderMaleGuestQuery
	 * @date Oct 22, 2014 10:44:13 AM
	 * @param flag
	 * @param deptId
	 * @param workerid
	 * @param beginTime
	 * @param endTime
	 * @param subType
	 * @param theme
	 * @param city
	 * @param beginNum
	 * @param endNum
	 * @return List<TaskModel>
	 */
	public List<TaskModel> workOrderMaleGuestQuery(String searchRange,
			String userId, String flag, String deptId, String workerid,
			String beginTime, String endTime, String subType, String theme,
			String city, int beginNum, int endNum);

	public int workOrderMaleGuestCount(String searchRange, String userId,
			String flag, String deptId, String workerid, String beginTime,
			String endTime, String subType, String theme, String city);

	/**
	 * 根据登录人userid 查处该登录人在整个工单处于哪个职务
	 * 
	 * @param userId
	 * @param pnrTransferOffice
	 * @return
	 */
	public String getLoginPersonStatus(String userId,
			PnrTransferOffice pnrTransferOffice);

	public String getLoginPersonStatusToPrecheck(String userId,
			PnrTransferOffice pnrTransferOffice);
	/**
	 * 新预检预修，根据userID确定登录人在整个工单处于什么身份
	 * @param userId
	 * @param pnrTransferOffice
	 * @return
	 */
	public String getLoginPersonStatusToNewPrecheck(String userId,
			PnrTransferOffice pnrTransferOffice);
	/**
	 * 公客接口派单
	 * 
	 * @param maleGuest
	 */
	public void performAdd(PnrTransferOffice maleGuest);
	/**
	 * 公客接口派单
	 * --公客投诉与正常的工单需要归集
	 * @author chenbing
	 * @title: performAddTogether
	 * @date Mar 10, 2016 16:14:34 PM
	 * @param precheck
	 *            void
 */
	public void performAddTogether(PnrTransferOffice maleGuest);
	
	/**
	 * 公客接口--修改原工单--针对重修情况
	 * @param maleGuest
	 */
	public void updateMaleGuestByWorkOrder(PnrTransferOffice maleGuest,String flag);

	/**
	 * 获取工单状态接收接口信息
	 * 
	 * @author wangyue
	 * @title: getMaleGuestData
	 * @date Sep 15, 2014 3:09:02 PM
	 * @param workOrderNo
	 *            工单编号
	 * @return TransferMaleGuestFlag 工单状态类对象
	 */
	public TransferMaleGuestFlag getMaleGuestData(String workOrderNo);

	/**
	 * 获取工单回单接口xml信息
	 * 
	 * @author wangyue
	 * @title: getMaleGuestReturnData
	 * @date Sep 16, 2014 9:59:34 AM
	 * @param workOrderNo
	 *            工单编码
	 * @return TransferMaleGuestReturn
	 */
	public TransferMaleGuestReturn getMaleGuestReturnData(String workOrderNo);

	/**
	 * 个人工作台--根据登录人id,查询出属于该登录人的所有传输局权限
	 * 
	 * @author wangyue
	 * @title: getLoginRole
	 * @date Sep 19, 2014 8:57:58 AM
	 * @param userId
	 * @return List
	 */
	public List<PersonNameUrlForm> getLoginRole(String userId);

	public List<WorkOrderStatisticsModel> transferOfficeStatistics();

	public String calculateThePercentage(Double a, Double b);

	/**
	 * 根据工单id获取公客工单编码
	 * 
	 * @author wangyue
	 * @title: getMaleGuestNumberByThemeId
	 * @date Sep 30, 2014 10:16:45 AM
	 * @return String
	 */
	public String getMaleGuestNumberByThemeId(String themeId);

	/**
	 * 预检预修派单--新流程（带总经理审核）
	 * 
	 * @author wangyue
	 * @title: precheckAdd
	 * @date Oct 15, 2014 8:56:34 AM
	 * @param precheck
	 *            void
	 */
	public void threePrecheckAdd(PnrTransferOffice precheck);
	/**
	 * olt-bbu机房优化申请流程
	 * 
	 * @author chujingang
	 * @title: oltBbuAdd
	 * @date Oct 15, 2014 8:56:34 AM
	 * @param precheck
	 *            void
	 */
	public void oltBbuAdd(PnrTransferOffice precheck);
	/**
	 * 新预检预修派单
	 * 
	 * @author wangyue
	 * @title: precheckAdd
	 * @date Oct 15, 2014 8:56:34 AM
	 * @param precheck
	 *            void
	 */
	public void newPrecheckAdd(PnrTransferOffice precheck);
	/**
	 * 旧预检预修派单
	 * 
	 * @author wangyue
	 * @title: precheckAdd
	 * @date Oct 15, 2014 8:56:34 AM
	 * @param precheck
	 *            void
	 */
	public void oldPrecheckAdd(PnrTransferOffice precheck);
	/**
	 * 商城接口派单
	 * 
	 * @author wangyue
	 * @title: precheckAdd
	 * @date Oct 15, 2014 8:56:34 AM
	 * @param precheck
	 *            void
	 */
	public void precheckAdd(PnrTransferOffice precheck);

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
	 * 工单管理-"公客-传输局工单"-未归集工单 下方的归集集合
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
	 * 工单管理-"公客-传输局工单"-归集工单 上方的集合
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
	 * 工单管理-"公客-传输局工单"-未归集工单 上方的集合
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
     * 判断该工单号在系统中是否已存在，存在返回该工单详细信息
      * @author wangyue
      * @title: judgeWorkOrderIsExit
      * @date Nov 12, 2014 5:02:21 PM
      * @param workOrderNumber
      * @return List
     */
    public List judgeWorkOrderIsExit(String workOrderNumber);
    /**
     * 公客--催单接口
      * @author wangyue
      * @title: maleGuestReminder
      * @date Nov 24, 2014 4:35:36 PM
      * @param maleGuestFrom void
     */
    public void maleGuestReminder(TransferMaleGuestInform maleGuestFrom);
    /**
     * 根据公客工单号，判断该工单是否处理完
     * @param maleGuestNumber公客工单号
     * @return
     */
    public String judgeOrderIsDo(String maleGuestNumber);
    /**
     * 附件展示
      * @author wangyue
      * @title: showSheetAccessoriesList
      * @date Nov 24, 2014 6:04:56 PM
      * @param mapping
      * @param form
      * @param request
      * @param response void
     */
    public void showSheetAccessoriesList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)throws Exception;
    /**
     * 新预检预修工单--附件显示
      * @author wangyue
      * @title: newShowSheetAccessoriesList
      * @date Mar 5, 2015 9:17:08 AM
      * @param processInstanceId
      * @return
      * @throws Exception List<TawCommonsAccessoriesForm>
     */
    public List<TawCommonsAccessoriesForm> newShowSheetAccessoriesList(String processInstanceId)throws Exception;
    
    /**
     * ol-bbu机房优化流程--附件显示
     * 
     * @param processInstanceId
     * @return
     * @throws Exception
     */
    public List<TawCommonsAccessoriesForm> showPnrOltBbuRoomSheetAccessoriesList(String processInstanceId)throws Exception;
    
   /**
    * 大修改造--附件显示
    * @param processInstanceId
    * @param flag
    * @return
    * @throws Exception
    */
    public List<TawCommonsAccessoriesForm> getAccessoriesList(String processInstanceId,String flag)throws Exception;
    
    /**
     * 机房拆除流程--附件显示
     * @param processInstanceId
     * @param flag
     * @return
     * @throws Exception
     */
     public List<TawCommonsAccessoriesForm> getRoomDemolitionAccessoriesList(String processInstanceId,String flag)throws Exception;
    
    
    
    /**
     * 回单接口调用
     * @param maleGuestNumber
     * @return
     */
    public String doMaleGurstInterface(String maleGuestNumber);
    
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
	 * 会审率
	 * @param processInstanceId
	 * @param cityId
	 * @return
	 */
	public Double getCountersignRatioByProcessInstanceId(String processInstanceId,String cityId);

    /**
     * 大修改造工单接口调用
      * @author wangyue
      * @title: overHaulRemakeAdd
      * @date Jan 30, 2015 4:31:41 PM
      * @param precheck void
     */
    public void overHaulRemakeAdd(PnrTransferOffice precheck);
    
    /**
     * 干线预检预修工单接口调用
      * @author wangyue
      * @title: arteryPrecheckAdd
      * @date Mar 6, 2015 3:20:26 PM
      * @param precheck void
     */
    public void arteryPrecheckAdd(PnrTransferOffice precheck);
    
    /**
     * 大修改造工单--获取登录人身份
      * @author wangyue
      * @title: getLoginPersonStatusToOverhaulRemake
      * @date Feb 2, 2015 11:22:25 AM
      * @param userId
      * @param pnrTransferOffice
      * @return String
     */
    public String getLoginPersonStatusToOverhaulRemake(String userId,
			PnrTransferOffice pnrTransferOffice);
    
	/**
	 * 工单编号：
		  本地网：
		预检预修：B-JN-CQ-JX-20150215-1 
		抢修：B-JN-CQ-QX-20150215-1
		  干线：
		 预检预修：G- JN-CQ-JX-20150215-1
		抢修：G-JN-CQ-QX-20150215-1
		B代表本地网
		      G代表干线
		      JN代表济南
		      CQ代表长清
		      20150215代表时间
		      1：代表第几个工单

	 * @param processKey
	 * @param initator
	 * @param type  --本地网：B 或干线：G
	 * @param shortName
	 * @param attributes 例如属性，简称等
	 * @return
	 */
	public  String getSheetId(String processKey,String initator,String type,String shortName);
	
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
	/**
	 *  Android获得 归集相关信息
	 * @param androidQuery
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<AndroidWorkOrderTask> getAndroidTaskListTogether(AndroidQuery androidQuery,int pageIndex,int pageSize );
	/**
	 * 工单管理-"公客-传输局工单"-手动归档工单 集合
	 * 
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
	
	//归集工单列表
	public List<TaskModel> querySingleImputationList(String userId,MaleGuestSelectAttribute selectAttribute, int firstResult, int endResult,int pageSize);
	
	//未归集工单列表统计数
	public int queryNoSingleImputationCount(String userId,MaleGuestSelectAttribute selectAttribute);
	
	//未归集工单列表
	public List<TaskModel> queryNoSingleImputationList(String userId,MaleGuestSelectAttribute selectAttribute, int firstResult, int endResult,int pageSize);
	
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
	
	//生成一次核验报表
	public Map<String,Object> collectFirstVerifyData(Map<String,String> param);
	
	//更新报表审批状态(公用)
	public void updateApproveFlag(Map<String,String> param);
	
	//获取报表未审批的工单个数(公用)
	public int getNotApprovedSheetCount(MaleGuestSelectAttribute selectAttribute);
	
	//查询审批人签字(公用)
	public String getApprovalSign(MaleGuestSelectAttribute selectAttribute);
	
	//提交审批人签字(公用)
	public int submitApprovalSign(MaleGuestSelectAttribute selectAttribute);
	
	
	//生成二次抽查报表
	public Map<String,Object> collectSecondInspectionData(Map<String,String> param);
	
	//二次抽查报表审批率
	public double calApprovalRate(String userId,MaleGuestSelectAttribute selectAttribute);
	
	//获取处理人（公用）
	public String getDealingPeopleOfRepair(String areaId,String roleId);
	
	//生成周期领用表
	public String createCycleCollarReport(String userId,String total,MaleGuestSelectAttribute selectAttribute);
	
//	//去周期领用表的报表编号
//	public List<String> getReportNumbers(String userId,MaleGuestSelectAttribute selectAttribute);
//	
//	//生成周期领用表，报表编号+开始日期+结束日期+所属日期
//	public String getCycleCollarReportMsg(List<String> list,String userId,MaleGuestSelectAttribute selectAttribute);
	
	//根据开始日期和结束日期，查询这个开始日期和这个结束日期已经生成的周期领用表 条数
	public int getCycleCollarReportCountByStartDateAndEndDate(String userId,MaleGuestSelectAttribute selectAttribute);
	
	//根据开始日期和结束日期，查询这个开始日期和这个结束日期已经生成的周期领用表 集合
	public List<TaskModel> getCycleCollarReportListByStartDateAndEndDate(String userId,MaleGuestSelectAttribute selectAttribute,int firstResult,int endResult,int pageSize);

	//根据开始日期和结束日期，在这个时间范围内已经生成的周期领用表（用开始日期和结束日期查询不到数据的时候）
	public List<CycleCollarReportModel> getCycleCollarReportListByHomeDate(String userId,MaleGuestSelectAttribute selectAttribute);
	
	//周期领用表 拼接提示信息
	public String getCycleCollarReportMsg(List<CycleCollarReportModel> list);
	
	//判断周期领用表是否已提交
	public String isSubmitOfCycleCollarReport(String userId,MaleGuestSelectAttribute selectAttribute);
	
	//获取周期领用表的部分字段的值（公用）
	public String getAttributeValueOfCycleCollarReport(String userId,MaleGuestSelectAttribute selectAttribute,String flag);
	
	//提交周期领用表
	public void submitCreateCycleCollarReport(String userId,String reportNumber,String fuJianVal,MaleGuestSelectAttribute selectAttribute);
	
	//取周期领用表上传的附件信息
	public String getAttachmentsOfCycleCollarReport(String userId,MaleGuestSelectAttribute selectAttribute, String flag);
	
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
	
	//获取主场景中文值和子场景的中文值
	public Map<String,String> getMainScenesAndCopyScenesName(HttpServletRequest request,Map<String,String> param);
	
	//审批位置统计信息（材料金额、电杆、光缆数量、接头盒数量）
	public List<StatisticsMaterialInforModel> statisticsMaterialInfor(String userId,MaleGuestSelectAttribute selectAttribute,String reportFlag);
	
	//导出详情
	public HSSFWorkbook exportDetails(String userId,String reportFlag,MaleGuestSelectAttribute selectAttribute,Map<String,String> param);
	
	//显示公客场景模板明细
	public List<MaleSceneDetailModel>  getMaleSceneDetail(String processInstanceId,String linkFlag,Map<String,String> param);
	
	//获取材料的统计信息
	//public MaleSceneStatisticsModel getMaleSceneStatistics(List<TaskModel> list,String reportFlag,Map<String,String> param);
	
	//获取材料的统计信息
	public MaleSceneStatisticsModel getMaleSceneStatistics(String userId,MaleGuestSelectAttribute selectAttribute,String reportFlag,Map<String,String> param);
	
	//取公客归集工单子工单的最小时间（最小告警发生时间或者最小派单时间）
	public Date getMinDateForSubWorkOrder(String mainProcessInstanceId,List sublist,String flag,Map<String,String> param);
	
	//生成周期领用表的材料数量统计
	public MaterialQuantityModel getMaterialQuantityOfCycleReport(String userId,MaleGuestSelectAttribute selectAttribute,Map<String,String> param);
	
	//导出周期领用表的材料数量汇总
	public HSSFWorkbook exportMaterialQuantity(String userId,MaleGuestSelectAttribute selectAttribute,String reportFlag,Map<String,String> param);

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
	 * 公客系统 回调-处理环节；涉及到归集工单及子工单的回调。
	 * 
	 * 规则：手机与web端施工队处理之后，查看该工单是否有子工单，有子工单则工单也一样回填公客接口。
	 * @param themeId
	 * @param processInstanceId
	 * @param maleGuestState
	 * @param maleGuestReturn
	 */
	public void maleGuestHandleInterfaceCall(TransferMaleGuestReturn maleGuestReturn ,String maleGuestState,String processInstanceId ,String themeId);

	/**
	 * 	 周期领用表报表编号是否存在
	 	 * @author WANGJUN
	 	 * @title: existsCycleReportNumber
	 	 * @date Jun 30, 2016 10:35:48 AM
	 	 * @param reportNumber
	 	 * @return boolean true存在;false不存在
	 */
	public boolean existsCycleReportNumber(String reportNumber);
	
	/**
	 *   通过报表编号查询验证表里是否有数据
	 	 * @author WANGJUN
	 	 * @title: isVerifiedCycleReportNumber
	 	 * @date Jun 30, 2016 11:44:26 AM
	 	 * @param reportNumber
	 	 * @return boolean true已验证;false未验证
	 */
	public boolean isVerifiedCycleReportNumber(String reportNumber);
	
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
	 * 	 保存抽检结果
	 	 * @author WANGJUN
	 	 * @title: saveSamplingJudgments
	 	 * @date Aug 5, 2016 11:35:39 AM
	 	 * @param param
	 	 * @return String
	 */
	public String saveSamplingJudgments(ParameterModel param);
	
	/**
	 *   拼接省公司抽检的查询条件
	 	 * @author WANGJUN
	 	 * @title: joinSamplingListCondition
	 	 * @date Aug 5, 2016 3:33:25 PM
	 	 * @param pageIndexString
	 	 * @param conditionQueryModel
	 	 * @return String
	 */
	public String joinSamplingListCondition(String pageIndexString,ConditionQueryModel conditionQueryModel);
	
	/**
	 *   解析省公司抽检的查询条件
	 	 * @author WANGJUN
	 	 * @title: joinSamplingListCondition
	 	 * @date Aug 5, 2016 3:33:25 PM
	 	 * @param pageIndexString
	 	 * @param conditionQueryModel
	 	 * @return String
	 */
	public String analysisSamplingListCondition(String condition);
	
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

