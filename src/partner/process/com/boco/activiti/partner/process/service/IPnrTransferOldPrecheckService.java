package com.boco.activiti.partner.process.service;

import java.util.List;
import java.util.Map;

import com.boco.activiti.partner.process.model.PnrTransferOffice;
import com.boco.activiti.partner.process.po.TaskModel;
import com.boco.eoms.deviceManagement.common.service.CommonGenericService;

public interface IPnrTransferOldPrecheckService extends CommonGenericService<PnrTransferOffice>{
	/**
	 * 获取待回复工单总数
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
			String wsTitle, String status);
	/**
	 * 获取待回复工单集合
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
	public List<TaskModel> getToreplyJobOfEmergencyJobList(String userId,
			String sendStartTime, String sendEndTime, String wsNum,
			String wsTitle, String status, int firstResult, int endResult,
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
}
