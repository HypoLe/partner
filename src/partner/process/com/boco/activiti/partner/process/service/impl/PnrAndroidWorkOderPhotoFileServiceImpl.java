package com.boco.activiti.partner.process.service.impl;

import com.boco.activiti.partner.process.dao.IPnrAndroidWorkOderPhotoFileDao;
import com.boco.activiti.partner.process.model.PnrAndroidWorkOderPhotoFile;
import com.boco.activiti.partner.process.service.IPnrAndroidWorkOderPhotoFileService;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;

/**
 
 */
public class PnrAndroidWorkOderPhotoFileServiceImpl extends CommonGenericServiceImpl<PnrAndroidWorkOderPhotoFile> implements IPnrAndroidWorkOderPhotoFileService {

    private IPnrAndroidWorkOderPhotoFileDao pnrAndroidWorkOderPhotoFileDao;


	@Override
	public void savePhoto(
			PnrAndroidWorkOderPhotoFile pnrAndroidWorkOderPhotoFile) {
		pnrAndroidWorkOderPhotoFileDao.savePhoto(pnrAndroidWorkOderPhotoFile);
	}
  
	public IPnrAndroidWorkOderPhotoFileDao getPnrAndroidWorkOderPhotoFileDao() {
		return pnrAndroidWorkOderPhotoFileDao;
	}

	public void setPnrAndroidWorkOderPhotoFileDao(
			IPnrAndroidWorkOderPhotoFileDao pnrAndroidWorkOderPhotoFileDao) {
		this.pnrAndroidWorkOderPhotoFileDao = pnrAndroidWorkOderPhotoFileDao;
		this.setCommonGenericDao(pnrAndroidWorkOderPhotoFileDao);
	}

}
