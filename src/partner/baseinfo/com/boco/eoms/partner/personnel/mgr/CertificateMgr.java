package com.boco.eoms.partner.personnel.mgr;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.upload.FormFile;

import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
import com.boco.eoms.partner.personnel.model.Certificate;
import com.boco.eoms.partner.resourceInfo.util.ImportResult;
	/**
 * <p>
 * Title:人员证书管理
 * </p>
 * <p>
 * Description:人员证书管理
 * </p>
 * <p>
 * Jul 10, 2012 10:44:01 AM
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */
public interface CertificateMgr extends CommonGenericService<Certificate> {
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
	public List getCertificateByUserid(String userid) throws Exception;
	/**
	 * 
	* @Title: createCertSystemNo 
	* @Description: TODO
	* @param 
	* @Time:Dec 24, 2012-2:23:55 PM
	* @Author:fengguangping
	* @return String    返回类型 
	* @throws
	 */
	public String createCertSystemNo(String id);
	/**
	 * 
	* @Title: getPartnerCertStatisticsList 
	* @Description: jdbc数据钻取时获得查询结果，
	* @param 
	* @Time:Dec 26, 2012-11:37:51 AM
	* @Author:fengguangping
	* @return Map    返回类型 
	* @throws
	 */
	public Map getPartnerCertStatisticsList(final Integer curPage, final Integer pageSize,final String whereStr);
	
	
}
