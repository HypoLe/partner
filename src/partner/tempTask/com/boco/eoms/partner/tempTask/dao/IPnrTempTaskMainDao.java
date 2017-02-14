package com.boco.eoms.partner.tempTask.dao;

import com.boco.eoms.base.dao.Dao;
import java.util.List;
import java.util.Map;
import com.boco.eoms.partner.tempTask.model.PnrTempTaskMain;

/**
 * <p>
 * Title:合作伙伴临时任务管理
 * </p>
 * <p>
 * Description:合作伙伴临时任务管理主表信息
 * </p>
 * <p>
 * Mon Apr 26 14:09:01 CST 2010
 * </p>
 * 
 * @author daizhigang
 * @version 1.0
 * 
 */
public interface IPnrTempTaskMainDao extends Dao {

    /**
    *
    *取合作伙伴临时任务管理列表
    * @return 返回合作伙伴临时任务管理列表
    */
    public List getPnrTempTaskMains();
    
    /**
    * 根据主键查询合作伙伴临时任务管理
    * @param id 主键
    * @return 返回某id的合作伙伴临时任务管理
    */
    public PnrTempTaskMain getPnrTempTaskMain(final String id);
    
    /**
    *
    * 保存合作伙伴临时任务管理    
    * @param pnrTempTaskMain 合作伙伴临时任务管理
    * 
    */
    public void savePnrTempTaskMain(PnrTempTaskMain pnrTempTaskMain);
    
    /**
    * 根据id删除合作伙伴临时任务管理
    * @param id 主键
    * 
    */
    public void removePnrTempTaskMain(final String id);
    
    /**
    * 分页取列表
    * @param curPage 当前页
    * @param pageSize 每页显示条数
    * @param whereStr where条件
    * @return map中total为条数,result(list) curPage页的记录
    */
    public Map getPnrTempTaskMains(final Integer curPage, final Integer pageSize,
			final String whereStr);
	
}