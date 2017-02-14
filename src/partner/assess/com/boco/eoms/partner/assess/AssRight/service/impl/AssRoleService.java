/**
 * 
 */
package com.boco.eoms.partner.assess.AssRight.service.impl;

import java.util.ArrayList;
import java.util.List;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.commons.system.area.model.TawSystemArea;
import com.boco.eoms.commons.system.area.service.ITawSystemAreaManager;
import com.boco.eoms.commons.system.role.model.TawSystemSubRole;
import com.boco.eoms.commons.system.role.service.ITawSystemSubRoleManager;
import com.boco.eoms.partner.assess.AssRight.service.IAssRoleService;
import com.boco.eoms.partner.assess.AssRight.util.AssRoleIdList;
import com.boco.eoms.partner.assess.util.AssConstants;


/**
 * <p>
 * Title:评估操作角色处理类
 * </p>
 * <p>
 * Description:评估操作角色处理类
 * </p>
 * <p>
 * Date:Nov 30, 2010 4:27:06 PM
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
 * 
 */
public class AssRoleService  implements IAssRoleService{

	protected String beenNameForRoleIdList = "";
	
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
	public List getRoleAreaByUserId(String userId,String operateType) {
		String roleId = "";
		List subRoleList = new ArrayList();
		//当用户是admin时不做地域筛选,地域默认为1,子角色id为""
		if("admin".equals(userId)){
			
		}else{
			ITawSystemSubRoleManager subRoleMgr = (ITawSystemSubRoleManager)ApplicationContextHolder
				.getInstance().getBean("ItawSystemSubRoleManager");
			AssRoleIdList roleIdList = (AssRoleIdList)ApplicationContextHolder
			.getInstance().getBean(beenNameForRoleIdList);
			if(operateType.equals(AssConstants.OPERATE_TREE_CONFIG)){
				roleId = roleIdList.getTreeConfigRoleId();
			}else if(operateType.equals(AssConstants.OPERATE_TEMPLATE_AUDIT)){
				roleId = roleIdList.getTemplateAuditRoleId();
			}else if(operateType.equals(AssConstants.OPERATE_REPORT_AUDIT)){
				roleId = roleIdList.getReportAuditRoleId();
			}else if(operateType.equals(AssConstants.OPERATE_REPORT_EXECUTE)){
				roleId = roleIdList.getReportExecuteRoleId();
			}else if(operateType.equals(AssConstants.OPERATE_REPORT_SHOW)){
				roleId = roleIdList.getReportShowRoleId();
			}
			subRoleList = subRoleMgr.getSubRoles(userId,roleId);
		}
		return subRoleList;
	}

	/**
	 * 根据角色集合得到地域集合
	 * 
	 * @param List subRoleList
	 *             角色集合
	 *            
	 * @return List （地域集合）
	 */
	public List getAreasBySubRoleList(List subRoleList) {
		List areaList = new ArrayList();
		String areaId = "";
		TawSystemSubRole subRole = null; 
		for(int i=0;i<subRoleList.size();i++){
			subRole = (TawSystemSubRole)subRoleList.get(i);
			areaId = subRole.getDeptId();
			areaList.add(areaId);
		}
		return areaList;
	}
	
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
	public List getAreasByUserId(String userId,String operateType) {

		List areaList = new ArrayList();
		//当用户是admin时不做地域筛选,地域默认为所有地市
		if("admin".equals(userId)){
			ITawSystemAreaManager AreaMgr = (ITawSystemAreaManager) ApplicationContextHolder
			.getInstance().getBean("ItawSystemAreaManager");
			AssRoleIdList roleIdList = (AssRoleIdList)ApplicationContextHolder
			.getInstance().getBean(beenNameForRoleIdList);
			String rootAreaId = roleIdList.getRootAreaId();
			List areas = AreaMgr.getSonAreaByAreaId(rootAreaId);
			String areaId = "";
			for(int i=0;i<areas.size();i++){
				areaId = ((TawSystemArea)areas.get(i)).getAreaid();
				areaList.add(areaId);
			}
		}else{
			List subRoleList = getRoleAreaByUserId(userId,operateType);
			areaList = getAreasBySubRoleList(subRoleList);
		}
		return areaList;
	}
}
