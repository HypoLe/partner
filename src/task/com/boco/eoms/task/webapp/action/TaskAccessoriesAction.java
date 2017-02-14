package com.boco.eoms.task.webapp.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.task.mgr.ITaskAccessoriesMgr;
import com.boco.eoms.task.mgr.ITaskMgr;
import com.boco.eoms.task.model.Task;
import com.boco.eoms.task.model.TaskAccessories;
import com.boco.eoms.task.webapp.form.TaskAccessoriesForm;

/**
 * 任务附件action
 * 
 * @author qiuzi
 * 
 */
public final class TaskAccessoriesAction extends BaseAction {

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
		return uploadPage(mapping, form, request, response);
	}

	/**
	 * 打开附件上传页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward uploadPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ITaskMgr taskMgr = (ITaskMgr) getBean("taskMgr");
		List fileList = new ArrayList();
		String taskId = StaticMethod
				.null2String(request.getParameter("taskId"));
		if ("".equals(taskId)) {
			taskId = StaticMethod.nullObject2String(request
					.getAttribute("taskId"));
		}
		if ("".equals(taskId)) { // 安全起见，无任务Id则无法上传
			request.setAttribute("nopriv", "1");
		}
		Task task = taskMgr.getTask(taskId);
		if (null != task) {
			String accessories = StaticMethod
					.null2String(task.getAccessories()).trim();
			if (!"".equals(accessories)) {
				String[] array = accessories.split(",");
				ITaskAccessoriesMgr accessoriesMgr = (ITaskAccessoriesMgr) getBean("accessoriesMgr");
				for (int i = 0; i < array.length; i++) {
					String accessoriesId = array[i];
					TaskAccessories accessory = accessoriesMgr
							.getFile(accessoriesId);
					fileList.add(accessory);
				}
			}
		}
		request.setAttribute("drafter", task.getDrafter());
		request.setAttribute("taskId", taskId);
		request.setAttribute("accessories", fileList);
		return mapping.findForward("uploadPage");
	}

	/**
	 * 上传附件
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward upload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String uploadInfo = "";
		TaskAccessoriesForm accessoriesForm = (TaskAccessoriesForm) form;
		FormFile formFile = accessoriesForm.getUploadFile();
		String taskId = StaticMethod.nullObject2String(accessoriesForm
				.getTaskId());
		if ("".equals(taskId)) {
			uploadInfo = "文件大小超出限制，请关闭此窗口重新上传！";
		}
		request.setAttribute("taskId", taskId);
		try {
			if (null != formFile && !"".equals(formFile.getFileName())) {
				ITaskAccessoriesMgr accessoriesMgr = (ITaskAccessoriesMgr) getBean("accessoriesMgr");
				String folderPath = request.getSession().getServletContext()
						.getRealPath("/")
						+ "taskAccessories";
				accessoriesMgr.uploadFile(formFile, folderPath, taskId,
						((TawSystemSessionForm) request.getSession()
								.getAttribute("sessionform")).getUserid());
				uploadInfo = "上传成功！";
			}
		} catch (Exception e) {
			uploadInfo = "上传过程出现错误！";
			e.printStackTrace();
		} finally {
			request.setAttribute("uploadInfo", uploadInfo);
			return uploadPage(mapping, form, request, response);
		}
	}

	/**
	 * 下载文件
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void download(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = StaticMethod.null2String(request.getParameter("id")); // 下载文件的Id
		ITaskAccessoriesMgr accessoriesMgr = (ITaskAccessoriesMgr) getBean("accessoriesMgr");
		TaskAccessories accessory = accessoriesMgr.getFile(id);

		File file = new File(accessory.getFilePath() + File.separator
				+ accessory.getMappingName());

		// 读到流中
		InputStream inStream = new FileInputStream(file);// 文件的存放路径
		// 设置输出的格式
		response.reset();
		response.setContentType("application/x-msdownload;charset=GBK");
		response.setCharacterEncoding("UTF-8");
		String fileName = URLEncoder.encode(accessory.getFileName(), "UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename="
				+ new String(fileName.getBytes("UTF-8"), "GBK"));

		// 循环取出流中的数据
		byte[] b = new byte[1024];
		int len = 0;
		while ((len = inStream.read(b)) > 0)
			response.getOutputStream().write(b, 0, len);
		inStream.close();
	}

	/**
	 * 删除附件
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String uploadInfo = "";
		TaskAccessoriesForm accessoriesForm = (TaskAccessoriesForm) form;
		String taskId = StaticMethod.nullObject2String(accessoriesForm
				.getTaskId());
		String id = StaticMethod.nullObject2String(accessoriesForm.getId());
		if ("".equals(taskId)) {
			uploadInfo = "删除过程中出现错误！";
		}
		request.setAttribute("taskId", taskId);
		try {
			if (null != id && !"".equals(id)) {
				ITaskAccessoriesMgr accessoriesMgr = (ITaskAccessoriesMgr) getBean("accessoriesMgr");
				accessoriesMgr.delFile(id, taskId);
				uploadInfo = "删除成功！";
			}
		} catch (Exception e) {
			uploadInfo = "删除过程中出现错误！";
			e.printStackTrace();
		} finally {
			request.setAttribute("uploadInfo", uploadInfo);
			return uploadPage(mapping, form, request, response);
		}
	}
}