package com.boco.eoms.summary.dao;

import com.boco.eoms.base.dao.Dao;
import java.util.List;
import java.util.Map;
import com.boco.eoms.summary.model.TawzjMonth;

/**
 * <p>
 * Title:月工作总结
 * </p>
 * <p>
 * Description:true
 * </p>
 * <p>
 * Wed Jun 17 14:13:03 CST 2009
 * </p>
 * 
 * @author hanlu
 * @version 3.5
 * 
 */
public interface ITawzjMonthDao extends Dao {

	/**
    *
    *取月工作总结列表
    * @return 返回月工作总结列表
    */
    public List getTawzjMonths();
    
    /**
    * 根据主键查询月工作总结
    * @param id 主键
    * @return 返回某id的月工作总结
    */
    public TawzjMonth getTawzjMonth(final String id);
    
    /**
    *
    * 保存月工作总结    
    * @param tawzjMonth 月工作总结
    * 
    */
    public void saveTawzjMonth(TawzjMonth tawzjMonth);
    
    /**
    * 根据id删除月工作总结
    * @param id 主键
    * 
    */
    public void removeTawzjMonth(final String id);
    
    /**
    * 分页取列表
    * @param curPage 当前页
    * @param pageSize 每页显示条数
    * @param whereStr where条件
    * @return map中total为条数,result(list) curPage页的记录
    */
    public Map getTawzjMonths(final Integer curPage, final Integer pageSize,
			final String whereStr);
	
}