package com.boco.eoms.partner.personnel.mgr;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.upload.FormFile;

import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
import com.boco.eoms.partner.personnel.model.Reward;
import com.boco.eoms.partner.resourceInfo.util.ImportResult;
	/**
 * <p>
 * Title:人员奖励管理
 * </p>
 * <p>
 * Description:人员奖励管理
 * </p>
 * <p>
 * Jul 12, 2012 02:44:01 PM
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */
public interface RewardMgr extends CommonGenericService<Reward> {
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
	 * 
	* @Title: createRewardSystemNo 
	* @Description: TODO
	* @param 
	* @Time:Dec 24, 2012-2:25:44 PM
	* @Author:fengguangping
	* @return String    返回类型 
	* @throws
	 */
	public String createRewardSystemNo(String id);
}
