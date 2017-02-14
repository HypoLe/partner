package com.boco.eoms.partner.deviceAssess.webapp.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.partner.deviceAssess.mgr.CounselMgr;
import com.boco.eoms.partner.deviceAssess.mgr.DeviceAssessApproveMgr;
import com.boco.eoms.partner.deviceAssess.mgr.DeviceAssessContentMgr;
import com.boco.eoms.partner.deviceAssess.model.Counsel;
import com.boco.eoms.partner.deviceAssess.model.DeviceAssessApprove;
import com.boco.eoms.partner.deviceAssess.model.DeviceAssessContent;
import com.boco.eoms.partner.deviceAssess.util.CounselConstants;
import com.boco.eoms.partner.deviceAssess.util.excelimport.ImportResult;
import com.boco.eoms.partner.deviceAssess.webapp.form.CounselForm;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

/**
 * <p>
 * Title:咨询服务事件信息表
 * </p>
 * <p>
 * Description:咨询服务事件信息表
 * </p>
 * <p>
 * Mon Sep 27 15:01:30 CST 2010
 * </p>
 * 
 * @moudle.getAuthor() benweiwei
 * @moudle.getVersion() 1.0 
 * 
 */
public final class CounselAction extends BaseAction {
 
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
	 * 新增咨询服务事件信息表
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
	 * 修改咨询服务事件信息表
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
		CounselMgr counselMgr = (CounselMgr) getBean("counselMgr");
		DeviceAssessApproveMgr deviceAssessApproveMgr = (DeviceAssessApproveMgr) getBean("deviceAssessApproveMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		Counsel counsel = counselMgr.getCounsel(id);
		CounselForm counselForm = (CounselForm) convert(counsel);
		updateFormBean(mapping, request, counselForm);
		DeviceAssessContentMgr dacMgr = (DeviceAssessContentMgr)getBean("deviceAssessContentMgr");
		List<DeviceAssessContent>   dacList=dacMgr.findAssessContentList(counsel.getId());
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
		CounselMgr counselMgr = (CounselMgr) getBean("counselMgr");
		DeviceAssessApproveMgr deviceAssessApproveMgr = (DeviceAssessApproveMgr) getBean("deviceAssessApproveMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		Counsel counsel = counselMgr.getCounsel(id);
		CounselForm counselForm = (CounselForm) convert(counsel);
		updateFormBean(mapping, request, counselForm);
		DeviceAssessContentMgr dacMgr = (DeviceAssessContentMgr)getBean("deviceAssessContentMgr");
		List<DeviceAssessContent>   dacList=dacMgr.findAssessContentList(counsel.getId());
		request.setAttribute("dacList", dacList);
		request.setAttribute("size", dacList.size());
		request.setAttribute("PAGE_TYPE", "DETAIL_TYPE");
		return mapping.findForward("goToDetail");
	}
	
	/**
	 * 保存咨询服务事件信息表
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
		CounselMgr counselMgr = (CounselMgr) getBean("counselMgr");
		CounselForm counselForm = (CounselForm) form;
		boolean isNew = (null == counselForm.getId() || "".equals(counselForm.getId()));
		Counsel counsel = (Counsel) convert(counselForm);
		
//		转派厂家时间
		Date changeTime = counsel.getChangeTime();
//		最终反馈时间
		Date finallyTime = counsel.getFinallyTime();
		
		Long changeTimeMs = changeTime.getTime();//获得毫秒值 
		Long finallyTimeMs = finallyTime.getTime();//获得毫秒值 
		
		Long resumeLong = finallyTimeMs - changeTimeMs;
		int minute = (int)(resumeLong/60000);   //共计分钟数
		int hour = minute/60;  //共计小时数
		int minuteLast = minute%60;
		String minuteString = String.valueOf(minuteLast);
		if(minuteString.length()==1){
			minuteString = "0" + minuteString ;
		}
		String resumeString = String.valueOf(hour)+":"+minuteString;
		counsel.setFinallyLong(resumeString);		
		
//		if (isNew) {
//			counselMgr.saveCounsel(counsel);
//		} else {
//			counselMgr.saveCounsel(counsel);
//		}
			
			
			/**
			 * @author huhao
			 * modify  2011-11-17
			 * 添加到统计审批
			 */
//			ITawSystemDictTypeManager dictMgr=(ITawSystemDictTypeManager)ApplicationContextHolder
//			   .getInstance().getBean("ItawSystemDictTypeManager");
//			DeviceAssessApproveMgr deviceAssessApproveMgr = (DeviceAssessApproveMgr) getBean("deviceAssessApproveMgr");
			DeviceAssessApprove daa = new DeviceAssessApprove();
//			DeviceAssessApprove newDeviceAssessApprove = new DeviceAssessApprove();
//			newDeviceAssessApprove= deviceAssessApproveMgr.getDeviceAssessApprove("1122102", counsel.getId());
//			if(null!=newDeviceAssessApprove){
//				daa.setId(newDeviceAssessApprove.getId());
//			}
			Date date= new Date();
			Integer state=2;
			String detailUrl="/partner/deviceAssess/counsels.do?method=goToDetail";
			String modifyUrl="/partner/deviceAssess/counsels.do?method=edit";
			daa.setAssessId(counsel.getId());
			daa.setAssessType("1122102");
			daa.setClassName(Counsel.class.getSimpleName());
			daa.setName(counselForm.getAffairName());
			daa.setCommitTime(CommonUtils.toEomsStandardDate(date));
			daa.setState(state);
			daa.setApproveUser(counselForm.getApproveUser());
			daa.setSheetNum(counselForm.getSheetNum());
			daa.setDetailUrl(detailUrl);
			daa.setModifyUrl(modifyUrl);
			counselMgr.saveDataAndApprove(counsel,daa);
			return mapping.findForward("success");
	}
	
	/**
	 * 删除咨询服务事件信息表
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
		CounselMgr counselMgr = (CounselMgr) getBean("counselMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		counselMgr.removeCounsel(id);
		return search(mapping, form, request, response);
	}
	
	/**
	 * 分页显示咨询服务事件信息表列表
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
				CounselConstants.COUNSEL_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = Integer.valueOf(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		CounselMgr counselMgr = (CounselMgr) getBean("counselMgr");
		Map map = (Map) counselMgr.getCounsels(pageIndex, pageSize, "");
		List list = (List) map.get("result");
		request.setAttribute(CounselConstants.COUNSEL_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("list");
	}
	
	/**
	 * 导入
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward importData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		CounselForm uploadForm = (CounselForm) form;
		FormFile formFile = uploadForm.getImportFile();
		
		PrintWriter writer = null;
		try{
			CounselMgr counselMgr = (CounselMgr) getBean("counselMgr");
			String approveUser = request.getParameter("approvalPerson");
			Map params = new HashMap();
			params.put("approveUser", approveUser);
			writer = response.getWriter();
			ImportResult result = counselMgr.importFromFile(formFile,params);
			if(result.getResultCode().equals("200")) {
				writer.write(
						new Gson().toJson(new ImmutableMap.Builder<String, String>()
								.put("success", "true")
								.put("msg", "ok")
								.put("infor", "'导入成功,共计导入"+result.getImportCount()+"条记录'").build()));
			}
		}catch(Exception e){
			e.printStackTrace();
			writer.write(
					new Gson().toJson(new ImmutableMap.Builder<String, String>()
							.put("success", "false")
							.put("msg", "failure")
							.put("infor", e.getMessage()).build()));
		}finally{
			if(writer != null){
				writer.close();
			}
		}
		return null;
	}
	
	/**
	 * 下载模板文件
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void download(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String rootPath = request.getRealPath("/partner/deviceAssess");
		String fileName = "sheet5.xls";
		File file = new File(rootPath + File.separator + fileName);

		// 读到流中
		InputStream inStream = new FileInputStream(file);// 文件的存放路径
		// 设置输出的格式
		response.reset();
		response.setContentType("application/x-msdownload;charset=GBK");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename="
				+ new String(fileName.getBytes("UTF-8"), "GBK"));

		// 循环取出流中的数据
		byte[] b = new byte[1024];
		int len = 0;
		while ((len = inStream.read(b)) > 0)
			response.getOutputStream().write(b, 0, len);
		inStream.close();
	}
	
}