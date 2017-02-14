package com.boco.eoms.deviceManagement.technicData.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.boco.eoms.deviceManagement.technicData.dao.TechnicDataDao;
import com.boco.eoms.deviceManagement.technicData.model.TechnicDataFile;
import com.boco.eoms.deviceManagement.technicData.model.TechnicDataFolderMapping;
import com.boco.eoms.deviceManagement.technicData.model.TechnicDataFolderShare;
import com.boco.eoms.deviceManagement.technicData.util.FilePathProcessor;
import com.boco.eoms.deviceManagement.technicData.util.FileAttriubuteLocator;

public class TechnicDataServiceImpl implements TechnicDataService {

	private TechnicDataDao technicDataDao;

	public TechnicDataDao getTechnicDataDao() {
		return technicDataDao;
	}

	public void setTechnicDataDao(
			TechnicDataDao technicDataDao) {
		this.technicDataDao = technicDataDao;
	}

	public float getFreeSpace(String userId, float maxSize) {
		return 0;
	}

	public TechnicDataFolderShare getTechnicDataFolderShare(
			String id) {
		return technicDataDao.getTechnicDataFolderShare(id);
	}

	public List listMyShare(String fromUserId) {
		return technicDataDao.listMyShare(fromUserId);
	}

	public List listShareToMeUsers(String toUserId) {
		return technicDataDao.listShareToMeUsers(toUserId);
	}

	public List listShareToMe(String fromUserId, String toUserId) {
		return technicDataDao.listShareToMe(fromUserId, toUserId);
	}

	public void removeTechnicDataFolderShare(
			TechnicDataFolderShare technicDataFolderShare) {
		technicDataDao
				.removeTechnicDataFolderShare(technicDataFolderShare);
	}

	public void saveTechnicDataFolderShare(
			TechnicDataFolderShare technicDataFolderShare) {
		technicDataDao
				.saveTechnicDataFolderShare(technicDataFolderShare);
	}

	public TechnicDataFolderShare getTechnicDataFolderShare(
			String fromUserId, String folderPath) {
		return technicDataDao.getTechnicDataFolderShare(
				fromUserId, folderPath);
	}

	public boolean removeFolder(String rootPath, String folderMappingId,
			String userId) {
		TechnicDataFolderShare shareInfo = getTechnicDataFolderShare(
				userId, folderMappingId);
		removeTechnicDataFolderShare(shareInfo);
		delFolderMapping(folderMappingId);
		File folder = new File(rootPath + File.separator + userId
				+ File.separator + folderMappingId);
		try {
			folder.delete();

		} catch (Exception e) {
			// return false;
		}
		List subFolders = listsubFolders(folderMappingId);
		for (int i = 0; i < subFolders.size(); i++) {
			TechnicDataFolderMapping folderMapping = (TechnicDataFolderMapping) subFolders
					.get(i);
			removeFolder(rootPath, folderMapping.getId(), userId);
		}
		return true;
	}

	public boolean renameFolder(File currentFolder, File newFolder,
			String userId) {
		// 相对路径
		String currentPath = FilePathProcessor.getRelativePath(userId,
				currentFolder.getPath());
		String newPath = FilePathProcessor.getRelativePath(userId, newFolder
				.getPath());
		try {
			currentFolder.renameTo(newFolder);
		} catch (Exception e) {
			return false;
		}
		// 包括自己在内的子文件夹共享信息列表
		List subFolders = listMyShareInfoIncludeSub(userId, currentPath);
		for (int i = 0; i < subFolders.size(); i++) {
			TechnicDataFolderShare shareInfo = (TechnicDataFolderShare) subFolders
					.get(i);
			shareInfo.setShareFolderPath(shareInfo.getShareFolderPath()
					.replaceFirst(currentPath, newPath));
			saveTechnicDataFolderShare(shareInfo);
		}
		return true;
	}

	private List listMyShareInfoIncludeSub(String userId, String folderPath) {
		List myShareList = technicDataDao.listMyShare(userId);
		List myShareSubList = new ArrayList();
		for (int i = 0; i < myShareList.size(); i++) {
			TechnicDataFolderShare shareInfo = (TechnicDataFolderShare) myShareList
					.get(i);
			if (shareInfo.getShareFolderPath().startsWith(folderPath)) {
				myShareSubList.add(shareInfo);
			}
		}
		return myShareSubList;
	}

	public void delFolderMapping(String id) {
		technicDataDao.delFolderMapping(id);
	}

	public String getFolderNameFromMapping(String id) {
		return technicDataDao.getFolderNameFromMapping(id);
	}

	public String getMappingId(String userId, String folderName, String parentId) {
		return technicDataDao
				.getMappingId(userId, folderName, parentId);
	}

	public String saveFolderMapping(TechnicDataFolderMapping mapping) {
		return technicDataDao.saveFolderMapping(mapping);
	}

	public List listsubFolders(String parentId) {
		return technicDataDao.listsubFolders(parentId);
	}

	public String initUserRootFolder(String userId, String userRootPath) {
		TechnicDataFolderMapping folderMapping = new TechnicDataFolderMapping();
		File file = new File(userRootPath);
		String mappingId = getMappingId(userId, userId, "0");
		if (!file.exists()) {
			file.mkdirs();
		}
		if (mappingId == null || "".equals(mappingId)) {
			folderMapping.setUserId(userId);
			folderMapping.setFolderName(userId);
			folderMapping.setParentId("0");
			mappingId = saveFolderMapping(folderMapping);
		}
		return mappingId;
	}

	public TechnicDataFolderMapping getTechnicDataFolderMapping(
			String id) {
		return technicDataDao.getTechnicDataFolderMapping(id);
	}

	public void removeFolderShareInfo(String folderPath, String fromUserId) {
		technicDataDao.removeFolderShareInfo(folderPath, fromUserId);
	}

	public void delTechnicDataFile(String userId,
			String folderMappingId, String mappingName) {
		TechnicDataFile technicDataFile = searchTechnicDataFile(
				userId, mappingName);
		technicDataDao
				.delTechnicDataFile(technicDataFile);
		String rootPath = FileAttriubuteLocator.getNetDiskAttributes()
				.getNetDiskRootPath();
		String filePath = rootPath + File.separator + userId + File.separator
				+ folderMappingId + File.separator + mappingName;
		File file = new File(filePath);
		try {
			FileUtils.forceDelete(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public TechnicDataFile searchTechnicDataFile(String userId,
			String mappingName) {
		TechnicDataFile technicDataFile = technicDataDao
				.getTechnicDataFile(userId, mappingName);
		if (technicDataFile.getId() != null
				&& !"".equals(technicDataFile.getId())) {
			String rootPath = FileAttriubuteLocator.getNetDiskAttributes()
					.getNetDiskRootPath();
			String filePath = rootPath + File.separator
					+ technicDataFile.getUserId() + File.separator
					+ technicDataFile.getFolderMappingId()
					+ File.separator + technicDataFile.getMappingName();
			File file = new File(filePath);
			if (!file.exists()) {
				technicDataDao
						.delTechnicDataFile(technicDataFile);
				return new TechnicDataFile();
			}
		}
		return technicDataFile;
	}

	public List listTechnicDataFile(String userId,
			String folderMappingId) {
		List list = technicDataDao.listTechnicDataFile(userId,
				folderMappingId);
		String rootPath = FileAttriubuteLocator.getNetDiskAttributes()
				.getNetDiskRootPath();
		for (int i = 0; i < list.size(); i++) {
			TechnicDataFile netDiskFile = (TechnicDataFile) list
					.get(i);
			File file = new File(rootPath + File.separator
					+ netDiskFile.getUserId() + File.separator
					+ netDiskFile.getFolderMappingId() + File.separator
					+ netDiskFile.getMappingName());
			if (!file.exists()) {
				list.remove(i);
				technicDataDao.delTechnicDataFile(netDiskFile);
			}
		}
		return list;
	}

	public void saveTechnicDataFile(
			TechnicDataFile technicDataFile) {
		technicDataDao
				.saveTechnicDataFolderFile(technicDataFile);
	}

	public Integer getNumOfFolder(String nodeId) {
		return technicDataDao.getNumOfFolder(nodeId);
	}

	public Integer getNumOfMyShareFolder(String nodeId) {
		return technicDataDao.getNumOfMyShareFolder(nodeId);
	}

	public Integer getNumOfShareToMeFolder(String nodeId) {
		return technicDataDao.getNumOfShareToMeFolder(nodeId);
	}

	public List searchByConditions(String userid, String fileName, String uploadStratTime, String uploadEndTime, String uploadUsers) {
		List returnList = new ArrayList();
		//只有当用户没有选择上传用户或者上传用户包含用户本人时，才去查询自己的文件。
		if (uploadUsers.equals("")) {
			List myFilesList = technicDataDao.searchMyFilesByConditions(fileName, uploadStratTime, uploadEndTime, userid);
			returnList.addAll(myFilesList);
		} else {
			String[] user = uploadUsers.split(",");
			StringBuffer sbUsers = new StringBuffer();
			for (int i=0;i<user.length;i++) {
				if(user[i].equals(userid)) {
					List myFilesList = technicDataDao.searchMyFilesByConditions(fileName, uploadStratTime, uploadEndTime, userid);
					returnList.addAll(myFilesList);
				}
			}
		}
		//查询共享给自己的符合条件的文件
		List shareToMeFilesList = technicDataDao.searchShareToMeFilesByConditions(fileName, uploadStratTime, uploadEndTime, uploadUsers, userid);
		returnList.addAll(shareToMeFilesList);
		return returnList;
	}
	
}
