package com.boco.eoms.partner.baseinfo.cache;

import com.boco.eoms.commons.cache.exception.CacheKeyCallbackException;
import com.boco.eoms.commons.cache.support.ICacheKeyCallback;
import com.boco.eoms.commons.cache.util.CacheUtil;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;

/**
 * 
 * <p>
 * Title:部门返回key值的callback实现类
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Date:2008-1-22 9:55:18
 * </p>
 * 
 * @author 曲静波
 * @version 1.0
 * 
 */
public class PnrDeptCacheKeyCallback implements ICacheKeyCallback {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.boco.eoms.commons.cache.support.ICacheKeyCallback#execute(java.lang.String,
	 *      java.lang.String, java.lang.Object[])
	 */
	public String[] execute(String clsName, String methodName,
			Object[] arguments) throws CacheKeyCallbackException {
		String targetName = clsName + "." + methodName;
		
		// 执行的saveTawSystemDept方法，添加部门方法
		if ("com.boco.eoms.partner.baseinfo.mgr.impl.PartnerDeptMgrImpl.getCompanyByProvience"
				.equals(targetName)) {
			PartnerDept dept = (PartnerDept) arguments[0];

			// 拼写参数
//			Object[] targetid2name = new Object[] { dept.getId(),
//					"ItawSystemDeptManagerFlush" };
			Object[] parm=new Object[]{"areaId","firstdeptsimble"};
				return new String[] {
						CacheUtil
								.getCacheKey(
										"com.boco.eoms.partner.baseinfo.mgr.impl.PartnerDeptMgrImpl.getCompanyByProvience",
										"getCompanyByProvience", parm) };
			

			// 返回key值

		} 

		return null;
	}
}
