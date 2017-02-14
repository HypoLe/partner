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

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.partner.deviceAssess.mgr.DeviceAssessContentMgr;
import com.boco.eoms.partner.deviceAssess.mgr.FtraininfoMgr;
import com.boco.eoms.partner.deviceAssess.mgr.InsideDisposeMgr;
import com.boco.eoms.partner.deviceAssess.model.DeviceAssessApprove;
import com.boco.eoms.partner.deviceAssess.model.DeviceAssessContent;
import com.boco.eoms.partner.deviceAssess.model.Ftraininfo;
import com.boco.eoms.partner.deviceAssess.model.InsideDispose;
import com.boco.eoms.partner.deviceAssess.util.FtraininfoConstants;
import com.boco.eoms.partner.deviceAssess.webapp.form.FtraininfoForm;
import com.boco.eoms.partner.deviceAssess.webapp.form.InsideDisposeForm;

/**
 * <p>
 * Title:pnr_deviceassess_ftrain_info
 * </p>
 * <p>
 * Description:pnr_deviceassess_ftrain_info
 * </p>
 * <p>
 * Sun Sep 26 09:11:03 CST 2010
 * </p>
 * 
 * @moudle.getAuthor() zhangxuesong
 * @moudle.getVersion() 1.0
 * 
 */
public final class FtraininfoAction extends BaseAction {
 
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
		ActionForward af = new ActionForward("/ftraininfos.do?method=search",true);
		return af;
	}
 	
 	/**
	 * 新增pnr_deviceassess_ftrain_info
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
	
    
    public ActionForward goToRebuteSubmit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
    	FtraininfoMgr ftraininfoMgr = (FtraininfoMgr) getBean("ftraininfoMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		Ftraininfo ftraininfo = ftraininfoMgr.getFtraininfo(id);
		FtraininfoForm ftraininfoForm = (FtraininfoForm) convert(ftraininfo);
		updateFormBean(mapping, request, ftraininfoForm);
    	
    	//审批内容Mgr
		DeviceAssessContentMgr dacMgr = (DeviceAssessContentMgr)getBean("deviceAssessContentMgr");
		//该事件（数据）的审批列表
		List<DeviceAssessContent> dacList = dacMgr.findAssessContentList(ftraininfo.getId());
		request.setAttribute("dacList", dacList);
		request.setAttribute("size", dacList.size());
		
    	request.setAttribute("PAGE_TYPE", "REBUTESUBMIT_PAGE");
		return mapping.findForward("edit");
	}
	/**
	 * 修改pnr_deviceassess_ftrain_info
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
		FtraininfoMgr ftraininfoMgr = (FtraininfoMgr) getBean("ftraininfoMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		Ftraininfo ftraininfo = ftraininfoMgr.getFtraininfo(id);
		FtraininfoForm ftraininfoForm = (FtraininfoForm) convert(ftraininfo);
		updateFormBean(mapping, request, ftraininfoForm);
		
		DeviceAssessContentMgr dacMgr = (DeviceAssessContentMgr)getBean("deviceAssessContentMgr");
		//该事件（数据）的审批列表
		List<DeviceAssessContent> dacList = dacMgr.findAssessContentList(ftraininfo.getId());
		request.setAttribute("dacList", dacList);
		request.setAttribute("size", dacList.size());
		
		request.setAttribute("PAGE_TYPE", "EDIT_PAGE");
		return mapping.findForward("edit");
	}
	
    public ActionForward goToDetail(ActionMapping mapping, ActionForm form,
    		HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    	//审批内容Mgr
    	DeviceAssessContentMgr dacMgr = (DeviceAssessContentMgr)getBean("deviceAssessContentMgr");
    	FtraininfoMgr ftraininfoMgr = (FtraininfoMgr) getBean("ftraininfoMgr");
    	String id = StaticMethod.null2String(request.getParameter("id"));
    	Ftraininfo ftraininfo = ftraininfoMgr.getFtraininfo(id);
    	
    	//该事件（数据）的审批列表
    	List<DeviceAssessContent> dacList = dacMgr.findAssessContentList(ftraininfo.getId());
    	request.setAttribute("dacList", dacList);
    	request.setAttribute("size", dacList.size());
    	request.setAttribute("ftraininfo", ftraininfo);
    	request.setAttribute("PAGE_TYPE", "DETAIL_TYPE");
    	
    	return mapping.findForward("goToDetail");
    }
	/**
	 * 保存pnr_deviceassess_ftrain_info
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
		FtraininfoMgr ftraininfoMgr = (FtraininfoMgr) getBean("ftraininfoMgr");
		FtraininfoForm ftraininfoForm = (FtraininfoForm) form;
		boolean isNew = (null == ftraininfoForm.getId() || "".equals(ftraininfoForm.getId()));
		Ftraininfo ftraininfo = (Ftraininfo) convert(ftraininfoForm);
		/*
		 * tp培训人数
		 * ep合格人数
		 * pNum为培训合格率
		 * */

		double tp=Double.valueOf(ftraininfoForm.getTrainPopulace());
		double ep=Double.valueOf(ftraininfoForm.getEligibPopulace());
		double pNum=(ep/tp)*100.00;
		ftraininfo.setTrainEligibRate(pNum);
		
		if (isNew) {
		ftraininfo.setCreateTime(new Date());
		} 
//		else {
//			ftraininfoMgr.saveFtraininfo(ftraininfo);
//		}
			DeviceAssessApprove daa = new DeviceAssessApprove();
			daa.setAssessType("1122111");//事件类型
//			daa.setSheetNum(ftraininfoForm.getSheetNum());//工单号
			daa.setName(ftraininfoForm.getEventName());//名称
			daa.setCommitTime(new DateTime(new Date()).toString("yyyy-MM-dd HH:mm:ss"));//提交时间
			daa.setApproveUser(request.getParameter("approvalUser"));//审批人
			daa.setClassName(Ftraininfo.class.getSimpleName());//实体类名
			daa.setModifyUrl("/partner/deviceAssess/ftraininfos.do?method=edit");//修改URL链接
			daa.setDetailUrl("/partner/deviceAssess/ftraininfos.do?method=goToDetail");//详细查看URL链接
			daa.setState(2);//审批状态（0驳回 1通过 2待审批）
			
			ftraininfoMgr.saveDataAndApprove(ftraininfo,daa);
				
			return mapping.findForward("success");
//		return search(mapping, ftraininfoForm, request, response);
	}
	
	/**
	 * 删除pnr_deviceassess_ftrain_info
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
		FtraininfoMgr ftraininfoMgr = (FtraininfoMgr) getBean("ftraininfoMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		ftraininfoMgr.removeFtraininfo(id);
		return search(mapping, form, request, response);
	}
	
	/**
	 * 分页显示pnr_deviceassess_ftrain_info列表
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
				FtraininfoConstants.FTRAININFO_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = Integer.valueOf(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		FtraininfoMgr ftraininfoMgr = (FtraininfoMgr) getBean("ftraininfoMgr");
		Map map = (Map) ftraininfoMgr.getFtraininfos(pageIndex, pageSize, "");
		List list = (List) map.get("result");
		request.setAttribute(FtraininfoConstants.FTRAININFO_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("list");
	}
	
}