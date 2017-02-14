package com.boco.activiti.partner.process.service;

import java.util.List;
import java.util.Map;

import com.boco.activiti.partner.process.po.PreflightDetailStatisticPartnerModel;
import com.boco.activiti.partner.process.po.PreflightStatisticPartnerModel;
import com.boco.activiti.partner.process.po.WorkOrderStatisticsDrillModel;
import com.boco.activiti.partner.process.po.WorkOrderStatisticsModel;
import com.boco.activiti.partner.process.po.WorkOrderStatisticsModel2;
import com.boco.eoms.partner.inspect.model.InspectStatisticArea;

/**
 
 */
public interface IPnrStatisticsService {
	/**
	 * 首页工单统计钻取
	 * @param flag 1：故障工单，2：任务工单
	 * @param city
	 * @param beginTime
	 * @param endTime
	 * @param subType
	 * @return Map<String, Object>
	 */
	public Map<String, Object> statisticsPartnerIndexDrill(String flag,String city,String beginTime, String endTime, String subType,String level,String person,int firstIndex,int lastIndex);
    /**
     * 首页工单统计钻取--在途统计
     * @param flag 1：故障工单，2：任务工单
     * @param city
     * @param beginTime
     * @param endTime
     * @param subType
     * @return  Map<String, Object>
     */
    public Map<String, Object> statisticsPartnerIndexDrill3(String flag,String city,String beginTime, String endTime, String subType,String level,String person,int firstIndex,int lastIndex);
	/**
	 * 工单统计钻取
	 * @param  type
	 * @param flag ：1：总工单；2：超时工单； 3：未归档工单； 4：归档工单。
	 * @param city
	 * @param beginTime
	 * @param endTime
	 * @param subType
	 * @return List<WorkOrderStatisticsDrillModel>
	 */
	public Map<String, Object> workOrderStatisticsDrill(String type,String flag,String city,String beginTime, String endTime, String subType,int firstIndex,int lastIndex);
	//传输局--预检预修工单和抢修工单
	public Map<String, Object>transferOfficeStatisticsDrill(String type,String flag,String city,String beginTime, String endTime, String subType,int firstIndex,int lastIndex);
	
	//区县级
	public Map<String, Object> workOrderStatisticsDrillbycity(String type,String flag,String city,String beginTime, String endTime, String subType,int firstIndex,int lastIndex);
	//处理人级
	public Map<String, Object> workOrderStatisticsDrillbyperson(String person,String type,String flag,String city,String beginTime, String endTime, String subType,int firstIndex,int lastIndex);
	public List<WorkOrderStatisticsModel> workOrderStatistics(String type,String beginTime, String endTime, String subType);
	public List<WorkOrderStatisticsModel> workOrderStatisticsbyCity(String city,String cityname,String type,String beginTime, String endTime, String subType);
	public Map<String, Object> workOrderStatisticsbyCountry(String city,String type,String beginTime, String endTime, String subType,int firstIndex,int lastIndex);
	//传输局--预检预修和抢修工单统计
	public List<WorkOrderStatisticsModel> transferOfficeStatistics(String type,String beginTime, String endTime, String subType);
	public List<WorkOrderStatisticsModel> transferOfficeStatisticsbyCity(String city,String cityname,String type,String beginTime, String endTime, String subType);
	public Map<String, Object> transferOfficeStatisticsbyCountry(String city,String type,String beginTime, String endTime, String subType,int firstIndex,int lastIndex);
	public Map<String, Object> transferOfficeStatisticsDrillbyperson(String person,String type,String flag,String city,String beginTime, String endTime, String subType,int firstIndex,int lastIndex);
	public Map<String, Object> transferOfficeStatisticsDrillbycity(String type,String flag,String city,String beginTime, String endTime, String subType,int firstIndex,int lastIndex);
	
	public List<WorkOrderStatisticsModel2> workOrderStatistics2(String beginTime, String endTime);
    public List<WorkOrderStatisticsModel2> workOrderStatistics3(String beginTime, String endTime);
    /**
     * 首页工单统计：钻取地市时县级统计
     * @param city
     * @param beginTime
     * @param endTime
     * @return List<WorkOrderStatisticsModel2>
     */
    public List<WorkOrderStatisticsModel2> workOrderStatistics2(String city,String beginTime, String endTime);
    /**
     * 首页工单统计：钻取地市时县级统计--在途统计
     * @param city
     * @param beginTime
     * @param endTime
     * @return List<WorkOrderStatisticsModel2>
     */
    public List<WorkOrderStatisticsModel2> workOrderStatistics3(String city,String beginTime, String endTime);
    /**
     * 首页工单统计：钻取县级统计处理人
     * @param country
     * @param beginTime
     * @param endTime
     * @return List<WorkOrderStatisticsModel2>
     */
    public List<WorkOrderStatisticsModel2> workOrderStatistics2Person(String country,String beginTime, String endTime);
    /**
     * 首页工单统计：钻取县级统计处理人--在途统计
     * @param country
     * @param beginTime
     * @param endTime
     * @return List<WorkOrderStatisticsModel2>
     */
    public List<WorkOrderStatisticsModel2> workOrderStatistics2Person3(String country,String beginTime, String endTime);
    
    /**
	 * 统计预检预修项目详情表
	 * @param  themeinterface
	 * @param  taskdefkey
	 * @param  quarter
	 * 地市
	 * @return
	 */
	public List<PreflightStatisticPartnerModel> findPreflightStatisticCityHis(String themeinterface,String taskdefkey,String quarter);
	
	 /**
	 * 统计预检预修项目详情表
	 * @param year
	 * @param month
	 * @param excelType 报表类型
	 * 区县
	 * @return
	 */
	public List<PreflightStatisticPartnerModel> findPreflightStatisticCountryHis(String themeinterface,String taskdefkey,String quarter,String city);
	
	 /**
	 * 统计预检预修项目详情表
	 * @param year
	 * @param month
	 * @param excelType 报表类型
	 * @return
	 */
	public List<PreflightDetailStatisticPartnerModel> findPreflightDatilStatisticCityHis(String year,String month,String excelType, int firstResult,
			int endResult, int pageSize);
	
	/**
	 * 统计预检预修项目详情表数量
	 * @param year
	 * @param month
	 * @param excelType 报表类型
	 * @return
	 */
	public int findPreflightDatilStatisticCityHisCount(String year,String month,String excelType);
	
	/**
	 * 机房优化周报
	 * @param 
	 * @param 
	 * @param 
	 * @return
	 */
	public List<PreflightDetailStatisticPartnerModel>  findWeeklyStatisticHis(String sendStartTime,String sendEndTime,String region,String country,String themeinterface,String taskdefkey ,int firstResult,
			int endResult, int pageSize);
	
    
}
