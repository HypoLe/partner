package com.boco.eoms.partner.baseinfo.webapp.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.dict.model.TawSystemDictType;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;
import com.boco.eoms.partner.baseinfo.util.PartnerCityByUser;
import com.boco.eoms.partner.serviceArea.mgr.IGridMgr;
import com.boco.eoms.partner.serviceArea.mgr.ILineMgr;
import com.boco.eoms.partner.serviceArea.mgr.IPointMgr;
import com.boco.eoms.partner.serviceArea.model.Grid;

/**
 * <p>
 * Ajax调用联动action
 * </p>
 * <p>
 * Date:2010-09-01 09:00:00
 * </p>
 * <p>
 * Tue Feb 10 17:33:14 CST 2009
 * </p>
 * 
 * @author() benweiwei
 * @version() 1.2
 * 
 */
public final class LinkageAction extends BaseAction {
	
	/**
	 * 页面二级联动，已知资源级别，返回对应的巡检周期
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward changeWeekTime(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONArray json = new JSONArray();
		JSONObject jitem = new JSONObject();
        //获取资源级别 ID
		String pnrId = request.getParameter("pnrid");
    	//获取 人员ID
//		String userid = this.getUser(request).getUserid();
//		String countyBuffer = PartnerCityByUser.getCountyByCity(cityId);	
		
		//根据所属资源级别 列出相应巡检周期
	String weekTimeBuffer = PartnerCityByUser.getWeekTimeByPnrId(pnrId);	
				
		jitem.put("cb", weekTimeBuffer);
		json.put(jitem);
		
		response.setCharacterEncoding("utf-8");		
		
		response.getWriter().write(json.toString());		
		
		return null;
	}
	
	
	/**
	 * 页面二级联动，已知地市，返回对应县区list
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward changeCity(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONArray json = new JSONArray();
		JSONObject jitem = new JSONObject();

		String cityId = request.getParameter("city");
    	
		String userid = this.getUser(request).getUserid();
//		String countyBuffer = PartnerCityByUser.getCountyByCity(cityId);	
		
		//根据所属地市、用户的县区权限 列出相应县区
		String countyBuffer = PartnerCityByUser.getCountyOfUserRight(userid,cityId);	
		
		
		StringBuffer grid_list = new StringBuffer();
		grid_list.append("," + "");
		grid_list.append("," + "--请选择所属网格--");
		String gridBuffer = grid_list.toString();
		gridBuffer = gridBuffer.substring(1, gridBuffer.length());
		
		// 列出选择当前地市的合作伙伴
		String region = StaticMethod.null2String(request.getParameter("region"));
		String city = StaticMethod.null2String(request.getParameter("city"));
		StringBuffer provider_list = new StringBuffer();
		provider_list.append("," + "");
		provider_list.append("," + "--请选择合作伙伴--");
		ILineMgr lineMgr = (ILineMgr) getBean("iLineMgr");
		List provider = lineMgr.listProviderOfCity(region,city);
		for (int i = 0; i < provider.size(); i++) {
			provider_list.append("," + provider.get(i));
			provider_list.append("," + provider.get(i));
		}
		String providerBuffer = provider_list.toString();
		providerBuffer = providerBuffer.substring(1, providerBuffer.length());

		
		jitem.put("cb", countyBuffer);
		jitem.put("pb", providerBuffer);
		jitem.put("gl", gridBuffer);
		json.put(jitem);
		
		response.setCharacterEncoding("utf-8");		
		
		response.getWriter().write(json.toString());

		
		
		return null;
	}
	
	
	/**
	 * 页面二级联动，已知地市，县区 联动出合作伙伴
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward changePartner(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONArray json = new JSONArray();
		JSONObject jitem = new JSONObject();
		//region地市，city县区
		String region = StaticMethod.null2String(request.getParameter("region"));
		String city = StaticMethod.null2String(request.getParameter("city"));

		StringBuffer where = new StringBuffer();
		where.append(" where 1=1 "); 
		if(!"".equals(region)){
			where.append(" and region = '");
			where.append(region);
			where.append("'");
		}
		if(!"".equals(city)){
			where.append(" and city = '");
			where.append(city);
			where.append("'");
		}

		
		// 列出选择当前地市的合作伙伴
		StringBuffer provider_list = new StringBuffer();
		provider_list.append("," + "");
		provider_list.append("," + "--请选择合作伙伴--");

		ILineMgr lineMgr = (ILineMgr) getBean("iLineMgr");
		List provider = lineMgr.listProviderOfCity(region,city);
	
		for (int i = 0; i < provider.size(); i++) {
			Object[] provider1 = (Object[])provider.get(i);
//		修改为新增字段后的设定	
			provider_list.append("," + provider1[1]+"_"+provider1[2]);
			provider_list.append("," + provider1[0]);
		}
		String providerBuffer = provider_list.toString();
		
		providerBuffer = providerBuffer.substring(1, providerBuffer.length());
		
		//为了正常显示网格
		StringBuffer grid_list = new StringBuffer();
		grid_list.append("," + "");
		grid_list.append("," + "--请选择所属网格--");
		String gridBuffer = grid_list.toString();
		gridBuffer = gridBuffer.substring(1, gridBuffer.length());

		jitem.put("pb", providerBuffer);
		jitem.put("gl", gridBuffer);
		json.put(jitem);
		
		response.setCharacterEncoding("utf-8");		
		response.getWriter().write(json.toString());

		return null;
	}
	
	
	/**
	 * 页面二级联动，已知地市，县区，合作伙伴返回对应的网格名称
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward changeGrid(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONArray json = new JSONArray();
		JSONObject jitem = new JSONObject();
		
		String cityId = StaticMethod.null2String(request.getParameter("city"));
		String cityValue = StaticMethod.null2String(request.getParameter("cityValue"));
		String providerValue = StaticMethod.null2String(request.getParameter("providerValue"));

		StringBuffer where = new StringBuffer();
		where.append(" where isDel = '0' "); 
		if(!"".equals(cityValue)){
			where.append(" and city = '");
			where.append(cityValue);
			where.append("'");
		}
		if(!"".equals(cityId)){
			where.append(" and region = '");
			where.append(cityId);
			where.append("'");
		}
		if(!"".equals(providerValue)){
//			providerValue = java.net.URLDecoder.decode(providerValue , "UTF-8"); 
//			修改后的查询
			String[] providers =  providerValue.split("_");
			where.append(" and partnerid = '");
			where.append(providers[0]);
			where.append("'");
		}
		IGridMgr gridMgr = (IGridMgr) getBean("iGridMgr");
		List geidList = gridMgr.getGridsByWhere(where.toString());
		
		StringBuffer g_list = new StringBuffer();
		g_list.append("," + "");
		g_list.append("," + "--请选择所属网格--");
		
		for (int i = 0; i < geidList.size(); i++) {
			Grid grid = (Grid)geidList.get(i);
			
			g_list.append("," + grid.getId());
			g_list.append("," + grid.getGridName());
		}
//		存入网格list
		jitem.put("gl", g_list.toString().substring(1));
		json.put(jitem);
		
		response.setCharacterEncoding("utf-8");		
		response.getWriter().write(json.toString());

		return null;
	}
	
	
	/**
	 * 已知地市（不需要县区）， 联动出合作伙伴
	 * 网格维护中用
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward changeToPartner(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONArray json = new JSONArray();
		JSONObject jitem = new JSONObject();
		//region地市
		String region = StaticMethod.null2String(request.getParameter("region"));

		StringBuffer where = new StringBuffer();
		where.append(" where 1=1 "); 
		if(!"".equals(region)){
			where.append(" and region = '");
			where.append(region);
			where.append("'");
		}

		
		// 列出选择当前地市的合作伙伴
		StringBuffer provider_list = new StringBuffer();
		provider_list.append("," + "");
		provider_list.append("," + "--请选择合作伙伴--");

		ILineMgr lineMgr = (ILineMgr) getBean("iLineMgr");
		List provider = lineMgr.listProviderByRegion(region);
	
		for (int i = 0; i < provider.size(); i++) {
			
			provider_list.append("," + provider.get(i));
			provider_list.append("," + provider.get(i));
		}
		String providerBuffer = provider_list.toString();
		
		providerBuffer = providerBuffer.substring(1, providerBuffer.length());
		
		//为了正常显示网格
		StringBuffer grid_list = new StringBuffer();
		grid_list.append("," + "");
		grid_list.append("," + "--请选择所属网格--");
		String gridBuffer = grid_list.toString();
		gridBuffer = gridBuffer.substring(1, gridBuffer.length());

		jitem.put("pb", providerBuffer);
		jitem.put("gl", gridBuffer);
		json.put(jitem);
		
		response.setCharacterEncoding("utf-8");		
		response.getWriter().write(json.toString());

		return null;
	}

	
	/**
	 * 专业 联动 设备类型
	 * author:benweiwei
	 * 2010-9-28
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward changeFacility(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JSONArray json = new JSONArray();
		JSONObject jitem = new JSONObject();
		String speciality = request.getParameter("speciality");
		ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");
		List facilityList = mgr.getDictSonsByDictid(speciality);

		
		StringBuffer c_list = new StringBuffer();
		c_list.append("," + "");
		c_list.append("," + "请选择设备类型");
		
		for (int i = 0; i < facilityList.size(); i++) {
			TawSystemDictType tsDict = (TawSystemDictType)facilityList.get(i);
			
			c_list.append("," + tsDict.getDictId());
			c_list.append("," + tsDict.getDictName());
		}
		String facility = c_list.toString();
		
		facility = facility.substring(1, facility.length());

		
//		存入网格list
		jitem.put("facility", facility);
		json.put(jitem);
		
		response.setCharacterEncoding("utf-8");		
		response.getWriter().write(json.toString());
		
		
		
		return null;
	}
	
	
}