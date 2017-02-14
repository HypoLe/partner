package com.boco.activiti.partner.process.dao;

import java.util.Map;

import com.boco.activiti.partner.process.model.PnrActReviewStaff;


public interface IPnrActReviewStaffDao  {

	/**
	 * 保存会审人员
	 */
    public void savePnrActReviewStaff(PnrActReviewStaff pnrActReviewStaff);
    
    /**
	 * 通过主键ID获取会审人员
	 * @param id
	 * @return
	 */
    public PnrActReviewStaff getPnrActReviewStaff(final String id);
    
    /**
     * 查询会审人员
     * @param pageIndex
     * @param pageSize
     * @param whereStr
     * @return
     */
    @SuppressWarnings("unchecked")
	public Map queryPnrActReviewStaff(final Integer curPage,final Integer pageSize,final String whereStr);
    
  
}