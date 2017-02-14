package com.boco.eoms.partner.baseinfo.mgr;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IPnrStatMgr {
	
	
	//得到基本表，包括（大合作伙伴、小合作伙伴、地市、县区）
	public List getBasePartnerAndRegion(final String areaStr,final String partnerStr);
	
	public List getLineReportStat(final String endTime);
	
	public List getReportLineStatByLineLevel(final String areaStr,final String gradeStr,final String timeStr);
	//车辆统计
	public List getReportCarStat(final String timeStr,final String whereStr);
	//仪器仪表统计
	public List getReportApparatusStat(final String whereStr,final String timeStr);
	public List getReportApparatusPartnerStat(final String partnerStr,final String whereStr ,final String regionStr);
	//合作伙伴使用情况统计
	public List getUseCaseStat(final String parentareaid,final String whereStr,final String regionStr);

	
	//列出所有合作伙伴
	public List listLinePartner();
	//列出所有地市
	public List listLineRegion();
	//列出所有县区
	public List listLineCity();
	
	public List getReportProvideStat(final String whereStr);
	
	/**
	 * 合作伙伴市场份额
	 */
	public List getReportMarketStat(final String regionStr) ;
//	基站统计
	public List getReportSiteStat(final String region,final String city,final String provider,final Date timeEnd,final String province);
	
	public List getReportSiteStatBylevel(final String region,final String level,final Date timeEnd,final String province);
	
//	人员统计
	public List getReportPersonStat(final String region,final String city,final String provider,final Date timeEnd,final String province);
//	人员统计按合作伙伴
	public List getReportPersonStatByPor(final String region,final String professional,final String provider,final Date timeEnd,final String province,final String regionStr);
  
//	根据加入时间和地市得到所有合作伙伴人员集合
	public List getPartnerUsersByTimeAndCity(final String time,final String cityStr);
//	根据加入时间和地市得到所有合作伙伴集合
	public List getPartnersByTimeAndCity(final String time,final String cityStr);

//	人力资源视图
	public List getReportPersonViewStat(final String regionStr) ;
	public List getReportKPImStat(final String province,final String areaStr,final String providerStr,final String gridStr,final String year,final String month,final String table) ;

//	根据加入时间得到所有合作伙伴集合
	public List getPartnersByTime(final String time);
	/**
	 * 得到各地域合作伙伴（大）应配的油机配置
	 * @param cityStr 地域字符串
	 */
	public List getOilEngineByPartner(final String cityStr);
	/**
	 * 得到各地域合作伙伴（大）实配的油机
	 * @param endTime 时间
	 * @param cityStr 地域字符串
	 */
	public List getOilByPartner(final String endTime,final String cityStr);
	/**
	 * 得到各地域合作伙伴（大）实配的油机列表
	 * @param endTime 时间
	 * @param areaId 地域
	 * @param partnerId 合作伙伴
	 * @param power 功率
	 */
	public List getOilList(final String endTime,final String areaId,final String partnerId,final String power);
	/**
	 * 根据合作伙伴、油机性质和状态得到油机列表
	 * @param endTime 时间
	 * @param partnerId 合作伙伴
	 * @param kind 油机性质
	 * @param state 状态
	 */
	public List getOilNumListByType(final String endTime,final String partnerId,final String kind,final String state,final String regionStr);
	/**
	 * 网格满意度统计
	 * 统计条件：按地市、网格、合作伙伴统计。按年,月统计。
	 * @param region 地市
	 * @param provider 合作伙伴
	 * @param gridName 网格
	 * @param year 年
	 * @param month 月
	 */
//	public List getGridSatisfyStat(final String region,final String provider,final String gridName,final String year,final String month, final List LineListRegion) ;
	/**
	 * 合作伙伴市场份额数据点饼图
	 */
	public List getPointPie(String area) ;
	/**
	 * 合作伙伴市场份额基站饼图
	 */
	public List getSitePie(String area) ;

	
	
	/**
	 * 代维响应速度统计
	 * 统计条件：按地市、网格、合作伙伴统计。按年,月统计。
	 * @param region 地市
	 * @param provider 合作伙伴
	 * @param gridName 网格
	 * @param year 年
	 * @param month 月
	 */
//	public List getServiceSpeedStat(final String region,final String provider,final String gridName,final String year,final String month,final List LineListPartner) ;

	/**
	 * 得到各地域合作伙伴（大）应配的人员配置
	 * @param cityStr 地域字符串
	 */
	public List getPersonConfig(final String cityStr);
	/**
	 * 得到各地域合作伙伴（大）实配的人员
	 * @param endTime 时间
	 * @param cityStr 地域字符串
	 */
	public List getPersonByPartner(final String endTime,final String cityStr);
	/**
	 * 得到各地域各合作伙伴数量用于显示Chart图表
	 * @param time 时间
	 * @param city 地域
	 */
	public String getPartnerNumforChart(final String time,final String city,final String urlString);
	
	
	public List getPartnerNumUsers(final String city);
	
	public List getPartnerNumOil(final String city);
	
	public List getPartnerNumCar(final String city);
	
	public List getPartnerNumApparatus(final String city);
}
