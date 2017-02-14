package com.boco.eoms.deviceManagement.faultSheet.action;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.common.util.StaticMethod;
import com.boco.eoms.commons.system.dict.service.ID2NameService;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcService;
import com.boco.eoms.deviceManagement.faultInfo.utils.CommonConstants;
import com.boco.eoms.deviceManagement.faultInfo.utils.CommonUtils;
import com.boco.eoms.deviceManagement.faultSheet.model.FaultSheetManagement;
import com.boco.eoms.deviceManagement.faultSheet.model.TdModel;
import com.boco.eoms.deviceManagement.faultSheet.service.FaultSheetManagementService;
//import com.boco.eoms.deviceManagement.faultSheet.service.WorkOrderNoDaoService;
import com.google.common.base.Strings;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

public class FaultSheetStatisticsAction extends BaseAction {
	/**
	 * 跳转到统计页面
	 * */
	public ActionForward goToStatisticePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("goToStatisticePage");
	}
	/**
	 * 统计故障工单Action
	 * */
	public ActionForward statisticsList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
//		WorkOrderNoDaoService workOder = (WorkOrderNoDaoService)ApplicationContextHolder.getInstance().getBean("WorkOrderNoDaoService");
		String startTime = StaticMethod.null2String(request.getParameter("startTime"),"");
		String endTime = StaticMethod.null2String(request.getParameter("endTime"),"");
		String deptId=StaticMethod.null2String(request.getParameter("deptId"),"");
//		Search search = new Search();
//		if("".equals(startTime)){
//			search.addFilterGreaterOrEqual("operateTime", startTime);
//			
//		}
//		if("".equals(endTime)){
//			search.addFilterLessOrEqual("operateTime", endTime);
//			
//		}
//		if("".equals(deptId)){
//			search.addFilterEqual("detailment_dept", deptId);
//			
//		}
//		//计算总共工单数目
//		int sum = workOder.searchAndCount(search).getTotalCount();
//		// 添加条件计算接单及时数目
//		Search searchGetTime = search.copy();
//		searchGetTime.addFilterEqual("areintimereceived", "1");
//		int getInTime = workOder.searchAndCount(searchGetTime).getTotalCount();
//		//添加条件计算处理及时数目
		CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) ApplicationContextHolder.getInstance().getBean("commonSpringJdbcService");
		String whereCondition =" where faultstate='归档' and iddeleted='0'";
		if(!"".equals(startTime)){
			whereCondition+=" and operateTime >= '"+startTime+"'";
		}
		if(!"".equals(endTime)){
			whereCondition+=" and operateTime <= '"+endTime+"'";
		}
		if(!"".equals(deptId)){
			whereCondition+=" and detailment_dept = '"+deptId+"'";
		}
		//计算总共工单数目
		String sumSql = "select detailment_dept,count(id) from dm_faultsheetmanagement " +
		whereCondition +
				" group by  detailment_dept order by detailment_dept"; 	 	
		List<ListOrderedMap> sumList = jdbcService.queryForList(sumSql);
//		// 添加条件计算接单及时数目
//		String getInTimeSql = "select detailment_dept,count(id) from dm_faultsheetmanagement " +
//		whereCondition +
//		" and areintimereceived='1' group by  detailment_dept order by detailment_dept"; 
//		List<ListOrderedMap> getInTimeList = jdbcService.queryForList(getInTimeSql);
//		//添加条件计算处理及时数目
//		String compleInTimeSql = "select detailment_dept,count(id) from dm_faultsheetmanagement " +
//		whereCondition +
//		" and areintimecomplete='1' group by  detailment_dept order by detailment_dept"; 
//		List<ListOrderedMap> compleInTimeList = jdbcService.queryForList(compleInTimeSql);
		//构建最后显示的list
		List<List<TdModel>> list = new ArrayList<List<TdModel>>();
		List<String > headList = new ArrayList<String >();
		headList.add("执行部门");
		headList.add("完成工单总数");
		headList.add("及时接单总数");
		headList.add("及时完成总数");
		headList.add("接单及时率");
		headList.add("完成及时率");
		// 计算及时率和超时率
		for(int i=0 ;i<sumList.size();i++){
			ListOrderedMap sumMap = sumList.get(i);
//			 添加条件计算接单及时数目
			String getInTimeSql = "select detailment_dept,count(id) from dm_faultsheetmanagement " +
			whereCondition +"and detailment_dept='"+(String)sumMap.get("detailment_dept")+"'"+
			" and areintimereceived='1' group by  detailment_dept order by detailment_dept"; 
			List<ListOrderedMap> getInTimeList = jdbcService.queryForList(getInTimeSql);
			//添加条件计算处理及时数目
			String compleInTimeSql = "select detailment_dept,count(id) from dm_faultsheetmanagement " +
			whereCondition +"and detailment_dept='"+(String)sumMap.get("detailment_dept")+"'"+
			" and areintimecomplete='1' group by  detailment_dept order by detailment_dept"; 
			List<ListOrderedMap> compleInTimeList = jdbcService.queryForList(compleInTimeSql);
			ListOrderedMap getInTimeMap=null ;
			if(getInTimeList.size()!=0){
			 getInTimeMap = getInTimeList.get(0);
			
			}
			ListOrderedMap compleInTimeMap=null;
			if(compleInTimeList.size()!=0){
				compleInTimeMap = compleInTimeList.get(0);
				
				}
			
		   List<TdModel> tdList = new ArrayList<TdModel>();
		   TdModel td= new TdModel();
		   ID2NameService service = (ID2NameService) ApplicationContextHolder
			.getInstance().getBean("ID2NameGetServiceCatch");
		   String id2name=service.id2Name((String)sumMap.get("detailment_dept"), "tawSystemDeptDao");
		   td.setName(id2name);
		   tdList.add(td);
		   td=new TdModel();
		   td.setName(sumMap.get("(count)").toString());
		   tdList.add(td);
		   td=new TdModel();
		   String getIn = getInTimeMap!=null?getInTimeMap.get("(count)").toString():"0";
		   td.setUrl("/faultSheethz/faulSheetStatistics.do?method=detailList&startTime="+startTime+"&endTime="+endTime+"&deptId="+(String)sumMap.get("detailment_dept")+"&areintimereceived=1");
		   td.setName(getIn);
		   tdList.add(td);
		   td=new TdModel();
		   String compleIn =getInTimeMap!=null?compleInTimeMap.get("(count)").toString():"0";
		   td.setUrl("/faultSheethz/faulSheetStatistics.do?method=detailList&startTime="+startTime+"&endTime="+endTime+"&deptId="+(String)sumMap.get("detailment_dept")+"&areintimecomplete=1");
		   td.setName(compleIn);
		   tdList.add(td);
		   Float getInTime = new Float(getIn)/new Float((sumMap.get("(count)").toString()))*100;
		   Float compleInTime = new Float(compleIn)/new Float((sumMap.get("(count)").toString()))*100;
		   String getInTimeS="";
		   if(getInTime==0.0){
			    getInTimeS="0%";
		   }else{
		      getInTimeS = String.valueOf(getInTime).substring(0,5)+"%";
		   }
		     td=new TdModel();
		     td.setName(getInTimeS);
		    
		     tdList.add(td);
		     String compleInTimeS ="";
		     if(compleInTime==0.0){
		    	 compleInTimeS="0%";
			   }else{
				   compleInTimeS = String.valueOf(compleInTime).substring(0,5)+"%";
			   }
		     td=new TdModel();
		     td.setName(compleInTimeS);
		    
		     tdList.add(td);
		     
		   list.add(tdList);
		}
		
		
		request.setAttribute("headList", headList);
		request.setAttribute("tableList", list);
		return mapping.findForward("statisticsList");
	}
	/**
	 * 统计故障工单钻取
	 * */
	public ActionForward detailList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String areintimereceived = StaticMethod.null2String((String)request.getParameter("areintimereceived"),"");
		String areintimecomplete = StaticMethod.null2String((String)request.getParameter("areintimecomplete"),"");
		String startTime = StaticMethod.null2String(request.getParameter("startTime"),"");
		String endTime = StaticMethod.null2String(request.getParameter("endTime"),"");
		String deptId=StaticMethod.null2String(request.getParameter("deptId"),"");
		Search search = new Search();
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(),
				search);
		String work_OrderNo = request.getParameter("work_OrderNo");
		String themess = request.getParameter("themess");

		if (null != work_OrderNo && null != themess) {
			search.addFilterILike("work_OrderNo", "%" + work_OrderNo + "%")
					.addFilterILike("themess", "%" + themess + "%");
		}
		search.addFilterEqual("idDeleted", "0");
		search.addFilterEqual("faultState", "归档");
		
		if(!"".equals(startTime)){
			search.addFilterGreaterThan("operateTime", startTime);
		}
		if(!"".equals(endTime)){
			search.addFilterLessThan("operateTime", endTime);
		}
		if(!"".equals(deptId)){
			search.addFilterEqual("detailment_dept", deptId);
		}
		if(!"".equals(areintimereceived)){
			search.addFilterEqual("areInTimeReceived", "1");
		}
		if(!"".equals(areintimecomplete)){
			search.addFilterEqual("areInTimeComplete", "1");
		}
		
		
		
		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"faultSheetList")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.addSort("operateTime", true);
		FaultSheetManagementService fs = (FaultSheetManagementService) ApplicationContextHolder.getInstance().getBean("FaultSheetManagementService");
		SearchResult<FaultSheetManagement> searchResult = fs
				.searchAndCount(search);
		List<FaultSheetManagement> faultSheetList = searchResult.getResult();
		request.setAttribute("faultSheetList", faultSheetList);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("bsfrList", searchResult.getResult());
		request.setAttribute("size", searchResult.getTotalCount());	 	
		return mapping.findForward("detailList");
	}
	
}
