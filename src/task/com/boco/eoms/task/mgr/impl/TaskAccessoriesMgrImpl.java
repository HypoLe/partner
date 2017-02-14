package com.boco.eoms.task.mgr.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.struts.upload.FormFile;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.task.dao.ITaskAccessoriesDao;
import com.boco.eoms.task.mgr.ITaskAccessoriesMgr;
import com.boco.eoms.task.mgr.ITaskMgr;
import com.boco.eoms.task.model.Task;
import com.boco.eoms.task.model.TaskAccessories;

public class TaskAccessoriesMgrImpl implements ITaskAccessoriesMgr {

	private ITaskAccessoriesDao accessoriesDao;

	public void setAccessoriesDao(ITaskAccessoriesDao accessoriesDao) {
		this.accessoriesDao = accessoriesDao;
	}

	public void delFile(String fileId, String taskId) throws Exception {
		// 从任务中删除附件
		ITaskMgr taskMgr = (ITaskMgr) ApplicationContextHolder.getInstance()
				.getBean("taskMgr");
		Task task = taskMgr.getTask(taskId);
		String accessories = StaticMethod.null2String(task.getAccessories()).trim();
		String[] accessoriesId = accessories.split(",");
		String newAccessoriesId = "";
		for (int i = 0; i < accessoriesId.length; i++) {
			if (!accessoriesId[i].equals(fileId)) {
				if ("".equals(newAccessoriesId)) {
					newAccessoriesId += accessoriesId[i];
				} else {
					newAccessoriesId += "," + accessoriesId[i];
				}
			}
		}
		task.setAccessories(newAccessoriesId);
		taskMgr.saveTask(task);

		// 从附件表中删除附件
		accessoriesDao.delAccessories(fileId);
	}

	public void uploadFile(FormFile formFile, String folderPath, String taskId,
			String uploader) throws Exception {
		// 目录不存在则创建
		File folder = new File(folderPath);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		String fileName = formFile.getFileName();
		String expand = fileName.substring(fileName.lastIndexOf('.') + 1);// 文件扩展名
		Date puredate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String date = dateFormat.format(puredate);
		String attend = String.valueOf(puredate.getTime()); // 取得毫秒数
		attend = attend.substring(attend.length() - 2, attend.length()); // 取毫秒的后两位,避免有同时上传的可能性
		String mappingName = date + attend + "." + expand; // 映射文件名

		File file = new File(folderPath + File.separator + mappingName);
		InputStream stream = formFile.getInputStream();
		// 将该数据流写入到指定文件中
		FileOutputStream out = new FileOutputStream(file);
		byte[] buffer = new byte[4096]; // To hold file contents
		int bytes_read;
		while ((bytes_read = stream.read(buffer)) != -1) // Read until EOF
		{
			out.write(buffer, 0, bytes_read);
		}
		if (stream != null) {
			try {
				stream.close();
			} catch (IOException e) {
				;
			}
		}
		if (out != null) {
			try {
				out.close();
			} catch (IOException e) {
				;
			}
		}
		// --- 上传文件 ---

		// --- 设置accessory属性值 ---
		TaskAccessories accessory = new TaskAccessories();
		accessory.setFileName(fileName);
		accessory.setMappingName(mappingName);
		accessory.setFilePath(folderPath);
		accessory.setFileSize(String.valueOf(formFile.getFileSize()));
		accessory.setUploader(uploader);
		accessory.setUploadTime(StaticMethod.getCurrentDateTime());
		// --- 设置accessory属性值 ---

		// 保存附件
		String accessoryId = accessoriesDao.saveAccessories(accessory);

		// 将附件保存到任务表中
		ITaskMgr taskMgr = (ITaskMgr) ApplicationContextHolder.getInstance()
				.getBean("taskMgr");
		Task task = taskMgr.getTask(taskId);
		if (null == task.getAccessories() || "".equals(task.getAccessories())) {
			task.setAccessories(accessoryId);
		} else {
			task.setAccessories(task.getAccessories().trim() + ","
					+ accessoryId);
		}
		taskMgr.saveTask(task);
	}

	public TaskAccessories getFile(String fileId) throws Exception {
		return accessoriesDao.getAccessories(fileId);
	}

}
