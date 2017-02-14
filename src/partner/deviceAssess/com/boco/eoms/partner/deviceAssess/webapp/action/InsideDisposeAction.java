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
import org.joda.time.DateTime;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.loging.BocoLog;
import com.boco.eoms.partner.deviceAssess.mgr.DeviceAssessContentMgr;
import com.boco.eoms.partner.deviceAssess.mgr.InsideDisposeMgr;
import com.boco.eoms.partner.deviceAssess.model.DeviceAssessApprove;
import com.boco.eoms.partner.deviceAssess.model.DeviceAssessContent;
import com.boco.eoms.partner.deviceAssess.model.InsideDispose;
import com.boco.eoms.partner.deviceAssess.util.InsideDisposeConstants;
import com.boco.eoms.partner.deviceAssess.util.excelimport.ImportResult;
import com.boco.eoms.partner.deviceAssess.webapp.form.InsideDisposeForm;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

/**
 * <p>
 * Title:移动内部处理的设备故障事件信息
 * </p>
 * <p>
 * Description:移动内部处理的设备故障事件信息
 * </p>
 * <p>
 * Sun Sep 26 09:32:29 CST 2010
 * </p>
 * 
 * @moudle.getAuthor() benweiwei
 * @moudle.getVersion() 1.0
 * 
 */
public final class InsideDisposeAction extends BaseAction {
 
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
		ActionForward af = new ActionForward("/insideDisposes.do?method=search",true);
		return af;
	}
 	
	/**
	 * 新增移动内部处理的设备故障事件信息
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
    	InsideDisposeMgr insideDisposeMgr = (InsideDisposeMgr) getBean("insideDisposeMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		InsideDispose insideDispose = insideDisposeMgr.getInsideDispose(id);
		InsideDisposeForm insideDisposeForm = (InsideDisposeForm) convert(insideDispose);
		updateFormBean(mapping, request, insideDisposeForm);
    	
    	//审批内容Mgr
		DeviceAssessContentMgr dacMgr = (DeviceAssessContentMgr)getBean("deviceAssessContentMgr");
		//该事件（数据）的审批列表
		List<DeviceAssessContent> dacList = dacMgr.findAssessContentList(insideDispose.getId());
		request.setAttribute("dacList", dacList);
		request.setAttribute("size", dacList.size());
		
    	request.setAttribute("PAGE_TYPE", "REBUTESUBMIT_PAGE");
		return mapping.findForward("edit");
	}
	
	/**
	 * 修改移动内部处理的设备故障事件信息
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
		InsideDisposeMgr insideDisposeMgr = (InsideDisposeMgr) getBean("insideDisposeMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		InsideDispose insideDispose = insideDisposeMgr.getInsideDispose(id);
		InsideDisposeForm insideDisposeForm = (InsideDisposeForm) convert(insideDispose);
		updateFormBean(mapping, request, insideDisposeForm);
		
		//审批内容Mgr
		DeviceAssessContentMgr dacMgr = (DeviceAssessContentMgr)getBean("deviceAssessContentMgr");
		//该事件（数据）的审批列表
		List<DeviceAssessContent> dacList = dacMgr.findAssessContentList(insideDispose.getId());
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
    	InsideDisposeMgr insideDisposeMgr = (InsideDisposeMgr) getBean("insideDisposeMgr");
    	String id = StaticMethod.null2String(request.getParameter("id"));
    	InsideDispose insideDispose = insideDisposeMgr.getInsideDispose(id);
    	
    	//该事件（数据）的审批列表
    	List<DeviceAssessContent> dacList = dacMgr.findAssessContentList(insideDispose.getId());
    	request.setAttribute("dacList", dacList);
    	request.setAttribute("size", dacList.size());
    	request.setAttribute("insideDispose", insideDispose);
    	request.setAttribute("PAGE_TYPE", "DETAIL_TYPE");
    	
    	return mapping.findForward("goToDetail");
    }
	
	/**
	 * 保存移动内部处理的设备故障事件信息
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
		InsideDisposeMgr insideDisposeMgr = (InsideDisposeMgr) getBean("insideDisposeMgr");
		InsideDisposeForm insideDisposeForm = (InsideDisposeForm) form;
		boolean isNew = (null == insideDisposeForm.getId() || "".equals(insideDisposeForm.getId()));
		InsideDispose insideDispose = (InsideDispose) convert(insideDisposeForm);
//		获得故障开始时间
		Date beginTime = insideDispose.getBeginTime();
//		业务恢复时间
		Date resumeTime = insideDispose.getResumeTime();
//		故障消除时间
		Date removeTime = insideDispose.getRemoveTime();
		
		Long beginTimeMs = beginTime.getTime();//获得毫秒值 
		Long resumeTimeMs = resumeTime.getTime();//获得毫秒值 
		Long removeTimeMs = removeTime.getTime();//获得毫秒值 
		
		Long resumeLong = resumeTimeMs - beginTimeMs;
		int minute = (int)(resumeLong/60000);   //共计分钟数
		int hour = minute/60;  //共计小时数
		int minuteLast = minute%60;
		String minuteString = String.valueOf(minuteLast);
		if(minuteString.length()==1){
			minuteString = "0" + minuteString  ;
		}
		String resumeString = String.valueOf(hour)+":"+minuteString;
		insideDispose.setResumeLong(resumeString);		
		
		Long disposalLong = removeTimeMs - beginTimeMs;
		minute = (int)(disposalLong/60000);   //共计分钟数
		hour = minute/60;  //共计小时数
		minuteLast = minute%60;
		minuteString = String.valueOf(minuteLast);
		if(minuteString.length()==1){
			minuteString = "0" + minuteString;
		}
		String disposalString = String.valueOf(hour)+":"+minuteString;
		
		insideDispose.setDisposalLong(disposalString);

		//设置审批信息(事件ID在保存了insideDispose生成主键后设置)
		DeviceAssessApprove daa = new DeviceAssessApprove();
		daa.setAssessType("1122101");//事件类型
		daa.setSheetNum(insideDisposeForm.getSheetNum());//工单号
		daa.setName(insideDisposeForm.getAffairName());//名称
		daa.setCommitTime(new DateTime(new Date()).toString("yyyy-MM-dd HH:mm:ss"));//提交时间
		daa.setApproveUser(request.getParameter("approvalUser"));//审批人
		daa.setClassName(InsideDispose.class.getSimpleName());//实体类名
		daa.setModifyUrl("/partner/deviceAssess/insideDisposes.do?method=edit");//修改URL链接
		daa.setDetailUrl("/partner/deviceAssess/insideDisposes.do?method=goToDetail");//详细查看URL链接
		daa.setState(2);//审批状态（0驳回 1通过 2待审批）
		insideDisposeMgr.saveDataAndApprove(insideDispose,daa);
			
		ActionForward af = new ActionForward("/insideDisposes.do?method=search",true);
		return af;
	}
	
	/**
	 * 删除移动内部处理的设备故障事件信息
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
			InsideDisposeMgr insideDisposeMgr = (InsideDisposeMgr) getBean("insideDisposeMgr");
			String id = StaticMethod.null2String(request.getParameter("id"));
			insideDisposeMgr.removeInsideDispose(id);

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
	 * 分页显示移动内部处理的设备故障事件信息列表
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
				InsideDisposeConstants.INSIDEDISPOSE_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = Integer.valueOf(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		InsideDisposeMgr insideDisposeMgr = (InsideDisposeMgr) getBean("insideDisposeMgr");
		Map map = (Map) insideDisposeMgr.getInsideDisposes(pageIndex, pageSize, "");
		List list = (List) map.get("result");
		request.setAttribute(InsideDisposeConstants.INSIDEDISPOSE_LIST, list);
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
		InsideDisposeForm uploadForm = (InsideDisposeForm) form;
		FormFile formFile = uploadForm.getImportFile();
		
		PrintWriter writer = null;
		try{
			InsideDisposeMgr insideDisposeMgr = (InsideDisposeMgr) getBean("insideDisposeMgr");
			String approveUser = request.getParameter("approvalPerson");
			Map params = new HashMap();
			params.put("approveUser", approveUser);
			writer = response.getWriter();
			ImportResult result = insideDisposeMgr.importFromFile(formFile,params);
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
		String fileName = "sheet2.xls";
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