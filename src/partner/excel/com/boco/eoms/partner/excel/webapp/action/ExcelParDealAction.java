package com.boco.eoms.partner.excel.webapp.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.partner.excel.mgr.IParContentsExcelMgr;
import com.boco.eoms.partner.excel.mgr.IParExcelMgr;
import com.boco.eoms.partner.excel.util.ExcelParImportUtil;


/**
 * <p>
 * Title: Excel的导入功能
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author benweiwei
 * @version 1.0(2009-10-26)
 */

public class ExcelParDealAction extends BaseAction {

	/**
	 * @跳转到选择要导入的EXCEL文件
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @throws Exception
	 * @return ActionForward
	 */
	public ActionForward performSelect(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String exc = request.getParameter("exc");
		String dow = request.getParameter("dow");
		request.setAttribute("exc", exc);
		request.setAttribute("dow", dow);
		return mapping.findForward("success");
	}
	/**
	 * @跳转到下载EXCEL模板的页面
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @throws Exception
	 * @return ActionForward
	 */
	public ActionForward performDownload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			return mapping.findForward("download");
	}
	/**
	 * @执行导入操作
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @throws Exception
	 * @return ActionForward
	 */
	public ActionForward performImport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		try {
			TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
			String userName = sessionform.getUsername();
			String deptName = sessionform.getDeptname();
			String userId = this.getUser(request).getUserid();
			// 将EXCEL文件上传到服务器，并返回上传文件保存的路径
			ExcelParImportUtil excelImportUtil = new ExcelParImportUtil();
			File newExcelFile = excelImportUtil.pushExcelFile(request, userName, deptName);
//			将Excel里面的数据导入到数据库(hibernate)
			IParContentsExcelMgr iparContentsExcelMgr = (IParContentsExcelMgr) getBean("iparContentsExcelMgr");
			List errorList = excelImportUtil.processExcel(newExcelFile, 6, 0, iparContentsExcelMgr, userId);
			if(errorList.size()>0){
				request.setAttribute("err", errorList);
			}
			return mapping.findForward("successimp");
		} catch (Exception e) {
			e.printStackTrace();
			return mapping.findForward("successimp");
		}
	}
	/**
	 * @执行导入操作(jdbc)
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @throws Exception
	 * @return ActionForward
	 */
	public ActionForward performImportJDBC(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		try {
			TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
			String userName = sessionform.getUsername();
			String deptName = sessionform.getDeptname();

			// 将EXCEL文件上传到服务器，并返回上传文件保存的路径
			ExcelParImportUtil excelImportUtil = new ExcelParImportUtil();
			File newExcelFile = excelImportUtil.pushExcelFile(request, userName, deptName);
			// 将Excel里面的数据导入到数据库(jdbc)
			IParExcelMgr iparExcelMgr = (IParExcelMgr) getBean("iparExcelMgr");
			List errorList = excelImportUtil.processExcelJDBC(newExcelFile, 5, 0,iparExcelMgr);			
			if(errorList.size()>0){
				request.setAttribute("err", errorList.get(0));
			}
			return mapping.findForward("successimp");
		} catch (Exception e) {
			e.printStackTrace();
			return mapping.findForward("successimp");
		}
	}
	/**
	 * 下载模版
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void download(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String encoding = request.getCharacterEncoding();
		String downloadfileName = null; //文件名
		if("UTF-8".equals(encoding.toUpperCase())){
			downloadfileName = request.getParameter("mod");
		}
		else{
			downloadfileName = new String(request.getParameter("mod").getBytes(encoding), "UTF-8");
		}
		String configPath = "";
		try{
			configPath =  ExcelParDealAction.class.getResource("").toString();
//			configPath = configPath.substring(5,configPath.indexOf("WEB-INF"))+"WEB-INF/classes/com/boco/eoms/km/config/";
			configPath = configPath.substring(5,configPath.indexOf("WEB-INF"))+"WEB-INF/pages/partner/parExceldeal/download/";
			}catch(Exception e){
				e.printStackTrace();
		}
//		String downloadPath = request.getRealPath("/")+ "WEB-INF\\pages\\partner\\parExceldeal\\download\\";
		File file = new File(configPath+downloadfileName);
		// 读到流中
		InputStream inStream = new FileInputStream(file);// 文件的存放路径
		// 设置输出的格式
		response.reset();
		response.setContentType("application/x-msdownload;charset=GBK");
		response.setCharacterEncoding("UTF-8");
		String fileName = URLEncoder.encode(file.getName(), "UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename="
				+ new String(fileName.getBytes("UTF-8"), "GBK"));

		// 循环取出流中的数据
		byte[] b = new byte[1024];
		int len;
		while ((len = inStream.read(b)) > 0)
			response.getOutputStream().write(b, 0, len);
		inStream.close();
	}
	
}
