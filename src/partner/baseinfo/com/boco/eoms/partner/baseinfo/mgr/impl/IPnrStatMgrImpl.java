package com.boco.eoms.partner.baseinfo.mgr.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.boco.eoms.partner.baseinfo.dao.IPnrStatDao;
import com.boco.eoms.partner.baseinfo.mgr.IPnrStatMgr;
import com.boco.eoms.partner.baseinfo.model.AreaDeptTree;
import com.boco.eoms.partner.baseinfo.model.FusionChart;
import com.boco.eoms.partner.baseinfo.model.FusionChartData;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;
import com.boco.eoms.partner.baseinfo.util.FusionChartMethod;

public class IPnrStatMgrImpl implements IPnrStatMgr {

	private IPnrStatDao  ipnrStatDao;
	
	public List getBasePartnerAndRegion(final String areaStr,final String partnerStr){
		return ipnrStatDao.getBasePartnerAndRegion(areaStr,partnerStr);
	}
	
	public List getLineReportStat(final String endTime) {
		return ipnrStatDao.getLineReportStat(endTime);
	}
	
	public List getReportLineStatByLineLevel(final String areaStr,final String gradeStr,final String timeStr) {
		return ipnrStatDao.getReportLineStatByLineLevel(areaStr,gradeStr,timeStr);
	}	
	//车辆统计
	public List getReportCarStat(final String timeStr,final String whereStr) {
		return ipnrStatDao.getReportCarStat(timeStr,whereStr);
	}
	//仪器仪表统计
	public List getReportApparatusStat(final String whereStr,final String timeStr) {
		return ipnrStatDao.getReportApparatusStat(whereStr,timeStr);
	}
	public List getReportApparatusPartnerStat(final String partnerStr,final String whereStr,final String regionStr) {
		return ipnrStatDao.getReportApparatusPartnerStat(partnerStr,whereStr,regionStr);
	}
	//合作伙伴使用情况统计
	public List getUseCaseStat(final String parentareaid,final String whereStr,final String regionStr) {
		return ipnrStatDao.getUseCaseStat(parentareaid,whereStr,regionStr);
	}
	
	
	//列出所有合作伙伴
	public List listLinePartner() {
		return ipnrStatDao.listLinePartner();
	}
	//列出所有地市
	public List listLineRegion() {
		return ipnrStatDao.listLineRegion();
	}
	//列出所有县区
	public List listLineCity() {
		return ipnrStatDao.listLineCity();
	}
	
	

	public IPnrStatDao getIpnrStatDao() {
		return ipnrStatDao;
	}

	public void setIpnrStatDao(IPnrStatDao ipnrStatDao) {
		this.ipnrStatDao = ipnrStatDao;
	}

	/**
	 * 合作伙伴信息 统计报表 
	 * 统计条件：按地市统计，按月统计合作伙伴数量，按合作伙伴、专业统计维护人员数量
	 */
	public List getReportProvideStat(final String whereStr) {
		return ipnrStatDao.getReportProvideStat(whereStr);
	}
	
	/**
	 * 合作伙伴市场份额
	 */
	public List getReportMarketStat(final String regionStr) {
		return ipnrStatDao.getReportMarketStat(regionStr);
	}
//	基站统计	
	public List getReportSiteStat(final String region,final String city,final String provider,final Date timeEnd,final String province){
		return ipnrStatDao.getReportSiteStat(region, city, provider, timeEnd,province);
	}
	
	public List getReportSiteStatBylevel(final String region,final String level,final Date timeEnd,final String province) {
		return ipnrStatDao.getReportSiteStatBylevel(region, level, timeEnd,province);
	}
	
//	人员统计
	public List getReportPersonStat(final String region,final String city,final String provider,final Date timeEnd,final String province){
		return ipnrStatDao.getReportPersonStat(region, city, provider, timeEnd, province);
	}

	public List getReportPersonStatByPor(final String region,final String professional,final String provider,final Date timeEnd,final String province,final String regionStr){
		return ipnrStatDao.getReportPersonStatByPor(region, professional, provider, timeEnd, province,regionStr);
	}
	public List getPartnerUsersByTimeAndCity(final String time,final String cityStr){
		return ipnrStatDao.getPartnerUsersByTimeAndCity(time,cityStr);
	}
	public List getPartnersByTimeAndCity(final String time,final String cityStr){
		return ipnrStatDao.getPartnersByTimeAndCity(time,cityStr);
	}

	
//	人力资源视图
	public List getReportPersonViewStat(final String regionStr) {
		return ipnrStatDao.getReportPersonViewStat(regionStr);
	}
	public List getReportKPImStat(final String province,final String areaStr,final String providerStr,final String gridStr,final String year,final String month,final String table) {
		return ipnrStatDao.getReportKPImStat(province, areaStr, providerStr, gridStr, year, month,table);
	}

	public List getOilEngineByPartner(final String cityStr){
		return ipnrStatDao.getOilEngineByPartner(cityStr);
	}
	public List getOilByPartner(final String endTime,final String cityStr){
		return ipnrStatDao.getOilByPartner(endTime,cityStr);
	}
	public List getOilList(final String endTime,final String areaId,final String partnerId,final String power){
		return ipnrStatDao.getOilList(endTime,areaId,partnerId,power);
	}
	public List getOilNumListByType(final String endTime,final String partnerId,final String kind,final String state,final String regionStr){
		return ipnrStatDao.getOilNumListByType(endTime,partnerId,kind,state,regionStr);
	}
	public List getPartnersByTime(final String time){
		List partnerList = ipnrStatDao.getPartnersByTime(time);
		AreaDeptTree tree = null;
		PartnerDept partner = null;
		String partnerTemp = "";
		List lastPartnerList = new ArrayList();
		Object[] object = new Object[2];
        for(int i=0;i<partnerList.size();i++){
			object =  (Object[])partnerList.get(i);
			tree = (AreaDeptTree)object[0];
			partner = (PartnerDept)object[1];
			if(!partnerTemp.equals(tree.getInterfaceHeadId())){
				lastPartnerList.add(object);
				partnerTemp = tree.getInterfaceHeadId();
			}
        }
		return lastPartnerList;
	}
	/**
	 * 网格满意度统计
	 * 统计条件：按地市、网格、合作伙伴统计。按年,月统计。
	 * @param region 地市
	 * @param provider 合作伙伴
	 * @param gridName 网格
	 * @param year 年
	 * @param month 月
	 */
	/*public List getGridSatisfyStat(final String region,final String provider,final String gridName,final String year,final String month, final List LineListRegion){
		List listStat = new ArrayList();
		GridSatisfyStatForm gridSatisfyStatForm = new GridSatisfyStatForm(); 
		List list = ipnrStatDao.getGridSatisfyStat(region,provider,gridName,year,month,LineListRegion);
		Object[] object = new Object[4];
		PartnerDept dept = null;
		GridSatisfaction gsf = null;
		String tempStr = "";
		String rowStr = "";
		for(int i=0;i<list.size();i++){
			object =  (Object[])list.get(i);
			dept = (PartnerDept)object[0];
			gsf = (GridSatisfaction)object[3];
			rowStr = year+"_"+month+"_"+gsf.getRegion()+"_" + gsf.getCity() + "_"+dept.getName()+"_"+gsf.getGrid();
			if(!rowStr.equals(tempStr)&&i!=0){
				listStat.add(gridSatisfyStatForm);
				gridSatisfyStatForm = new GridSatisfyStatForm();
			}
			gridSatisfyStatForm.setYear(gsf.getYear());
			gridSatisfyStatForm.setMonth(gsf.getMonth());
			gridSatisfyStatForm.setRegion(gsf.getRegion());
			gridSatisfyStatForm.setCity(gsf.getCity());
			gridSatisfyStatForm.setGrid(gsf.getGrid());
			gridSatisfyStatForm.setProvider(dept.getName());
			gridSatisfyStatForm.setSynRating(gridSatisfyStatForm.getSynRating()+gsf.getSynRating());
			gridSatisfyStatForm.setTieMaintenance(gridSatisfyStatForm.getTieMaintenance()+gsf.getTieMaintenance());
			gridSatisfyStatForm.setFaultDispose(gridSatisfyStatForm.getFaultDispose()+gsf.getFaultDispose());
			gridSatisfyStatForm.setPhonicsQuality(gridSatisfyStatForm.getPhonicsQuality()+gsf.getPhonicsQuality());
			gridSatisfyStatForm.setBusinessLobby(gridSatisfyStatForm.getBusinessLobby()+gsf.getBusinessLobby());
			gridSatisfyStatForm.setCustomerComplaints(gridSatisfyStatForm.getCustomerComplaints()+gsf.getCustomerComplaints());
			gridSatisfyStatForm.setValueCustomer(gridSatisfyStatForm.getValueCustomer()+gsf.getValueCustomer());
			gridSatisfyStatForm.setCorporateCustomer(gridSatisfyStatForm.getCorporateCustomer()+gsf.getCorporateCustomer());
			gridSatisfyStatForm.setCompanyAct(gridSatisfyStatForm.getCompanyAct()+gsf.getCompanyAct());
			gridSatisfyStatForm.setPersonnelStatus(gridSatisfyStatForm.getPersonnelStatus()+gsf.getPersonnelStatus());
			gridSatisfyStatForm.setInstrumentStatus(gridSatisfyStatForm.getInstrumentStatus()+gsf.getInstrumentStatus());
			gridSatisfyStatForm.setManagementAbility(gridSatisfyStatForm.getManagementAbility()+gsf.getManagementAbility());
			tempStr = rowStr;
			if(i==list.size()-1){
				listStat.add(gridSatisfyStatForm);
			}
		}
		return listStat;
	}*/
	/**
	 * 合作伙伴市场份额数据点饼图
	 */
	public List getPointPie(String area){
		List dataList = new ArrayList();
		List list = ipnrStatDao.getPointPie(area);
//		ID2NameService mgr = (ID2NameService) ApplicationContextHolder
//			.getInstance().getBean("id2nameService");
		Object[] data = null;
		for(int i=0;i<list.size();i++){
			data = (Object[])list.get(i);
			String[] dataStr = new String[2];
//			dataStr[0] = mgr.id2Name(String.valueOf(data[0]),"partnerDeptDao");
			dataStr[0]=String.valueOf(data[0]);
			dataStr[1]=String.valueOf(data[1]);
			dataList.add(dataStr);
		}
		return dataList;
	}
	/**
	 * 合作伙伴市场份额基站饼图
	 */
	public List getSitePie(String area){
		List dataList = new ArrayList();
		List list = ipnrStatDao.getSitePie(area);
//		ID2NameService mgr = (ID2NameService) ApplicationContextHolder
//			.getInstance().getBean("id2nameService");
		Object[] data = null;
		for(int i=0;i<list.size();i++){
			data = (Object[])list.get(i);
			String[] dataStr = new String[2];
//			dataStr[0] = mgr.id2Name(String.valueOf(data[0]),"partnerDeptDao");
			dataStr[0]=String.valueOf(data[0]);
			dataStr[1]=String.valueOf(data[1]);
			dataList.add(dataStr);
		}
		return dataList;
	}
	
	
	
	/**
	 * 代维响应速度统计
	 * 统计条件：按地市、网格、合作伙伴统计。按年,月统计。
	 * @param region 地市
	 * @param provider 合作伙伴
	 * @param gridName 网格
	 * @param year 年
	 * @param month 月
	 */
	/*public List getServiceSpeedStat(final String region,final String provider,final String gridName,final String year,final String month,final List LineListPartner){
		
		List listStat = new ArrayList();
		ServiceSpeedStatForm serviceSpeedForm = new ServiceSpeedStatForm(); 
		List list = ipnrStatDao.getServiceSpeedStat(region,provider,gridName,year,month,LineListPartner);
		Object[] object = new Object[4];
		PartnerDept dept = null;
		ServiceSpeed serviceSpeed =null;
		String tempStr = "";
		String rowStr = "";

		for(int i=0;i<list.size();i++){
			object =  (Object[])list.get(i);
			dept = (PartnerDept)object[0];
			serviceSpeed = (ServiceSpeed)object[3];
			rowStr = year+"_"+month+"_"+serviceSpeed.getRegion()+serviceSpeed.getCity()+"_"+dept.getName()+"_"+serviceSpeed.getGrid();
			
			if(!rowStr.equals(tempStr)&&i!=0){
				listStat.add(serviceSpeedForm);
				serviceSpeedForm = new ServiceSpeedStatForm();
			}
			serviceSpeedForm.setYear(serviceSpeed.getYear());
			serviceSpeedForm.setMonth(serviceSpeed.getMonth());
			serviceSpeedForm.setRegion(serviceSpeed.getRegion());
			serviceSpeedForm.setCity(serviceSpeed.getCity());
			serviceSpeedForm.setGrid(serviceSpeed.getGrid());
			serviceSpeedForm.setProvider(dept.getName());
			//网络故障响应度
			serviceSpeedForm.setWebfailure(serviceSpeedForm.getWebfailure() + serviceSpeed.getWebfailure());
			//客户投诉处理响应度
			serviceSpeedForm.setCustomerComplaints(serviceSpeedForm.getCustomerComplaints() + serviceSpeed.getCustomerComplaints());
			//对基层业务、服务的响应度
			serviceSpeedForm.setToService(serviceSpeedForm.getToService() + serviceSpeed.getToService());
			//表报上报及时率
			serviceSpeedForm.setFromReport(serviceSpeedForm.getFromReport() + serviceSpeed.getFromReport());
			//表报上报准确率
			serviceSpeedForm.setFromPrecision(serviceSpeedForm.getFromPrecision() + serviceSpeed.getFromPrecision());
			//资料更新准确率
			serviceSpeedForm.setDatumUpdate(serviceSpeedForm.getDatumUpdate() + serviceSpeed.getDatumUpdate());
			//应急通信保障响应度
			serviceSpeedForm.setCommSecurity(serviceSpeedForm.getCommSecurity() + serviceSpeed.getCommSecurity());
						
			tempStr = rowStr;
			if(i==list.size()-1){
				listStat.add(serviceSpeedForm);
			}
		}
		return listStat;
	}*/

	/**
	 * 得到各地域合作伙伴（大）应配的人员配置
	 * @param cityStr 地域字符串
	 */
	public List getPersonConfig(final String cityStr){
		return ipnrStatDao.getPersonConfig(cityStr);
	}
	
	/**
	 * 得到各地域合作伙伴（大）实配的人员
	 * @param endTime 时间
	 * @param cityStr 地域字符串
	 */
	public List getPersonByPartner(final String endTime,final String cityStr){
		return ipnrStatDao.getPersonByPartner(endTime,cityStr);
	}
	/**
	 * 得到各地域各合作伙伴数量用于显示Chart图表
	 * @param time 时间
	 * @param city 地域
	 */
	public String getPartnerNumforChart(final String time,final String city, final String urlString){
		FusionChart fusionChart = new FusionChart();
		String strXml = "";
		List dateList =new ArrayList();
		List list = ipnrStatDao.getPartnerNumforChart(time,city);
		Object[] object = new Object[3];
		for(int i=0;i<list.size();i++){
			object =  (Object[])list.get(i);
			FusionChartData fusionChartData = new FusionChartData();
			fusionChartData.setTitle(String.valueOf(object[0]));
			fusionChartData.setNumForInt(String.valueOf(object[1]));
			fusionChartData.setUrl("n-"+urlString+"/partner/baseinfo/partnerDepts.do?method=search&notShowAll=1&interfaceHeadId="+String.valueOf(object[2])+"&city="+city);
			dateList.add(fusionChartData);
		}
		fusionChart.setCaption("合作伙伴占有份额图表");
		strXml = FusionChartMethod.getString(dateList, fusionChart);
		return strXml;
	}

	public List getPartnerNumApparatus(String city) {
		List list = ipnrStatDao.getPartnerNumApparatuss(city);
		return list;
	}

	public List getPartnerNumCar(String city) {
		List list = ipnrStatDao.getPartnerNumCars(city);
		return list;
	}

	public List getPartnerNumOil(String city) {
		List list = ipnrStatDao.getPartnerNumOils(city);
		return list;
	}

	public List getPartnerNumUsers(String city) {
		List list = ipnrStatDao.getPartnerNumUsers(city);
		return list;
	}
}
