package com.boco.eoms.partner.personnel.mgr;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.upload.FormFile;

import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
import com.boco.eoms.partner.personnel.model.WorkExperience;
import com.boco.eoms.partner.resourceInfo.util.ImportResult;
/**
 * 
* @ClassName WorkExperienceMgr
 * @Description TODO
 * @author  fengguangping
 * @date Nov 29, 2012
 */
public interface WorkExperienceMgr extends CommonGenericService<WorkExperience> {
	/**
	 * 逻辑删除某一人员的所有信息
	 * @param userid
	 */
	public void removeAllByUserId(String userid);
	/**
	 * 
	* @Title: createWorExSystemNo 
	* @Description: TODO
	* @param 
	* @Time:Dec 24, 2012-2:26:47 PM
	* @Author:fengguangping
	* @return String    返回类型 
	* @throws
	 */
	public String createWorExSystemNo(String id);
	/**
	* @Title: importFromFile 
	* @Description: 移动管理员批量导入
	* @param 
	* @Time:Nov 30, 2012-9:18:14 AM
	* @Author:fengguangping
	* @return ImportResult    返回类型 
	* @throws
	 */
	public ImportResult importFromFile(FormFile formFile,HttpServletRequest request) throws Exception;}
