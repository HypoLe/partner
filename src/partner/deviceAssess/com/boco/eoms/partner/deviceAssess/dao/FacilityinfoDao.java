package com.boco.eoms.partner.deviceAssess.dao;

import com.boco.eoms.base.dao.Dao;
import java.util.List;
import java.util.Map;
import com.boco.eoms.partner.deviceAssess.model.Facilityinfo;

/**
 * <p>
 * Title:厂家设备问题事件信息
 * </p>
 * <p>
 * Description:厂家设备问题事件信息
 * </p>
 * <p>
 * Tue Sep 28 15:24:09 CST 2010 
 * </p>
 * 
 * @author zhangxuesong
 * @version 1.0
 * 
 */
public interface FacilityinfoDao extends Dao {

    /**
    *
    *取厂家设备问题事件信息列表
    * @return 返回厂家设备问题事件信息列表
    */
    public List getFacilityinfos();
    
    /**
    * 根据主键查询厂家设备问题事件信息
    * @param id 主键
    * @return 返回某id的厂家设备问题事件信息
    */
    public Facilityinfo getFacilityinfo(final String id);
    
    /**
    *
    * 保存厂家设备问题事件信息    
    * @param facilityinfo 厂家设备问题事件信息
    * 
    */
    public void saveFacilityinfo(Facilityinfo facilityinfo);
    
    /**
    * 根据id删除厂家设备问题事件信息
    * @param id 主键
    * 
    */
    public void removeFacilityinfo(final String id);
    
    /**
    * 分页取列表
    * @param curPage 当前页
    * @param pageSize 每页显示条数
    * @param whereStr where条件
    * @return map中total为条数,result(list) curPage页的记录
    */
    public Map getFacilityinfos(final Integer curPage, final Integer pageSize,
			final String whereStr);
	
}