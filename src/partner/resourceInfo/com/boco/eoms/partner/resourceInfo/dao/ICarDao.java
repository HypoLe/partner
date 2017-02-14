package com.boco.eoms.partner.resourceInfo.dao;

import java.util.List;
import java.util.Map;
import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.deviceManagement.common.dao.CommonGenericDao;
import com.boco.eoms.partner.resourceInfo.model.Car;

public interface ICarDao extends CommonGenericDao<Car, String> {
	/**
	 * 
	 *@Description:hql更新车辆实体
	 *@date May 15, 2013 10:15:09 AM
	 *@author Fengguangping fengguangping@boco.com.cn
	 *@param hql
	 *@throws Exception
	 */
	public void updateCarByHql(String hql) throws Exception;
	/**
	 * 
	 *@Description:通过车牌号更新车辆的坐标
	 *@date May 15, 2013 10:15:31 AM
	 *@author Fengguangping fengguangping@boco.com.cn
	 *@param latitude
	 *@param longtitude
	 *@param carNumber
	 *@throws Exception
	 */
	public void updateCarLocationByCarNumber(String latitude,String longtitude,String carNumber) throws Exception;

	public Map getCars(final Integer curPage, final Integer pageSize, final String whereStr);
}
