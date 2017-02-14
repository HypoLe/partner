package com.boco.eoms.partner.personnel.mgr;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.upload.FormFile;

import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
import com.boco.eoms.partner.personnel.model.PXExperience;
import com.boco.eoms.partner.resourceInfo.util.ImportResult;
	/**
 * <p>
 * Title:人员培训经历管理
 * </p>
 * <p>
 * Description:人员培训经历管理
 * </p>
 * <p>
 * Jul 16, 2012 04:44:01 PM
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */
public interface PXExperienceMgr extends CommonGenericService<PXExperience> {
	/**
	 * 逻辑删除某一人员的所有信息
	 * @param userid
	 */
	public void removeAllByUserId(String userid);
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
	/**
	* @Title: getCertificateByUserid 
	* @Description: 根据userid查找证书信息
	* @param 
	* @Time:Dec 15, 2012-3:10:34 PM
	* @Author:fengguangping
	* @return List    返回类型 
	* @throws
	 */
	public List getPXExperienceByUserid(String userid) throws Exception;
	/**
	 * 
	* @Title: createPxExSystemNo 
	* @Description: TODO
	* @param 
	* @Time:Dec 24, 2012-2:25:10 PM
	* @Author:fengguangping
	* @return String    返回类型 
	* @throws
	 */
	public String createPxExSystemNo(String id);
}
