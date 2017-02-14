package com.boco.eoms.partner.inspect.mgr.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.inspect.dao.IPnrInspectTaskFileDao;
import com.boco.eoms.partner.inspect.mgr.IPnrInspectTaskFileMgr;
import com.boco.eoms.partner.inspect.model.PnrInspectTaskFile;
import com.boco.eoms.partner.inspect.util.InspectConstants;

public class PnrInspectTaskFileMgrImpl implements IPnrInspectTaskFileMgr {
	private IPnrInspectTaskFileDao pnrInspectTaskFileDao;

	public void saveOrUpdateTaskFile(PnrInspectTaskFile taskFile,String data) throws SQLException {
		pnrInspectTaskFileDao.save(taskFile,data);
	}
	
	
	public PnrInspectTaskFile getInspectTaskFile(String id)  {
		return (PnrInspectTaskFile)pnrInspectTaskFileDao.getObject(PnrInspectTaskFile.class, id);
	}
	
	public void deleteInspectTaskFile(String id) {
		pnrInspectTaskFileDao.removeObject(PnrInspectTaskFile.class, id);
	}
	
	public IPnrInspectTaskFileDao getPnrInspectTaskFileDao() {
		return pnrInspectTaskFileDao;
	}

	public void setPnrInspectTaskFileDao(
			IPnrInspectTaskFileDao pnrInspectTaskFileDao) {
		this.pnrInspectTaskFileDao = pnrInspectTaskFileDao;
	}


	public List findFileByHQL(String hql) {
		List list = this.pnrInspectTaskFileDao.findByHQL(hql);
		return list;
	}
	
	/**
	 * 维护员提交巡检结果--增加图数据（福建二期提供手机接口）
	 * add by zhangkeqi
	 * Jul 19, 2012 9:17:55 AM
	 * @param taskItemId
	 * @param photoThumbnailData
	 * @param photoData
	 * @throws SQLException 
	 */
	public void saveInspectTaskItemPhotoData(String taskItemId,String photoThumbnailData,String photoData) throws SQLException {
		if(photoThumbnailData != null && !"".equals(photoThumbnailData)) {
			PnrInspectTaskFile photoThumbnail = new PnrInspectTaskFile();
			photoThumbnail.setFileType(InspectConstants.TASK_ITEM_PHOTO_THUMBNAIL);
			pnrInspectTaskFileDao.save(photoThumbnail,photoThumbnailData);
		}
		
		if(photoData != null && !"".equals(photoData)) {
			PnrInspectTaskFile photo = new PnrInspectTaskFile();
			photo.setFileType(InspectConstants.TASK_ITEM_PHOTO);
			pnrInspectTaskFileDao.save(photo, photoData);
		}
	}
	
	/**
	 * 查询巡检项照片缩略图id（福建二期提供手机接口）
	 * 查询成功返回照片缩略图id，否则反回空值
	 * add by zhangkeqi
	 * Jul 19, 2012 10:01:27 AM
	 * @param taskItemId
	 * @return
	 */
	public String getTaskItemPhotoThumbnailId(String taskItemId){
		String hql = "select file.id from PnrInspectTaskFile file where file.taskItemId='" + taskItemId + "' and file.fileType='" + InspectConstants.TASK_ITEM_PHOTO_THUMBNAIL + "'";
		List list = this.pnrInspectTaskFileDao.findByHQL(hql);
		
		String photoThumbnailId = "";
		if(list!=null && list.size()>0) {
			photoThumbnailId = (String)list.get(0);
		}
		
		return photoThumbnailId;
	}
	/**
	 * 查询巡检项照片id（福建二期提供手机接口）
	 * 查询成功返回照片id，否则反回空值
	 * 
	 * add by zhangkeqi
	 * Jul 19, 2012 10:01:27 AM
	 * @param taskItemId
	 * @return
	 */
	public String getTaskItemPhotoId(String taskItemId){
		String hql = "select file.id from PnrInspectTaskFile file where file.taskItemId='" + taskItemId + "' and file.fileType='" + InspectConstants.TASK_ITEM_PHOTO + "'";
		List list = this.pnrInspectTaskFileDao.findByHQL(hql);
		
		String photoId = "";
		if(list!=null && list.size()>0) {
			photoId = (String)list.get(0);
		}
		
		return photoId;
	}
	
	/**
	 * 根据图片id反回图片的base64数据（福建二期提供手机接口）
	 * 成功返回图片数据，否则反回空值
	 * 
	 * add by zhangkeqi
	 * Jul 19, 2012 10:05:48 AM
	 * @param photoId
	 * @return
	 * @throws SQLException 
	 */
	public String getPhotoData(String photoId) throws SQLException {
		String data = "";
		PnrInspectTaskFile taskFile = null;
		List list = this.findFileByHQL("from PnrInspectTaskFile file where file.id='"+photoId+"'");
		if(list != null && list.size()>0) {
			taskFile = (PnrInspectTaskFile)list.get(0);
		}
		
		if(taskFile != null) {
			java.sql.Clob clob = taskFile.getFileData();
			if(clob!=null) {
				data = clob.getSubString(1, (int)clob.length());
			}
		}
		return data;
	}


	/**
	 * 根据图片的类型进行分页查询
	 * */
	public Map getResources(Integer curPage, Integer pageSize, String whereStr) {
		
		return pnrInspectTaskFileDao.getResources(curPage, pageSize, whereStr);
	}


	public PnrInspectTaskFile getPnrInspectTaskFileByFileId(String fileId) {
		String hql = "from PnrInspectTaskFile where fileType='3gp' and  file_id='" + fileId+"' order by createTime desc";
		List list = this.pnrInspectTaskFileDao.findByHQL(hql);
		
		if(list!=null && list.size()>0) {
			return (PnrInspectTaskFile) list.get(0);
		}
		
		return null;
	}
	
}
