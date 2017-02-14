package com.boco.eoms.partner.inspect.mgr;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.inspect.model.PnrInspectTaskFile;


/**
 * <p>
 * Title:属地化巡检文件上传（Base64转码）业务类
 * </p>
 * <p>
 * Description:属地化巡检文件上传（Base64转码）业务类
 * </p>
 * <p>
 * Date:2012-8-20 上午09:56:10
 * </p>
 * 
 * @author lee
 * @version 1.0
 * 
 */
public interface IPnrInspectTaskFileMgr {
	public void saveOrUpdateTaskFile(PnrInspectTaskFile taskFile,String data) throws SQLException;
	public PnrInspectTaskFile getInspectTaskFile(String id);
	public void deleteInspectTaskFile(String id);
	public List findFileByHQL(String hql);
	
	/**
	 * 维护员提交巡检结果--增加图数据（福建二期提供手机接口）
	 * add by zhangkeqi
	 * Jul 19, 2012 9:17:55 AM
	 * @param taskItemId
	 * @param photoThumbnailData
	 * @param photoData
	 * @throws SQLException 
	 */
	public void saveInspectTaskItemPhotoData(String taskItemId,String photoThumbnailData,String photoData) throws SQLException;
	/**
	 * 查询巡检项照片缩略图id（福建二期提供手机接口）
	 * 查询成功返回照片缩略图id，否则反回空值
	 * add by zhangkeqi
	 * Jul 19, 2012 10:01:27 AM
	 * @param taskItemId
	 * @return
	 */
	public String getTaskItemPhotoThumbnailId(String taskItemId);
	/**
	 * 查询巡检项照片id（福建二期提供手机接口）
	 * 查询成功返回照片id，否则反回空值
	 * 
	 * add by zhangkeqi
	 * Jul 19, 2012 10:01:27 AM
	 * @param taskItemId
	 * @return
	 */
	public String getTaskItemPhotoId(String taskItemId);
	/**
	 * 查询巡检项照片id（福建二期提供手机接口）
	 * 查询成功返回照片id，否则反回空值
	 * 
	 * add by zhangkeqi
	 * Jul 19, 2012 10:01:27 AM
	 * @param taskItemId
	 * @return
	 */
	public PnrInspectTaskFile getPnrInspectTaskFileByFileId(String fileId);
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
	public String getPhotoData(String photoId) throws SQLException;
	
	/**
	 * 根据图片的类型进行分页查询
	 * */
	public Map getResources(Integer curPage, Integer pageSize,
			final String whereStr);
	
}
