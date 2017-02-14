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
import com.boco.eoms.partner.deviceAssess.mgr.DeviceAssessContentMgr;
import com.boco.eoms.partner.deviceAssess.mgr.LserveinfoMgr;
import com.boco.eoms.partner.deviceAssess.mgr.RepairinfoMgr;
import com.boco.eoms.partner.deviceAssess.model.DeviceAssessApprove;
import com.boco.eoms.partner.deviceAssess.model.DeviceAssessContent;
import com.boco.eoms.partner.deviceAssess.model.Lserveinfo;
import com.boco.eoms.partner.deviceAssess.util.LserveinfoConstants;
import com.boco.eoms.partner.deviceAssess.util.excelimport.ImportResult;
import com.boco.eoms.partner.deviceAssess.webapp.form.InsideDisposeForm;
import com.boco.eoms.partner.deviceAssess.webapp.form.LserveinfoForm;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

/**
 * <p>
 * Title:现场服务事件信息
 * </p>
 * <p>
 * Description:现场服务事件信息
 * </p>
 * <p>
 * Mon Sep 27 11:46:52 CST 2010
 * </p>
 * 
 * @moudle.getAuthor() zhangxuesong
 * @moudle.getVersion() 1.0
 * 
 */
public final class LserveinfoAction extends BaseAction {
 
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
		ActionForward af = new ActionForward("/lserveinfos.do?method=search",true);
		return af;
	}
 	
 	/**
	 * 新增现场服务事件信息
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
	 * 修改现场服务事件信息
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
		LserveinfoMgr lserveinfoMgr = (LserveinfoMgr) getBean("lserveinfoMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		Lserveinfo lserveinfo = lserveinfoMgr.getLserveinfo(id);
		LserveinfoForm lserveinfoForm = (LserveinfoForm) convert(lserveinfo);
		updateFormBean(mapping, request, lserveinfoForm);
		
		
		DeviceAssessContentMgr dacMgr = (DeviceAssessContentMgr)getBean("deviceAssessContentMgr");
		//该事件（数据）的审批列表
		List<DeviceAssessContent> dacList = dacMgr.findAssessContentList(lserveinfo.getId());
		request.setAttribute("dacList", dacList);
		request.setAttribute("size", dacList.size());	
		request.setAttribute("PAGE_TYPE", "EDIT_PAGE");
		return mapping.findForward("edit");
	}
	
    public ActionForward goToRebuteSubmit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
    	LserveinfoMgr lserveinfoMgr = (LserveinfoMgr) getBean("lserveinfoMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		Lserveinfo lserveinfo = lserveinfoMgr.getLserveinfo(id);
		LserveinfoForm lserveinfoForm = (LserveinfoForm) convert(lserveinfo);
		updateFormBean(mapping, request, lserveinfoForm);
    	
    	//审批内容Mgr
		DeviceAssessContentMgr dacMgr = (DeviceAssessContentMgr)getBean("deviceAssessContentMgr");
		//该事件（数据）的审批列表
		List<DeviceAssessContent> dacList = dacMgr.findAssessContentList(lserveinfo.getId());
		request.setAttribute("dacList", dacList);
		request.setAttribute("size", dacList.size());
		
    	request.setAttribute("PAGE_TYPE", "REBUTESUBMIT_PAGE");
		return mapping.findForward("edit");
	}
    
    public ActionForward goToDetail(ActionMapping mapping, ActionForm form,
    		HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    	//审批内容Mgr
    	DeviceAssessContentMgr dacMgr = (DeviceAssessContentMgr)getBean("deviceAssessContentMgr");
    	LserveinfoMgr lserveinfoMgr = (LserveinfoMgr) getBean("lserveinfoMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		Lserveinfo lserveinfo = lserveinfoMgr.getLserveinfo(id);
    	
    	//该事件（数据）的审批列表
    	List<DeviceAssessContent> dacList = dacMgr.findAssessContentList(lserveinfo.getId());
    	request.setAttribute("dacList", dacList);
    	request.setAttribute("size", dacList.size());
    	request.setAttribute("lserveinfo", lserveinfo);
    	request.setAttribute("PAGE_TYPE", "DETAIL_TYPE");
    	
    	return mapping.findForward("goToDetail");
    }
	/**
	 * 保存现场服务事件信息
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
		LserveinfoMgr lserveinfoMgr = (LserveinfoMgr) getBean("lserveinfoMgr");
		LserveinfoForm lserveinfoForm = (LserveinfoForm) form;
		boolean isNew = (null == lserveinfoForm.getId() || "".equals(lserveinfoForm.getId()));
		Lserveinfo lserveinfo = (Lserveinfo) convert(lserveinfoForm);
		
		
		DeviceAssessApprove daa = new DeviceAssessApprove();
		daa.setAssessType("1122110");//事件类型
		daa.setSheetNum(lserveinfoForm.getSheetNum());//工单号
		daa.setName(lserveinfoForm.getAffairName());//名称
		daa.setCommitTime(new DateTime(new Date()).toString("yyyy-MM-dd HH:mm:ss"));//提交时间
		daa.setApproveUser(request.getParameter("approvalUser"));//审批人
		daa.setClassName(Lserveinfo.class.getSimpleName());//实体类名
		daa.setModifyUrl("/partner/deviceAssess/lserveinfos.do?method=edit");//修改URL链接
		daa.setDetailUrl("/partner/deviceAssess/lserveinfos.do?method=goToDetail");//详细查看URL链接
		daa.setState(2);//审批状态（0驳回 1通过 2待审批）
		
		lserveinfoMgr.saveDataAndApprove(lserveinfo,daa);
			
		return mapping.findForward("success");
	}
	
	/**
	 * 删除现场服务事件信息
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
		LserveinfoMgr lserveinfoMgr = (LserveinfoMgr) getBean("lserveinfoMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		lserveinfoMgr.removeLserveinfo(id);
		return search(mapping, form, request, response);
	}
	
	/**
	 * 分页显示现场服务事件信息列表
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
				LserveinfoConstants.LSERVEINFO_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = Integer.valueOf(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		LserveinfoMgr lserveinfoMgr = (LserveinfoMgr) getBean("lserveinfoMgr");
		Map map = (Map) lserveinfoMgr.getLserveinfos(pageIndex, pageSize, "");
		List list = (List) map.get("result");
		request.setAttribute(LserveinfoConstants.LSERVEINFO_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("list");
	}
	
	  public ActionForward importData(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) {
			response.setCharacterEncoding("utf-8");
			LserveinfoForm uploadForm = (LserveinfoForm) form;
			FormFile formFile = uploadForm.getImportFile();
			
			PrintWriter writer = null;
			try{
				LserveinfoMgr lserveinfoMgr = (LserveinfoMgr) getBean("lserveinfoMgr");
				String approveUser = request.getParameter("approvalPerson");
				Map params = new HashMap();
				params.put("approveUser", approveUser);
				writer = response.getWriter();
				ImportResult result = lserveinfoMgr.importFromFile(formFile,params);
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
			String fileName = "sheet7.xls";
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