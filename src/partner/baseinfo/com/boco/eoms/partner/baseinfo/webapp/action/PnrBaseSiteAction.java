package com.boco.eoms.partner.baseinfo.webapp.action;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.abdera.Abdera;
import org.apache.abdera.factory.Factory;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.apache.abdera.model.Person;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.partner.baseinfo.mgr.IPnrBaseSiteMgr;
import com.boco.eoms.partner.baseinfo.model.AreaDeptTree;
import com.boco.eoms.partner.baseinfo.model.PnrBaseSite;
import com.boco.eoms.partner.baseinfo.util.PartnerCityByUser;
import com.boco.eoms.partner.baseinfo.util.PnrBaseSiteConstants;
import com.boco.eoms.partner.baseinfo.webapp.form.PnrBaseSiteForm;
import com.boco.eoms.partner.serviceArea.mgr.ILineMgr;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;

/**
 * <p>
 * Title:合作伙伴站址信息管理
 * </p>
 * <p>
 * Description:站址信息管理
 * </p>
 * <p>
 * Wed Mar 24 14:01:56 CST 2010
 * </p>
 * 
 * @moudle.getAuthor() daizhigang
 * @moudle.getVersion() 1.0
 * 
 */
public final class PnrBaseSiteAction extends BaseAction {
 
	/**
	 * 未指定方法时默认调用的方法
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return search(mapping, form, request, response);
	}
 	
 	/**
	 * 新增合作伙伴站址信息管理
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
    	String userid = this.getUser(request).getUserid();
    	List city = PartnerCityByUser.getCityByUser(userid);
    	request.setAttribute("city", city);
		return mapping.findForward("edit");
	}
	
	/**
	 * 修改合作伙伴站址信息管理
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IPnrBaseSiteMgr pnrBaseSiteMgr = (IPnrBaseSiteMgr) getBean("pnrBaseSiteMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		PnrBaseSite pnrBaseSite = pnrBaseSiteMgr.getPnrBaseSite(id);
		PnrBaseSiteForm pnrBaseSiteForm = (PnrBaseSiteForm) convert(pnrBaseSite);
		updateFormBean(mapping, request, pnrBaseSiteForm);
    	String userid = this.getUser(request).getUserid();
    	List city = PartnerCityByUser.getCityByUser(userid);
    	request.setAttribute("city", city);
		return mapping.findForward("edit");
	}
	
	/**
	 * 显示合作伙伴站址信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    public ActionForward detail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IPnrBaseSiteMgr pnrBaseSiteMgr = (IPnrBaseSiteMgr) getBean("pnrBaseSiteMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		PnrBaseSite pnrBaseSite = pnrBaseSiteMgr.getPnrBaseSite(id);
		PnrBaseSiteForm pnrBaseSiteForm = (PnrBaseSiteForm) convert(pnrBaseSite);
		updateFormBean(mapping, request, pnrBaseSiteForm);
		return mapping.findForward("detail");
	}
	/**
	 * 保存合作伙伴站址信息管理
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		//得到登陆人的考核操作角色,用以确定地域信息
		String userId = sessionform.getUserid();
		IPnrBaseSiteMgr pnrBaseSiteMgr = (IPnrBaseSiteMgr) getBean("pnrBaseSiteMgr");
		PnrBaseSiteForm pnrBaseSiteForm = (PnrBaseSiteForm) form;
		boolean isNew = (null == pnrBaseSiteForm.getId() || "".equals(pnrBaseSiteForm.getId()));
		PnrBaseSite pnrBaseSite = (PnrBaseSite) convert(pnrBaseSiteForm);
		if (isNew) {
			pnrBaseSite.setAddTime(StaticMethod.getLocalTime());
			pnrBaseSite.setAddUser(userId);
			pnrBaseSite.setIsDel("0");
			pnrBaseSiteMgr.savePnrBaseSite(pnrBaseSite);
		} else {
			pnrBaseSite.setUpdateTime(StaticMethod.getLocalTime());
			pnrBaseSite.setUpdateUser(userId);
			pnrBaseSiteMgr.savePnrBaseSite(pnrBaseSite);
			return mapping.findForward("success");
		}
		return search(mapping, pnrBaseSiteForm, request, response);
	}
	
	/**
	 * 删除合作伙伴站址信息管理
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward remove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IPnrBaseSiteMgr pnrBaseSiteMgr = (IPnrBaseSiteMgr) getBean("pnrBaseSiteMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		//得到登陆人的考核操作角色,用以确定地域信息
		String userId = sessionform.getUserid();
		pnrBaseSiteMgr.removePnrBaseSite(id,userId);
		return mapping.findForward("success");
	}
	
	/**
	 * 分页显示合作伙伴站址信息管理列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				PnrBaseSiteConstants.PNRBASESITE_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		IPnrBaseSiteMgr pnrBaseSiteMgr = (IPnrBaseSiteMgr) getBean("pnrBaseSiteMgr");
		StringBuffer where = new StringBuffer();
		where.append(" where isDel = '0' ");
		String code = StaticMethod.null2String(request.getParameter("code"));
		String name = StaticMethod.null2String(request.getParameter("name"));
		String city = StaticMethod.null2String(request.getParameter("city"));
		String town = StaticMethod.null2String(request.getParameter("town"));
		
		if(!"".equals(code)){
			where.append(" and code ='");
			where.append(code);
			where.append("'");
		}		
		if(!"".equals(name)){
			where.append(" and name ='");
			where.append(name);
			where.append("'");
		}
		if(!"".equals(city)){
			where.append(" and city ='");
			where.append(city);
			where.append("'");
		}	
		if(!"".equals(town)){
			where.append(" and town ='");
			where.append(town);
			where.append("'");
		}	
		Map map = (Map) pnrBaseSiteMgr.getPnrBaseSites(pageIndex, pageSize, where.toString());
		List list = (List) map.get("result");
    	String userid = this.getUser(request).getUserid();
    	List cityList = PartnerCityByUser.getCityByUser(userid);
    	request.setAttribute("city", cityList);
		request.setAttribute(PnrBaseSiteConstants.PNRBASESITE_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("list");
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
		IPnrBaseSiteMgr pnrBaseSiteMgr = (IPnrBaseSiteMgr) getBean("pnrBaseSiteMgr");

		String countyBuffer = PartnerCityByUser.getCountyByCity(cityId);	
		/**
		 * 列出选择当前地市的合作伙伴
		 */
		StringBuffer provider_list = new StringBuffer();
		provider_list.append("," + "");
		provider_list.append("," + "--请选择合作伙伴--");

		List provider = pnrBaseSiteMgr.listProviderOfCity(cityId);
	
		for (int i = 0; i < provider.size(); i++) {
			AreaDeptTree adt = (AreaDeptTree)provider.get(i);
			
			provider_list.append("," + adt.getNodeName());
			provider_list.append("," + adt.getNodeName());
		}
		String providerBuffer = provider_list.toString();
		
		providerBuffer = providerBuffer.substring(1, providerBuffer.length());
		
		
		StringBuffer grid_list = new StringBuffer();
		grid_list.append("," + "");
		grid_list.append("," + "--请选择所属网格--");
		String gridBuffer = grid_list.toString();
		
		gridBuffer = gridBuffer.substring(1, gridBuffer.length());
		jitem.put("cb", countyBuffer);
		jitem.put("pb", providerBuffer);
		jitem.put("gl", gridBuffer);
		json.put(jitem);
		
		response.setCharacterEncoding("utf-8");		
		
		response.getWriter().write(json.toString());

		
		
		return null;
	}
	
}