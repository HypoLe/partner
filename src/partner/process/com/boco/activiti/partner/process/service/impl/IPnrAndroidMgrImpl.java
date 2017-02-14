package com.boco.activiti.partner.process.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.boco.activiti.partner.process.model.PnrAndroidPhotoFile;
import com.boco.activiti.partner.process.model.PnrTroubleTicket;
import com.boco.activiti.partner.process.service.IPnrAndroidMgr;
import com.boco.eoms.deviceManagement.common.pojo.CommonSearch;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.process.dao.PnrAndroidPhotoFileDao;
import com.googlecode.genericdao.search.ExampleOptions;
import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

public class IPnrAndroidMgrImpl extends CommonGenericServiceImpl<PnrAndroidPhotoFile> implements IPnrAndroidMgr{
	
	
	private  PnrAndroidPhotoFileDao pnrAndroidPhotoFileDao;

	@Override
	public void saveOrUpdateTaskFile(PnrAndroidPhotoFile photoFile, String data)
			throws SQLException {
		// TODO Auto-generated method stub
		
		pnrAndroidPhotoFileDao.save(photoFile, data);
		
		
	}

	public PnrAndroidPhotoFileDao getPnrAndroidPhotoFileDao() {
		return pnrAndroidPhotoFileDao;
	}

	public void setPnrAndroidPhotoFileDao(
			PnrAndroidPhotoFileDao pnrAndroidPhotoFileDao) {
		this.pnrAndroidPhotoFileDao = pnrAndroidPhotoFileDao;
		this.setCommonGenericDao(pnrAndroidPhotoFileDao);

	}

	@Override
	public Map getResources(Integer curPage, Integer pageSize, String whereStr) {
		// TODO Auto-generated method stub
		return pnrAndroidPhotoFileDao.getResources(curPage, pageSize, whereStr);
	}
	
	
}
