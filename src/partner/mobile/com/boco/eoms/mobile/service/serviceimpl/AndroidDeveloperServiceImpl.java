package com.boco.eoms.mobile.service.serviceimpl;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.mobile.dao.IAndroidDeveloperDao;
import com.boco.eoms.mobile.model.AndroidDeveloper;
import com.boco.eoms.mobile.service.IAndroidDeveloperService;
public class AndroidDeveloperServiceImpl  extends CommonGenericServiceImpl<AndroidDeveloper>  implements IAndroidDeveloperService{

	private IAndroidDeveloperDao androidDeveloperDao;

	public IAndroidDeveloperDao getAndroidDeveloperDao() {
		return androidDeveloperDao;
	}

	public void setAndroidDeveloperDao(IAndroidDeveloperDao androidDeveloperDao) {
		this.androidDeveloperDao = androidDeveloperDao;
		this.setCommonGenericDao(androidDeveloperDao);
	}

	



	
}
