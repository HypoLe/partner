package com.boco.eoms.partner.baseinfo.webapp.action;

import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.partner.baseinfo.mgr.AreaDeptTreeMgr;
import com.boco.eoms.partner.baseinfo.mgr.IAptitudeMgr;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.model.Aptitude;
import com.boco.eoms.partner.baseinfo.model.AreaDeptTree;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;
import com.boco.eoms.partner.baseinfo.util.AptitudeConstants;
import com.boco.eoms.partner.baseinfo.util.PartnerCityByUser;
import com.boco.eoms.partner.baseinfo.webapp.form.AptitudeForm;
import com.boco.eoms.partner.serviceArea.mgr.ILineMgr;
import com.boco.eoms.partner.serviceArea.mgr.ISiteMgr;
import com.boco.eoms.partner.serviceArea.model.Line;
import com.boco.eoms.partner.serviceArea.model.Site;
import com.boco.eoms.partner.serviceArea.model.SitePapers;
import com.boco.eoms.partner.serviceArea.webapp.form.LineForm;

/**
 * <p>
 * Title:合作伙伴资质
 * </p>
 * <p>
 * Description:合作伙伴资质
 * </p>
 * <p>
 * Fri Dec 18 14:11:52 CST 2009
 * </p>
 * 
 * @moudle.getAuthor() benweiwei
 * @moudle.getVersion() 1.0
 * 
 */
public final class AptitudeAction extends BaseAction {
 
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
	 * 新增合作伙伴
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    public ActionForward newExpert(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
    	String detail = StaticMethod.null2String(request.getParameter("detail"));//是否跳转到编辑页面
    	String searchInto=StaticMethod.null2String(request.getParameter("searchInto"));//是否由统计界面进入到detail页面；
    	String hasRightForAdd=StaticMethod.null2String(request.getParameter("hasRightForEdit"));
    	if(!"".equals(detail)){
    		request.setAttribute("methodName", "detail");
    	}else{
    		request.setAttribute("methodName", "edit");
    	}
		String nodeId = StaticMethod.null2String(request.getParameter("nodeId"));//nodeId 是地市的nodeId
		String id = StaticMethod.null2String(request.getParameter("id"));
		request.setAttribute("nodeId", nodeId);
		if(nodeId.length()>=7){
			PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr) getBean("partnerDeptMgr");
			PartnerDept partnerDept = partnerDeptMgr.getPartnerDeptByTreeNodeId(nodeId);
			request.setAttribute("proId1", partnerDept.getId());
		}
		if(!id.equals("")){
			request.setAttribute("proId1", id);
		}
		request.setAttribute("searchInto", searchInto);
		request.setAttribute("hasRightForAdd", hasRightForAdd);
		return mapping.findForward("newExpert");
	}
 	
 	/**
	 * 新增合作伙伴资质
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
		return mapping.findForward("edit");
	}
	
	/**
	 * 修改合作伙伴资质
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
		IAptitudeMgr aptitudeMgr = (IAptitudeMgr) getBean("iAptitudeMgr");
		String proId = StaticMethod.null2String(request.getParameter("proId"));
		String id = StaticMethod.null2String(request.getParameter("id"));
		if(!"".equals(id)){
			Aptitude aptitude = aptitudeMgr.getAptitude(id);
			AptitudeForm aptitudeForm = (AptitudeForm) convert(aptitude);
			updateFormBean(mapping, request, aptitudeForm);
		}else if(!"".equals(proId)){
			PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr) getBean("partnerDeptMgr");
			PartnerDept partnerDept = partnerDeptMgr.getPartnerDept(proId);
			request.setAttribute("proName", partnerDept.getName());
		}
		request.setAttribute("proId", proId);
		return mapping.findForward("edit");
	}
    
    
	/**
	 * 查看合作伙伴资质详细信息
	 * author:wangjunfeng
	 * 2010-03-01
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
    	IAptitudeMgr aptitudeMgr = (IAptitudeMgr) getBean("iAptitudeMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		
		Aptitude aptitude = aptitudeMgr.getAptitude(id);
		AptitudeForm aptitudeForm = (AptitudeForm) convert(aptitude);
		
		updateFormBean(mapping, request, aptitudeForm);
		return mapping.findForward("detail");
	}
    
	/**
	 * 修改合作伙伴资质
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    public ActionForward editNor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IAptitudeMgr aptitudeMgr = (IAptitudeMgr) getBean("iAptitudeMgr");
		String proId = StaticMethod.null2String(request.getParameter("proId"));
		String id = StaticMethod.null2String(request.getParameter("id"));
		if(!"".equals(id)){
			Aptitude aptitude = aptitudeMgr.getAptitude(id);
			AptitudeForm aptitudeForm = (AptitudeForm) convert(aptitude);
			updateFormBean(mapping, request, aptitudeForm);
		}else if(!"".equals(proId)){
			PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr) getBean("partnerDeptMgr");
			PartnerDept partnerDept = partnerDeptMgr.getPartnerDept(proId);
			request.setAttribute("proName", partnerDept.getName());
		}
		request.setAttribute("proId", proId);
		return mapping.findForward("editNor");
	}	
	/**
	 * 保存合作伙伴资质
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
		String userId = this.getUser(request).getUserid();
		IAptitudeMgr aptitudeMgr = (IAptitudeMgr) getBean("iAptitudeMgr");
		AptitudeForm aptitudeForm = (AptitudeForm) form;
		boolean isNew = (null == aptitudeForm.getId() || "".equals(aptitudeForm.getId()));
		Aptitude aptitude = (Aptitude) convert(aptitudeForm);
		aptitude.setIsDel(Integer.valueOf(0));
		String proId = request.getParameter("proId");
		if (isNew) {
			aptitude.setAddTime(StaticMethod.getLocalTime());
			aptitude.setAddUser(userId);
			aptitudeMgr.saveAptitude(aptitude);
		} else {
			aptitude.setUpdateUser(userId);
			aptitude.setUpdateTime(StaticMethod.getLocalTime());
			aptitudeMgr.saveAptitude(aptitude);
		}
		request.setAttribute("proId", proId);
		request.setAttribute("operType", "save");
		return mapping.findForward("edit");
	}

	/**
	 * 保存合作伙伴资质
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveNor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String userId = this.getUser(request).getUserid();
		IAptitudeMgr aptitudeMgr = (IAptitudeMgr) getBean("iAptitudeMgr");
		AptitudeForm aptitudeForm = (AptitudeForm) form;
		boolean isNew = (null == aptitudeForm.getId() || "".equals(aptitudeForm.getId()));
		Aptitude aptitude = (Aptitude) convert(aptitudeForm);
		aptitude.setIsDel(Integer.valueOf(0));
		String proId = request.getParameter("proId");
		if (isNew) {
			aptitude.setAddTime(StaticMethod.getLocalTime());
			aptitude.setAddUser(userId);
			aptitudeMgr.saveAptitude(aptitude);
		} else {
			aptitude.setUpdateUser(userId);
			aptitude.setUpdateTime(StaticMethod.getLocalTime());
			aptitudeMgr.saveAptitude(aptitude);
		}
		request.setAttribute("proId", proId);
		request.setAttribute("operType", "save");
		return mapping.findForward("search");
	}
	
	/**
	 * 删除合作伙伴资质
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
		String userId = this.getUser(request).getUserid();
		String proId = request.getParameter("proId");
		IAptitudeMgr aptitudeMgr = (IAptitudeMgr) getBean("iAptitudeMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		if(!"".equals(id)){
			Aptitude aptitude = aptitudeMgr.getAptitude(id);
			aptitude.setDelTime(StaticMethod.getLocalTime());
			aptitude.setDelUser(userId);
			aptitude.setIsDel(Integer.valueOf(1));
			aptitudeMgr.saveAptitude(aptitude);
		}
		String ids[] = request.getParameterValues("ids");
		if(ids!=null){
			for(int i=0;i<ids.length;i++){
				Aptitude aptitude = aptitudeMgr.getAptitude(ids[i]);
				aptitude.setDelTime(StaticMethod.getLocalTime());
				aptitude.setDelUser(userId);
				aptitude.setIsDel(Integer.valueOf(1));
				aptitudeMgr.saveAptitude(aptitude);
			}
		}
		request.setAttribute("proId", proId);
		return searchOne(mapping, form, request, response);
	}
	
	/**
	 * 分页显示合作伙伴资质列表
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
				AptitudeConstants.APTITUDE_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		IAptitudeMgr aptitudeMgr = (IAptitudeMgr) getBean("iAptitudeMgr");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String providerName = StaticMethod.null2String(request.getParameter("providerName"));
		String aptitudeName = StaticMethod.null2String(request.getParameter("aptitudeName"));
		String astsString = StaticMethod.null2String(request.getParameter("asts"));
		String asteString = StaticMethod.null2String(request.getParameter("aste"));
		String aetsString = StaticMethod.null2String(request.getParameter("aets"));
		String aeteString = StaticMethod.null2String(request.getParameter("aete"));
		String aptitudeLevle = StaticMethod.null2String(request.getParameter("aptitudeLevle"));
		String aptitudeDesc = StaticMethod.null2String(request.getParameter("aptitudeDesc"));
		Date asts = null;
		Date aste = null;
		Date aets = null;
		Date aete = null;
		StringBuffer where = new StringBuffer();
		where.append(" where isDel=0 ");
		if(!"".equals(providerName)){
			where.append(" and providerName like '%");
			where.append(providerName);
			where.append("%' ");	
		}
		if(!"".equals(aptitudeName)){
			where.append(" and aptitudeName like '%");
			where.append(aptitudeName);
			where.append("%' ");	
		}
		if(!"".equals(aptitudeDesc)){
			where.append(" and aptitudeDesc like '%");
			where.append(aptitudeDesc);
			where.append("%' ");	
		}
		if(!"".equals(aptitudeLevle)){
			where.append(" and aptitudeLevle = '");
			where.append(aptitudeLevle);
			where.append("' ");
		}
		if(!"".equals(astsString)&&!"".equals(asteString)){
			request.setAttribute("asts", astsString);
			request.setAttribute("aste", asteString);
			asts = sdf.parse(astsString);
			aste = sdf.parse(asteString);
		}
		if(!"".equals(aetsString)&&!"".equals(aeteString)){
			request.setAttribute("aets", aetsString);
			request.setAttribute("aete", aeteString);
			aets = sdf.parse(aetsString);
			aete = sdf.parse(aeteString);
		}
		Map map = (Map) aptitudeMgr.getAptitudes(pageIndex, pageSize, where.toString(), asts, aste, aets, aete);
		List list = (List) map.get("result");
		request.setAttribute(AptitudeConstants.APTITUDE_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("list");
	}
	/**
	 * 判断合作伙伴资质名称是否存在ajax
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward validationAptitudeName(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String aptId = request.getParameter("aptId");
		String aptitudeName = request.getParameter("aptitudeName");
		String providerName = request.getParameter("providerName");
		IAptitudeMgr aptitudeMgr = (IAptitudeMgr) getBean("iAptitudeMgr");
		Aptitude aptitude = aptitudeMgr.getAptitude(aptId);
		StringBuffer where = new StringBuffer();
		where.append(" where aptitudeName = '");
		where.append(aptitudeName);
		where.append("' and providerName = '");
		where.append(providerName);
		where.append("'");
		List list = aptitudeMgr.getAptitudes(where.toString());
		
		// 设置返回页面的信息
		JSONArray json = new JSONArray();
		JSONObject jitem = new JSONObject();
		if(list.size()>0){
			if(StaticMethod.null2String(aptitude.getAptitudeName()).equals(aptitudeName)&&StaticMethod.null2String(aptitude.getProviderName()).equals(providerName)){
				jitem.put("message", true);
			}else{
				jitem.put("message", false);
			}
		} else {
			jitem.put("message", true);
		}
		json.put(jitem);
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(json.toString());
		return null;
	}
	/**
	 * 分页显示合作伙伴资质列表(one)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchOne(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				AptitudeConstants.APTITUDE_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		IAptitudeMgr aptitudeMgr = (IAptitudeMgr) getBean("iAptitudeMgr");
		StringBuffer where = new StringBuffer();
		String proId = StaticMethod.null2String(request.getParameter("proId"));
		String proId1 = StaticMethod.null2String(request.getParameter("proId1"));
		if(!proId1.equals("")){
			proId = proId1;
		}
		where.append(" where isDel=0");
		if(!proId.equals("")){
			where.append(" and proId = '");
			where.append(proId);
			where.append("'");
		}
		Map map = (Map) aptitudeMgr.getAptitudes(pageIndex, pageSize, where.toString());
		List list = (List) map.get("result");
		request.setAttribute(AptitudeConstants.APTITUDE_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("proId", proId);
		return mapping.findForward("listone");
	}

}