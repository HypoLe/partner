package com.boco.eoms.partner.personnel.mgr;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.upload.FormFile;

import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
import com.boco.eoms.partner.personnel.model.DWInfo;
import com.boco.eoms.partner.resourceInfo.util.ImportResult;
	/**
 * <p>
 * Title:人员资质信息管理
 * </p>
 * <p>
 * Description:人员资质信息管理
 * </p>
 * <p>
 * Jul 16, 2012 02:44:01 PM
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */
public interface DWInfoMgr extends CommonGenericService<DWInfo> {
	/**
	 * 获取所有资质
	 * @param userid
	 */
	public List<DWInfo>  findByHql(String hql);
	/**
	 * 逻辑删除某一人员的所有信息
	 * @param userid
	 */
	public void  removeAllByUserId(String userid);
	/**
	 * 逻辑或者物理删除某一人员的所有信息
	 * @param userid
	 */
	public void  removeAllByUserId(String userid,boolean logic);
	/**
	 * 
	* @Title: createdwInfoSystemNo 
	* @Description: TODO
	* @param 
	* @Time:Dec 24, 2012-2:24:34 PM
	* @Author:fengguangping
	* @return String    返回类型 
	* @throws
	 */
	public String createdwInfoSystemNo(String id);
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
