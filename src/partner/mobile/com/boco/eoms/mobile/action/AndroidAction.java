package com.boco.eoms.mobile.action;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.common.util.StaticMethod;
import com.boco.eoms.commons.accessories.dao.TawCommonsAccessoriesDao;
import com.boco.eoms.commons.accessories.exception.AccessoriesException;
import com.boco.eoms.commons.accessories.model.TawCommonsAccessories;
import com.boco.eoms.commons.accessories.model.TawCommonsAccessoriesConfig;
import com.boco.eoms.commons.accessories.service.ITawCommonsAccessoriesConfigManager;
import com.boco.eoms.commons.accessories.util.AccessoriesMgrLocator;
import com.boco.eoms.commons.accessories.util.AccessoriesUtil;
import com.boco.eoms.commons.loging.BocoLog;
import com.boco.eoms.commons.system.dict.model.TawSystemDictType;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;
import com.boco.eoms.mobile.model.AndroidAppInfo;
import com.boco.eoms.mobile.service.IAndroidService;
import com.boco.eoms.mobile.util.MobileCommonUtils;
import com.boco.eoms.mobile.util.PartnerConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.googlecode.genericdao.search.Search;
import com.oreilly.servlet.multipart.FilePart;
import com.oreilly.servlet.multipart.MultipartParser;
import com.oreilly.servlet.multipart.Part;

public class AndroidAction extends BaseAction {
	public void downPartnerDictData(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String msg = "";
		String[] dictType = PartnerConstants.dictType;
		ITawSystemDictTypeManager dictTypeManager = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");

		if (dictType.length < 1) {
			MobileCommonUtils.responseWrite(response, "", "UTF-8");
			return;
		}
		ArrayList<ArrayList<TawSystemDictType>> dictTypeList = new ArrayList<ArrayList<TawSystemDictType>>();
		for (int i = 0; i < dictType.length; i++) {
			if (null != dictTypeManager.getDictSonsByDictid(dictType[i])
					&& !dictTypeManager.getDictSonsByDictid(dictType[i])
							.isEmpty())
				dictTypeList.add(dictTypeManager
						.getDictSonsByDictid(dictType[i]));
		}

		Gson gson = new GsonBuilder().serializeNulls().create();
		if (dictTypeList.isEmpty()) {
			MobileCommonUtils.responseWrite(response, "", "UTF-8");
			return;
		} else {
			msg = gson.toJson(dictTypeList);
		}
		if (null != msg && !"".equals(msg)) {
			MobileCommonUtils.responseWrite(response, msg, "UTF-8");
			return;
		} else
			MobileCommonUtils.responseWrite(response, "", "UTF-8");
		return;
	}

	/**
	 * 查询版本
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void checkVersion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String allApp = StaticMethod.nullObject2String(request.getParameter("allApp"));
		boolean isAllApp = "".equals(allApp)||"false".equals(allApp)?false:true;//是否是查询所有版本
		if(isAllApp){
			IAndroidService androidService  = (IAndroidService) this.getBean("androidServiceImpl");
			Search search = new Search();
			search.addFilterEqual("downloadStatus", "0");
			List<AndroidAppInfo> returList = androidService.search(search);
			
			if(null != returList && !returList.isEmpty()){
				Gson gson = new GsonBuilder().serializeNulls().create();
				String msg = gson.toJson(returList);
		
				MobileCommonUtils.responseWrite(response, msg, "UTF-8");
				return;
			}else{
				MobileCommonUtils.responseWrite(response, "", "UTF-8");
				return;
			}
		}
		
		String hallPackage = StaticMethod.nullObject2String(request.getParameter("hallPackage"));
		IAndroidService androidService  = (IAndroidService) this.getBean("androidServiceImpl");
		Search search = new Search();
		search.addFilterEqual("downloadStatus", "0");
		search.addFilterEqual("packageName", hallPackage);
		List<AndroidAppInfo> returList = androidService.search(search);
		
		
		Gson gson = new GsonBuilder().serializeNulls().create();
		if(!returList.isEmpty()){
			String msg = gson.toJson(returList.get(0));
			MobileCommonUtils.responseWrite(response, msg, "UTF-8");
		}else{
			MobileCommonUtils.responseWrite(response, "", "UTF-8");
		}
		return;

	}
	/**
	 * 检查最新版本
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void getCheckVersion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String appName = StaticMethod.nullObject2String(request.getParameter("appName"));
		
		String appVersion = StaticMethod.nullObject2String(request.getParameter("appVersion"));
		
		int flag = -1;
		IAndroidService androidService  = (IAndroidService) this.getBean("androidServiceImpl");
		Search search = new Search();
		appName +=".apk";
		search.addFilterEqual("fileName", appName);
		search.addFilterEqual("downloadStatus", "0");
		List<AndroidAppInfo> returList = androidService.search(search);
		
		String introduction="";
		if(null != returList && !returList.isEmpty()){
			
			Gson gson = new GsonBuilder().serializeNulls().create();
			String versionCode = returList.get(0).getVersionCode();			
			flag =versionCode.compareTo(appVersion);	
			introduction=returList.get(0).getIntroduction();
		}
		
		String returnJson="[{\"sigle\":\"getVersion\"},{\"status\":\"false\"},{\"introduction\":\""+introduction+"\"}]";
		
		if(flag>0){			
			
			returnJson = "[{\"sigle\":\"getVersion\"},{\"status\":\"true\"},{\"introduction\":\""+introduction+"\"}]";
		}
	     	     
		 MobileCommonUtils.responseWrite(response, returnJson, "UTF-8");
		
				
	}


	public void downLoadNewVersion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setCharacterEncoding("UTF-8");
			String appId = StaticMethod.nullObject2String(request.getParameter("appId"));
			IAndroidService androidService  = (IAndroidService) this.getBean("androidServiceImpl");
			Search search = new Search();
			search.addFilterEqual("id", appId);
			
			List<AndroidAppInfo> returnList = androidService.search(search);
			if(null != returnList && !returnList.isEmpty()){
				AndroidAppInfo info = returnList.get(0);
				String path = returnList.get(0).getFilePath();
				File file = new File(path);
				System.out.println("file.length   "+file.length());
				if (file.exists()) {
					long filelength = file.length(); // 取得文件长度
					byte[] b = new byte[1024];
					// 设置文件输出流
					FileInputStream fin = new FileInputStream(file);
					DataInputStream in = new DataInputStream(fin);
					// 设置响应头信息，让下载的文件显示保存信息
					response.addHeader(
							"Content-Disposition",
							"attachment;filename="
							+ URLEncoder.encode(path,
							"utf-8"));
					// 设置输出流的MIME类型
					response.setContentType("application/octet-stream");
					// 确定长度
					String filesize = Long.toString(filelength);
					// 设置输出文件的长度
					response.setHeader("Content-Length ", filesize);
					// 取得输出流
					ServletOutputStream servletout = response.getOutputStream();
					// 发送文件数据，每次1024字节，最后一次单独计算
					int totalsize = 1024;
					byte[] buffer = new byte[totalsize];
					int length = 0;
					while ((length = in.read(buffer)) != -1) {
						servletout.write(buffer, 0, length);
						servletout.flush();
					}
					servletout.close();
					in.close();
					fin.close();
					
				}
			}
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public ActionForward listAppHall(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		IAndroidService androidService  = (IAndroidService) this.getBean("androidServiceImpl");
		Search search = new Search();
		search.addFilterEqual("packageName", "com.boco.apphall");
		search.addFilterEqual("downloadStatus", "0");
		search.addSortDesc("addTime");
		List<AndroidAppInfo> returnList = androidService.search(search);
		if(null != returnList && !returnList.isEmpty()){
			request.setAttribute("appInfo", returnList.get(0));
		}
//		request.setAttribute("", arg1)
		return mapping.findForward("listAppHall");
	}
	public void downLoadAppHall(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String appId = request.getParameter("appId");
		IAndroidService androidService  = (IAndroidService) this.getBean("androidServiceImpl");
		Search search = new Search();
		search.addFilterEqual("id", appId);
		List<AndroidAppInfo> returnList = androidService.search(search);
		if(null != returnList && !returnList.isEmpty()){
			String filePath = returnList.get(0).getFilePath();
			File file = new File(filePath);
			if (file.exists()) {
				long filelength = file.length(); // 取得文件长度
				byte[] b = new byte[1024];
				// 设置文件输出流
				FileInputStream fin = new FileInputStream(file);
				DataInputStream in = new DataInputStream(fin);
				// 设置响应头信息，让下载的文件显示保存信息
				response.addHeader(
						"Content-Disposition",
						"attachment;filename="
						+ URLEncoder.encode(filePath,
						"utf-8"));
				// 设置输出流的MIME类型
				response.setContentType("application/octet-stream");
				// 确定长度
				String filesize = Long.toString(filelength);
				// 设置输出文件的长度
				response.setHeader("Content-Length ", filesize);
				// 取得输出流
				ServletOutputStream servletout = response.getOutputStream();
				// 发送文件数据，每次1024字节，最后一次单独计算
				int totalsize = 1024;
				byte[] buffer = new byte[totalsize];
				int length = 0;
				while ((length = in.read(buffer)) != -1) {
					servletout.write(buffer, 0, length);
					servletout.flush();
				}
				servletout.close();
				in.close();
				fin.close();
			}
		}
	}
	
	public List saveFile(HttpServletRequest request, String appCode,
			String accesspriesFileNames) throws AccessoriesException {
		return null;
	}
	public void validateApp(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response){
		String userId = this.getUserId(request);
		String appPackage = StaticMethod.nullObject2String(request.getParameter("package"));
		
		
	}
	
}
