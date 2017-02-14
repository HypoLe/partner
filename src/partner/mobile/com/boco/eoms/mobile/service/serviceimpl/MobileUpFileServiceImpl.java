package com.boco.eoms.mobile.service.serviceimpl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.commons.accessories.exception.AccessoriesException;
import com.boco.eoms.commons.accessories.service.ITawCommonsAccessoriesManager;
import com.boco.eoms.mobile.service.IMobileUpFileService;

public class MobileUpFileServiceImpl implements IMobileUpFileService{
	public List saveFile(HttpServletRequest request, String appCode,String accesspriesFileNames) throws AccessoriesException {
		ITawCommonsAccessoriesManager mgrr = (ITawCommonsAccessoriesManager) ApplicationContextHolder.getInstance().getBean("ItawCommonsAccessoriesManager");
		return mgrr.saveFile(request, appCode, "");
	}
}
