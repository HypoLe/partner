package com.boco.eoms.partner.baseinfo.webapp.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.area.model.TawSystemArea;
import com.boco.eoms.commons.system.dict.model.TawSystemDictType;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;
import com.boco.eoms.partner.baseinfo.mgr.AreaDeptTreeMgr;
import com.boco.eoms.partner.baseinfo.mgr.IPnrStatMgr;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.model.AreaDeptTree;
import com.boco.eoms.partner.baseinfo.model.FusionChart;
import com.boco.eoms.partner.baseinfo.model.FusionChartData;
import com.boco.eoms.partner.baseinfo.model.OilEngineConfigure;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;
import com.boco.eoms.partner.baseinfo.model.PartnerUser;
import com.boco.eoms.partner.baseinfo.model.PersonConfig;
import com.boco.eoms.partner.baseinfo.model.TawPartnerOil;
import com.boco.eoms.partner.baseinfo.util.FusionChartMethod;
import com.boco.eoms.partner.baseinfo.util.MakePieChart;
import com.boco.eoms.partner.baseinfo.util.PartnerCityByUser;
import com.boco.eoms.partner.baseinfo.util.PnrBaseAreaIdList;
import com.boco.eoms.partner.baseinfo.util.RoleIdList;
import com.boco.eoms.partner.baseinfo.webapp.form.BaseinfoStatForm;
import com.boco.eoms.partner.baseinfo.webapp.form.GridSatisfyStatForm;
import com.boco.eoms.partner.baseinfo.webapp.form.LineStatReportForm;
import com.boco.eoms.partner.baseinfo.webapp.form.OilStatByCtiyForm;
import com.boco.eoms.partner.baseinfo.webapp.form.OilStatByStateForm;
import com.boco.eoms.partner.baseinfo.webapp.form.ServiceSpeedStatForm;
import com.boco.eoms.partner.serviceArea.mgr.ISiteMgr;
import com.boco.eoms.partner.serviceArea.model.Line;
import com.boco.eoms.partner.serviceArea.util.SiteConstants;

/**
 * <p>
 * Title:合作伙伴 统计报表
 * </p>
 * <p>
 * Description:统计报表
 * </p>
 * <p>
 * 2010-1-7 
 * </p>
 * 
 * @moudle.getAuthor() 
 * @moudle.getVersion() 1.0
 * 
 */
public final class PnrStatAction extends BaseAction {
 
	
	/**
	 * 线路统计 输入统计条件页面
	 * 统计条件：按合作伙伴名称和地市统计。按月统计。
	 * author:wangjunfeng
	 */
	public ActionForward lineCondition(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	   	/**
    	 * 加载当前用户所属地市
    	 */
    	//当前用户名
    	String userid = this.getUser(request).getUserid();
    	List LineListRegion = PartnerCityByUser.getCityByUser(userid);
    	
    	/**
    	 * 加载合作伙伴
    	 */
    	IPnrStatMgr pnrStatMgr = (IPnrStatMgr) getBean("iPnrStatMgr");
		StringBuffer provider_list = new StringBuffer();
		provider_list.append("," + "");
		provider_list.append("," + "--请选择合作伙伴--");
		List LineListPartner = pnrStatMgr.listLinePartner();
		for (int i = 0; i < LineListPartner.size(); i++) {
			String partner = LineListPartner.get(i).toString();
			provider_list.append("," + partner);
			provider_list.append("," + partner);
			
		}
		String providerBuffer = provider_list.toString();
		providerBuffer = providerBuffer.substring(1, providerBuffer.length());
		
		request.setAttribute("regionBuffer", LineListRegion);
		request.setAttribute("providerBuffer", providerBuffer);

		return mapping.findForward("lineCondition");
	}

	/**
	 * 线路统计 输入统计条件页面
	 * 统计条件：按合作伙伴名称和地市统计。按月统计。
	 * author:wangjunfeng
	 */
	public ActionForward lineConditionByLineLevel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	   	/**
    	 * 加载当前用户所属地市
    	 */
    	//当前用户名
    	String userid = this.getUser(request).getUserid();
    	List LineListRegion = PartnerCityByUser.getCityByUser(userid);

    	
    	request.setAttribute("regionBuffer", LineListRegion);
    	
		return mapping.findForward("lineConditionByLineLevel");
	}

	/**
	 * 页面二级联动，已知地市，返回对应县区list
	 * author:wangjunfeng
	 */
	public ActionForward changeRegion(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JSONArray json = new JSONArray();
		JSONObject jitem = new JSONObject();
		String regionId = request.getParameter("region");

		String cityBuffer= PartnerCityByUser.getCountyByCity(regionId);
		
		jitem.put("cb", cityBuffer);
		json.put(jitem);
		
		response.setCharacterEncoding("utf-8");		
		response.getWriter().write(json.toString());

		return null;
	}
	
	

	/**
	 * 线路 统计
	 * 统计条件：按合作伙伴名称和地市统计。按月统计。  从选择月开始统计之前所有的信息
	 * 2010-01-08
	 * author:wangjunfeng
	 * 
	 */
	public ActionForward getLineStatReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
 
		// 加载当前用户所属地市city
    	String userid = this.getUser(request).getUserid();
    	List regionList = PartnerCityByUser.getCityByUser(userid);

		
		//年份
		String year = StaticMethod.nullObject2String(request.getParameter("year"));
		//月份
		String month = StaticMethod.nullObject2String(request.getParameter("month"));
		String endTime = null;
		if(year!=null && !"".equals(year) && month !=null && !"".equals(month)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			String timeSelect = year + "-" + (month); 
			Date date = sdf.parse(timeSelect);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.MONTH, 1);
			endTime = sdf.format(cal.getTime());

		}
		
		IPnrStatMgr pnrStatMgr = (IPnrStatMgr) getBean("iPnrStatMgr");
		//地市名称条件
		String areaStr="";
		String[] regions = (String[])(request.getParameterValues("region"));
		if(regions[0]!=null && !"".equals(regions[0])){
			for(int i=0;!("").equals(regions[0])&&i<regions.length;i++){
				if(i!=0){
					areaStr=areaStr+",";
				}
				areaStr="'"+regions[i]+"'";
			}
		}else{
		    for(int i = 0;regionList.size()>i;i++){
		    	TawSystemArea tawSystemArea = (TawSystemArea)regionList.get(i);
		    	if(i!=0){
		    		areaStr=areaStr+",";
				}
		    	areaStr+="'"+ tawSystemArea.getAreaid() +"'";
		    }
		}
		
		//合作伙伴条件
		String partnerStr ="";
		String [] partners =(String[])(request.getParameterValues("provider"));
		if(partners[0]!=null && !"".equals(partners[0])){
			for(int i=0;!("").equals(partners[0]) && i<partners.length ; i++){
				if(i!=0){
					partnerStr = partnerStr + ",";
				}
				partnerStr="'"+ partners[i] +"'";
			}
		}
		
		
		
		Object[] object= null;
		PartnerDept dept = null;
		AreaDeptTree tree = null;
		TawSystemArea tsa = null;
		Line line = null;
		String keyLineSum ="";
		String keyConnect ="";
		Map baseMap = new HashMap();
		Map rowMap = new HashMap();

		
		
		
		//线路表
		List lineReportStat = pnrStatMgr.getLineReportStat(endTime);
		for(int i=0;i<lineReportStat.size();i++){
			object =(Object[])lineReportStat.get(i);
			line =(Line)object[0];
			tree =(AreaDeptTree)object[1];
			
			//keyLineSum = line.getProvider()+"_"+line.getRegion()+"_"+line.getCity()+"_lineSum";
			
			keyConnect = tree.getInterfaceHeadId() +"_"+ line.getRegion()+"_"+line.getCity()+"_connect";
			//相同合作伙伴（小）、地市、县区的线路长度的和
			//baseMap.put(keyLineSum, String.valueOf(StaticMethod.nullObject2int(baseMap.get(keyLineSum), 0)+line.getLineLength()));
			
			//大合作伙伴、地市、县区 
			baseMap.put(keyConnect, String.valueOf(StaticMethod.nullObject2int(baseMap.get(keyConnect), 0)+line.getLineLength()));
		}
		
		
		//合并后
		LineStatReportForm lineStatReportForm = null ;
		List lineStatList = new ArrayList();
		
		String bigPartnerId = null;
		String bigPartnerValue =null;
		String partnerValue = null;
		String regionValue = null;
		String cityValue =null;
		Integer lineLength =0;
		
		
		
		//得到基本表，包括（大合作伙伴、小合作伙伴、地市、县区）
		List baseList = pnrStatMgr.getBasePartnerAndRegion(areaStr,partnerStr);
		for(int i=0;i<baseList.size();i++){
			lineStatReportForm = new LineStatReportForm();
			object = (Object[])baseList.get(i);
			dept = (PartnerDept)object[0];
			tree = (AreaDeptTree)object[1];
			tsa = (TawSystemArea)object[2];
			
			//大合作伙伴ID
			bigPartnerId= tree.getInterfaceHeadId();
			//大合作伙伴
			bigPartnerValue=dept.getName();
			//小合作伙伴
			partnerValue =tree.getNodeName();
			//地市ID
			regionValue =tree.getAreaId();
			//县区ID
			cityValue =tsa.getAreaid();
			
			String bigPartnerIdTem = "";
			String regionTem = "";
			String cityTem = "";

			//小合作伙伴名称、地市ID、县区ID
			//keyLineSum = tree.getNodeName()+"_"+tree.getAreaId()+"_"+tsa.getAreaid()+"_lineSum";
			
			keyConnect = tree.getInterfaceHeadId()+"_"+tree.getAreaId()+"_"+tsa.getAreaid()+"_connect";

			if(!bigPartnerIdTem.equals(bigPartnerId) && !regionTem.equals(regionValue) &&!cityTem.equals(cityValue) ){
				lineStatReportForm.setBigProviderID(bigPartnerId);
				lineStatReportForm.setBigProvider(bigPartnerValue);
				lineStatReportForm.setProvider(partnerValue);
				lineStatReportForm.setRegion(regionValue);
				lineStatReportForm.setCity(cityValue);
				//线路的长度和
				lineLength  = StaticMethod.nullObject2int(baseMap.get(keyConnect),0);
				lineStatReportForm.setLineLength(lineLength);

				if(!bigPartnerIdTem.equals(bigPartnerId)){
					bigPartnerIdTem = bigPartnerId;
				}
				if(!regionTem.equals(regionTem)){
					regionTem = regionTem;
				}
				if(!cityTem.equals(cityValue)){
					cityTem = cityValue;
				}
				
				lineStatList.add(lineStatReportForm);

				rowMap.put(bigPartnerId+"_num", String.valueOf(StaticMethod.nullObject2int(rowMap.get(bigPartnerId+"_num"),0)+1));
				rowMap.put(bigPartnerId+"_"+regionValue+"_num", String.valueOf(StaticMethod.nullObject2int(rowMap.get(bigPartnerId+"_"+regionValue+"_num"),0)+1));
				rowMap.put(bigPartnerId+"_"+regionValue+"_"+cityValue+"_num", String.valueOf(StaticMethod.nullObject2int(rowMap.get(bigPartnerId+"_"+regionValue+"_"+cityValue+"_num"),0)+1));

			}

			
			
			
		}
		
		request.setAttribute("rowMap", rowMap);
		request.setAttribute("lineStatList", lineStatList);

		
/* 2010-3-31 wangjunfeng
 * 		for(int i=0;i<lineStatList.size();i++){
			lineStatReportForm = new LineStatReportForm();
			lineStatReportForm = (LineStatReportForm)lineStatList.get(i);
			
			//大合作伙伴
			bigPartnerValue = lineStatReportForm.getBigProvider();
			//小合作伙伴
			partnerValue =lineStatReportForm.getProvider();
			//地市ID
			regionValue =lineStatReportForm.getRegion();
			//县区ID
			cityValue =lineStatReportForm.getCity();
			//线路长度
			lineLength =lineStatReportForm.getLineLength();
		}

*/	
		
		
/*	原来的
 * 	String whereStr=" where 1=1 ";
		String timeStr=" ";
		IPnrStatMgr pnrStatMgr = (IPnrStatMgr) getBean("iPnrStatMgr");
		//统计条件
		if (partnerName!=null && !"".equals(partnerName)){
			whereStr += " and bs.partner= '"+ partnerName +"'" ;
		}
		if (region!=null && !"".equals(region)){
			whereStr += "  and bs.region= '"+ region +"'";
		}else{
		    for(int i = 0;regionList.size()>i;i++){
		    	TawSystemArea tawSystemArea = (TawSystemArea)regionList.get(i);
		    	if(i==0){
		    		whereStr+=" and ( bs.region = '";
		    	}else{
		    		whereStr+=" or bs.region = '";
		    	}
		    	whereStr+=tawSystemArea.getAreaid();
		    	whereStr+="' ";
		    	if(i==regionList.size()-1){
		    		whereStr+=")";
		    	}
		    }
		}
		
		
		//where time_new < to_date('2010-1-10 9:04:09','yyyy-MM-dd HH24:mi:ss') or time_new is null
		if(month!=0){
			String timeSelect=null;
			if(month==12){
				timeSelect = (year +1)+"-01-01" + " 00:00:00";
			}else{
				timeSelect = year+"-" + (month+1) + "-01" + " 00:00:00";
			}
			timeStr += " and time_new < to_date('"+ timeSelect +"','yyyy-MM-dd HH24:mi:ss') or time_new is null ";

		}
		
		List listLineStat = pnrStatMgr.getReportLineStat(whereStr,timeStr);
		
		String partnerNameStat =null;
		String regionStat =null;
		String cityStat = null;
		Map rowMap = new HashMap();
		
		for(int i = 0;listLineStat.size() >i;i++){
			Object[] lineObject =  (Object[])listLineStat.get(i);
			//合作伙伴名称
    		partnerNameStat = (String)lineObject[0];
    		//地市名称
    		regionStat = (String)lineObject[2];
    		//县区名称
    		cityStat = (String)lineObject[4];
    		//线路长度
    		//sunLineLengthStat = String.valueOf(lineObject[5]);

			rowMap.put(partnerNameStat+"_num", String.valueOf(StaticMethod.nullObject2int(rowMap.get(partnerNameStat+"_num"),0)+1));
			rowMap.put(partnerNameStat+"_"+regionStat+"_num", String.valueOf(StaticMethod.nullObject2int(rowMap.get(partnerNameStat+"_"+regionStat+"_num"),0)+1));
			rowMap.put(partnerNameStat+"_"+regionStat+"_"+cityStat+"_num", String.valueOf(StaticMethod.nullObject2int(rowMap.get(partnerNameStat+"_"+regionStat+"_"+cityStat+"_num"),0)+1));
		}
		request.setAttribute("rowMap", rowMap);
		request.setAttribute("listLineStat", listLineStat);*/
		return mapping.findForward("lineStatReport");
	}

	/**
	 * 线路 统计
	 * 统计条件：按地市、县区、线路等级统计。按月统计。   从当前月开始统计之前所有的信息
	 * 2010-01-11
	 * author:wangjunfeng
	 * 
	 */
	public ActionForward getLineStatReportByLineLevel(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//地市
		String region = StaticMethod.nullObject2String(request.getParameter("region"));
		//县区
		String city = StaticMethod.nullObject2String(request.getParameter("city"));
		//线路级别
		String grade = StaticMethod.nullObject2String(request.getParameter("grade"));
		//年份
		int year = StaticMethod.nullObject2int(request.getParameter("year"));
		//月份
		int month = StaticMethod.nullObject2int(request.getParameter("month"));
		
    	// 加载当前用户所属地市city
    	String userid = this.getUser(request).getUserid();
    	List regionList = PartnerCityByUser.getCityByUser(userid);

		String areaStr ="";		//region.areaid = '3901' and city.areaid='390101'
		String gradeStr=""; 	//and line.grade=1
		String timeStr ="";
		
		IPnrStatMgr pnrStatMgr = (IPnrStatMgr) getBean("iPnrStatMgr");
		//统计条件
		if (region!=null && !"".equals(region)){
			areaStr += "  region.areaid = '"+ region +"' ";
		}else{
		    for(int i = 0;regionList.size()>i;i++){
		    	TawSystemArea tawSystemArea = (TawSystemArea)regionList.get(i);
		    	if(i==0){
		    		areaStr+="  ( region.areaid = '";
		    	}else{
		    		areaStr+=" or region.areaid = '";
		    	}
		    	areaStr+=tawSystemArea.getAreaid();
		    	areaStr+="' ";
		    	if(i==regionList.size()-1){
		    		areaStr+=")";
		    	}
		    }
		}
	
//			else{
//			areaStr += "  region.parentareaid = '"+ parentareaid +"' ";			
//		}
		if (city!=null && !"".equals(city)){
			areaStr += " and city.areaid='"+ city +"'" ;
		}
		if (grade!=null && !"".equals(grade)){
			gradeStr += " and line.grade='"+ grade +"' " ;
		}
		if(month!=0){
			String timeSelect = year+"-" + (month+1) + "-01" + "-00:00:00";
			timeStr += " where time_new < to_date('"+ timeSelect +"','yyyy-MM-dd HH24:mi:ss') or time_new is null ";
			
		}
		
		
		List listLineStatByLineLevel = pnrStatMgr.getReportLineStatByLineLevel(areaStr,gradeStr,timeStr);
		String regionStat =null;
		String cityStat = null;
		String gradeStat =null;
		//String sunLineLengthStat =null;
		Map rowMap = new HashMap();
		for(int i = 0;listLineStatByLineLevel.size()>i;i++){
			Object[] lineObject =  (Object[])listLineStatByLineLevel.get(i);
    		//地市名称
    		regionStat = (String)lineObject[0];
    		//县区名称
    		cityStat = (String)lineObject[2];
			//线路等级
    		gradeStat = (String)lineObject[4];
    		//线路长度
    		//sunLineLengthStat = (String)lineObject[5];
			rowMap.put(regionStat+"_num", String.valueOf(StaticMethod.nullObject2int(rowMap.get(regionStat+"_num"),0)+1));
			rowMap.put(regionStat+"_"+cityStat+"_num", String.valueOf(StaticMethod.nullObject2int(rowMap.get(regionStat+"_"+cityStat+"_num"),0)+1));
			rowMap.put(regionStat+"_"+cityStat+"_"+gradeStat+"_num", String.valueOf(StaticMethod.nullObject2int(rowMap.get(regionStat+"_"+cityStat+"_"+gradeStat+"_num"),0)+1));
		}
		
		request.setAttribute("rowMap", rowMap);
		request.setAttribute("listLineStatByLineLevel", listLineStatByLineLevel);
		
		return mapping.findForward("lineStatReportByLineLevel");
	}

	/**
	 * 管理视图：合作伙伴市场份额
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchMark(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String stat = StaticMethod.null2String(request.getParameter("stat"));
		if("".equals(stat)){
			return mapping.findForward("searchMark");
		}
		
/*//		查看省的areaid
		AreaDeptTreeMgr areaDeptTreeMgr = (AreaDeptTreeMgr) getBean("areaDeptTreeMgr");	
		List list =  areaDeptTreeMgr.getInfoByCondition(" from AreaDeptTree areaDeptTree where node_type='province' ");
		String provinceNum = "";
		if(list.size()>0){
			AreaDeptTree areaDeptTree = (AreaDeptTree)list.get(0);
			provinceNum = areaDeptTree.getAreaId();
		}
*/		
		String whereStr = "";
		if("org.hibernate.dialect.InformixDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())){
			whereStr = "tree_areaid";
		} 
		else if("org.hibernate.dialect.OracleDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())){
			whereStr = "tree.areaid";
		}
    	// 加载当前用户所属地市city
    	String userid = this.getUser(request).getUserid();
    	List regionList = PartnerCityByUser.getCityByUser(userid);
    	
    	IPnrStatMgr pnrStatMgr = (IPnrStatMgr) getBean("iPnrStatMgr");
		String regionStr=" ";
	    for(int i = 0;regionList.size()>i;i++){
	    	TawSystemArea tawSystemArea = (TawSystemArea)regionList.get(i);
	    	if(i==0){
	    		regionStr+=" and (  " + whereStr + "= '";
	    	}else{
	    		regionStr+=" or " + whereStr + " = '";
	    	}
	    	regionStr+=tawSystemArea.getAreaid();
	    	regionStr+="' ";
	    	if(i==regionList.size()-1){
	    		regionStr+=")";
	    	}
	    }

		
		List markListTest = pnrStatMgr.getReportMarketStat(regionStr);
		
		
		List markList = new ArrayList();
		String regionStatTest ="";
		String regionStatTestTag ="";
		
		//spring 注入
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
		
		for(int i=0;markListTest.size()>i;i++){
			Object[] markTestObject = (Object[]) markListTest.get(i);
			regionStatTest=(String)markTestObject[1];
			if(regionStatTest!=null){
				if(regionStatTest.equals(province) ){

				}else{
					markList.add(markTestObject);
				}
			}
			regionStatTestTag = regionStatTest;
		}
		
	
		
		
		
		
		Map rowMap = new HashMap();
		for(int i = 0 ;i<markList.size();i++){
			Object[] mark = (Object[])markList.get(i);
			String pro = (String)mark[0];
			rowMap.put(pro+"_num", String.valueOf(StaticMethod.nullObject2int(rowMap.get(pro+"_num"),0)+1));
		}
		request.setAttribute("rowMap", rowMap);
		request.setAttribute("markList", markList);		
		return mapping.findForward("searchMark");
	}

	
	
	// ===================== 车辆统计 ====================== 
	/**
	 * 车辆统计 输入统计条件页面
	 * 统计条件：按合作伙伴名称和地市统计。按月统计。
	 * author:wangjunfeng
	 * 
	 */
	public ActionForward carCondition(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	   	/**
    	 * 加载当前用户所属地市
    	 */
    	//当前用户名
    	String userid = this.getUser(request).getUserid();
    	List CarListRegion = PartnerCityByUser.getCityByUser(userid);
    	
    	/**
    	 * 加载合作伙伴
    	 */
    	IPnrStatMgr pnrStatMgr = (IPnrStatMgr) getBean("iPnrStatMgr");
		StringBuffer provider_list = new StringBuffer();
		provider_list.append("," + "");
		provider_list.append("," + "--请选择合作伙伴--");
		List CarListPartner = pnrStatMgr.listLinePartner();
		for (int i = 0; i < CarListPartner.size(); i++) {
			String partner = CarListPartner.get(i).toString();
			provider_list.append("," + partner);
			provider_list.append("," + partner);
			
		}
		String providerBuffer = provider_list.toString();
		providerBuffer = providerBuffer.substring(1, providerBuffer.length());
		
		request.setAttribute("regionBuffer", CarListRegion);
		request.setAttribute("providerBuffer", providerBuffer);

		return mapping.findForward("carCondition");
	}
	

	/**
	 * 车辆 统计
	 * 统计条件：按所属地市和合作伙伴名称统计。按月统计。  从选择月开始统计之前所有的信息
	 * 2010-01-19
	 * author:wangjunfeng
	 * 
	 */
	public ActionForward getCarStatReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		//地市
		String region = StaticMethod.nullObject2String(request.getParameter("region"));
		//合作伙伴名称
		String partnerName = StaticMethod.nullObject2String(request.getParameter("provider"));
		//年份
		int year = StaticMethod.nullObject2int(request.getParameter("year"));
		//月份
		int month = StaticMethod.nullObject2int(request.getParameter("month"));
		
    	// 加载当前用户所属地市city
    	String userid = this.getUser(request).getUserid();
    	List regionList = PartnerCityByUser.getCityByUser(userid);

		String whereStr=" where 1=1  ";
		String timeStr = " ";
		IPnrStatMgr pnrStatMgr = (IPnrStatMgr) getBean("iPnrStatMgr");
		//统计条件
		if (region!=null && !"".equals(region)){
			whereStr += "  and bs.region= '"+ region +"' " ;
		}else{
		    for(int i = 0;regionList.size()>i;i++){
		    	TawSystemArea tawSystemArea = (TawSystemArea)regionList.get(i);
		    	if(i==0){
		    		whereStr+=" and ( bs.region = '";
		    	}else{
		    		whereStr+=" or bs.region = '";
		    	}
		    	whereStr+=tawSystemArea.getAreaid();
		    	whereStr+="' ";
		    	if(i==regionList.size()-1){
		    		whereStr+=")";
		    	}
		    }
		}
		

		
		
		if (partnerName!=null && !"".equals(partnerName)){
			whereStr += " and bs.partner= '"+ partnerName +"'" ;
		}
		//where time_new < to_date('2010-1-10 9:04:09','yyyy-MM-dd HH24:mi:ss') or time_new is null
		if(month!=0){
			String timeSelect="";
			if(month==12){
				timeSelect = (year+1) +"-01-01" + " 00:00:00";
			}else{
				timeSelect = year+"-" + (month+1) + "-01" + " 00:00:00";
			}
			
			timeStr += " and addtime< to_date('"+ timeSelect +"','yyyy-MM-dd HH24:mi:ss') ";
		}
		List listCarStat = pnrStatMgr.getReportCarStat(timeStr,whereStr);
		String partnerNameStat =null;
		String regionStat =null;
		String ypNumStat = null;
		String spNumStat =null;
		Map rowMap = new HashMap();
		for(int i = 0;listCarStat.size() >i;i++){
			Object[] carObject =  (Object[])listCarStat.get(i);
    		//地市名称
    		regionStat = (String)carObject[1];
			//合作伙伴名称
    		partnerNameStat = (String)carObject[2];
    		//车辆应配数量
    		//ypNumStat = (String)carObject[3];
    		//车里实配数量
    		//spNumStat = String.valueOf(carObject[4]);

			rowMap.put(regionStat+"_num", String.valueOf(StaticMethod.nullObject2int(rowMap.get(regionStat+"_num"),0)+1));
			rowMap.put(regionStat+"_"+partnerNameStat+"_num", String.valueOf(StaticMethod.nullObject2int(rowMap.get(regionStat+"_"+partnerNameStat+"_num"),0)+1));
		}
		request.setAttribute("rowMap", rowMap);
		request.setAttribute("listCarStat", listCarStat);
		return mapping.findForward("carStatReport");
	}

	
	
	
	/**
	 * 基站 统计
	 * 统计条件：按地市、县区、合作伙伴统计。按月统计。   从当前月开始统计之前所有的信息
	 * 2010-01-11
	 * author:benweiwei
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getSiteReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//地市
		String region = StaticMethod.nullObject2String(request.getParameter("region"));
		//县区
		String city = StaticMethod.nullObject2String(request.getParameter("city"));
		//合作伙伴
		String provider = StaticMethod.nullObject2String(request.getParameter("provider"));
		//年份
		int year = StaticMethod.nullObject2int(request.getParameter("year"));
		//月份
		int month = StaticMethod.nullObject2int(request.getParameter("month"));
		
	   	/**
    	 * 加载当前用户所属地市
    	 */
    	//当前用户名
    	String userid = this.getUser(request).getUserid();
    	List regionList = PartnerCityByUser.getCityByUser(userid);

		String areaStr ="";		
		String providerStr=""; 
		String cityStr = "";
		Date timeEnd =null;
		IPnrStatMgr pnrStatMgr = (IPnrStatMgr) getBean("iPnrStatMgr");
		//统计条件
		if (region!=null && !"".equals(region)){
			areaStr = " and bs.region = '"+ region +"' ";
		}else{
		    for(int i = 0;regionList.size()>i;i++){
		    	TawSystemArea tawSystemArea = (TawSystemArea)regionList.get(i);
		    	if(i==0){
		    		areaStr+=" and ( bs.region = '";
		    	}else{
		    		areaStr+=" or bs.region = '";
		    	}
		    	areaStr+=tawSystemArea.getAreaid();
		    	areaStr+="' ";
		    	if(i==regionList.size()-1){
		    		areaStr+=")";
		    	}
		    }
		}
		if (city!=null && !"".equals(city)){
			cityStr = " and bs.city='"+ city +"'" ;
		}
		if (provider!=null && !"".equals(provider)){
			providerStr = " and partner='"+ provider +"' " ;
		}
		if(month!=0){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			if(month!=12){
				month=month+1;
			}else{
				year=year+1;
				month=1;
			}
			String timeSelect = year+"-"+ (month);
			Date date = sdf.parse(timeSelect);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.set(Calendar.DATE, 1);
			cal.set(Calendar.HOUR, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			timeEnd = cal.getTime();
		}
		
//		查看省的areaid
		AreaDeptTreeMgr areaDeptTreeMgr = (AreaDeptTreeMgr) getBean("areaDeptTreeMgr");	
		List list =  areaDeptTreeMgr.getInfoByCondition(" from AreaDeptTree areaDeptTree where node_type='province' ");
		String provinceNum = "";
		if(list.size()>0){
			AreaDeptTree areaDeptTree = (AreaDeptTree)list.get(0);
			provinceNum = areaDeptTree.getAreaId();
		}
		
		List listSiteStat = pnrStatMgr.getReportSiteStat(areaStr, cityStr, providerStr, timeEnd,provinceNum);
		String regionStat ="";
		String providerStat = "";
//		String cityStat ="";
		//String sunLineLengthStat =null;
		Map rowMap = new HashMap();
		for(int i = 0;listSiteStat.size()>i;i++){
			Object[] lineObject =  (Object[])listSiteStat.get(i);
    		//合作伙伴名称
			providerStat = (String)lineObject[0];
    		//地市名称
			regionStat = (String)lineObject[1];
			
//			cityStat = (String)lineObject[2];
			rowMap.put(providerStat+"_num", String.valueOf(StaticMethod.nullObject2int(rowMap.get(providerStat+"_num"),0)+1));
			rowMap.put(providerStat+"_"+regionStat+"_num", String.valueOf(StaticMethod.nullObject2int(rowMap.get(providerStat+"_"+regionStat+"_num"),0)+1));
		}
		
		request.setAttribute("rowMap", rowMap);
		request.setAttribute("listSiteStat", listSiteStat);

		
    	/**
    	 * 加载合作伙伴
    	 */
		StringBuffer provider_list = new StringBuffer();
		provider_list.append("," + "");
		provider_list.append("," + "--请选择合作伙伴--");
		List LineListPartner = pnrStatMgr.listLinePartner();
		for (int i = 0; i < LineListPartner.size(); i++) {
			String partner = LineListPartner.get(i).toString();
			provider_list.append("," + partner);
			provider_list.append("," + partner);
			
		}
		String providerBuffer = provider_list.toString();
		providerBuffer = providerBuffer.substring(1, providerBuffer.length());
		
		request.setAttribute("regionBuffer", regionList);
		request.setAttribute("providerBuffer", providerBuffer);
		return mapping.findForward("listSiteStat");
	}

	/**
	 * 基站统计 输入统计条件页面
	 * 统计条件：按地市、县区、合作伙伴统计。按月统计。   从当前月开始统计之前所有的信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward siteCondition(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	   	/**
    	 * 加载当前用户所属地市
    	 */
    	//当前用户名
    	String userid = this.getUser(request).getUserid();
    	List LineListRegion = PartnerCityByUser.getCityByUser(userid);
    			
    	/**
    	 * 加载合作伙伴
    	 */
    	IPnrStatMgr pnrStatMgr = (IPnrStatMgr) getBean("iPnrStatMgr");
		StringBuffer provider_list = new StringBuffer();
		provider_list.append("," + "");
		provider_list.append("," + "--请选择合作伙伴--");
		List LineListPartner = pnrStatMgr.listLinePartner();
		for (int i = 0; i < LineListPartner.size(); i++) {
			String partner = LineListPartner.get(i).toString();
			provider_list.append("," + partner);
			provider_list.append("," + partner);
			
		}
		String providerBuffer = provider_list.toString();
		providerBuffer = providerBuffer.substring(1, providerBuffer.length());
		
		request.setAttribute("regionBuffer", LineListRegion);
		request.setAttribute("providerBuffer", providerBuffer);

		return mapping.findForward("listSiteStat");
	}
	/**
	 * 基站统计 输入统计条件页面
	 * 统计条件：按地市、基站等级统计。按月统计。   从当前月开始统计之前所有的信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward siteConditionBylevel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	   	/**
    	 * 加载当前用户所属地市
    	 */
    	//当前用户名
    	String userid = this.getUser(request).getUserid();
    	List LineListRegion = PartnerCityByUser.getCityByUser(userid);
		request.setAttribute("regionBuffer", LineListRegion);

		return mapping.findForward("listSiteStat");
	}
	/**
	 * 基站 统计
	 * 统计条件：按地市、基站等级统计。
	 * 2010-01-11
	 * author:benweiwei
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getSiteReportBylevel(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//地市
		String region = StaticMethod.nullObject2String(request.getParameter("region"));
		//等级
		String level = StaticMethod.nullObject2String(request.getParameter("siteLevle"));
		
		String first = StaticMethod.nullObject2String(request.getParameter("first"));
	   	/**
    	 * 加载当前用户所属地市
    	 */
    	//当前用户名

    	String userid = this.getUser(request).getUserid();
    	List LineListRegion = PartnerCityByUser.getCityByUser(userid);

    	request.setAttribute("regionBuffer", LineListRegion);
    	
    	if("".equals(first)){
    		return mapping.findForward("listSiteStatBylevel");
    	}

		String areaStr ="";		
		String levelStr=""; 
		Date timeEnd =null;
		IPnrStatMgr pnrStatMgr = (IPnrStatMgr) getBean("iPnrStatMgr");
		//统计条件
		if (region!=null && !"".equals(region)){
			areaStr = " and areaid = '"+ region +"' ";
		}else{
		    for(int i = 0;LineListRegion.size()>i;i++){
		    	TawSystemArea tawSystemArea = (TawSystemArea)LineListRegion.get(i);
		    	if(i==0){
		    		areaStr+=" and ( areaid = '";
		    	}else{
		    		areaStr+=" or areaid = '";
		    	}
		    	areaStr+=tawSystemArea.getAreaid();
		    	areaStr+="' ";
		    	if(i==LineListRegion.size()-1){
		    		areaStr+=")";
		    	}
		    }
		}
		if (level!=null && !"".equals(level)){
			levelStr = " and site_levle='"+ level +"'" ;
		}
//		if(month!=0){
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
//			String timeSelect = year+"-"+ (month);
//			Date date = sdf.parse(timeSelect);
//			Calendar cal = Calendar.getInstance();
//			cal.setTime(date);
//			cal.set(Calendar.DATE, 1);
//			cal.set(Calendar.HOUR, 0);
//			cal.set(Calendar.MINUTE, 0);
//			cal.set(Calendar.SECOND, 0);
//			timeEnd = cal.getTime();
//		}
//		
//		
//		查看省的areaid
		AreaDeptTreeMgr areaDeptTreeMgr = (AreaDeptTreeMgr) getBean("areaDeptTreeMgr");	
		List list =  areaDeptTreeMgr.getInfoByCondition(" from AreaDeptTree areaDeptTree where node_type='province' ");
		String provinceNum = "";
		if(list.size()>0){
			AreaDeptTree areaDeptTree = (AreaDeptTree)list.get(0);
			provinceNum = areaDeptTree.getAreaId();
		}
		List listSiteStat = pnrStatMgr.getReportSiteStatBylevel(areaStr, levelStr, timeEnd,provinceNum);
		String regionStat ="";
		Map rowMap = new HashMap();
		for(int i = 0;listSiteStat.size()>i;i++){
			Object[] lineObject =  (Object[])listSiteStat.get(i);
    		//合作伙伴名称
			regionStat = (String)lineObject[0];
    		//地市名称
			rowMap.put(regionStat+"_num", String.valueOf(StaticMethod.nullObject2int(rowMap.get(regionStat+"_num"),0)+1));
		}
		request.setAttribute("rowMap", rowMap);
		request.setAttribute("listSiteStat", listSiteStat);

    	
		return mapping.findForward("listSiteStatBylevel");
	}


	/**
	 * 分页显示站点信息管理列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchSite(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				SiteConstants.SITE_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		
		PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr) getBean("partnerDeptMgr");
		AreaDeptTreeMgr areaDeptTreeMgr = (AreaDeptTreeMgr) getBean("areaDeptTreeMgr");		
		
		StringBuffer where = new StringBuffer();
		where.append(" where isDel = '0' ");
		String region = StaticMethod.null2String(request.getParameter("region"));
		String county = StaticMethod.null2String(request.getParameter("city"));
		String provider = StaticMethod.null2String(request.getParameter("provider"));
		
		if(!"".equals(region)){
			where.append(" and region ='");
			where.append(region);
			where.append("'");
		}
				
		if(!"".equals(county)){
			where.append(" and city ='");
			where.append(county);
			where.append("'");
		}		
		if(!"".equals(provider)){
//			查询该合作伙伴的所有下级维护的公司
//			String pro = provider.substring(0, provider.length()-2);
//			List list = partnerDeptMgr.getPartnerDepts(" and name like '"+pro+"%' ");
			StringBuffer pros = new StringBuffer();
//			if(list.size()>0){
//				PartnerDept partnerDept = (PartnerDept)list.get(0);
//				String interString = partnerDept.getInterfaceHeadId();
				List areaDeptTreelist = areaDeptTreeMgr.getInfoByCondition(" from AreaDeptTree areaDeptTree where interfaceHeadId = '"+provider+"' and node_type='factory'");
				for(int i =0; areaDeptTreelist.size()>i;i++){
					AreaDeptTree areaDeptTree = (AreaDeptTree)areaDeptTreelist.get(i);
					pros.append("'");
					pros.append(areaDeptTree.getNodeName());
					pros.append("'");
					if(i!=areaDeptTreelist.size()-1){
						pros.append(",");
					}
				}
//			}
			if(!"".equals(pros.toString())){
				where.append(" and provider in (");
				where.append(pros.toString());
				where.append(")");
			}
		}
		ISiteMgr siteMgr = (ISiteMgr) getBean("iSiteMgr");
		Map map = (Map) siteMgr.getSites(pageIndex, pageSize, where.toString());
		List list = (List) map.get("result");
		request.setAttribute(SiteConstants.SITE_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("listSite");
	}


	
	

	// ===================== 仪器仪表统计 ====================== 
	/**
	 * 仪器仪表统计 输入统计条件页面
	 * 统计条件：地市名称和合作伙伴名称和仪器仪表类型。按月统计。
	 * 2010-01-19
	 * author:wangjunfeng
	 * 
	 */
	public ActionForward apparatusNumCondition(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	   	/**
    	 * 加载当前用户所属地市
    	 */
    	//当前用户名
    	String userid = this.getUser(request).getUserid();
    	List ApparatusListRegion = PartnerCityByUser.getCityByUser(userid);
		
    	/**
    	 * 加载合作伙伴
    	 */
    	IPnrStatMgr pnrStatMgr = (IPnrStatMgr) getBean("iPnrStatMgr");
		StringBuffer provider_list = new StringBuffer();
		provider_list.append("," + "");
		provider_list.append("," + "--请选择合作伙伴--");
		List ApparatusListPartner = pnrStatMgr.listLinePartner();
		for (int i = 0; i < ApparatusListPartner.size(); i++) {
			String partner = ApparatusListPartner.get(i).toString();
			provider_list.append("," + partner);
			provider_list.append("," + partner);
			
		}
		String providerBuffer = provider_list.toString();
		providerBuffer = providerBuffer.substring(1, providerBuffer.length());
		
		request.setAttribute("regionBuffer", ApparatusListRegion);
		request.setAttribute("providerBuffer", providerBuffer);

		return mapping.findForward("apparatusNumCondition");
	}
	
	/**
	 * 仪器仪表统计  统计结果
	 * 统计条件：地市名称和合作伙伴名称和仪器仪表类型。按月统计。
	 * 2010-01-19
	 * author:wangjunfeng
	 * 
	 */
	public ActionForward getApparatusNumStatReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	//当前用户名
    	String userid = this.getUser(request).getUserid();
    	List regionList = PartnerCityByUser.getCityByUser(userid);
//    	TawSystemArea ua = (TawSystemArea)LineListRegion.get(0);
//		String parentareaid = ua.getParentAreaid();

		//地市
		String region = StaticMethod.nullObject2String(request.getParameter("region"));
		//合作伙伴名称
		String partnerName = StaticMethod.nullObject2String(request.getParameter("provider"));
		//仪器仪表类型
		//String apparatusType = StaticMethod.nullObject2String(request.getParameter("type"));
		
		
		//仪器仪表名称
		String apparatusName = StaticMethod.nullObject2String(request.getParameter("apparatusName"));
		
		
		//年份
		int year = StaticMethod.nullObject2int(request.getParameter("year"));
		//月份
		int month = StaticMethod.nullObject2int(request.getParameter("month"));
    	

		String whereStr=" where 1=1  ";	
		String timeStr = " ";
		IPnrStatMgr pnrStatMgr = (IPnrStatMgr) getBean("iPnrStatMgr");
		//统计条件
		if (region!=null && !"".equals(region)){
			whereStr += "  and region= '"+ region +"' " ;
		}else{
		    for(int i = 0;regionList.size()>i;i++){
		    	TawSystemArea tawSystemArea = (TawSystemArea)regionList.get(i);
		    	if(i==0){
		    		whereStr+=" and ( region = '";
		    	}else{
		    		whereStr+=" or region = '";
		    	}
		    	whereStr+=tawSystemArea.getAreaid();
		    	whereStr+="' ";
		    	if(i==regionList.size()-1){
		    		whereStr+=")";
		    	}
		    }
		}
		


		if (partnerName!=null && !"".equals(partnerName)){
			whereStr += " and partner= '"+ partnerName +"'" ;
		}
/*		if(apparatusType !=null && !"".equals(apparatusType)){
			whereStr +="  and type='"+ apparatusType +"'";
		}
*/		
		if(apparatusName !=null && !"".equals(apparatusName)){
			whereStr +="  and apparatusName like '%"+ apparatusName +"%'";
		}

		//where time_new < to_date('2010-1-10 9:04:09','yyyy-MM-dd HH24:mi:ss') or time_new is null
		if(month!=0){
			String timeSelect="";
			if(month==12){
				timeSelect = (year+1) +"-01-01" + " 00:00:00";
			}else{
				timeSelect = year+"-" + (month+1) + "-01" + " 00:00:00";
			}
			timeStr += " and addtime< to_date('"+ timeSelect +"','yyyy-MM-dd HH24:mi:ss') ";
		}
		
		
		List listApparatusStatTest = pnrStatMgr.getReportApparatusStat(whereStr,timeStr);
		
		List listApparatusStat = new ArrayList();
		String  regionStatTest="";
		String partnerNameTest="";
		String apparatusNameTest ="";
		String regionStatTestTag ="";
		String partnerNameTestTag="";
		
		for(int i=0;listApparatusStatTest.size() > i;i++){
			Object[] apparatusTestObject = (Object[])listApparatusStatTest.get(i);
			 regionStatTest = (String) apparatusTestObject[0];
			 partnerNameTest = (String) apparatusTestObject[2];
			 apparatusNameTest =(String) apparatusTestObject[3];
			if(apparatusNameTest==null){
				if(regionStatTest.equals(regionStatTestTag) && partnerNameTest.equals(partnerNameTestTag)){
					
				}else{
					listApparatusStat.add(apparatusTestObject);
				}
			}else{
				listApparatusStat.add(apparatusTestObject);
			}
			regionStatTestTag=regionStatTest;
			partnerNameTestTag=partnerNameTest;
			
		}

		
		
		String partnerNameStat =null;
		String regionStat =null;
		String ypNumStat = null;
		String spNumStat =null;
		Map rowMap = new HashMap();
		for(int i = 0;listApparatusStat.size() >i;i++){
			Object[] apparatusObject =  (Object[])listApparatusStat.get(i);
    		//地市名称
    		regionStat = (String)apparatusObject[1];
			//合作伙伴名称
    		partnerNameStat = (String)apparatusObject[2];
    		

			rowMap.put(regionStat+"_num", String.valueOf(StaticMethod.nullObject2int(rowMap.get(regionStat+"_num"),0)+1));
			rowMap.put(regionStat+"_"+partnerNameStat+"_num", String.valueOf(StaticMethod.nullObject2int(rowMap.get(regionStat+"_"+partnerNameStat+"_num"),0)+1));
		}
		request.setAttribute("rowMap", rowMap);
		request.setAttribute("listApparatusStat", listApparatusStat);
		return mapping.findForward("apparatusNumStatReport");
	}
	
	
	/**
	 * 仪器仪表统计 输入统计条件页面
	 * 统计条件：合作伙伴名称和仪器仪表类型和运行状态。按月统计。
	 * 2010-01-19
	 * author:wangjunfeng
	 * 
	 */
	public ActionForward apparatusStatCondition(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		
		/**
    	 * 加载合作伙伴
    	 */
    	IPnrStatMgr pnrStatMgr = (IPnrStatMgr) getBean("iPnrStatMgr");
		StringBuffer provider_list = new StringBuffer();
		provider_list.append("," + "");
		provider_list.append("," + "--请选择合作伙伴--");
		List ApparatusListPartner = pnrStatMgr.listLinePartner();
		for (int i = 0; i < ApparatusListPartner.size(); i++) {
			String partner = ApparatusListPartner.get(i).toString();
			provider_list.append("," + partner);
			provider_list.append("," + partner);
			
		}
		String providerBuffer = provider_list.toString();
		providerBuffer = providerBuffer.substring(1, providerBuffer.length());
		
		request.setAttribute("providerBuffer", providerBuffer);

		return mapping.findForward("apparatusStatCondition");
	}
	
	/**
	 * 仪器仪表统计  统计结果
	 * 统计条件：合作伙伴名称和仪器仪表类型和运行状态。按月统计。
	 * author:wangjunfeng
	 * 
	 */
	public ActionForward getApparatusStatReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
 		
		//合作伙伴名称
		String partnerName = StaticMethod.nullObject2String(request.getParameter("provider"));
		//仪器仪表类型
		//String apparatusType = StaticMethod.nullObject2String(request.getParameter("type"));
		
		//仪器仪表名称
		String apparatusName = StaticMethod.nullObject2String(request.getParameter("apparatusName"));
		
		//仪器仪表状态
		String apparatusState = StaticMethod.nullObject2String(request.getParameter("state"));
		//年份
		int year = StaticMethod.nullObject2int(request.getParameter("year"));
		//月份
		int month = StaticMethod.nullObject2int(request.getParameter("month"));
		
    	// 加载当前用户所属地市city
    	String userid = this.getUser(request).getUserid();
    	List regionList = PartnerCityByUser.getCityByUser(userid);

		String partnerStr=" where 1=1  ";
		String whereStr=" ";
		String regionStr=" ";
	    for(int i = 0;regionList.size()>i;i++){
	    	TawSystemArea tawSystemArea = (TawSystemArea)regionList.get(i);
	    	if(i==0){
	    		regionStr+=" and (  tree.areaid= '";
	    	}else{
	    		regionStr+=" or tree.areaid = '";
	    	}
	    	regionStr+=tawSystemArea.getAreaid();
	    	regionStr+="' ";
	    	if(i==regionList.size()-1){
	    		regionStr+=")";
	    	}
	    }

		IPnrStatMgr pnrStatMgr = (IPnrStatMgr) getBean("iPnrStatMgr");
		//统计条件
		if (partnerName!=null && !"".equals(partnerName)){
			partnerStr += " and partner= '"+ partnerName +"'" ;
		}
		if(apparatusName !=null && !"".equals(apparatusName)){
			whereStr +="  and apparatusName like '%"+ apparatusName +"%'";
		}
		if (apparatusState!=null && !"".equals(apparatusState)){
			whereStr += "  and state= '"+ apparatusState +"' " ;
		}
		

		if(month!=0){
			String timeSelect="";
			if(month==12){
				timeSelect = (year+1) +"-01-01" + " 00:00:00";
			}else{
				timeSelect = year+"-" + (month+1) + "-01" + " 00:00:00";
			}
			whereStr += " and addtime< to_date('"+ timeSelect +"','yyyy-MM-dd HH24:mi:ss') ";
		}

		List listApparatusStatTest = pnrStatMgr.getReportApparatusPartnerStat(partnerStr,whereStr,regionStr);
		
		List listApparatusStat = new ArrayList();
		String partnerNameTest ="";
		//String appartusTypeTest="";
		String apparatusNameTest=""; 
		String partnerNameTestTag="";
		for(int i=0;listApparatusStatTest.size() > i;i++){
			Object[] apparatusTestObject = (Object[])listApparatusStatTest.get(i);
			partnerNameTest = (String) apparatusTestObject[0];
			apparatusNameTest = (String) apparatusTestObject[1];
			if(apparatusNameTest==null){
				if(partnerNameTest.equals(partnerNameTestTag)){
					
				}else{
					listApparatusStat.add(apparatusTestObject);
				}
			}else{
				listApparatusStat.add(apparatusTestObject);
			}
			partnerNameTestTag=partnerNameTest;
		}
		
		
		
		String partnerNameStat =null;
		String apparatusNameStat =null;
		Map rowMap = new HashMap();
		for(int i = 0;listApparatusStat.size() >i;i++){
			Object[] apparatusObject =  (Object[])listApparatusStat.get(i);
			//合作伙伴名称
    		partnerNameStat = (String)apparatusObject[0];
    		//仪器仪表类型
    		//apparatusTypeStat = String.valueOf(apparatusObject[1]);
    		//仪器仪表名称
    		apparatusNameStat = String.valueOf(apparatusObject[1]);
//    		//运行状态
//    		apparatusStateStat = String.valueOf(apparatusObject[3]);
//    		//仪器仪表数量
//    		numStat = String.valueOf(apparatusObject[4]);
			
    		rowMap.put(partnerNameStat+"_num", String.valueOf(StaticMethod.nullObject2int(rowMap.get(partnerNameStat+"_num"),0)+1));
			rowMap.put(partnerNameStat+"_"+apparatusNameStat+"_num", String.valueOf(StaticMethod.nullObject2int(rowMap.get(partnerNameStat+"_"+apparatusNameStat+"_num"),0)+1));
		}
		request.setAttribute("rowMap", rowMap);
		request.setAttribute("listApparatusStat", listApparatusStat);
		return mapping.findForward("apparatusStatReport");
	}
	
	/**
	 * 人员 统计
	 * 统计条件：按地市、维护专业、合作伙伴统计。按月统计。   从当前月开始统计之前所有的信息
	 * 2010-01-11
	 * author:benweiwei
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getPersonReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//地市
		String region = StaticMethod.nullObject2String(request.getParameter("region"));
		//合作伙伴
		String provider = StaticMethod.nullObject2String(request.getParameter("provider"));
		//年份
		int year = StaticMethod.nullObject2int(request.getParameter("year"));
		//月份
		int month = StaticMethod.nullObject2int(request.getParameter("month"));
		
		String serviceProfession = StaticMethod.nullObject2String(request.getParameter("serviceProfession"));
		
		String first = StaticMethod.nullObject2String(request.getParameter("first"));
	   	/**
    	 * 加载当前用户所属地市
    	 */
    	//当前用户名
    	String userid = this.getUser(request).getUserid();
    	List LineListRegion = PartnerCityByUser.getCityByUser(userid);
    	
		IPnrStatMgr pnrStatMgr = (IPnrStatMgr) getBean("iPnrStatMgr");
    	/**
    	 * 加载合作伙伴
    	 */
		StringBuffer provider_list = new StringBuffer();
		provider_list.append("," + "");
		provider_list.append("," + "--请选择合作伙伴--");
		List LineListPartner = pnrStatMgr.listLinePartner();
		for (int i = 0; i < LineListPartner.size(); i++) {
			String partner = LineListPartner.get(i).toString();
			provider_list.append("," + partner);
			provider_list.append("," + partner);
			
		}
		String providerBuffer = provider_list.toString();
		providerBuffer = providerBuffer.substring(1, providerBuffer.length());
		
		request.setAttribute("regionBuffer", LineListRegion);
		request.setAttribute("providerBuffer", providerBuffer);
		
		if("".equals(first)){
			return mapping.findForward("listPersonStat");
		}

		String cityStr ="";		
		String providerStr=""; 
		String serviceProfessionStr = "";
		Date timeEnd =null;

//		//统计条件
		if (region!=null && !"".equals(region)){
			cityStr = " '"+ region +"' ";
		}else{
		    for(int i = 0;LineListRegion.size()>i;i++){
		    	TawSystemArea tawSystemArea = (TawSystemArea)LineListRegion.get(i);
		    	if(i!=0){
		    		cityStr = cityStr +",";
		    	}
		    	cityStr += " '"+ tawSystemArea.getAreaid() +"' ";
		    }
		}		
		String endTime="";
		if(year!=0&&month!=0){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			String timeSelect = year+"-"+ (month);
			Date date = sdf.parse(timeSelect);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.MONTH, 1);
			endTime = sdf.format(cal.getTime());
		}
		
		String keyTemp = ""; 
        Map subPartnerNumMap = new HashMap();
        Map partnerNumMap = new HashMap();
        List lastPartnerList = new ArrayList();
		String key ="";
        String subKey="";
		Object[] object =  null;
		AreaDeptTree tree = null;
		PartnerDept partner = null;		
		//得到各地市的合作伙伴
		List partnerList = pnrStatMgr.getPartnersByTimeAndCity(endTime, cityStr);
        for(int i=0;i<partnerList.size();i++){
			object =  (Object[])partnerList.get(i);
			tree = (AreaDeptTree)object[0];
			partner = (PartnerDept)object[1];
			if(!provider.equals("")){
				if(partner.getName().equals(provider)){
					subKey = tree.getAreaId()+"_"+tree.getInterfaceHeadId()+"_num";
					key = tree.getAreaId()+"_num";
					subPartnerNumMap.put(subKey, String.valueOf(StaticMethod.nullObject2int(subPartnerNumMap.get(subKey), 0)+1));
					if(!keyTemp.equals(subKey)){
						lastPartnerList.add(object);
						partnerNumMap.put(key, String.valueOf(StaticMethod.nullObject2int(partnerNumMap.get(key), 0)+1));
						keyTemp = subKey;
					}
				}
			} else {
				subKey = tree.getAreaId()+"_"+tree.getInterfaceHeadId()+"_num";
				key = tree.getAreaId()+"_num";
				subPartnerNumMap.put(subKey, String.valueOf(StaticMethod.nullObject2int(subPartnerNumMap.get(subKey), 0)+1));
				if(!keyTemp.equals(subKey)){
					lastPartnerList.add(object);
					partnerNumMap.put(key, String.valueOf(StaticMethod.nullObject2int(partnerNumMap.get(key), 0)+1));
					keyTemp = subKey;
				}
			}
        }
		
        //得到应配数
        PersonConfig personConfig = null;
	    List personList = pnrStatMgr.getPersonConfig(cityStr);
	    Map userConfMap = new HashMap();
	    for(int i=0;i<personList.size();i++){
			object =  (Object[])personList.get(i);
			tree = (AreaDeptTree)object[0];
			personConfig = (PersonConfig)object[1];
			key = tree.getAreaId()+"_"+tree.getInterfaceHeadId()+"_"+personConfig.getServiceProfessional()+"_confnum";
			userConfMap.put(key, StaticMethod.nullObject2int(userConfMap.get(key), 0)+personConfig.getDisposeNo());
	    }        
	    
        //得到实配数
	    List partnerUserList = pnrStatMgr.getPersonByPartner(endTime,cityStr);
	    PartnerUser partnerUser = null;
	    for(int i=0;i<partnerUserList.size();i++){
			object =  (Object[])partnerUserList.get(i);
			tree = (AreaDeptTree)object[0];
			partnerUser = (PartnerUser)object[1];
			key = tree.getAreaId()+"_"+tree.getInterfaceHeadId()+"_sumnum";
			subKey = tree.getAreaId()+"_"+tree.getInterfaceHeadId()+"_"+partnerUser.getServiceProfessional()+"_num";
			userConfMap.put(subKey, String.valueOf(StaticMethod.nullObject2int(userConfMap.get(subKey), 0)+1));
			userConfMap.put(key, String.valueOf(StaticMethod.nullObject2int(userConfMap.get(key), 0)+1));
	    }	    
	    
        ITawSystemDictTypeManager tawSystemDictTypeManager = (ITawSystemDictTypeManager)getBean("ItawSystemDictTypeManager");
        List  professionList = new ArrayList();
        if(!serviceProfession.equals("")){
        	professionList.add(tawSystemDictTypeManager.getDictTypeByDictid(serviceProfession));
        } else {
        	professionList = tawSystemDictTypeManager.getDictSonsByDictid("1121201");
        }
        
        TawSystemDictType tawSystemDictType = null;
        List statFormList = new ArrayList();
        BaseinfoStatForm statForm = null; 
        
		for(int i=0;i<lastPartnerList.size();i++){	
			object =  (Object[])lastPartnerList.get(i);
			tree = (AreaDeptTree)object[0];
			partner = (PartnerDept)object[1];
			for(int j=0;j<professionList.size();j++){
				statForm = new BaseinfoStatForm();
				tawSystemDictType = (TawSystemDictType)professionList.get(j);
				key = tree.getAreaId()+"_"+tree.getInterfaceHeadId()+"_"+StaticMethod.nullObject2String(tawSystemDictType.getDictId());
				statForm.setAreaId(tree.getAreaId());
				statForm.setPartnerId(tree.getInterfaceHeadId());
				statForm.setPartnerName(partner.getName());
				statForm.setPartnerNum(StaticMethod.nullObject2String(partnerNumMap.get(key), "0"));
				statForm.setProfessionId(StaticMethod.nullObject2String(tawSystemDictType.getDictId()));
				statForm.setProfessionName(StaticMethod.nullObject2String(tawSystemDictType.getDictName()));
				statForm.setUserNum(StaticMethod.nullObject2String(userConfMap.get(key+"_num"), "0"));
				statForm.setUserConfig(StaticMethod.nullObject2String(userConfMap.get(key+"_confnum"), "0"));
				statFormList.add(statForm);
//				System.out.println(itemXML.getItemName()+"_"+itemXML.getItemId());
			}    
		}
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("statFormList", statFormList);
		request.setAttribute("partnerNumMap", partnerNumMap);
		request.setAttribute("subPartnerNumMap", subPartnerNumMap);
		request.setAttribute("professionSize", String.valueOf(professionList.size()));
		return mapping.findForward("listPersonStat");
	}
	


	/**
	 * 人员 统计 
	 * 统计条件：合作伙伴统计。按月统计。   从当前月开始统计之前所有的信息
	 * 2010-01-11
	 * author:benweiwei
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getPersonReportByPro(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		
		//合作伙伴
		String provider = StaticMethod.nullObject2String(request.getParameter("provider"));
		//年份
		int year = StaticMethod.nullObject2int(request.getParameter("year"));
		//月份
		int month = StaticMethod.nullObject2int(request.getParameter("month"));
		
		String first = StaticMethod.nullObject2String(request.getParameter("first"));
	   	/**
    	 * 加载当前用户所属地市
    	 */
		IPnrStatMgr pnrStatMgr = (IPnrStatMgr) getBean("iPnrStatMgr");
		
		
    	/**
    	 * 加载合作伙伴
    	 */
		StringBuffer provider_list = new StringBuffer();
		provider_list.append("," + "");
		provider_list.append("," + "--请选择合作伙伴--");
		List LineListPartner = pnrStatMgr.listLinePartner();
		for (int i = 0; i < LineListPartner.size(); i++) {
			String partner = LineListPartner.get(i).toString();
			provider_list.append("," + partner);
			provider_list.append("," + partner);
			
		}
		String providerBuffer = provider_list.toString();
		providerBuffer = providerBuffer.substring(1, providerBuffer.length());
		
		request.setAttribute("providerBuffer", providerBuffer);
		
		if("".equals(first)){
			return mapping.findForward("listPersonStatByPro");
		}

		String providerStr=""; 
		Date timeEnd =null;

		if (provider!=null && !"".equals(provider)){
			providerStr = " where name='"+ provider +"' " ;
		}
		if(month!=0){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			if(month!=12){
				month=month+1;
			}else{
				year=year+1;
				month=1;
			}
			String timeSelect = year+"-"+ (month);
			Date date = sdf.parse(timeSelect);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.set(Calendar.DATE, 1);
			cal.set(Calendar.HOUR, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			timeEnd = cal.getTime();
		}
 
		// 加载当前用户所属地市city
    	String userid = this.getUser(request).getUserid();
    	List regionList = PartnerCityByUser.getCityByUser(userid);
		
		String regionStr=" ";
	    for(int i = 0;regionList.size()>i;i++){
	    	TawSystemArea tawSystemArea = (TawSystemArea)regionList.get(i);
	    	if(i==0){
	    		regionStr+=" and (  tree.areaid= '";
	    	}else{
	    		regionStr+=" or tree.areaid = '";
	    	}
	    	regionStr+=tawSystemArea.getAreaid();
	    	regionStr+="' ";
	    	if(i==regionList.size()-1){
	    		regionStr+=")";
	    	}
	    }

		
		List listPersonStat = pnrStatMgr.getReportPersonStatByPor("", "", providerStr, timeEnd, "",regionStr);
		String providerStat = "";
		//String sunLineLengthStat =null;
		Map rowMap = new HashMap();
		for(int i = 0;listPersonStat.size()>i;i++){
			Object[] lineObject =  (Object[])listPersonStat.get(i);
    		//合作伙伴名称
    		//地市名称
			providerStat = (String)lineObject[0];
			
			rowMap.put(providerStat+"_num", String.valueOf(StaticMethod.nullObject2int(rowMap.get(providerStat+"_num"),0)+1));
		}
		
		request.setAttribute("rowMap", rowMap);
		request.setAttribute("listPersonStat", listPersonStat);

		return mapping.findForward("listPersonStatByPro");
	}	
	
	
	/**
	 * 合作伙伴信息统计 输入统计条件页面
	 * author:daizhigang
	 */
	public ActionForward baseinfoByCtiy(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	   	/**
    	 * 加载当前用户所属地市
    	 */
    	//当前用户名
    	String userid = this.getUser(request).getUserid();
    	List LineListRegion = PartnerCityByUser.getCityByUser(userid);
		request.setAttribute("regionBuffer", LineListRegion);
		return mapping.findForward("baseinfoByCtiy");
	}
	/**
	 * 合作伙伴信息统计
	 * author:daizhigang
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getBaseinfoByCtiy(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	// 加载当前用户所属地市city
    	String userid = this.getUser(request).getUserid();
    	List regionList = PartnerCityByUser.getCityByUser(userid);

		String[] citys = (String[])(request.getParameterValues("region"));
		String cityStr = "";
		String endTime="";
		List userList = null;
		IPnrStatMgr pnrStatMgr = (IPnrStatMgr) getBean("iPnrStatMgr");
		if(citys[0]!=null && !"".equals(citys[0])){
			for(int i=0;!("").equals(citys[0])&&i<citys.length;i++){
				if(i!=0){
					cityStr=cityStr+",";
				}
				cityStr="'"+citys[i]+"'";
			}

		}else{
		    for(int i = 0;regionList.size()>i;i++){
		    	TawSystemArea tawSystemArea = (TawSystemArea)regionList.get(i);
		    	if(i!=0){
					cityStr=cityStr+",";
				}
				cityStr+="'"+ tawSystemArea.getAreaid() +"'";
		    	
		    }
		}
		
		
		String year = StaticMethod.nullObject2String(request.getParameter("year"));
		String month = StaticMethod.nullObject2String(request.getParameter("month"));
		
		
		if("".equals(year)||"".equals(month)){
			userList = pnrStatMgr.getPartnerUsersByTimeAndCity(endTime, cityStr);
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			String timeSelect = year+"-"+ (month);
			Date date = sdf.parse(timeSelect);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.MONTH, 1);
			endTime = sdf.format(cal.getTime());
			userList = pnrStatMgr.getPartnerUsersByTimeAndCity(endTime, cityStr);
		}
		AreaDeptTree tree = null;
		PartnerUser user = null;
		PartnerDept partner = null;
		String key ="";
		Map userNumMap = new HashMap();
		Object[] object =  null;
		for(int i=0;i<userList.size();i++){
			object =  (Object[])userList.get(i);
			tree = (AreaDeptTree)object[0];
			user = (PartnerUser)object[1];
			key = tree.getAreaId()+"_"+tree.getInterfaceHeadId()+"_"+user.getServiceProfessional();
			userNumMap.put(key, String.valueOf(StaticMethod.nullObject2int(userNumMap.get(key), 0)+1));
		}
		List partnerList = pnrStatMgr.getPartnersByTimeAndCity(endTime, cityStr);
        //取id2name的service
        //IDictService service = (IDictService) getBean("DictService");
        //List professionList = service.getDictItems(Util.constituteDictId("dict-partner-baseinfo", "serviceProfession"));
       
        ITawSystemDictTypeManager tawSystemDictTypeManager = (ITawSystemDictTypeManager)getBean("ItawSystemDictTypeManager");
     
        List  professionList = tawSystemDictTypeManager.getDictSonsByDictid("1121201");
        
        TawSystemDictType tawSystemDictType = null;
        List statFormList = new ArrayList();
        BaseinfoStatForm statForm = null; 
        String keyTemp = ""; 
        Map subPartnerNumMap = new HashMap();
        Map partnerNumMap = new HashMap();
        List lastPartnerList = new ArrayList();
        String subKey="";
        for(int i=0;i<partnerList.size();i++){
			object =  (Object[])partnerList.get(i);
			tree = (AreaDeptTree)object[0];
			partner = (PartnerDept)object[1];
			subKey = tree.getAreaId()+"_"+tree.getInterfaceHeadId()+"_num";
			key = tree.getAreaId()+"_num";
			subPartnerNumMap.put(subKey, String.valueOf(StaticMethod.nullObject2int(subPartnerNumMap.get(subKey), 0)+1));
			if(!keyTemp.equals(subKey)){
				lastPartnerList.add(object);
				partnerNumMap.put(key, String.valueOf(StaticMethod.nullObject2int(partnerNumMap.get(key), 0)+1));
				keyTemp = subKey;
			}
        }
		for(int i=0;i<lastPartnerList.size();i++){	
			object =  (Object[])lastPartnerList.get(i);
			tree = (AreaDeptTree)object[0];
			partner = (PartnerDept)object[1];
			for(int j=0;j<professionList.size();j++){
				statForm = new BaseinfoStatForm();
				tawSystemDictType = (TawSystemDictType)professionList.get(j);
				key = tree.getAreaId()+"_"+tree.getInterfaceHeadId()+"_"+StaticMethod.nullObject2String(tawSystemDictType.getDictId());
				statForm.setAreaId(tree.getAreaId());
				statForm.setPartnerId(tree.getInterfaceHeadId());
				statForm.setPartnerName(partner.getName());
				statForm.setPartnerNum(StaticMethod.nullObject2String(partnerNumMap.get(key), "0"));
				statForm.setProfessionId(StaticMethod.nullObject2String(tawSystemDictType.getDictId()));
				statForm.setProfessionName(StaticMethod.nullObject2String(tawSystemDictType.getDictName()));
				statForm.setUserNum(StaticMethod.nullObject2String(userNumMap.get(key), "0"));
				statFormList.add(statForm);
//				System.out.println(itemXML.getItemName()+"_"+itemXML.getItemId());
			}
			
//			System.out.println(tree.getAreaId()+"_"+tree.getInterfaceHeadId()+"_"+StaticMethod.nullObject2int(partnerNumMap.get(tree.getAreaId()+"_num"), 0));
		}
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("statFormList", statFormList);
		request.setAttribute("partnerNumMap", partnerNumMap);
		request.setAttribute("subPartnerNumMap", subPartnerNumMap);
		request.setAttribute("professionSize", String.valueOf(professionList.size()));
		return mapping.findForward("listBaseinfoByCtiy");
	}


	/**
	 * 人力资源视图 统计 
	 * 2010-01-11
	 * author:benweiwei
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getReportPersonViewStat(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String first = StaticMethod.nullObject2String(request.getParameter("first"));

		
		
		if("".equals(first)){
			return mapping.findForward("listPersonViewStat");
		}

		Date time =StaticMethod.getLocalTime();

			Calendar cal = Calendar.getInstance();
			cal.setTime(time);
			cal.set(Calendar.DATE, 1);
			cal.set(Calendar.HOUR, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String endTime = sdf.format(cal.getTime());
/*//		查看省的areaid
		AreaDeptTreeMgr areaDeptTreeMgr = (AreaDeptTreeMgr) getBean("areaDeptTreeMgr");	
		List list =  areaDeptTreeMgr.getInfoByCondition(" from AreaDeptTree areaDeptTree where node_type='province' ");
		String provinceNum = "";
		if(list.size()>0){
			AreaDeptTree areaDeptTree = (AreaDeptTree)list.get(0);
			provinceNum = areaDeptTree.getAreaId();
		}*/
		
    	// 加载当前用户所属地市city
    	String userid = this.getUser(request).getUserid();
    	List regionList = PartnerCityByUser.getCityByUser(userid);
    	
		String regionStr=" ";
	    for(int i = 0;regionList.size()>i;i++){
	    	TawSystemArea tawSystemArea = (TawSystemArea)regionList.get(i);
	    	if(i==0){
	    		regionStr+=" and (  tree.areaid= '";
	    	}else{
	    		regionStr+=" or tree.areaid = '";
	    	}
	    	regionStr+=tawSystemArea.getAreaid();
	    	regionStr+="' ";
	    	if(i==regionList.size()-1){
	    		regionStr+=")";
	    	}
	    }


		IPnrStatMgr pnrStatMgr = (IPnrStatMgr) getBean("iPnrStatMgr");
		List listPersonStatTest = pnrStatMgr.getReportPersonViewStat(regionStr);
		String regT = "";
		String providerT = "";
		String reg = "";
		String provider = "";
		String profess = "";
		List listPersonStat = new ArrayList();
		//String sunLineLengthStat =null;
		for(int i = 0;listPersonStatTest.size()>i;i++){
			Object[] Object =  (Object[])listPersonStatTest.get(i);
			reg=(String)Object[0];
			provider = (String)Object[1];
			profess = (String)Object[2];
			if(profess==null){
				if(reg.equals(regT)&&provider.equals(providerT)){
				}else{
					listPersonStat.add(Object);
				}
			}else{
				listPersonStat.add(Object);
			}
			regT = reg;
			providerT = provider;
		}		
		String regStat = "";
		String providerStat = "";
		Map rowMap = new HashMap();
		for(int i = 0;listPersonStat.size()>i;i++){
			Object[] Object =  (Object[])listPersonStat.get(i);
    		//合作伙伴名称
    		//地市名称
			regStat=(String)Object[0];
			providerStat = (String)Object[1];
			
			rowMap.put(regStat+"_num", String.valueOf(StaticMethod.nullObject2int(rowMap.get(regStat+"_num"),0)+1));
			rowMap.put(regStat+"_"+providerStat+"_num", String.valueOf(StaticMethod.nullObject2int(rowMap.get(regStat+"_"+providerStat+"_num"),0)+1));

		}
		
		request.setAttribute("rowMap", rowMap);
		request.setAttribute("listPersonStat", listPersonStat);

		return mapping.findForward("listPersonViewStat");
	}	

	/**
	 * KPI 统计
	 * 统计条件：按地市、网格、合作伙伴统计。按年统计。   统计所属年所有的信息
	 * 2010-01-11
	 * author:benweiwei
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getKPImonthReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		
		//地市
		String region = StaticMethod.nullObject2String(request.getParameter("region"));
		//合作伙伴
		String provider = StaticMethod.nullObject2String(request.getParameter("provider"));
		//网格
		String gridName = StaticMethod.nullObject2String(request.getParameter("gridName"));
		//年份
		String year = StaticMethod.nullObject2String(request.getParameter("year"));
		//月份
		String month = StaticMethod.nullObject2String(request.getParameter("month"));
		
		String first = StaticMethod.nullObject2String(request.getParameter("first"));
	   	/**
    	 * 加载当前用户所属地市
    	 */
    	//当前用户名
		IPnrStatMgr pnrStatMgr = (IPnrStatMgr) getBean("iPnrStatMgr");

		String userid = this.getUser(request).getUserid();
		List LineListRegion = PartnerCityByUser.getCityByUser(userid);

    	/**
    	 * 加载合作伙伴
    	 */
		StringBuffer provider_list = new StringBuffer();
		provider_list.append("," + "");
		provider_list.append("," + "--请选择合作伙伴--");
		List LineListPartner = pnrStatMgr.listLinePartner();
		for (int i = 0; i < LineListPartner.size(); i++) {
			String partner = LineListPartner.get(i).toString();
			provider_list.append("," + partner);
			provider_list.append("," + partner);
			
		}
		String providerBuffer = provider_list.toString();
		providerBuffer = providerBuffer.substring(1, providerBuffer.length());
		
		request.setAttribute("regionBuffer", LineListRegion);
		request.setAttribute("providerBuffer", providerBuffer);
		
		if("".equals(first)){
			return mapping.findForward("listKPImStat");
		}

		String areaStr ="";		
		String providerStr=""; 
		String gridStr = "";
		String yearStr = "";
		String monthStr = "";
		String table = "";
		String wherePaStr = "";
		String whereNaStr = "";
 		
		if("org.hibernate.dialect.InformixDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())){
			wherePaStr = "partner.tree_areaid";
			whereNaStr = "partner.tree_nodename";
		} 
		else if("org.hibernate.dialect.OracleDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())){
			wherePaStr = "partner.areaid";
			whereNaStr = "partner.name";
		}
//		//统计条件
		if (region!=null && !"".equals(region)){
			areaStr = " and " + wherePaStr + " = '"+ region +"' ";
		}else{
		    for(int i = 0;LineListRegion.size()>i;i++){
		    	TawSystemArea tawSystemArea = (TawSystemArea)LineListRegion.get(i);
		    	if(i==0){
		    		areaStr+=" and ( " + wherePaStr + " = '";
		    	}else{
		    		areaStr+=" or " + wherePaStr + " = '";
		    	}
		    	areaStr+=tawSystemArea.getAreaid();
		    	areaStr+="' ";
		    	if(i==LineListRegion.size()-1){
		    		areaStr+=")";
		    	}
		    }
		}
		if (gridName!=null && !"".equals(gridName)){
			gridStr = " and pm.grid_name ='"+ gridName +"'" ;
		}
		if (provider!=null && !"".equals(provider)){
			providerStr = " and "+whereNaStr+"='"+ provider +"' " ;
		}
		if(month!=null && !"".equals(month)){
			monthStr =  " and month='"+ month +"' " ;
		}
		if(year!=null && !"".equals(year)){
			yearStr =  " and year='"+ year +"' " ;
		}
		if(month!=null && !"".equals(month)){
			table = "pnr_maintenance_gkm";
		}else{
			table ="pnr_maintenance_gky";
		}
//		查看省的areaid
		AreaDeptTreeMgr areaDeptTreeMgr = (AreaDeptTreeMgr) getBean("areaDeptTreeMgr");	
		List list =  areaDeptTreeMgr.getInfoByCondition(" from AreaDeptTree areaDeptTree where node_type='province' ");
		String provinceNum = "";
		if(list.size()>0){
			AreaDeptTree areaDeptTree = (AreaDeptTree)list.get(0);
			provinceNum = areaDeptTree.getAreaId();
		}
		
		List listKPImStatold = pnrStatMgr.getReportKPImStat(provinceNum, areaStr, providerStr, gridStr, yearStr, monthStr,table);
		String regT = "";
		String providerT = "";
		String reg = "";
		String provider1 = "";
		String grid = "";
		List listKPImStat = new ArrayList();
		//String sunLineLengthStat =null;
		for(int i = 0;listKPImStatold.size()>i;i++){
			Object[] Object =  (Object[])listKPImStatold.get(i);
			reg=(String)Object[0];
			provider1 = (String)Object[1];
			grid = (String)Object[2];
			if(grid==null){
				if(reg.equals(regT)&&provider1.equals(providerT)){
				}else{
					listKPImStat.add(Object);
				}
			}else{
				listKPImStat.add(Object);
			}
			regT = reg;
			providerT = provider1;
		}	
		String regionStat = "";
		String providerStat = "";
		Map rowMap = new HashMap();
		for(int i = 0;listKPImStat.size()>i;i++){
			Object[] lineObject =  (Object[])listKPImStat.get(i);
    		//合作伙伴名称
			regionStat  = (String)lineObject[0];
    		//地市名称
			providerStat = (String)lineObject[1];
			
			rowMap.put(regionStat+"_num", String.valueOf(StaticMethod.nullObject2int(rowMap.get(regionStat+"_num"),0)+1));
			rowMap.put(regionStat+"_"+providerStat+"_num", String.valueOf(StaticMethod.nullObject2int(rowMap.get(regionStat+"_"+providerStat+"_num"),0)+1));
		}
		String my = "";
		if(month!=null && !"".equals(month)){
			my = year+"年"+month+"月";
		}else{
			my = year+"年";
		}
		request.setAttribute("yearmonth", my);
		request.setAttribute("rowMap", rowMap);
		request.setAttribute("listKPImStat", listKPImStat);

		return mapping.findForward("listKPImStat");
	}


	
	/**
	 * 合作伙伴各功率油机数量统计
	 * author:daizhigang
	 */
	public ActionForward oilStatByCtiy(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	   	/**
    	 * 加载当前用户所属地市
    	 */
    	//当前用户名
    	String userid = this.getUser(request).getUserid();
    	List LineListRegion = PartnerCityByUser.getCityByUser(userid);
		request.setAttribute("regionBuffer", LineListRegion);
		return mapping.findForward("oilStatByCtiy");
	}
	/**
	 * 合作伙伴各功率油机数量统计
	 * author:daizhigang
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getOilStatByCtiy(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	// 加载当前用户所属地市city
    	String userid = this.getUser(request).getUserid();
    	List regionList = PartnerCityByUser.getCityByUser(userid);

		String[] citys = (String[])(request.getParameterValues("region"));
		String cityStr = "";
		if(citys[0]!=null && !"".equals(citys[0])){
			for(int i=0;!("").equals(citys[0])&&i<citys.length;i++){
				if(i!=0){
					cityStr=cityStr+",";
				}
				cityStr="'"+citys[i]+"'";
			}

		}else{
		    for(int i = 0;regionList.size()>i;i++){
		    	TawSystemArea tawSystemArea = (TawSystemArea)regionList.get(i);
		    	if(i!=0){
					cityStr=cityStr+",";
				}
				cityStr+="'"+ tawSystemArea.getAreaid() +"'";
		    	
		    }
		}
		
		String year = StaticMethod.nullObject2String(request.getParameter("year"));
		String month = StaticMethod.nullObject2String(request.getParameter("month"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		String timeSelect = year+"-"+ (month);
		Date date = sdf.parse(timeSelect);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, 1);
		String endTime = sdf.format(cal.getTime());
		IPnrStatMgr pnrStatMgr = (IPnrStatMgr) getBean("iPnrStatMgr"); 
		String keyTemp = ""; 
        Map subPartnerNumMap = new HashMap();
        Map partnerNumMap = new HashMap();
        List lastPartnerList = new ArrayList();
		String key ="";
        String subKey="";
		Object[] object =  null;
		AreaDeptTree tree = null;
		PartnerDept partner = null;
        //得到应配数
        OilEngineConfigure oilConfigure = null;
	    List oilEngineList = pnrStatMgr.getOilEngineByPartner(cityStr);
	    Map oilConfMap = new HashMap();
	    for(int i=0;i<oilEngineList.size();i++){
			object =  (Object[])oilEngineList.get(i);
			tree = (AreaDeptTree)object[0];
			oilConfigure = (OilEngineConfigure)object[1];
			key = tree.getAreaId()+"_"+tree.getInterfaceHeadId()+"_confnum";
			oilConfMap.put(key, oilConfigure.getDisposeNo());
	    }
        //得到实配数
	    List oilList = pnrStatMgr.getOilByPartner(endTime,cityStr);
	    TawPartnerOil oil = null;
	    for(int i=0;i<oilList.size();i++){
			object =  (Object[])oilList.get(i);
			tree = (AreaDeptTree)object[0];
			oil = (TawPartnerOil)object[1];
			key = tree.getAreaId()+"_"+tree.getInterfaceHeadId()+"_sumnum";
			subKey = tree.getAreaId()+"_"+tree.getInterfaceHeadId()+"_"+oil.getPower()+"_num";
			oilConfMap.put(subKey, String.valueOf(StaticMethod.nullObject2int(oilConfMap.get(subKey), 0)+1));
			oilConfMap.put(key, String.valueOf(StaticMethod.nullObject2int(oilConfMap.get(key), 0)+1));
	    }
		//得到各地市的合作伙伴
		List partnerList = pnrStatMgr.getPartnersByTimeAndCity(endTime, cityStr);
        for(int i=0;i<partnerList.size();i++){
			object =  (Object[])partnerList.get(i);
			tree = (AreaDeptTree)object[0];
			partner = (PartnerDept)object[1];
			subKey = tree.getAreaId()+"_"+tree.getInterfaceHeadId()+"_num";
			key = tree.getAreaId()+"_num";
			subPartnerNumMap.put(subKey, String.valueOf(StaticMethod.nullObject2int(subPartnerNumMap.get(subKey), 0)+1));
			if(!keyTemp.equals(subKey)){
				lastPartnerList.add(object);
				partnerNumMap.put(key, String.valueOf(StaticMethod.nullObject2int(partnerNumMap.get(key), 0)+1));
				keyTemp = subKey;
			}
        }
        //得到所有功率分类
		//定义取出所有厂家的集合，使用公共字典取得数据
		ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");	
		ArrayList powerDictList = mgr.getDictSonsByDictid("11207");//功率的字典id
		TawSystemDictType powerDict = null;
		/*
		 //如果换成xml字典
		//取id2name的service
        IDictService service = (IDictService) getBean("DictService");
        List professionList = service.getDictItems(Util.constituteDictId("dict-partner-baseinfo", "power"));
        DictItemXML itemXML = null;
		 */
        //合并后的合作伙伴集合
		OilStatByCtiyForm statForm = null;
		List statFormList = new ArrayList();
        for(int i=0;i<lastPartnerList.size();i++){	
			object =  (Object[])lastPartnerList.get(i);
			tree = (AreaDeptTree)object[0];
			partner = (PartnerDept)object[1];
			for(int j=0;j<powerDictList.size();j++){
				statForm = new OilStatByCtiyForm();
				powerDict = (TawSystemDictType)powerDictList.get(j);
				key = tree.getAreaId()+"_"+tree.getInterfaceHeadId();
				subKey = tree.getAreaId()+"_"+tree.getInterfaceHeadId()+"_"+StaticMethod.nullObject2String(powerDict.getDictId());
				
				statForm.setAreaId(tree.getAreaId());
				statForm.setPartnerId(tree.getInterfaceHeadId());
				statForm.setPartnerName(partner.getName());
				statForm.setPower(powerDict.getDictId());
				statForm.setPowName(powerDict.getDictName());
				statForm.setOilNum(StaticMethod.nullObject2String(oilConfMap.get(subKey+"_num"), "无"));
				statForm.setOilSumNum(StaticMethod.nullObject2String(oilConfMap.get(key+"_sumnum"), "无"));
				statForm.setOilConfigNum(StaticMethod.nullObject2String(oilConfMap.get(key+"_confnum"), "无"));
				statFormList.add(statForm);
//				System.out.println(itemXML.getItemName()+"_"+itemXML.getItemId());
			}
			
//			System.out.println(tree.getAreaId()+"_"+tree.getInterfaceHeadId()+"_"+StaticMethod.nullObject2int(partnerNumMap.get(tree.getAreaId()+"_num"), 0));
		}
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("endTime", endTime);
		request.setAttribute("statFormList", statFormList);
		request.setAttribute("partnerNumMap", partnerNumMap);
		request.setAttribute("subPartnerNumMap", subPartnerNumMap);
		request.setAttribute("powerNum", String.valueOf(powerDictList.size()));
		return mapping.findForward("listOilStatByCtiy");
	}
	
	
	/**
	 * 根据实配数连接得到列表
	 * author:daizhigang
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 
	public ActionForward getOilList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String endTime = StaticMethod.nullObject2String(request.getParameter("endTime"));
		String areaId = StaticMethod.nullObject2String(request.getParameter("areaId"));
		String partnerId = StaticMethod.nullObject2String(request.getParameter("partnerId"));
		String power = StaticMethod.nullObject2String(request.getParameter("power"));
		
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				TawPartnerOilConstants.TAWPARTNEROIL_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		
		IPnrStatMgr pnrStatMgr = (IPnrStatMgr) getBean("iPnrStatMgr"); 
//		List oilList = pnrStatMgr.getOilList(endTime,areaId,partnerId,power);
		
		Map map = (Map) pnrStatMgr.getOilList(pageIndex,pageSize,endTime,areaId,partnerId,power);
		List list = (List) map.get("result");
		List oilList = new ArrayList();
		Object[] object = null;
		TawPartnerOil oil = null;
		for(int i = 0;i<list.size();i++){
			object =  (Object[])list.get(i);
			oil = (TawPartnerOil)object[1];
			oilList.add(oil);
		}
		request.setAttribute(TawPartnerOilConstants.TAWPARTNEROIL_LIST, oilList);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("oilList");
	}
	*/
	/*
	 * 根据实配数连接得到列表
	 * author:daizhigang
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getOilList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String endTime = StaticMethod.nullObject2String(request.getParameter("endTime"));
		String areaId = StaticMethod.nullObject2String(request.getParameter("areaId"));
		String partnerId = StaticMethod.nullObject2String(request.getParameter("partnerId"));
		String power = StaticMethod.nullObject2String(request.getParameter("power"));
		IPnrStatMgr pnrStatMgr = (IPnrStatMgr) getBean("iPnrStatMgr"); 
		List list =  pnrStatMgr.getOilList(endTime,areaId,partnerId,power);
		List oilList = new ArrayList();
		Object[] object = null;
		TawPartnerOil oil = null;
		String partnerName = "";
		//定义取出所有类型的集合，使用公共字典取得数据
		ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");	
		ArrayList powerDictList = mgr.getDictSonsByDictid("11206");//功率的字典id
		TawSystemDictType typeDict = null;		
		String[] oilNum = new String[powerDictList.size()];
		String[] kind = new String[powerDictList.size()];
		Map oilTypeMap = new HashMap();
		for(int i = 0;i<list.size();i++){
			object =  (Object[])list.get(i);
			
			oil = (TawPartnerOil)object[1];
			oilTypeMap.put(oil.getKind(), String.valueOf(StaticMethod.nullObject2int(oilTypeMap.get(oil.getKind()), 0)+1));
		}
		for(int i=0;i<powerDictList.size();i++){
			typeDict = (TawSystemDictType)powerDictList.get(i);
			kind[i] = typeDict.getDictId();
			oilNum[i] = StaticMethod.nullObject2String(oilTypeMap.get(typeDict.getDictId()), "0");
		}
		request.setAttribute("endTime", endTime);
		request.setAttribute("areaId", areaId);
		request.setAttribute("partnerId", partnerId);
		request.setAttribute("power", power);
		request.setAttribute("oilNum", oilNum);
		request.setAttribute("kind", kind);
		return mapping.findForward("oilList");
	}
	/**
	 * 合作伙伴名称和油机性质和运行状态。按月统计
	 * author:daizhigang
	 */
	public ActionForward oilStatByState(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("oilStatByState");
	}
	/**
	 * 合作伙伴名称和油机性质和运行状态。按月统计
	 * author:daizhigang
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getOilStatByState(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	// 加载当前用户所属地市city
    	String userid = this.getUser(request).getUserid();
    	List regionList = PartnerCityByUser.getCityByUser(userid);

		String regionStr=" ";
	    for(int i = 0;regionList.size()>i;i++){
	    	TawSystemArea tawSystemArea = (TawSystemArea)regionList.get(i);
	    	if(i==0){
	    		regionStr+=" and (  tree.areaId= '";
	    	}else{
	    		regionStr+=" or tree.areaId = '";
	    	}
	    	regionStr+=tawSystemArea.getAreaid();
	    	regionStr+="' ";
	    	if(i==regionList.size()-1){
	    		regionStr+=")";
	    	}
	    }

		
		String year = StaticMethod.nullObject2String(request.getParameter("year"));
		String month = StaticMethod.nullObject2String(request.getParameter("month"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		String timeSelect = year+"-"+ (month);
		Date date = sdf.parse(timeSelect);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, 1);
		String endTime = sdf.format(cal.getTime());
		String partnerId = StaticMethod.nullObject2String(request.getParameter("partnerId"));
		String kind = StaticMethod.nullObject2String(request.getParameter("kind"));
		String state = StaticMethod.nullObject2String(request.getParameter("state"));
		IPnrStatMgr pnrStatMgr = (IPnrStatMgr) getBean("iPnrStatMgr"); 
		List oilList = pnrStatMgr.getOilNumListByType(endTime, partnerId, kind,state,regionStr);
		Map oilNumMap = new HashMap();
		Object[] object = new Object[5];
		String partnerTempId = "";
		String kindTemp = "";
		String stateTemp = "";
		String oilNum = "";
		String key = "";
		for(int i=0;i<oilList.size();i++){
			object  = (Object[])oilList.get(i);
			partnerTempId = StaticMethod.nullObject2String(object[0]);
			kindTemp = StaticMethod.nullObject2String(object[2]);
			stateTemp = StaticMethod.nullObject2String(object[3]);
			key = partnerTempId+"_"+kindTemp+"_"+stateTemp;
			oilNum = StaticMethod.nullObject2String(object[4]);
			oilNumMap.put(key, oilNum);
		}
		//定义取出所有类型和状态的集合，使用公共字典取得数据
		ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");	
		ArrayList kindDictList = mgr.getDictSonsByDictid("11206");//性质的字典id
		ArrayList stateDictList = mgr.getDictSonsByDictid("11205");//状态的字典id
		TawSystemDictType kindDict = null;	
		TawSystemDictType stateDict = null;
		List partnerList = pnrStatMgr.getPartnersByTime(endTime);
		AreaDeptTree tree = null;
		PartnerDept partner = null;
		OilStatByStateForm stateForm = null;
		List statFormList = new ArrayList();
        for(int i=0;i<partnerList.size();i++){
			object =  (Object[])partnerList.get(i);
			tree = (AreaDeptTree)object[0];
			partner = (PartnerDept)object[1];
			for(int j=0;j<kindDictList.size();j++){
				kindDict = (TawSystemDictType)kindDictList.get(j);
				for(int k=0;k<stateDictList.size();k++){
					stateDict = (TawSystemDictType)stateDictList.get(k);
					stateForm = new OilStatByStateForm();
					stateForm.setPartnerId(partner.getId());
					stateForm.setPartnerName(partner.getName());
					stateForm.setOilNum(StaticMethod.nullObject2String(oilNumMap.get(partner.getId()+"_"+kindDict.getDictId()+"_"+stateDict.getDictId()),"0"));	
					stateForm.setKind(kindDict.getDictId());
					stateForm.setState(stateDict.getDictId());
					statFormList.add(stateForm);
				}
			}
			
        }
		
		String kindSize = String.valueOf(kindDictList.size()*stateDictList.size());
		String stateSize = String.valueOf(stateDictList.size());
		request.setAttribute("kindSize", kindSize);
		request.setAttribute("stateSize", stateSize);
		request.setAttribute("statFormList", statFormList);
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("endTime", endTime);
		return mapping.findForward("listOilStatByState");
	}
	
	// ===================== 合作伙伴使用情况统计 ====================== 
	/**
	 * 合作伙伴使用情况统计 输入统计条件页面
	 * 统计条件：按合作伙伴名称和时间范围进行统计。
	 * 2010-01-27
	 * author:wangjunfeng
	 * 
	 */
	public ActionForward useCaseCondition(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		/**
    	 * 加载合作伙伴
    	 */
    	IPnrStatMgr pnrStatMgr = (IPnrStatMgr) getBean("iPnrStatMgr");
		StringBuffer provider_list = new StringBuffer();
		provider_list.append("," + "");
		provider_list.append("," + "--请选择合作伙伴--");
		List ApparatusListPartner = pnrStatMgr.listLinePartner();
		for (int i = 0; i < ApparatusListPartner.size(); i++) {
			String partner = ApparatusListPartner.get(i).toString();
			provider_list.append("," + partner);
			provider_list.append("," + partner);
			
		}
		String providerBuffer = provider_list.toString();
		providerBuffer = providerBuffer.substring(1, providerBuffer.length());
		
		request.setAttribute("providerBuffer", providerBuffer);

		return mapping.findForward("useCaseCondition");
	}
	
	/**
	 * 合作伙伴使用情况统计 统计结果
	 * 统计条件：按合作伙伴名称和时间范围进行统计。
	 * 2010-01-27
	 * author:wangjunfeng
	 */
	public ActionForward getUseCaseStatReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	//当前用户名
    	String userid = this.getUser(request).getUserid();
    	List LineListRegion = PartnerCityByUser.getCityByUser(userid);
    	TawSystemArea ua = (TawSystemArea)LineListRegion.get(0);
		String parentareaid = ua.getParentAreaid();

		
		String regionStr=" ";
	    for(int i = 0;LineListRegion.size()>i;i++){
	    	TawSystemArea tawSystemArea = (TawSystemArea)LineListRegion.get(i);
	    	if(i==0){
	    		regionStr+=" and (  tree.areaid= '";
	    	}else{
	    		regionStr+=" or tree.areaid = '";
	    	}
	    	regionStr+=tawSystemArea.getAreaid();
	    	regionStr+="' ";
	    	if(i==LineListRegion.size()-1){
	    		regionStr+=")";
	    	}
	    }


		//合作伙伴名称
		String partnerName = StaticMethod.nullObject2String(request.getParameter("provider"));
		//时间范围 
		String beginTime = StaticMethod.null2String(request.getParameter("beginTime"));
		String endTime = StaticMethod.null2String(request.getParameter("endTime"));
		
		String whereStr=" where 1=1  ";
		//统计条件
		if (partnerName!=null && !"".equals(partnerName)){
			whereStr += " and partner= '"+ partnerName +"'" ;
		}
		if(beginTime!=null && !"".equals(beginTime)){
			whereStr+=" and opertime between '"+ beginTime +"' "; 
		}	
		if(endTime!=null && !"".equals(endTime)){
			whereStr+=" and '"+ endTime +"'"; 
		}	
		
		IPnrStatMgr pnrStatMgr = (IPnrStatMgr) getBean("iPnrStatMgr");
		List listUseCaseStat = pnrStatMgr.getUseCaseStat(parentareaid,whereStr,regionStr);
		String partnerNameStat =null;
		Map rowMap = new HashMap();
		for(int i = 0;listUseCaseStat.size() >i;i++){
			Object[] apparatusObject =  (Object[])listUseCaseStat.get(i);
			//合作伙伴名称
    		partnerNameStat = (String)apparatusObject[0];
			rowMap.put(partnerNameStat+"_num", String.valueOf(StaticMethod.nullObject2int(rowMap.get(partnerNameStat+"_num"),0)+1));
		}
		request.setAttribute("rowMap", rowMap);
		request.setAttribute("listUseCaseStat", listUseCaseStat);
		return mapping.findForward("useCaseStatReport");
	}

	

	/**
	 * 网格满意度统计
	 * 统计条件：按地市、网格、合作伙伴统计。按年,月统计。
	 * 2010-03-15
	 * author:daizhigang
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	/*public ActionForward getGridSatisfyStat(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		
		//地市
		String region = StaticMethod.nullObject2String(request.getParameter("region"));
		//合作伙伴
		String provider = StaticMethod.nullObject2String(request.getParameter("provider"));
		//网格
		String gridName = StaticMethod.nullObject2String(request.getParameter("gridName"));
		//年份
		String year = StaticMethod.nullObject2String(request.getParameter("year"));
		//月份
		String month = StaticMethod.nullObject2String(request.getParameter("month"));
		
		String first = StaticMethod.nullObject2String(request.getParameter("first"));
	   	*//**
    	 * 加载当前用户所属地市
    	 *//*
    	//当前用户名
		IPnrStatMgr pnrStatMgr = (IPnrStatMgr) getBean("iPnrStatMgr");

		String userid = this.getUser(request).getUserid();
		List LineListRegion = PartnerCityByUser.getCityByUser(userid);

    	*//**
    	 * 加载合作伙伴
    	 *//*
		StringBuffer provider_list = new StringBuffer();
		provider_list.append("," + "");
		provider_list.append("," + "--请选择合作伙伴--");
		List LineListPartner = pnrStatMgr.listLinePartner();
		for (int i = 0; i < LineListPartner.size(); i++) {
			String partner = LineListPartner.get(i).toString();
			provider_list.append("," + partner);
			provider_list.append("," + partner);
			
		}
		String providerBuffer = provider_list.toString();
		providerBuffer = providerBuffer.substring(1, providerBuffer.length());
		
		request.setAttribute("regionBuffer", LineListRegion);
		request.setAttribute("providerBuffer", providerBuffer);
		
		if("".equals(first)){
			return mapping.findForward("listGridSatisfyStat");
		}

		
		
//		查看省的areaid
		AreaDeptTreeMgr areaDeptTreeMgr = (AreaDeptTreeMgr) getBean("areaDeptTreeMgr");	
		List list =  areaDeptTreeMgr.getInfoByCondition(" from AreaDeptTree areaDeptTree where node_type='province' ");
		String provinceNum = "";
		if(list.size()>0){
			AreaDeptTree areaDeptTree = (AreaDeptTree)list.get(0);
			provinceNum = areaDeptTree.getAreaId();
		}
		
		List listGridSatisfyStat = pnrStatMgr.getGridSatisfyStat(region,provider,gridName,year,month,LineListRegion);
		GridSatisfyStatForm gsf = null;
		Map rowMap = new HashMap();
		for(int i=0;i<listGridSatisfyStat.size();i++){
			gsf =  (GridSatisfyStatForm)listGridSatisfyStat.get(i);
			rowMap.put(gsf.getRegion(), String.valueOf(StaticMethod.nullObject2int(rowMap.get(gsf.getRegion()), 0)+1));
			rowMap.put(gsf.getRegion()+"_"+gsf.getCity(),String.valueOf(StaticMethod.nullObject2int(rowMap.get(gsf.getRegion()+"_"+gsf.getCity()), 0)+1));
			rowMap.put(gsf.getRegion()+"_"+gsf.getCity()+"_"+gsf.getGrid(), String.valueOf(StaticMethod.nullObject2int(rowMap.get(gsf.getRegion()+"_"+gsf.getCity()+"_"+gsf.getGrid()), 0)+1));
			rowMap.put(gsf.getRegion()+"_"+gsf.getCity()+"_"+gsf.getGrid()+"_"+gsf.getProvider(), String.valueOf(StaticMethod.nullObject2int(rowMap.get(gsf.getRegion()+"_"+gsf.getCity()+"_"+gsf.getGrid()+"_"+gsf.getProvider()), 0)+1));
		}
		
		
		String time = "";
		if(month!=null && !"".equals(month)){
			time = year+"年"+month+"月";
		}else{
			time = year+"年";
		}
		request.setAttribute("time", time);
		request.setAttribute("rowMap", rowMap);
		
		
		request.setAttribute("listGridSatisfyStat", listGridSatisfyStat);

		return mapping.findForward("listGridSatisfyStat");
	}*/
	/**
	 * 合作伙伴市场份额(数据点占全省/市百分比)饼图
	 * 2010-03-15
	 * author:daizhigang
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getPointPie(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String area = StaticMethod.nullObject2String(request.getParameter("area"));
    	String userid = this.getUser(request).getUserid();
    	List LineListRegion = PartnerCityByUser.getCityByUser(userid);
		request.setAttribute("regionBuffer", LineListRegion);
		IPnrStatMgr pnrStatMgr = (IPnrStatMgr) getBean("iPnrStatMgr");
		List datalistProvince = pnrStatMgr.getPointPie(area);
		String tempPath =  "partnerUpload/pic/";
		String jpgName = "pie_point_"+area+".gif";
		String fileNameProvince = MakePieChart.getPieChart("代维公司维护数据点占有百分比", datalistProvince,tempPath,jpgName);
		String filePathProvince = request.getContextPath()+"/"+tempPath+fileNameProvince;
		request.setAttribute("filePathProvince", filePathProvince);
		request.setAttribute("area", area);		
		return mapping.findForward("pointPiepage");
	}
	/**
	 * 合作伙伴市场份额(基站占全省/市百分比)饼图
	 * 2010-03-15
	 * author:daizhigang
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getSitePie(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String area = StaticMethod.nullObject2String(request.getParameter("area"));
    	String userid = this.getUser(request).getUserid();
    	List LineListRegion = PartnerCityByUser.getCityByUser(userid);
		request.setAttribute("regionBuffer", LineListRegion);
		IPnrStatMgr pnrStatMgr = (IPnrStatMgr) getBean("iPnrStatMgr");
		List datalistProvince = pnrStatMgr.getSitePie(area);
		String tempPath =  "partnerUpload/pic/";
		String jpgName = "pie_Site_"+area+".gif";
		String fileNameProvince = MakePieChart.getPieChart("代维公司维护基站占有百分比", datalistProvince,tempPath,jpgName);
		String filePathProvince = request.getContextPath()+"/"+tempPath+fileNameProvince;
		request.setAttribute("filePathProvince", filePathProvince);
		request.setAttribute("area", area);		
		return mapping.findForward("sitePiepage");
	}
	
	
	
	/**
	 * 代维响应速度统计
	 * 统计条件：按地市、网格、合作伙伴统计。按年,月统计。
	 * 2010-03-17
	 * author:wangjunfeng
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	/*public ActionForward getServiceSpeedStat(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
				
		//地市
		String region = StaticMethod.nullObject2String(request.getParameter("region"));
		//合作伙伴
		String provider = StaticMethod.nullObject2String(request.getParameter("provider"));
		//网格
		String gridName = StaticMethod.nullObject2String(request.getParameter("gridName"));
		//年份
		String year = StaticMethod.nullObject2String(request.getParameter("year"));
		//月份
		String month = StaticMethod.nullObject2String(request.getParameter("month"));
		
		String first = StaticMethod.nullObject2String(request.getParameter("first"));
	   	*//**
    	 * 加载当前用户所属地市
    	 *//*
    	//当前用户名
		IPnrStatMgr pnrStatMgr = (IPnrStatMgr) getBean("iPnrStatMgr");

		String userid = this.getUser(request).getUserid();
		List LineListRegion = PartnerCityByUser.getCityByUser(userid);

    	*//**
    	 * 加载合作伙伴
    	 *//*
		StringBuffer provider_list = new StringBuffer();
		provider_list.append("," + "");
		provider_list.append("," + "--请选择合作伙伴--");
		List LineListPartner = pnrStatMgr.listLinePartner();
		for (int i = 0; i < LineListPartner.size(); i++) {
			String partner = LineListPartner.get(i).toString();
			provider_list.append("," + partner);
			provider_list.append("," + partner);
			
		}
		String providerBuffer = provider_list.toString();
		providerBuffer = providerBuffer.substring(1, providerBuffer.length());
		
		request.setAttribute("regionBuffer", LineListRegion);
		request.setAttribute("providerBuffer", providerBuffer);
		
		if("".equals(first)){
			return mapping.findForward("listServiceSpeedStat");
		}
//		查看省的areaid
		AreaDeptTreeMgr areaDeptTreeMgr = (AreaDeptTreeMgr) getBean("areaDeptTreeMgr");	
		List list =  areaDeptTreeMgr.getInfoByCondition(" from AreaDeptTree areaDeptTree where node_type='province' ");
		String provinceNum = "";
		if(list.size()>0){
			AreaDeptTree areaDeptTree = (AreaDeptTree)list.get(0);
			provinceNum = areaDeptTree.getAreaId();
		}
		
		List listServiceSpeedStat = pnrStatMgr.getServiceSpeedStat(region,provider,gridName,year,month,LineListRegion);
		ServiceSpeedStatForm serviceSpeedForm = null;
		Map rowMap = new HashMap();
		for(int i=0;i<listServiceSpeedStat.size();i++){
			serviceSpeedForm =  (ServiceSpeedStatForm)listServiceSpeedStat.get(i);
			rowMap.put(serviceSpeedForm.getRegion(), String.valueOf(StaticMethod.nullObject2int(rowMap.get(serviceSpeedForm.getRegion()), 0)+1));
			rowMap.put(serviceSpeedForm.getRegion()+"_"+serviceSpeedForm.getCity(), String.valueOf(StaticMethod.nullObject2int(rowMap.get(serviceSpeedForm.getRegion()+"_"+serviceSpeedForm.getCity()), 0)+1));
			rowMap.put(serviceSpeedForm.getRegion()+"_"+serviceSpeedForm.getCity()+"_"+serviceSpeedForm.getGrid(), String.valueOf(StaticMethod.nullObject2int(rowMap.get(serviceSpeedForm.getRegion()+"_"+serviceSpeedForm.getCity()+"_"+serviceSpeedForm.getGrid()), 0)+1));
			rowMap.put(serviceSpeedForm.getRegion()+"_"+serviceSpeedForm.getCity()+"_"+serviceSpeedForm.getGrid()+"_"+serviceSpeedForm.getProvider(), String.valueOf(StaticMethod.nullObject2int(rowMap.get(serviceSpeedForm.getRegion()+"_"+serviceSpeedForm.getCity()+"_"+serviceSpeedForm.getGrid()+"_"+serviceSpeedForm.getProvider()), 0)+1));
		}
		String time = "";
		if(month!=null && !"".equals(month)){
			time = year+"年"+month+"月";
		}else{
			time = year+"年";
		}
		request.setAttribute("time", time);
		request.setAttribute("rowMap", rowMap);
		
		request.setAttribute("listServiceSpeedStat", listServiceSpeedStat);

		return mapping.findForward("listServiceSpeedStat");
	}
	*/
	/**
	 * 各地市的合作伙伴数量统计报表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author daizhigang
	 * @since 1.0
	 */
	public ActionForward getPartnerNumforChart(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
//		String citys = StaticMethod.nullObject2String(request.getParameterValues("area"));
		String city = StaticMethod.null2String(request.getParameter("area"));
		if("all".equals(city)){
			city="";
		}
		IPnrStatMgr pnrStatMgr = (IPnrStatMgr) getBean("iPnrStatMgr");
		String strXML = pnrStatMgr.getPartnerNumforChart("",city,request.getContextPath());
		request.setAttribute("strXML", strXML);
		PnrBaseAreaIdList roleIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");

		List LineListRegion = PartnerCityByUser.getCityByProvince(String.valueOf(roleIdList.getRootAreaId()));
		request.setAttribute("regionBuffer", LineListRegion);
		if("".equals(city)){
			city="all";
		}
		request.setAttribute("city", city);
		return mapping.findForward("partnerNumforChart");
	}
	
	/*
	 * 各城市的合作伙伴人力资源
	 */
	public ActionForward getNumUsers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String xmlStr;
		List dataList = new ArrayList();
		List userList = new ArrayList();
		IPnrStatMgr pnrStatMgr = (IPnrStatMgr) getBean("iPnrStatMgr");
		AreaDeptTreeMgr areaDeptTreeMgr = (AreaDeptTreeMgr) getBean("areaDeptTreeMgr");	
		String nodeId = StaticMethod.nullObject2String(request.getParameter("nodeId"));
		AreaDeptTree areaDeptTree = areaDeptTreeMgr.getAreaDeptTreeByNodeId(nodeId);
		String citys = StaticMethod.nullObject2String(request.getParameter("nodeId"));
		String city = areaDeptTree.getAreaId();
		if(citys.length()>3){
			userList = pnrStatMgr.getPartnerNumUsers(city);
		}else{
			userList = pnrStatMgr.getPartnerNumUsers("");
		}
		for(int index = 0; index < userList.size(); index++){
			FusionChartData fusionChartData = new FusionChartData();
			Object[] items = (Object[])userList.get(index);
			String company = items[0].toString();
			String usersCount = items[1].toString();
			fusionChartData.setTitle(company);
			fusionChartData.setNumForInt(usersCount);
			if(citys.length()>3){
				fusionChartData.setUrl("partnerUsers.do?method=search&in=area&areaId=" + city + "&bigDeptId=" + items[2].toString()
						+ "&nodeId="+ citys);
			}else{
				fusionChartData.setUrl("partnerUsers.do?method=search&in=province&bigDeptId=" + items[2].toString()
						+ "&nodeId="+ citys);
			}
			dataList.add(fusionChartData);
		}
		FusionChart fusionChart = new FusionChart();
		fusionChart.setCaption("人力信息");
		xmlStr = FusionChartMethod.getString(dataList, fusionChart);
		request.setAttribute("strXML", xmlStr);
		return mapping.findForward("partnerNumRes");
	}
	
	/*
	 * 各城市的合作伙伴仪器仪表
	 */
	public ActionForward getNumTawApparatuss(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String xmlStr;
		List dataList = new ArrayList();
		List apparatusList = new ArrayList();
		IPnrStatMgr pnrStatMgr = (IPnrStatMgr) getBean("iPnrStatMgr");
		String citys = StaticMethod.nullObject2String(request.getParameter("nodeId"));
		if(citys.length()>3){
			apparatusList = pnrStatMgr.getPartnerNumApparatus(citys);
		}else{
			apparatusList = pnrStatMgr.getPartnerNumApparatus("");
		}
		for(int index = 0; index < apparatusList.size(); index++){
			FusionChartData fusionChartData = new FusionChartData();
			Object[] items = (Object[])apparatusList.get(index);
			String company = items[0].toString();
			String apparatussCount = items[1].toString();
			fusionChartData.setTitle(company);
			fusionChartData.setNumForInt(apparatussCount);
			if(citys.length()>3){
				fusionChartData.setUrl("tawApparatuss.do?method=search&areaId=" + citys + "&bigDeptId=" + items[2].toString()
						+ "&nodeId="+ citys + "&in=area");
			}else{
				fusionChartData.setUrl("tawApparatuss.do?method=search&bigDeptId=" + items[2].toString()
						+ "&nodeId="+ citys + "&in=province");
			}
			dataList.add(fusionChartData);
		}
		FusionChart fusionChart = new FusionChart();
		fusionChart.setCaption("仪器仪表");
		xmlStr = FusionChartMethod.getString(dataList, fusionChart);
		request.setAttribute("strXML", xmlStr);
		return mapping.findForward("partnerNumRes");
	}
	
	/*
	 * 各城市的合作伙伴车辆
	 */
	public ActionForward tawPartnerCars(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String xmlStr;
		List dataList = new ArrayList();
		List carList = null;
		IPnrStatMgr pnrStatMgr = (IPnrStatMgr) getBean("iPnrStatMgr");
		String citys = StaticMethod.nullObject2String(request.getParameter("nodeId"));
		if(citys.length()>3){
			carList = pnrStatMgr.getPartnerNumCar(citys);
		}else{
			carList = pnrStatMgr.getPartnerNumCar("");
		}
		for(int index = 0; index < carList.size(); index++){
			FusionChartData fusionChartData = new FusionChartData();
			Object[] items = (Object[])carList.get(index);
			String company = items[0].toString();
			String usersCount = items[1].toString();
			fusionChartData.setTitle(company);
			fusionChartData.setNumForInt(usersCount);
			if(citys.length()>3){
				fusionChartData.setUrl("tawPartnerCars.do?method=search&areaId=" + citys + "&bigDeptId=" + items[2].toString()
						+ "&nodeId="+ citys + "&in=area");
			}else{
				fusionChartData.setUrl("tawPartnerCars.do?method=search&bigDeptId=" + items[2].toString()
						+ "&nodeId="+ citys + "&in=province");
			}
			dataList.add(fusionChartData);
		}
		FusionChart fusionChart = new FusionChart();
		fusionChart.setCaption("车辆管理");
		xmlStr = FusionChartMethod.getString(dataList, fusionChart);
		request.setAttribute("strXML", xmlStr);
		return mapping.findForward("partnerNumRes");
	}
	
	/*
	 * 各城市的合作伙伴油机
	 */
	public ActionForward tawPartnerOils(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String xmlStr;
		List dataList = new ArrayList();
		List oilList = null;
		IPnrStatMgr pnrStatMgr = (IPnrStatMgr) getBean("iPnrStatMgr");
		String citys = StaticMethod.nullObject2String(request.getParameter("nodeId"));
		if(citys.length()>3){
			oilList = pnrStatMgr.getPartnerNumOil(citys);
		} else {
			oilList = pnrStatMgr.getPartnerNumOil("");
		}
		for(int index = 0; index < oilList.size(); index++){
			FusionChartData fusionChartData = new FusionChartData();
			Object[] items = (Object[])oilList.get(index);
			String company = items[0].toString();
			String usersCount = items[1].toString();
			fusionChartData.setTitle(company);
			fusionChartData.setNumForInt(usersCount);
			if(citys.length()>3){
				fusionChartData.setUrl("tawPartnerOils.do?method=search&areaId=" + citys + "&bigDeptId=" + items[2].toString()
						+ "&nodeId="+ citys + "&in=area");
			}else{
				fusionChartData.setUrl("tawPartnerOils.do?method=search&bigDeptId=" + items[2].toString()
						+ "&nodeId="+ citys + "&in=province");
			}

			dataList.add(fusionChartData);
		}
		FusionChart fusionChart = new FusionChart();
		fusionChart.setCaption("油机管理");
		xmlStr = FusionChartMethod.getString(dataList, fusionChart);
		request.setAttribute("strXML", xmlStr);
		return mapping.findForward("partnerNumRes");
	}
	
}