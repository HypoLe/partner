package com.boco.activiti.partner.process.dao;

import java.util.List;
import java.util.Map;

import com.boco.activiti.partner.process.model.NetResInspectDraft;
import com.boco.activiti.partner.process.model.NetResInspectTurnToSendModel;
import com.boco.activiti.partner.process.model.OptionUtil;
import com.boco.activiti.partner.process.model.PnrAndroidPhotoFile;
import com.boco.activiti.partner.process.po.ConditionQueryModel;
import com.boco.activiti.partner.process.po.NetResInspectModel;
import com.boco.activiti.partner.process.po.RoomDemolitionModel;
import com.boco.activiti.partner.process.po.TaskModel;
import com.boco.eoms.commons.accessories.webapp.form.TawCommonsAccessoriesForm;

public interface INetResInspectJDBCDao {
	/**
	 * 预检预修工单--待回复或待办工单--工单总数
	 * 
	 * @author wangyue
	 * @title: getTransferNewPrecheckCount
	 * @date Feb 6, 2015 4:08:58 PM
	 * @param userid
	 *            登录人id
	 * @param flag
	 *            待回复和待办工单标志
	 * @param conditionQueryModel
	 *            条件查询实体类
	 * @return int
	 */
	public int getTransferNewPrecheckCount(String userid, String flag,
			String processKey, ConditionQueryModel conditionQueryModel);
	
	/**
	 * 预检预修--待回复或待办工单--工单集合
	  * @author wangyue
	  * @title: getTransferNewPrecheck
	  * @date Feb 6, 2015 4:41:55 PM
	  * @param userid
	  * @param flag
	  * @param processKey
	  * @param conditionQueryModel
	  * @param firstResult
	  * @param endResult
	  * @param pageSize
	  * @return List<TaskModel>
	 */
	public List<NetResInspectModel> getTransferNewPrecheckList(String userid, String flag,
			String processKey, ConditionQueryModel conditionQueryModel,
			int firstResult, int endResult, int pageSize);
	/**
	 * 预检预修工单--根据区县运维中心主管获取其它部门人员
	  * @author wangyue
	  * @title: getPrecheckShipModelBycountryCharge
	  * @date Feb 11, 2015 1:49:49 PM
	  * @param countryCharge  区县维护中心主管
	  * @return PrecheckShipModel 预检预修人员实体类
	 */
	public List<Map> getPrecheckShipModelBycountryCharge(String countryCharge);
	
	/**
	 * 已处理工单条数
	 * @param processDefinitionKey
	 * @param userId
	 * @param conditionQueryModel
	 * @return
	 */
	public int getHaveProcessCount(String processDefinitionKey, String userId,
			ConditionQueryModel conditionQueryModel);
	
	/**
	 * 已处理工单明细
	 * @param processDefinitionKey
	 * @param userId
	 * @param conditionQueryModel
	 * @param firstResult
	 * @param endResult
	 * @param pageSize
	 * @return
	 */
	public List<NetResInspectModel> getHaveProcessList(String processDefinitionKey,
			String userId,ConditionQueryModel conditionQueryModel, int firstResult,
			int endResult, int pageSize);

	
	


	/**
	 * 由我创建的工单条数
	 * 
	 */ 
	public int getByCreatingWorkOrderCount(String processDefinitionKey, String userId,ConditionQueryModel conditionQueryModel);
	
	/**
	 * 由我创建的工单明细
	 * 
	 */
	public List<NetResInspectModel> getByCreatingWorkOrderList(String processDefinitionKey,
			String userId, ConditionQueryModel conditionQueryModel, int firstResult,
			int endResult, int pageSize);
	/**
	 * 本地网预检预修--新建工单图片集合
	  * @author wangyue
	  * @title: getPrecheckPhotoes
	  * @date Mar 13, 2015 4:07:07 PM
	  * @return List<PnrAndroidWorkOderPhotoFile>
	 */
	public List<PnrAndroidPhotoFile> getPrecheckPhotoes(String userId,String photoDescribe,String createTime,String photoCreate,String faultLocation);
	/**
	 * 根据id查询出图片存储的路径
	  * @author wangyue
	  * @title: getPhotoPlth
	  * @date Mar 17, 2015 10:45:22 AM
	  * @param id
	  * @return String
	 */
	public String getPhotoPlth(String id);
	/**
	 * 根据工单号查看本工单新建时选择的图片
	  * @author wangyue
	  * @title: showPhotoByProcessInstanceId
	  * @date Mar 19, 2015 4:18:07 PM
	  * @param processInstanceId
	  * @return List<PnrAndroidWorkOderPhotoFile>
	 */
	public List<PnrAndroidPhotoFile> showPhotoByProcessInstanceId(String processInstanceId);
	/**
	 * 根据图片id查询该图片的所有信息
	  * @author wangyue
	  * @title: getOnePnrAndroidPhotoFileById
	  * @date Mar 20, 2015 10:13:32 AM
	  * @param id
	  * @return PnrAndroidPhotoFile
	 */
	public PnrAndroidPhotoFile getOnePnrAndroidPhotoFileById(String id);
	
	/**
	 * 工单抓回 条数
	 * @param processDefinitionKey 流程ID
	 * @param userTaskFlag	能抓回的环节ID集合
	 * @param userId	登录用户ID
	 * @param conditionQueryModel 查询条件
	 * @return
	 */
	public int getBackSheetCount(String processDefinitionKey,String userId,ConditionQueryModel conditionQueryModel);
	
	/**
	 * 工单抓回 明细
	 * @param processDefinitionKey 流程ID
	 * @param userTaskFlag	能抓回的环节ID集合
	 * @param userId	登录用户ID
	 * @param conditionQueryModel	查询条件
	 * @param firstResult
	 * @param endResult
	 * @param pageSize
	 * @return
	 */
	public List<TaskModel> getBackSheetList(String processDefinitionKey,String userId,ConditionQueryModel conditionQueryModel, int firstResult,
			int endResult, int pageSize);
	/**
	 * 删除该工单下关联的所有图片
	  * @author wangyue
	  * @title: judgeIsExitBypgohoIdAndProcessInstanceId
	  * @date Mar 23, 2015 2:13:11 PM
	  * @param processInstanceId
	  * @param photoId
	  * @return void
	 */
	public void judgeIsExitBypgohoIdAndProcessInstanceId(String processInstanceId);
	
	/**
	 * 已归档工单条数
	 * @param processDefinitionKey 流程ID
	 * @param userId 用户ID
	 * @param sendStartTime 派单开始时间
	 * @param sendEndTime	派单结束时间
	 * @param wsNum	工单号	
	 * @param wsTitle	工单主题
	 * @param status	工单状态
	 * @return
	 */ 
	public int getArchivedCount(String processDefinitionKey, String userId,
			String sendStartTime, String sendEndTime, String wsNum,
			String wsTitle, String status,String themeInterface);
	
	/**
	 * 已归档工单明细
	 * @param processDefinitionKey	流程ID
	 * @param userId	用户ID
	 * @param sendStartTime	派单开始时间
	 * @param sendEndTime	派单结束时间
	 * @param wsNum	工单号
	 * @param wsTitle	工单主题
	 * @param status 工单状态
	 * @param firstResult
	 * @param endResult
	 * @param pageSize
	 * @return
	 */
	public List<TaskModel> getArchivedList(String processDefinitionKey,
			String userId, String sendStartTime, String sendEndTime,
			String wsNum, String wsTitle, String status,String themeInterface, int firstResult,
			int endResult, int pageSize);

	/**
	 * 修改图片状态
	  * @author wangyue
	  * @title: setStateByphotoId
	  * @date Apr 1, 2015 5:11:44 PM
	  * @param photoId
	  * @param state void
	 */
	public void setStateByphotoId(String photoId,String state);
	
	/**
	 * 根据工单流程id查询出于该工单关联的图片
	  * @author wangyue
	  * @title: getPhotoByProcessInstanceId
	  * @date Apr 2, 2015 10:37:47 AM
	  * @param processInstanceId
	  * @return List<PnrAndroidPhotoFile>
	 */
	public String[] getPhotoByProcessInstanceId(String processInstanceId);

	/**
	 * 根据机房ID删除机房
	 * @param id
	 */
	public String deleteRoomResourceById(String id); 
	
	/**
	 * 查询工单
	 */
	public List<NetResInspectModel> getQueryWorkOrderList(String areaid, String flag,
			String processKey, ConditionQueryModel conditionQueryModel,
			int firstResult, int endResult, int pageSize);
	
	/**
	 * 查询工单总数
	 */
	public int getQueryWorkOrderCount(String areaid, String flag,
			String processKey, ConditionQueryModel conditionQueryModel);
	
	/**
	 * 	 根据区县和专业获取现场核实人
	 	 * @author WANGJUN
	 	 * @title: getChecker
	 	 * @date Jun 21, 2016 9:01:39 AM
	 	 * @param county
	 	 * @param specialty
	 	 * @return String
	 */
	public String getChecker(String county,String specialty);
	
	/**
	 * 	 查询自动派发的子工单数量
	 	 * @author WANGJUN
	 	 * @title: getSubCount
	 	 * @date Jun 28, 2016 2:23:33 PM
	 	 * @param userId
	 	 * @param processType
	 	 * @param conditionQueryModel
	 	 * @return int
	 */
	public int getSubCount(String userId,String processType,ConditionQueryModel conditionQueryModel);
	
	/**
	 * 	 查询自动派发的子工单集合
	 	 * @author WANGJUN
	 	 * @title: getSubCount
	 	 * @date Jun 28, 2016 2:23:33 PM
	 	 * @param userId
	 	 * @param processType
	 	 * @param conditionQueryModel
	 	 * @return int
	 */
	public List<TaskModel> getSubList(String userid,String processKey,ConditionQueryModel conditionQueryModel, int firstResult,int endResult, int pageSize);
	/**
	 * 	 查询自动派发的子工单信息
	 	 * @author ZHOUKEQING
	 	 * @title: getNetResInspectDraft
	 	 * @date Jun 29, 2016 2:23:33 PM
	 	 * @param id
	 	 * @return NetResInspectDraft
	 */
	public NetResInspectDraft getNetResInspectDraft(String processInstanceId);
	/**
	 * 	 改变众筹工单派发到本地网及抢修工单草稿状态
	 	 * @author ZHOUKEQING
	 	 * @title: getNetResInspectDraft
	 	 * @date Jun 29, 2016 2:23:33 PM
	 	 * @param id
	 	 * @return NetResInspectDraft
	 */
	public void updateNetResInspectDraft(String netResInspectId);
	
	/**
	 * 	 获取地市的select
	 	 * @author zhoukeqing
	 	 * @title: getCountrySelect
	 	 * @date Jul 11, 2016 2:42:35 PM
	 	 * @param param
	 	 * @return String
	 */
	public List<OptionUtil> getCountrySelect(String city);
	/**
	 * 	 保存转派信息
	 	 * @author zhoukeqing
	 	 * @title: saveTurnToSendInfo
	 	 * @date Jul 11, 2016 2:42:35 PM
	 	 * @param param
	 	 * @return String
	 */
	public void saveTurnToSendInfo(NetResInspectTurnToSendModel netResInspectTurnToSendModel);
	/**
	 * 	 手机工单代办列表查询
	 	 * @author zhoukeqing
	 	 * @title: searchListsendundo
	 	 * @date Jul 13, 2016 2:42:35 PM
	 	 * @param param
	 	 * @return String
	 */
	public List<NetResInspectModel> searchListsendundo(String userid,int pageSize,int pageIndex) ;
	 /**
     * 机房拆除流程--附件显示
     * @param processInstanceId
     * @param flag
     * @return
     * @throws Exception
     */
     public List<TawCommonsAccessoriesForm> getRoomDemolitionAccessoriesList(String processInstanceId,String flag);
     
    /**
     *  根据区县获取子流程的派单人
     	 * @author WANGJUN
     	 * @title: getSubProcessInitiator
     	 * @date Sep 8, 2016 4:45:36 PM
     	 * @param county
     	 * @param processType
     	 * @return String
     */
 	public String getSubProcessInitiator(String county,String processType);
}
