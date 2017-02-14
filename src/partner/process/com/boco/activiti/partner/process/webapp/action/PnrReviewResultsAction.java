package com.boco.activiti.partner.process.webapp.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.boco.activiti.partner.process.service.IPnrReviewResultsService;
import com.boco.activiti.partner.process.webapp.form.PnrReviewResultsForm;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.deviceManagement.common.utils.CommonConstants;
import com.boco.eoms.partner.netresource.util.ImportResult;
import com.boco.eoms.partner.process.util.CommonUtils;
import com.boco.eoms.sheet.base.webapp.action.SheetAction;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

/**
 * 会审结果ACTION
 * 
 * @author WANGJUN
 * 
 */
public class PnrReviewResultsAction extends SheetAction {

	/**
	 * 打开会审结果导入界面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward openReviewResultsImport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userid = sessionForm.getUserid();
		request.setAttribute("userid", userid);
		return mapping.findForward("reviewResultsImport");
	}

	/**
	 * 导出会审结果导入模板
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward downloadReviewResultsImportModel(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String rootPath = request.getRealPath("/partner/processExcelModel");
		String fileName = "会审结果导入模板.xls";
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
		return null;
	}

	/**
	 * 导入会审结果
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward importReviewResults(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		// 导入人
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userid = sessionForm.getUserid();
		// 路径
		String osPath = request.getSession().getServletContext()
				.getRealPath("");
		PnrReviewResultsForm pnrReviewResultsForm = (PnrReviewResultsForm) form;
		FormFile formFile = pnrReviewResultsForm.getImportFile();

		PrintWriter writer = null;

		ImportResult result = null;

		String errMsg = "";

		String flagdiv = "";

		try {

			writer = response.getWriter();

			// 基站机房
			// 流程结束

			// IPnrTransferOfficeService pnrTransferOfficeService =
			// (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");

			IPnrReviewResultsService pnrReviewResultsService = (IPnrReviewResultsService) getBean("pnrReviewResultsService");

			// BsBtConfigMgr bsBtConfigMgr = (BsBtConfigMgr)
			// getBean("sdBsbtConfigService");
			result = pnrReviewResultsService.importReviewResultsFromFile(
					formFile, userid, osPath);

			if (result.getResultCode().equals("200")) {
				String msg = "会审结果共计:成功导入"
						+ (result.getImportCount() - result.getErrorCount())
						+ "条；<br>" + "数据需要修改，未导入" + result.getErrorCount()
						+ "条.";

				int error1 = result.getErrorCount();
				if (error1 > 0) {

					flagdiv = "1";

				}

				writer.write(new Gson()
						.toJson(new ImmutableMap.Builder<String, String>().put(
								"success", "true").put("msg", "ok").put(
								"infor", msg).put("flagdiv", flagdiv).build()));

			}

		} catch (Exception e) {
			e.printStackTrace();

			writer.write(new Gson()
					.toJson(new ImmutableMap.Builder<String, String>().put(
							"success", "false").put("msg", "failure").put(
							"infor", errMsg + e.getMessage()).build()));

		} finally {
			if (writer != null) {
				writer.close();
			}
		}
		return null;
	}

	/**
	 * 会审结果查询
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ActionForward queryReviewResultsList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 导入人
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userid = sessionForm.getUserid();
		IPnrReviewResultsService pnrReviewResultsService = (IPnrReviewResultsService) getBean("pnrReviewResultsService");
		// 1.分页
		final Integer pageSize = CommonConstants.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "list");
		final int pageIndex = Strings.isNullOrEmpty(pageIndexString) ? 0
				: Integer.valueOf(pageIndexString).intValue() - 1;

		// 2.获取参数
		// 导入开始时间
		String importStartTime = request.getParameter("importStartTime");
		// 导入结束时间
		String importEndTime = request.getParameter("importEndTime");
		// 处理情况
		String handleMarkFlag = request.getParameter("handleMarkFlag");

		String queryStr = "";
		if (StringUtils.isNotEmpty(handleMarkFlag)
				&& "0".equals(handleMarkFlag)) {
			queryStr = "OK";
		}

		// 3.拼接查询条件
		String whereStrHql = " where 1=1 ";

		String whereStrSql = " where 1=1 ";// 给SQL删除用

		String orderStr = "order by importTime desc ";

		whereStrHql += "and importUser='" + userid + "' ";
		whereStrSql += "and import_user='" + userid + "' ";

		if (importStartTime != null && !"".equals(importStartTime)) {
			whereStrHql += "and res.importTime >=:dateStart ";
			whereStrSql += "and import_time >= to_date('" + importStartTime
					+ "','yyyy-mm-dd hh24:mi:ss') ";// 给SQL删除用
		}
		if (importEndTime != null && !"".equals(importEndTime)) {
			whereStrHql += "and res.importTime <=:dateEnd  ";
			whereStrSql += "and import_time <= to_date('" + importEndTime
					+ "','yyyy-mm-dd hh24:mi:ss') ";// 给SQL删除用
		}
		if (handleMarkFlag != null && !"".equals(handleMarkFlag)) {
			whereStrHql += "and handleMark = '" + handleMarkFlag + "' ";
			whereStrSql += "and handle_mark = '" + handleMarkFlag + "' ";// 给SQL删除用
		}

		whereStrHql += orderStr;

		// 4.查询
		Map map = (Map) pnrReviewResultsService.getReviewResultsList(pageIndex,
				pageSize, whereStrHql,importStartTime,importEndTime);
		List list = (List) map.get("result");
		request.setAttribute("list", list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("queryStr", queryStr);
		request.setAttribute("whereStr", whereStrSql);

		// 5.回传查询条件
		request.setAttribute("importStartTime", importStartTime);
		request.setAttribute("importEndTime", importEndTime);
		request.setAttribute("handleMarkFlag", handleMarkFlag);

		return mapping.findForward("reviewResultsList");
	}

	/**
	 * 删除会审信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward deletePnrReviewResults(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		IPnrReviewResultsService pnrReviewResultsService = (IPnrReviewResultsService) getBean("pnrReviewResultsService");
		String whereStr = request.getParameter("whereStr");
		String deleteType = request.getParameter("deleteType");
		if (deleteType != null && !"".equals(deleteType)) {
			if ("ALL".equals(deleteType)) {
				pnrReviewResultsService.deletePnrReviewResults(whereStr);
			} else if ("SOME".equals(deleteType)) {
				String ids = "";
				String whereIdsStr = "";
				Map map = request.getParameterMap();
				Object[] idArray = (Object[]) map.get("checkboxId");
				for (int i = 0; i < idArray.length; i++) {
					ids = ids + "'" + idArray[i].toString() + "',";
				}
				ids = ids.substring(0, ids.length() - 1);
				whereIdsStr = whereStr + "and id in (" + ids + ")";
				pnrReviewResultsService.deletePnrReviewResults(whereIdsStr);
			}

		}
		return mapping.findForward("deletePnrReviewResults");
	}

}
