package com.boco.activiti.partner.process.service;

import com.boco.activiti.partner.process.model.PnrAndroidWorkOderPhotoFile;
import com.boco.eoms.deviceManagement.common.service.CommonGenericService;


public interface IPnrAndroidWorkOderPhotoFileService extends CommonGenericService<PnrAndroidWorkOderPhotoFile>  {
	public void savePhoto(PnrAndroidWorkOderPhotoFile pnrAndroidWorkOderPhotoFile);

}
