package com.boco.eoms.parter.baseinfo.fee.dao;

import com.boco.eoms.base.dao.Dao;
import java.util.List;
import java.util.Map;
import com.boco.eoms.parter.baseinfo.fee.model.PartnerFeeUnit;

/**
 * <p>
 * Title:合作伙伴费用单位
 * </p>
 * <p>
 * Description:合作伙伴费用单位
 * </p>
 * <p>
 * Tue Sep 08 09:38:34 CST 2009
 * </p>
 * 
 * @author lvweihua
 * @version 3.5
 * 
 */
public interface PartnerFeeUnitDao extends Dao {

    /**
    *
    *取合作伙伴费用单位列表
    * @return 返回合作伙伴费用单位列表
    */
    public List getPartnerFeeUnits();
    
    /**
    * 根据主键查询合作伙伴费用单位
    * @param id 主键
    * @return 返回某id的合作伙伴费用单位
    */
    public PartnerFeeUnit getPartnerFeeUnit(final String id);
    
    /**
    *
    * 保存合作伙伴费用单位    
    * @param partnerFeeUnit 合作伙伴费用单位
    * 
    */
    public void savePartnerFeeUnit(PartnerFeeUnit partnerFeeUnit);
    
    /**
    * 根据id删除合作伙伴费用单位
    * @param id 主键
    * 
    */
    public void removePartnerFeeUnit(final String id);
    
    /**
    * 分页取列表
    * @param curPage 当前页
    * @param pageSize 每页显示条数
    * @param whereStr where条件
    * @return map中total为条数,result(list) curPage页的记录
    */
    public Map getPartnerFeeUnits(final Integer curPage, final Integer pageSize,
			final String whereStr);
	
}