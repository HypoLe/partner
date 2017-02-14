package com.boco.eoms.partner.netresource.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.loging.BocoLog;
import com.boco.eoms.deviceManagement.common.pojo.EomsSearch;
import com.boco.eoms.deviceManagement.common.utils.CommonConstants;
import com.boco.eoms.deviceManagement.common.utils.CommonSqlHelper;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.partner.baseinfo.model.PnrOrgFinalistSheet;
import com.boco.eoms.partner.dataSynch.util.DataSynchConstant;
import com.boco.eoms.partner.dataSynch.util.DataSynchJdbcUtil;
import com.boco.eoms.partner.deviceInspect.util.TableNameMappingNetres;
import com.boco.eoms.partner.netresource.service.IEomsService;
import com.boco.eoms.partner.netresource.service.IPnrNetResourceService;
import com.boco.eoms.partner.res.mgr.PnrResConfigMgr;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.googlecode.genericdao.search.SearchResult;


public class PnrNetResourceAction extends BaseAction {
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("gotoAddPnrOrgFinalistSheet");
	}


	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String id = StaticMethod.null2String(request.getParameter("id"));
		if(!"".equals(id)) {
			return this.edit(mapping, form, request, response);
		}
		
		IEomsService eomsService= (IEomsService)this.getBean("eomsService");
		PnrOrgFinalistSheet pofs = new PnrOrgFinalistSheet();
		BeanUtils.populate(pofs, request.getParameterMap());
		pofs.setAddTime(StaticMethod.getCurrentDateTime());
		pofs.setAddUser(this.getUserId(request));
		
		eomsService.save(pofs);
		
		return mapping.findForward("success");
	}
	
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		IEomsService eomsService= (IEomsService)this.getBean("eomsService");
		String id = StaticMethod.null2String(request.getParameter("id"));
		PnrOrgFinalistSheet pofs = new PnrOrgFinalistSheet();
		BeanUtils.populate(pofs, request.getParameterMap());
    	
		boolean flag = eomsService.save(pofs);
		return mapping.findForward("success");
	}
	
	/**
	 * 
	 * 方法说明： 跳转列表页面
	 * 作   者: zhangkeqi
	 * 创建时间: Aug 28, 2012-4:09:47 PM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward gotoListPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String model = StaticMethod.null2String(request.getParameter("model"));
		Class klass = Class.forName(model);
		String klassName = model.substring(model.lastIndexOf(".")+1,model.length());
		String disParam = CommonUtils.lowerFirst(klassName)+"List";
		
		IEomsService eomsService = (IEomsService) this.getBean("eomsService");
		
		EomsSearch eomsSearch = new EomsSearch();
		eomsSearch.setSearchClass(klass);
		int firstResult = CommonUtils.getFirstResultOfDisplayTag(request, disParam);
		eomsSearch.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		eomsSearch.setMaxResults(CommonConstants.PAGE_SIZE);
		eomsSearch = CommonUtils.getSqlFromRequestMap(request, eomsSearch);
		//search.addSortDesc("createTime");
		SearchResult searchResult = eomsService.searchAndCount(eomsSearch);
		List modelList = searchResult.getResult();
		
		request.setAttribute(disParam,modelList);
		request.setAttribute("model", model);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("size", searchResult.getTotalCount());
		
		//获取最近同步数据结果
		Map<String,Object> dataSynchResult = this.getDataSynchInfo(klassName);
		request.setAttribute("dataSynchResult", dataSynchResult);
		//设置是否开启手动Excel同步
		Map<String,String> map = DataSynchConstant.getHandleSynchConfig();
		String openHandleSynch = map.get("is-open");
		request.setAttribute("openHandleSynch", openHandleSynch);
		
     	return mapping.findForward("goto"+klassName+"ListPage");
	}
	
	
	/**
	 * 
	 * 方法说明： 跳转同步变量页面
	 * 作   者: zhangkeqi
	 * 创建时间: Aug 28, 2012-4:09:47 PM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward gotoDataSynchResultPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String model = StaticMethod.null2String(request.getParameter("model"));
		String synchType = StaticMethod.null2String(request.getParameter("synchType"));
		Class klass = Class.forName(model);
		String klassName = model.substring(model.lastIndexOf(".")+1,model.length());
		String disParam = CommonUtils.lowerFirst(klassName)+"DSRList";
		
		int firstResult = CommonUtils.getFirstResultOfDisplayTag(request, disParam);
		String datasynch_result_id = StaticMethod.null2String(request.getParameter("datasynch_result_id"));
		
		String table = className2DBTable(klassName);
		Map<String,Object> map = getDataSynchResultList(table,datasynch_result_id,firstResult,30);
		List<Map<String,Object>> resultList = (List<Map<String,Object>>)map.get("result");
		int count = (Integer)map.get("count");
		
		request.setAttribute(disParam,resultList);
		request.setAttribute("pagesize", resultList.size());
		request.setAttribute("size", count);
		request.setAttribute("model", model);
		request.setAttribute("synchType", synchType);
		
		//获取最近同步数据结果
		Map<String,Object> dataSynchResult = this.getDataSynchInfo(klassName);
		request.setAttribute("dataSynchResult", dataSynchResult);
		
		return mapping.findForward("goto"+klassName+"DSRListPage");
	}
	
	
	public Map<String,Object> getDataSynchResultList(String table,String datasynch_result_id,int pageIndex,int pageSize) {
		Map<String,Object> map = new HashMap<String,Object>();
		
		DataSynchJdbcUtil jdbcUtil = (DataSynchJdbcUtil)ApplicationContextHolder.getInstance().getBean("dataSynchJdbcUtil");
		Connection conn = null;
		Statement stmt = null;
		List<Map<String,Object>> resultList = null;
		try {
			conn = jdbcUtil.getCon();
			stmt = conn.createStatement();
			int skip = pageIndex*pageSize;
			String sql = "";
			sql = CommonSqlHelper.formatPageSql("select t.* from "+table+"_cr t where datasynch_result_id='"+datasynch_result_id+"'", skip, pageSize);
//			String sql = "select * from "+table+"_cr where datasynch_result_id='4a10addea57140039ac235d95c60c176'";
			ResultSet rs = stmt.executeQuery(sql);
			resultList = DataSynchJdbcUtil.resultSet2ListMap(rs);
			
			map.put("result", resultList);
			
			String countSql = "select count(*) from "+table+"_cr where datasynch_result_id='"+datasynch_result_id+"'";
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
	
	public Map<String,Object> getDataSynchInfo(String className) {
		Map<String,Object> infoMap = new HashMap<String,Object>();
		String table = className2DBTable(className);
		
		String date = StaticMethod.getCurrentDateTime("yyyy-MM-dd")+" 23:59:59";
		DataSynchJdbcUtil jdbcUtil = (DataSynchJdbcUtil)ApplicationContextHolder.getInstance().getBean("dataSynchJdbcUtil");
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = jdbcUtil.getCon();
			String sql = "";
			if(CommonSqlHelper.isOracleDialect()) {
				sql = "select * from (select * from irms_datasynch_result where datasynch_model=? and datasynch_time < ? and datasynch_type = ? order by datasynch_time desc) where rownum=1";
			} else {
				sql = "select first 1 * from irms_datasynch_result where datasynch_model=? and datasynch_time < ? and datasynch_type = ? order by datasynch_time desc";
			}
			ps = (PreparedStatement) conn.prepareStatement(sql);
			ps.setString(1, table);
			ps.setString(2, date);
			ps.setString(3, "add");
			ResultSet setAdd = ps.executeQuery();
			
			//dataSynchAddResultId和dataSynchDeleteResultId在jsp页面弄反了，所以这里也返过来
			while(setAdd.next()){
				infoMap.put("dataSynchDeleteResultId",setAdd.getString(1));
				infoMap.put("dataSynchAddResultCount",setAdd.getInt(3));
				infoMap.put("datasynchTime",setAdd.getString(4));
			}
			
			ps.setString(3, "delete");
			ResultSet setDelete = ps.executeQuery();
			while(setDelete.next()){
				infoMap.put("dataSynchAddResultId",setDelete.getString(1));
				infoMap.put("datasynchTime",setDelete.getString(4));
				infoMap.put("dataSynchDeleteResultCount",setDelete.getInt(3));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try{
				if(ps != null){
					ps.close();
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
		return infoMap;
	}
	
	/**
	 * 
	 * 方法说明：跳转到详情页面
	 * 作   者: zhangkeqi
	 * 创建时间: Aug 28, 2012-5:06:54 PM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward gotoDetailPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String id = StaticMethod.null2String(request.getParameter("id"));
		String model = StaticMethod.null2String(request.getParameter("model"));
		Class klass = Class.forName(model);
		
		IEomsService eomsService = (IEomsService)this.getBean("eomsService");
		eomsService.setPersistentClass(klass);
		
		Object obj = null;
		obj = eomsService.find(id);
		
		request.setAttribute(model.substring(model.lastIndexOf(".")+1,model.length()).toLowerCase(), obj);
		return mapping.findForward("goto"+model.substring(model.lastIndexOf(".")+1,model.length())+"DetailPage");
	}
	
	
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		response.setCharacterEncoding("utf-8");
		try {
			IEomsService eomsService= (IEomsService)this.getBean("eomsService");
			String id = request.getParameter("id");
			Object pofs = eomsService.find(id);
			eomsService.remove(pofs);
			
			response.getWriter().write(
					new Gson().toJson(new ImmutableMap.Builder<String, String>()
							.put("success", "true")
							.put("msg", "ok")
							.put("infor", "删除成功！").build()));
			
		} catch (RuntimeException e) {
			BocoLog.info(this, "删除出错！");
			e.printStackTrace();
			response.getWriter().write(
					new Gson().toJson(new ImmutableMap.Builder<String, String>()
							.put("success", "true")
							.put("msg", "ok")
							.put("infor", "删除出错！").build()));
		} finally {
			return null;
		}
	}
	
	public static String className2DBTable(String className) {
		char[] sqlChar = className.toCharArray();
		char c;
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<sqlChar.length;i++) {
			c = sqlChar[i];
			if(i==0) {
				c = Character.toLowerCase(c);
				sb.append(c);
			} else if(Character.isUpperCase(c)) {
				if(i != (sqlChar.length - 1)) {
					c = sqlChar[i];
					c = Character.toLowerCase(c);
					sb.append("_");
					sb.append(c);
					continue;
				}
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	/**
	 * 同步网络资源到巡检资源
	 */
	public ActionForward synchNetResToResConfig(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String synchResultId = request.getParameter("id");//irms_datasynch_result表id
		String model = request.getParameter("model"); //网络资源类型
		try{
			IPnrNetResourceService pnrNetResourceService= (IPnrNetResourceService)this.getBean("pnrNetResourceService");
			pnrNetResourceService.synchNetResToResConfig(synchResultId,model);
		}catch(Exception e){
			e.printStackTrace();
			response.getOutputStream().print("fault");
			return null;
		}
		response.getOutputStream().print("success");
		return null;
	}
	
	/**
	 * 网络资源同步统计
	 */
	@SuppressWarnings("unchecked")
	public ActionForward netResourceCount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IPnrNetResourceService pnrNetResourceService= (IPnrNetResourceService)this.getBean("pnrNetResourceService");
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
		TableNameMappingNetres util = new TableNameMappingNetres();
		Map<String, String> map = util.loadModelMapping();
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		List list = pnrNetResourceService.netResourceCount(firstResult*CommonConstants.PAGE_SIZE, CommonConstants.PAGE_SIZE);
		request.setAttribute("list",list.get(1));
		request.setAttribute("pageSize", CommonConstants.PAGE_SIZE);
		request.setAttribute("resultSize", list.get(0));
		request.setAttribute("map", map);
		return mapping.findForward("netResourceCount");
	}
}
