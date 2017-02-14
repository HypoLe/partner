package com.boco.activiti.partner.process.dao;

import java.util.List;
import java.util.Map;

import com.boco.activiti.partner.process.po.TaskModel;

public interface IPnrTransferOldPrecheckJDBCDao {
	
	public int getToreplyJobOfEmergencyJobCount(String userId,
			String sendStartTime, String sendEndTime, String wsNum,
			String wsTitle, String status);
	public List<TaskModel> getToreplyJobOfEmergencyJobList(String userId,
			String sendStartTime, String sendEndTime, String wsNum,
			String wsTitle, String status, int firstResult, int endResult,
			int pageSize);
	/**
	 * 预检预修中--根据指派的传输分局获取市公司人员
	  * @author wangyue
	  * @title: getSGSByCountryCSJ
	  * @date Nov 13, 2014 3:54:40 PM
	  * @param countryCSJ
	  * @return List<Map>
	 */
	public List<Map> getSGSByCountryCSJ(String countryCSJ,String roleid);
	/**
	 * 预检预修中--根据指派的传输分局获取代维公司人员
	  * @author wangyue
	  * @title: getSGSByCountryCSJ
	  * @date Nov 13, 2014 3:54:40 PM
	  * @param countryCSJ
	  * @return List<Map>
	 */
	public List<Map> getDaiWeiSGSByCountryCSJ(String countryCSJ);
	/**
	 * 预检预修中--根据指派的传输分局获取市传输局人员
	  * @author wangyue
	  * @title: getSGSByCountryCSJ
	  * @date Nov 13, 2014 3:54:40 PM
	  * @param countryCSJ
	  * @return List<Map>
	 */
	public List<Map> getCityCSJByCountryCSJ(String countryCSJ);
}
