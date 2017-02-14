/**
 * 
 */
package com.boco.eoms.partner.assess.AssAlgorithm.mgr.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.boco.eoms.partner.assess.AssAlgorithm.dao.IAssSelectedInstanceDao;
import com.boco.eoms.partner.assess.AssAlgorithm.mgr.IAssSelectedInstanceMgr;
import com.boco.eoms.partner.assess.AssAlgorithm.model.AssSelectedInstance;

/**
 * <p>
 * Title:指标打分实例
 * </p>
 * <p>
 * Description:指标打分实例
 * </p>
 * <p>
 * Date:Dec 30, 2010 6:12:54 AM
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
 * 
 */
public class AssSelectedInstanceMgrImpl implements IAssSelectedInstanceMgr {
	 
	private IAssSelectedInstanceDao  assSelectedInstanceDao;
 	
	public IAssSelectedInstanceDao getAssSelectedInstanceDao() {
		return this.assSelectedInstanceDao;
	}
 	
	public void setAssSelectedInstanceDao(IAssSelectedInstanceDao assSelectedInstanceDao) {
		this.assSelectedInstanceDao = assSelectedInstanceDao;
	}
	
    public void saveAssSelectedInstance(AssSelectedInstance instance) {
    		assSelectedInstanceDao.saveAssSelectedInstance(instance);
    }
    
	public Map getAssSelectedInstanceMap(final String kpiId,final String taskId,final String areaId,final String time,final String partnerId){
		List instanceList = assSelectedInstanceDao.getAssSelectedInstanceMap(kpiId,taskId,areaId,time,partnerId);
		AssSelectedInstance instance = null;
		Map instanceMap = new HashMap();
		for(int i=0;i<instanceList.size();i++){
			instance = (AssSelectedInstance)instanceList.get(i);
			instanceMap.put(instance.getConfigId(), instance);
		}
		return instanceMap;
	}
	
	public List getAssSelectedInstanceList(final String kpiId,final String taskId,final String areaId,final String time,final String partnerId){
		return assSelectedInstanceDao.getAssSelectedInstanceMap(kpiId,taskId,areaId,time,partnerId);
	}	
	public void removeAssSelectedInstances(final String kpiId,final String taskId,final String areaId,final String time,final String partnerId){
		assSelectedInstanceDao.removeAssSelectedInstances(kpiId,taskId,areaId,time,partnerId);
	}
	public List getAssSelectedInstanceListByQuote(final String taskId,final String areaId,final String time,final String partnerId,final String quote){
		return assSelectedInstanceDao.getAssSelectedInstanceListByQuote(taskId, areaId, time, partnerId, quote);
	}
	
}
