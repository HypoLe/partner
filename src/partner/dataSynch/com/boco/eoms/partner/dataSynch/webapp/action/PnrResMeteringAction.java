package com.boco.eoms.partner.dataSynch.webapp.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.model.PageModel;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.partner.dataSynch.mgr.ISynchExceptionRecordMgr;
import com.boco.eoms.partner.dataSynch.model.SynchExceptionRecord;
import com.boco.eoms.partner.dataSynch.util.DataSynchJdbcUtil;

/** 
 * Description: 计次费用明细表Action
 * Copyright:   Copyright (c)2012
 * Company:     BOCO
 * @author:     zhangkeqi
 * @version:    1.0
 * Create at:   12 17, 2012 5:26:45 PM
 */
public class PnrResMeteringAction extends BaseAction {
	
	/**
	 * 查询展示异常列表
	 */
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		ISynchExceptionRecordMgr synchExceptionRecordMgr = (ISynchExceptionRecordMgr) getBean("synchExceptionRecordMgr");
		SynchExceptionRecord record = new SynchExceptionRecord();
		record.setDataType(request.getParameter("dataType"));
		record.setCuid(request.getParameter("createTime"));
		
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				"list")
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = Integer.valueOf(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		PageModel pm = synchExceptionRecordMgr.findSynchExceptionRecordList(pageIndex*pageSize, pageSize,record);
		request.setAttribute("list", pm.getDatas());
		request.setAttribute("size", pm.getTotal());
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("list"); 
	}
	
	
	/**
	 * 计次费用明细
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward pnrResMeteringDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		String eventId = StaticMethod.null2String(request.getParameter("eventId"));
		String sheet = StaticMethod.null2String(request.getParameter("sheet"));
		
		List<Map<String,Object>> pnrResMeteringDetail = this.getPnrResMeteringDetail(eventId,sheet);
		
		request.setAttribute("pnrResMeteringDetail", pnrResMeteringDetail);
		return mapping.findForward("pnrResMeteringDetail");
	}
	
	/**
	 * 查询工单计次明细列表
	 */
/*	public ActionForward findSheetMeteringList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		MeteringService meteringService = (MeteringService)getBean("meteringService");
		//查询条件
		String eventTitle = StaticMethod.null2String(request.getParameter("eventTitle"));
		String eventType = StaticMethod.null2String(request.getParameter("eventType"));
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("eventTitle", eventTitle);
		paramMap.put("eventType", eventType);
		final Integer pageSize = CommonConstants.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
		final int pageIndex = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		
		PageModel pm = meteringService.findSheetMeteringList(paramMap, pageIndex*pageSize, pageSize);
		request.setAttribute("list", pm.getDatas());
		request.setAttribute("size", pm.getTotal());
		request.setAttribute("pagesize", pageSize);
		
		return mapping.findForward("sheetMeteringList");
	}*/
	
	/**
	 * 计次费用明细列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ActionForward pnrResMeteringSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		//where s_year=?
		//and s_mon=?
		//and province_comany_id=?
		//and region_comany_id=?
		//and city_comany_id=?;
		String s_year = StaticMethod.null2String(request.getParameter("s_year"));
		String s_mon = StaticMethod.null2String(request.getParameter("s_mon"));
		String province_comany_id = StaticMethod.null2String(request.getParameter("province_comany_id"));
		String region_comany_id = StaticMethod.null2String(request.getParameter("region_comany_id"));
		String city_comany_id = StaticMethod.null2String(request.getParameter("city_comany_id"));
		String metering_type = StaticMethod.null2String(request.getParameter("metering_type"));
		String specialty = StaticMethod.null2String(request.getParameter("specialty"));
		boolean queryAllFlag = true;
		if(!s_year.equals("") || !s_mon.equals("") || !province_comany_id.equals("") || !region_comany_id.equals("") || !city_comany_id.equals("")|| !metering_type.equals("")|| !specialty.equals("")) {
			queryAllFlag = false;
		}
		request.setAttribute("s_year", s_year);
		request.setAttribute("s_mon", StaticMethod.null2int(s_mon));
		request.setAttribute("province_comany_id", province_comany_id);
		request.setAttribute("region_comany_id", region_comany_id);
		request.setAttribute("city_comany_id", city_comany_id);

		/*导出判断 begin*/
		int pageIndex = 0;
		int pageSize = 0;
		String exportType = StaticMethod
		.null2String(request
				.getParameter(new org.displaytag.util.ParamEncoder(
						"pnrResMeteringList")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_EXPORTTYPE)));
		request.setAttribute("exportType", exportType);
		if(!exportType.equals("")) {
			pageSize = new Integer(-1);
		} else {
			pageIndex = CommonUtils.getFirstResultOfDisplayTag(request, "pnrResMeteringList");
			pageSize = 15;
		}
		int skip = pageIndex*pageSize;
		/*导出判断 end*/
		
		String fromAndWhere = "";
		if(queryAllFlag) {
			fromAndWhere = " from pnr_res_metering ";
		} else {
			fromAndWhere =" from pnr_res_metering where 1=1 ";
			
			if(!s_year.equals("")) {
				fromAndWhere += "and s_year='"+s_year+"' ";
			}
			if(!s_mon.equals("")) {
				fromAndWhere += "and s_mon='"+s_mon+"' ";
			}
			if(!province_comany_id.equals("")) {
				fromAndWhere += "and province_comany_id='"+province_comany_id+"' ";
			}
			if(!region_comany_id.equals("")) {
				fromAndWhere += "and region_comany_id='"+region_comany_id+"' ";
			}
			if(!city_comany_id.equals("")) {
				fromAndWhere += "and city_comany_id='"+city_comany_id+"' ";
			}
			if(!metering_type.equals("")) {
				fromAndWhere += "and metering_type_id='"+metering_type+"' ";
			}
			if(!specialty.equals("")) {
				fromAndWhere += "and specialty='"+specialty+"' ";
			}
		}
		
		String sql = "";
		if(pageSize == -1) {
			sql = "select skip "+skip+" ";
		} else {
			sql = "select skip "+skip+" limit "+pageSize+" ";
		}
		sql +=  " s_year ,"+       
				"s_mon ,"+              
				"soa_get_deptname(province_comany_id,'province') as deptname_province,"+              
				"soa_get_deptname(region_comany_id,'region') as deptname_region,"+              
				"soa_get_deptname(city_comany_id,'city') as deptname_city,"+              
				"res_name ,"+              
				"sheetid ,"+              
				"sheet_sysid ,"+              
				"sheet_title,"+              
				"metering_type,"+              
				"specialty_name ,"+              
				"nvl(result_for_res_type,0)+"+
				"nvl(result_for_res_level,0)+"+
				"nvl(result_for_dev_type,0)+"+
				"nvl(result_for_env,0)+"+
				"nvl(result_for_region,0)+"+
				"nvl(result_for_intime,0)+"+
				"nvl(result_for_archive,0) as tot_num,"+              
				"res_type_name ,"+              
				"nvl(result_for_res_type,0) as result_for_res_type,"+              
				"res_level_name ,"+              
				"nvl(result_for_res_level,0) as result_for_res_level,"+              
				"dev_type_name ,"+              
				"nvl(result_for_dev_type,0) as result_for_dev_type,"+              
				"env_features_name ,"+              
				"nvl(result_for_env,0) as result_for_env,"+              
				"region_type_name ,"+              
				"nvl(result_for_region ,0) as result_for_region,"+       
				"intime_level,"+              
				"nvl(result_for_intime,0) as result_for_intime,"+             
				"archive_level ,"+              
				"nvl(result_for_archive,0) as result_for_archive,"+          
				"soa_get_username(operateuserid) as username "+fromAndWhere;
		String countSql = "select count(*) " + fromAndWhere;
		
		Map<String,Object> map = getDataSynchResultList(sql,countSql);
		List<Map<String,Object>> resultList = (List<Map<String,Object>>)map.get("result");
		int count = (Integer)map.get("count");
		
		request.setAttribute("pnrResMeteringList",resultList);
		request.setAttribute("pagesize", pageSize);
		request.setAttribute("size", count);
		
		//ActionForward forward = new ActionForward("WEB-INF/pages/partner/netresource/pnrResMeteringList.jsp",true);
		
		return mapping.findForward("pnrResMeteringList");
	}
	
	public Map<String,Object> getDataSynchResultList(String sql,String countSql) {
		Map<String,Object> map = new HashMap<String,Object>();
		
		DataSynchJdbcUtil jdbcUtil = (DataSynchJdbcUtil)ApplicationContextHolder.getInstance().getBean("dataSynchJdbcUtil");
		Connection conn = null;
		Statement stmt = null;
		List<Map<String,Object>> resultList = null;
		try {
			conn = jdbcUtil.getCon();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			resultList = DataSynchJdbcUtil.resultSet2ListMap(rs);
			
			map.put("result", resultList);
			
			ResultSet crs = stmt.executeQuery(countSql);
			int count = 0;
			while(crs.next()) {
				count = crs.getInt(1);
				break;
			}
			
			map.put("count", count);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try{
				if(stmt != null){
					stmt.close();
				}
				if(conn != null) {
					if(!conn.isClosed()) {
						conn.close();
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return map;
	}
	public List<Map<String,Object>> getPnrResMeteringDetail(String eventId,String sheet) {

		List<Map<String,Object>> resultList = null;
		String sql = "select price,formula,formulavalue,eventnumber,charging,physicalproperty from pnr_metering_main " +
				"where eventkey= '"+eventId+"' and eventtype='"+sheet+"'";
		
		DataSynchJdbcUtil jdbcUtil = (DataSynchJdbcUtil)ApplicationContextHolder.getInstance().getBean("dataSynchJdbcUtil");
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = jdbcUtil.getCon();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			resultList = DataSynchJdbcUtil.resultSet2ListMap(rs);
			System.out.println(resultList.size());
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try{
				if(stmt != null){
					stmt.close();
				}
				if(conn != null) {
					if(!conn.isClosed()) {
						conn.close();
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return resultList;
	}
}
