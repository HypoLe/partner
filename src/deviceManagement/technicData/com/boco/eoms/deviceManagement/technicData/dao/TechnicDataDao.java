package com.boco.eoms.deviceManagement.technicData.dao;

import java.util.List;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.deviceManagement.technicData.model.TechnicDataFile;
import com.boco.eoms.deviceManagement.technicData.model.TechnicDataFolderMapping;
import com.boco.eoms.deviceManagement.technicData.model.TechnicDataFolderShare;

/**
 * 
 * <p>
 * Title:代维技术资料管理dao接口
 * </p>
 * <p>
 * Description:代维技术资料管理dao接口
 * </p>
 * <p>
 * Date:2008-5-6 13:53:15
 * </p>
 * 
 * @author 张珂齐
 * 
 */
public interface TechnicDataDao extends Dao {

	/**
	 * 
	 * @param id
	 */
	public TechnicDataFolderShare getTechnicDataFolderShare(
			String id);

	/**
	 * 保存文件夹共享信息
	 * 
	 * @param technicDataFolderShare
	 *            文件夹共享信息
	 */
	public void saveTechnicDataFolderShare(
			TechnicDataFolderShare technicDataFolderShare);

	/**
	 * 删除文件夹共享信息
	 * 
	 * @param technicDataFolderShare
	 * 
	 */
	public void removeTechnicDataFolderShare(
			TechnicDataFolderShare technicDataFolderShare);

	/**
	 * 获取共享文件夹给我的全部用户
	 * 
	 * @param toUserId
	 * @return
	 */
	public List listShareToMeUsers(String toUserId);

	/**
	 * 获取某用户共享给我的全部文件夹
	 * 
	 * @param fromUserId
	 * @param toUserId
	 * @return
	 */
	public List listShareToMe(String fromUserId, String toUserId);

	/**
	 * 获取我共享的全部文件夹共享信息
	 * 
	 * @param fromUserId
	 * @return
	 */
	public List listMyShare(String fromUserId);

	/**
	 * 删除某文件夹的全部共享信息
	 * 
	 * @param folderPath
	 * @param fromUserId
	 */
	public void removeFolderShareInfo(String folderPath, String fromUserId);

	/**
	 * 根据文件夹所属用户Id和文件夹路径获取文件夹共享信息
	 * 
	 * @param fromUserId
	 * @param folderPath
	 * @return
	 */
	public TechnicDataFolderShare getTechnicDataFolderShare(
			String fromUserId, String folderPath);

	/**
	 * 从映射中查询文件夹名
	 * 
	 * @param id
	 *            主键（也是在服务器上实际创建的文件夹名）
	 * @return
	 */
	public String getFolderNameFromMapping(String id);

	/**
	 * 根据用户ID和虚拟路径查询主键，即真实路径
	 * 
	 * @param userId
	 *            用户ID
	 * @param folderPath
	 *            文件夹名称
	 * @return
	 */
	public String getMappingId(String userId, String folderName, String parentId);

	/**
	 * 保存映射关系
	 * 
	 * @param mapping
	 *            映射
	 * @return
	 */
	public String saveFolderMapping(TechnicDataFolderMapping mapping);

	/**
	 * 根据主键删除映射关系
	 * 
	 * @param id
	 *            主键
	 */
	public void delFolderMapping(String id);

	/**
	 * 获取子文件夹列表
	 * 
	 * @param parentId
	 *            父文件夹Id
	 */
	public List listsubFolders(String parentId);

	/**
	 * 根据主键查询映射信息
	 * 
	 * @param id
	 * @return
	 */
	public TechnicDataFolderMapping getTechnicDataFolderMapping(
			String id);

	/**
	 * 保存上传的文件信息到数据库
	 * 
	 * @param technicDataFile
	 */
	public void saveTechnicDataFolderFile(
			TechnicDataFile technicDataFile);

	/**
	 * 通过映射文件名查询文件信息,映射文件名是精确到毫秒的时间
	 * 
	 * @param mappingName
	 * @return
	 */
	public TechnicDataFile getTechnicDataFile(String userId, String mappingName);

	/**
	 * 查询某用户某文件夹下的文件列表
	 * 
	 * @param userId
	 * @param folderMappingId
	 * @return
	 */
	public List listTechnicDataFile(String userId,
			String folderMappingId);

	/**
	 * 删除数据库上传文件信息
	 * 
	 * @param technicDataFile
	 */
	public void delTechnicDataFile(
			TechnicDataFile technicDataFile);
	
	/**
	 * 返回文件夹中文件的个数
	 * @author wangbeiying
	 * @param nodeId
	 * @return 
	 */
	public Integer getNumOfFolder(String nodeId);
	
	/**
	 * 返回我共享的文件夹中的文件个数
	 * @author wangbeiying
	 * @param nodeId
	 * @return 
	 */
	public Integer getNumOfMyShareFolder(String nodeId);
	
	/**
	 * 返回共享给我的文件夹中的文件个数
	 * @author wangbeiying
	 * @param nodeId
	 * @return 
	 */
	public Integer getNumOfShareToMeFolder(String nodeId);
	
	/**
	 * 返回满足条件的自己上传的结果集
	 * @author wangbeiying
	 * @param fileName
	 * @param uploadStratTime
	 * @param uploadEndTime
	 * @param uploadUser
	 * @return
	 */
	public List searchMyFilesByConditions(String fileName,String uploadStratTime,String uploadEndTime,String uploadUser);
	
	/**
	 * 返回满足条件的共享给自己的结果集
	 * @author wangbeiying
	 * @param fileName
	 * @param uploadStratTime
	 * @param uploadEndTime
	 * @param uploadUsers
	 * @param searchUser
	 * @return
	 */
	public List searchShareToMeFilesByConditions(String fileName,String uploadStratTime,String uploadEndTime,String uploadUsers, String searchUser);

}
