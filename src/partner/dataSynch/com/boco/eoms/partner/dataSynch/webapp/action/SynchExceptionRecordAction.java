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
 * Description: 同步异常数据Action
 * Copyright:   Copyright (c)2012
 * Company:     BOCO
 * @author:     LiuChang
 * @version:    1.0
 * Create at:   Apr 17, 2012 5:26:45 PM
 */
public class SynchExceptionRecordAction extends BaseAction {
	
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
	 * 查询展示异常列表
	 */
	public ActionForward inspectFeeDetailSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		//年度、月份、专业、地区代维公司名、区县维护中心名
		String s_year = StaticMethod.null2String(request.getParameter("s_year"));
		String s_mon = StaticMethod.null2String(request.getParameter("s_mon"));
		String special_name = StaticMethod.null2String(request.getParameter("special_name"));
		String region_m_name = StaticMethod.null2String(request.getParameter("region_m_name"));
		String city_m_name = StaticMethod.null2String(request.getParameter("city_m_name"));
		boolean queryAllFlag = true;
		if(!s_year.equals("") || !s_mon.equals("") || !special_name.equals("") || !region_m_name.equals("") || !city_m_name.equals("")) {
			queryAllFlag = false;
		}
		request.setAttribute("s_year", s_year);
		request.setAttribute("s_mon", StaticMethod.null2int(s_mon));
		request.setAttribute("special_name", special_name);
		request.setAttribute("region_m_name", region_m_name);
		request.setAttribute("city_m_name", city_m_name);

		int pageIndex = CommonUtils.getFirstResultOfDisplayTag(request, "inspectFeeDetailList");
		int pageSize = 15;
		int skip = pageIndex*pageSize;
		
		String fromAndWhere = "";
		if(queryAllFlag) {
			fromAndWhere = " from inspect_fee_detail ";
		} else {
			fromAndWhere =" from inspect_fee_detail where 1=1 ";
			
			if(!s_year.equals("")) {
				fromAndWhere += "and s_year='"+s_year+"' ";
			}
			if(!s_mon.equals("")) {
				fromAndWhere += "and s_mon='"+s_mon+"' ";
			}
			if(!special_name.equals("")) {
				fromAndWhere += "and special_name like '%"+special_name+"%' ";
			}
			if(!region_m_name.equals("")) {
				fromAndWhere += "and region_m_name like '%"+region_m_name+"%' ";
			}
			if(!city_m_name.equals("")) {
				fromAndWhere += "and city_m_name like '%"+city_m_name+"%' ";
			}
		}
		String sql = "select skip "+skip+" limit "+pageSize+" * "+fromAndWhere;
		String countSql = "select count(*) " + fromAndWhere;
		
		Map<String,Object> map = getDataSynchResultList(sql,countSql);
		List<Map<String,Object>> resultList = (List<Map<String,Object>>)map.get("result");
		int count = (Integer)map.get("count");
		
		request.setAttribute("inspectFeeDetailList",resultList);
		request.setAttribute("pagesize", pageSize);
		request.setAttribute("size", count);
		
		//ActionForward forward = new ActionForward("WEB-INF/pages/partner/netresource/inspectFeeDetailList.jsp",true);
		
		return mapping.findForward("inspectFeeDetailList");
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
}
