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

import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.partner.deviceAssess.mgr.DeviceAssessApproveMgr;
import com.boco.eoms.partner.deviceAssess.mgr.DeviceAssessContentMgr;
import com.boco.eoms.partner.deviceAssess.mgr.RepairinfoMgr;
import com.boco.eoms.partner.deviceAssess.model.DeviceAssessApprove;
import com.boco.eoms.partner.deviceAssess.model.DeviceAssessContent;
import com.boco.eoms.partner.deviceAssess.model.Repairinfo;
import com.boco.eoms.partner.deviceAssess.util.RepairinfoConstants;
import com.boco.eoms.partner.deviceAssess.util.excelimport.ImportResult;
import com.boco.eoms.partner.deviceAssess.webapp.form.InsideDisposeForm;
import com.boco.eoms.partner.deviceAssess.webapp.form.RepairinfoForm;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.util.StaticMethod;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

/**
 * <p>
 * Title:板件返修事件信息
 * </p>
 * <p>
 * Description:板件返修事件信息
 * </p>
 * 
 * @moudle.getAuthor() heminxi
 * @moudle.getVersion() 2.0
 * 
 */
public final class RepairinfoAction extends BaseAction {
 
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
	 * 新增板件返修事件信息
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
	 * 修改板件返修事件信息
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
		RepairinfoMgr repairinfoMgr = (RepairinfoMgr) getBean("repairinfoMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		Repairinfo repairinfo = repairinfoMgr.getRepairinfo(id);
		RepairinfoForm repairinfoForm = (RepairinfoForm) convert(repairinfo);
		updateFormBean(mapping, request, repairinfoForm);
		
//		审批内容Mgr
		DeviceAssessContentMgr dacMgr = (DeviceAssessContentMgr)getBean("deviceAssessContentMgr");
		//该事件（数据）的审批列表
		List<DeviceAssessContent> dacList = dacMgr.findAssessContentList(repairinfo.getId());
		request.setAttribute("dacList", dacList);
		request.setAttribute("size", dacList.size());
		request.setAttribute("PAGE_TYPE", "EDIT_PAGE");
		return mapping.findForward("edit");
	}
	
	/**
	 * 保存板件返修事件信息
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
		RepairinfoMgr repairinfoMgr = (RepairinfoMgr) getBean("repairinfoMgr");
		RepairinfoForm repairinfoForm = (RepairinfoForm) form;
		boolean isNew = (null == repairinfoForm.getId() || "".equals(repairinfoForm.getId()));
		Repairinfo repairinfo = (Repairinfo) convert(repairinfoForm);
		/*
		 *送修时间: retTime
		 *返回时间: repTime
		 * */
		Date retTime=repairinfo.getRepairTime();
		Date repTime=repairinfo.getReturnTime();
		Long retTimeMs = retTime.getTime();//获得毫秒值 
		Long repTimeMs = repTime.getTime();//获得毫秒值 
		Long repLong=repTimeMs-retTimeMs;
		int minute = (int)(repLong/60000);   //共计分钟数
		int hour = minute/60;  //共计小时数
		int day=hour/24;  //共计天数
		
		int minuteLast = minute%60;
		String minuteString = String.valueOf(minuteLast);
		if(minuteString.length()==1){
			minuteString = "0" + minuteString ;
		}
		String resumeString = String.valueOf(hour)+":"+minuteString;
//		counsel.setFinallyLong(resumeString);		
		
		
		repairinfo.setRepairLongHour(resumeString);
		repairinfo.setRepairLong(day);
		repairinfo.setTotal(1);
		

			DeviceAssessApprove daa = new DeviceAssessApprove();
			daa.setAssessType("1122108");//事件类型
			daa.setSheetNum(repairinfoForm.getSheetNum());//工单号
			daa.setName(repairinfoForm.getAffairName());//名称
			daa.setCommitTime(new DateTime(new Date()).toString("yyyy-MM-dd HH:mm:ss"));//提交时间
			daa.setApproveUser(request.getParameter("approvalUser"));//审批人
			daa.setClassName(Repairinfo.class.getSimpleName());//实体类名
			daa.setModifyUrl("/partner/deviceAssess/repairinfos.do?method=edit");//修改URL链接
			daa.setDetailUrl("/partner/deviceAssess/repairinfos.do?method=goToDetail");//详细查看URL链接
			daa.setState(2);//审批状态（0驳回 1通过 2待审批）
			repairinfoMgr.saveDataAndApprove(repairinfo, daa);
					
			return mapping.findForward("success");
	}
	
	/**
	 * 删除板件返修事件信息
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
		RepairinfoMgr repairinfoMgr = (RepairinfoMgr) getBean("repairinfoMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		repairinfoMgr.removeRepairinfo(id);
		return search(mapping, form, request, response);
	}
	
	/**
	 * 分页显示板件返修事件信息列表
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
				RepairinfoConstants.REPAIRINFO_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = Integer.valueOf(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		RepairinfoMgr repairinfoMgr = (RepairinfoMgr) getBean("repairinfoMgr");
		Map map = (Map) repairinfoMgr.getRepairinfos(pageIndex, pageSize, "");
		List list = (List) map.get("result");
		request.setAttribute(RepairinfoConstants.REPAIRINFO_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("list");
	}
	
	  public ActionForward goToDetail(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			RepairinfoMgr repairinfoMgr = (RepairinfoMgr) getBean("repairinfoMgr");
			String id = StaticMethod.null2String(request.getParameter("id"));	
			Repairinfo repairinfo = repairinfoMgr.getRepairinfos(id);
			
			
			
			DeviceAssessContentMgr dacMgr = (DeviceAssessContentMgr)getBean("deviceAssessContentMgr");
			List<DeviceAssessContent>   dacList=dacMgr.findAssessContentList(repairinfo.getId());
			request.setAttribute("dacList", dacList);
			request.setAttribute("repairinfo", repairinfo);
			request.setAttribute("size", dacList.size());
			request.setAttribute("PAGE_TYPE", "DETAIL_TYPE");
			return mapping.findForward("goToDetail");
		}
	  
	  public ActionForward importData(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) {
			response.setCharacterEncoding("utf-8");
			RepairinfoForm uploadForm = (RepairinfoForm) form;
			FormFile formFile = uploadForm.getImportFile();
			
			PrintWriter writer = null;
			try{
				RepairinfoMgr repairinfoMgr = (RepairinfoMgr) getBean("repairinfoMgr");
				String approveUser = request.getParameter("approvalPerson");
				Map params = new HashMap();
				params.put("approveUser", approveUser);
				writer = response.getWriter();
				ImportResult result = repairinfoMgr.importFromFile(formFile,params);
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
	  
		public void download(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			String rootPath = request.getRealPath("/partner/deviceAssess");
			String fileName = "sheet6.xls";
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