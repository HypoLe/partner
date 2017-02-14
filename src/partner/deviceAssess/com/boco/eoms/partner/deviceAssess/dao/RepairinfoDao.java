package com.boco.eoms.partner.deviceAssess.dao;

import com.boco.eoms.base.dao.Dao;
import java.util.List;
import java.util.Map;
import com.boco.eoms.partner.deviceAssess.model.Repairinfo;

/**
 * <p>
 * Title:板件返修事件信息
 * </p>
 * <p>
 * Description:板件返修事件信息
 * </p>
 * <p>
 * Tue Sep 28 15:24:09 CST 2010
 * </p>
 *  
 * @author zhangxuesong
 * @version 1.0
 * 
 */
public interface RepairinfoDao extends Dao {

    /**
    *
    *取板件返修事件信息列表
    * @return 返回板件返修事件信息列表
    */
    public List getRepairinfos();
    
    /**
    * 根据主键查询板件返修事件信息
    * @param id 主键
    * @return 返回某id的板件返修事件信息
    */
    public Repairinfo getRepairinfo(final String id);
    
    /**
    *
    * 保存板件返修事件信息    
    * @param repairinfo 板件返修事件信息
    * 
    */
    public void saveRepairinfo(Repairinfo repairinfo);
    
    /**
    * 根据id删除板件返修事件信息
    * @param id 主键
    * 
    */
    public void removeRepairinfo(final String id);
    
    /**
    * 分页取列表
    * @param curPage 当前页
    * @param pageSize 每页显示条数
    * @param whereStr where条件
    * @return map中total为条数,result(list) curPage页的记录
    */
    public Map getRepairinfos(final Integer curPage, final Integer pageSize,
			final String whereStr);
	
}