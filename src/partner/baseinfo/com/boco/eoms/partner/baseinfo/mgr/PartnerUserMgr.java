package com.boco.eoms.partner.baseinfo.mgr;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.upload.FormFile;

import com.boco.eoms.commons.system.user.model.TawSystemUser;
import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
import com.boco.eoms.partner.baseinfo.model.PartnerUser;
import com.boco.eoms.partner.resourceInfo.util.ImportResult;

/**
 * <p>
 * Title:��f��Ϣ
 * </p>
 * <p>
 * Description:��f��Ϣ
 * </p>
 * <p>
 * Tue Feb 10 17:33:14 CST 2009
 * </p>
 * 
 * @author liujinlong
 * @version 3.5
 * 
 */
 public interface PartnerUserMgr extends CommonGenericService<PartnerUser> {
 
	public List getPartnerUsers();
    
	public PartnerUser getPartnerUser(final String id);
    
	/**
     * 根据userId查询代维人员
     * @param userId 系统用户名（不是巡检员姓名）
     * @return
     */
    public PartnerUser getPartnerUserByUserId(String userId);
    
    /**
     * 根据userId和密码查询代维人员
     * @param userId 系统用户名（不是巡检员姓名）
     * @param password 密码
     * @return
     */
    public PartnerUser getPartnerUserByUserId(String userId,String password);
    
	public void savePartnerUser(PartnerUser partnerUser);
    
	public void removePartnerUser(final String id);
    
	public Map getPartnerUsers(final Integer curPage, final Integer pageSize,
			final String whereStr);
	
	//add by chenruoke
	 public Map getPartnerUsersForGis(final Integer curPage, final Integer pageSize,
				final String whereStr , final String toDay) ;
	
    //判断人力信息userId 是否唯一
    public Boolean isunique(final String userId);
    
	//批量删除某地市某厂商下所有的人力信息，参数是treeNodeId
	public void removePartnerUserByTreeNodeId(final String treeNodeId);
    /**
    * 按条件查询人员ȡ�б�
    * @param where  where条件
    * @return 符合条件的人员信息
    */	
	public List getPartnerUsers(final String where);	
	
	/**
	 * 得到公司的所有USER不包括用户自己
	 * 
	 * @param deptid
	 * @param userid
	 * @return
	 */
	public List getUserByCompidNoSelf(String deptid,String userid);
	
	/**
	 * 得到部门的所有USER
	 * 
	 * @param deptid
	 * @return
	 */
	public List getUserByCompids(String deptid);
	
	public void approve(String id,String approveStatus);
	
	public void approveAll(String id,String approveStatus);
	
	/**
	 * 根据部门ID查询该部门所有子孙部门的代维人员
	 */
	public List<PartnerUser> findAllPnrTestters(String deptid);
	
	/**
	 * 根据部门ID查询该部门所有子孙部门的代维人员总数
	 */
	public int findAllPnrTesttersCount(String deptid);
	
	/**
	 * 根据身份证号码获取代维人员
	 * @param personCardNo
	 * @return
	 */
	public PartnerUser getPartnerUserByPersonCardNo(String personCardNo);
	
	/**
	 * 验证身份证号是否唯一
	 * @param personCardNo
	 * @return
	 */
	public boolean isPersonCardNoUnique(String personCardNo);
	/**
	 * 删除人员的信息以及相关联的技能信息；
	* @Title: deletePartnerUserInfoAndSkillInfo 
	* @Description: TODO
	* @param 
	* @Time:Nov 27, 2012-5:36:47 PM
	* @Author:fengguangping
	* @return void    返回类型 
	* @throws
	 */
	public void deletePartnerUserInfoAndSkillInfo(String partnerUserid);	
	/**
	 * 
	* @Title: insertPartnerUserToTawSystemUser 
	* @Description:将partnerUser加入到tawSystemUser中；
	* @param 
	* @Time:Dec 1, 2012-11:49:32 AM
	* @Author:fengguangping
	* @return void    返回类型 
	* @throws
	 */
	public void insertPartnerUserToTawSystemUser(PartnerUser partnerUser,TawSystemUser tawSystemUser);
	
	/**
	 * 
	 * @param partnerUser
	 * @param tawSystemUser
	 * @param plaintext 密码明文
	 */
	public void insertPartnerUserToTawSystemUserNew(PartnerUser partnerUser,TawSystemUser tawSystemUser,String plaintext);
	/**
	 * 
	* @Title: getPartnerUserDeptIdAndAreaId 
	* @Description: 获取partnerUser的deptid和areaid；
	* @param 
	* @Time:Dec 1, 2012-11:50:20 AM
	* @Author:fengguangping
	* @return String[]    返回类型 
	* @throws
	 */
	public String[]  getPartnerUserDeptIdAndAreaId(String workerid);
	/**
	* @Title: importFromFile 
	* @Description: 管理员批量导入
	* @param 
	* @Time:Dec 1, 2012-4:22:02 PM
	* @Author:fengguangping
	* @return ImportResult    返回类型 
	* @throws
	 */
	public ImportResult importFromFile(FormFile formFile,HttpServletRequest request) throws Exception;
	/**
	 * 更新人员的人员的位置坐标
	* @Title: updatePnrUserLocation 
	* @Description: TODO
	* @param 
	* @Time:Dec 5, 2012-2:43:10 PM
	* @Author:fengguangping
	* @return boolean    返回类型 
	* @throws
	 */
	public boolean updatePnrUserLocation(String userId,String latitude,String longtitude) throws Exception;
}