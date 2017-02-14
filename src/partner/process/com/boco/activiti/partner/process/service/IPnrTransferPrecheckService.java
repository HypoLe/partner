package com.boco.activiti.partner.process.service;

import java.util.List;
import java.util.Map;

import com.boco.activiti.partner.process.model.PnrTransferOffice;
import com.boco.activiti.partner.process.po.ConditionQueryModel;
import com.boco.activiti.partner.process.po.TaskModel;
import com.boco.eoms.deviceManagement.common.service.CommonGenericService;

public interface IPnrTransferPrecheckService extends CommonGenericService<PnrTransferOffice>{
	/**
	 * 获取待恢复工单总数
	  * @author wangyue
	  * @title: getToreplyJobOfEmergencyJobCount
	  * @date Nov 10, 2014 5:07:00 PM
	  * @param userId
	  * @param sendStartTime
	  * @param sendEndTime
	  * @param wsNum
	  * @param wsTitle
	  * @param status
	  * @return int
	 */
	public int getToreplyJobOfEmergencyJobCount(String userId,
			String sendStartTime, String sendEndTime, String wsNum,
			String wsTitle, String status,String city,String country,String lineType,String workType);
	public List<TaskModel> getToreplyJobOfEmergencyJobList(String userId,
			String sendStartTime, String sendEndTime, String wsNum,
			String wsTitle, String status,String city,String country,String lineType,String workType, int firstResult, int endResult,
			int pageSize);
	public int getToreplyJobOfEmergencyJobForCountersignCount(String userId,
			String sendStartTime, String sendEndTime, String wsNum,
			String wsTitle, String status,String city,String country,String lineType,String workType);
	/**
	 * 
	 * @param userId  --当前人账号
	 * @param sendStartTime --派发开始时间
	 * @param sendEndTime	--派发结束时间
	 * @param wsNum			--工单号
	 * @param wsTitle		--工单名称
	 * @param status		--工单类型：预检预修、大修、改造
	 * @param city			--地市
	 * @param country		--区县
	 * @param lineType		--线路属性
	 * @param workType		--工单类型
	 * @param firstResult	
	 * @param endResult
	 * @param pageSize
	 * @return
	 */
	public List<TaskModel> getToreplyJobOfEmergencyJobForCountersignList(String userId,
			String sendStartTime, String sendEndTime, String wsNum,
			String wsTitle, String status,String city,String country,String lineType,String workType, int firstResult, int endResult,
			int pageSize);
	/**
	 * 预检预修中--根据指派的传输分局获取市公司人员
	  * @author wangyue
	  * @title: getSGSByCountryCSJ
	  * @date Nov 13, 2014 3:52:28 PM
	  * @param countryCSJ
	  * @return List<Map>
	 */
	public List<Map> getSGSByCountryCSJ(String countryCSJ,String roleid);
	/**
	 * 预检预修中--根据指派的传输分局获取代维公司人员
	  * @author wangyue
	  * @title: getSGSByCountryCSJ
	  * @date Nov 13, 2014 3:52:28 PM
	  * @param countryCSJ
	  * @return List<Map>
	 */
	public List<Map> getDaiWeiSGSByCountryCSJ(String countryCSJ);
	/**
	 * 预检预修中--根据指派的传输分局获取市传输局人员
	  * @author wangyue
	  * @title: getSGSByCountryCSJ
	  * @date Nov 13, 2014 3:52:28 PM
	  * @param countryCSJ
	  * @return List<Map>
	 */
	public List<Map> getCityCSJByCountryCSJ(String countryCSJ);
	
	/**
	 * 已处理工单明细
	 * 
	 * @param processDefinitionKey
	 * @param userId
	 * @param sendStartTime
	 * @param sendEndTime
	 * @param wsNum
	 * @param wsTitle
	 * @param status
	 * @param firstResult
	 * @param endResult
	 * @param pageSize
	 * @return
	 */
	public List<TaskModel> getHaveProcessList(String processDefinitionKey,
			String userId, String sendStartTime, String sendEndTime,
			String wsNum, String wsTitle, String status, int firstResult,
			int endResult, int pageSize);

	/**
	 * 已处理工单条数
	 * @param processDefinitionKey
	 * @param userId
	 * @param sendStartTime
	 * @param sendEndTime
	 * @param wsNum
	 * @param wsTitle
	 * @param status
	 * @return
	 */ 
	public int getHaveProcessCount(String processDefinitionKey, String userId,
			String sendStartTime, String sendEndTime, String wsNum,
			String wsTitle, String status);
	
	/**
	 * 已归档工单明细
	 * @param processDefinitionKey
	 * @param userId
	 * @param sendStartTime
	 * @param sendEndTime
	 * @param wsNum
	 * @param wsTitle
	 * @param status
	 * @param firstResult
	 * @param endResult
	 * @param pageSize
	 * @return
	 */
	public List<TaskModel> getArchivedList(String processDefinitionKey,
			String userId, String sendStartTime, String sendEndTime,
			String wsNum, String wsTitle, String status, int firstResult,
			int endResult, int pageSize);

	/**
	 * 已归档工单条数
	 * @param processDefinitionKey
	 * @param userId
	 * @param sendStartTime
	 * @param sendEndTime
	 * @param wsNum
	 * @param wsTitle
	 * @param status
	 * @return
	 */ 
	public int getArchivedCount(String processDefinitionKey, String userId,
			String sendStartTime, String sendEndTime, String wsNum,
			String wsTitle, String status);
	/**************************************大修改造工单--方法***************************************************/
	/**
	 * 根据userID获取该用户所在部门
	  * @author wangyue
	  * @title: getDeptByUserId
	  * @date Jan 29, 2015 4:14:32 PM
	  * @param userid
	  * @return String
	 */
	public String getDeptByUserId(String userid);
	
	
	/**
	 * 大修改造工单--待回复工单总数
	  * @author wangyue
	  * @title: getOverhaulRemakCount
	  * @date Jan 30, 2015 9:21:50 AM
	  * @param userid 登录人id
	  * @param conditionQueryModel  条件查询实体类
	  * @return int
	 */
	public int getOverhaulRemakCount(String userid,ConditionQueryModel conditionQueryModel);
	
	/**
	 * 大修改造工单--待回复工单集合
	  * @author wangyue
	  * @title: getOverhaulRemakJobList
	  * @date Jan 30, 2015 10:08:02 AM
	  * @param userId 登录人id
	  * @param conditionQueryModel 条件查询实体类
	  * @param firstResult
	  * @param endResult
	  * @param pageSize
	  * @return List<TaskModel>
	 */
	public List<TaskModel> getOverhaulRemakJobList(String userId,
			ConditionQueryModel conditionQueryModel, int firstResult, int endResult,
			int pageSize);
	
	/**
	 * 大修改造工单-已归档工单总数
	  * @author wangyue
	  * @title: getOverhaulRemakArchivedCount
	  * @date Feb 2, 2015 5:00:51 PM
	  * @param processOverhaulKey 大修工单流程id
	  * @param processRemakeKey 改造工单流程id
	  * @param conditionQueryModel 条件查询实体类
	  * @return int
	 */
	public int getOverhaulRemakArchivedCount(String processOverhaulKey,
			String processRemakeKey, String userid,
			ConditionQueryModel conditionQueryModel);
			/**
	 * 大修改造工单--已归档工单集合
	  * @author wangyue
	  * @title: getOverhaulRemakeArchivedList
	  * @date Feb 4, 2015 11:42:05 AM
	  * @param processOverhaulKey 大修工单流程id
	  * @param processRemakeKey 改造工单流程id
	  * @param userid 登录人id
	  * @param conditionQueryModel 条件查询实体类
	  * @param firstResult
	  * @param endResult
	  * @param pageSize
	  * @return List<TaskModel>
	 */
	public List<TaskModel> getOverhaulRemakeArchivedList(
			String processOverhaulKey, String processRemakeKey, String userid,
			ConditionQueryModel conditionQueryModel, int firstResult,
			int endResult, int pageSize);
	
	
	
	
	
	
}
