package com.boco.activiti.partner.process.dao;

import com.boco.activiti.partner.process.model.PnrAndroidWorkOderPhotoFile;
import com.boco.eoms.deviceManagement.common.dao.CommonGenericDao;

public interface IPnrAndroidWorkOderPhotoFileDao extends CommonGenericDao<PnrAndroidWorkOderPhotoFile,String>{
	public void savePhoto(PnrAndroidWorkOderPhotoFile pnrAndroidWorkOderPhotoFile);
	
}
