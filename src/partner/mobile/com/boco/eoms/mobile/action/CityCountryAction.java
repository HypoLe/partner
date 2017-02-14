package com.boco.eoms.mobile.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.common.util.StaticMethod;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.user.service.ITawSystemUserManager;
import com.boco.eoms.mobile.util.MobileCommonUtils;
import com.boco.eoms.partner.baseinfo.util.PartnerCityByUser;
import com.boco.eoms.partner.baseinfo.util.PnrBaseAreaIdList;
import com.boco.eoms.partner.serviceArea.mgr.ILineMgr;
import com.google.gson.Gson;

public class CityCountryAction extends BaseAction{
	/**
	 * 查询部门或人员
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void getCity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String getType = StaticMethod.nullObject2String(request.getParameter("getType"));//类型为city  or contry
		String name = StaticMethod.nullObject2String(request.getParameter("name"));//类型为city  or contry
		if("".equals(getType)){//如果为空值,则不进行查询,返回""
			MobileCommonUtils.responseWrite(response, "", "UTF-8");
			return;
		}
		String returnJson = "";
		if("city".equals(getType)){
//			根据省ID获取地市
			Gson gson = new Gson();	
			PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
			String province = pnrBaseAreaIdList.getRootAreaId();
	    	request.setAttribute("city", PartnerCityByUser.getCityByProvince(province));
			returnJson = gson.toJson(PartnerCityByUser.getCityByProvince(province));
//			System.out.println(returnJson);
			MobileCommonUtils.responseWrite(response, returnJson, "UTF-8");
			return;
		}
		MobileCommonUtils.responseWrite(response, "", "UTF-8");
		return;
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
	public ActionForward getContory(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONArray json = new JSONArray();
		JSONObject jitem = new JSONObject();

		String cityId = request.getParameter("city");
    	
		String userid = this.getUser(request).getUserid();
		
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
		
		MobileCommonUtils.responseWrite(response, json.toString(), "UTF-8");
		return null;
	}
}
