package com.boco.eoms.partner.deviceAssess.webapp.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.joda.time.DateTime;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.loging.BocoLog;
import com.boco.eoms.partner.deviceAssess.mgr.DeviceAssessContentMgr;
import com.boco.eoms.partner.deviceAssess.mgr.FacilityinfoMgr;
import com.boco.eoms.partner.deviceAssess.mgr.InsideDisposeMgr;
import com.boco.eoms.partner.deviceAssess.model.DeviceAssessApprove;
import com.boco.eoms.partner.deviceAssess.model.DeviceAssessContent;
import com.boco.eoms.partner.deviceAssess.model.Facilityinfo;
import com.boco.eoms.partner.deviceAssess.model.InsideDispose;
import com.boco.eoms.partner.deviceAssess.util.FacilityinfoConstants;
import com.boco.eoms.partner.deviceAssess.webapp.form.FacilityinfoForm;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

/**
 * <p>
 * Title:厂家设备问题事件信息
 * </p>
 * <p>
 * Description:厂家设备问题事件信息
 * </p>
 * <p>
 * Tue Sep 28 15:24:09 CST 2010
 * </p>
 * 
 * @moudle.getAuthor() zhangxuesong
 * @moudle.getVersion() 1.0
 * 
 */
public final class FacilityinfoAction extends BaseAction {
 
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
		ActionForward af = new ActionForward("/facilityinfos.do?method=search",true);
		return af;
	}
 	
 	/**
	 * 新增厂家设备问题事件信息
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
    	request.setAttribute("PAGE_TYPE", "ADD_PAGE");
		return mapping.findForward("edit");
	}
	
    /**
	 * 驳回再提交移动内部处理的设备故障事件信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    public ActionForward goToRebuteSubmit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
    	FacilityinfoMgr facilityinfoMgr = (FacilityinfoMgr) getBean("facilityinfoMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		Facilityinfo facilityinfo = facilityinfoMgr.getFacilityinfo(id);
		FacilityinfoForm facilityinfoForm = (FacilityinfoForm) convert(facilityinfo);
		updateFormBean(mapping, request, facilityinfoForm);
    	
    	//审批内容Mgr
		DeviceAssessContentMgr dacMgr = (DeviceAssessContentMgr)getBean("deviceAssessContentMgr");
		//该事件（数据）的审批列表
		List<DeviceAssessContent> dacList = dacMgr.findAssessContentList(facilityinfo.getId());
		request.setAttribute("dacList", dacList);
		request.setAttribute("size", dacList.size());
		
    	request.setAttribute("PAGE_TYPE", "REBUTESUBMIT_PAGE");
		return mapping.findForward("edit");
	}
    
	/**
	 * 修改厂家设备问题事件信息
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
		FacilityinfoMgr facilityinfoMgr = (FacilityinfoMgr) getBean("facilityinfoMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		Facilityinfo facilityinfo = facilityinfoMgr.getFacilityinfo(id);
		FacilityinfoForm facilityinfoForm = (FacilityinfoForm) convert(facilityinfo);
		updateFormBean(mapping, request, facilityinfoForm);
		
		//审批内容Mgr
		DeviceAssessContentMgr dacMgr = (DeviceAssessContentMgr)getBean("deviceAssessContentMgr");
		//该事件（数据）的审批列表
		List<DeviceAssessContent> dacList = dacMgr.findAssessContentList(facilityinfo.getId());
		request.setAttribute("dacList", dacList);
		request.setAttribute("size", dacList.size());
		
		request.setAttribute("PAGE_TYPE", "EDIT_PAGE");
		return mapping.findForward("edit");
	}
	
    /**
     * 查看移动内部处理的设备故障事件信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward goToDetail(ActionMapping mapping, ActionForm form,
    		HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    	//审批内容Mgr
    	DeviceAssessContentMgr dacMgr = (DeviceAssessContentMgr)getBean("deviceAssessContentMgr");
    	FacilityinfoMgr facilityinfoMgr = (FacilityinfoMgr) getBean("facilityinfoMgr");
    	String id = StaticMethod.null2String(request.getParameter("id"));
    	Facilityinfo facilityinfo = facilityinfoMgr.getFacilityinfo(id);
    	
    	//该事件（数据）的审批列表
    	List<DeviceAssessContent> dacList = dacMgr.findAssessContentList(facilityinfo.getId());
    	request.setAttribute("dacList", dacList);
    	request.setAttribute("size", dacList.size());
    	request.setAttribute("facilityinfo", facilityinfo);
    	request.setAttribute("PAGE_TYPE", "DETAIL_TYPE");
    	
    	return mapping.findForward("goToDetail");
    }
    
	/**
	 * 保存厂家设备问题事件信息
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
		FacilityinfoMgr facilityinfoMgr = (FacilityinfoMgr) getBean("facilityinfoMgr");
		FacilityinfoForm facilityinfoForm = (FacilityinfoForm) form;
		boolean isNew = (null == facilityinfoForm.getId() || "".equals(facilityinfoForm.getId()));
		Facilityinfo facilityinfo = (Facilityinfo) convert(facilityinfoForm);
		
		//设置审批信息(事件ID在保存了insideDispose生成主键后设置)
		DeviceAssessApprove daa = new DeviceAssessApprove();
		daa.setAssessType("1122107");//事件类型
		daa.setSheetNum("");//工单号
		daa.setName(facilityinfoForm.getIssueName());//名称
		daa.setCommitTime(new DateTime(new Date()).toString("yyyy-MM-dd HH:mm:ss"));//提交时间
		daa.setApproveUser(request.getParameter("approvalUser"));//审批人
		daa.setClassName(Facilityinfo.class.getSimpleName());//实体类名
		daa.setModifyUrl("/partner/deviceAssess/facilityinfos.do?method=edit");//修改URL链接
		daa.setDetailUrl("/partner/deviceAssess/facilityinfos.do?method=goToDetail");//详细查看URL链接
		daa.setState(2);//审批状态（0驳回 1通过 2待审批）
		facilityinfoMgr.saveDataAndApprove(facilityinfo,daa);
			
		ActionForward af = new ActionForward("/facilityinfos.do?method=search",true);
		return af;
	}
	
	/**
	 * 删除厂家设备问题事件信息
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
		response.setCharacterEncoding("utf-8");
		try {
			FacilityinfoMgr facilityinfoMgr = (FacilityinfoMgr) getBean("facilityinfoMgr");
			String id = StaticMethod.null2String(request.getParameter("id"));
			facilityinfoMgr.removeFacilityinfo(id);

			response.getWriter().write(
					new Gson()
							.toJson(new ImmutableMap.Builder<String, String>()
									.put("success", "true").put("msg", "ok")
									.put("infor", "删除成功！").build()));

		} catch (RuntimeException e) {
			BocoLog.info(this, "删除出错！");
			e.printStackTrace();
			response.getWriter().write(
					new Gson()
							.toJson(new ImmutableMap.Builder<String, String>()
									.put("success", "true").put("msg", "ok")
									.put("infor", "删除出错！").build()));
		} finally {
			return null;
		}
	}
	
	/**
	 * 分页显示厂家设备问题事件信息列表
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
				FacilityinfoConstants.FACILITYINFO_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = Integer.valueOf(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		FacilityinfoMgr facilityinfoMgr = (FacilityinfoMgr) getBean("facilityinfoMgr");
		Map map = (Map) facilityinfoMgr.getFacilityinfos(pageIndex, pageSize, "");
		List list = (List) map.get("result");
		request.setAttribute(FacilityinfoConstants.FACILITYINFO_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("list");
	}
}