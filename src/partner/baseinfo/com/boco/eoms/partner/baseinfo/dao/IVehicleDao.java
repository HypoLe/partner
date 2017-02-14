package com.boco.eoms.partner.baseinfo.dao;

import com.boco.eoms.base.dao.Dao;
import java.util.List;
import java.util.Map;
import com.boco.eoms.partner.baseinfo.model.Vehicle;

/**
 * <p>
 * Title:车辆配置
 * </p>
 * <p>
 * Description:资源配置管理 车辆配置
 * </p>
 * <p>
 * Mon Dec 07 19:17:45 CST 2009
 * </p>
 * 
 * @author wangjunfeng
 * @version 1.0
 * 
 */
public interface IVehicleDao extends Dao {

    /**
    *
    *取车辆配置列表
    * @return 返回车辆配置列表
    */
    public List getVehicles();
    
    /**
    *
    *根据当前用户 加载当前用户所在地市 列表 (地市或县区)
	 * @return 返回当前用户所在地市列表(地市或县区)
    */
    public List listCityOfUser(final String userName);

 
    /**
    *根据当前地域信息 取出对应地域ID
	 * @return 返回地域ID列表
    */
    public List listCityOfAreaName(final String areaName);

    
    /**
    *
    *根据当前用户 加载当前用户所在地市 列表 (地市)
	 * @return 返回当前用户所在地市列表(地市)
    */
    public List listCityOfArea(final String areaid);

    /**
    *
    *二级联动菜单 加载县区 列表
    * @return 返回抽查记录列表
    */
    public List listCountyOfCity(final String cityId,final String len);
 
    
	/**
	 * 
	 * 判断是否有重复(联合主键)
	 * @return 返回重复条件的列表 
	 */
	public List isUnique(final String whereStr);
    
    
    /**
    * 根据主键查询车辆配置
    * @param id 主键
    * @return 返回某id的车辆配置
    */
    public Vehicle getVehicle(final String id);
    
    /**
    *
    * 保存车辆配置    
    * @param vehicle 车辆配置
    * 
    */
    public void saveVehicle(Vehicle vehicle);
    
    /**
    * 根据id删除车辆配置
    * @param id 主键
    * 
    */
    public void removeVehicle(final String id);
    
    /**
    * 分页取列表
    * @param curPage 当前页
    * @param pageSize 每页显示条数
    * @param whereStr where条件
    * @return map中total为条数,result(list) curPage页的记录
    */
    public Map getVehicles(final Integer curPage, final Integer pageSize,
			final String whereStr);
	
}