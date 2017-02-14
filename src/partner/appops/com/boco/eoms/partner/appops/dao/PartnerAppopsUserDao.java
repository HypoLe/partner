package com.boco.eoms.partner.appops.dao;

import java.util.List;
import java.util.Map;

import com.boco.eoms.deviceManagement.common.dao.CommonGenericDao;
import com.boco.eoms.partner.appops.model.IPnrPartnerAppOpsUser;

public interface PartnerAppopsUserDao  extends CommonGenericDao<IPnrPartnerAppOpsUser, String>{
	  public Map getPartnerUsers(final Integer curPage, final Integer pageSize,
				final String whereStr);
	  public List getPartnerUsers(final String where);
	/**
	 * 保存运维人员信息
	  * @author wangyue
	  * @title: saveAppopsUser
	  * @date Sep 27, 2014 1:59:15 PM
	  * @param appopsUser void
	 */
	public void saveAppopsUser(IPnrPartnerAppOpsUser appopsUser);
	
}
