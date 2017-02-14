package com.boco.activiti.partner.process.webapp.androaction;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileOutputStream;
import net.sf.json.JSONArray;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.boco.activiti.partner.process.model.JunctionBoxCheckInfor;
import com.boco.activiti.partner.process.model.JunctionBoxCheckPhoto;
import com.boco.activiti.partner.process.po.JunctionBoxModel;
import com.boco.activiti.partner.process.service.IJunctionBoxCheckPhotoService;
import com.boco.activiti.partner.process.service.IJunctionBoxCheckService;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.mobile.util.MobileCommonUtils;
import com.boco.eoms.mobile.util.MobileConstants;
import com.boco.eoms.partner.process.util.CommonUtils;
import com.boco.eoms.sheet.base.webapp.action.NewSheetAction;

/**
 * Created with IntelliJ IDEA. User: informix Date: 13-9-30 Time: 上午11:31 To
 * change this template use File | Settings | File Templates.
 */
public class AndroidJunctionBoxCheckAction extends NewSheetAction {
	private static Logger logger = Logger
			.getLogger(AndroidWorkOrderAction.class);

	/**
	 * 通过二维码扫描，查看交接箱详情
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void showJunctionBoxDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String fiberNodeId = StaticMethod.nullObject2String(request
				.getParameter("fiberNodeId")); // 交接箱ID

		IJunctionBoxCheckService junctionBoxCheckService = (IJunctionBoxCheckService) getBean("junctionBoxCheckService");
		String returnJson = "";
		// 1.交接箱数据是否存在
		JunctionBoxModel junctionBoxModel = junctionBoxCheckService
				.findJunctionBoxDetail(fiberNodeId);
		if (junctionBoxModel != null) {// 2.判断交接箱是否已经被核查了
			boolean result = junctionBoxCheckService
					.judgeJunctionBoxIsCheckCompleted(fiberNodeId);
			Map<String, Object> returnMap = new HashMap<String, Object>();
			if (result) {
				returnMap.put("resultInfor", "haveCheck");
			} else {
				returnMap.put("resultInfor", "notCheck");
			}
			returnMap.put("fiberNodeId", junctionBoxModel.getFiberNodeId());
			returnMap.put("fiberNodeNo", junctionBoxModel.getFiberNodeNo());
			returnMap.put("bureauId", junctionBoxModel.getBureauId());
			returnMap.put("bureauName", junctionBoxModel.getBureauName());
			returnMap.put("areaId", junctionBoxModel.getAreaId());
			returnMap.put("areaName", junctionBoxModel.getAreaName());
			returnMap.put("cityId", junctionBoxModel.getCityId());
			returnMap.put("cityName", junctionBoxModel.getCityName());
			returnMap.put("memo", junctionBoxModel.getMemo());
			returnMap.put("countNum", junctionBoxModel.getCountNum());
			JSONArray returnArray = JSONArray.fromObject(returnMap);
			returnJson = returnArray.toString();

		} else {
			returnJson = "[{\"resultInfor\":\"notExist\"}]";// 在数据库中查询不到交接箱信息
		}
		MobileCommonUtils.responseWrite(response, returnJson, "UTF-8");
		return;
	}

	/**
	 * 保存交接箱核查信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void saveJunctionBoxCheckInfor(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			TawSystemSessionForm sessionform = (TawSystemSessionForm) request
					.getSession().getAttribute("sessionform");
			String userId = sessionform.getUserid();
			// 1.保存核查信息
			String fiberNodeId = request.getParameter("fiberNodeId");// 交接箱id
			String fiberNodeNo = request.getParameter("fiberNodeNo");// 交接箱编号
			String gpsX = request.getParameter("gpsX");// 交接箱x坐标
			String gpsY = request.getParameter("gpsY");// 交接箱y坐标
			// String insertMan = request.getParameter("insertMan");//采集人
			String insertDate = request.getParameter("insertDate");// 采集时间
			String posiChange = request.getParameter("posiChange");// 位置是否发生变化
			String modulChange = request.getParameter("modulChange");// 模块数是否发生变化
			String changeMemo = request.getParameter("changeMemo");// 变化内容

			DecimalFormat df = new DecimalFormat(".######");
			IJunctionBoxCheckService junctionBoxCheckService = (IJunctionBoxCheckService) getBean("junctionBoxCheckService");
			JunctionBoxCheckInfor junctionBoxCheckInfor = new JunctionBoxCheckInfor();
			junctionBoxCheckInfor.setFiberNodeId(Double.valueOf(fiberNodeId));
			junctionBoxCheckInfor.setFiberNodeNo(fiberNodeNo);
			junctionBoxCheckInfor.setGpsX(Double.valueOf(gpsX));
			junctionBoxCheckInfor.setGpsY(Double.valueOf(gpsY));
			junctionBoxCheckInfor.setInsertMan(userId);
			junctionBoxCheckInfor.setInsertDate(insertDate);
			junctionBoxCheckInfor.setPosiChange(posiChange);
			junctionBoxCheckInfor.setModulChange(modulChange);
			junctionBoxCheckInfor.setChangeMemo(changeMemo);
			junctionBoxCheckInfor.setCreateTime(new Date());
			junctionBoxCheckService.save(junctionBoxCheckInfor);
		} catch (Exception e) {
			e.printStackTrace();
			MobileCommonUtils.responseWrite(response,
					MobileConstants.failureStr, "UTF-8");
		}
		MobileCommonUtils.responseWrite(response, MobileConstants.successStr,
				"UTF-8");
	}

	/**
	 * 保存交接箱核查照片
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void saveJunctionBoxCheckPhoto(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DiskFileItemFactory difactory;
		try {
			String fiberNodeId = "";
			String fiberNodeNo = "";
			String longitude = "";
			String latitude = "";
			String photoTime = "";

			difactory = new DiskFileItemFactory();
			difactory.setSizeThreshold(1024 * 1024);
			ServletFileUpload upload = new ServletFileUpload(difactory);
			upload.setSizeMax(1024 * 3024); // 设置上传文件的最大容量
			String fileName = StaticMethod.getCurrentDateTime("yyyyMMddHHmmss")
					+ StaticMethod.getRandomCharAndNumr(4);

			String tPath = "junctionBoxCheck" + File.separatorChar
					+ fileName.substring(0, 4) + File.separatorChar
					+ fileName.substring(4, 6) + File.separatorChar
					+ fileName.substring(6, 8) + File.separatorChar;
			tPath = tPath.replace("\\", "/");

			String remotePhotoUrl = CommonUtils
					.getImgJunctionBoxCheckPublicUrl(); // 存放图片的共享目录

			String placePath = remotePhotoUrl + tPath;

			String nameStr = "";// 文件名（带路径）

			SimpleDateFormat dtFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			List<FileItem> items = upload.parseRequest(request);
			; // 取得表单全部数据
			for (FileItem item : items) {
				if (!item.isFormField()) {
					String comeFileName = item.getName();
					nameStr = tPath + comeFileName;
					InputStream in = null;
					OutputStream out = null;
					try {
						SmbFile remoteFile2 = new SmbFile(placePath);
						if (!remoteFile2.exists()) {
							remoteFile2.mkdirs();
						}
						SmbFile remoteFile = new SmbFile(placePath
								+ item.getName());
						remoteFile.connect(); // 尝试连接
						in = item.getInputStream();
						out = new BufferedOutputStream(new SmbFileOutputStream(
								remoteFile));

						byte[] buffer = new byte[4096];
						int len = 0; // 读取长度
						while ((len = in.read(buffer, 0, buffer.length)) != -1) {
							out.write(buffer, 0, len);
						}
						out.flush(); // 刷新缓冲的输出流
					} catch (Exception e) {
						String msg = "交接箱核查上传-发生错误：" + e.getLocalizedMessage();
						System.out.println(msg);
						MobileCommonUtils.responseWrite(response,
								MobileConstants.failureStr, "UTF-8");

						return;
					} finally {
						try {
							if (out != null) {
								out.close();
							}
							if (in != null) {
								in.close();
							}
						} catch (Exception e) {
						}
					}

				} else {
					String name = item.getFieldName();
					if ("fiberNodeId".equals(name)) {// 交接箱ID
						fiberNodeId = StaticMethod.nullObject2String(item
								.getString());
					}

					if ("fiberNodeNo".equals(name)) {// 交接箱名
						// fiberNodeNo = StaticMethod.nullObject2String(item
						// .getString());

						fiberNodeNo = new String(StaticMethod
								.nullObject2String(item.getString()).getBytes(
										"ISO8859_1"), "utf-8");
					}
					if ("longitude".equals(name)) {// 经度
						longitude = StaticMethod.nullObject2String(item
								.getString());
					}
					if ("latitude".equals(name)) {// 纬度
						latitude = StaticMethod.nullObject2String(item
								.getString());
					}
					if ("photoTime".equals(name)) {// 照片时间
						photoTime = StaticMethod.nullObject2String(item
								.getString());
					}
				}
			}
			if (fiberNodeId.equals("") || fiberNodeNo.equals("")) {
				MobileCommonUtils.responseWrite(response,
						MobileConstants.failureStr, "UTF-8");
				return;
			}
			// 往交接箱核查照片表保存照片信息
			TawSystemSessionForm sessionform = (TawSystemSessionForm) request
					.getSession().getAttribute("sessionform");
			String userId = sessionform.getUserid();
			IJunctionBoxCheckPhotoService junctionBoxCheckPhotoService = (IJunctionBoxCheckPhotoService) getBean("junctionBoxCheckPhotoService");
			System.out.println("junctionBoxCheckPhotoService="
					+ junctionBoxCheckPhotoService);
			JunctionBoxCheckPhoto junctionBoxCheckPhoto = new JunctionBoxCheckPhoto();
			System.out
					.println("junctionBoxCheckPhoto=" + junctionBoxCheckPhoto);
			junctionBoxCheckPhoto.setFilePath(nameStr);
			junctionBoxCheckPhoto.setFiberNodeId(Double.valueOf(fiberNodeId));
			junctionBoxCheckPhoto.setFiberNodeNo(fiberNodeNo);
			junctionBoxCheckPhoto.setLongitude(longitude);
			junctionBoxCheckPhoto.setLatitude(latitude);
			if (!photoTime.equals("")) {
				Date parse = dtFormat.parse(photoTime);
				junctionBoxCheckPhoto.setPhotoTime(parse);
			}
			junctionBoxCheckPhoto.setUserId(userId);
			junctionBoxCheckPhoto.setCreateTime(new Date());
			junctionBoxCheckPhotoService.save(junctionBoxCheckPhoto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		MobileCommonUtils.responseWrite(response, MobileConstants.successStr,
				"UTF-8");

	}

}
