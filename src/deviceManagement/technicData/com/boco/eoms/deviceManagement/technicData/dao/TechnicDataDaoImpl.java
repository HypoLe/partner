package com.boco.eoms.deviceManagement.technicData.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.ObjectRetrievalFailureException;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.deviceManagement.technicData.model.TechnicDataFile;
import com.boco.eoms.deviceManagement.technicData.model.TechnicDataFolderMapping;
import com.boco.eoms.deviceManagement.technicData.model.TechnicDataFolderShare;

public class TechnicDataDaoImpl extends BaseDaoHibernate implements
		TechnicDataDao {

	public TechnicDataFolderShare getTechnicDataFolderShare(
			String id) {
		TechnicDataFolderShare technicDataFolderShare = (TechnicDataFolderShare) getHibernateTemplate()
				.get(TechnicDataFolderShare.class, id);
		if (technicDataFolderShare == null) {
			throw new ObjectRetrievalFailureException(
					TechnicDataFolderShare.class, id);
		}
		return technicDataFolderShare;
	}

	public void saveTechnicDataFolderShare(
			TechnicDataFolderShare technicDataFolderShare) {
		if (technicDataFolderShare.getId() == null
				|| "".equals(technicDataFolderShare.getId())) {
			getHibernateTemplate().save(technicDataFolderShare);
		} else {
			getHibernateTemplate().saveOrUpdate(technicDataFolderShare);
		}
	}

	public void removeTechnicDataFolderShare(
			TechnicDataFolderShare technicDataFolderShare) {
		if (technicDataFolderShare.getId() != null
				&& !"".equals(technicDataFolderShare.getId())) {
			getHibernateTemplate().delete(technicDataFolderShare);
		}
	}

	public List listMyShare(String fromUserId) {
		String hql = " from TechnicDataFolderShare foldershare where foldershare.fromUserId='"
				+ fromUserId + "'";
		return getHibernateTemplate().find(hql);
	}

	public List listShareToMeUsers(String toUserId) {
		String hql = "select distinct(foldershare.fromUserId),foldershare.fromUserName from TechnicDataFolderShare foldershare where (foldershare.toUserId like('%"
				+ toUserId
				+ "%') or foldershare.toUserId='all_users') and foldershare.fromUserId!='"
				+ toUserId + "'";
		return getHibernateTemplate().find(hql);
	}

	public List listShareToMe(String fromUserId, String toUserId) {
		String hql = " from TechnicDataFolderShare foldershare where (foldershare.toUserId like('%"
				+ toUserId
				+ "%') and fromUserId='"
				+ fromUserId
				+ "') or (foldershare.toUserId='all_users' and fromUserId='"
				+ fromUserId + "')";
		return getHibernateTemplate().find(hql);
	}

	public void removeFolderShareInfo(String folderPath, String fromUserId) {
		String hql = " from TechnicDataFolderShare foldershare where foldershare.shareFolderPath='"
				+ folderPath
				+ "' and foldershare.fromUserId='"
				+ fromUserId
				+ "'";
		List list = new ArrayList();
		list = (ArrayList) getHibernateTemplate().find(hql);
		if (list != null) {
			if (list.size() > 0) {
				TechnicDataFolderShare shareInfo = new TechnicDataFolderShare();
				for (int i = 0; i < list.size(); i++) {
					shareInfo = (TechnicDataFolderShare) list.get(i);
					getHibernateTemplate().delete(shareInfo);
				}
			}
		}
	}

	public TechnicDataFolderShare getTechnicDataFolderShare(
			String fromUserId, String folderPath) {
		String hql = " from TechnicDataFolderShare foldershare where foldershare.shareFolderPath='"
				+ folderPath
				+ "' and foldershare.fromUserId='"
				+ fromUserId
				+ "'";
		TechnicDataFolderShare folderShareInfo = new TechnicDataFolderShare();
		List list = new ArrayList();
		list = (ArrayList) getHibernateTemplate().find(hql);
		if (list != null) {
			if (list.size() > 0) {
				folderShareInfo = (TechnicDataFolderShare) list.get(0);
			}
		}
		return folderShareInfo;
	}

	public void delFolderMapping(String id) {
		TechnicDataFolderMapping mapping = getTechnicDataFolderMapping(id);
		getHibernateTemplate().delete(mapping);
	}

	public String getFolderNameFromMapping(String id) {
		String folderPath = "";
		TechnicDataFolderMapping mapping = getTechnicDataFolderMapping(id);
		if (mapping.getFolderName() != null
				&& !"".equals(mapping.getFolderName())) {
			folderPath = mapping.getFolderName();
		}
		return folderPath;
	}

	public String getMappingId(String userId, String folderName, String parentId) {
		String id = "";
		String hql = " from TechnicDataFolderMapping mapping where mapping.userId='"
				+ userId
				+ "' and mapping.folderName='"
				+ folderName
				+ "' and mapping.parentId='" + parentId + "'";
		TechnicDataFolderMapping mapping = new TechnicDataFolderMapping();
		List list = new ArrayList();
		list = (ArrayList) getHibernateTemplate().find(hql);
		if (list != null) {
			if (list.size() > 0) {
				mapping = (TechnicDataFolderMapping) list.get(0);
				id = mapping.getId();
			}
		}
		return id;
	}

	public String saveFolderMapping(TechnicDataFolderMapping mapping) {
		if (mapping.getId() == null || "".equals(mapping.getId())) {
			getHibernateTemplate().save(mapping);
		} else {
			getHibernateTemplate().saveOrUpdate(mapping);
		}
		return mapping.getId();
	}

	public TechnicDataFolderMapping getTechnicDataFolderMapping(
			String id) {
		TechnicDataFolderMapping technicDataFolderMapping = (TechnicDataFolderMapping) getHibernateTemplate()
				.get(TechnicDataFolderMapping.class, id);
		if (technicDataFolderMapping == null) {
			throw new ObjectRetrievalFailureException(
					TechnicDataFolderMapping.class, id);
		}
		return technicDataFolderMapping;
	}

	public List listsubFolders(String parentId) {
		String hql = " from TechnicDataFolderMapping mapping where mapping.parentId='"
				+ parentId + "'";
		List list = new ArrayList();
		list = (ArrayList) getHibernateTemplate().find(hql);
		return list;
	}

	public void delTechnicDataFile(
			TechnicDataFile technicDataFile) {
		if (technicDataFile.getId() != null
				&& !"".equals(technicDataFile.getId())) {
			getHibernateTemplate().delete(technicDataFile);
		}
	}

	public TechnicDataFile getTechnicDataFile(String userId,
			String mappingName) {
		String hql = " from TechnicDataFile file where file.userId='"
				+ userId + "' and file.mappingName='" + mappingName + "'";
		TechnicDataFile technicDataFile = new TechnicDataFile();
		List list = new ArrayList();
		list = (ArrayList) getHibernateTemplate().find(hql);
		if (list != null) {
			if (list.size() > 0) {
				technicDataFile = (TechnicDataFile) list.get(0);
			}
		}
		return technicDataFile;
	}

	public List listTechnicDataFile(String userId,
			String folderMappingId) {
		String hql = " from TechnicDataFile file where file.userId='"
				+ userId + "' and file.folderMappingId='" + folderMappingId
				+ "'";
		List list = new ArrayList();
		list = (ArrayList) getHibernateTemplate().find(hql);
		return list;
	}

	public void saveTechnicDataFolderFile(
			TechnicDataFile technicDataFile) {
		if (technicDataFile.getId() == null
				|| "".equals(technicDataFile.getId())) {
			getHibernateTemplate().save(technicDataFile);
		} else {
			getHibernateTemplate().saveOrUpdate(technicDataFile);
		}
	}

	public Integer getNumOfFolder(String nodeId) {
		List list = new ArrayList();
		list = (ArrayList) getHibernateTemplate().find("select count(id) from TechnicDataFile file where folderMappingId=?",nodeId);
		return (Integer)list.get(0);
	}

	public Integer getNumOfMyShareFolder(String nodeId) {
		List list = new ArrayList();
		list = (ArrayList) getHibernateTemplate().find("select count(file.id) from TechnicDataFolderShare sharefile,TechnicDataFile file where sharefile.shareFolderPath=file.folderMappingId and sharefile.shareFolderPath=? ",nodeId);
		return (Integer)list.get(0);
	}

	public Integer getNumOfShareToMeFolder(String nodeId) {
		List list = new ArrayList();
		list = (ArrayList) getHibernateTemplate().find("select count(file.id) from TechnicDataFolderShare sharefile,TechnicDataFile file where sharefile.shareFolderPath=file.folderMappingId and sharefile.shareFolderPath=? ",nodeId);
		return (Integer)list.get(0);
	}

	public List searchMyFilesByConditions(String fileName, String uploadStratTime, String uploadEndTime, String uploadUser) {
		StringBuffer sb = new StringBuffer("from TechnicDataFile file where userId = ? ");
		List objectList = new ArrayList();
		objectList.add(uploadUser);
		if (!fileName.equals("")) {
			sb.append("and fileName like ? ");
			objectList.add("%"+fileName+"%");
		}
		if (!uploadStratTime.equals("")) {
			sb.append("and uploadTime > ? ");
			objectList.add(uploadStratTime);
		}
		if (!uploadEndTime.equals("")) {
			sb.append("and uploadTime < ? ");
			objectList.add(uploadEndTime);
		}
		//Object[] obj = {"%"+fileName+"%",uploadStratTime,uploadEndTime,uploadUser};
		List list = new ArrayList();
		list = (ArrayList) getHibernateTemplate().find(sb.toString(),objectList.toArray());
		return list;
	}

	public List searchShareToMeFilesByConditions(String fileName, String uploadStratTime, String uploadEndTime, String uploadUsers, String searchUser) {
		StringBuffer sb = new StringBuffer("select file from TechnicDataFile file,TechnicDataFolderShare sharefile where sharefile.shareFolderPath=file.folderMappingId and (sharefile.toUserId = ? or (sharefile.toUserId = 'all_users' and sharefile.fromUserId != '"+searchUser+"'))");
		List objectList = new ArrayList();
		objectList.add(searchUser);
		if (!fileName.equals("")) {
			sb.append("and file.fileName like ? ");
			objectList.add("%"+fileName+"%");
		}
		if (!uploadStratTime.equals("")) {
			sb.append("and file.uploadTime > ? ");
			objectList.add(uploadStratTime);
		}
		if (!uploadEndTime.equals("")) {
			sb.append("and file.uploadTime < ? ");
			objectList.add(uploadEndTime);
		}
		if (!uploadUsers.equals("")) {
			String[] user = uploadUsers.split(",");
			StringBuffer sbUsers = new StringBuffer();
			for (int i=0;i<user.length;i++) {
				sbUsers.append("'"+user[i]+"',");
			}
			sb.append("and sharefile.fromUserId in ("+sbUsers.substring(0, sbUsers.length()-1)+") ");
			//objectList.add(uploadUsers);
		}
		//Object[] obj = {"%"+fileName+"%",uploadStratTime,uploadEndTime,uploadUser};
		List list = new ArrayList();
		list = (ArrayList) getHibernateTemplate().find(sb.toString(),objectList.toArray());
		return list;
	}

}
