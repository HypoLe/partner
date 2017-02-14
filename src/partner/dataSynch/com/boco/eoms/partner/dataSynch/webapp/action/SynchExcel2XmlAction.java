package com.boco.eoms.partner.dataSynch.webapp.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
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

import com.boco.eoms.base.model.PageModel;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.partner.dataSynch.excel2xml.Excel2xmlProcesser;
import com.boco.eoms.partner.dataSynch.excel2xml.ZipUtil;
import com.boco.eoms.partner.dataSynch.mgr.AbstractDataSynchMgr;
import com.boco.eoms.partner.dataSynch.mgr.ISynchExceptionRecordMgr;
import com.boco.eoms.partner.dataSynch.model.CheckFile;
import com.boco.eoms.partner.dataSynch.model.SynchExceptionRecord;
import com.boco.eoms.partner.dataSynch.util.CommonUtil;
import com.boco.eoms.partner.dataSynch.util.DataParserUtils;
import com.boco.eoms.partner.dataSynch.util.Excel2XMLUtil;
import com.boco.eoms.partner.dataSynch.util.FileUpload;
import com.google.gson.JsonObject;

/**
 * 
 * Description: <p>
 *				数据同步Excel转化成xmlAction
 *				</p>
 * Copyright:   Copyright (c)2012
 * Company:     BOCO
 * @author:     zhangkeqi
 * @version:    1.0
 * Create at:   Dec 5, 2012-9:51:44 PM
 */
public class SynchExcel2XmlAction extends BaseAction {
	
	/**
	 * 查询展示异常列表
	 */
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ISynchExceptionRecordMgr synchExceptionRecordMgr = (ISynchExceptionRecordMgr) getBean("synchExceptionRecordMgr");
		SynchExceptionRecord record = new SynchExceptionRecord();
		record.setDataType(request.getParameter("dataType"));
		record.setCuid(request.getParameter("createTime"));
		
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				"list")
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = Integer.valueOf(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		PageModel pm = synchExceptionRecordMgr.findSynchExceptionRecordList(pageIndex*pageSize, pageSize,record);
		request.setAttribute("list", pm.getDatas());
		request.setAttribute("size", pm.getTotal());
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("list");
	}
	
	
	public ActionForward uploadExcelFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("UTF-8");
		JsonObject json = new JsonObject();
		OutputStream os = response.getOutputStream();
		try {
			String savePath = servlet.getServletContext().getRealPath("/dataSynch/excel/");
			File saveDir = new File(savePath);
			if(!saveDir.exists()) {  
				saveDir.mkdirs();
			}
			
			Map<String,Object> uploadMap = FileUpload.upload(request,saveDir);
			
			if("true".equals(uploadMap.get("success"))) {
				//装载Excel数据同步映射文件
				Excel2XMLUtil.initMap();
				Excel2xmlProcesser processer = new Excel2xmlProcesser();
				String excelFilePath = uploadMap.get("savePath").toString();
				String excel2xmlDir = servlet.getServletContext().getRealPath("/dataSynch/excel2xml/");
				processer.setResName(((Map)uploadMap.get("params")).get("resourceName").toString());
				processer.setExcel2xmlDir(excel2xmlDir);
				processer.excelFile2XmlFile(excelFilePath);
				String generateTime = processer.getGenerateTime();
				String baseFileName = processer.getBaseFileName();
				File xmlDataDir = new File(excel2xmlDir,generateTime);
				//生成压缩文件文件名为baseFileName.zip
				String zipFileName = baseFileName+".zip";
				File zipFile = new File(xmlDataDir,zipFileName);
				ZipUtil.zipFiles(xmlDataDir.listFiles(),zipFile);
				
				
				// 读到流中
				InputStream is = new FileInputStream(zipFile);// 文件的存放路径
				// 设置输出的格式
				response.reset();
				response.setContentType("application/x-msdownload;charset=UTF-8");
				response.setHeader("Content-Disposition", "attachment; filename="+zipFileName);
	
				// 循环取出流中的数据
				byte[] b = new byte[1024];
				int len = 0;
				while ((len = is.read(b)) > 0) {
					os.write(b, 0, len);
				}
				is.close();
				os.flush();
				
				json.addProperty("info", "true");
				json.addProperty("message", "上传成功,Excel转xml成功！");
			} else {
				json.addProperty("info", "false");
				json.addProperty("message", "上传失败！");
				
			}

//			os.write(json.toString().getBytes());
//			os.flush();
		} catch(Exception e) {
			e.printStackTrace();
			json.addProperty("info", "false");
			json.addProperty("message", "上传失败,发生未知错误！");
			
//			os.write(json.toString().getBytes());
//			os.flush();
		} finally {
			os.close();
		}
		return null;
	}
	
	public ActionForward uploadSynchDataFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("UTF-8");
		JsonObject json = new JsonObject();
		try {
			String time = new Date().getTime()+"";
			String saveDirStr = servlet.getServletContext().getRealPath("/dataSynch/uploadzip/"+time);
			File saveDir = new File(saveDirStr);
			if(!saveDir.exists()) {  
				saveDir.mkdirs();
			}
			
			Map<String,Object> uploadMap = FileUpload.upload(request,saveDir);
			if("true".equals(uploadMap.get("success"))) {
				//解压数据文件
				String dataFilePath = uploadMap.get("savePath").toString();
				File dataFile = new File(dataFilePath);
				ZipUtil.unZipFiles(dataFile, saveDir);
				//获取CHK文件
				File chkFile = filterCHKFile(dataFile.getParentFile());
				//同步数据
				String resName = ((Map)uploadMap.get("params")).get("resourceName").toString();
				String beanName = CommonUtil.lowerFirst(resName)+"MgrImpl";
				Map<String,String> map = this.dataSynch(chkFile.getAbsolutePath(),beanName,saveDirStr);
				
				if("true".equals(map.get("success"))) {
					json.addProperty("info", "true");
					json.addProperty("message", "上传成功数据同步成功");
				} else {
					json.addProperty("info", "true");
					json.addProperty("message", "上传成功，数据同步失败："+map.get("info").toString());
				}
				
			} else {
				json.addProperty("info", "false");
				json.addProperty("message", "上传失败！");
			}
			response.getWriter().write(json.toString());
		} catch(Exception e) {
			e.printStackTrace();
			json.addProperty("info", "false");
			json.addProperty("message", "上传失败,发生未知错误！");
			response.getWriter().write(json.toString());
		}
		return null;
	}
	
	/**
	 * 数据同步
	 * @throws Exception 
	 */
	private Map<String,String> dataSynch(String checkFilePath,String beanName,String dataFileDir) throws Exception {
		List<CheckFile> checkFileList;
		Map<String,String> map = new HashMap<String,String>();
		map.put("success","true");
		try {
			checkFileList = DataParserUtils.parseCheckFile(checkFilePath);
			if(checkFileList.isEmpty()){
				System.out.println("------------checkFileList.isEmpty();------------");
				map.put("info", "CHK文件为空");
				map.put("success","false");
				return map;
			}
			AbstractDataSynchMgr dataSynchMgr = (AbstractDataSynchMgr) ApplicationContextHolder
			.getInstance().getBean(beanName);
			dataSynchMgr.setSynchType("excel");
			dataSynchMgr.setDataFileDir(dataFileDir);
			dataSynchMgr.dataSynch(checkFileList);
		} catch (Exception e) {
			map.put("success","false");
			map.put("info", e.getMessage());
			e.printStackTrace();
//			throw e;
		}
		return map;
	}
	/**
	 * 过滤CHK文件
	 * @param baseDir
	 * @return
	 */
	private File filterCHKFile(File baseDir) {
		File[] fs = baseDir.listFiles();
		for(File f : fs) {
			if(f.isFile()) {
				if(f.getName().indexOf("CHK")>0) {
					return f;
				}
			}
		}
		return null;
	}
	
	public static void main(String[]safd) {
		
		
		System.out.println(System.getProperty("java.io.tmpdir"));
		System.out.println(System.getenv("TMP"));
		System.out.println(System.getProperty("user.dir"));
	}
	
}
