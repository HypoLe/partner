package com.boco.eoms.mobile.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import webwork.util.BeanUtil;

import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.common.util.StaticMethod;
import com.boco.eoms.deviceManagement.common.utils.CommonConstants;
import com.boco.eoms.mobile.form.AndroidAppInfoForm;
import com.boco.eoms.mobile.model.AndroidAppInfo;
import com.boco.eoms.mobile.model.AndroidDeveloper;
import com.boco.eoms.mobile.service.IAndroidDeveloperService;
import com.boco.eoms.mobile.service.IAndroidService;
import com.google.common.base.Strings;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

public class AndroidAppManagerAction extends BaseAction {

	/**
	 * 新增android 应用
	 */
	public ActionForward goToEditAndroidApp(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String id = StaticMethod.nullObject2String(request.getParameter("id"));
		if(!"".equals(id)){
			IAndroidService androidService  = (IAndroidService) this.getBean("androidServiceImpl");
			Search search = new Search();
			search.addFilterEqual("id", id);
			List<AndroidAppInfo> returnList = androidService.search(search);
			request.setAttribute("androidAppInfo", returnList.get(0));
			request.setAttribute("type", "detail");
		}else{
			request.setAttribute("type", "new");
		}
		return mapping.findForward("editAndroidApp");
	}

	/**
	 * 新增android 应用
	 * 
	 * @throws IOException
	 */
	public ActionForward editAndroidApp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		IAndroidService androidService  = (IAndroidService) this.getBean("androidServiceImpl");
		String type = StaticMethod.nullObject2String(request.getParameter("type"));
		if(type.equals("modifyDownloadStatus")){
			String value = StaticMethod.nullObject2String(request.getParameter("value"));
			String id = StaticMethod.nullObject2String(request.getParameter("id"));
			Search search = new Search();
			search.addFilterEqual("id", id);
			AndroidAppInfo androidAppInfo = androidService.searchUnique(search);
			androidAppInfo.setDownloadStatus(value);
			
			androidService.save(androidAppInfo);
			response.getWriter().write("success");
			return null;
		}
		
		
		AndroidAppInfoForm appForm = (AndroidAppInfoForm) form;
		String tempPath = request.getRealPath("androidapp");
		FormFile formfile = appForm.getAppFile();
		if(null == formfile){
			response.getWriter().write("上传文件失败");
			return null;
		}
		int fileSize = formfile.getFileSize();// 得到文件大小
		String fileName = formfile.getFileName();// 得到文件名
		AndroidAppInfo androidAppInfo = new AndroidAppInfo();
		BeanUtil.copy(appForm, androidAppInfo);
		androidAppInfo.setAddTime(StaticMethod.getCurrentDateTime());
		androidAppInfo.setAddUser(getUserId(request));
		androidAppInfo.setFileSize(fileSize+"");
		androidAppInfo.setFileName(fileName);
		androidAppInfo.setFilePath(tempPath+"/"+fileName);
		if (!(fileSize == 0 || fileName.equals(""))) {// 如果文件大小不为0或文件名不为空的话就上传到c盘下
			FileOutputStream fos = new FileOutputStream(tempPath+"/" + fileName);
			fos.write(formfile.getFileData());// 写入字节
			fos.flush();
			fos.close();
		} else {
			return mapping.findForward("error");
		}
		androidAppInfo.setDownloadStatus("0");
		androidService.save(androidAppInfo);
		return mapping.findForward("success");
		// return null;
		// return mapping.findForward("editAndroidApp");
	}

	/**
	 * 进入andrid 列表
	 */
	public ActionForward goToListAndroidApp(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		IAndroidService androidService = (IAndroidService) this
				.getBean("androidServiceImpl");
		Search search = new Search();
		
		
		search.addSortAsc("addTime");
		String pageIndexString = request.getParameter((new org.displaytag.util.ParamEncoder("androidAppInfoList").encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		
		
		SearchResult<AndroidAppInfo> searchResult = androidService.searchAndCount(search);
		
		
		
		
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("androidAppInfoList", searchResult.getResult());
		request.setAttribute("resultSize", searchResult.getTotalCount());
		return mapping.findForward("listAndroidApp");
	}
	

	/**
	 * 删除android应用,逻辑删除
	 */
	public ActionForward deleteAndroidApp(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
			String appId = StaticMethod.nullObject2String(request.getParameter("id"));
			Search search = new Search();
			search.addFilterEqual("id", appId);
			IAndroidService androidService  = (IAndroidService) this.getBean("androidServiceImpl");
			SearchResult<AndroidAppInfo> searchResult = androidService.searchAndCount(search);
			List<AndroidAppInfo> returList = searchResult.getResult();
			for(int i = 0;i<returList.size();i++){
				File file = new File(returList.get(i).getFilePath());
				if(file.exists()){//如果文件删除,则删除该条数据
					file.delete();
				}
				androidService.remove(returList.get(i));
			}
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/plain");
			try {
				response.getWriter().write("success".toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
	}
	
	
	/**
	 * 进入andrid 列表
	 */
	public ActionForward goToListDeveloper(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		IAndroidService androidService = (IAndroidService) this
		.getBean("androidServiceImpl");
		Search search = new Search();
		search.addSortAsc("addTime");
		SearchResult<AndroidAppInfo> searchResult = androidService
		.searchAndCount(search);
		
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("androidAppInfoList", searchResult.getResult());
		request.setAttribute("resultSize", searchResult.getTotalCount());
		return mapping.findForward("listListDeveloper");
	}
	
	/**
	 * 进入andrid 列表
	 */
	public ActionForward editDeveloper(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		String editType = StaticMethod.nullObject2String(request.getParameter("editType"));
		
		
		IAndroidDeveloperService developService = (IAndroidDeveloperService) this.getBean("androidDeveloperServiceImpl");
		if("add".equals(editType)){
			AndroidDeveloper developer = new AndroidDeveloper();
			String developerName = StaticMethod.nullObject2String(request.getParameter("developerName"));
			String serialNumber = StaticMethod.nullObject2String(request.getParameter("serialNumber"));
			String publicKey = StaticMethod.nullObject2String(request.getParameter("publicKey"));
			String remark = StaticMethod.nullObject2String(request.getParameter("remark"));
			developer.setDeveloperName(developerName);
			developer.setSerialNumber(serialNumber);
			developer.setPublicKey(publicKey);
			developer.setRemark(remark);
			developer.setIsAllow(true);
			developer.setAddUser(this.getUserId(request));
			developer.setAddTime(StaticMethod.getCurrentDateTime());
			developer.setDeleteFlag(0);
			developService.save(developer);
		}
		
		if("edit".equals(editType)){
			String id = StaticMethod.nullObject2String(request.getParameter("id"));
			Search search = new Search();
			search.addFilterEqual("id", id);
			AndroidDeveloper developer = developService.searchUnique(search);
			
			
			String developerName = StaticMethod.nullObject2String(request.getParameter("developerName"));
			String serialNumber = StaticMethod.nullObject2String(request.getParameter("serialNumber"));
			String publicKey = StaticMethod.nullObject2String(request.getParameter("publicKey"));
			String remark = StaticMethod.nullObject2String(request.getParameter("remark"));
			developer.setDeveloperName(developerName);
			developer.setSerialNumber(serialNumber);
			developer.setPublicKey(publicKey);
			developer.setRemark(remark);
			developer.setIsAllow(true);
			developer.setAddUser(this.getUserId(request));
			developer.setAddTime(StaticMethod.getCurrentDateTime());
			developer.setDeleteFlag(0);
			developService.save(developer);
		}
		
		if("del".equals(editType)){
			String id = StaticMethod.nullObject2String(request.getParameter("id"));
			Search search = new Search();
			search.addFilterEqual("id", id);
			AndroidDeveloper developer = developService.searchUnique(search);
			developService.remove(developer);
		}
		
		return mapping.findForward("listListDeveloper");
	}

}
