package com.boco.eoms.partner.deviceAssess.webapp.action;


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

import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.partner.deviceAssess.mgr.DeviceAssessContentMgr;
import com.boco.eoms.partner.deviceAssess.mgr.SoftupinfoMgr;
import com.boco.eoms.partner.deviceAssess.model.DeviceAssessApprove;
import com.boco.eoms.partner.deviceAssess.model.DeviceAssessContent;
import com.boco.eoms.partner.deviceAssess.model.Softupinfo;
import com.boco.eoms.partner.deviceAssess.util.SoftupinfoConstants;
import com.boco.eoms.partner.deviceAssess.webapp.form.SoftupinfoForm;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.util.StaticMethod;

/**
 * <p>
 * Title:软件升级事件信息
 * </p>
 * <p>
 * Description:软件升级事件信息
 * </p>
 * <p>
 * Mon Sep 27 11:46:52 CST 2010
 * </p>
 * 
 * @moudle.getAuthor() zhangxuesong
 * @moudle.getVersion() 1.0
 * 
 */
public final class SoftupinfoAction extends BaseAction {
 
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
	 * 新增软件升级事件信息
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
	 * 修改软件升级事件信息
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
		SoftupinfoMgr softupinfoMgr = (SoftupinfoMgr) getBean("softupinfoMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		Softupinfo softupinfo = softupinfoMgr.getSoftupinfo(id);
		SoftupinfoForm softupinfoForm = (SoftupinfoForm) convert(softupinfo);
		updateFormBean(mapping, request, softupinfoForm);
//		审批内容Mgr
		DeviceAssessContentMgr dacMgr = (DeviceAssessContentMgr)getBean("deviceAssessContentMgr");
		//该事件（数据）的审批列表
		List<DeviceAssessContent> dacList = dacMgr.findAssessContentList(softupinfo.getId());
		request.setAttribute("dacList", dacList);
		request.setAttribute("size", dacList.size());
		request.setAttribute("PAGE_TYPE", "EDIT_PAGE");
		return mapping.findForward("edit");
	}
	
	/**
	 * 保存软件升级事件信息
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
		SoftupinfoMgr softupinfoMgr = (SoftupinfoMgr) getBean("softupinfoMgr");
		SoftupinfoForm softupinfoForm = (SoftupinfoForm) form;
		boolean isNew = (null == softupinfoForm.getId() || "".equals(softupinfoForm.getId()));
		Softupinfo softupinfo = (Softupinfo) convert(softupinfoForm);
		/**
		 *
		 * 升级设备数量:ufa
		 *升级成功数量:uha
		 *升级成功率:ur
		 */
//		double ufa=Double.valueOf(softupinfoForm.getUpfixtureAmount());
//		double uha=Double.valueOf(softupinfoForm.getUphitAmount());
//		double ur=(uha/ufa)*100.0;
//		softupinfo.setUphitRate(ur);


		DeviceAssessApprove daa = new DeviceAssessApprove();
		daa.setAssessType("1122109");//事件类型
		daa.setSheetNum(softupinfoForm.getSheetNum());//工单号
		daa.setName(softupinfoForm.getAffairName());//名称
		daa.setCommitTime(new DateTime(new Date()).toString("yyyy-MM-dd HH:mm:ss"));//提交时间
		daa.setApproveUser(request.getParameter("approvalUser"));//审批人
		daa.setClassName(Softupinfo.class.getSimpleName());//实体类名
		daa.setModifyUrl("/partner/deviceAssess/softupinfos.do?method=edit");//修改URL链接
		daa.setDetailUrl("/partner/deviceAssess/softupinfos.do?method=goToDetail");//详细查看URL链接
		daa.setState(2);//审批状态（0驳回 1通过 2待审批）
		
		softupinfoMgr.saveDataAndApprove(softupinfo, daa);
		return mapping.findForward("success");
	}
	
	/**
	 * 删除软件升级事件信息
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
		SoftupinfoMgr softupinfoMgr = (SoftupinfoMgr) getBean("softupinfoMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		softupinfoMgr.removeSoftupinfo(id);
		return search(mapping, form, request, response);
	}
	
	/**
	 * 分页显示软件升级事件信息列表
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
				SoftupinfoConstants.SOFTUPINFO_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = Integer.valueOf(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		SoftupinfoMgr softupinfoMgr = (SoftupinfoMgr) getBean("softupinfoMgr");
		Map map = (Map) softupinfoMgr.getSoftupinfos(pageIndex, pageSize, "");
		List list = (List) map.get("result");
		request.setAttribute(SoftupinfoConstants.SOFTUPINFO_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("list");
	}
	
	 public ActionForward goToDetail(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
		    SoftupinfoMgr softupinfoMgr = (SoftupinfoMgr) getBean("softupinfoMgr");
			String id = StaticMethod.null2String(request.getParameter("id"));
			Softupinfo softupinfo = softupinfoMgr.getSoftupinfo(id);
			
			
			DeviceAssessContentMgr dacMgr = (DeviceAssessContentMgr)getBean("deviceAssessContentMgr");
			List<DeviceAssessContent>   dacList=dacMgr.findAssessContentList(softupinfo.getId());
			request.setAttribute("dacList", dacList);
			request.setAttribute("size", dacList.size());
			request.setAttribute("softupinfo", softupinfo);
			request.setAttribute("PAGE_TYPE", "DETAIL_TYPE");
			return mapping.findForward("goToDetail");
		}
}