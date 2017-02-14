package com.boco.eoms.partner.excel.webapp.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.common.resource.ExcelUtil;

public class ExcelParDealExportAction extends BaseAction {
		
//	/**
//	 * 导出Excel 网格KPI月报
//	 * author:wangjunfeng
//	 * 2010-6-12
//	 * @throws Exception
//	 */
//	public ActionForward excleExport(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response)
//			throws Exception {
//
//		IGridKPIMonthMgr gridKPIMonthMgr = (IGridKPIMonthMgr) ApplicationContextHolder
//		.getInstance().getBean("iGridKPIMonthMgr");
//		
//		GridKPIMonthForm gridKPIMonthForm = (GridKPIMonthForm) form;
//		GridKPIMonth gridKPIMonthCondition = (GridKPIMonth) convert(gridKPIMonthForm);
//		
//		String region = gridKPIMonthCondition.getRegion();
//		String cityStr = gridKPIMonthCondition.getCity();
//		String provider = gridKPIMonthCondition.getCompanyName();
//		String gridName = gridKPIMonthCondition.getGridName();
//		String year = gridKPIMonthCondition.getYear();
//		String month = gridKPIMonthCondition.getMonth();
//		
//		StringBuffer where = new StringBuffer();
//		where.append(" where isDel = '0' ");
//		if(!"".equals(provider)){
//			where.append(" and companyName = '");
//			where.append(provider);
//			where.append("'");
//		}
//		if(!"".equals(gridName)){
//			where.append(" and gridName = '");
//			where.append(gridName);
//			where.append("'");
//		}
//		if(!"".equals(year)){
//			where.append(" and year = '");
//			where.append(year);
//			where.append("'");
//		}
//		if(!"".equals(month)){
//			where.append(" and month = '");
//			where.append(month);
//			where.append("'");
//		}
//		
//		if(!"".equals(cityStr)){
//			where.append(" and city = '");
//			where.append(cityStr);
//			where.append("' ");
//		}
//		if(!"".equals(region)){
//			where.append(" and region = '");
//			where.append(region);
//			where.append("' ");
//		}
//		
//		//导出条件
//		List list = gridKPIMonthMgr.getGridKPI(where.toString());
//		
//		
//		
//		response.setHeader("Content-Disposition", "attachment; filename="
//				+ new String(("月考核网格KPI报表.xls").getBytes("GBK"), "ISO8859-1"));
//		Object[] temp;
//		List dataList = new ArrayList();
//		
//		TawSystemAreaDaoHibernate tawSystemAreaDao=(TawSystemAreaDaoHibernate)getBean("tawSystemAreaDao");
//		
//		for (int i=0;i<list.size();i++) {
//			temp = new Object[41];  //页面上41个字段  
//			
//			GridKPIMonth gridKPIMonth = (GridKPIMonth)list.get(i);
//			
//			temp[0] = gridKPIMonth.getYear();  //年
//			temp[1] = gridKPIMonth.getMonth(); //月
//			if(gridKPIMonth.getRegion() == null ||"".equals(gridKPIMonth.getRegion())||tawSystemAreaDao.id2Name(gridKPIMonth.getCity())==null){
//				temp[2] = "";
//			}else{
//				temp[2] = tawSystemAreaDao.id2Name(gridKPIMonth.getRegion())+"_"+gridKPIMonth.getRegion() ; //地市
//			}
//			if(gridKPIMonth.getCity() == null ||"".equals(gridKPIMonth.getCity()) ||tawSystemAreaDao.id2Name(gridKPIMonth.getCity())==null){
//				temp[3] = "";
//			}else{
//				temp[3] = tawSystemAreaDao.id2Name(gridKPIMonth.getCity())+"_"+gridKPIMonth.getCity() ; //县区
//			}
//			
//			temp[4] = gridKPIMonth.getCompanyName(); //合作伙伴
//			temp[5] = gridKPIMonth.getGridName(); //网格名称
//			temp[6] = gridKPIMonth.getGridNumber(); //网格ID
//			temp[7] = gridKPIMonth.getStaffPlaceValue();//人员到位率数值(%)
//			temp[8] = gridKPIMonth.getStaffPlaceScore();//人员到位率得分
//			temp[9] = gridKPIMonth.getCertificatesValue();//持证上岗率数值(%)
//			
//			temp[10] = gridKPIMonth.getCertificatesScore();//持证上岗率得分
//			temp[11] = gridKPIMonth.getConfigMeterValue(); //仪表工具配置率数值(%)
//			temp[12] = gridKPIMonth.getConfigMeterScore();//仪表工具配置率得分
//			temp[13] = gridKPIMonth.getVehicleConfigValue();//车辆配置率数值(%)
//			temp[14] = gridKPIMonth.getVehicleConfigScore();//车辆配置率得分
//			temp[15] = gridKPIMonth.getMachineConfigValue();//油机配置率数值(%)
//			temp[16] = gridKPIMonth.getMachineConfigScore();//油机配置率得分
//			
//			temp[17] = gridKPIMonth.getPartnerPromoteValue();//合作伙伴管理系统推广使用数值(%)
//			temp[18] = gridKPIMonth.getPartnerPromoteScore();//合作伙伴管理系统推广使用得分
//			temp[19] = gridKPIMonth.getGridGroupValue();//网格维护组巡数值(%)
//			temp[20] = gridKPIMonth.getGridGroupScore();//网格维护组巡得分
//
//			temp[21] = gridKPIMonth.getCampForwardValue();//营维合一推进数值(%)
//			temp[22] = gridKPIMonth.getCampForwardScore(); //营维合一推进得分
//			temp[23] = gridKPIMonth.getDropCallRateValue();//掉话率数值(%)
//			temp[24] = gridKPIMonth.getDropCallRateScore();//掉话率得分
//			temp[25] = gridKPIMonth.getThroughRateValue();//接通率数值(%)
//			temp[26] = gridKPIMonth.getThroughRateScore();//接通率得分
//			temp[27] = gridKPIMonth.getMostfailRateValue();//接入网重大故障率数值(%)
//			temp[28] = gridKPIMonth.getMostfailRateScore();//接入网重大故障率得分
//			temp[29] = gridKPIMonth.getCellIntegrityValue();//小区完好性数值(%)
//			temp[30] = gridKPIMonth.getCellIntegrityScore();//小区完好性得分
//
//			temp[31] = gridKPIMonth.getCustomersFavorableValue();//集团客户良好率数值(%)
//			temp[32] = gridKPIMonth.getCustomersFavorableScore(); //集团客户良好率得分
//			temp[33] = gridKPIMonth.getIntegratedNodeValue();//综合业务节点完好性数值(%)
//			temp[34] = gridKPIMonth.getIntegratedNodeScore();//综合业务节点完好性得分
//			temp[35] = gridKPIMonth.getEomsOrderValue();//EOMS工单处理及时率数值(%)
//			temp[36] = gridKPIMonth.getEomsOrderScore();//EOMS工单处理及时率得分
//			temp[37] = gridKPIMonth.getCorporateComplaintsValue();//定为我省企业责任的升级投诉事件数值(%)
//			temp[38] = gridKPIMonth.getCorporateComplaintsScore();//定为我省企业责任的升级投诉事件得分
//			temp[39] = gridKPIMonth.getCustomerIndexValue();//客户满意度指标数值(%)
//			temp[40] = gridKPIMonth.getCustomerIndexScore();//客户满意度指标得分
//			
//			for (int j = 0; j < 41; j++) {
//				if(temp[j]!=null) temp[j]=temp[j].toString().trim();
//			}
//			
//			dataList.add(temp);
//		}
//		
//		int tempSize =41 ;
//		ExcelUtil.WriteExcel(request
//				.getRealPath("/WEB-INF/pages/partner/parExceldeal/download/gridKPIMonth(hibernate).xls"),
//				"", 7, dataList, response.getOutputStream() ,tempSize);
//		
//		return null;
//		
//	}
//
//
//
//	
//	/**
//	 * 导出Excel 资源配置到位率
//	 * author:wangjunfeng
//	 * 2010-6-23
//	 * @throws Exception
//	 */
//	public ActionForward excleExportResourceConfigRate(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response)
//			throws Exception {
///*福建版本开始  
//		IPnrStatMgr pnrStatMgr = (IPnrStatMgr) getBean("iPnrStatMgr"); 
//		String region = StaticMethod.nullObject2String(request.getParameter("region"));
//		String cityStr = StaticMethod.nullObject2String(request.getParameter("city"));
//		String provider = StaticMethod.nullObject2String(request.getParameter("provider"));
//		String gridName = StaticMethod.nullObject2String(request.getParameter("grid"));
//		String year = StaticMethod.nullObject2String(request.getParameter("year"));
//		String month = StaticMethod.nullObject2String(request.getParameter("month"));
//		
//		StringBuffer where = new StringBuffer();
//		where.append(" ");
//		if(!"".equals(cityStr)){
//			where.append(" and tsa.areaid = '");
//			where.append(cityStr);
//			where.append("' ");
//		}
//		if(!"".equals(region)){
//			where.append(" and  tree.areaid = '");
//			where.append(region);
//			where.append("' ");
//		}
//		if(!"".equals(provider)){
//			where.append(" and tree.nodename = '");
//			where.append(provider);
//			where.append("'");
//		}
//		if(!"".equals(gridName)){
//			where.append(" and gide.grid_name =  '");
//			where.append(gridName);
//			where.append("'");
//		}		
//
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
//		String timeSelect = year+"-"+ (month);
//		Date date = sdf.parse(timeSelect);
//		Calendar cal = Calendar.getInstance();
//		cal.setTime(date);
//		cal.add(Calendar.MONTH, 1);
//		String endTime = sdf.format(cal.getTime());
//		
//		//导出条件
//		List listTemp = pnrStatMgr.getPartnersByTimeAndCityRate(endTime, where.toString());
//		
//		
//		response.setHeader("Content-Disposition", "attachment; filename="
//				+ new String(("资源配置到位率报表.xls").getBytes("GBK"), "ISO8859-1"));
//		Object[] temp;
//		List dataList = new ArrayList();
//		
//		TawSystemAreaDaoHibernate tawSystemAreaDao=(TawSystemAreaDaoHibernate)getBean("tawSystemAreaDao");
//
//		String regionStat ="";
//		String cityStat ="";
//		String partnerNameStat ="";
//		String gridStat ="";
//		ResourceConfigRateForm  resourceConfigRateForm = null;
//		List statFormList = new ArrayList();
//		PnrBaseInfoBO pnrinfobo = PnrBaseInfoBO.getInstance();
//		
//		//统计
//		for(int k=0;k<listTemp.size();k++){
////			resourceConfigRateForm = resourceConfigRateForm = (ResourceConfigRateForm)listTemp.get(k);
////			regionStat = resourceConfigRateForm.getRegion();
////			cityStat =  resourceConfigRateForm.getCity();
////			partnerNameStat =  resourceConfigRateForm.getPartnerName();
////			gridStat =  resourceConfigRateForm.getGrid();
//
//			Object[] bsObject =  (Object[])listTemp.get(k);
//			
//    		//地市名称
//    		regionStat = (String)bsObject[2];
//    		//县区名称
//    		cityStat = (String)bsObject[4];
//    		//所属公司（小合作伙伴）
//    		partnerNameStat = (String)bsObject[1];
//    		//网格名称
//    		gridStat = (String)bsObject[6];
//    		
//    		resourceConfigRateForm  = new ResourceConfigRateForm();
//			resourceConfigRateForm.setRegion(regionStat);
//			resourceConfigRateForm.setCity(cityStat);
//			resourceConfigRateForm.setPartnerName(partnerNameStat);
//			resourceConfigRateForm.setGrid(gridStat);
//			//人员应配 数量
//			resourceConfigRateForm.setPersonConfigNum(pnrinfobo.getPersonyps(cityStat, partnerNameStat, gridStat).toString());
//			//人员实配 数量
//			resourceConfigRateForm.setPersonNum(pnrinfobo.getPersonsps(cityStat, gridStat).toString());
//			//人员持证上岗人数
//			resourceConfigRateForm.setPersonspsIsHaspost(pnrinfobo.getPersonspsIsHaspost(cityStat, gridStat).toString());
//			//仪器仪表应配 数量
//			resourceConfigRateForm.setApparatusConfnum(StaticMethod.nullObject2String(pnrinfobo.getYqybyps(cityStat, partnerNameStat, gridStat)));
//			//仪器仪表实配 数量
//			resourceConfigRateForm.setApparatusNum(StaticMethod.nullObject2String(pnrinfobo.getYqybsps(cityStat, gridStat)));
//			//油机应配 数量
//			resourceConfigRateForm.setOilConfigNum(StaticMethod.nullObject2String(pnrinfobo.getOilyps(cityStat, partnerNameStat, gridStat)));
//			//油机实配 数量
//			resourceConfigRateForm.setOilNum(StaticMethod.nullObject2String(pnrinfobo.getOilSps(cityStat, gridStat)));
//			//车辆应配 数量
//			resourceConfigRateForm.setCarConfnum(StaticMethod.nullObject2String(pnrinfobo.getCaryps(cityStat, partnerNameStat, gridStat)));
//			//车辆实配 数量
//			resourceConfigRateForm.setCarNum(StaticMethod.nullObject2String(pnrinfobo.getCarsps(cityStat, gridStat)));
//			statFormList.add(resourceConfigRateForm);
//		}
//		
//		for (int i=0;i<statFormList.size();i++) {
//			temp = new Object[17];  //页面上17个字段  
//			
//			ResourceConfigRateForm  rateForm = (ResourceConfigRateForm)statFormList.get(i);
//			
//			temp[0] = tawSystemAreaDao.id2Name(rateForm.getRegion()) ; 	 //地市
//			temp[1] = tawSystemAreaDao.id2Name(rateForm.getCity()) ; 	 //县区
//			temp[2] = rateForm.getPartnerName() ; //所属公司			
//			temp[3] = rateForm.getGrid() ; //网格
//			
//			temp[4] = rateForm.getPersonConfigNum(); //持上岗证人员 应配数量
//			temp[5] = rateForm.getPersonNum(); //持上岗证人员到位数量 
//			temp[6] = rateForm.getPersonRateMy(); //持上岗率
//			temp[7] = rateForm.getPersonRate(); //人员到位率 
//			
//			temp[8] = rateForm.getApparatusConfnum(); //仪表应配数量
//			temp[9] = rateForm.getApparatusNum(); //仪表到位数量
//			temp[10] = rateForm.getApparatusRate(); //仪表到位率
//			
//			temp[11] = rateForm.getOilConfigNum(); //油机应配数量
//			temp[12] = rateForm.getOilNum(); //油机到位数量
//			temp[13] = rateForm.getOilRate(); //油机到位率
//
//			temp[14] = rateForm.getCarConfnum(); //车辆应配数量
//			temp[15] = rateForm.getCarNum(); //车辆到位数量
//			temp[16] = rateForm.getCarRate(); //车辆到位率
//
//			
//			for (int j = 0; j < 17; j++) {
//				if(temp[j]!=null) temp[j]=temp[j].toString().trim();
//			}
//			
//			dataList.add(temp);
//		}
//		int tempSize = 17 ;
//		ExcelUtil.WriteExcel(request
//				.getRealPath("/WEB-INF/pages/partner/parExceldeal/download/resourceConfigRate.xls"),
//				"", 2, dataList, response.getOutputStream(),tempSize);
//		
//		return null;
//福建版本结束*/
//		
//
///*广西版本开始 */		
//		IPnrStatMgr pnrStatMgr = (IPnrStatMgr) getBean("iPnrStatMgr"); 
//		String region = StaticMethod.nullObject2String(request.getParameter("region"));
//		String provider = StaticMethod.nullObject2String(request.getParameter("provider"));
//		String gridCondition = StaticMethod.nullObject2String(request.getParameter("grid"));
//		String year = StaticMethod.nullObject2String(request.getParameter("year"));
//		String month = StaticMethod.nullObject2String(request.getParameter("month"));
//		//统计条件
//		String whereStr="";
//		if(region!=null && !"".equals(region)){
//			whereStr += " and t1.areaid='"+ region +"'  ";
//		}
//		if(provider!=null && !"".equals(provider)){
//			String[] providers =  provider.split("_");
//			whereStr += " and t2.partnerid='"+ providers[0] +"'  ";
//		}
//		if(gridCondition!=null && !"".equals(gridCondition)){
//			whereStr += " and gide.id = '"+ gridCondition +"'  ";
//		}
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
//		String timeSelect = year+"-"+ (month);
//		Date date = sdf.parse(timeSelect);
//		Calendar cal = Calendar.getInstance();
//		cal.setTime(date);
//		cal.add(Calendar.MONTH, 1);
//		String endTime = sdf.format(cal.getTime());
//		
//		//导出条件
//		List listTemp = pnrStatMgr.getPartnersByTimeAndCityRate(endTime, whereStr);
//		
//		
//		response.setHeader("Content-Disposition", "attachment; filename="
//				+ new String(("资源配置到位率报表.xls").getBytes("GBK"), "ISO8859-1"));
//		Object[] temp;
//		List dataList = new ArrayList();
//		
//		TawSystemAreaDaoHibernate tawSystemAreaDao=(TawSystemAreaDaoHibernate)getBean("tawSystemAreaDao");
//
//		String cityStat= "";
//		String regionIdStat ="";
//		String partnerIdStat ="";
//		String partnerNameStat ="";
//		String gridIdStat ="";
//		String gridStat ="";
//		ResourceConfigRateForm  resourceConfigRateForm = null;
//		List statFormList = new ArrayList();
//		PnrBaseInfoBO pnrinfobo = PnrBaseInfoBO.getInstance();
//		
//		//统计
//		for(int k=0;k<listTemp.size();k++){
//			Object[] bsObject =  (Object[])listTemp.get(k);
//			
//    		//地市ID
//			regionIdStat = (String)bsObject[0];
//    		//合作伙伴ID
//			partnerIdStat = (String)bsObject[1];
//    		//合作伙伴名称
//    		partnerNameStat = (String)bsObject[2];    		
//    		//网格ID
//    		gridIdStat = (String)bsObject[3];
//    		//网格名称
//    		gridStat = (String)bsObject[4];
//    		
//    		resourceConfigRateForm = new ResourceConfigRateForm();
//			resourceConfigRateForm.setRegion(regionIdStat);
//			resourceConfigRateForm.setPartnerId(partnerIdStat);
//			resourceConfigRateForm.setPartnerName(partnerNameStat);
//			resourceConfigRateForm.setGridId(gridIdStat);
//			resourceConfigRateForm.setGridName(gridStat);
//			
//			//人员应配 数量
//			resourceConfigRateForm.setPersonConfigNum(pnrinfobo.getPersonyps(cityStat, partnerIdStat, gridIdStat ,endTime).toString());
//			//人员实配 数量
//			resourceConfigRateForm.setPersonNum(pnrinfobo.getPersonsps(cityStat, partnerIdStat ,gridIdStat ,endTime).toString());
//			
//			//人员持证上岗人数
//			resourceConfigRateForm.setPersonspsIsHaspost(pnrinfobo.getPersonspsIsHaspost(cityStat, partnerIdStat ,gridIdStat ,endTime).toString());
//			
//			//仪器仪表应配 数量
//			resourceConfigRateForm.setApparatusConfnum(StaticMethod.nullObject2String(pnrinfobo.getYqybyps(cityStat, partnerIdStat, gridIdStat ,endTime)));
//			//仪器仪表实配 数量
//			resourceConfigRateForm.setApparatusNum(StaticMethod.nullObject2String(pnrinfobo.getYqybsps(cityStat, partnerIdStat ,gridIdStat ,endTime)));
//			
//			//油机应配 数量
//			resourceConfigRateForm.setOilConfigNum(StaticMethod.nullObject2String(pnrinfobo.getOilyps(cityStat, partnerIdStat, gridIdStat ,endTime)));
//			//油机实配 数量
//			resourceConfigRateForm.setOilNum(StaticMethod.nullObject2String(pnrinfobo.getOilSps(cityStat,partnerIdStat,gridIdStat ,endTime)));
//			
//			//车辆应配 数量
//			resourceConfigRateForm.setCarConfnum(StaticMethod.nullObject2String(pnrinfobo.getCaryps(cityStat, partnerIdStat, gridIdStat ,endTime)));
//			//车辆实配 数量
//			resourceConfigRateForm.setCarNum(StaticMethod.nullObject2String(pnrinfobo.getCarsps(cityStat, partnerIdStat,gridIdStat ,endTime)));
//			statFormList.add(resourceConfigRateForm);
//		}
//		
//		for (int i=0;i<statFormList.size();i++) {
//			temp = new Object[16];  //页面上16个字段  
//			
//			ResourceConfigRateForm  rateForm = (ResourceConfigRateForm)statFormList.get(i);
//			
//			temp[0] = tawSystemAreaDao.id2Name(rateForm.getRegion()) ; 	 //地市
//			temp[1] = rateForm.getPartnerName() ; //所属公司			
//			temp[2] = rateForm.getGridName() ; //网格
//			
//			temp[3] = rateForm.getPersonConfigNum(); //人员 应配数量
//			temp[4] = rateForm.getPersonNum(); //人员到位数量 
//			temp[5] = rateForm.getPersonRate()+"%"; //人员到位率
//			temp[6] = rateForm.getPersonspsIsHaspost(); //人员持证上岗人数
//			
//			temp[7] = rateForm.getApparatusConfnum(); //仪表应配数量
//			temp[8] = rateForm.getApparatusNum(); //仪表到位数量
//			temp[9] = rateForm.getApparatusRate()+"%"; //仪表到位率
//			
//			temp[10] = rateForm.getOilConfigNum(); //油机应配数量
//			temp[11] = rateForm.getOilNum(); //油机到位数量
//			temp[12] = rateForm.getOilRate()+"%"; //油机到位率
//
//			temp[13] = rateForm.getCarConfnum(); //车辆应配数量
//			temp[14] = rateForm.getCarNum(); //车辆到位数量
//			temp[15] = rateForm.getCarRate()+"%"; //车辆到位率
//
//			
//			for (int j = 0; j < 16; j++) {
//				if(temp[j]!=null) temp[j]=temp[j].toString().trim();
//			}
//			
//			dataList.add(temp);
//		}
//		int tempSize = 16 ;
//		ExcelUtil.WriteExcel(request
//				.getRealPath("/WEB-INF/pages/partner/parExceldeal/download/ResourceConfigRate(gx).xls"),
//				"", 2, dataList, response.getOutputStream(),tempSize);
//		
//		return null;
//		
//	}


	
	/**
	 * 导出Excel 资源配置到位率
	 * author:zhangkeqi
	 * 2012-2-20
	 * @throws Exception
	 */
	public ActionForward excleExportDevice(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//IPnrInspectTreeMgr inspectTreeMgr = (IPnrInspectTreeMgr) getBean("iPnrInspectTreeMgr");
		String nodId = StaticMethod.nullObject2String(request.getParameter("nodId"));
		List list = new ArrayList();
		// 设置输出的格式
		response.reset();
		response.setHeader("Content-Disposition", "attachment; filename="
				+ new String(("设备类型关系表表.xls").getBytes("GBK"), "ISO8859-1"));
		Object[] temp;
		Object[] drvice;
		List dataList = new ArrayList();
		for(int i = 0 ; i<list.size();i++){
			temp = new Object[4];
			drvice = (Object[])list.get(i);
			temp[0] = drvice[0].toString().trim();
			temp[1] = drvice[1].toString().trim();
			temp[2] = drvice[2].toString().trim();
			temp[3] = drvice[3].toString().trim();
			dataList.add(temp);
		}
		int tempSize = 4 ;
		ExcelUtil.WriteExcel(request
				.getRealPath("/WEB-INF/pages/partner/parExceldeal/download/deviceExcel.xls"),
				"", 2, dataList, response.getOutputStream(),tempSize);
		
		return null;
	}	
}
