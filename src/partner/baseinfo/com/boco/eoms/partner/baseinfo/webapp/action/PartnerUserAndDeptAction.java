package com.boco.eoms.partner.baseinfo.webapp.action;

import java.io.IOException;
import java.util.ArrayList;
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

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.StaticVariable;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.loging.BocoLog;
import com.boco.eoms.commons.system.area.model.TawSystemArea;
import com.boco.eoms.commons.system.area.service.ITawSystemAreaManager;
import com.boco.eoms.commons.ui.util.JSONUtil;
import com.boco.eoms.commons.ui.util.UIConstants;
import com.boco.eoms.partner.baseinfo.mgr.PartnerUserAndDeptMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerUserAndDept;
import com.boco.eoms.partner.baseinfo.util.PartnerUserAndAreaConstants;
import com.boco.eoms.partner.baseinfo.util.PnrBaseAreaIdList;
import com.boco.eoms.partner.baseinfo.webapp.form.PartnerUserAndAreaForm;
import com.boco.eoms.partner.baseinfo.webapp.form.PartnerUserAndDeptForm;

/**
 * <p>
 * Title:��Ա�������
 * </p>
 * <p>
 * Description:��Ա�������
 * </p>
 * <p>
 * Tue Mar 10 16:24:32 CST 2009
 * </p>
 * 
 * @moudle.getAuthor() liujinlong
 * @moudle.getVersion() 3.5
 * 
 */
public final class PartnerUserAndDeptAction extends BaseAction {
 
	/**
	 * 
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
	 * 
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
    	
    	//取出省公司的地域ID（spring 注入） add by :wangjunfeng 2010-4-2 
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
		request.setAttribute("province", province);

		return mapping.findForward("edit");
	}
	
	/**
	 * 
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

    	//取出省公司的地域ID（spring 注入） add by :wangjunfeng 2010-4-2 
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
		request.setAttribute("province", province);
    	
    	PartnerUserAndDeptMgr partnerUserAndDeptMgr = (PartnerUserAndDeptMgr) getBean("partnerUserAndDeptMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		PartnerUserAndDept partnerUserAndDept = partnerUserAndDeptMgr.getPartnerUserAndDept(id);
		PartnerUserAndDeptForm partnerUserAndDeptForm = (PartnerUserAndDeptForm) convert(partnerUserAndDept);
		updateFormBean(mapping, request, partnerUserAndDeptForm);
		return mapping.findForward("edit");
	}
	
	/**
	 * ������Ա�������
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
		PartnerUserAndDeptMgr partnerUserAndDeptMgr = (PartnerUserAndDeptMgr) getBean("partnerUserAndDeptMgr");
		PartnerUserAndDeptForm partnerUserAndDeptForm = (PartnerUserAndDeptForm) form;
		boolean isNew = (null == partnerUserAndDeptForm.getId() || "".equals(partnerUserAndDeptForm.getId()));
		String deptId =partnerUserAndDeptForm.getDeptId();
//		PartnerUserAndArea partnerUserAndArea = (PartnerUserAndArea) convert(partnerUserAndAreaForm);
		
		if (isNew) {
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			PartnerUserAndDept partnerUserAndDept = (PartnerUserAndDept) convert(partnerUserAndDeptForm);
			partnerUserAndDept.setDeptId(deptId);
			if(partnerUserAndDeptMgr.isunique(partnerUserAndDept.getUserId()).booleanValue()){
				partnerUserAndDeptMgr.savePartnerUserAndDept(partnerUserAndDept);
			}
			else {
				updateFormBean(mapping, request, partnerUserAndDeptForm);
				request.setAttribute("fallure", " 用户ID已经存在");
				return mapping.findForward("edit");
			}
		} else {
			PartnerUserAndDept area = partnerUserAndDeptMgr.getPartnerUserAndDept(partnerUserAndDeptForm.getId());
			area.setDeptId(deptId);
			if(area.getUserId().equals(partnerUserAndDeptForm.getUserId())){
				area.setDeptNames(partnerUserAndDeptForm.getDeptNames());
				area.setName(partnerUserAndDeptForm.getName());
				
				area.setDeptId(partnerUserAndDeptForm.getDeptId());
				
				partnerUserAndDeptMgr.savePartnerUserAndDept(area);
			}
			else {
				if(partnerUserAndDeptMgr.isunique(partnerUserAndDeptForm.getUserId()).booleanValue()){
					area.setDeptNames(partnerUserAndDeptForm.getDeptNames());
					area.setName(partnerUserAndDeptForm.getName());
					area.setUserId(partnerUserAndDeptForm.getUserId());
					partnerUserAndDeptMgr.savePartnerUserAndDept(area);
					
					
				}
				else {
					updateFormBean(mapping, request, partnerUserAndDeptForm);
					request.setAttribute("fallure", " 用户ID已经存在");
					return mapping.findForward("edit");
				}
			}

		}
		return search(mapping, partnerUserAndDeptForm, request, response);
	}
	
	/**
	 * ɾ����Ա�������
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
		PartnerUserAndDeptMgr partnerUserAndDeptMgr = (PartnerUserAndDeptMgr) getBean("partnerUserAndDeptMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		if(!id.equals("")){
			partnerUserAndDeptMgr.removePartnerUserAndDept(id);
		}
		String[] ids = request.getParameterValues("checkbox11");
        if(ids!=null){
	        for(int i=0;i<ids.length;i++){
	        	partnerUserAndDeptMgr.removePartnerUserAndDept(ids[i]);
	        }
        } 
		return search(mapping, form, request, response);
	}
	
	/**
	 * ��ҳ��ʾ��Ա��������б�
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
		String pageIndexName = new org.displaytag.util.ParamEncoder("partnerUserAndDeptList")
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		PartnerUserAndDeptMgr partnerUserAndDeptMgr = (PartnerUserAndDeptMgr) getBean("partnerUserAndDeptMgr");
		String whereStr = " where 1=1";
		//组装查询条件
		if(request.getParameter("nameSearch")!=null&&!request.getParameter("nameSearch").equals("")){
			whereStr += " and name like '%"+request.getParameter("nameSearch")+"%'";
		}
		if(request.getParameter("userIdSearch")!=null&&!request.getParameter("userIdSearch").equals("")){
			whereStr += " and userId like '%"+request.getParameter("userIdSearch")+"%'";
		}
		Map map = (Map) partnerUserAndDeptMgr.getPartnerUserAndDepts(pageIndex, pageSize, whereStr);
		List list = (List) map.get("result");
		request.setAttribute("partnerUserAndDeptList", list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("list");
	}
	

}