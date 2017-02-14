package com.boco.eoms.partner.netresource.service;

import java.util.List;

import org.apache.struts.upload.FormFile;

import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
import com.boco.eoms.partner.netresource.model.bs.BsBtResource;
import com.boco.eoms.partner.netresource.model.bs.TowerAntennas;
import com.boco.eoms.partner.netresource.util.ImportResult;

/** 
 * Description: 
 * Copyright:   Copyright (c)2013
 * Company:     BOCO 

 * @author:     chenbing 
 * @version:    1.0 
 */ 
public interface TowerAntennasMgr extends CommonGenericService<TowerAntennas> {
		
	
		/**
		 * 通过姓名获取TowerAntennas对象
		 * @param anName
		 * @return List
		 */
		public List<TowerAntennas> getTowerAntennasByName(String anName);
		/**
		 * 资源导入-铁塔天馈资源
		 * @param formFile
		 * @param province
		 * @param specialty
		 * @param creatorId
		 * @return
		 * @throws Exception 
		 */
		public ImportResult importTowerAntennasFromFile(FormFile formFile,String province,String specialty,String creatorId,String osPath) throws Exception;  //资源的导入
		
}
