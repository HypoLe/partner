package com.boco.eoms.partner.sheet.commontask.webapp.action;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.dict.service.ID2NameService;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.partner.home.util.HomeHelper;
import com.boco.eoms.partner.property.util.StatisticsResultExport;
import com.boco.eoms.partner.sheet.commontask.service.PnrCommonTaskStatisticMgr;
import com.boco.eoms.partner.statistically.pojo.TdObjModel;

/** 
 * Description: 通用任务工单统计ACTION
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Jul 23, 2012 3:26:12 PM 
 */
 
 public class PnrCommonTaskStatisticAction extends BaseAction  {
 	
	 
	 /**
		 * 跳转到工单统计页面
		 */
	public ActionForward showStatisticPage(ActionMapping mapping,ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取当前登录人员信息
		PnrCommonTaskStatisticMgr pnrCommonTaskStatisticMgr = (PnrCommonTaskStatisticMgr)getBean("pnrCommonTaskStatisticMgr");
		TawSystemSessionForm sessionform = (TawSystemSessionForm)request.getSession().getAttribute("sessionform"); 
		String sessionDeptId = sessionform.getDeptid();
		String sessionAreaId = pnrCommonTaskStatisticMgr.getDeptIdToAreaId(sessionDeptId);
		request.setAttribute("sessionDeptId", sessionDeptId);
		request.setAttribute("sessionAreaId", sessionAreaId);
		return mapping.findForward("sheetStatistic");
	}
		
	
	/**
	 * 显示工单统计结果信息
	 */
	public ActionForward showStatisticInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		PnrCommonTaskStatisticMgr pnrCommonTaskStatisticMgr = (PnrCommonTaskStatisticMgr)getBean("pnrCommonTaskStatisticMgr");
		String whereStr = "";
		String toSelectAreaId = "";
		String sessionAreaId = StaticMethod.nullObject2String(request.getParameter("sessionAreaId"));
		String sessionDeptId = StaticMethod.nullObject2String(request.getParameter("sessionDeptId"));
		
	    String yearflag = StaticMethod.nullObject2String(request.getParameter("year"));    //查询年份
	    String monthflag = StaticMethod.nullObject2String(request.getParameter("month"));  //查询月份
	    if((!yearflag.equals("") && yearflag.length()>0) && (!monthflag.equals("") && monthflag.length()>0)){
	    	if(Integer.parseInt(monthflag)<10){
	    		monthflag = "0"+monthflag;
	    	}
	    	whereStr = whereStr+ " and substr(sendtime,0,7)='"+yearflag+"-"+monthflag+"'";
	    }
	    String startTime = StaticMethod.nullObject2String(request.getParameter("startTime")); //查询开始时间
	    String endTime   = StaticMethod.nullObject2String(request.getParameter("endTime")); //查询结束时间
	    if((!startTime.equals("") && startTime.length()>0) && (!endTime.equals("") && endTime.length()>0)){
	    	whereStr = whereStr+ " and sendtime>='"+startTime+" 00:00:00' and sendtime<='"+endTime+" 23:59:59'";
	    }    
	    if(yearflag.equals("") && monthflag.equals("") && startTime.equals("") && endTime.equals("")){
	    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");//设置日期格式
			String createtime = df.format(new Date());// new Date()为获取当前系统时间
			whereStr = whereStr+ " and substr(sendtime,0,7)='"+createtime+"'";
			
	    }
	    request.setAttribute("startTime", startTime);
		request.setAttribute("endTime", endTime);
		request.setAttribute("year", yearflag);
		request.setAttribute("month", StaticMethod.nullObject2String(request.getParameter("month")));
		
	    String sendUser  = StaticMethod.nullObject2String(request.getParameter("sendUser")); //按派单人查询
	    if((!sendUser.equals("") && sendUser.length()>0)){
	    	whereStr = whereStr+ " and senduserid='"+sendUser+"'";
	    }
	    String sendDept  = StaticMethod.nullObject2String(request.getParameter("sendDept")); //按派单部门查询
	    if((!sendDept.equals("") && sendDept.length()>0)){
	    	whereStr = whereStr+ " and senddeptid='"+sendDept+"'";
	    }
	    request.setAttribute("sendUser", sendUser);
		request.setAttribute("sendUserName", StaticMethod.nullObject2String(request.getParameter("sendUserName")));
		request.setAttribute("sendDept", sendDept);
		request.setAttribute("sendDeptName", StaticMethod.nullObject2String(request.getParameter("sendDeptName")));
	    
	    String acceptUser= StaticMethod.nullObject2String(request.getParameter("acceptUser")); //按处理人查询
	    if((!acceptUser.equals("") && acceptUser.length()>0)){
	    	whereStr = whereStr+ " and operateuserid='"+acceptUser+"'";
	    }
	    String acceptDept= StaticMethod.nullObject2String(request.getParameter("acceptDept")); //按处理部门查询
	    if((!acceptDept.equals("") && acceptDept.length()>0)){
	    	whereStr = whereStr+ " and operatedeptid='"+acceptDept+"'";
	    }
	    
	    request.setAttribute("acceptUser", acceptUser);
		request.setAttribute("acceptUserName", StaticMethod.nullObject2String(request.getParameter("acceptUserName")));
		request.setAttribute("acceptDept", acceptDept);
		request.setAttribute("acceptDeptName", StaticMethod.nullObject2String(request.getParameter("acceptDeptName")));
		
	    String areaIdSearch = StaticMethod.nullObject2String((request.getParameter("areaIdSearch"))); //按区域查询
	    if((!areaIdSearch.equals("") && areaIdSearch.length()>0)){
	    	whereStr = whereStr+ " and areaid like '"+areaIdSearch+"%'";	
	    	toSelectAreaId = areaIdSearch;
	    }else{
	    	toSelectAreaId = sessionAreaId;
	    }
	    request.setAttribute("areaIdSearch", areaIdSearch);
		request.setAttribute("area", StaticMethod.nullObject2String(request.getParameter("area")));
			
	    String mainSpecialty = StaticMethod.nullObject2String(request.getParameter("mainSpecialty")); //按代维专业查询
	    if((!mainSpecialty.equals("") && mainSpecialty.length()>0)){
	    	whereStr = whereStr+ " and mainspecialty='"+mainSpecialty+"'";
	    } 
	    request.setAttribute("mainSpecialty", mainSpecialty);

		
	    String mainTaskType =  StaticMethod.nullObject2String(request.getParameter("mainTaskType")); //按任务类型查询
	    if((!mainTaskType.equals("") && mainTaskType.length()>0)){
	    	whereStr = whereStr+ " and maintasktype='"+mainTaskType+"'";
	    }
	    request.setAttribute("mainTaskType", mainTaskType);
	    
	    String statisticsItem[] = request.getParameterValues("statisticsItem");
	    request.setAttribute("statisticsItem", statisticsItem);
	    String itemArea = "NO";
		String itemComp = "NO";
		String itemStat = "NO";
		String itemHold = "NO";
		String itemUnaccept = "NO";
		String itemAccept = "NO";
		String itemDeal = "NO";
	    if(statisticsItem.length>0){
	    	for(int i=0;i<statisticsItem.length;i++){
	    		if(statisticsItem[i].equals("itemArea")) itemArea = "YES";
	    		if(statisticsItem[i].equals("itemComp")) itemComp = "YES";
	    		if(statisticsItem[i].equals("itemStat")) itemStat = "YES";
	    		if(statisticsItem[i].equals("itemHold")) itemHold = "YES";
	    		if(statisticsItem[i].equals("itemUnaccept")) itemUnaccept = "YES";
	    		if(statisticsItem[i].equals("itemAccept")) itemAccept = "YES";
	    		if(statisticsItem[i].equals("itemDeal")) itemDeal = "YES";   		
	    	}
	    }
		
		ID2NameService service = (ID2NameService) ApplicationContextHolder.getInstance().getBean("ID2NameGetServiceCatch");
		String excalflag = request.getParameter("excal"); //导出标识
		//点击区域、代维公司钻取下层区域方法
		String alinkareaid = "";
		String nextCompid = "";
		if(excalflag!=null && excalflag.equals("yes")){
			alinkareaid = StaticMethod.nullObject2String(request.getParameter("Excelalinkareaid"));
			nextCompid = StaticMethod.nullObject2String(request.getParameter("ExcelalinkcompanyId"));
		}else{
			alinkareaid = StaticMethod.nullObject2String(request.getParameter("alinkareaid"));
			nextCompid = StaticMethod.nullObject2String(request.getParameter("alinkcompanyId"));
		}
		request.setAttribute("alinkareaid", alinkareaid);
		String alinkcompanyId = "";
		 
		request.setAttribute("alinkcompanyId", nextCompid);
		String maintainCompanyId = StaticMethod.nullObject2String(request.getParameter("maintainCompanyId")); //按代维公司查询
		if((!nextCompid.equals("") && nextCompid.length()>0)){
			alinkcompanyId =  nextCompid;
		}else{
			if(!maintainCompanyId.equals("") && maintainCompanyId.length()>0){
				List list = pnrCommonTaskStatisticMgr.getCompanyDeptToIdList(StaticMethod.nullObject2String(maintainCompanyId));
				Map map = (Map)list.get(0);
				alinkcompanyId = StaticMethod.nullObject2String(map.get("id"));
			}
		}
		request.setAttribute("maintainCompanyId", maintainCompanyId);
	    request.setAttribute("maintainCompany", StaticMethod.nullObject2String(request.getParameter("maintainCompany")));
	    List allResult = new ArrayList();
	    List excalList = new ArrayList();
		if(!alinkareaid.equals("") && alinkareaid.length()>0){
			List alinkarealist = pnrCommonTaskStatisticMgr.getAreaIdList(alinkareaid);
			if( alinkarealist.size()>0 ){
				for(int a=0;a<alinkarealist.size();a++){
					Map alinkMap = (Map)alinkarealist.get(a);
					List list = pnrCommonTaskStatisticMgr.getAreaIdToCompanyIdList(StaticMethod.nullObject2String(alinkMap.get("areaid")));
					List lastList = pnrCommonTaskStatisticMgr.getAreaIdList(StaticMethod.nullObject2String(alinkMap.get("areaid")));
					if(list.size()>0){
						for(int i=0;i<list.size();i++){
							Map map = (Map)list.get(i);
							List mapNext = new ArrayList();
							String deptid = StaticMethod.nullObject2String(map.get("dept_mag_id"));
							String companyId = StaticMethod.nullObject2String(map.get("id"));
							List lastCompList = pnrCommonTaskStatisticMgr.getCompanyIdList(companyId);
							String compflag = "0";
							if(lastCompList.size()>0){
								compflag = "1";
							}
							if(!deptid.equals("") && deptid.length()>0){
								List resultList = pnrCommonTaskStatisticMgr.getSheetAreaIndexList( deptid, whereStr);
								if(resultList.size()>0){							
									mapNext.add(service.id2Name(StaticMethod.nullObject2String(alinkMap.get("areaid")), "tawSystemAreaDao"));														
									mapNext.add(service.id2Name(companyId, "partnerDeptDao"));									
									mapNext.add(StaticMethod.nullObject2String(resultList.get(0)));
									mapNext.add(StaticMethod.nullObject2String(resultList.get(1)));
									mapNext.add(StaticMethod.nullObject2String(resultList.get(2)));
									mapNext.add(StaticMethod.nullObject2String(resultList.get(3)));
									mapNext.add(StaticMethod.nullObject2String(resultList.get(4)));
									mapNext.add(StaticMethod.nullObject2String(resultList.get(5)));
									mapNext.add(StaticMethod.nullObject2String(resultList.get(6)));
									mapNext.add(StaticMethod.nullObject2String(resultList.get(7)));
									mapNext.add(StaticMethod.nullObject2String(resultList.get(8)));
									mapNext.add(StaticMethod.nullObject2String(resultList.get(9)));
									mapNext.add(StaticMethod.nullObject2String(resultList.get(10)));
									mapNext.add(StaticMethod.nullObject2String(resultList.get(11)));
									mapNext.add(StaticMethod.nullObject2String(resultList.get(12)));
									mapNext.add(StaticMethod.nullObject2String(resultList.get(13)));
									mapNext.add(StaticMethod.nullObject2String(alinkMap.get("areaid")));	
									mapNext.add(companyId);
									mapNext.add(lastList.size());
									mapNext.add(compflag);
									allResult.add(mapNext);
								}							
							}else{
								mapNext.add(service.id2Name(StaticMethod.nullObject2String(alinkMap.get("areaid")), "tawSystemAreaDao"));											
								mapNext.add(service.id2Name(companyId, "partnerDeptDao"));								
								mapNext.add('0');
								mapNext.add('0');
								mapNext.add('0');
								mapNext.add('0');
								mapNext.add('0');
								mapNext.add('0');
								mapNext.add('0');
								mapNext.add('0');
								mapNext.add('0');
								mapNext.add('0');
								mapNext.add('0');
								mapNext.add('0');
								mapNext.add('0');
								mapNext.add('0');
								mapNext.add(StaticMethod.nullObject2String(alinkMap.get("areaid")));			
								mapNext.add(companyId);
								mapNext.add(lastList.size());
								mapNext.add(0);
								allResult.add(mapNext);
							}
						}	
					}
				}		
			}
			HomeHelper homeHelper = new HomeHelper();
			List spanList = homeHelper.verticalGrowp(1, 0, 0, allResult);
			excalList = spanList;
			request.setAttribute("sessionDeptId", sessionDeptId);
			request.setAttribute("sessionAreaId", sessionAreaId);
			request.setAttribute("itemArea",itemArea);
			request.setAttribute("itemComp",itemComp);
			request.setAttribute("itemStat",itemStat);
			request.setAttribute("itemHold",itemHold);
			request.setAttribute("itemUnaccept",itemUnaccept);
			request.setAttribute("itemAccept",itemAccept);
			request.setAttribute("itemDeal",itemDeal);
			request.setAttribute("querylist", spanList);
		}else if(!alinkcompanyId.equals("") && alinkcompanyId.length()>0){
			List alinkarealist = pnrCommonTaskStatisticMgr.getCompanyIdList(alinkcompanyId);
	
			if( alinkarealist.size()>0 ){
				for(int a=0;a<alinkarealist.size();a++){
					Map alinkMap = (Map)alinkarealist.get(a);
					List mapNext = new ArrayList();
					String deptid = StaticMethod.nullObject2String(alinkMap.get("dept_mag_id"));
					String companyId = StaticMethod.nullObject2String(alinkMap.get("id"));
					List lastList = pnrCommonTaskStatisticMgr.getCompanyIdList(companyId);
					String areaflag = "0";
					String compflag = "0";
					if(lastList.size()>0){
						areaflag = "1";
						compflag = "1";
					}
					if(!deptid.equals("") && deptid.length()>0){
						List resultList = pnrCommonTaskStatisticMgr.getSheetAreaIndexList( deptid, whereStr);
						if(resultList.size()>0){							
							mapNext.add(service.id2Name(StaticMethod.nullObject2String(alinkMap.get("areaid")), "tawSystemAreaDao"));													
							mapNext.add(service.id2Name(companyId, "partnerDeptDao"));							
							mapNext.add(StaticMethod.nullObject2String(resultList.get(0)));
							mapNext.add(StaticMethod.nullObject2String(resultList.get(1)));
							mapNext.add(StaticMethod.nullObject2String(resultList.get(2)));
							mapNext.add(StaticMethod.nullObject2String(resultList.get(3)));
							mapNext.add(StaticMethod.nullObject2String(resultList.get(4)));
							mapNext.add(StaticMethod.nullObject2String(resultList.get(5)));
							mapNext.add(StaticMethod.nullObject2String(resultList.get(6)));
							mapNext.add(StaticMethod.nullObject2String(resultList.get(7)));
							mapNext.add(StaticMethod.nullObject2String(resultList.get(8)));
							mapNext.add(StaticMethod.nullObject2String(resultList.get(9)));
							mapNext.add(StaticMethod.nullObject2String(resultList.get(10)));
							mapNext.add(StaticMethod.nullObject2String(resultList.get(11)));
							mapNext.add(StaticMethod.nullObject2String(resultList.get(12)));
							mapNext.add(StaticMethod.nullObject2String(resultList.get(13)));
							mapNext.add(StaticMethod.nullObject2String(alinkMap.get("areaid")));
							mapNext.add(companyId);
							mapNext.add(areaflag);
							mapNext.add(compflag);
							allResult.add(mapNext);
						}							
					}else{
						mapNext.add(service.id2Name(StaticMethod.nullObject2String(alinkMap.get("areaid")), "tawSystemAreaDao"));											
						mapNext.add(service.id2Name(companyId, "partnerDeptDao"));						
						mapNext.add('0');
						mapNext.add('0');
						mapNext.add('0');
						mapNext.add('0');
						mapNext.add('0');
						mapNext.add('0');
						mapNext.add('0');
						mapNext.add('0');
						mapNext.add('0');
						mapNext.add('0');
						mapNext.add('0');
						mapNext.add('0');
						mapNext.add('0');
						mapNext.add('0');
						mapNext.add(StaticMethod.nullObject2String(alinkMap.get("areaid")));	
						mapNext.add(companyId);
						mapNext.add(areaflag);
						mapNext.add(compflag);
						allResult.add(mapNext);
					}
				}		
			}
			HomeHelper homeHelper = new HomeHelper();
			List spanList = homeHelper.verticalGrowp(1, 0, 0, allResult);
			excalList = spanList;
			request.setAttribute("sessionDeptId", sessionDeptId);
			request.setAttribute("sessionAreaId", sessionAreaId);
			request.setAttribute("itemArea",itemArea);
			request.setAttribute("itemComp",itemComp);
			request.setAttribute("itemStat",itemStat);
			request.setAttribute("itemHold",itemHold);
			request.setAttribute("itemUnaccept",itemUnaccept);
			request.setAttribute("itemAccept",itemAccept);
			request.setAttribute("itemDeal",itemDeal);
			request.setAttribute("querylist", spanList);
		}else{
			//默认查询区域以及条件输入查询区域代码
			if(!toSelectAreaId.equals("") && toSelectAreaId.length()>0){
				List list = pnrCommonTaskStatisticMgr.getAreaIdToCompanyIdList(toSelectAreaId);
				List lastList = pnrCommonTaskStatisticMgr.getAreaIdList(toSelectAreaId);				
				if(list.size()>0){
					for(int i=0;i<list.size();i++){
						Map map = (Map)list.get(i);
						List mapNext = new ArrayList();
						String deptid = StaticMethod.nullObject2String(map.get("dept_mag_id"));
						String companyId = StaticMethod.nullObject2String(map.get("id"));
						List lastCompList = pnrCommonTaskStatisticMgr.getCompanyIdList(companyId);
						String compflag = "0";
						if(lastCompList.size()>0){
							compflag = "1";
						}
						if(!deptid.equals("") && deptid.length()>0){
							List resultList = pnrCommonTaskStatisticMgr.getSheetAreaIndexList( deptid, whereStr);
							if(resultList.size()>0){							
								mapNext.add(service.id2Name(toSelectAreaId, "tawSystemAreaDao"));												
								mapNext.add(service.id2Name(companyId, "partnerDeptDao"));								
								mapNext.add(StaticMethod.nullObject2String(resultList.get(0)));
								mapNext.add(StaticMethod.nullObject2String(resultList.get(1)));
								mapNext.add(StaticMethod.nullObject2String(resultList.get(2)));
								mapNext.add(StaticMethod.nullObject2String(resultList.get(3)));
								mapNext.add(StaticMethod.nullObject2String(resultList.get(4)));
								mapNext.add(StaticMethod.nullObject2String(resultList.get(5)));
								mapNext.add(StaticMethod.nullObject2String(resultList.get(6)));
								mapNext.add(StaticMethod.nullObject2String(resultList.get(7)));
								mapNext.add(StaticMethod.nullObject2String(resultList.get(8)));
								mapNext.add(StaticMethod.nullObject2String(resultList.get(9)));
								mapNext.add(StaticMethod.nullObject2String(resultList.get(10)));
								mapNext.add(StaticMethod.nullObject2String(resultList.get(11)));
								mapNext.add(StaticMethod.nullObject2String(resultList.get(12)));
								mapNext.add(StaticMethod.nullObject2String(resultList.get(13)));
								mapNext.add(toSelectAreaId);	
								mapNext.add(companyId);
								mapNext.add(lastList.size());
								mapNext.add(compflag);
								allResult.add(mapNext);
							}
							
						} 
					}	
				}
				HomeHelper homeHelper = new HomeHelper();
				List spanList = homeHelper.verticalGrowp(1, 0, 0, allResult);	
				excalList = spanList;
				request.setAttribute("sessionDeptId", sessionDeptId);
				request.setAttribute("sessionAreaId", sessionAreaId);
				request.setAttribute("itemArea",itemArea);
				request.setAttribute("itemComp",itemComp);
				request.setAttribute("itemStat",itemStat);
				request.setAttribute("itemHold",itemHold);
				request.setAttribute("itemUnaccept",itemUnaccept);
				request.setAttribute("itemAccept",itemAccept);
				request.setAttribute("itemDeal",itemDeal);
				request.setAttribute("querylist", spanList);
			}
		}
		
		if (excalflag!=null && excalflag.equals("yes")) {  
			List headList = new ArrayList();
			String[] title = { "区域", "代维公司","运行中", "已归档", "已作废", "强制归档",
					"强制作废", "未接工单（未超时）", "未接工单（已超时）", "已受理工单（未超时）", "已受理工单（已超时）",
					"已回复工单（未超时）", "已回复工单（已超时）", "归档工单（满意）", "归档工单（合格）",
					"归档工单（不满意）"};
			List resultList = new ArrayList();
			if(excalList.size()>0){			
				for(int i=0;i<excalList.size();i++){
					List<TdObjModel> list = (List<TdObjModel>)excalList.get(i);
					List newList = new ArrayList();
					for(int j=0;j<(list.size()-4);j++){
						newList.add(StaticMethod.nullObject2String(list.get(j).getName()));
					}
					resultList.add(newList);
				}
				
			}
			HomeHelper homeHelper = new HomeHelper();
			List spanList = homeHelper.verticalGrowp(1, 0, 0, resultList);	
			for(int t=0;t<title.length;t++){
				headList.add(title[t]);
			}
			//执行导出
			String fileName="partner";
			StatisticsResultExport.exportStatisticsResultToXLSFile(true, spanList, headList, fileName, response,request);
			request.setAttribute("alinkcompanyId", "");
			request.setAttribute("alinkareaid", "");
			return null;
		}else {
			//跳转到统计页面
			return mapping.findForward("overall");
		}	
	}
	

	public ActionForward showIndexList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		PnrCommonTaskStatisticMgr pnrCommonTaskStatisticMgr = (PnrCommonTaskStatisticMgr)getBean("pnrCommonTaskStatisticMgr");
		String whereStr = "";
		String yearflag = request.getParameter("year");    //查询年份
	    String monthflag = request.getParameter("month");  //查询月份
	    if((!yearflag.equals("") && yearflag.length()>0) && (!monthflag.equals("") && monthflag.length()>0)){
	    	if(Integer.parseInt(monthflag)<10){
	    		monthflag = "0"+monthflag;
	    	}
	    	whereStr = whereStr+ " and substr(sendtime,0,7)='"+yearflag+"-"+monthflag+"'";
	    }
	    String startTime = request.getParameter("startTime"); //查询开始时间
	    String endTime   = request.getParameter("endTime"); //查询结束时间
	    if((!startTime.equals("") && startTime.length()>0) && (!endTime.equals("") && endTime.length()>0)){
	    	whereStr = whereStr+ " and sendtime>='"+startTime+" 00:00:00' and sendtime<='"+endTime+" 23:59:59'";
	    }    
	    if(yearflag.equals("") && monthflag.equals("") && startTime.equals("") && endTime.equals("")){
	    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");//设置日期格式
			String createtime = df.format(new Date());// new Date()为获取当前系统时间
			whereStr = whereStr+ " and substr(sendtime,0,7)='"+createtime+"'";
			
	    }
	    String sendUser  = request.getParameter("sendUser"); //按派单人查询
	    if((!sendUser.equals("") && sendUser.length()>0)){
	    	whereStr = whereStr+ " and senduserid='"+sendUser+"'";
	    }
	    String sendDept  = request.getParameter("sendDept"); //按派单部门查询
	    if((!sendDept.equals("") && sendDept.length()>0)){
	    	whereStr = whereStr+ " and senddeptid='"+sendDept+"'";
	    }
	    String acceptUser= request.getParameter("acceptUser"); //按处理人查询
	    if((!acceptUser.equals("") && acceptUser.length()>0)){
	    	whereStr = whereStr+ " and operateuserid='"+acceptUser+"'";
	    }
	    String acceptDept= request.getParameter("acceptDept"); //按处理部门查询
	    if((!acceptDept.equals("") && acceptDept.length()>0)){
	    	whereStr = whereStr+ " and operatedeptid='"+acceptDept+"'";
	    }
	    String companyId = request.getParameter("companyId"); //按代维公司查询	
	    
	    String mainSpecialty = request.getParameter("mainSpecialty"); //按代维专业查询
	    if((!mainSpecialty.equals("") && mainSpecialty.length()>0)){
	    	whereStr = whereStr+ " and mainspecialty='"+mainSpecialty+"'";
	    } 
	    String mainTaskType =  request.getParameter("mainTaskType"); //按任务类型查询
	    if((!mainTaskType.equals("") && mainTaskType.length()>0)){
	    	whereStr = whereStr+ " and maintasktype='"+mainTaskType+"'";
	    }
	    String flag =request.getParameter("flag");
	    int total = Integer.parseInt(request.getParameter("total"));
	    String deptid = pnrCommonTaskStatisticMgr.getCompanyIdToDeptIdList(companyId);
	    
	    String pageIndexName = new org.displaytag.util.ParamEncoder("querylist").encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes().getPageSize();
		final Integer pageIndex = new Integer(GenericValidator.isBlankOrNull(request.getParameter(pageIndexName)) ? 0 : (Integer.parseInt(request.getParameter(pageIndexName)) - 1));

		Map map = (Map)pnrCommonTaskStatisticMgr.querySheetIndexList(total, pageIndex, pageSize, deptid, flag, whereStr);
		List list = (List) map.get("result");
		request.setAttribute("querylist", list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("sheetlist");
	}
 }
 



