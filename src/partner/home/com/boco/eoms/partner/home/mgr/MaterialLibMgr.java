package com.boco.eoms.partner.home.mgr;

import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
import com.boco.eoms.partner.home.model.MaterialLib;
import com.boco.eoms.partner.home.model.MatlibScopeDept;
/**
 * <p>
 * Title:资料库信息
 * </p>
 * <p>
 * Description:资料库信息
 * </p>
 * <p>
 * Sep 3, 2012 10:53:19 AM
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */
public interface MaterialLibMgr extends CommonGenericService<MaterialLib> {
	/**保存或更新
	 * @param materialLib 主对象
	 * @param matlibScopeDepts materialLib的关系matlibScopeDepts
	 * */
	public void save(MaterialLib materialLib,MatlibScopeDept... matlibScopeDepts);
}
