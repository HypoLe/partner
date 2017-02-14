package com.boco.eoms.partner.baseinfo.dao;

import com.boco.eoms.base.dao.Dao;
import java.util.List;
import java.util.Map;
import com.boco.eoms.partner.baseinfo.model.PartnerEvaluation;

/**
 * <p>
 * Title:合作伙伴服务评价
 * </p>
 * <p>
 * Description:合作伙伴服务评价
 * </p>
 * <p>
 * Tue Apr 27 16:50:24 CST 2010
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public interface PartnerEvaluationDao extends Dao {

    /**
    *
    *取合作伙伴服务评价列表
    * @return 返回合作伙伴服务评价列表
    */
    public List getPartnerEvaluations();
    
    /**
    * 根据主键查询合作伙伴服务评价
    * @param id 主键
    * @return 返回某id的合作伙伴服务评价
    */
    public PartnerEvaluation getPartnerEvaluation(final String id);
    
    /**
    *
    * 保存合作伙伴服务评价    
    * @param partnerEvaluation 合作伙伴服务评价
    * 
    */
    public void savePartnerEvaluation(PartnerEvaluation partnerEvaluation);
    
    /**
    * 根据id删除合作伙伴服务评价
    * @param id 主键
    * 
    */
    public void removePartnerEvaluation(final String id);
    
    /**
    * 分页取列表
    * @param curPage 当前页
    * @param pageSize 每页显示条数
    * @param whereStr where条件
    * @return map中total为条数,result(list) curPage页的记录
    */
    public Map getPartnerEvaluations(final Integer curPage, final Integer pageSize,
			final String whereStr);
	
}