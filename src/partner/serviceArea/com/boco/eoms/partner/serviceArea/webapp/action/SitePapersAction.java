package com.boco.eoms.partner.serviceArea.webapp.action;

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
import com.boco.eoms.partner.serviceArea.mgr.IGridMgr;
import com.boco.eoms.partner.serviceArea.mgr.ISiteMgr;
import com.boco.eoms.partner.serviceArea.mgr.ISitePapersMgr;
import com.boco.eoms.partner.serviceArea.model.Grid;
import com.boco.eoms.partner.serviceArea.model.Site;
import com.boco.eoms.partner.serviceArea.model.SitePapers;
import com.boco.eoms.partner.serviceArea.util.SitePapersConstants;
import com.boco.eoms.partner.serviceArea.webapp.form.GridForm;
import com.boco.eoms.partner.serviceArea.webapp.form.SitePapersForm;

/**
 * <p>
 * Title:基站证件信息
 * </p>
 * <p>
 * </p>
 * <p>
 * Wed Nov 18 11:29:29 CST 2009
 * </p>
 * 
 * @moudle.getAuthor() benweiwei
 * @moudle.getVersion() 1.0
 * 
 */
public final class SitePapersAction extends BaseAction {
 
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
	 * 新增基站证件信息
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
	 * 修改基站证件信息
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
		ISitePapersMgr sitePapersMgr = (ISitePapersMgr) getBean("iSitePapersMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		
		String idSite = StaticMethod.null2String(request.getParameter("idSite"));
		
		if(!"".equals(id)){
			SitePapers sitePapers = sitePapersMgr.getSitePapers(id);
			SitePapersForm sitePapersForm = (SitePapersForm) convert(sitePapers);
			updateFormBean(mapping, request, sitePapersForm);
		}
		String siteNo = request.getParameter("siteNo");
		ISiteMgr siteMgr = (ISiteMgr) getBean("iSiteMgr");
		Site site = siteMgr.getSite(idSite);
		request.setAttribute("siteName", site.getSiteName());
		request.setAttribute("siteNo", site.getSiteNo());
		request.setAttribute("idSite", idSite);
		return mapping.findForward("edit");
	}
	
	/**
	 * 保存基站证件信息
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
		ISitePapersMgr sitePapersMgr = (ISitePapersMgr) getBean("iSitePapersMgr");
		String userId = this.getUser(request).getUserid();
		SitePapersForm sitePapersForm = (SitePapersForm) form;
		boolean isNew = (null == sitePapersForm.getId() || "".equals(sitePapersForm.getId()));
		SitePapers sitePapers = (SitePapers) convert(sitePapersForm);
		String idSite = request.getParameter("idSite");
		if (isNew) {
			sitePapers.setIsDel(Integer.valueOf("0"));
			sitePapers.setAddTime(StaticMethod.getLocalTime());
			sitePapers.setAddUser(userId);
			sitePapers.setIdSite(sitePapers.getIdSite());
			sitePapersMgr.saveSitePapers(sitePapers);
		} else {
			sitePapers.setUpdateTime(StaticMethod.getLocalTime());
			sitePapers.setUpdateUser(userId);
			sitePapersMgr.saveSitePapers(sitePapers);
		}
		request.setAttribute("operType", "save");
		return mapping.findForward("edit");
	}
	
	/**
	 * 删除基站证件信息
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
		ISitePapersMgr sitePapersMgr = (ISitePapersMgr) getBean("iSitePapersMgr");
//		String id = StaticMethod.null2String(request.getParameter("id"));
//		获取用户,设定删除时间和用户,伪删除
		String userId = this.getUser(request).getUserid();
		
		Date delDate = StaticMethod.getLocalTime();
		String ids[] = request.getParameterValues("ids");
		for(int i=0;i<ids.length;i++){
			SitePapers sitePapers = sitePapersMgr.getSitePapers(ids[i]);
			sitePapers.setDelTime(delDate);
			sitePapers.setDelUser(userId);
			sitePapers.setIsDel(Integer.valueOf(1));
			sitePapersMgr.saveSitePapers(sitePapers);
		}
		return search(mapping, form, request, response);
	}
	
	/**
	 * 分页显示基站证件信息列表
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
				SitePapersConstants.SITEPAPERS_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		String idSite = StaticMethod.null2String(request.getParameter("idSite"));
		StringBuffer where = new StringBuffer();
		where.append(" where isDel = '0' ");
		if(!"".equals(idSite)){
			where.append(" and idSite='");
			where.append(idSite);
			where.append("'");
		}
		ISitePapersMgr sitePapersMgr = (ISitePapersMgr) getBean("iSitePapersMgr");
		Map map = (Map) sitePapersMgr.getSitePaperss(pageIndex, pageSize, where.toString());
		List list = (List) map.get("result");
		request.setAttribute(SitePapersConstants.SITEPAPERS_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("idSite", idSite);
		return mapping.findForward("list");
	}
	/**
	 * 判断基站证件号是否存在ajax
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward validationPapersNo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String sitePapersId = request.getParameter("sitePapersId");
		String papersNo = request.getParameter("papersNo");
		ISitePapersMgr sitePapersMgr = (ISitePapersMgr) getBean("iSitePapersMgr");
		SitePapers sitePapers = sitePapersMgr.getSitePapers(sitePapersId);
		List list = sitePapersMgr.getSitePapersByPaperNo(papersNo);
		
		// 设置返回页面的信息
		JSONArray json = new JSONArray();
		JSONObject jitem = new JSONObject();
		if(list.size()>0){
			if(StaticMethod.null2String(String.valueOf(sitePapers.getPapersNo())).equals(papersNo)){
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
	
}