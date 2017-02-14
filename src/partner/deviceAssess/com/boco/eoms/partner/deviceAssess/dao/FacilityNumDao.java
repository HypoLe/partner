package com.boco.eoms.partner.deviceAssess.dao;

import java.util.List;
import java.util.Map;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.partner.deviceAssess.model.FacilityNum;

/**
 * <p>
 * Title:设备量信息
 * </p>
 * <p>
 * Description:设备量信息
 * </p>
 * <p>
 * Wed Sep 29 11:28:40 CST 2010
 * </p> 
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public interface FacilityNumDao extends Dao {

    /**
    *
    *取设备量信息列表
    * @return 返回设备量信息列表
    */
    public List getFacilityNums();
    
    /**
    * 根据主键查询设备量信息
    * @param id 主键
    * @return 返回某id的设备量信息
    */
    public FacilityNum getFacilityNum(final String id);
    
    /**
    *
    * 保存设备量信息    
    * @param facilityNum 设备量信息
    * 
    */
    public void saveFacilityNum(FacilityNum facilityNum);
    
    /**
    * 根据id删除设备量信息
    * @param id 主键
    * 
    */
    public void removeFacilityNum(final String id);
    
    /**
    * 分页取列表
    * @param curPage 当前页
    * @param pageSize 每页显示条数
    * @param whereStr where条件
    * @return map中total为条数,result(list) curPage页的记录
    */
    public Map getFacilityNums(final Integer curPage, final Integer pageSize,
			final String whereStr);
	
}