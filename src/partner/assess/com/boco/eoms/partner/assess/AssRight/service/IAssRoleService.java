/**
 * 
 */
package com.boco.eoms.partner.assess.AssRight.service;

import java.util.List;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Date:Dec 10, 2010 1:24:01 AM
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
 * 
 */
public interface IAssRoleService {

	/**
	 * 根据用户名和操作类型得到对应考核模块子角色,地域信息(作为该用户的操作地域信息)
	 * 
	 * @param userId
	 *            用户名id
	 * @param operateType
	 *            操作类型
	 *            
	 * @return List<TawSystemSubRole>
	 */
	public List getRoleAreaByUserId(String userId,String operateType);

	/**
	 * 根据角色集合得到地域集合
	 * 
	 * @param List subRoleList
	 *             角色集合
	 *            
	 * @return List （地域集合）
	 */
	public List getAreasBySubRoleList(List subRoleList);
	
	/**
	 * 根据用户名和操作类型得到地域集合
	 * 
	 * @param userId
	 *            用户名id
	 * @param operateType
	 *            操作类型
	 *            
	 * @return List （地域id）
	 */
	public List getAreasByUserId(String userId,String operateType);
}
