package com.boco.eoms.partner.deviceAssess.dao;

import com.boco.eoms.base.dao.Dao;
import java.util.List;
import java.util.Map;
import com.boco.eoms.partner.deviceAssess.model.Ftraininfo;

/**
 * <p>
 * Title:pnr_deviceassess_ftrain_info
 * </p>
 * <p>
 * Description:pnr_deviceassess_ftrain_info
 * </p>
 * <p> 
 * Sun Sep 26 09:11:03 CST 2010
 * </p>
 * 
 * @author zhangxuesong
 * @version 1.0
 * 
 */
public interface FtraininfoDao extends Dao {

    /**
    *
    *取pnr_deviceassess_ftrain_info列表
    * @return 返回pnr_deviceassess_ftrain_info列表
    */
    public List getFtraininfos();
    
    /**
    * 根据主键查询pnr_deviceassess_ftrain_info
    * @param id 主键
    * @return 返回某id的pnr_deviceassess_ftrain_info
    */
    public Ftraininfo getFtraininfo(final String id);
    
    /**
    *
    * 保存pnr_deviceassess_ftrain_info    
    * @param ftraininfo pnr_deviceassess_ftrain_info
    * 
    */
    public void saveFtraininfo(Ftraininfo ftraininfo);
    
    /**
    * 根据id删除pnr_deviceassess_ftrain_info
    * @param id 主键
    * 
    */
    public void removeFtraininfo(final String id);
    
    /**
    * 分页取列表
    * @param curPage 当前页
    * @param pageSize 每页显示条数
    * @param whereStr where条件
    * @return map中total为条数,result(list) curPage页的记录
    */
    public Map getFtraininfos(final Integer curPage, final Integer pageSize,
			final String whereStr);
	
}