package com.boco.eoms.mobile.service.serviceimpl;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.mobile.dao.IAndroidAppInfoDao;
import com.boco.eoms.mobile.model.AndroidAppInfo;
import com.boco.eoms.mobile.service.IAndroidService;
public class AndroidServiceImpl  extends CommonGenericServiceImpl<AndroidAppInfo>  implements IAndroidService{

	private IAndroidAppInfoDao appInfoDao;

	public IAndroidAppInfoDao getAppInfoDao() {
		return appInfoDao;
	}

	public void setAppInfoDao(IAndroidAppInfoDao appInfoDao) {
		this.appInfoDao = appInfoDao;
		this.setCommonGenericDao(appInfoDao);
	}



	
}
