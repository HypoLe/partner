package com.boco.activiti.partner.process.service;

import java.util.Map;

import com.boco.activiti.partner.process.model.PnrActReviewStaff;

public interface PnrActReviewStaffService {

	/**
	 * 保存会审人员
	 */
	public void savePnrActReviewStaff(PnrActReviewStaff pnrActReviewStaff);

	/**
	 * 通过主键ID获取会审人员
	 * 
	 * @param id
	 * @return
	 */
	public PnrActReviewStaff getPnrActReviewStaff(String id);
	
	/**
	 * 查询会审人员
	 */
	@SuppressWarnings("unchecked")
	public Map queryPnrActReviewStaff(Integer pageIndex,Integer pageSize,String whereStr);
	
	/**
	 * 验证地市的审核人员是否已存在
	 * @param cityId
	 * @param id
	 * @return
	 */
	public int checkCityIdUnique(String cityId,String id);

}