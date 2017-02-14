package com.boco.eoms.partner.deviceAssess.webapp.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
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
import com.boco.eoms.partner.deviceAssess.mgr.BigFaultMgr;
import com.boco.eoms.partner.deviceAssess.mgr.DeviceAssessContentMgr;
import com.boco.eoms.partner.deviceAssess.model.BigFault;
import com.boco.eoms.partner.deviceAssess.model.DeviceAssessApprove;
import com.boco.eoms.partner.deviceAssess.model.DeviceAssessContent;
import com.boco.eoms.partner.deviceAssess.util.BigFaultConstants;
import com.boco.eoms.partner.deviceAssess.util.excelimport.ImportResult;
import com.boco.eoms.partner.deviceAssess.webapp.form.BigFaultForm;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

/**
 * <p>
 * Title:厂家设备重大故障事件信息
 * </p>
 * <p>
 * Description:厂家设备重大故障事件信息
 * </p>
 * <p>
 * Mon Sep 27 13:45:23 CST 2010
 * </p>
 *  
 * @moudle.getAuthor() benweiwei
 * @moudle.getVersion() 1.0
 * 
 */
public final class BigFaultAction extends BaseAction {
 
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
		ActionForward af = new ActionForward("/bigDefaults.do?method=search",true);;
		return af;
	}
 	
 	/**
	 * 新增厂家设备重大故障事件信息
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
    	BigFaultMgr bigFaultMgr = (BigFaultMgr) getBean("bigFaultMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		BigFault bigFault = bigFaultMgr.getBigFault(id);
		BigFaultForm bigFaultForm = (BigFaultForm) convert(bigFault);
		updateFormBean(mapping, request, bigFaultForm);
    	
    	//审批内容Mgr
		DeviceAssessContentMgr dacMgr = (DeviceAssessContentMgr)getBean("deviceAssessContentMgr");
		//该事件（数据）的审批列表
		List<DeviceAssessContent> dacList = dacMgr.findAssessContentList(bigFault.getId());
		request.setAttribute("dacList", dacList);
		request.setAttribute("size", dacList.size());
		
    	request.setAttribute("PAGE_TYPE", "REBUTESUBMIT_PAGE");
		return mapping.findForward("edit");
	}
    
	/**
	 * 修改厂家设备重大故障事件信息
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
		BigFaultMgr bigFaultMgr = (BigFaultMgr) getBean("bigFaultMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		BigFault bigFault = bigFaultMgr.getBigFault(id);
		BigFaultForm bigFaultForm = (BigFaultForm) convert(bigFault);
		updateFormBean(mapping, request, bigFaultForm);
		
		//审批内容Mgr
		DeviceAssessContentMgr dacMgr = (DeviceAssessContentMgr)getBean("deviceAssessContentMgr");
		//该事件（数据）的审批列表
		List<DeviceAssessContent> dacList = dacMgr.findAssessContentList(bigFault.getId());
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
    	BigFaultMgr bigFaultMgr = (BigFaultMgr) getBean("bigFaultMgr");
    	String id = StaticMethod.null2String(request.getParameter("id"));
    	BigFault bigFault = bigFaultMgr.getBigFault(id);
    	
    	//该事件（数据）的审批列表
    	List<DeviceAssessContent> dacList = dacMgr.findAssessContentList(bigFault.getId());
    	request.setAttribute("dacList", dacList);
    	request.setAttribute("size", dacList.size());
    	request.setAttribute("bigFault", bigFault);
    	request.setAttribute("PAGE_TYPE", "DETAIL_TYPE");
    	
    	return mapping.findForward("goToDetail");
    }
    
	/**
	 * 保存厂家设备重大故障事件信息
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
		BigFaultMgr bigFaultMgr = (BigFaultMgr) getBean("bigFaultMgr");
		BigFaultForm bigFaultForm = (BigFaultForm) form;
		BigFault bigFault = (BigFault) convert(bigFaultForm);
		
//		获得故障开始时间
		Date beginTime = bigFault.getBeginTime();
//		业务恢复时间
		Date resumeTime = bigFault.getResumeTime();
//		故障消除时间
		Date removeTime = bigFault.getRemoveTime();
		
		Long beginTimeMs = beginTime.getTime();//获得毫秒值 
		Long resumeTimeMs = resumeTime.getTime();//获得毫秒值 
		Long removeTimeMs = removeTime.getTime();//获得毫秒值 
		
		Long resumeLong = resumeTimeMs - beginTimeMs;
		int minute = (int)(resumeLong/60000);   //共计分钟数
		int hour = minute/60;  //共计小时数
		int minuteLast = minute%60;
		String minuteString = String.valueOf(minuteLast);
		if(minuteString.length()==1){
			minuteString = minuteString + "0";
		}
		String resumeString = String.valueOf(hour)+":"+minuteString;
		bigFault.setResumeLong(resumeString);		
		
		Long faultLong = removeTimeMs - beginTimeMs;
		minute = (int)(faultLong/60000);   //共计分钟数
		hour = minute/60;  //共计小时数
		minuteLast = minute%60;
		minuteString = String.valueOf(minuteLast);
		if(minuteString.length()==1){
			minuteString = "0" + minuteString;
		}
		String faultString = String.valueOf(hour)+":"+minuteString;
		
		bigFault.setFaultLong(faultString);
		
		//设置审批信息(事件ID在保存了bigFault生成主键后设置)
		DeviceAssessApprove daa = new DeviceAssessApprove();
		daa.setAssessType("1122105");//事件类型
		daa.setSheetNum(bigFaultForm.getSheetNum());//工单号
		daa.setName(bigFaultForm.getBigFaultName());//名称
		daa.setCommitTime(new DateTime(new Date()).toString("yyyy-MM-dd HH:mm:ss"));//提交时间
		daa.setApproveUser(request.getParameter("approvalUser"));//审批人
		daa.setClassName(BigFault.class.getSimpleName());//实体类名
		daa.setModifyUrl("/partner/deviceAssess/bigFaults.do?method=edit");//修改URL链接
		daa.setDetailUrl("/partner/deviceAssess/bigFaults.do?method=goToDetail");//详细查看URL链接
		daa.setState(2);//审批状态（0驳回 1通过 2待审批）
		
		bigFaultMgr.saveDataAndApprove(bigFault,daa);
		ActionForward af = new ActionForward("/bigFaults.do?method=search",true);
		return af;
	}
	
	/**
	 * 删除厂家设备重大故障事件信息
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
			BigFaultMgr bigFaultMgr = (BigFaultMgr) getBean("bigFaultMgr");
			String id = StaticMethod.null2String(request.getParameter("id"));
			bigFaultMgr.removeBigFault(id);

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
	 * 分页显示厂家设备重大故障事件信息列表
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
				BigFaultConstants.BIGFAULT_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = Integer.valueOf(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		BigFaultMgr bigFaultMgr = (BigFaultMgr) getBean("bigFaultMgr");
		Map map = (Map) bigFaultMgr.getBigFaults(pageIndex, pageSize, "");
		List list = (List) map.get("result");
		request.setAttribute(BigFaultConstants.BIGFAULT_LIST, list);
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
		BigFaultForm uploadForm = (BigFaultForm) form;
		FormFile formFile = uploadForm.getImportFile();
		
		PrintWriter writer = null;
		try{
			BigFaultMgr bigFaultMgr = (BigFaultMgr) getBean("bigFaultMgr");
			String approveUser = request.getParameter("approvalPerson");
			Map params = new HashMap();
			params.put("approveUser", approveUser);
			writer = response.getWriter();
			ImportResult result = bigFaultMgr.importFromFile(formFile,params);
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
		String fileName = "sheet3.xls";
		File file = new File(rootPath + File.separator + fileName);

		// 读到流中
		InputStream inStream = new FileInputStream(file);// 文件的存放路径
		// 设置输出的格式
		response.reset();
		response.setContentType("application/x-msdownload;charset=GBK");
		response.setCharacterEncoding("GB2312");
		response.setHeader("Content-Disposition", "attachment; filename="
				+ new String(fileName.getBytes("gbk"), "iso8859-1"));

		// 循环取出流中的数据
		byte[] b = new byte[1024];
		int len = 0;
		while ((len = inStream.read(b)) > 0)
			response.getOutputStream().write(b, 0, len);
		inStream.close();
	}

}