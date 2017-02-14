package com.boco.eoms.partner.deviceAssess.webapp.action;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.abdera.Abdera;
import org.apache.abdera.factory.Factory;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.apache.abdera.model.Person;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.partner.deviceAssess.mgr.CounselMgr;
import com.boco.eoms.partner.deviceAssess.mgr.DeviceAssessApproveMgr;
import com.boco.eoms.partner.deviceAssess.mgr.DeviceAssessContentMgr;
import com.boco.eoms.partner.deviceAssess.mgr.PeventinfoMgr;
import com.boco.eoms.partner.deviceAssess.model.DeviceAssessApprove;
import com.boco.eoms.partner.deviceAssess.model.DeviceAssessContent;
import com.boco.eoms.partner.deviceAssess.model.Peventinfo;
import com.boco.eoms.partner.deviceAssess.util.PeventinfoConstants;
import com.boco.eoms.partner.deviceAssess.webapp.form.PeventinfoForm;

/**
 * <p>
 * Title:peventinfo
 * </p>
 * <p>
 * Description:peventinfo
 * </p>
 * <p>
 * Sat Sep 25 10:35:07 CST 2010
 * </p>
 * 
 * @moudle.getAuthor() zhangxuesong
 * @moudle.getVersion() 1.0
 * 
 */
public final class PeventinfoAction extends BaseAction {

	/**
	 * 未指定方法时默认调用的方法
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
	 * 新增peventinfo
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
	 * 修改peventinfo
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
		PeventinfoMgr peventinfoMgr = (PeventinfoMgr) getBean("peventinfoMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		Peventinfo peventinfo = peventinfoMgr.getPeventinfo(id);
		PeventinfoForm peventinfoForm = (PeventinfoForm) convert(peventinfo);
		updateFormBean(mapping, request, peventinfoForm);
		
		DeviceAssessContentMgr dacMgr = (DeviceAssessContentMgr)getBean("deviceAssessContentMgr");
		List<DeviceAssessContent>   dacList=dacMgr.findAssessContentList(peventinfo.getId());
		request.setAttribute("dacList", dacList);
		request.setAttribute("size", dacList.size());
		request.setAttribute("PAGE_TYPE", "EDIT_PAGE");
		return mapping.findForward("edit");
	}
	/**
	 * 查看详细咨询服务事件信息表
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
    	PeventinfoMgr peventinfoMgr = (PeventinfoMgr) getBean("peventinfoMgr");
		DeviceAssessApproveMgr deviceAssessApproveMgr = (DeviceAssessApproveMgr) getBean("deviceAssessApproveMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		Peventinfo peventinfo= peventinfoMgr.getPeventinfo(id);
		PeventinfoForm peventinfoForm = (PeventinfoForm) convert(peventinfo);
		updateFormBean(mapping, request, peventinfoForm);
		DeviceAssessContentMgr dacMgr = (DeviceAssessContentMgr)getBean("deviceAssessContentMgr");
		List<DeviceAssessContent>   dacList=dacMgr.findAssessContentList(peventinfo.getId());
		request.setAttribute("dacList", dacList);
		request.setAttribute("size", dacList.size());
		request.setAttribute("PAGE_TYPE", "DETAIL_TYPE");
		return mapping.findForward("goToDetail");
	}
	
	/**
	 * 保存peventinfo
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
		PeventinfoMgr peventinfoMgr = (PeventinfoMgr) getBean("peventinfoMgr");
		PeventinfoForm peventinfoForm = (PeventinfoForm) form;
		boolean isNew = (null == peventinfoForm.getId() || ""
				.equals(peventinfoForm.getId()));
		Peventinfo peventinfo = (Peventinfo) convert(peventinfoForm);
		// if (isNew) {
		if("".endsWith(peventinfo.getId())||null==peventinfo.getId()){
		Date creatTime=new Date();
		peventinfo.setCreatTime(creatTime);
		}
		peventinfoMgr.savePeventinfo(peventinfo);
		// } else {
		// peventinfoMgr.savePeventinfo(peventinfo);
		// }
		
		
		/**
		 * @author huhao
		 * modify  2011-11-17
		 * 添加到统计审批
		 */
//		ITawSystemDictTypeManager dictMgr=(ITawSystemDictTypeManager)ApplicationContextHolder
//		   .getInstance().getBean("ItawSystemDictTypeManager");
		DeviceAssessApproveMgr deviceAssessApproveMgr = (DeviceAssessApproveMgr) getBean("deviceAssessApproveMgr");
		DeviceAssessApprove deviceAssessApprove = new DeviceAssessApprove();
		DeviceAssessApprove newDeviceAssessApprove = new DeviceAssessApprove();
		newDeviceAssessApprove= deviceAssessApproveMgr.getDeviceAssessApprove("1122104", peventinfo.getId());
		if(null!=newDeviceAssessApprove){
			deviceAssessApprove.setId(newDeviceAssessApprove.getId());
		}
		Date date= new Date();
		Integer state=2;
		String detailUrl="/partner/deviceAssess/peventinfos.do?method=goToDetail&id="+peventinfo.getId();
		String modifyUrl="/partner/deviceAssess/peventinfos.do?method=edit&id="+peventinfo.getId();
		deviceAssessApprove.setAssessId(peventinfo.getId());
		deviceAssessApprove.setAssessType("1122104");
		deviceAssessApprove.setClassName("Counsel");
		deviceAssessApprove.setName(peventinfoForm.getProName());
		deviceAssessApprove.setCommitTime(CommonUtils.toEomsStandardDate(date));
		deviceAssessApprove.setState(state);
		deviceAssessApprove.setApproveUser(peventinfoForm.getApproveUser());
		deviceAssessApprove.setSheetNum(peventinfoForm.getProNum());
		deviceAssessApprove.setDetailUrl(detailUrl);
		deviceAssessApprove.setModifyUrl(modifyUrl);
		deviceAssessApproveMgr.saveOrUpdateApprove(deviceAssessApprove);
		return mapping.findForward("success");
		
}
	

	/**
	 * 删除peventinfo
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
		PeventinfoMgr peventinfoMgr = (PeventinfoMgr) getBean("peventinfoMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		peventinfoMgr.removePeventinfo(id);
		return search(mapping, form, request, response);
	}

	/**
	 * 分页显示peventinfo列表
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
				PeventinfoConstants.PEVENTINFO_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = Integer.valueOf(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		PeventinfoMgr peventinfoMgr = (PeventinfoMgr) getBean("peventinfoMgr");
		Map map = (Map) peventinfoMgr.getPeventinfos(pageIndex, pageSize, "");
		List list = (List) map.get("result");
		request.setAttribute(PeventinfoConstants.PEVENTINFO_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("list");
	}

}