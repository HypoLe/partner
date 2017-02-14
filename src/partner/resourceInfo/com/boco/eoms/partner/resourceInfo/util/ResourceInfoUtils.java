package com.boco.eoms.partner.resourceInfo.util;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;

/**  
 * @Title: ResourceInfoUtils.java
 * @Package com.boco.eoms.partner.resourceInfo.util
 * @Description: 
 * @author fengguangping fengguangping@boco.com.cn
 * @date Mar 25, 2013  5:02:25 PM  
 */
public class ResourceInfoUtils {
	/**
	 * 
	* @Title: deptUUidToDeptId 
	* @Description: 先判断id是deptid还是UUid如果是uuid将对应的dept找到并返回该deptid
	* @param id
	* @return String     
	* @throws
	 */
	public static String deptUUidToDeptId(String id){
		String deptId="";
		id=StaticMethod.null2String(id);
		if (!"".equals(id)) {
			if (id.length()==32) {//32位说明是uuid
				PartnerDeptMgr deptMgr=(PartnerDeptMgr)ApplicationContextHolder.getInstance().getBean("partnerDeptMgr");
				PartnerDept dept=deptMgr.getPartnerDept(id);
				deptId=StaticMethod.null2String(dept.getDeptMagId());
			} else {
				deptId=id;
			}
		}
		return deptId;
	}
}
