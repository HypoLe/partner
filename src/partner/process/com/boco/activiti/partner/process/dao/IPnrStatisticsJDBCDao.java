package com.boco.activiti.partner.process.dao;

import java.util.List;

import com.boco.activiti.partner.process.po.PreflightDetailStatisticPartnerModel;
import com.boco.activiti.partner.process.po.PreflightStatisticPartnerModel;
import com.boco.activiti.partner.process.po.WorkOrderStatisticsDrillModel;
import com.boco.activiti.partner.process.po.WorkOrderStatisticsModel;
import com.boco.activiti.partner.process.po.WorkOrderStatisticsModel2;
import com.boco.eoms.partner.inspect.model.InspectStatisticArea;

/**
 
 */
public interface IPnrStatisticsJDBCDao {
	/**
	 * 首页工单统计钻取得到故障工单列表
	 * @param city
	 * @param beginTime
	 * @param endTime
	 * @param subType
	 * @param level 级别 ：地市city；区县country；处理人：person
	 * @param  person 当level为person时的处理人
	 * @return List<WorkOrderStatisticsDrillModel>
	 */
	public List<WorkOrderStatisticsDrillModel> TroubleStatisticsPartnerIndexDrill(String city,String beginTime, String endTime, String subType,String level,String person);


        /**
         * 首页工单统计钻取得到故障工单列表--在途工单
         * @param city
         * @param beginTime
         * @param endTime
         * @param subType
         * @param level 级别 ：地市city；区县country；处理人：person
         * @param  person 当level为person时的处理人
         * @return List<WorkOrderStatisticsDrillModel>
         */
        public List<WorkOrderStatisticsDrillModel> TroubleStatisticsPartnerIndexDrill3(String city,String beginTime, String endTime, String subType,String level,String person);
    /**
     * 首页工单统计钻取得到任务工单列表
     * @param city
     * @param beginTime
     * @param endTime
     * @param subType
     * @param level 级别 ：地市city；区县country；处理人：person
     * @param  person 当level为person时的处理人
     * @return List<WorkOrderStatisticsDrillModel>
     */
	public List<WorkOrderStatisticsDrillModel> TaskStatisticsPartnerIndexDrill(String city,String beginTime, String endTime, String subType,String level,String person);
    /**
     * 首页工单统计钻取得到任务工单列表---在途统计
     * @param city
     * @param beginTime
     * @param endTime
     * @param subType
     * @param level 级别 ：地市city；区县country；处理人：person
     * @param  person 当level为person时的处理人
     * @return List<WorkOrderStatisticsDrillModel>
     */
    public List<WorkOrderStatisticsDrillModel> TaskStatisticsPartnerIndexDrill3(String city,String beginTime, String endTime, String subType,String level,String person);
	/**
	 * 工单统计钻取得到任务工单列表
	 * @param flag ：1：总工单；2：超时工单； 3：未归档工单； 4：归档工单。
	 * @param city
	 * @param beginTime
	 * @param endTime
	 * @param subType
	 * @return List<WorkOrderStatisticsDrillModel>
	 */
    public List<WorkOrderStatisticsDrillModel> taskTicketStatisticsDrill(String flag,String city,String beginTime, String endTime, String subType);
    /**
     * 工单统计钻取得到故障工单列表
     * @param flag 1：总工单；2：超时工单； 3：未归档工单； 4：归档工单。
     * @param city
     * @param beginTime
     * @param endTime
     * @param subType
     * @return List<WorkOrderStatisticsDrillModel>
     */
    public List<WorkOrderStatisticsDrillModel> troubleTicketStatisticsDrill(String flag,String city,String beginTime, String endTime, String subType);
    /**
     * 工单统计钻取得到传输局工单列表
     * @param flag 1：总工单；2：超时工单； 3：未归档工单； 4：归档工单。
     * @param city
     * @param beginTime
     * @param endTime
     * @param subType
     * @return List<WorkOrderStatisticsDrillModel>
     */
    public List<WorkOrderStatisticsDrillModel> transferOfficeTicketStatisticsDrill(String flag,String city,String beginTime, String endTime, String subType);
    /**
     * 传输局工单--预检预修工单和抢修工单
      * @author wangyue
      * @title: transferOfficeInterfaceStatisticsDrill
      * @date Oct 20, 2014 7:36:45 PM
      * @param flag
      * @param city
      * @param beginTime
      * @param endTime
      * @param subType
      * @return List<WorkOrderStatisticsDrillModel>
     */
    public List<WorkOrderStatisticsDrillModel> transferOfficeInterfaceStatisticsDrill(String type,String flag,String city,String beginTime, String endTime, String subType);
    
    /**
	 * 工单统计钻取得到任务工单列表
	 * @param flag ：1：总工单；2：超时工单； 3：未归档工单； 4：归档工单。
	 * @param city
	 * @param beginTime
	 * @param endTime
	 * @param subType
	 * @return List<WorkOrderStatisticsDrillModel>用于地市级钻取
	 */
    public List<WorkOrderStatisticsDrillModel> taskTicketStatisticsDrillbycity(String flag,String city,String beginTime, String endTime, String subType);
    /**
     * 工单统计钻取得到故障工单列表
     * @param flag 1：总工单；2：超时工单； 3：未归档工单； 4：归档工单。
     * @param city
     * @param beginTime
     * @param endTime
     * @param subType
     * @return List<WorkOrderStatisticsDrillModel>用于地市级钻取
     */
    public List<WorkOrderStatisticsDrillModel> troubleTicketStatisticsDrillbycity(String flag,String city,String beginTime, String endTime, String subType);
    /**
     * 工单统计钻取得到传输局工单列表
     * @param flag 1：总工单；2：超时工单； 3：未归档工单； 4：归档工单。
     * @param city
     * @param beginTime
     * @param endTime
     * @param subType
     * @return List<WorkOrderStatisticsDrillModel>用于地市级钻取
     */
    public List<WorkOrderStatisticsDrillModel> transferOfficeTicketStatisticsDrillbycity(String flag,String city,String beginTime, String endTime, String subType);
    //用于处理人工单页面进行钻取
    public List<WorkOrderStatisticsDrillModel> taskTicketStatisticsDrillbyperson(String person,String flag,String city,String beginTime, String endTime, String subType);
    public List<WorkOrderStatisticsDrillModel> troubleTicketStatisticsDrillbyperson(String person,String flag,String city,String beginTime, String endTime, String subType);
    public List<WorkOrderStatisticsDrillModel> transferOfficeTicketStatisticsDrillbyperson(String person,String flag,String city,String beginTime, String endTime, String subType);
    
    public List<WorkOrderStatisticsModel> taskTicketWorkOrderStatistics(String beginTime, String endTime, String subType);
    public List<WorkOrderStatisticsModel> troubleTicketWorkOrderStatistics(String beginTime, String endTime, String subType);
    public List<WorkOrderStatisticsModel> transferOfficeTicketWorkOrderStatistics(String beginTime, String endTime, String subType);
    public List<WorkOrderStatisticsModel> transferInterfaceWorkOrderStatistics(String transferType,String beginTime, String endTime, String subType);
    
    public List<WorkOrderStatisticsModel> taskTicketWorkOrderStatisticsbycity(String city,String beginTime, String endTime, String subType);
    public List<WorkOrderStatisticsModel> troubleTicketWorkOrderStatisticsbycity(String city,String beginTime, String endTime, String subType);
    public List<WorkOrderStatisticsModel> transferOfficeWorkOrderStatisticsbycity(String city,String beginTime, String endTime, String subType);
    public List<WorkOrderStatisticsModel> transferOfficeInterfaceStatisticsbycity(String type,String city,String beginTime, String endTime, String subType);
    public List<WorkOrderStatisticsDrillModel> transferOfficeInterfaceStatisticsDrillbyperson(String type,String person,String flag,String city,String beginTime, String endTime, String subType);
    
    public List<WorkOrderStatisticsModel> taskTicketWorkOrderStatisticsbycountry(String city,String beginTime, String endTime, String subType);
    public List<WorkOrderStatisticsModel> troubleTicketWorkOrderStatisticsbycountry(String city,String beginTime, String endTime, String subType);
    public List<WorkOrderStatisticsModel> transferOfficeWorkOrderStatisticsbycountry(String city,String beginTime, String endTime, String subType);
    public List<WorkOrderStatisticsModel> transferOfficeInterfaceStatisticsbycountry(String type,String city,String beginTime, String endTime, String subType);
    public List<WorkOrderStatisticsDrillModel> transferOfficeInterfaceStatisticsDrillbycity(String type,String flag,String city,String beginTime, String endTime, String subType);
    
    
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
     * 首页工单统计：钻取县级统计
     * @param country
     * @param beginTime
     * @param endTime
     * @return List<WorkOrderStatisticsModel2>
     */
    public List<WorkOrderStatisticsModel2> workOrderStatistics2Person(String country,String beginTime, String endTime);
    
    /**
     * 首页工单统计：钻取地市时县级统计--在途统计
     * @param city
     * @param beginTime
     * @param endTime
     * @return List<WorkOrderStatisticsModel2>
     */
    public List<WorkOrderStatisticsModel2> workOrderStatistics3(String city,String beginTime, String endTime);
    /**
     * 首页工单统计：钻取县级统计--在途统计
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
	 * @param  month
	 * 地市
	 * @return
	 */
	public List<PreflightStatisticPartnerModel> findPreflightStatisticCityHis(String themeinterface,String taskdefkey,String quarter);
	
	
	/**
	 * 统计历史区县巡检情况
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
	 * 预检预修详情数量
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
