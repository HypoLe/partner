package com.boco.eoms.partner.baseinfo.dao;

import com.boco.eoms.base.dao.Dao;
import java.util.List;
import java.util.Map;
import com.boco.eoms.partner.baseinfo.model.InstrumentConfig;

/**
 * <p>
 * Title:仪器仪表配置
 * </p>
 * <p>
 * Description:仪器仪表配置
 * </p>
 * <p>
 * Mon Dec 14 14:07:13 CST 2009
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public interface IInstrumentConfigDao extends Dao {

    /**
    *
    *取仪器仪表配置列表
    * @return 返回仪器仪表配置列表
    */
    public List getInstrumentConfigs();
    
    /**
    * 根据主键查询仪器仪表配置
    * @param id 主键
    * @return 返回某id的仪器仪表配置
    */
    public InstrumentConfig getInstrumentConfig(final String id);
    
    /**
    *
    * 保存仪器仪表配置    
    * @param instrumentConfig 仪器仪表配置
    * 
    */
    public void saveInstrumentConfig(InstrumentConfig instrumentConfig);
    
    /**
    * 根据id删除仪器仪表配置
    * @param id 主键
    * 
    */
    public void removeInstrumentConfig(final String id);
    
    /**
    * 分页取列表
    * @param curPage 当前页
    * @param pageSize 每页显示条数
    * @param whereStr where条件
    * @return map中total为条数,result(list) curPage页的记录
    */
    public Map getInstrumentConfigs(final Integer curPage, final Integer pageSize,
			final String whereStr);
    /**
     * 按条件取仪器仪表配置列表
     * @param whereStr where条件
     * @return 返回符合条件的仪器仪表列表
     */
    public List getInstrumentConfigs(final String where);
}