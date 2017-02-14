package com.boco.eoms.partner.process.dao;

import java.sql.SQLException;
import java.util.Map;

import com.boco.activiti.partner.process.model.PnrAndroidPhotoFile;
import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.deviceManagement.common.dao.CommonGenericDao;
import com.boco.eoms.partner.process.model.PartnerProcessMain;

public interface PnrAndroidPhotoFileDao  extends  CommonGenericDao<PnrAndroidPhotoFile, String>{
	
	void save(PnrAndroidPhotoFile photoFile, String data) throws SQLException;
	
	   public Map getResources(Integer curPage, Integer pageSize, String whereStr);

}
