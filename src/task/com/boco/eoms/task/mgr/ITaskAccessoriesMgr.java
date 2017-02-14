package com.boco.eoms.task.mgr;

import org.apache.struts.upload.FormFile;

import com.boco.eoms.task.model.TaskAccessories;

/**
 * 任务管理附件业务接口
 * 
 * @author qiuzi
 * 
 */
public interface ITaskAccessoriesMgr {

	/**
	 * 上传文件
	 * 
	 * @param formFile
	 *            文件
	 * @param folderPath
	 *            上传文件路径
	 * @param taskId
	 *            文件所属任务Id
	 * @param uploader
	 *            上传人
	 * @throws Exception
	 */
	public void uploadFile(FormFile formFile, String folderPath, String taskId,
			String uploader) throws Exception;

	/**
	 * 删除文件
	 * 
	 * @param fileId
	 *            文件Id
	 * @param taskId
	 *            任务Id
	 * @throws Exception
	 */
	public void delFile(String fileId, String taskId) throws Exception;

	/**
	 * 根据主键查询文件
	 * 
	 * @param fileId
	 *            文件Id
	 * @throws Exception
	 */
	public TaskAccessories getFile(String fileId) throws Exception;

}