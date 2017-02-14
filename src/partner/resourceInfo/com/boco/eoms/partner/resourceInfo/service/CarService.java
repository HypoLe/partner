package com.boco.eoms.partner.resourceInfo.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.upload.FormFile;

import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
import com.boco.eoms.partner.resourceInfo.model.Apparatus;
import com.boco.eoms.partner.resourceInfo.model.Car;
import com.boco.eoms.partner.resourceInfo.util.ImportResult;

public interface CarService extends CommonGenericService<Car>{
	/**
	 * 
	 *@Description 通过excel批量导入
	 *@date May 3, 2013 5:14:15 PM
	 *@author Fengguangping fengguangping@boco.com.cn
	 *@param formFile
	 *@return
	 *@throws Exception
	 */
	public ImportResult importFromFile( FormFile formFile) throws Exception;
	/**
	 * 
	 *@Description 通过车牌号和主键id来获取车辆列表信息
	 *@date May 3, 2013 5:14:18 PM
	 *@author Fengguangping fengguangping@boco.com.cn
	 *@param carNumber：车牌号
	 *@param id：主键id
	 *@return
	 *@throws Exception
	 */
	public List findByCarNumber(String carNumber,String id) throws Exception;
	/**
	 * 通过主键id获取车辆信息
	 */
	public Car find(String id);
	/**
	 * 
	 *@Description 通过车辆GPS编号和主键id获取车辆列表
	 *@date May 3, 2013 5:14:08 PM
	 *@author Fengguangping fengguangping@boco.com.cn
	 *@param carGPSNumber：车辆GPS编号
	 *@param id：主键id
	 *@return
	 *@throws Exception
	 */
	public List<Car> findByCarGPSNumber(String carGPSNumber, String id) throws Exception;
	/**
	 * 
	 *@Description 从登录的申请人员所在省公司开始搜索获取可以发起可申请调度(车辆的删除标志位‘0’,状态为‘123020201’-在用,调度状态为‘0’-空闲)的车辆;
	 *该方法还可以扩展作为应急调度时，移动人员可以查看他管辖区域的可调度车辆;
	 *@date May 3, 2013 5:14:02 PM
	 *@author Fengguangping fengguangping@boco.com.cn
	 *@return
	 *@throws Exception
	 */
	public List<Car> getDispatchCar(HttpServletRequest request) throws Exception;
	/**
	 * 
	 *@Description 根据指定部门的deptid获取可申请调度(车辆的删除标志位‘0’,状态为‘123020201’-在用,调度状态为‘0’-空闲)的车辆;
	 *@date May 3, 2013 5:20:01 PM
	 *@author Fengguangping fengguangping@boco.com.cn
	 *@param deptid:部门的deptid
	 *@return
	 *@throws Exception
	 */
	public List<Car> getDispatchCar(String deptid) throws Exception;
	/**
	 * 
	 *@Description:通过车牌号获取未删除的车辆实体
	 *@date May 6, 2013 10:08:14 AM
	 *@author Fengguangping fengguangping@boco.com.cn
	 *@param carNumber:车牌号
	 *@return
	 *@throws Exception
	 */
	public Car getCarByCarNumber(String carNumber)throws Exception;
	/**
	 * 
	 *@Description:通过车牌号更新车辆坐标信息
	 *@date May 15, 2013 9:16:29 AM
	 *@author Fengguangping fengguangping@boco.com.cn
	 *@param carNumber
	 *@param latitude
	 *@param longtitude
	 *@throws Exception
	 */
	public void updateCarLocationByCarNumber(String carNumber,String latitude,String longtitude) throws Exception;
	/**
	 * 
	 *@Description:通过车牌号获取车辆的任务列表;
	 *@date May 15, 2013 9:17:58 AM
	 *@author Fengguangping fengguangping@boco.com.cn
	 *@param carNumber
	 *@return
	 *@throws Exception
	 */
	public List getCarTaskListByCarNumber(String carNumber) throws Exception;
	
	public Map getCars(final Integer curPage, final Integer pageSize, final String whereStr) throws Exception ;
}
