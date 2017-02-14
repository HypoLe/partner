package com.boco.eoms.partner.deviceAssess.dao;

import com.boco.eoms.base.dao.Dao;
import java.util.List;
import java.util.Map;
import com.boco.eoms.partner.deviceAssess.model.Peventinfo;

/**
 * <p>
 * Title:peventinfo
 * </p>
 * <p>
 * Description:peventinfo
 * </p>
 * <p>
 * Sat Sep 25 10:35:07 CST 2010
 * </p>
 * 
 * @author zhangxuesong
 * @version 1.0 
 * 
 */
public interface PeventinfoDao extends Dao {

    /**
    *
    *取peventinfo列表
    * @return 返回peventinfo列表
    */
    public List getPeventinfos();
    
    /**
    * 根据主键查询peventinfo
    * @param id 主键
    * @return 返回某id的peventinfo
    */
    public Peventinfo getPeventinfo(final String id);
    
    /**
    *
    * 保存peventinfo    
    * @param peventinfo peventinfo
    * 
    */
    public void savePeventinfo(Peventinfo peventinfo);
    
    /**
    * 根据id删除peventinfo
    * @param id 主键
    * 
    */
    public void removePeventinfo(final String id);
    
    /**
    * 分页取列表
    * @param curPage 当前页
    * @param pageSize 每页显示条数
    * @param whereStr where条件
    * @return map中total为条数,result(list) curPage页的记录
    */
    public Map getPeventinfos(final Integer curPage, final Integer pageSize,
			final String whereStr);
	
}