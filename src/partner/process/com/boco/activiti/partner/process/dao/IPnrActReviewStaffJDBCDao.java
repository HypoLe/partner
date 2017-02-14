package com.boco.activiti.partner.process.dao;

/**
 * 
 * @author WangJun
 *
 */

public interface IPnrActReviewStaffJDBCDao {
	
   /**
	 * 验证地市的审核人员是否已存在
	 * @param cityId
	 * @param id
	 * @return
	 */
    public int checkCityIdUnique(String cityId,String id);
	
	

}
