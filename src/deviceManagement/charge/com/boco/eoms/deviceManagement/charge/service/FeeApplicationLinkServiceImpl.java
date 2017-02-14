package com.boco.eoms.deviceManagement.charge.service;


import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.deviceManagement.charge.dao.FeeApplicationLinkDao;
import com.boco.eoms.deviceManagement.charge.model.FeeApplicationLink;

import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;


public class FeeApplicationLinkServiceImpl extends
              CommonGenericServiceImpl<FeeApplicationLink> implements
              FeeApplicationLinkService {

	public void setFeeApplicationLinkDao(FeeApplicationLinkDao feeApplicationLinkDao) {
		this.setCommonGenericDao(feeApplicationLinkDao);
	}
	
	
	
	
}
