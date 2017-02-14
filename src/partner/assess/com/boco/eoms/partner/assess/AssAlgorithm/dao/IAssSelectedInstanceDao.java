/**
 * 
 */
package com.boco.eoms.partner.assess.AssAlgorithm.dao;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.assess.AssAlgorithm.model.AssSelectedInstance;

/**
 * <p>
 * Title:指标打分实例
 * </p>
 * <p>
 * Description:指标打分实例
 * </p>
 * <p>
 * Date:Dec 30, 2010 6:17:54 AM
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
 * 
 */
public interface IAssSelectedInstanceDao {
    /**
	    * 保存指标打分实例
	    * @param instance 指标打分实例
	    */
	public void saveAssSelectedInstance(AssSelectedInstance instance);
    /**
	    * 根据taskId,areaId,time,partnerId得到对应的指标打分实例集合
	    * @param List 指标打分实例集合
	    */
	public List getAssSelectedInstanceMap(final String kpiId,final String taskId,final String areaId,final String time,final String partnerId);
	/**
	    * 根据taskId,areaId,time,partnerId删除对应的指标打分实例集合
	    * @param void
	    */
	public void removeAssSelectedInstances(final String kpiId,final String taskId,final String areaId,final String time,final String partnerId);
    /**
	    * 根据taskId,areaId,time,partnerId,quote(是否引用上月数据)得到对应的指标打分实例集合
	    * @param List 指标打分实例集合
	    */	
	public List getAssSelectedInstanceListByQuote(final String taskId,final String areaId,final String time,final String partnerId,final String quote);

}
