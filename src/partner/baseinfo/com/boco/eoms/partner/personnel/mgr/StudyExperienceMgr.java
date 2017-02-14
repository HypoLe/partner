package com.boco.eoms.partner.personnel.mgr;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.upload.FormFile;

import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
import com.boco.eoms.partner.personnel.model.StudyExperience;
import com.boco.eoms.partner.resourceInfo.util.ImportResult;
	/**
 * <p>
 * Title:人员学习经历管理
 * </p>
 * <p>
 * Description:人员学习经历管理
 * </p>
 * <p>
 * Jul 16, 2012 04:44:01 PM
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */
public interface StudyExperienceMgr extends CommonGenericService<StudyExperience> {
	/**
	 * 逻辑删除某一人员的所有信息
	 * @param userid
	 */
	public void removeAllByUserId(String userid);
	/**
	 * 逻辑删除某一人员的所有信息
	 * @param userid
	 */
	public List<StudyExperience> findByHql(String hql);
	/**
	 * 
	* @Title: createStuExSystemNo 
	* @Description: 系统编码
	* @param 
	* @Time:Dec 24, 2012-1:45:00 PM
	* @Author:fengguangping
	* @return String    返回类型 
	* @throws
	 */
	public String createStuExSystemNo(String id);
	/**
	* @Title: importFromFile 
	* @Description: 移动管理员批量导入
	* @param 
	* @Time:Nov 30, 2012-9:18:14 AM
	* @Author:fengguangping
	* @return ImportResult    返回类型 
	* @throws
	 */
	public ImportResult importFromFile(FormFile formFile,HttpServletRequest request) throws Exception;
}
