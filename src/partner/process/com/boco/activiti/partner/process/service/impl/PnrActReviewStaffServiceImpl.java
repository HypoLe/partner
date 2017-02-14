package com.boco.activiti.partner.process.service.impl;

import java.util.Map;

import com.boco.activiti.partner.process.dao.IPnrActReviewStaffDao;
import com.boco.activiti.partner.process.dao.IPnrActReviewStaffJDBCDao;
import com.boco.activiti.partner.process.model.PnrActReviewStaff;
import com.boco.activiti.partner.process.service.PnrActReviewStaffService;

public class PnrActReviewStaffServiceImpl implements PnrActReviewStaffService {
	private IPnrActReviewStaffDao pnrActReviewStaffDao;
	private IPnrActReviewStaffJDBCDao pnrActReviewStaffDaoJDBC;

	/**
	 * 保存会审人员
	 */
	public void savePnrActReviewStaff(PnrActReviewStaff pnrActReviewStaff) {
		pnrActReviewStaffDao.savePnrActReviewStaff(pnrActReviewStaff);
	}

	/**
	 * 通过主键ID获取会审人员
	 * 
	 * @param id
	 * @return
	 */
	public PnrActReviewStaff getPnrActReviewStaff(String id) {
		return pnrActReviewStaffDao.getPnrActReviewStaff(id);
	}
	
	/**
	 * 查询会审人员
	 */
	@SuppressWarnings("unchecked")
	public Map queryPnrActReviewStaff(Integer pageIndex,Integer pageSize,String whereStr){
		return pnrActReviewStaffDao.queryPnrActReviewStaff( pageIndex, pageSize, whereStr);
	}

	/**
	 * 验证地市的审核人员是否已存在
	 * @param cityId
	 * @param id
	 * @return
	 */
	public int checkCityIdUnique(String cityId,String id){
		return pnrActReviewStaffDaoJDBC.checkCityIdUnique(cityId,id);
	}

	public IPnrActReviewStaffDao getPnrActReviewStaffDao() {
		return pnrActReviewStaffDao;
	}

	public void setPnrActReviewStaffDao(
			IPnrActReviewStaffDao pnrActReviewStaffDao) {
		this.pnrActReviewStaffDao = pnrActReviewStaffDao;
	}

	public IPnrActReviewStaffJDBCDao getPnrActReviewStaffDaoJDBC() {
		return pnrActReviewStaffDaoJDBC;
	}

	public void setPnrActReviewStaffDaoJDBC(
			IPnrActReviewStaffJDBCDao pnrActReviewStaffDaoJDBC) {
		this.pnrActReviewStaffDaoJDBC = pnrActReviewStaffDaoJDBC;
	}

	
}